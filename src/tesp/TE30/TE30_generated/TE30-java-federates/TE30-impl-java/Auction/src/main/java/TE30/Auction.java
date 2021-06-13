package TE30;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.opencsv.CSVWriter;

import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.utils.CpswtUtils;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
    
/**
 * The Auction type of federate for the federation designed in WebGME.
 *
 */
public class Auction extends AuctionBase {    
    private final static Logger log = LogManager.getLogger();

    private String config;
    
    private double currentTime = 0;
    
    private boolean ieee8500NamingScheme; // toggle between TE30 and IEEE8500 naming conventions

    private boolean receivedSimTime = false;
    
    private double logicalTimeScale;
    
    private double lastLogicalTime;
    
    private LocalDateTime dt_now;
    
    private SimpleAuction aucObj;
    
    private Map<String, hvac> hvacObjs = new HashMap<String, hvac>();

    private Market market = new Market();
    
    private PhysicalStatus status = new PhysicalStatus();
    
    private Map<String, House> houses = new HashMap<String, House>();
    
    private Map<String, Meter> meters = new HashMap<String, Meter>();

    public Auction(AuctionConfig params) throws Exception {
        super(params);
        
        config = params.configFileName;
        ieee8500NamingScheme = params.ieee8500;
    }

    private void checkReceivedSubscriptions() {
        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof SimTime) {
                handleInteractionClass((SimTime) interaction);
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
            else if (object instanceof LMP) {
                handleObjectClass((LMP) object);
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
        String[] house_names = new String[hvac_keys.length];
        for(int i=0; i<hvac_keys.length; i++){
            String key = (String) hvac_keys[i];
            Map<String, Object> row = (Map<String, Object>)((Map<String, Object>) dict.get("controllers")).get(key);
            hvac newHvac = new hvac(row, key, aucObj);
            hvacObjs.put(key, newHvac);
            
            // register the HLA objects
            House newHouse = new House();
            newHouse.registerObject(getLRC());
            houses.put(newHvac.get_house_name(), newHouse);
            house_names[i] = newHvac.get_house_name();
            
            Meter newMeter = new Meter();
            newMeter.registerObject(getLRC());
            meters.put(newHvac.get_meter_name(), newMeter);
        }
        
        log.trace("starting initialization");
        
        // register the market HLA object
        market.registerObject(getLRC());
        status.registerObject(getLRC());
        
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
        File csvfile = new File(Auction.class.getResource("/").toURI());
        CSVWriter op = new CSVWriter(new FileWriter(csvfile + "/" + "auction.csv", true));
        op.writeNext(new String[] {"t[s]", "bWantMarket", "bSetDefaults", "Unresponsive_mw",
                "Responsive_max_mw", "Responsive_c2", "Responsive_c1", "Responsive_deg",
                "Clear_Price", "Clear_Type"});
        op.flush();

        CSVWriter hout = new CSVWriter(new FileWriter(csvfile + "/" + "house.csv", true));
        String[] houseCsvHeader = new String[house_names.length + 1];
        houseCsvHeader[0] = "t[s]";
        for(int i=0; i<house_names.length; i++){
            houseCsvHeader[i+1] = house_names[i];
        }
        hout.writeNext(houseCsvHeader, true);
        hout.flush();
        
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
            
            checkReceivedSubscriptions();

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
                
                status.set_unresponsive_mw(aucObj.agg_unresp);
                status.set_responsive_max_mw(aucObj.agg_resp_max);
                status.set_responsive_c1(aucObj.agg_c1);
                status.set_responsive_c2(aucObj.agg_c2);
                status.set_responsive_deg(aucObj.agg_deg);
                status.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                
                AggUn = aucObj.agg_unresp; //For Testing Purposes
                AggRespMax = aucObj.agg_resp_max; //For Testing Purposes
                AggC2 = aucObj.agg_c2; //For Testing Purposes
                AggC1 = aucObj.agg_c1; //For Testing Purposes
                AggDeg = aucObj.agg_deg; //For Testing Purposes
                
                tnext_agg += period;
            }
            if(time_granted >= tnext_clear){
                if(bWantMarket){
                    aucObj.clear_market(tnext_clear, time_granted);
                    ClearingPrice = aucObj.clearing_price; //For Testing Purposes
                    
                    market.set_clearing_price(ClearingPrice);
                    market.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                    
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
                String[] csvoutput = new String[house_names.length + 1];
                csvoutput[0] = String.valueOf(time_granted);
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
                            int houseIndex = Arrays.asList(house_names).indexOf(value.get_house_name());
                            csvoutput[houseIndex+1] = String.valueOf(value.setpoint);
                        }
                    }
                }
                hout.writeNext(csvoutput, true);
                hout.flush();
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

        op.close();
        hout.close();
    }

    private void handleInteractionClass(SimTime interaction) {
        long unixTimeDuration = interaction.get_unixTimeStop() - interaction.get_unixTimeStart();
        
        logicalTimeScale = interaction.get_timeScale();
        lastLogicalTime = unixTimeDuration / logicalTimeScale;
        
        //dt_now = LocalDateTime.ofInstant(Instant.ofEpochSecond(interaction.get_unixTimeStart()), TimeZone.getDefault().toZoneId());
        dt_now = LocalDateTime.ofInstant(Instant.ofEpochSecond(interaction.get_unixTimeStart()), TimeZone.getTimeZone("America/Los_Angeles").toZoneId());
        
        log.debug("received SimTime");
        receivedSimTime = true;
    }

    private void handleObjectClass(Substation object) {
        try {
            double refload = parse_kw(object.get_distribution_load());
            aucObj.set_refload(refload);
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
        if (ieee8500NamingScheme) {
            // IEEE8500 meter names are used as prefixes for the house names
            key = name + "_house_hvac";
        } else {
            // TE30 meter names have the format F1_tpm_rt_ID; house names have the format F1_house_ID
            key = "F1_house_" + name.substring(name.lastIndexOf("_")+1) + "_hvac";
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
    
    private void handleObjectClass(LMP object) {
        log.trace("received LMP as {}", object.get_lmp());
        aucObj.set_lmp(object.get_lmp());
    }
    
    private Map<String, Object> load_json_case(String fname) throws Exception
    {
        log.debug("loading JSON {}", fname);
        InputStream inputStream = this.getClass().getResourceAsStream(fname);
        String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        return mapper.readValue(json, type);
    }
    
    private double parse_kw(String arg) throws Exception
    {
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
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            AuctionConfig federateConfig = federateConfigParser.parseArgs(args, AuctionConfig.class);
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
