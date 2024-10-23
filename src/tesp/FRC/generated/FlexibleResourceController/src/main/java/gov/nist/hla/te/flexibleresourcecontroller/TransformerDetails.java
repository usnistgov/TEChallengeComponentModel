package gov.nist.hla.te.flexibleresourcecontroller;

import java.lang.Math;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class TransformerDetails {
    private final static Logger log = LogManager.getLogger();

    private LinkedList<Double> realPowerHistory = new LinkedList<Double>();

    private String name;
    private double capacity;
    private int historySize;

    private double mPrice;

    // timescale = number of seconds per 1 logical time
    TransformerDetails(String[] data, double timeScale) {
        final int secondsOfHistory = 15 * 60;

        if (data == null || data.length != 2) {
            throw new RuntimeException("invalid transformer configuration");
        }
        if (timeScale <= 0) {
            throw new RuntimeException("invalid transformer timeScale");
        }

        this.name = data[0];
        this.capacity = Double.parseDouble(data[1]) * 1000; // convert kW to W
        this.historySize = (int)Math.floor(secondsOfHistory / timeScale);
        this.mPrice = 1; // default value for no congestion

        if (historySize == 0) {
            log.warn("historySize calculated as 0 ({} / {})", secondsOfHistory, timeScale);
        }
    }
    
    public void setRealPower(double value) {
        realPowerHistory.add(value);
        if (realPowerHistory.size() > historySize) {
            realPowerHistory.remove(); // remove oldest element
        }
        update();
    }

    public String getName() {
        return name;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getMPrice() {
        return mPrice;
    }

    private void update() {
        double mFlow = getRealPowerAverage() / capacity;

        if (mFlow >= 0.75) {
            mPrice = Math.pow(mFlow, 3.074) / 2 + 0.7935;
        } else if (mFlow >= -0.75) {
            mPrice = 1;
        } else if (mFlow >= -1.25) {
            mPrice = 2.5 - 2 * mFlow;
        } else {
            mPrice = 0;
        }
    }

    private double getRealPowerAverage() {
        if (realPowerHistory.isEmpty()) {
            return 0; // prevent divide by 0
        }

        double sumTotal = 0;
        for (double value : realPowerHistory) {
            sumTotal += value;
        }
        return sumTotal / realPowerHistory.size();
    }
}
