package gov.nist.hla.te.useragent;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class HouseAgent implements Agent {
    private final static Logger log = LogManager.getLogger();

    private String id;

    private String currentInterval = null;

    private ArrayList<Double> loadForecast = new ArrayList<Double>();

    public HouseAgent(String id, ArrayList<Double> data) {
        this.id = id;
        this.loadForecast = data;

        if (this.loadForecast.size() < 24) {
            log.error("house agent {} initialized with less than 24 hours of data", this.id);
            this.loadForecast.clear();
        }
    }

    public void setMarketInterval(String interval) {
        if (currentInterval != null && currentInterval != interval) {
            if (loadForecast.size() < 24) {
                log.warn("house agent {} missing forecast data for the market {}", id, interval);
                loadForecast.clear();
            } else {
                loadForecast.subList(0, 24).clear();
            }
        }
        currentInterval = interval;
    }
    
    public void handleQuote(String price, String quantity, char side) {
    }
    
    public void handleTransaction(String price, String quantity, char side){
    }

    public String getId() {
        return "";
    }

    public String getPrice(char side) {
        return "";
    }

    public String getQuantity(char side) {
        return "";
    }
}
