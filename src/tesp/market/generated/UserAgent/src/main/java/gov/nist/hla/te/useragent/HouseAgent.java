package gov.nist.hla.te.useragent;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class HouseAgent implements Agent {
    private final static Logger log = LogManager.getLogger();

    private String agentId;
    private String transformerId;

    private ArrayList<Double> loadForecast = new ArrayList<Double>();

    private static final int INTERVAL_LENGTH = 24;
    private Double[] transactedAmount = new Double[INTERVAL_LENGTH];

    public HouseAgent(String id, ArrayList<Double> data) {
        this.agentId = id;
        this.transformerId = id.split(":")[0];

        this.loadForecast = data;
        if (this.loadForecast.size() < INTERVAL_LENGTH) {
            log.error("{} initialized with less than {} data points", agentId, INTERVAL_LENGTH);
            this.loadForecast.clear();
        }

        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            transactedAmount[i] = 0.0;
        }
    }

    public String getAgentId() {
        return agentId;
    }

    public String getTransformerId() {
        return transformerId;
    }
    
    public String handleQuote(String priceString, String quantityString, boolean isBuyQuote) {
        if (loadForecast.isEmpty()) {
            log.warn("{} cannot handle quote due to missing load forecast data", agentId);
        }
        if (isBuyQuote) {
            return "";
        }
        String desiredQuantity = "";

        String[] receivedQuantity = quantityString.split(" ");

        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            Double quantity = loadForecast.get(i) - transactedAmount[i]; // possible rounding errors ?

            if (quantity > Double.parseDouble(receivedQuantity[i])) {
                quantity = Double.parseDouble(receivedQuantity[i]); // can tender.quantity exceed quote.quantity ?
            } else if (quantity < 0) {
                quantity = 0.0;
            }
            
            if (i > 0) {
                desiredQuantity += " ";
            }
            desiredQuantity += String.format("%.4f", quantity);
        }
        return desiredQuantity;
    }
    
    public void handleTransaction(String priceString, String quantityString, boolean isBuyTransaction) {
        if (loadForecast.isEmpty()) {
            log.warn("{} cannot handle transaction due to missing load forecast data", agentId);
            return;
        }
        if (isBuyTransaction) {
            return;
        }

        String[] quantities = quantityString.split(" ");

        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            transactedAmount[i] += Double.parseDouble(quantities[i]);
            final Double quantityDifference = loadForecast.get(i) - transactedAmount[i]; // possible rounding errors ?

            if (quantityDifference > 0) {
                log.info("{} still requires {} kWh for slot {} after transactions", agentId, quantityDifference, i);
            }
        }
    }

    public void closeMarket() {
        if (!loadForecast.isEmpty()) {
            loadForecast.subList(0, INTERVAL_LENGTH).clear();
            if (loadForecast.size() < INTERVAL_LENGTH) {
                log.warn("{} has run out of load forecast data", agentId);
                loadForecast.clear();
            }

            for (int i = 0; i < INTERVAL_LENGTH; i++) {
               log.info("{} bought {} kWh in slot {}", agentId, transactedAmount[i], i);
                transactedAmount[i] = 0.0;
            }
        }
    }
}
