package gov.nist.hla.te.useragent;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.math3.distribution.LogNormalDistribution;

class VehicleAgent implements Agent {
    private final static Logger log = LogManager.getLogger();

    private String agentId;
    private String transformerId;

    private boolean isDayCharge;

    private double chargeCoefficient;
    private double chargeRequired;
    private double transactedTotal;

    private LogNormalDistribution chargeDistribution;
    private Random random = new Random();

    private static final int INTERVAL_LENGTH = 24;
    private Double[] transactedAmount = new Double[INTERVAL_LENGTH];

    public VehicleAgent(String id, double coefficient, double mu, double sigma) {
        this.agentId = id;
        this.transformerId = id.split(":")[0];
        this.chargeCoefficient = coefficient;
        this.chargeDistribution = new LogNormalDistribution(mu, sigma);

        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            transactedAmount[i] = 0.0;
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

    public String handleQuote(String id, String priceString, String quantityString, boolean isBuyQuote) {
        if (Integer.parseInt(id) < 2 || isBuyQuote) { // ignore first round of quotes
            String zeroQuantity = "";
            for (int i = 0; i < INTERVAL_LENGTH; i++) {
                if (i > 0) {
                    zeroQuantity += " ";
                }
                zeroQuantity += "0.0";
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

            double chargeRateLimit = MAX_CHARGE_RATE - transactedAmount[index];
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

        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            transactedAmount[i] += Double.parseDouble(quantities[i]);
            transactedTotal += Double.parseDouble(quantities[i]);
        }
    }

    public void closeMarket() {
        for (int i = 0; i < INTERVAL_LENGTH; i++) {
            log.info("{} bought {} kWh in slot {} (out of {} total)", agentId, transactedAmount[i], i, chargeRequired);
            transactedAmount[i] = 0.0;
        }
        transactedTotal = 0.0;
        resetCharge();
    }

    private void resetCharge() {
        isDayCharge = (random.nextDouble() < 0.25);
        chargeRequired = chargeCoefficient * chargeDistribution.inverseCumulativeProbability(random.nextDouble());
    }
}
