package gov.nist.hla.te.useragent;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class HouseAgent implements Agent {
    private final static Logger log = LogManager.getLogger();

    private String agentId;
    private String transformerId;

    private String buyResponseQuantity;
    private String sellResponseQuantity;

    private ArrayList<Double> loadForecast = new ArrayList<Double>();

    private static final int INTERVAL_LENGTH = 24;
    private Double[] transactedCost = new Double[INTERVAL_LENGTH];
    private Double[] transactedQuantity = new Double[INTERVAL_LENGTH];

    public HouseAgent(String id, ArrayList<Double> data) {
        this.agentId = id;
        this.transformerId = id.split(":")[0];

        this.loadForecast = data;
        if (this.loadForecast.size() < INTERVAL_LENGTH) {
            log.error("{} initialized with less than {} data points", agentId, INTERVAL_LENGTH);
            this.loadForecast.clear();
        }

        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            transactedCost[i] = 0.0;
            transactedQuantity[i] = 0.0;
        }
    }

    public String getAgentId() {
        return agentId;
    }

    public String getTransformerId() {
        return transformerId;
    }

    public String getBuyQuoteResponse() {
        return buyResponseQuantity;
    }

    public String getSellQuoteResponse() {
        return sellResponseQuantity;
    }

    public void handleBuyQuote(String id, String priceString, String quantityString, boolean hasMarketActivity) {
        buyResponseQuantity = handleQuote(quantityString, true);
    }

    public void handleSellQuote(String id, String priceString, String quantityString, boolean hasMarketActivity) {
        sellResponseQuantity = handleQuote(quantityString, false);
    }
    
    private String handleQuote(String quantityString, boolean isBuyQuote) {
        if (loadForecast.isEmpty()) {
            log.warn("{} cannot handle quote due to missing load forecast data", agentId);
            return "";
        }

        String desiredQuantity = "";

        String[] receivedQuantity = quantityString.split(" ");

        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            Double quantity = loadForecast.get(i) - transactedQuantity[i]; // possible rounding errors ?

            if ((isBuyQuote && quantity > 0) || (!isBuyQuote && quantity < 0)) {
                quantity = 0.0;
            }
            if (isBuyQuote) {
                quantity = Math.abs(quantity);
            }
            if (quantity > Double.parseDouble(receivedQuantity[i])) {
                quantity = Double.parseDouble(receivedQuantity[i]);
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

        ArrayList<Double> prices = new ArrayList<Double>();
        for (String price : priceString.split(" ")) {
            prices.add(Double.parseDouble(price));
        }

        String[] quantities = quantityString.split(" ");

        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            final double quantity = Double.parseDouble(quantities[i]);

            if (isBuyTransaction) { // market is buying from you
                transactedCost[i] -= quantity * prices.get(i);
                transactedQuantity[i] -= quantity;
            } else { // market is selling to you
                transactedCost[i] += quantity * prices.get(i);
                transactedQuantity[i] += quantity;
            }
        }
    }

    public MarketDetails closeMarket() {
        MarketDetails results = new MarketDetails();

        if (!loadForecast.isEmpty()) {
            loadForecast.subList(0, INTERVAL_LENGTH).clear();
            if (loadForecast.size() < INTERVAL_LENGTH) {
                log.warn("{} has run out of load forecast data", agentId);
                loadForecast.clear();
            }

            for (int i = 0; i < INTERVAL_LENGTH; i++) {
                if (i > 0) {
                    results.cost += " ";
                    results.quantity += " ";
                }
                results.cost += String.format("%.4f", transactedCost[i]);
                results.quantity += String.format("%.4f", transactedQuantity[i]);

                transactedCost[i] = 0.0;
                transactedQuantity[i] = 0.0;
            }

            log.info("{} total transaction cost {}", agentId, results.cost);
            log.info("{} total transaction quantity {}", agentId, results.quantity);
        }
        return results;
    }
}
