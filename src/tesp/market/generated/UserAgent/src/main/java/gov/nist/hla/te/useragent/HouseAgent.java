package gov.nist.hla.te.useragent;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class HouseAgent implements Agent {
    private final static Logger log = LogManager.getLogger();

    private String id;

    private ArrayList<Double> loadForecast = new ArrayList<Double>();

    public HouseAgent(String id, ArrayList<Double> data) {
        this.id = id;
        this.loadForecast = data;

        if (this.loadForecast.size() < 24) {
            log.error("house agent {} initialized with less than 24 hours of data", this.id);
            this.loadForecast.clear();
        }
    }

    public void closeMarket() {
        if (loadForecast.size() < 24) {
            log.warn("house agent {} has run out of load forecast data");
            loadForecast.clear();
        } else {
            loadForecast.subList(0, 24).clear();
        }
    }
    
    public String handleQuote(String marketId, String price, String quantity, boolean isBuyQuote) {
        return "";
    }
    
    public String handleTransaction(String marketId, String price, String quantity, boolean isBuyQuote) {
        return "";
    }
}
