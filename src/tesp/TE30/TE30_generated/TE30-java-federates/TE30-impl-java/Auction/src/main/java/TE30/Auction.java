package TE30;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.opencsv.CSVWriter;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Auction_loop {
    //Manages the simple_auction and hvac agents for the te30 and sgip1 examples
    public static void main(String[] args)  throws Exception
    {//Helper function that initializes and runs the agents
        //String configfile = "/TE_Challenge_agent_dict.json";//JSON agent configuration file for TE30
        String configfile = "/IEEE_8500_agent_dict.json";//JSON agent configuration file for TE30
        //String metrics_root = "TE_Challenge";
        
        //use "auction_subscriptions.txt" if original version is desired
        //String subscriptions = "/auction_subscriptions.txt";
        //use "auction_subscriptions2.txt" if original version is desired
        //String subscriptions = "/auction_subscriptions2.txt"; //TE30 subscriptions
        String subscriptions = "/auction_subscriptions_8500.txt"; //IEEE8500 subscriptions
        
        boolean bWantMarket = true; // set to false if no market is desired
        //int time_stop = 48 * 3600; //TE30 simulation
        int time_stop = 24 * 3600; //IEEE8500 simulation
        
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
        
        Map<String, Object> topicMap = new HashMap<String, Object>();//# to dispatch incoming messages; 0..5 for LMP, Feeder load, airtemp, mtr volts, hvac load, hvac state
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
        
        int tnext_bid = period - 2 * dt;    //3 * dt  # controllers calculate their final bids
        int tnext_agg = period - 2 * dt;    // auction calculates and publishes aggregate bid
        int tnext_clear = period;           // clear the market with LMP
        int tnext_adjust = period;          // + dt   # controllers adjust setpoints based on their bid and clearing
        
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
        List<List<String>> subs = read_file(subscriptions, "    ");
        List<List<String>> subs_part;
        
        while (time_granted < time_stop){
            //////The original version or the python code had a time step (dt) of 15 sec. In the updated version
            //////the time step varies based on the upcoming task (e.g., bid, agg, clear etc). The updated version 
            //////requires significantly less input data points (< 140,000 vs > 2,200,000). This code can handle both versions
            //////adjusting some of the simulation parameters based on the input data point array size.
            //////To reduce simulation time for the original version the sub list is split into 
            //////smaller sub lists for simulations with large input files
            time_granted = (int) Collections.min(Arrays.asList(tnext_bid, tnext_agg, tnext_clear, tnext_adjust, time_stop));
            if(subs.size() < 140000){   
                subs_part = subs;
            }else{
                //time_granted += dt;
                if(time_granted < 6000){
                    subs_part = subs.subList(0, 302387);
                }else if(time_granted >= 6000 && time_granted < 12000){
                    subs_part = subs.subList(302387,612527);
                }else if(time_granted >= 12000 && time_granted < 18000){
                    subs_part = subs.subList(612527,922667);
                }else if(time_granted >= 18000 && time_granted < 24000){
                    subs_part = subs.subList(922667,1232807);
                }else if(time_granted >= 24000 && time_granted < 30000){
                    subs_part = subs.subList(1232807,1542947);
                }else if(time_granted >= 30000 && time_granted < 36000){
                    subs_part = subs.subList(1542947,1853087);
                }else if(time_granted >= 36000 && time_granted < 42000){
                    subs_part = subs.subList(1853087,2163227);
                }else if(time_granted >= 42000 && time_granted < 48000){
                    subs_part = subs.subList(2163227,2473367);
                }else if(time_granted >= 48000 && time_granted < 54000){
                    subs_part = subs.subList(2473367,2783507);
                }else if(time_granted >= 54000 && time_granted < 60000){
                    subs_part = subs.subList(2783507,3093647);
                }else if(time_granted >= 60000 && time_granted < 66000){
                    subs_part = subs.subList(3093647,3403787);
                }else if(time_granted >= 66000 && time_granted < 72000){
                    subs_part = subs.subList(3403787,3713927);
                }else if(time_granted >= 72000 && time_granted < 78000){
                    subs_part = subs.subList(3713927,4024067);
                }else{
                    subs_part = subs.subList(4024067,subs.size()-1);
                }
            }
            
            time_delta = time_granted - time_last;
            time_last = time_granted;
            dt_now = dt_now.plusSeconds(time_delta);
            day_of_week = dt_now.getDayOfWeek().getValue()-1;
            hour_of_day = dt_now.getHour();
            
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
                    bw.write("" + time_granted + "  " + value.name + "/cooling_setpoint " + value.basepoint);
                    bw.newLine();
                    bw.flush();
                }
            }
            if(bSetDefaults){
                for(hvac value : hvacObjs.values()){
                    //fncs.publish (obj.name + '/bill_mode', 'HOURLY')
                    bw.write("" + time_granted + "  " + value.name + "/bill_mode    " + "HOURLY");
                    bw.newLine();
                    bw.flush();
                    //fncs.publish (obj.name + '/monthly_fee', 0.0)
                    bw.write("" + time_granted + "  " + value.name + "/monthly_fee  " + "0.0");
                    bw.newLine();
                    bw.flush();
                    //fncs.publish (obj.name + '/thermostat_deadband', obj.deadband)
                    bw.write("" + time_granted + "  " + value.name + "/thermostat_deadband  " + value.deadband);
                    bw.newLine();
                    bw.flush();
                    //fncs.publish (obj.name + '/heating_setpoint', 60.0)
                    bw.write("" + time_granted + "  " + value.name + "/heating_setpoint " + "60.0");
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
                bw.write("" + time_granted + "  " + "unresponsive_mw    " + aucObj.agg_unresp);
                bw.newLine();
                bw.flush();
                //fncs.publish ('responsive_max_mw', aucObj.agg_resp_max)
                AggRespMax = aucObj.agg_resp_max; //For Testing Purposes
                bw.write("" + time_granted + "  " + "responsive_max_mw  " + aucObj.agg_resp_max);
                bw.newLine();
                bw.flush();
                //fncs.publish ('responsive_c2', aucObj.agg_c2)
                AggC2 = aucObj.agg_c2; //For Testing Purposes
                bw.write("" + time_granted + "  " + "responsive_c2  " + aucObj.agg_c2);
                bw.newLine();
                bw.flush();
                //fncs.publish ('responsive_c1', aucObj.agg_c1)
                AggC1 = aucObj.agg_c1; //For Testing Purposes
                bw.write("" + time_granted + "  " + "responsive_c1  " + aucObj.agg_c1);
                bw.newLine();
                bw.flush();
                //fncs.publish ('responsive_deg', aucObj.agg_deg)
                AggDeg = aucObj.agg_deg; //For Testing Purposes
                bw.write("" + time_granted + "  " + "responsive_deg " + aucObj.agg_deg);
                bw.newLine();
                bw.flush();
                tnext_agg += period;
            }
            if(time_granted >= tnext_clear){
                if(bWantMarket){
                    aucObj.clear_market(tnext_clear, time_granted);
                    //fncs.publish ('clear_price', aucObj.clearing_price)
                    ClearingPrice = aucObj.clearing_price; //For Testing Purposes
                    bw.write("" + time_granted + "  " + "clear_price    " + aucObj.clearing_price);
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
                        bw.write("" + time_granted + "  " + value.name + "/price    " + aucObj.clearing_price);
                        bw.newLine();
                        bw.flush();
                        if(value.bid_accepted()){
                            //fncs.publish (obj.name + '/cooling_setpoint', obj.setpoint)
                            bw.write("" + time_granted + "  " + value.name + "/cooling_setpoint " + value.setpoint);
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
                    ""+AggUn,                   //unresponsive_mw
                    ""+AggRespMax,              //responsive_max_mw
                    ""+AggC2,                   //responsive_c2
                    ""+AggC1,                   //responsive_c1
                    ""+AggDeg,                  //responsive_deg
                    ""+ClearingPrice,           //clear_price
                    ""+ClearingType,});         //clear_type
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


/**
 * The Auction type of federate for the federation designed in WebGME.
 *
 */
public class Auction extends AuctionBase {
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // Meter vMeter = new Meter();
    // Market vMarket = new Market();
    // House vHouse = new House();
    //
    ///////////////////////////////////////////////////////////////////////

    public Auction(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
        // vMeter.registerObject(getLRC());
        // vMarket.registerObject(getLRC());
        // vHouse.registerObject(getLRC());
        //
        ///////////////////////////////////////////////////////////////////////
    }

    private void checkReceivedSubscriptions() {

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof Substation) {
                handleObjectClass((Substation) object);
            }
            else if (object instanceof Meter) {
                handleObjectClass((Meter) object);
            }
            else if (object instanceof House) {
                handleObjectClass((House) object);
            }
            else {
                log.debug("unhandled object reflection: {}", object.getClassName());
            }
        }
    }

    private void execute() throws Exception {
        if(super.isLateJoiner()) {
            log.info("turning off time regulation (late joiner)");
            currentTime = super.getLBTS() - super.getLookAhead();
            super.disableTimeRegulation();
        }

        /////////////////////////////////////////////
        // TODO perform basic initialization below //
        /////////////////////////////////////////////

        AdvanceTimeRequest atr = new AdvanceTimeRequest(currentTime);
        putAdvanceTimeRequest(atr);

        if(!super.isLateJoiner()) {
            log.info("waiting on readyToPopulate...");
            readyToPopulate();
            log.info("...synchronized on readyToPopulate");
        }

        ///////////////////////////////////////////////////////////////////////
        // TODO perform initialization that depends on other federates below //
        ///////////////////////////////////////////////////////////////////////

        if(!super.isLateJoiner()) {
            log.info("waiting on readyToRun...");
            readyToRun();
            log.info("...synchronized on readyToRun");
        }

        startAdvanceTimeThread();
        log.info("started logical time progression");

        while (!exitCondition) {
            atr.requestSyncStart();
            enteredTimeGrantedState();

            ////////////////////////////////////////////////////////////////////////////////////////
            // TODO objects that must be sent every logical time step
            //
            //    vMeter.set_bill_mode(<YOUR VALUE HERE >);
            //    vMeter.set_measured_voltage_1(<YOUR VALUE HERE >);
            //    vMeter.set_monthly_fee(<YOUR VALUE HERE >);
            //    vMeter.set_name(<YOUR VALUE HERE >);
            //    vMeter.set_price(<YOUR VALUE HERE >);
            //    vMeter.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //
            //    vMarket.set_clearing_price(<YOUR VALUE HERE >);
            //    vMarket.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //
            //    vHouse.set_air_temperature(<YOUR VALUE HERE >);
            //    vHouse.set_cooling_setpoint(<YOUR VALUE HERE >);
            //    vHouse.set_heating_setpoint(<YOUR VALUE HERE >);
            //    vHouse.set_hvac_load(<YOUR VALUE HERE >);
            //    vHouse.set_name(<YOUR VALUE HERE >);
            //    vHouse.set_power_state(<YOUR VALUE HERE >);
            //    vHouse.set_thermostat_deadband(<YOUR VALUE HERE >);
            //    vHouse.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //
            //////////////////////////////////////////////////////////////////////////////////////////

            checkReceivedSubscriptions();

            ////////////////////////////////////////////////////////////////////////////////////////
            // TODO break here if ready to resign and break out of while loop
            ////////////////////////////////////////////////////////////////////////////////////////


            if (!exitCondition) {
                currentTime += super.getStepSize();
                AdvanceTimeRequest newATR = new AdvanceTimeRequest(currentTime);
                putAdvanceTimeRequest(newATR);
                atr.requestSyncEnd();
                atr = newATR;
            }
        }

        // call exitGracefully to shut down federate
        exitGracefully();

        ////////////////////////////////////////////////////////////////////////////////////////
        // TODO Perform whatever cleanups needed before exiting the app
        ////////////////////////////////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(Substation object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(Meter object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(House object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            FederateConfig federateConfig = federateConfigParser.parseArgs(args, FederateConfig.class);
            Auction federate = new Auction(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}