package com.EPlusJsonMaven;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVWriter;

public class EPlusJson_loop {
	public static void main(String[] args)  throws Exception
	{
		//String subscriptions = "/eplusjson_subscriptions.txt"; //TE30 subscriptions
		String subscriptions = "/eplusjson_subscriptions_8500.txt"; //IEEE8500 subsciptions
		String rootname = "EPlusJson";
		
		int time_granted = 0;
		//int time_stop = 2*24*60*60;
		int time_stop = 1*24*60*60;
		int time_agg = 5*60;
				    
		//for real-time pricing response - can redefine on the command line
		double base_price = 0.02;
		double degF_per_price = 25.0;
		double max_delta_hi = 4.0;
		double max_delta_lo = 4.0;
		double delta = 0.0;
		double phaseWatts = 0.0;
				    
		double price = base_price;
		double totalWatts = 0.0;
		
		// read-in values eplus_json subscribes to from the Auction and EPlus federates
	    // that is done to bypass the fncs communication in order to isolate the 
	    // eplus_json federate from the expriment.
		List<List<String>> subs = read_file(subscriptions, "	");
		
		//Output CSV file
		File csvfile = new File(EPlusJson_loop.class.getResource("/").toURI());
		CSVWriter op = new CSVWriter(new FileWriter(csvfile + "/" + rootname + ".csv", true));
		op.writeNext(new String[] {"t[s]","cooling_setpoint_delta","heating_setpoint_delta","power_A",
				"power_B","power_C","bill_mode","price","monthly_fee","occupants"});
		op.flush();
		
		while (time_granted < time_stop){
			time_granted += time_agg;
			double occupants = 0.0;
			final int simTime = time_granted;
			List<List<String>> events = new ArrayList<List<String>>();
			subs.forEach(sub->{
				if(Integer.parseInt(sub.get(0)) == simTime){
					events.add(Arrays.asList(sub.get(0), sub.get(1), sub.get(2)));
				}
			});
			for(int i=0; i<events.size(); i++){
				String topic = events.get(i).get(1);
				if(topic.equals("kwhr_price")){
					price = Double.parseDouble(events.get(i).get(2));
				}else if(topic.equals("electric_demand_power")){
					totalWatts = Double.parseDouble(events.get(i).get(2));
				}else if(topic.contains("occupants_")){
					occupants += Double.parseDouble(events.get(i).get(2));
				}else{
					System.out.println(events.get(i).get(2));
				}
			}
			// this is price response
			delta = degF_per_price * (price - base_price);
			if (delta < -max_delta_lo){
				delta = -max_delta_lo;
			}else if(delta > max_delta_hi){
				delta = max_delta_hi;
			}
			
			phaseWatts = totalWatts / 3.0;
			//fncs::publish ("cooling_setpoint_delta", to_string(delta));
			//fncs::publish ("heating_setpoint_delta", to_string(-delta));
			//fncs::publish ("power_A", to_string(phaseWatts));
			//fncs::publish ("power_B", to_string(phaseWatts));
			//fncs::publish ("power_C", to_string(phaseWatts));
			//fncs::publish ("bill_mode", "HOURLY");
			String bill_mode = "HOURLY";
			//fncs::publish ("price", to_string(price));
			//fncs::publish ("monthly_fee", to_string(0.0));
			String monthly_fee = "0.0";
			op.writeNext(new String[] {""+time_granted,
					""+delta,				//cooling_setpoint_delta
					""+(-delta),			//heating_setpoint_delta
					""+phaseWatts,			//phase A power
					""+phaseWatts,			//phase B power
					""+phaseWatts,			//phase C power
					""+bill_mode,			//bill mode
					""+price,				//price
					""+monthly_fee,			//monthly fee
					""+occupants});			//occupants
			op.flush();
			System.out.println(time_granted + "/" + time_stop);
			
		}
		op.close();
	}
	
	public static List<List<String>> read_file(String fname, String delimiter) throws Exception
	{
		
		File file = new File(EPlusJson_loop.class.getResource(fname).toURI());
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

}
