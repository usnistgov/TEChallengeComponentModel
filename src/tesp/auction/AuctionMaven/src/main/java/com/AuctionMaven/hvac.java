package com.AuctionMaven;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
//Class that controls the responsive thermostat for one house.

//Implements the ramp bidding method, with HVAC power as the
//bid quantity, and thermostat setting changes as the response
//mechanism.
public class hvac{
	//This agent manages thermostat setpoint and bidding for a house

    //Args:
    //    dict: dictionary row for this agent from the JSON configuration file
    //    key: name of this agent, also key for its dictionary row
    //    aucObj (simple_auction): the auction this agent bids into

    //Attributes:
    //    name: name of this agent
    //    control_mode: control mode from dict (not implemented)
    //    houseName: name of the corresponding house in GridLAB-D, from dict
    //    meterName: name of the corresponding triplex_meter in GridLAB-D, from dict
    //    period: market clearing period, in seconds, from dict
    //    wakeup_start: hour of the day (0..24) for scheduled weekday wakeup period thermostat setpoint, from dict
    //    daylight_start: hour of the day (0..24) for scheduled weekday daytime period thermostat setpoint, from dict
    //    evening_start: hour of the day (0..24) for scheduled weekday evening (return home) period thermostat setpoint, from dict
    //    night_start: hour of the day (0..24) for scheduled weekday nighttime period thermostat setpoint, from dict
    //    wakeup_set: preferred thermostat setpoint for the weekday wakeup period, in deg F, from dict
    //    daylight_set: preferred thermostat setpoint for the weekday daytime period, in deg F, from dict
    //    evening_set: preferred thermostat setpoint for the weekday evening (return home) period, in deg F, from dict
    //    night_set: preferred thermostat setpoint for the weekday nighttime period, in deg F, from dict
    //    weekend_day_start: hour of the day (0..24) for scheduled weekend daytime period thermostat setpoint, from dict
    //    weekend_day_set: preferred thermostat setpoint for the weekend daytime period, in deg F, from dict
    //    weekend_night_start: hour of the day (0..24) for scheduled weekend nighttime period thermostat setpoint, from dict
    //    weekend_night_set: preferred thermostat setpoint for the weekend nighttime period, in deg F, from dict
    //    deadband: thermostat deadband in deg F, invariant, from dict
    //    offset_limit: maximum allowed change from the time-scheduled setpoint, in deg F, from dict
    //    ramp: bidding ramp denominator in multiples of the price standard deviation, from dict
    //    price_cap: the highest allowed bid price in $/kwh, from dict
    //    bid_delay: from dict, not implemented
    //    use_predictive_bidding: from dict, not implemented
    //    std_dev: standard deviation of expected price, determines the bidding ramp slope, initialized from aucObj
    //    mean: mean of the expected price, determines the bidding ramp origin, initialized from aucObj
    //    Trange: the allowed range of setpoint variation, bracketing the preferred time-scheduled setpoint
    //    air_temp: current air temperature of the house in deg F
    //    hvac_kw: most recent non-zero HVAC power in kW, this will be the bid quantity
    //    mtr_v: current line-neutral voltage at the triplex meter
    //    hvac_on: True if the house HVAC is currently running
    //    basepoint: the preferred time-scheduled thermostat setpoint in deg F
    //    setpoint: the thermostat setpoint, including price response, in deg F
    //    bid_price: the current bid price in $/kwh
    //    cleared_price: the cleared market price in $/kwh
        
	public String name;
	private String control_mode;
	private String houseName;
	private String meterName;
	private int period;
	private double wakeup_start;
	private double daylight_start;
	private double evening_start;
	private double night_start;
	private double wakeup_set;
	private double daylight_set;
	private double evening_set;
	private double night_set;
	private double weekend_day_start;
	private double weekend_day_set;
	private double weekend_night_start;
	private double weekend_night_set;
	public double deadband;
	private double offset_limit;
	private double ramp;
	private double price_cap;
	private double bid_delay;
	private int use_predictive_bidding;
	
	private double std_dev;
	private double mean;
	private double Trange;
	private double air_temp;
	private double hvac_kw;
	private double mtr_v;
	private boolean hvac_on;
	public double basepoint;
	public double setpoint;
	private double cleared_price;
	private double bid_price;
	
	hvac(Map<String, Object> dict, String key, SimpleAuction aucObj){
		this.name = key;
		this.control_mode = (String) dict.get("control_mode");
		this.houseName = (String) dict.get("houseName");
		this.meterName = (String) dict.get("meterName");
		this.period = (int) dict.get("period");
		this.wakeup_start = (double) dict.get("wakeup_start");
		this.daylight_start = (double) dict.get("daylight_start");
		this.evening_start = (double) dict.get("evening_start");
		this.night_start = (double) dict.get("night_start");
		this.wakeup_set = (double) dict.get("wakeup_set");
		this.daylight_set = (double) dict.get("daylight_set");
		this.evening_set = (double) dict.get("evening_set");
		this.night_set = (double) dict.get("night_set");
		this.weekend_day_start = (double) dict.get("weekend_day_start");
		this.weekend_day_set = (double) dict.get("weekend_day_set");
		this.weekend_night_start = (double) dict.get("weekend_night_start");
		this.weekend_night_set = (double) dict.get("weekend_night_set");
		this.deadband = (double) dict.get("deadband");
		this.offset_limit = (double) dict.get("offset_limit");
		this.ramp = (double) dict.get("ramp");
		this.price_cap = (double) dict.get("price_cap");
		this.bid_delay = (double) dict.get("bid_delay");
		this.use_predictive_bidding = (int) dict.get("use_predictive_bidding");

		this.std_dev = aucObj.std_dev;
		this.mean = aucObj.clearing_price;

		this.Trange = Math.abs(2.0 * this.offset_limit);

		this.air_temp = 78.0;
		this.hvac_kw = 3.0;
		this.mtr_v = 120.0;
		this.hvac_on = false;

		this.basepoint = 0.0;
		this.setpoint = 0.0;
		this.cleared_price = 0.0;
		this.bid_price = 0.0;
	}
	
	public void inform_bid(double price){
		//Set the cleared_price attribute

        //Args:
        //    price (float): cleared price in $/kwh
            
		this.cleared_price = price;
	}
	
	public boolean bid_accepted(){
		//Update the thermostat setting if the last bid was accepted

        //The last bid is always "accepted". If it wasn't high enough,
        //then the thermostat could be turned up.p

        //Returns:
        //    Boolean: True if the thermostat setting changes, False if not.
            
		if(this.std_dev > 0.0){
			double offset = (this.cleared_price - this.mean) * this.Trange / this.ramp / this.std_dev;
			if(offset < -this.offset_limit){
				offset = -this.offset_limit;
			}else if(offset > this.offset_limit){
				offset = this.offset_limit;
			}
			this.setpoint = this.basepoint + offset;
			return true;
		}
		return false;
	}
	
	public List formulate_bid(){
		//Bid to run the air conditioner through the next period
        
        //Returns:
        //    [double, double, Boolean]: bid price in $/kwh, bid quantity in kW and current HVAC on state
            
		double p = this.mean + (this.air_temp - this.basepoint) * this.ramp * this.std_dev / this.Trange;
		if(p >= this.price_cap){
			this.bid_price = this.price_cap;
		}else if(p <= 0.0){
			this.bid_price = 0.0;
		}else{
			this.bid_price = p;
		}
		return Arrays.asList(this.bid_price, this.hvac_kw, this.hvac_on);
	}
	
	public boolean change_basepoint(int hod, int dow){
		//Updates the time-scheduled thermostat setting

        //Args:
        //    hod: the hour of the day, from 0 to 24
        //    dow: the day of the week, zero being Monday

        //Returns:
        //    Boolean: True if the setting changed, Falso if not
            
		double val;
		if(dow > 4){//a weekend
			val = this.weekend_night_set;
			if(hod >= this.weekend_day_start && hod < this.weekend_night_start){
				val = this.weekend_day_set;
			}
		}else{//a weekday
			val = this.night_set;
			if(hod >= this.wakeup_start && hod < this.daylight_start){
				val = this.wakeup_set;
			}else if(hod >= this.daylight_start && hod < this.evening_start){
				val = this.daylight_set;
			}else if(hod >= this.evening_start && hod < this.night_start){
				val = this.evening_set;
			}
		}
		if(Math.abs(this.basepoint - val) > 0.1){
			this.basepoint = val;
			return true;
		}
		return false;
	}
	
	public void set_hvac_load(String str){
		//Sets the hvac_load attribute, if greater than zero

        //Args:
        //    str: FNCS message with load in kW
            
		double val = parse_fncs_number(str);
		if(val > 0.0){
			this.hvac_kw = val;
		}
	}
	
	public void set_hvac_state(String str){
		//Sets the hvac_on attribute

        //Args:
        //    str: FNCS message with state, ON or OFF
            
		if(str.equals("OFF")){
			this.hvac_on = false;
		}else{
			this.hvac_on = true;
		}
	}
	
	public void set_air_temp(String str){
		//Sets the air_temp attribute

        //Args:
        //    str: FNCS message with temperature in degrees Fahrenheit
            
		this.air_temp = parse_fncs_number(str);
	}
	
	public void set_voltage(String str){
		//Sets the mtr_v attribute

        //Args:
        //    str: FNCS message with meter line-neutral voltage
            
		this.mtr_v = parse_fncs_magnitude(str);
	}
	
	public double parse_fncs_number(String arg){
		//Parse floating-point number from a FNCS message; must not have leading sign or exponential notation

	    //Args:
	    //    arg: the FNCS string value

	    //Returns:
	    //    double: the parsed number
	        
		String str = "";
		for(int i=0; i<arg.length(); i++){
			if(Character.isDigit(arg.charAt(i)) || Character.toString(arg.charAt(i)).equals(".")){
				str += arg.charAt(i);
			}
		}
		return Double.parseDouble(str);
	}
	
	public double parse_fncs_magnitude(String arg){
		//Parse the magnitude of a possibly complex number from FNCS

	    //Args:
	    //    arg: the FNCS string value

	    //Returns:
	    //    double: the parsed number, or 0 if parsing fails
	        
		String tok = arg.replaceFirst("[+-]", "");
		tok = tok.replaceAll("[^\\d.+-]", "");
		ArrayList vals = new ArrayList(Arrays.asList(tok.split("[+-]+")));
		if(vals.size() < 2){// only a real part provided
			vals.add("0");
		}
		vals.set(0, Double.parseDouble(vals.get(0).toString()));
		if(Character.toString(arg.charAt(0)).equals("-")){
			vals.set(0, ((double) vals.get(0))*(-1.0));
		}
		return (double) vals.get(0);
	}

}
