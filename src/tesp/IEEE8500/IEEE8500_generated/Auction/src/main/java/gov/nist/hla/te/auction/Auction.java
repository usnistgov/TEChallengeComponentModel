package gov.nist.hla.te.auction;

import gov.nist.hla.te.auction.rti.*;

import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.utils.CpswtUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.opencsv.CSVWriter;

import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Auction extends AuctionBase {
    private final static Logger log = LogManager.getLogger();

    private enum NamingConvention {
        TE30,
        IEEE8500,
        FEEDER_GENERATOR
    }

    private double currentTime = 0;

    private boolean receivedSimTime = false;
    
    private double logicalTimeScale;
    
    private double lastLogicalTime;
    
    public int lmpReceived;
    
    public int lmpExpected;
    
    private String config;

    private NamingConvention namingConvention; // select {TE30, IEEE8500, FeederGenerator} naming convention
    
    private LocalDateTime dt_now;
    
    private SimpleAuction aucObj;
    
    private Map<String, hvac> hvacObjs = new HashMap<String, hvac>();

    private Map<String, House> houses = new HashMap<String, House>();
    
    private Map<String, Meter> meters = new HashMap<String, Meter>();

    public Auction(AuctionConfig params) throws Exception {
        super(params);
        config = params.configFileName;

        final String namingConventionString = params.namingConvention.toLowerCase();
        if (namingConventionString.equals("te30")) {
            namingConvention = NamingConvention.TE30;
            log.info("using TE30 names for houses and meters");
        } else if (namingConventionString.equals("ieee8500")) {
            namingConvention = NamingConvention.IEEE8500;
            log.info("using IEEE8500 names for houses and meters");
        } else if (namingConventionString.equals("feedergenerator")) {
            namingConvention = NamingConvention.FEEDER_GENERATOR;
            log.info("using FeederGenerator names for houses and meters");
        } else {
            log.error("unknown naming convention: {}", params.namingConvention);
            throw new IOException("bad naming convention");
        }
    }

    private void checkReceivedSubscriptions() {
        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof SimTime) {
                handleInteractionClass((SimTime) interaction);
            }
            else if (interaction instanceof DataReceipt) {
                handleInteractionClass((DataReceipt) interaction);
            }
            else if (interaction instanceof LMP) {
                handleInteractionClass((LMP) interaction);
            }
            else {
                log.debug("unhandled interaction: {}", interaction.getClassName());
            }
        }

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

boolean bWantMarket = true; // set to false if no market is desired
        
        // ====== load the JSON dictionary; create the corresponding objects =========
        Map<String, Object> dict= load_json_case(config);
        String market_key = (String)((Map) dict.get("markets")).keySet().toArray()[0];// only using the first market
        Map<String, Object> market_row = (Map<String, Object>) ((Map<String, Object>) dict.get("markets")).get(market_key);
        String unit = market_row.get("unit").toString();
        
        /////////////////////////////////
        //TODO - create metas and metrics for the json output files (auction_isolated.py, lines 33-36)
        /////////////////////////////////
        
        log.trace("creating auction object");
        aucObj = new SimpleAuction(market_row, market_key);
        
        int dt = (int) dict.get("dt");
        int period = aucObj.period;
        
        log.trace("creating hvac objects");
        Object[] hvac_keys = ((Map<String, Object>)dict.get("controllers")).keySet().toArray();
        for(int i=0; i<hvac_keys.length; i++){
            String key = (String) hvac_keys[i];
            Map<String, Object> row = (Map<String, Object>)((Map<String, Object>) dict.get("controllers")).get(key);
            hvac newHvac = new hvac(row, key, aucObj);
            hvacObjs.put(key, newHvac);
            
            // register the HLA objects
            House newHouse = new House();
            newHouse.registerObject(getLRC());
            houses.put(newHvac.get_house_name(), newHouse);
            
            Meter newMeter = new Meter();
            newMeter.registerObject(getLRC());
            meters.put(newHvac.get_meter_name(), newMeter);
        }
        
        log.trace("starting initialization");
        
        // ==================== Time step looping under FNCS ===========================
        aucObj.initAuction();
        double LMP = aucObj.mean;
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
        CSVWriter op = new CSVWriter(new FileWriter(Paths.get("auction.csv").toFile()));
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
        
        ////////////////////////////End Test////////////////////////////
        ////////////////////////////////////////////////////////////////////////////

        AdvanceTimeRequest atr = new AdvanceTimeRequest(currentTime);
        putAdvanceTimeRequest(atr);

        if(!super.isLateJoiner()) {
            log.info("waiting on readyToPopulate...");
            readyToPopulate();
            log.info("...synchronized on readyToPopulate");
        }

        while (!receivedSimTime) {
            log.info("waiting to receive SimTime...");
            synchronized (lrc) {
                lrc.tick();
            }
            checkReceivedSubscriptions();
            if (!receivedSimTime) {
                CpswtUtils.sleep(1000);
            }
        }

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

            log.info("logical time {} / {}", currentTime, lastLogicalTime);

            final double federateTime = this.getCurrentTime() * logicalTimeScale;
            log.debug("current federate time is {}", federateTime);
            
            time_granted = (int) federateTime;
            time_delta = time_granted - time_last;
            time_last = time_granted;
            dt_now = dt_now.plusSeconds(time_delta);
            day_of_week = dt_now.getDayOfWeek().getValue()-1;
            hour_of_day = dt_now.getHour();

            lmpExpected = -1; // this black magic also needs some love
            lmpReceived = 0;
            while (lmpExpected < 0 || lmpReceived < lmpExpected) {
                checkReceivedSubscriptions();
                if (lmpExpected < 0 || lmpReceived < lmpExpected) {
                    log.info("waiting to receive LMP...");
                    CpswtUtils.sleep(1000);
                    synchronized (lrc) {
                        lrc.tick();
                    }
                }
            }
	    log.debug("STATE: after LMP");
            
            if(time_granted == 0){
                currentTime += super.getStepSize();
                AdvanceTimeRequest newATR = new AdvanceTimeRequest(currentTime);
                putAdvanceTimeRequest(newATR);
                atr.requestSyncEnd();
                atr = newATR;
                continue;
            }

            // set the time-of-day schedule
            for(hvac value : hvacObjs.values()){
                if(value.change_basepoint(hour_of_day, day_of_week)){
                    House house = houses.get(value.get_house_name());
                    house.set_name(value.get_house_name());
                    house.set_cooling_setpoint(value.basepoint);
                    //house.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                }
            }
            if(bSetDefaults){
                log.debug("setting default hvac values");
                for(hvac value : hvacObjs.values()){
                    Meter meter = meters.get(value.get_meter_name());
                    meter.set_name(value.get_meter_name());
                    meter.set_bill_mode("HOURLY");
                    meter.set_monthly_fee(0.0);
                    //meter.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                    
                    House house = houses.get(value.get_house_name());
                    house.set_name(value.get_house_name());
                    house.set_thermostat_deadband(value.deadband);
                    house.set_heating_setpoint(60.0);
                    //house.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                }
                bSetDefaults = false;
            }
            if(time_granted >= tnext_bid){
                log.debug("starting bids");
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
                log.debug("aggregating bids");
                aucObj.aggregate_bids();
                
//                status.set_unresponsive_mw(aucObj.agg_unresp);
//                status.set_responsive_max_mw(aucObj.agg_resp_max);
//                status.set_responsive_c1(aucObj.agg_c1);
//                status.set_responsive_c2(aucObj.agg_c2);
//                status.set_responsive_deg(aucObj.agg_deg);
//                status.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                
                AggUn = aucObj.agg_unresp; //For Testing Purposes
                AggRespMax = aucObj.agg_resp_max; //For Testing Purposes
                AggC2 = aucObj.agg_c2; //For Testing Purposes
                AggC1 = aucObj.agg_c1; //For Testing Purposes
                AggDeg = aucObj.agg_deg; //For Testing Purposes
                
                tnext_agg += period;
            }
            if(time_granted >= tnext_clear){
                log.debug("clearing market");
                if(bWantMarket){
                    aucObj.clear_market(tnext_clear, time_granted);
                    ClearingPrice = aucObj.clearing_price; //For Testing Purposes
                    
//                    market.set_clearing_price(ClearingPrice);
//                    market.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                    
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
                log.debug("adjusting controls");
                if(bWantMarket){
                    for(hvac value : hvacObjs.values()){
                        Meter meter = meters.get(value.get_meter_name());
                        meter.set_name(value.get_meter_name());
                        meter.set_price(aucObj.clearing_price);
                        //meter.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                        if(value.bid_accepted()){
                            House house = houses.get(value.get_house_name());
                            house.set_name(value.get_house_name());
                            house.set_cooling_setpoint(value.setpoint);
                            //house.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                        }
                    }
                }
                tnext_adjust += period;
            }
            
            // update all the objects at once to avoid dropped messages at GridLAB-D
            for (Meter meter : meters.values()) {
                meter.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            }
            for (House house : houses.values()) {
                house.updateAttributeValues(getLRC(), currentTime + getLookAhead());
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

            if (currentTime >= lastLogicalTime) {
                log.debug("reached last logical time step");
                break;
            }

	    log.debug("STATE: before time request");
            if (!exitCondition) {
                currentTime += super.getStepSize();
                AdvanceTimeRequest newATR =
                    new AdvanceTimeRequest(currentTime);
                putAdvanceTimeRequest(newATR);
                atr.requestSyncEnd();
                atr = newATR;
            }
	    log.debug("STATE: after time request");
        }

        // call exitGracefully to shut down federate
        exitGracefully();

        op.close();
    }

    private void handleInteractionClass(SimTime interaction) {
        long unixTimeDuration = interaction.get_unixTimeStop() - interaction.get_unixTimeStart();
        
        logicalTimeScale = interaction.get_timeScale();
        lastLogicalTime = unixTimeDuration / logicalTimeScale;
        
        dt_now = LocalDateTime.ofInstant(Instant.ofEpochSecond(interaction.get_unixTimeStart()), TimeZone.getTimeZone(interaction.get_timeZone()).toZoneId());
        log.info("received SimTime starting at {}", dt_now.toString());
        receivedSimTime = true;
    }

    private void handleInteractionClass(DataReceipt interaction) {
        log.info("received receipt from {} for {} values at t={}", interaction.get_uniqueId(), interaction.get_dataSent(), interaction.get_logicalTime());
        lmpExpected = interaction.get_dataSent();
    }

    private void handleInteractionClass(LMP interaction) {
        log.info("received LMP as {} at t={}", interaction.get_value(), currentTime);
        aucObj.set_lmp(interaction.get_value());
        lmpReceived += 1; 
    }

    private void handleObjectClass(Substation object) {
        try {
            double refload = parse_kw(object.get_distribution_load());
            aucObj.set_refload(refload);
            log.debug("new distribution load {}", refload);
        } catch (Exception e) {
            log.error("error when receiving distribution load", e);
        }
    }

    private void handleObjectClass(Meter object) {
        // this terrible code needs to be replaced; but it can't without a serious rewrite
        // the code in execute() needs this method to modify hvacObjs
        // to modify hvacObjs its necessary to generate this key
        String name = object.get_name();
        
        String key;
        switch (namingConvention) {
            case TE30:
                // TE30 meter names have the format F1_tpm_rt_ID; house names have the format F1_house_ID
                key = "F1_house_" + name.substring(name.lastIndexOf("_")+1) + "_hvac";
                break;
            case IEEE8500:
                // IEEE8500 meter names are used as prefixes for the house names
                key = name + "_house_hvac";
                break;
            case FEEDER_GENERATOR:
                // FeederGenerator houses are basenode_hse_id while meters are basenode_mtr_id
                key = name.replace("mtr", "hse") + "_hvac";
                break;
            default:
                throw new RuntimeException("unreachable case in switch (namingConvention)");
        }
        
        if(object.get_measured_voltage_1() == null || object.get_measured_voltage_1().isEmpty()) {
            log.trace("skipped {} - voltage not set", key);
        } else {
            log.trace("received {} as V1={}", key, object.get_measured_voltage_1());
            hvacObjs.get(key).set_voltage(object.get_measured_voltage_1());
        }
    }

    private void handleObjectClass(House object) {
        // this terrible code needs to be replaced; but it can't without a serious rewrite
        // the code in execute() needs this method to modify hvacObjs
        // to modify hvacObjs its necessary to generate this key
        String key = object.get_name() + "_hvac";
        
        log.trace("received {} as Tair={} Load={} On={}", key, object.get_air_temperature(), object.get_hvac_load(), object.get_power_state());
        hvacObjs.get(key).set_air_temp(object.get_air_temperature());
        hvacObjs.get(key).set_hvac_load(object.get_hvac_load());
        hvacObjs.get(key).set_hvac_state(object.get_power_state());
    }
    
    private Map<String, Object> load_json_case(String fname)
            throws IOException {
        log.debug("loading JSON {}", fname);
        File configFile = Paths.get(fname).toFile();
        ObjectMapper mapper = new ObjectMapper();
        MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        return mapper.readValue(configFile, type);
    }
    
    private double parse_kw(String arg) {
        double p = 0.0;
        double q = 0.0;
        int nsign = 0;
        int nexp = 0;
        int ndot = 0;
        int kpos = 0;
        
        String tok = arg.replaceAll("j VA", "");
        tok = tok.replaceAll("i VA", "");
        tok = tok.replaceAll(" VA", "");
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

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            AuctionConfig federateConfig =
                federateConfigParser.parseArgs(args, AuctionConfig.class);
            Auction federate =
                new Auction(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        }
        catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
