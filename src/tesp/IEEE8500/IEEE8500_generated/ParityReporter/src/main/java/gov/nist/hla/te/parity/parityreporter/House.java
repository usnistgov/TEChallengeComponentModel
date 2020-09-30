package gov.nist.hla.te.parity.parityreporter;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class House {
    private final static Logger log = LogManager.getLogger();
    
    private final String name;
    
    private double total_bill = 0;
    
    private double real_time_price = 0;
    
    private double measured_real_energy = 0;
    
    private double measured_real_energy_prev = 0;
    
    // assume participation in AT MOST ONE trade per hour period
    private ArrayList<Trade> trades = new ArrayList<Trade>();
    
    private int trade_index = 0;
    
    // assume flush 24-hour time intervals
    private Trade[] day_ahead_buffer = new Trade[24];
    
    House(String name) {
        this.name = name;
    }
    
    public void update(long unixTime, long periodInSeconds) {
        log.trace("update at t={}", unixTime, periodInSeconds);
        
        update_trade_index(unixTime);
        
        
        double average_real_power = (measured_real_energy - measured_real_energy_prev) / periodInSeconds; // this period needs to match the others (5min)
        double bill_increase = 0;
        //  compute bill increment / store total
        //      bill = sum_{24hr}(price_t * quantity_t + sum_{1hr}(price_now * (meter_{5min} - quantity_t/12)))
        
        log.info("House {} Total Bill = {} (+{})", name, total_bill, bill_increase);
    }
    
    // assume unixTime is monotonically increasing
    private void update_trade_index(long unixTime) {
        final Instant currentTime = Instant.ofEpochSecond(unixTime);
        log.trace("update_trade_index at t={}", currentTime);
        
        if (trade_index >= trades.size()) {
            log.error("no trade data for {} after {}", name, unixTime);
            return;
        }
        
        String tradeTimeText = trades.get(trade_index).getStartTime();
        Instant tradeTime = Instant.parse(tradeTimeText);
        
        if (currentTime.isBefore(tradeTime)) {
            log.error("no trade data for {} before {}", name, unixTime);
            return; // missed edge case: this exit condition + update called to compute bill
        }
        
        while (tradeTime.isBefore(currentTime)) {
            trade_index++;
            if (trade_index >= trades.size()) {
                log.error("no trade data for {} after {}", name, unixTime);
                return;
            }
            
            tradeTimeText = trades.get(trade_index).getStartTime();
            tradeTime = Instant.parse(tradeTimeText);
        }
        
        log.debug("trade_index {} -> {} at t={}", trade_index, tradeTime, currentTime);
    }
    
    private double get_quantity() {
        return trades.get(trade_index).getQuantity(); // can fail
    }
    
    private double get_price() {
        return trades.get(trade_index).getPrice(); // can fail
    }
    
    public void set_measured_real_energy(double value) {
        log.trace("set_real_energy {}", value);
        this.measured_real_energy_prev = this.measured_real_energy;
        this.measured_real_energy = value;
    }
    
    public void set_real_time_price(double value) {
        log.trace("set_real_time_price {}", value);
        this.real_time_price = value;
    }
    
    // assume measured_real_energy and real_time_price set
    public void calculate_bill() {
        log.trace("calculate_bill");
    }
    
    // assume 24 trades with 60 minute durations
    // TODO: match quantity units to measured_real_energy units
    public void add_trade(Trade trade) {
        log.trace("add_trade {}", trade.toString());
        final int time_zone_adjustment = 0; // what should this be?
        
        Instant time = Instant.parse(trade.getStartTime());
        int hour = time.get(ChronoField.HOUR_OF_DAY);
        hour = (hour + time_zone_adjustment) % 24;
        
        if (trade.getDurationInMinutes() != 60) {
            log.warn("trade is not for a 60 minute period: {}", trade.toString());
        }
        if (day_ahead_buffer[hour] != null) {
            log.error("duplicate trades for hour {}", hour);
            return;
        }
        day_ahead_buffer[hour] = trade;
        log.debug("trade {} -> {}", hour, trade.toString());
    }
    
    public void clear_trades() {
        log.trace("clear_trades");
        
        for (int i = 0; i < day_ahead_buffer.length; i++) {
            if (day_ahead_buffer[i] == null) {
                log.info("no trade for {} at hour {}", name, i);
                trades.add(new Trade());
            } else {
                trades.add(day_ahead_buffer[i]);
            }
            day_ahead_buffer[i] = null; // reset
        }
    }
}
