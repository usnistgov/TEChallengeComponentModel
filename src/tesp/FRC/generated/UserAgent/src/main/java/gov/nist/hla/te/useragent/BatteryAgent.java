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
    private double acceptablePriceDifference;

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
    private int indexOfChargeWindow;
    private double transactedDischargePrice;
    private double transactedQuantityMismatch;

    private String zeroQuantity;
    private boolean isActive;

    public BatteryAgent(String id, double capacity, double acceptablePriceDifference) {
        this.agentId = id;
        this.transformerId = id.split(":")[0];
        this.capacity = capacity;
        this.acceptablePriceDifference = acceptablePriceDifference;
        this.isActive = false;

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
        buyResponseQuantity = zeroQuantity;

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
        }
    }

    public void handleSellQuote(String id, String priceString, String quantityString, boolean hasMarketActivity) {
        if (Integer.parseInt(id) >= 2 && !hasMarketActivity) {
            isActive = true;
        }
        sellResponseQuantity = zeroQuantity;

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
        }
    }

    private void handleQuotes() { // TODO - include losses
        if (!receivedBuyQuote || !receivedSellQuote) {
            return;
        }
        receivedBuyQuote = false;
        receivedSellQuote = false;

        final double minimumUnit = 0.0001;

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

        Integer[] buyIndexSorted = new Integer[INTERVAL_LENGTH];
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            buyIndexSorted[i] = i;
        }

        Arrays.sort(buyIndexSorted, new Comparator<Integer>() {
            @Override public int compare(Integer i1, Integer i2) {
                return Double.compare(buyQuotePrices[i1], buyQuotePrices[i2]);
            }
        });

        if (transactedQuantityMismatch >= minimumUnit) { // need to discharge
            double maxPrice = 0.0;
            double responseQuantity = 0.0;
            int selectedIntervalIndex = 0;

            for (int i = 0; i < indexOfChargeWindow; i++) {
                if (buyQuotePrices[i] >= maxPrice) {
                    if (chargeRate[i] > 0) {
                        continue; // skip charge intervals
                    }

                    double availableQuantity = Math.min(buyQuoteQuantities[i], maxChargeRate - Math.abs(chargeRate[i]));
                    for (int j = i; j < indexOfChargeWindow; j++) {
                        availableQuantity = Math.min(availableQuantity, stateOfCharge[j]);
                    }

                    if (availableQuantity >= minimumUnit && (buyQuotePrices[i] > maxPrice || availableQuantity > responseQuantity)) {
                        maxPrice = buyQuotePrices[i];
                        responseQuantity = availableQuantity;
                        selectedIntervalIndex = i;
                    }
                }
            }
            dischargeAmount[selectedIntervalIndex] = responseQuantity;
        } else {
            Double[] estimatedROC = Arrays.copyOf(chargeRate, INTERVAL_LENGTH);
            Double[] estimatedSOC = Arrays.copyOf(stateOfCharge, INTERVAL_LENGTH);

            if (transactedQuantityMismatch <= -minimumUnit) {
                double mismatch = transactedQuantityMismatch;

                for (int i = 0; i < INTERVAL_LENGTH; i++) {
                    int c = sellIndexSorted[i];

                    if (transactedDischargePrice < sellQuotePrices[c] + acceptablePriceDifference) {
                        break;
                    }
                    if (c <= indexOfDischarge || estimatedROC[c] < 0 || maxChargeRate - estimatedROC[c] < minimumUnit) {
                        continue;
                    }

                    double availableQuantity = maxChargeRate - estimatedROC[c];
                    for (int j = c; j < INTERVAL_LENGTH; j++) {
                        availableQuantity = Math.min(availableQuantity, capacity - (estimatedSOC[j] + estimatedROC[j]));
                    }

                    if (availableQuantity >= minimumUnit) {
                        double responseQuantity = Math.min(availableQuantity, Math.abs(mismatch));
                        chargeAmount[c] += responseQuantity;
                        estimatedROC[c] += responseQuantity;
                        for (int j = c+1; j < INTERVAL_LENGTH; j++) {
                            estimatedSOC[j] += responseQuantity;
                        }

                        mismatch += responseQuantity;
                        if (mismatch > -minimumUnit) {
                            break;
                        }
                    }
                }
            } else {
                transactedQuantityMismatch = 0.0;

                boolean foundDischargeInterval = false;

                for (int i = INTERVAL_LENGTH; i > 0; i--) {
                    if (foundDischargeInterval) {
                        break;
                    }
                    int d = buyIndexSorted[i-1];

                    if (estimatedROC[d] > 0 || maxChargeRate - Math.abs(estimatedROC[d]) < minimumUnit
                            || estimatedSOC[d] + estimatedROC[d] < minimumUnit) {
                        continue;
                    }
                    double maxQuantity = Math.min(buyQuoteQuantities[d], maxChargeRate - Math.abs(estimatedROC[d]));

                    for (int j = 0; j < INTERVAL_LENGTH; j++) {
                        int c = sellIndexSorted[j];

                        if (buyQuotePrices[d] < sellQuotePrices[c] + acceptablePriceDifference) {
                            break;
                        }
                        if (c <= d || estimatedROC[c] < 0 || maxChargeRate - estimatedROC[c] < minimumUnit) {
                            continue;
                        }

                        double availableQuantity = maxChargeRate - estimatedROC[c];
                        for (int k = d; k <= c; k++) {
                            availableQuantity = Math.min(availableQuantity, estimatedSOC[k]);
                        }

                        if (availableQuantity >= minimumUnit) {
                            foundDischargeInterval = true;

                            double responseQuantity = Math.min(availableQuantity, maxQuantity);
                            chargeAmount[c] += responseQuantity;
                            dischargeAmount[d] += responseQuantity;

                            estimatedROC[c] += responseQuantity;
                            estimatedROC[d] -= responseQuantity;
                            for (int k = d+1; k <= c; k++) {
                                estimatedSOC[k] -= responseQuantity;
                            }

                            maxQuantity -= responseQuantity;
                            if (maxQuantity < minimumUnit) {
                                break;
                            }
                        }
                    }
                }
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
        String[] prices = priceString.split(" ");

        boolean foundFirstQuantity = false;

        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            final double quantity = Double.parseDouble(quantities[i]);
            final int sign = (isBuyTransaction ? -1 : 1);

            transactedCost[i] += sign * quantity * Double.parseDouble(prices[i]);
            transactedQuantity[i] += sign * quantity;
            transactedQuantityMismatch += sign * quantity;

            chargeRate[i] += sign * quantity;
            for (int j = i+1; j < INTERVAL_LENGTH; j++) {
                stateOfCharge[j] += sign * quantity;
            }

            if (!foundFirstQuantity && quantity > 0) {
                if (isBuyTransaction) {
                    indexOfDischarge = i;
                    transactedDischargePrice = Double.parseDouble(prices[i]);
                } else {
                    indexOfChargeWindow = i;
                }
            }
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
        log.info("{} total transaction cost {}", agentId, results.cost);
        log.info("{} total transaction quantity {}", agentId, results.quantity);

        // TODO - this should come from the FRC
        resetCharge(stateOfCharge[INTERVAL_LENGTH-1]); // probably need to add charge too
        this.isActive = false;

        return results;
    }

    private void resetCharge(double initialCharge) {
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            chargeRate[i] = 0.0;
            stateOfCharge[i] = initialCharge;
        }
    }
}
