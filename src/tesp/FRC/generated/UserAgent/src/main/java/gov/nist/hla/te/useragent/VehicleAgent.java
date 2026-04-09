package gov.nist.hla.te.useragent;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.math3.distribution.LogNormalDistribution;

class VehicleAgent implements Agent {
    private final static Logger log = LogManager.getLogger();

    private String agentId;
    private String transformerId;

    private String buyResponseQuantity;
    private String sellResponseQuantity;

    private boolean skipCharge;
    private boolean isDayCharge;

    private double chargeRequired;
    private List<Double> nextChargeRequired = new ArrayList<Double>();

    private double chargeCoefficient;
    private double transactedTotal;

    private LogNormalDistribution chargeDistribution;
    private Random random = new Random();

    private static final int INTERVAL_LENGTH = 24;
    private Double[] transactedCost = new Double[INTERVAL_LENGTH];
    private Double[] transactedQuantity = new Double[INTERVAL_LENGTH];

    public VehicleAgent(String csvLine, double coefficient, double mu, double sigma) {
        String[] csvElements = csvLine.split(",");

        this.agentId = csvElements[0];
        for (int i = 1; i < csvElements.length; i++) {
            nextChargeRequired.add(Double.parseDouble(csvElements[i]));
            log.debug("{} queued charge for {}", agentId, nextChargeRequired.get(nextChargeRequired.size()-1));
        }
        
        this.transformerId = agentId.split(":")[0]; // TODO - enforce
        this.chargeCoefficient = coefficient;
        this.chargeDistribution = new LogNormalDistribution(mu, sigma);

        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            transactedCost[i] = 0.0;
            transactedQuantity[i] = 0.0;
        }
        transactedTotal = 0.0;
        resetCharge();
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
        buyResponseQuantity = handleQuote(id, priceString, quantityString, true);
    }

    public void handleSellQuote(String id, String priceString, String quantityString, boolean hasMarketActivity) {
        sellResponseQuantity = handleQuote(id, priceString, quantityString, false);
    }

    private String handleQuote(String id, String priceString, String quantityString, boolean isBuyQuote) {
        if (Integer.parseInt(id) < 2 || isBuyQuote || skipCharge) { // ignore first round of quotes
            String zeroQuantity = "";
            for (int i = 0; i < INTERVAL_LENGTH; i++) {
                if (i > 0) {
                    zeroQuantity += " ";
                }
                zeroQuantity += "0.0000";
            }
            return zeroQuantity;
        }

        ArrayList<Double> prices = new ArrayList<Double>();
        for (String price : priceString.split(" ")) {
            prices.add(Double.parseDouble(price));
        }

        ArrayList<Double> quantities = new ArrayList<Double>();
        for (String quantity : quantityString.split(" ")) {
            quantities.add(Double.parseDouble(quantity));
        }

        if (prices.size() != INTERVAL_LENGTH || quantities.size() != INTERVAL_LENGTH) {
            log.error("{} received an invalid quote", agentId);
            return "";
        }

        Integer[] indexSorted = new Integer[INTERVAL_LENGTH];
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            indexSorted[i] = i;
        }

        Arrays.sort(indexSorted, new Comparator<Integer>() {
            @Override public int compare(Integer i1, Integer i2) {
                return Double.compare(prices.get(i1), prices.get(i2));
            }
        });

        double[] responseAmount = new double[INTERVAL_LENGTH];
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            responseAmount[i] = 0.0;
        }

        final double MAX_CHARGE_RATE = 7.2;
        final double tolerance = 1e-4;

        int iteration = 0;
        double responseTotal = 0.0;
        while (responseTotal + transactedTotal < chargeRequired && iteration < INTERVAL_LENGTH) {
            int index = indexSorted[iteration];

            if (isDayCharge) { // 9 to 5
                if (index < 16) { // assumes index 0 represents 17:00
                    iteration += 1;
                    continue;
                }
            }
            else { // 10 to 7
                if (index < 5 || index > 14 ) {
                    iteration += 1;
                    continue;
                }
            }

            double chargeRateLimit = MAX_CHARGE_RATE - transactedQuantity[index];
            if (chargeRateLimit > tolerance) {
                responseAmount[index] = Math.min(chargeRateLimit, chargeRequired - transactedTotal - responseTotal);
                responseTotal += responseAmount[index];
            }

            iteration += 1;
        }

        String desiredQuantity = "";
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            if (i > 0) {
                desiredQuantity += " ";
            }
            desiredQuantity += String.format("%.4f", responseAmount[i]);
        }

        return desiredQuantity;
    }

    public void handleTransaction(String priceString, String quantityString, boolean isBuyTransaction) {
        String[] quantities = quantityString.split(" ");

        ArrayList<Double> prices = new ArrayList<Double>();
        for (String price : priceString.split(" ")) {
            prices.add(Double.parseDouble(price));
        }

        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            final double quantity = Double.parseDouble(quantities[i]);
            transactedCost[i] += quantity * prices.get(i);
            transactedQuantity[i] += quantity;
            transactedTotal += quantity;
        }
    }

    public MarketDetails closeMarket() {
        MarketDetails results = new MarketDetails();

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
        log.info("{} charged {} kWh", agentId, transactedTotal);
        log.info("{} total transaction cost {}", agentId, results.cost);
        log.info("{} total transaction quantity {}", agentId, results.quantity);

        transactedTotal = 0.0;
        resetCharge();

        return results;
    }

    private void resetCharge() {
        skipCharge = false;

        if (!nextChargeRequired.isEmpty()) {
            double nightCharge = nextChargeRequired.get(0);
            nextChargeRequired.remove(0);

            double morningCharge = 0;
            if (!nextChargeRequired.isEmpty()) {
                morningCharge = nextChargeRequired.get(0);
                nextChargeRequired.remove(0);
            }
            log.debug("{} used next charge profile {}:{}", agentId, nightCharge, morningCharge);

            if (nightCharge > 0 && morningCharge > 0) {
                log.error("invalid vehicle configuration - {} attempted to charge in both windows", agentId);
                skipCharge = true;
            } else if (nightCharge < 1e-4 && morningCharge < 1e-4) { // doesn't check negative
                skipCharge = true;
            } else if (morningCharge < 1e-4) {
                isDayCharge = false;
                chargeRequired = nightCharge;
            } else {
                isDayCharge = true;
                chargeRequired = morningCharge;
            }
        } else {
            isDayCharge = (random.nextDouble() < 0.25);
            chargeRequired = chargeCoefficient * chargeDistribution.inverseCumulativeProbability(random.nextDouble());
            log.debug("{} generated new charge profile {}:{}", agentId, isDayCharge, chargeRequired);
        }
    }
}
