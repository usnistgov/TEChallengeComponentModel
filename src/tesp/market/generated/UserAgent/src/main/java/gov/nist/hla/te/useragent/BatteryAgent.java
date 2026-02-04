package gov.nist.hla.te.useragent;

import java.util.*;
import java.lang.Math;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class BatteryAgent implements Agent {
    private final static Logger log = LogManager.getLogger();

    private String agentId;
    private String transformerId;

    private double capacity;

    private String buyResponseQuantity;
    private String sellResponseQuantity;

    private static final int INTERVAL_LENGTH = 24;
    private Double[] transactedCost = new Double[INTERVAL_LENGTH];
    private Double[] transactedQuantity = new Double[INTERVAL_LENGTH];

    private Double[] chargeRate = new Double[INTERVAL_LENGTH];
    private Double[] stateOfCharge = new Double[INTERVAL_LENGTH];

    private boolean receivedBuyQuote = false;
    private Double[] buyQuotePrices = new Double[INTERVAL_LENGTH];
    private Double[] buyQuoteQuantities = new Double[INTERVAL_LENGTH];

    private boolean receivedSellQuote = false;
    private Double[] sellQuotePrices = new Double[INTERVAL_LENGTH];
    private Double[] sellQuoteQuantities = new Double[INTERVAL_LENGTH];

    private double maxChargeRate = 5.0;

    private int indexOfDischarge;
    private int indexOfFirstCharge;
    private double transactedQuantityMismatch;

    private String zeroQuantity;
    private boolean isActive;

    public BatteryAgent(String id, double capacity) {
        this.agentId = id;
        this.transformerId = id.split(":")[0];
        this.capacity = capacity;

        zeroQuantity = "";
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            if (i > 0) {
                zeroQuantity += " ";
            }
            zeroQuantity += "0.0000";
        }

        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            transactedCost[i] = 0.0;
            transactedQuantity[i] = 0.0;
        }
        resetCharge(capacity);
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
        if (Integer.parseInt(id) >= 2 && !hasMarketActivity) {
            isActive = true;
        }
        if (isActive) {
            int index = 0;
            for (String price : priceString.split(" ")) {
                buyQuotePrices[index++] = Double.parseDouble(price);
            }

            index = 0;
            for (String quantity : quantityString.split(" ")) {
                buyQuoteQuantities[index++] = Double.parseDouble(quantity);
            }
            receivedBuyQuote = true;
            handleQuotes();
        } else {
            buyResponseQuantity = zeroQuantity;
        }
    }

    public void handleSellQuote(String id, String priceString, String quantityString, boolean hasMarketActivity) {
        if (Integer.parseInt(id) >= 2 && !hasMarketActivity) {
            isActive = true;
        }
        if (isActive) {
            int index = 0;
            for (String price : priceString.split(" ")) {
                sellQuotePrices[index++] = Double.parseDouble(price);
            }

            index = 0;
            for (String quantity : quantityString.split(" ")) {
                sellQuoteQuantities[index++] = Double.parseDouble(quantity);
            }
            receivedSellQuote = true;
            handleQuotes();
        } else {
            sellResponseQuantity = zeroQuantity;
        }
    }

    // issues:
    //  price ties are not handled for sell quote responses
    //  multiple price peaks for buy quotes are not handled
    private void handleQuotes() { // TODO - include losses
        if (!receivedBuyQuote || !receivedSellQuote) {
            return;
        }

        final double tolerance = 1e-4;
        final double priceDifference = 0.04;

        double[] dischargeAmount = new double[INTERVAL_LENGTH];
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            dischargeAmount[i] = 0.0;
        }

        double[] chargeAmount = new double[INTERVAL_LENGTH];
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            chargeAmount[i] = 0.0;
        }

        Integer[] sellIndexSorted = new Integer[INTERVAL_LENGTH];
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            sellIndexSorted[i] = i;
        }

        Arrays.sort(sellIndexSorted, new Comparator<Integer>() {
            @Override public int compare(Integer i1, Integer i2) {
                return Double.compare(sellQuotePrices[i1], sellQuotePrices[i2]);
            }
        });

        if (transactedQuantityMismatch > tolerance) { // need to discharge
            double maxPrice = 0.0;
            double desiredQuantity = 0.0;
            int selectedIntervalIndex = 0;

            for (int i = 0; i < indexOfFirstCharge; i++) {
                if (buyQuotePrices[i] >= maxPrice) {
                    if (chargeRate[i] > 0) {
                        continue; // skip charge intervals
                    }

                    double availableQuantity = Math.min(buyQuoteQuantities[i], maxChargeRate - Math.abs(chargeRate[i]));
                    for (int j = i; j < indexOfFirstCharge; j++) {
                        availableQuantity = Math.min(availableQuantity, stateOfCharge[j]);
                    }

                    if (availableQuantity - tolerance > 0 && (buyQuotePrices[i] > maxPrice || availableQuantity > desiredQuantity)) {
                        maxPrice = buyQuotePrices[i];
                        desiredQuantity = availableQuantity;
                        selectedIntervalIndex = i;
                    }
                }
            }
            dischargeAmount[selectedIntervalIndex] = desiredQuantity;
        } else {
            Double[] predictedSOC = new Double[INTERVAL_LENGTH];
            for (int i = 0; i < INTERVAL_LENGTH; i++) {
                predictedSOC[i] = stateOfCharge[i];
            }

            int index = 0; // can this be undefined ?
            double desiredAmount = 0;

            if (transactedQuantityMismatch < -tolerance) { // need to charge
                index = indexOfDischarge;
                desiredAmount = Math.abs(transactedQuantityMismatch);
            } else {
                transactedQuantityMismatch = 0.0;

                double maxPrice = 0.0;
                for (int i = 0; i < INTERVAL_LENGTH; i++) {
                    if (buyQuotePrices[i] >= maxPrice) {
                        if (chargeRate[i] > 0) {
                            continue; // skip charge intervals
                        }

                        double availableQuantity = Math.min(buyQuoteQuantities[i], maxChargeRate - Math.abs(chargeRate[i]));
                        for (int j = i; j < INTERVAL_LENGTH; j++) {
                            availableQuantity = Math.min(availableQuantity, stateOfCharge[j]);
                        }

                        if (availableQuantity - tolerance > 0 && (buyQuotePrices[i] > maxPrice || availableQuantity > desiredAmount)) {
                            maxPrice = buyQuotePrices[i];
                            desiredAmount = availableQuantity;
                            index = i;
                        }
                    }
                }
                dischargeAmount[index] = desiredAmount;
            }
            
            int i = 0;
            while (desiredAmount >= tolerance) {
                if (i >= INTERVAL_LENGTH) {
                    break;
                }

                // TODO - use previous round cost
                if (buyQuotePrices[index] < sellQuotePrices[sellIndexSorted[i]] + priceDifference) {
                    break;
                }

                if (index >= sellIndexSorted[i]) {
                    continue;
                }

                if (chargeRate[sellIndexSorted[i]] < 0) {
                    continue;
                }

                double availableQuantity = Math.min(desiredAmount, maxChargeRate - chargeRate[sellIndexSorted[i]]);
                for (int j = sellIndexSorted[i]; j < INTERVAL_LENGTH; j++) {
                    availableQuantity = Math.min(availableQuantity, capacity - predictedSOC[j]);
                }

                if (availableQuantity - tolerance > 0) {
                    chargeAmount[sellIndexSorted[i]] = availableQuantity;
                    desiredAmount -= availableQuantity;

                    for (int j = sellIndexSorted[i]; j < INTERVAL_LENGTH; j++) {
                        predictedSOC[j] += availableQuantity;
                    }
                }

                i += 1;
            }

            if (desiredAmount > 0 && dischargeAmount[index] != 0.0) {
                dischargeAmount[index] -= desiredAmount;
            }
        }

        buyResponseQuantity = ""; // discharge
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            if (i > 0) {
                buyResponseQuantity += " ";
            }
            buyResponseQuantity += String.format("%.4f", dischargeAmount[i]);
        }
        
        sellResponseQuantity = ""; // charge
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            if (i > 0) {
                sellResponseQuantity += " ";
            }
            sellResponseQuantity += String.format("%.4f", chargeAmount[i]);
        }
    }

    public void handleTransaction(String priceString, String quantityString, boolean isBuyTransaction) {
        String[] quantities = quantityString.split(" ");

        ArrayList<Double> prices = new ArrayList<Double>();
        for (String price : priceString.split(" ")) {
            prices.add(Double.parseDouble(price));
        }

        boolean foundFirstQuantity = false;

        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            final double quantity = Double.parseDouble(quantities[i]);
            final int sign = (isBuyTransaction ? -1 : 1);

            transactedCost[i] += sign * quantity * prices.get(i);
            transactedQuantity[i] += sign * quantity;
            transactedQuantityMismatch += sign * quantity;

            chargeRate[i] += sign * quantity;
            for (int j = i+1; j < INTERVAL_LENGTH; j++) {
                stateOfCharge[j] += sign * quantity;
            }

            if (!foundFirstQuantity && quantity > 1e-4) {
                if (isBuyTransaction) {
                    indexOfDischarge = i;
                } else {
                    indexOfFirstCharge = i;
                }
            }
        }
    }

    public void closeMarket() {
        String finalPrice = "";
        String finalQuantity = "";
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            if (i > 0) {
                finalPrice += " ";
                finalQuantity += " ";
            }
            finalPrice += String.format("%.4f", transactedCost[i]);
            finalQuantity += String.format("%.4f", transactedQuantity[i]);

            transactedCost[i] = 0.0;
            transactedQuantity[i] = 0.0;
        }
        log.info("{} total transaction cost {}", agentId, finalPrice);
        log.info("{} total transaction quantity {}", agentId, finalQuantity);

        // TODO - this should come from the FRC
        resetCharge(stateOfCharge[INTERVAL_LENGTH-1]); // probably need to add charge too
    }

    private void resetCharge(double initialCharge) {
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            chargeRate[i] = 0.0;
            stateOfCharge[i] = initialCharge;
        }
        transactedQuantityMismatch = 0.0;
        indexOfFirstCharge = 0;
        indexOfDischarge = 0;
        isActive = false;
    }
}
