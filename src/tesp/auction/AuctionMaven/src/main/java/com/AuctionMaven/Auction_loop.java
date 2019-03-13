package com.AuctionMaven;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.opencsv.CSVWriter;


public class Auction_loop {
	public static void main(String[] args)  throws Exception
	{
		String configfile = "/TE_Challenge_agent_dict.json";
		//String metrics_root = "TE_Challenge";
		String subscriptions = "/auction_subscriptions.txt";
		boolean bWantMarket = true; // set to false if no market is desired
		int time_stop = 48 * 3600;
		
		String StartTime = "2013-07-01 00:00:00";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dt_now = LocalDateTime.parse(StartTime, dtf);
		
		
		
		// ====== load the JSON dictionary; create the corresponding objects =========
		Map<String, Object> dict= load_json_case(configfile);
		String market_key = (String)((Map) dict.get("markets")).keySet().toArray()[0];// only using the first market
		Map<String, Object> market_row = (Map<String, Object>) ((Map<String, Object>) dict.get("markets")).get(market_key);
		String unit = market_row.get("unit").toString();
		
		/////////////////////////////////
		//TODO - create metas and metrics for the json output files (auction_isolated.py, lines 33-36)
		/////////////////////////////////
		
		SimpleAuction aucObj = new SimpleAuction(market_row, market_key);
		
		int dt = (int) dict.get("dt");
		int period = aucObj.period;
		
		Map<String, Object> topicMap = new HashMap<String, Object>();
		topicMap.put("LMP", Arrays.asList(aucObj, 0));
		topicMap.put("refload", Arrays.asList(aucObj, 1));
		
		Map<String, hvac> hvacObjs = new HashMap<String, hvac>();
		Object[] hvac_keys = ((Map<String, Object>)dict.get("controllers")).keySet().toArray();
		for(int i=0; i<hvac_keys.length; i++){
			String key = (String) hvac_keys[i];
			Map<String, Object> row = (Map<String, Object>)((Map<String, Object>) dict.get("controllers")).get(key);
			hvacObjs.put(key, new hvac(row, key, aucObj));
			hvac ctl = hvacObjs.get(key);
			topicMap.put(key + "#Tair", Arrays.asList(ctl, 2));
			topicMap.put(key + "#V1", Arrays.asList(ctl, 3));
			topicMap.put(key + "#Load", Arrays.asList(ctl, 4));
			topicMap.put(key + "#On", Arrays.asList(ctl, 5));
		}
		// ==================== Time step looping under FNCS ===========================
		aucObj.initAuction();
		double LMP = aucObj.mean;
		double refload = 0.0;
		boolean bSetDefaults = true;
		
		int tnext_bid = period - 2 * dt;  	//3 * dt  # controllers calculate their final bids
		int tnext_agg = period - 2 * dt;  	// auction calculates and publishes aggregate bid
		int tnext_clear = period;         	// clear the market with LMP
		int tnext_adjust = period;        	// + dt   # controllers adjust setpoints based on their bid and clearing
		
		int time_granted = 0;
		int time_last = 0;
		int time_delta = 0;
		int hour_of_day = 0;
		int day_of_week = 0;
		
		////////////////////////////////////////////////////////////////////////////
	    ////////////////////////////For Testing Purposes////////////////////////////
		File csvfile = new File(Auction_loop.class.getResource("/").toURI());
		CSVWriter op = new CSVWriter(new FileWriter(csvfile + "/" + "auction.csv", true));
		op.writeNext(new String[] {"t[s]", "bWantMarket", "bSetDefaults", "Unresponsive_mw",
				"Responsive_max_mw", "Responsive_c2", "Responsive_c1", "Responsive_deg",
				"Clear_Price", "Clear_Type"});
		op.flush();
		
		double AggUn = 0.0; //For Testing Purposes
		double AggRespMax = 0.0; //For Testing Purposes
		double AggC2 = 0.0; //For Testing Purposes
		double AggC1 = 0.0; //For Testing Purposes
		double AggDeg = 0.0; //For Testing Purposes
		double ClearingPrice = 0.0; //For Testing Purposes
		String ClearingType = ""; //For Testing Purposes
		
		File txtfile = new File(Auction_loop.class.getResource("/").toURI());
		BufferedWriter bw = new BufferedWriter(new FileWriter(txtfile + "/" + "auction.txt", true));
		
		////////////////////////////End Test////////////////////////////
		////////////////////////////////////////////////////////////////////////////
		
		// read-in values auction subscribes to from the PyPower and GridLabD federates
	    // that is done to bypass the fncs communication in order to isolate the 
	    // auction federate from the expriment.
		List<List<String>> subs = read_file(subscriptions, "	");
		List<List<String>> subs_part;
		
		while (time_granted < time_stop){
			time_granted += dt;
			time_delta = time_granted - time_last;
			time_last = time_granted;
			//hour_of_day = 24.0 * (((time_granted) / 86400.0) % 1.0);
			dt_now = dt_now.plusSeconds(time_delta);
			day_of_week = dt_now.getDayOfWeek().getValue()-1;
			hour_of_day = dt_now.getHour();
	        //System.out.println("hour_of_day: " + hour_of_day);
	        //System.out.println("day_of_week: " + day_of_week);
			
			//////To reduce simulation time the sub list is split into 
		    //////smaller sub lists
			if(time_granted < 12500){
				subs_part = subs.subList(0, 174627);
			}else if(time_granted >= 12500 && time_granted < 25000){
				subs_part = subs.subList(174627,323944);
			}else if(time_granted >= 25000 && time_granted < 37500){
				subs_part = subs.subList(323944,440436);
			}else if(time_granted >= 37500 && time_granted < 50000){
				subs_part = subs.subList(440436,606823);
			}else if(time_granted >= 50000 && time_granted < 62500){
				subs_part = subs.subList(606823,802716);
			}else if(time_granted >= 62500 && time_granted < 75000){
				subs_part = subs.subList(802716,991166);
			}else if(time_granted >= 75000 && time_granted < 87500){
				subs_part = subs.subList(991166,1184490);
			}else if(time_granted >= 87500 && time_granted < 100000){
				subs_part = subs.subList(1184490,1374010);
			}else if(time_granted >= 100000 && time_granted < 112500){
				subs_part = subs.subList(1374010,1508983);
			}else if(time_granted >= 112500 && time_granted < 125000){
				subs_part = subs.subList(1508983,1627406);
			}else if(time_granted >= 125000 && time_granted < 137500){
				subs_part = subs.subList(1627406,1799737);
			}else if(time_granted >= 137500 && time_granted < 150000){
				subs_part = subs.subList(1799737,1997671);
			}else if(time_granted >= 150000 && time_granted < 162500){
				subs_part = subs.subList(1997671,2186598);
			}else{
				subs_part = subs.subList(2186598,subs.size()-1);
			}
			final int simTime = time_granted;
			List<List<String>> events = new ArrayList<List<String>>();
			subs_part.forEach(sub->{
				if(Integer.parseInt(sub.get(0)) == simTime){
					events.add(Arrays.asList(sub.get(0), sub.get(1), sub.get(2)));
				}
			});
			for(int i=0; i<events.size(); i++){
				String topic = events.get(i).get(1);
				String value = events.get(i).get(2);
				List row = (List) topicMap.get(topic);
				if((int)row.get(1) == 0){
					LMP = parse_fncs_magnitude(value);
					aucObj.set_lmp(LMP);
				}else if((int)row.get(1) == 1){
					refload = parse_kw(value);
					aucObj.set_refload(refload);
				}else if((int)row.get(1) == 2){
					((hvac)row.get(0)).set_air_temp(value);;
				}else if((int)row.get(1) == 3){
					((hvac)row.get(0)).set_voltage(value);
				}else if((int)row.get(1) == 4){
					((hvac)row.get(0)).set_hvac_load(value);
				}else if((int)row.get(1) == 5){
					((hvac)row.get(0)).set_hvac_state(value);
				}
			}
			// set the time-of-day schedule
			for(hvac value : hvacObjs.values()){
				if(value.change_basepoint(hour_of_day, day_of_week)){
					//fncs.publish (obj.name + '/cooling_setpoint', obj.basepoint)
					bw.write("" + time_granted + "	" + value.name + "/cooling_setpoint	" + value.basepoint);
					bw.newLine();
					bw.flush();
				}
			}
			if(bSetDefaults){
				for(hvac value : hvacObjs.values()){
					//fncs.publish (obj.name + '/bill_mode', 'HOURLY')
					bw.write("" + time_granted + "	" + value.name + "/bill_mode	" + "HOURLY");
					bw.newLine();
					bw.flush();
					//fncs.publish (obj.name + '/monthly_fee', 0.0)
					bw.write("" + time_granted + "	" + value.name + "/monthly_fee	" + "0.0");
					bw.newLine();
					bw.flush();
					//fncs.publish (obj.name + '/thermostat_deadband', obj.deadband)
					bw.write("" + time_granted + "	" + value.name + "/thermostat_deadband	" + value.deadband);
					bw.newLine();
					bw.flush();
					//fncs.publish (obj.name + '/heating_setpoint', 60.0)
					bw.write("" + time_granted + "	" + value.name + "/heating_setpoint	" + "60.0");
					bw.newLine();
					bw.flush();
				}
				bSetDefaults = false;
			}
			if(time_granted >= tnext_bid){
				aucObj.clear_bids();
				//String time_key = String.valueOf(tnext_clear);
				//Map<String, Object> controller_metrics = new HashMap<String, Object>();
				//controller_metrics...
				for(hvac value : hvacObjs.values()){
					List bid = value.formulate_bid();					
					if(bWantMarket){
						aucObj.collect_bid(bid);
					}
					//controller_metrics...
				}
				tnext_bid += period;
			}
			if(time_granted >= tnext_agg){
				aucObj.aggregate_bids();
				//fncs.publish ('unresponsive_mw', aucObj.agg_unresp)
		        AggUn = aucObj.agg_unresp; //For Testing Purposes
		        bw.write("" + time_granted + "	" + "unresponsive_mw	" + aucObj.agg_unresp);
				bw.newLine();
				bw.flush();
		        //fncs.publish ('responsive_max_mw', aucObj.agg_resp_max)
		        AggRespMax = aucObj.agg_resp_max; //For Testing Purposes
		        bw.write("" + time_granted + "	" + "responsive_max_mw	" + aucObj.agg_resp_max);
				bw.newLine();
				bw.flush();
		        //fncs.publish ('responsive_c2', aucObj.agg_c2)
		        AggC2 = aucObj.agg_c2; //For Testing Purposes
		        bw.write("" + time_granted + "	" + "responsive_c2	" + aucObj.agg_c2);
				bw.newLine();
				bw.flush();
		        //fncs.publish ('responsive_c1', aucObj.agg_c1)
		        AggC1 = aucObj.agg_c1; //For Testing Purposes
		        bw.write("" + time_granted + "	" + "responsive_c1	" + aucObj.agg_c1);
				bw.newLine();
				bw.flush();
		        //fncs.publish ('responsive_deg', aucObj.agg_deg)
		        AggDeg = aucObj.agg_deg; //For Testing Purposes
		        bw.write("" + time_granted + "	" + "responsive_deg	" + aucObj.agg_deg);
				bw.newLine();
				bw.flush();
		        tnext_agg += period;
			}
			if(time_granted >= tnext_clear){
				if(bWantMarket){
					aucObj.clear_market();
					//fncs.publish ('clear_price', aucObj.clearing_price)
	                ClearingPrice = aucObj.clearing_price; //For Testing Purposes
	                bw.write("" + time_granted + "	" + "clear_price	" + aucObj.clearing_price);
					bw.newLine();
					bw.flush();
					for(hvac value : hvacObjs.values()){
						value.inform_bid(aucObj.clearing_price);
					}
				}
				//String time_key = String.valueOf(tnext_clear);
				//auction_metrics...
				ClearingType = aucObj.clearing_type.toString();  //For Testing Purposes
				tnext_clear += period;
			}
			if(time_granted >= tnext_adjust){
				if(bWantMarket){
					for(hvac value : hvacObjs.values()){
						//fncs.publish (obj.name + '/price', aucObj.clearing_price)
						bw.write("" + time_granted + "	" + value.name + "/price	" + aucObj.clearing_price);
						bw.newLine();
						bw.flush();
						if(value.bid_accepted()){
							//fncs.publish (obj.name + '/cooling_setpoint', obj.setpoint)
							bw.write("" + time_granted + "	" + value.name + "/cooling_setpoint	" + value.setpoint);
							bw.newLine();
							bw.flush();
						}
					}
				}
				tnext_adjust += period;
			}
			
			op.writeNext(new String[] {""+time_granted,
					String.valueOf(bWantMarket),
					String.valueOf(bSetDefaults),
					""+AggUn,					//unresponsive_mw
					""+AggRespMax,				//responsive_max_mw
					""+AggC2,					//responsive_c2
					""+AggC1,					//responsive_c1
					""+AggDeg,					//responsive_deg
					""+ClearingPrice,			//clear_price
					""+ClearingType,});			//clear_type
			op.flush();	
			System.out.println(time_granted + "/" + time_stop);
		}
		bw.close();
		op.close();
	}
	
	public static Map<String, Object> load_json_case(String fname) throws Exception
	{
		File file = new File(Auction_loop.class.getResource(fname).toURI());
		String json = new String(Files.readAllBytes(file.toPath()));
	    ObjectMapper mapper = new ObjectMapper();
	    MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
	    Map<String, Object> ppc = mapper.readValue(json, type);
		
		return ppc;
	}
	
	public static List<List<String>> read_file(String fname, String delimiter) throws Exception
	{
		
		File file = new File(Auction_loop.class.getResource(fname).toURI());
		Scanner dataFile = new Scanner(file.toPath());
		List<List<String>> data = new ArrayList<List<String>>();
		while(dataFile.hasNext()){
			String line = dataFile.nextLine();
			Scanner sc = new Scanner(line);
            sc.useDelimiter(delimiter);
            List<String> list = new ArrayList<String>();
            while(sc.hasNext()){
            	list.add(sc.next());
            }
            sc.close();
            data.add(list);
		}
		dataFile.close();
		return data;
	}
	
	public static double parse_fncs_magnitude(String arg) throws Exception
	{
		String tok = arg.replaceAll("\\+-; MWVAFKdegrij", "");
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
	
	public static double parse_kw(String arg) throws Exception
	{
		double p = 0.0;
		double q = 0.0;
		int nsign = 0;
		int nexp = 0;
		int ndot = 0;
		int kpos = 0;
		
		String tok = arg.replaceAll("j VA", "");
		boolean bLastDigit = false;
		boolean bParsed = false;
		Double[] vals = new Double[2];
		for(int i=0; i<tok.length(); i++){
			if(Character.toString(tok.charAt(i)).equals("+") || Character.toString(tok.charAt(i)).equals("-")){
				nsign += 1;
			}else if(Character.toString(tok.charAt(i)).equals("e") || Character.toString(tok.charAt(i)).equals("E")){
				nexp += 1;
			}else if(Character.toString(tok.charAt(i)).equals(".")){
				ndot += 1;
			}
			if(nsign == 2 && nexp == 0){
				kpos = i;
			    break;
			}
			if(nsign == 3){
				kpos = i;
			    break;
			}
		}
		vals[0] = Double.valueOf(tok.substring(0, kpos));
		vals[1] = Double.valueOf(tok.substring(kpos, tok.length()));

		if(arg.contains("d")){
			vals[1] *= (Math.PI/180.0);
			p = vals[0] * (Math.cos(vals[1]));
			q = vals[0] * (Math.sin(vals[1]));
		}else if(arg.contains("r")){
			p = vals[0] * (Math.cos(vals[1]));
			q = vals[0] * (Math.sin(vals[1]));	
		}else{
			p = vals[0];
			q = vals[1];
		}
		if(arg.contains("KVA")){
			p *= 1.0;
			q *= 1.0;
		}else if(arg.contains("MVA")){
			p *= 1000.0;
			q *= 1000.0;
		}else{//VA
			p /= 1000.0;
			q /= 1000.0;
		}
		return p;
	}

}
