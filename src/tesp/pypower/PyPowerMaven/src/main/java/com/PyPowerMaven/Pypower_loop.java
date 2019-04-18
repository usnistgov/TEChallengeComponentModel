package com.PyPowerMaven;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.opencsv.CSVWriter;
//PYPOWER solutions under control of FNCS for te30 and sgip1 examples

public class Pypower_loop {
	//Public function to start PYPOWER solutions under control of FNCS

	//  The time step, maximum time, and other data must be set up in a JSON file.
	//  This function will run the case under FNCS, manage the FNCS message traffic,
	//  and shutdown FNCS upon completion. One file is written:

	//  - *rootname.csv*; intermediate solution results during simulation

	//  Args:
	//    casefile (str): the configuring JSON file name, without extension
	//    rootname (str): the root filename for metrics output, without extension
	//	  subscriptions (str): values pypower federate is subscribed to
	
	public static void main(String[] args)  throws Exception
	{
		//String path = "src/main/resources/";
<<<<<<< HEAD
		//String casefile = "/te30_pp.json"; //TE30 metadata
		String casefile = "/te30_pp_8500.json"; //IEEE8500 metadata (load multiplier is lower than in TE30 case)
		String rootname = "TE_Challenge";
		//String subscriptions = "/pypower_subscriptions3.txt"; //TE30 subscriptions
		String subscriptions = "/pypower_subscriptions_8500.txt"; //IEEE8500 subscriptions
=======
		String casefile = "/te30_pp.json";
		String rootname = "TE_Challenge";
		String subscriptions = "/pypower_subscriptions3.txt";
>>>>>>> f2d951a6968524cfa38b51ecde9da79b561e9bdd
		Map<String, Object> ppc = load_json_case(casefile);
		//String StartTime = (String) ppc.get("StartTime");
		int tmax = (Integer) ppc.get("Tmax");
		int period = (Integer) ppc.get("Period");
		int dt = (Integer) ppc.get("dt");
		////make_dictionary(ppc, rootname);
		
		/////////////////////////////////
		//TODO - create json output files (fncsPYPOWER_isolated.py, line 154):
		//	bus_TE_Challenge_metrics.json
		//	gen_TE_Challenge_metrics.json
		//	sys_TE_Challeng_emetrics.json
		/////////////////////////////////
		
		/////////////////////////////////
		//TODO - create metas and metrics for the json output files (fncsPYPOWER_isolated.py, line 157):
		//	bus_TE_Challenge_metrics.json
		//	gen_TE_Challenge_metrics.json
		//	sys_TE_Challeng_emetrics.json
		/////////////////////////////////
		
		//HashMap bus_metrics = new HashMap();
		//HashMap gen_metrics = new HashMap();
		//HashMap sys_metrics = new HashMap();
		
		List<List<Double>> gencost = (ArrayList<List<Double>>) ppc.get("gencost");
		List<List> fncsBus = (ArrayList<List>) ppc.get("FNCS");
		List<List<Double>> gen = (ArrayList<List<Double>>) ppc.get("gen");
		
		//HashMap ppopt_market = ppoption(0, 0, (Integer) ppc.get("opf_dc"));
		//HashMap ppopt_regular = ppoption(0, 0, (Integer) ppc.get("pf_dc"));
		
		List<List<String>> loads = read_file("/" + (String) ppc.get("CSVFile"), ",");
		
		int nloads = loads.size();
		int ts = 0;
		int tnext_opf = -dt;
		
		//initializing for metrics collection (Optional)
		//int tnext_metrics = 0;
		//double loss_accum = 0;
		//boolean conv_accum = true;
		//int n_accum = 0;
		//HashMap bus_accum = new HashMap();
		//HashMap gen_accum = new HashMap();
		//for(int i=0; i<fncsBus.size(); i++){
		//	String busnum = fncsBus.get(i).get(0).toString();
		//	bus_accum.put(busnum, Arrays.asList(0.0,0.0,0.0,0.0,0.0,0.0,0.0,99999.0));
		//}
		//for(int i=0; i<gen.size(); i++){
		//	gen_accum.put(new Integer(i+1).toString(), Arrays.asList(0.0,0.0,0.0));
		//}
		
		//Output CSV file
		File csvfile = new File(Pypower_loop.class.getResource("/").toURI());
		CSVWriter op = new CSVWriter(new FileWriter(csvfile + "/" + rootname + ".csv", true));
		op.writeNext(new String[] {"t[s]","Converged","Pload","Pgen","Ploss","Volts","P7 (csv)","Unresp (opf)",
				"P7 (rpf)","Resp (opf)","GLD Pub","BID?","P7 Min","V7","LMP_P7","LMP_Q7",
				"Pgen1","Pgen2","Pgen3","Pgen4","Pdisp","Deg","c2","c1"});
		op.flush();
		
		//transactive load components
		double csv_load = 0;     //from the file
		double unresp = 0;      //unresponsive load estimate from the auction agent
		double resp = 0;        //will be the responsive load as dispatched by OPF
		int resp_deg = 0;   //RESPONSIVE_DEG from FNCS
		double resp_c1 = 0;     //RESPONSIVE_C1 from FNCS
		double resp_c2 = 0;     //RESPONSIVE_C2 from FNCS
		double resp_max = 0;    //RESPONSIVE_MAX_MW from FNCS
		double feeder_load = 0; //amplified feeder MW
		double lmp = 0;
		List<List<Double>> opf_gen = new ArrayList<List<Double>>();
		List<List<Double>> opf_bus = new ArrayList<List<Double>>();
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> rpf = new HashMap<String, Object>();
		
		//read-in values pypower subscribes to from the Auction and GridLabD federates that is done to 
		//bypass the fncs communication in order to isolate the pypower federate from the experiment.
		//once on UCEF this information will come from the other federates in the experiment.
		List<List<String>> subs = read_file(subscriptions, "	");
		//int vz = 0;
		
		while(ts <= tmax){
			//start by getting the latest inputs from GridLAB-D and the Auction
			boolean new_bid = false;
			float load_scale = ((Double) fncsBus.get(0).get(2)).floatValue();
			final int simTime = ts;
			List<List<String>> events = new ArrayList<List<String>>();
			subs.forEach(sub->{
				if(Integer.parseInt(sub.get(1)) == simTime){
					events.add(Arrays.asList(sub.get(0), sub.get(1), sub.get(2)));
				}
			});
			for(int i=0; i<events.size(); i++){
				String topic = events.get(i).get(0);
				if(topic.equals("UNRESPONSIVE_MW")){
					unresp = load_scale * Float.parseFloat(events.get(i).get(2));
					fncsBus.get(0).set(3, unresp); // to poke unresponsive estimate into the bus load slot
					new_bid = true;
				}else if(topic.equals("RESPONSIVE_MAX_MW")){
					resp_max = load_scale * Float.parseFloat(events.get(i).get(2));
					new_bid = true;
				}else if(topic.equals("RESPONSIVE_C2")){
					resp_c2 = Double.parseDouble(events.get(i).get(2))/load_scale;
					new_bid = true;
				}else if(topic.equals("RESPONSIVE_C1")){
					resp_c1 = Double.parseDouble(events.get(i).get(2));
					new_bid = true;
				}else if(topic.equals("RESPONSIVE_DEG")){
					resp_deg = Integer.parseInt(events.get(i).get(2));
					new_bid = true;
				}else{
					List<Float> gld_load = parse_mva(events.get(i).get(2).toString());// actual value, may not match unresp + resp load
					feeder_load = gld_load.get(0) * load_scale;
				}
			}
			// update the case for bids, outages and CSV loads
		    int idx = ((ts + dt) / period) % nloads;
		    List<List<Double>> bus = (ArrayList<List<Double>>) ppc.get("bus");
		    gen = (ArrayList<List<Double>>) ppc.get("gen");
		    List<List<Float>> branch = (ArrayList<List<Float>>) ppc.get("branch");
		    gencost = (ArrayList<List<Double>>) ppc.get("gencost");
		    csv_load = Float.parseFloat(loads.get(idx).get(0));
		    bus.get(4).set(2, Double.parseDouble(loads.get(idx).get(1)));
		    bus.get(8).set(2, Double.parseDouble(loads.get(idx).get(2)));

		    // process the generator and branch outages
		    List<List<Integer>> units = (ArrayList<List<Integer>>) ppc.get("UnitsOut");
			List<List<Integer>> branches = (ArrayList<List<Integer>>) ppc.get("BranchesOut");
			for(int i=0; i<units.size(); i++){
				if(ts >= units.get(i).get(1) && ts <= units.get(i).get(2)){
					gen.get(units.get(i).get(0)).set(7, 0.0);;
				}else{
					gen.get(units.get(i).get(0)).set(7, 1.0);;
				}
			}
			for(int i=0; i<branches.size(); i++){
				if(ts >= branches.get(i).get(1) && ts <= branches.get(i).get(2)){
					branch.get(branches.get(i).get(0)).set(10, 0f);
				}else{
					branch.get(branches.get(i).get(0)).set(10, 1f);
				}
			}
			
			if(resp_deg == 2){
				gencost.get(4).set(3, 3.0);
				gencost.get(4).set(4, -resp_c2);
				gencost.get(4).set(5, resp_c1);
			}else if(resp_deg == 1){
				gencost.get(4).set(3, 2.0);
				gencost.get(4).set(4, resp_c1);
				gencost.get(4).set(5, 0.0);
			}else{
				gencost.get(4).set(3, 1.0);
				gencost.get(4).set(4, 999.0);
				gencost.get(4).set(5, 0.0);
			}
			gencost.get(4).set(6, 0.0);
			
			// expecting to solve opf one dt before the market clearing period ends, so GridLAB-D has time to use it
		    // for OPF, the FNCS bus load is CSV + Unresponsive estimate, with Responsive separately dispatchable
			if(ts >= tnext_opf){
				bus = (ArrayList<List<Double>>) ppc.get("bus");
				gen = (ArrayList<List<Double>>) ppc.get("gen");
				fncsBus = (ArrayList<List>) ppc.get("FNCS");
				bus.get(6).set(2, csv_load);
				for(int i=0; i<fncsBus.size(); i++){
					unresp = Float.parseFloat((fncsBus.get(i).get(3).toString()));
					int newidx = (Integer) fncsBus.get(i).get(0) - 1;
					if(unresp >= feeder_load){
						bus.get(newidx).set(2, (bus.get(newidx).get(2) + unresp));
					}else{
						bus.get(newidx).set(2, (bus.get(newidx).get(2) + feeder_load));
					}
				}
				gen.get(4).set(9, -resp_max);

				String ppc_json = map2string(ppc);
				
				File pythonOPF = new File(Pypower_loop.class.getResource("/callopf_iso.py").toURI());
				ProcessBuilder pb = new ProcessBuilder("python3", pythonOPF.toString(), "0", "0", ""+ppc.get("opf_dc"), ppc_json);
				Process p = pb.start();
				BufferedReader br_err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				while (br_err.readLine() != null) {
					System.out.println(br_err.readLine());
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String opf_res = br.readLine();
				if(((ArrayList<List<Double>>) ppc.get("bus")).get(0).size() < 17){
					//during the first opf run opf_iso.py adds zero columns to bus, gen branch for multipliers, etc
					//alternatively, the updated ppc could be returned by Python and replace the local ppc Map
					bus = (ArrayList<List<Double>>) ppc.get("bus");
					for(int i=0; i<bus.size(); i++){
						bus.get(i).addAll(Arrays.asList(0.0, 0.0, 0.0, 0.0));
					}
					gen = (ArrayList<List<Double>>) ppc.get("gen");
					for(int i=0; i<gen.size(); i++){
						gen.get(i).addAll(Arrays.asList(0.0, 0.0, 0.0, 0.0));
					}
					branch = (ArrayList<List<Float>>) ppc.get("branch");
					for(int i=0; i<branch.size(); i++){
						branch.get(i).addAll(Arrays.asList(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f));
					}
					ppc.put("bus", bus);
					ppc.put("gen", gen);
					ppc.put("branch", branch);
				}
				p.destroy();
				br.close();
				br_err.close();		
					
				res = string2map(opf_res);


				//if((boolean)res.get("success") == false){
				//	conv_accum = false;
				//}
				opf_bus = (ArrayList<List<Double>>) res.get("bus");
				opf_gen = (ArrayList<List<Double>>) res.get("gen");
				lmp = opf_bus.get(6).get(13);// lmp for Auction
				//fncs.publish('LMP_B7', 0.001 * lmp) # publishing $/kwh
				//System.out.println("lmp: " + lmp);
				resp = -1.0 * Double.valueOf(""+opf_gen.get(4).get(1));
				
				//if unit 2 (the normal swing bus) is dispatched at max, change the swing bus to 9
				List<List<Double>> bus_temp = (ArrayList<List<Double>>) ppc.get("bus");
				if(opf_gen.get(1).get(1) >= 191.0){
					bus_temp.get(1).set(1, 2.0);
					bus_temp.get(8).set(1, 3.0);
				}else{
					bus_temp.get(1).set(1, 3.0);
					bus_temp.get(8).set(1, 1.0);
				}
				ppc.put("bus", bus_temp);
				tnext_opf += period;
			}
			//always update the electrical quantities with a regular power flow
			bus = (ArrayList<List<Double>>) ppc.get("bus");
			gen = (ArrayList<List<Double>>) ppc.get("gen");
			bus.get(6).set(13, lmp);
			gen.get(0).set(1, opf_gen.get(0).get(1));
			gen.get(1).set(1, opf_gen.get(1).get(1));
			gen.get(2).set(1, opf_gen.get(2).get(1));
			gen.get(3).set(1, opf_gen.get(3).get(1));
			//during regular power flow, we use the actual CSV + feeder load, ignore dispatchable load and use actual
			bus.get(6).set(2, csv_load + feeder_load);
			gen.get(4).set(1, 0.0);// opf_gen[4, 1]
			gen.get(4).set(9, 0.0);

			String ppc_json = map2string(ppc);
			
			File pythonPF = new File(Pypower_loop.class.getResource("/callpf_iso.py").toURI());
			ProcessBuilder pb = new ProcessBuilder("python3", pythonPF.toString(), "0", "0", ""+ppc.get("pf_dc"), ppc_json);
			Process p = pb.start();
			BufferedReader br_err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while (br_err.readLine() != null) {
				System.out.println(br_err.readLine());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String pf_res = br.readLine();

			p.destroy();
			br.close();
			br_err.close();		
				
			rpf = string2map(pf_res);
			
			//if((int)rpf.get("success") == 0){
			//	conv_accum = false;
			//}
			bus = (ArrayList<List<Double>>) rpf.get("bus");
			gen = (ArrayList<List<Double>>) rpf.get("gen");
			
			double Pload = 0;
			double Pgen = 0;
			for(int i=0; i<bus.size(); i++){
				Pload += bus.get(i).get(2);
			}
			for(int i=0; i<gen.size(); i++){
				Pgen += gen.get(i).get(1);
			}
			double Ploss = Pgen - Pload;
			
			//update the metrics
			//n_accum += 1;
			//loss_accum += Ploss;
			//for(int i=0; i<fncsBus.size(); i++){
			//	int busnum = (int) fncsBus.get(i).get(0);
			//	int busidx = busnum-1;
			//	List<Double> row = bus.get(busidx);
				//LMP_P, LMP_Q, PD, QD, Vang, Vmag, Vmax, Vmin: row[11] and row[12] are Vmax and Vmin constraints
			//	double PD = row.get(2) + resp; //TODO, if more than one FNCS bus, track scaled_resp separately
			//	double Vpu = row.get(7);
			//	List<Double> bus_accum_row = (List<Double>) bus_accum.get(String.valueOf(busnum));
			//	bus_accum_row.set(0, bus_accum_row.get(0) + row.get(13) * 0.001);
			//	bus_accum_row.set(1, bus_accum_row.get(1) + row.get(14) * 0.001);
			//	bus_accum_row.set(2, bus_accum_row.get(2) + PD);
			//	bus_accum_row.set(3, bus_accum_row.get(3) + row.get(3));
			//	bus_accum_row.set(4, bus_accum_row.get(4) + row.get(8));
			//	bus_accum_row.set(5, bus_accum_row.get(5) + Vpu);
				
			//	if(Vpu > bus_accum_row.get(6)){
			//		bus_accum_row.set(6, Vpu);
			//	}
			//	if(Vpu < bus_accum_row.get(7)){
			//		bus_accum_row.set(7, Vpu);
			//	}
			//	bus_accum.put(String.valueOf(busnum), bus_accum_row);
			//}
			//for(int i=0; i<gen.size(); i++){
			//	List row = gen.get(i);
			//	int busidx = ((Double)row.get(0)).intValue()-1;
				//Pgen, Qgen, LMP_P  (includes the responsive load as dispatched by OPF)
				//System.out.println(gen_accum.get(String.valueOf(i)));
			//	List<Double> gen_accum_row = (List<Double>) gen_accum.get(String.valueOf(i+1));
			//	gen_accum_row.set(0, gen_accum_row.get(0) + (double) row.get(1));
			//	gen_accum_row.set(1, gen_accum_row.get(1) + (double) row.get(2));
			//	gen_accum_row.set(2, gen_accum_row.get(2) + opf_bus.get(busidx).get(13) * 0.001);
			//	gen_accum.put(String.valueOf(i+1), gen_accum_row);
			//}
			//write the metrics
			//if(ts >= tnext_metrics){
				//sys_metrics.put(ts, loss_accum/n_accum);
				//int busnum;
				//int busidx;
				//List<Double> row;
				//List<Double> met;
				//for(int i = 0; i<fncsBus.size(); i++){
				//	busnum = (int) fncsBus.get(i).get(0);
				//	busidx = busnum - 1;
				//	row = bus.get(busidx);
				//	met = (List<Double>) bus_accum.get(String.valueOf(busnum));
					///////////////////////////////////
					//TODO - Lines 448-457
					///////////////////////////////////
				//}
			//	tnext_metrics += period;
			//	n_accum = 0;
			//	loss_accum = 0;
			//	conv_accum = true;
				
			//}
			
			double volts = 1000.0 * bus.get(6).get(7) * bus.get(6).get(9) / Math.sqrt(3.0);//VLN for GridLAB-D
			//fncs.publish('three_phase_voltage_B7', volts)

			List<List<Double>> gen1 = (ArrayList<List<Double>>) res.get("gen");
			List<List<Double>> gencost1 = (ArrayList<List<Double>>) ppc.get("gencost");
			op.writeNext(new String[] {""+ts,
					res.get("success").toString(),
					""+Pload,					//Pload
					""+Pgen,					//Pgen
					""+Ploss,					//Ploss
					""+volts,					//Volts
					""+csv_load,				//P7 (csv)
					""+unresp,					//GLD Unresp
					""+bus.get(6).get(2),		//P7 (rpf)
					""+resp,					//Resp (opf)
					""+feeder_load,				//GLD Pub
					""+new_bid,
					""+gen.get(4).get(9),		//P7 Min
					""+bus.get(6).get(7),       //V7
			        ""+bus.get(6).get(13),      //LMP_P7
			        ""+bus.get(6).get(14),      //LMP_Q7
			        ""+gen.get(0).get(1),       //Pgen1
			        ""+gen.get(1).get(1),       //Pgen2 
			        ""+gen.get(2).get(1),       //Pgen3
			        ""+gen.get(3).get(1),       //Pgen4
			        ""+gen1.get(4).get(1), 		//Pdisp
			        ""+resp_deg,       			//degree
			        ""+gencost1.get(4).get(4),  	//c2
			        ""+gencost1.get(4).get(5)});	//c1
			op.flush();		
			

			if(ts >= tmax){
				break;
			}
			ts += dt;
			System.out.println(ts + "/" + tmax);
			
		}
		
		op.close();
		///////////////////////////////////
		//TODO - Lines 525-534
		///////////////////////////////////
		
		
	}
	
	public static Map<String, Object> load_json_case(String fname) throws Exception
	{
		//Helper function to load PYPOWER case from a JSON file

		//  Args:
		//    fname (str): the JSON file to open

		//  Returns:
		//    ppc (Map): the loaded PYPOWER case structure
		    
		File file = new File(Pypower_loop.class.getResource(fname).toURI());
		String json = new String(Files.readAllBytes(file.toPath()));
	    ObjectMapper mapper = new ObjectMapper();
	    MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
	    Map<String, Object> ppc = mapper.readValue(json, type);
	    //List<List<Double>> bus = (ArrayList<List<Double>>) ppc.get("bus");
	    //ppc.put("bus", bus);
		
		return ppc;
	}
	//public static void make_dictionary(Map<String, Object> ppc, String rootname) throws Exception{
		
	//	List<List<Double>> bus = (ArrayList<List<Double>>) ppc.get("bus");
	//	List<List<Integer>> gen = (ArrayList<List<Integer>>) ppc.get("gen");
	//	List<List<Float>> cost = (ArrayList<List<Float>>) ppc.get("gencost");
	//	List<List<Integer>> fncsBus = (ArrayList<List<Integer>>) ppc.get("FNCS");
	//	List<List<Integer>> units = (ArrayList<List<Integer>>) ppc.get("UnitsOut");
	//	List<List<Float>> branches = (ArrayList<List<Float>>) ppc.get("BranchesOut");
		
	//	String bustypename = new String();
	//	ArrayList generators = new ArrayList();
	//	ArrayList fncsBuses = new ArrayList();
	//	ArrayList unitsout = new ArrayList();
	//	ArrayList branchesout = new ArrayList();
		
	//	int busnum;
	//	double bustype;
	
	//	for(int i=0; i<gen.size(); i++){
	//		busnum = gen.get(i).get(0);
	//		bustype = bus.get(busnum-1).get(1);
	//		if(bustype == 1.0){
	//			bustypename = "pq";
	//		}else if(bustype == 2.0){
	//			bustypename = "pv";
	//		}else if(bustype == 3.0){
	//			bustypename = "swing";
	//		}else{
	//			bustypename = "unknown";
	//		}
	//		HashMap mMap = new HashMap();
	//		mMap.put("bus",busnum);
	//		mMap.put("bustype",bustypename);
	//		mMap.put("Pnom",gen.get(i).get(1));
	//		mMap.put("Pmax",gen.get(i).get(8));
	//		mMap.put("genfuel","tbd");
	//		mMap.put("gentype","tbd");
	//		mMap.put("StartupCost",cost.get(i).get(1));
	//		mMap.put("ShutdownCost",cost.get(i).get(2));
	//		mMap.put("c2",cost.get(i).get(4));
	//		mMap.put("c1",cost.get(i).get(5));
	//		mMap.put("c0",cost.get(i).get(6));
	//		generators.add(mMap);
	//	}
	//	for(int i=0; i<fncsBus.size(); i++){
	//		busnum = fncsBus.get(i).get(0);
	//		int busidx = busnum-1;
	//		HashMap mMap = new HashMap();
	//		mMap.put("Pnom",bus.get(busidx).get(2));
	//		mMap.put("Qnom",bus.get(busidx).get(3));
	//		mMap.put("area",bus.get(busidx).get(6));
	//		mMap.put("zone",bus.get(busidx).get(10));
	//		mMap.put("ampFactor",fncsBus.get(i).get(2));
	//		mMap.put("GLDsubstations",fncsBus.get(i).get(1));
	//		fncsBuses.add(mMap);
	//	}
	//	for(int i=0; i<units.size(); i++){
	//		HashMap mMap = new HashMap();
	//		mMap.put("unit",units.get(i).get(0));
	//		mMap.put("tout",units.get(i).get(1));
	//		mMap.put("tin",units.get(i).get(2));
	//		unitsout.add(mMap);
	//	}
	//	for(int i=0; i<branches.size(); i++){
	//		HashMap mMap = new HashMap();
	//		mMap.put("branch",branches.get(i).get(0));
	//		mMap.put("tout",branches.get(i).get(1));
	//		mMap.put("tin",branches.get(i).get(2));
	//		branchesout.add(mMap);
	//	}
	//	ArrayList ppdict = new ArrayList();
	//	HashMap mMap = new HashMap();
	//	mMap.put("baseMVA",ppc.get("baseMVA"));
	//	mMap.put("fncsBuses",fncsBuses);
	//	mMap.put("generators",generators);
	//	mMap.put("UnitsOut",unitsout);
	//	mMap.put("BranchesOut",branchesout);
	//	ppdict.add(mMap);
		//System.out.println(ppdict);
		////////////////////////////////
		//TODO - write ppdict to the _m_dict.json output file
		///////////////////////////////
	    
	//}
	
	// the ppoption method could be removed from here and rather be called from within Python. 
	// It is just a collection of PYPOWER settings.
	//public static HashMap ppoption(int VERBOSE, int OUT_ALL, int PF_DC) throws Exception
	//{
/////////////////////////////////////////////////////////////////////////
		//Used to set and retrieve a PYPOWER options vector.

	    //C{opt = ppoption()} returns the default options vector

	    //C{opt = ppoption(NAME1=VALUE1, NAME2=VALUE2, ...)} returns the default
	    //options vector with new values for the specified options, NAME# is the
	    //name of an option, and VALUE# is the new value.

	    //C{opt = ppoption(OPT, NAME1=VALUE1, NAME2=VALUE2, ...)} same as above
	    //except it uses the options vector OPT as a base instead of the default
	    //options vector.

	    //Examples::
	    //    opt = ppoption(PF_ALG=2, PF_TOL=1e-4);
	    //    opt = ppoption(opt, OPF_ALG=565, VERBOSE=2)
/////////////////////////////////////////////////////////////////////////
		
	//	ArrayList options = new ArrayList();
		/////////PF_OPTIONS/////////
	//	options.add(Arrays.asList("pf_alg", 1, "power flow algorithm:\n1 - Newton's method,"
	//			+ "\n2 - Fast-Decoupled (XB version),"
	//			+ "\n3 - Fast-Decoupled (BX version),\n4 - Gauss Seidel"));
	//	options.add(Arrays.asList("pf_tol", 1e-8, "termination tolerance on per unit P & Q mismatch"));
	//	options.add(Arrays.asList("pf_max_it", 10, "maximum number of iterations for Newton\'s method"));
	//	options.add(Arrays.asList("pf_max_it_fd", 30, "maximum number of iterations for fast decoupled method"));
	//	options.add(Arrays.asList("pf_max_it_gs", 1000, "maximum number of iterations for Gauss-Seidel method"));
	//	options.add(Arrays.asList("enforce_q_lims", false, "enforce gen reactive power limits, at expense of |V|"));
	//	options.add(Arrays.asList("pf_dc", false, "use DC power flow formulation, for power flow and OPF:"
	//			+ "\nFalse - use AC formulation & corresponding algorithm opts, "
	//			+ "\nTrue  - use DC formulation, ignore AC algorithm options"));
		
		/////////OPF_OPTIONS/////////
	//	options.add(Arrays.asList("opf_alg", 0, "algorithm to use for OPF:"
	//			+ "\n0 - choose best default solver available in the following order, 500, 540, 520 then 100/200 "
	//			+ "Otherwise the first digit specifies the problem formulation and the second specifies the solver, "
	//			+ "as follows, (see the User's Manual for more details)"
	//			+ "\n500 - generalized formulation, MINOS,"
	//			+ "\n540 - generalized formulation, MIPS primal/dual interior point method,"
	//			+ "\n545 - generalized formulation (except CCV), SC-MIPS step-controlled primal/dual interior point method"));
	//	options.add(Arrays.asList("opf_violation", 5e-6, "constraint violation tolerance"));
	//	options.add(Arrays.asList("opf_flow_lim", 0, "qty to limit for branch flow constraints:"
	//			+ "\n0 - apparent power flow (limit in MVA),"
	//			+ "\n1 - active power flow (limit in MW),"
	//			+ "\n2 - current magnitude (limit in MVA at 1 p.u. voltage"));
	//	options.add(Arrays.asList("opf_ignore_ang_lim", false, "ignore angle difference limits for branches even if specified"));
	//	options.add(Arrays.asList("opf_alg_dc", 0, "solver to use for DC OPF:"
	//			+ "\n0 - choose default solver based on availability in the following order, 600, 500, 200."
	//			+ "\n200 - PIPS, Python Interior Point Solver primal/dual interior point method,"
	//			+ "\n250 - PIPS-sc, step-controlled variant of PIPS"
	//			+ "\n400 - IPOPT, requires pyipopt interface to IPOPT solver available from: https://projects.coin-or.org/Ipopt/"
	//			+ "\n500 - CPLEX, requires Python interface to CPLEX solver"
	//			+ "\n600 - MOSEK, requires Python interface to MOSEK solver available from: http://www.mosek.com/"
	//			+ "\n700 - GUROBI, requires Python interface to Gurobi optimizer available from: http://www.gurobi.com/"));
		
		/////////OUTPUT_OPTIONS/////////
	//	options.add(Arrays.asList("verbose", 1, "amount of progress info printed:"
	//			+ "\n0 - print no progress info,"
	//			+ "\n1 - print a little progress info,"
	//			+ "\n2 - print a lot of progress info,"
	//			+ "\n3 - print all progress info"));
	//	options.add(Arrays.asList("out_all", -1, "controls printing of results:"
	//			+ "\n-1 - individual flags control what prints,"
	//			+ "\n0 - don't print anything (overrides individual flags),"
	//			+ "\n1 - print everything (overrides individual flags)"));
	//	options.add(Arrays.asList("out_sys_sum", true, "print system summary"));
	//	options.add(Arrays.asList("out_area_sum", false, "print area summaries"));
	//	options.add(Arrays.asList("out_bus", true, "print bus detail"));
	//	options.add(Arrays.asList("out_branch", true, "print branch detail"));
	//	options.add(Arrays.asList("out_gen", false, "print generator detail (OUT_BUS also includes gen info)"));
	//	options.add(Arrays.asList("out_all_lim", -1, "control constraint info output:"
	//			+ "\n-1 - individual flags control what constraint info prints,"
	//			+ "\n0 - no constraint info (overrides individual flags),"
	//			+ "\n1 - binding constraint info (overrides individual flags),"
	//			+ "\n2 - all constraint info (overrides individual flags)"));
	//	options.add(Arrays.asList("out_v_lim", 1, "control output of voltage limit info:"
	//			+ "\n0 - don't print,"
	//			+ "\n1 - print binding constraints only,"
	//			+ "\n2 - print all constraints (same options for OUT_LINE_LIM, OUT_PG_LIM, OUT_QG_LIM)"));
	//	options.add(Arrays.asList("out_line_lim", 1, "control output of line limit info"));
	//	options.add(Arrays.asList("out_pg_lim", 1, "control output of gen P limit info"));
	//	options.add(Arrays.asList("out_qg_lim", 1, "control output of gen Q limit info"));
	//	options.add(Arrays.asList("return_raw_der", 0, "return constraint and "
	//			+ "derivative info in results['raw'] (in keys g, dg, df, d2f))"));
		
		/////////OUTPUT_OPTIONS/////////
	//	options.add(Arrays.asList("pdipm_feastol", 0, "feasibility (equality) tolerance "
	//			+ "for Primal-Dual Interior Points Methods, set to value of OPF_VIOLATION by default"));
	//	options.add(Arrays.asList("pdipm_gradtol", 1e-6, "gradient tolerance for Primal-Dual Interior Points Methods"));
	//	options.add(Arrays.asList("pdipm_comptol", 1e-6, "complementary condition (inequality) "
	//			+ "tolerance for Primal-Dual Interior Points Methods"));
	//	options.add(Arrays.asList("pdipm_costtol", 1e-6, "optimality tolerance for Primal-Dual Interior Points Methods"));
	//	options.add(Arrays.asList("pdipm_max_it",  150, "maximum number of iterations for Primal-Dual Interior Points Methods"));
	//	options.add(Arrays.asList("scpdipm_red_it", 20, "maximum number of reductions per iteration "
	//			+ "for Step-Control Primal-Dual Interior Points Methods"));
		
	//	HashMap ppopt = new HashMap();
	//	for(int i=0; i<options.size(); i++){
	//		List ls = (List) options.get(i);
	//		ppopt.put(ls.get(0).toString().toUpperCase(), ls.get(1));
	//	}

	//	ppopt.put("VERBOSE", VERBOSE);
	//	ppopt.put("OUT_ALL", OUT_ALL);
	//	ppopt.put("PF_DC", PF_DC);
	//	return ppopt;
	//}
	public static List<List<String>> read_file(String fname, String delimiter) throws Exception
	{
		
		File file = new File(Pypower_loop.class.getResource(fname).toURI());
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
	public static List<Float> parse_mva(String arg) throws Exception
	{
		//Helper function to parse P+jQ from a FNCS value

		//  Args:
		//    arg (str): FNCS value in rectangular format

		//  Returns:
		//    float, float: P [MW] and Q [MVAR]
		    		
		float p = 0f;
		float q = 0f;
		
		String tok = arg.replaceAll("j VA", "");
		boolean bLastDigit = false;
		boolean bParsed = false;
		List vals = new ArrayList(Arrays.asList(0.0f, 0.0f));
		for(int i=0; i<tok.length(); i++){
			if(Character.toString(tok.charAt(i)).equals("+") || Character.toString(tok.charAt(i)).equals("-")){
				if(bLastDigit){
					vals.set(0, Float.parseFloat(tok.substring(0, i)));
					vals.set(1, Float.parseFloat(tok.substring(i)));
					bParsed = true;
					break;
				}
			}
			bLastDigit = Character.isDigit(tok.charAt(i));
		}
		if(!bParsed){
			vals.set(0, Float.parseFloat(tok));
		}
		if(tok.contains("d")){
			vals.set(1, ((float) vals.get(1))*(Math.PI/180.0));
			p = ((float)vals.get(0)) * ((float) Math.cos(((float)vals.get(1))));
			q = ((float)vals.get(0)) * ((float) Math.sin(((float)vals.get(1))));
		}else if(tok.contains("r")){
			p = ((float)vals.get(0)) * ((float) Math.cos(((float)vals.get(1))));
			q = ((float)vals.get(0)) * ((float) Math.sin(((float)vals.get(1))));	
		}else{
			p = (float) vals.get(0);
			q = (float) vals.get(1);
		}
		if(arg.contains("KVA")){
			p /= 1000.0;
			q /= 1000.0;
		}else if(arg.contains("MVA")){
			p *= 1.0;
			q *= 1.0;
		}else{
			p /= 1000000.0;
			q /= 1000000.0;
		}
		List<Float> pq = new ArrayList<>();
		pq.add(0, p);
		pq.add(1, q);
		return pq;
	}
	
	public static String map2string(Map ppc) throws Exception {
		ObjectMapper map = new ObjectMapper();
		String map2json = map.writerWithDefaultPrettyPrinter().writeValueAsString(ppc);
		return map2json;
	}
	
	public static Map string2map(String res) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
	    MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
	    Map<String, Object> str2map = mapper.readValue(res, type);
	    return str2map;
	}

}
