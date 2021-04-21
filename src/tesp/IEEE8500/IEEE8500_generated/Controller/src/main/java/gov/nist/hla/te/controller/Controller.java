package gov.nist.hla.te.controller;

import gov.nist.hla.te.controller.rti.*;

import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.utils.CpswtUtils;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

// Define the Controller type of federate for the federation.

public class Controller extends ControllerBase {
    private final static Logger log = LogManager.getLogger();

    private boolean receivedSimTime = false;

    private double currentTime = 0;

    private double logicalTimeScale;

    private ControllerConfig config;

    private LocalDateTime scenarioTime;
    
    private List<HouseData> houseData;
    
    private Map<String, House> houses = new HashMap<String, House>();

    public Controller(ControllerConfig params) throws Exception {
        super(params);
        config = params;
        initializeHouseData();
        registerHouseObjects();
    }

    private void checkReceivedSubscriptions() {
        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof SimTime) {
                handleInteractionClass((SimTime) interaction);
            }
            else if (interaction instanceof LoadForecast) {
                handleInteractionClass((LoadForecast) interaction);
            }
            else if (interaction instanceof DataReceipt) {
                handleInteractionClass((DataReceipt) interaction);
            }
            else if (interaction instanceof Tender) {
                handleInteractionClass((Tender) interaction);
            }
            else {
                log.debug("unhandled interaction: {}", interaction.getClassName());
            }
        }

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof RealTimePrice) {
                handleObjectClass((RealTimePrice) object);
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

            log.info("t = {} / {}", this.getCurrentTime(), scenarioTime.toString());

            if (scenarioTime.getMinute() % 5 == 0) {
                log.info("sending new set points to GridLAB-D");
                updateSetPoints();
            }

            checkReceivedSubscriptions();

            if (!exitCondition) {
                incrementScenarioTime();
                currentTime += super.getStepSize();
                AdvanceTimeRequest newATR =
                    new AdvanceTimeRequest(currentTime);
                putAdvanceTimeRequest(newATR);
                atr.requestSyncEnd();
                atr = newATR;
            }
        }

        // call exitGracefully to shut down federate
        exitGracefully();
    }

    private void handleInteractionClass(SimTime interaction) {
        logicalTimeScale = interaction.get_timeScale();
        
        scenarioTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(interaction.get_unixTimeStart()), TimeZone.getTimeZone(interaction.get_timeZone()).toZoneId());
        log.info("received SimTime starting at {}", scenarioTime.toString());
        receivedSimTime = true;
    }

    private void handleInteractionClass(LoadForecast interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(DataReceipt interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(Tender interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(RealTimePrice object) {
        //////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object //
        //////////////////////////////////////////////////////////
    }

    private void handleObjectClass(House object) {
        //////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object //
        //////////////////////////////////////////////////////////
    }

    private void initializeHouseData() throws IOException {
        this.houseData = new LinkedList<HouseData>();
        
        log.debug("parsing {}", config.configFileName);
        CSVReader reader = new CSVReader(new FileReader(config.configFileName));        
        String[] nextLine;
        
        try {
            // skip the header row
            if (reader.readNext() == null) {
                log.error("input file {} contains no data", config.configFileName);
                throw new IOException("bad input file");
            }
            
            // read the data row by row
            while ((nextLine = reader.readNext()) != null) {
                final String name = nextLine[0];
                final double deadband = Double.parseDouble(nextLine[1]);
                
                HouseData nextHouseData = new HouseData(name, deadband);
                for (int i = 2; i < nextLine.length; i++) {
                    nextHouseData.add(nextLine[i], config.isFahrenheit);
                }
                log.debug("parsed house {} with {} data points", nextLine[0], nextLine.length-2);
                houseData.add(nextHouseData);
            }
        } catch (CsvValidationException e) {
            throw new IOException(e);
        }
    }
    
    private void registerHouseObjects() {
        for (HouseData data : houseData) {
            House newHouse = new House();
            newHouse.registerObject(getLRC());
            houses.put(data.getName(), newHouse);
        }
    }
    
    private void updateSetPoints() {
        for (HouseData data : houseData) {
            // the values for heating setpoint and deadband are (unfortunately) required because VU object instances
            // provide default values for uninitialized attributes. the deadband and heating setpoint values have been
            // set to the same values as the double auction market
            House house = houses.get(data.getName());
            house.set_name(data.getName());
            house.set_cooling_setpoint(data.remove());
            house.set_heating_setpoint(60.0);
            house.set_thermostat_deadband(data.getDeadband());
            house.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            log.debug("house {} set to {}", house.get_name(), house.get_cooling_setpoint());
        }
    }
    
    private void incrementScenarioTime() {
        final double scenarioTimeDelta = this.getStepSize() * logicalTimeScale;
        scenarioTime = scenarioTime.plusSeconds((long)scenarioTimeDelta);
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            ControllerConfig federateConfig =
                federateConfigParser.parseArgs(args, ControllerConfig.class);
            Controller federate =
                new Controller(federateConfig);
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
