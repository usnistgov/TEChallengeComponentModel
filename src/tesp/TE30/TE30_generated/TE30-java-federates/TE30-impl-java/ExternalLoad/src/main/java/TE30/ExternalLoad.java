package TE30;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.*;
import java.net.*;

import com.opencsv.CSVWriter;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.utils.CpswtUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The ExternalLoad type of federate for the federation designed in WebGME.
 *
 */
public class ExternalLoad extends ExternalLoadBase {
    private final static String subscriptions = "/load_subscriptions.txt";

    private final static double base_price = 0.02;
    
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;
    
    private boolean receivedSimTime = false;
    private boolean receivedPrice = true;
    
    private double logicalTimeScale;
    
    private double lastLogicalTime;
    
    private double price = base_price;

    private Meter meter = new Meter();
    
    private Load load = new Load();

    public String Ipaddress = "";
    public int PortNum = 0;

    public ExternalLoad(FederateConfig params) throws Exception {
        super(params);
        
        log.debug("registering meter and load objects");
        meter.registerObject(getLRC());
        load.registerObject(getLRC());
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
            if (object instanceof Market) {
                handleObjectClass((Market) object);
            }
            else {
                log.debug("unhandled object reflection: {}", object.getClassName());
            }
        }
     }

    //////////////////////////////////////////////
    // Read IPaddress and PortNumber from Config//
    //////////////////////////////////////////////

    public ExternalLoad(InputSourceConfig params) throws Exception {
        super(params);
        Ipaddress = params.IP_address;
        PortNum = params.Port_Number;
    }
        
    private void execute() throws Exception {
        if(super.isLateJoiner()) {
            log.info("turning off time regulation (late joiner)");
            currentTime = super.getLBTS() - super.getLookAhead();
            super.disableTimeRegulation();
        }

        //for real-time pricing response - can redefine on the command line
        double degF_per_price = 25.0;
        double max_delta_hi = 4.0;
        double max_delta_lo = 4.0;
        double delta = 0.0;
        double hdelta = 0.0;
        double phaseWatts = 0.0;
        double totalWatts = 0.0;
        int period = 300; //market clearing time
        int dt = 15; //step time
        int tnext_rec = period;
        int tnext_send = period;

        String header;
        String time = "0.0";
        String varname, value;
        double varValue;


        // read-in values eplus_json subscribes to from the Auction and EPlus federates
        // that is done to bypass the fncs communication in order to isolate the 
        // eplus_json federate from the experiment.
        List<List<String>> subs = read_file(subscriptions, "\t");
        
        //Output CSV file
        File csvfile = new File(ExternalLoad.class.getResource("/").toURI());
        log.debug("creating the output file {}", csvfile.getPath());
        CSVWriter op = new CSVWriter(new FileWriter(csvfile + "/ExternalLoad.csv", true));
        op.writeNext(new String[] {"t[s]","cooling_setpoint_delta","heating_setpoint_delta","power_A",
                "power_B","power_C","bill_mode","price","monthly_fee","occupants"});
        op.flush();

        /////////////////////////////////////////////
        // Create Socket                           //
        /////////////////////////////////////////////
        System.out.println("IP address is "+ Ipaddress);
        System.out.println("Port Number is "+ PortNum);
        
        InetAddress addr = InetAddress.getByName(Ipaddress);  // the address needs to be changed
        ServerSocket welcomeSocket = new ServerSocket(PortNum, 50, addr);  // 6789 is port number. Can be changed
        java.net.Socket connectionSocket = welcomeSocket.accept(); // initial connection will be made at this point
        System.out.println("connection successful");
        log.info("connection successful");
     
        InputStreamReader inFromClient = new InputStreamReader(connectionSocket.getInputStream());
        BufferedReader buffDummy = new BufferedReader(inFromClient);
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        ///////////////////////////////////////Socket

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
            
            double occupants = 0.0;

            final double federateTime = this.getCurrentTime() * logicalTimeScale;
            log.debug("current federate time is {}", federateTime);
            List<List<String>> events = new ArrayList<List<String>>();
            subs.forEach(sub->{
                if(Integer.parseInt(sub.get(0)) == (int)federateTime){
                    events.add(Arrays.asList(sub.get(0), sub.get(1), sub.get(2)));
                }
            });

            ///////////////// Former External Load Start /////////////////
            //
            // for(int i=0; i<events.size(); i++){
            //     String topic = events.get(i).get(1);
            //     if(topic.equals("electric_demand_power")){
            //         totalWatts = Double.parseDouble(events.get(i).get(2));
            //     }else if(topic.contains("occupants_")){
            //         occupants += Double.parseDouble(events.get(i).get(2));
            //     }else{
            //         log.debug(events.get(i).get(2));
            //     }
            // }
            ///////////////// Former External Load End  /////////////////

            ///////////////// Receive Data from EP Start /////////////////
            if (receivedPrice) {
                if((header = buffDummy.readLine()).equals("TERMINATE")){
                    exitCondition = true;		
                }
                time = buffDummy.readLine();
                System.out.println("in loop header=" + header + " t=" + time);
                
                while(!(varname = buffDummy.readLine()).isEmpty()) {
                    value = buffDummy.readLine();
                    System.out.println("Received: " + varname + " as " + value);
                    varValue = Double.parseDouble(value);

                    if(varname.equals("FACILITY_FACILITY_TOTAL_ELECTRICITY_DEMAND_RATE")){
                        totalWatts = varValue;
                    }                            
                }
            }
            ///////////////// Receive Data from EP End /////////////////
            
            // this is price response
            delta = degF_per_price * (price - base_price);
            if (delta < -max_delta_lo){
                delta = -max_delta_lo;
            }else if(delta > max_delta_hi){
                delta = max_delta_hi;
            }
            hdelta = -delta;

            ///////////////// Send Data to EP Start /////////////////
            if (receivedPrice) {
                outToClient.writeBytes("SET\r\n" + time + "\r\n"+ "fmuCOOL_SETP_DELTA\r\n" + delta + "\r\n" + "fmuHEAT_SETP_DELTA\r\n" + hdelta + "\r\n" + "\r\n");
                System.out.println("SET\r\n" + time +  "\r\n"+ "fmuCOOL_SETP_DELTA\r\n" + delta + "\r\n" + "fmuHEAT_SETP_DELTA\r\n" + hdelta + "\r\n" + "\r\n");
                outToClient.flush();
                receivedPrice = false;
            }
            ///////////////// Send Data to EP End /////////////////
            
            phaseWatts = totalWatts / 3.0;
            
            String bill_mode = "HOURLY";
            double monthly_fee = 25.0;
            
            if(federateTime >= tnext_send){
            	meter.set_name("Eplus_meter");
                meter.set_bill_mode(bill_mode);
                meter.set_monthly_fee(monthly_fee);
                meter.set_price(price);
                meter.updateAttributeValues(getLRC(), currentTime + this.getLookAhead());
                
                load.set_name("Eplus_load");
                load.set_constant_power_A(phaseWatts);
                load.set_constant_power_B(phaseWatts);
                load.set_constant_power_C(phaseWatts);
                load.updateAttributeValues(getLRC(), currentTime + this.getLookAhead());
                
                tnext_send += period;
            }
            
            //fncs::publish ("cooling_setpoint_delta", to_string(delta));
            //fncs::publish ("heating_setpoint_delta", to_string(-delta));
            
            op.writeNext(new String[] {""+federateTime,
                    ""+delta,               //cooling_setpoint_delta
                    ""+(-delta),            //heating_setpoint_delta
                    ""+phaseWatts,          //phase A power
                    ""+phaseWatts,          //phase B power
                    ""+phaseWatts,          //phase C power
                    ""+bill_mode,           //bill mode
                    ""+price,               //price
                    ""+monthly_fee,         //monthly fee
                    ""+occupants});         //occupants
            op.flush();
            
            if(federateTime >= tnext_rec){
            	checkReceivedSubscriptions();
            	tnext_rec += period;
            }
            
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
    }
    
    private void handleInteractionClass(SimTime interaction) {
        long unixTimeDuration = interaction.get_unixTimeStop() - interaction.get_unixTimeStart();
        
        logicalTimeScale = interaction.get_timeScale();
        lastLogicalTime = unixTimeDuration / logicalTimeScale;
        
        log.debug("received SimTime");
        receivedSimTime = true;
    }
    
    private void handleObjectClass(Market object) {
        price = object.get_clearing_price();
        log.trace("new clearing price {}", price);
        receivedPrice = true;
    }
    
    private List<List<String>> read_file(String fname, String delimiter) throws Exception
    {
        log.debug("reading the file {}", fname);
        InputStream inputStream = this.getClass().getResourceAsStream(fname);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        List<List<String>> data = new ArrayList<List<String>>();
        while(reader.ready()) {
            String line = reader.readLine();
            List<String> list = new ArrayList<String>(Arrays.asList(line.split(delimiter)));
            data.add(list);
        }
        return data;
        
        /*
        File file = new File(ExternalLoad.class.getResource(fname).toURI());
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
        */
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            InputSourceConfig federateConfig = federateConfigParser.parseArgs(args, InputSourceConfig.class);
            ExternalLoad federate = new ExternalLoad(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
