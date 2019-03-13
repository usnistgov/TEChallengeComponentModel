package com.AuctionMaven;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class hvac{
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
		this.cleared_price = price;
	}
	
	public boolean bid_accepted(){
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
		double val = parse_fncs_number(str);
		if(val > 0.0){
			this.hvac_kw = val;
		}
	}
	
	public void set_hvac_state(String str){
		if(str.equals("OFF")){
			this.hvac_on = false;
		}else{
			this.hvac_on = true;
		}
	}
	
	public void set_air_temp(String str){
		this.air_temp = parse_fncs_number(str);
	}
	
	public void set_voltage(String str){
		this.mtr_v = parse_fncs_magnitude(str);
	}
	
	public double parse_fncs_number(String arg){
		String str = "";
		for(int i=0; i<arg.length(); i++){
			if(Character.isDigit(arg.charAt(i)) || Character.toString(arg.charAt(i)).equals(".")){
				str += arg.charAt(i);
			}
		}
		return Double.parseDouble(str);
	}
	
	public double parse_fncs_magnitude(String arg){
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
