package gov.nist.hla.te.flexibleresourcecontroller;

import gov.nist.hla.te.flexibleresourcecontroller.rti.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Define the FlexibleResourceController type of federate for the federation.

public class FlexibleResourceController extends FlexibleResourceControllerBase {
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;

    private Map<String, HouseConfiguration> houses = new HashMap<String, HouseConfiguration>();

    private Map<LocalDateTime, Double> dayAheadPriceQueue = new HashMap<LocalDateTime, Double>();
    private double[] dayAheadPrice = new double[24];

    private double realTimePrice;

    ////////////////////////////////////////////////////////////////////////
    // TODO instantiate objects that must be sent every logical time step //
    ////////////////////////////////////////////////////////////////////////
    // Inverter inverter =
    //     new Inverter();
    // Waterheater waterheater =
    //     new Waterheater();
    // House house =
    //     new House();

    public FlexibleResourceController(FlexibleResourceControllerConfig params) throws Exception {
        super(params);

        final String filepath = params.houseConfigurationFile;
        final String delimiter = ","; // csv input file

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filepath))) {
            String line;

            // skip the header
            line = reader.readLine();
            if (line == null) {
                log.error("the file {} is empty", filepath);
                throw new BadFileFormat(filepath);
            }
            log.debug("house parameters header: {}", line);

            // process each line of data
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(delimiter);

                HouseConfiguration house = new HouseConfiguration(data);
                houses.put(house.getID(), house);

                // register object instances for each house
                // inverter.registerObject(getLRC());
                // waterheater.registerObject(getLRC());
                // house.registerObject(getLRC());
            }
        } catch (IOException e) {
            log.error("failed to process the file {}", filepath);
            throw new BadFileFormat(e);
        }
    }

    private void generatePlaceholderData() {
        realTimePrice = 0.03;

        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 0, 0), 0.0315);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 1, 0), 0.02835);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 2, 0), 0.02657);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 3, 0), 0.02653);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 4, 0), 0.02661);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 5, 0), 0.02822);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 6, 0), 0.03366);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 7, 0), 0.03205);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 8, 0), 0.02889);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 9, 0), 0.03308);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 10, 0), 0.0359);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 11, 0), 0.04092);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 12, 0), 0.04474);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 13, 0), 0.04947);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 14, 0), 0.05409);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 15, 0), 0.05977);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 16, 0), 0.06945);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 17, 0), 0.08462);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 18, 0), 0.11568);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 19, 0), 0.13442);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 20, 0), 0.06268);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 21, 0), 0.05117);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 22, 0), 0.04157);
        dayAheadPriceQueue.put(LocalDateTime.of(2022, 11, 15, 23, 0), 0.03792);
    }

    private void processDayAheadPrices() {
        // TODO: make atomic
        boolean[] isSet = new boolean[24];
        Arrays.fill(isSet, false);

        for (Map.Entry<LocalDateTime, Double> entry : dayAheadPriceQueue.entrySet()) {
            final int hour = entry.getKey().getHour();
            final double price = entry.getValue();
            
            if (isSet[hour]) {
                log.error("DAP for hour {} set multiple times", hour);
                throw new RuntimeException("DAP");
            }

            isSet[hour] = true;
            dayAheadPrice[hour] = price;
            log.debug("DAP=({},{})", hour, price);
        }
        for (int i = 0; i < 24; i++) {
            if (!isSet[i]) {
                log.error("DAP for hour {} is unspecified", i);
                throw new RuntimeException("DAP");
            }
        }
        dayAheadPriceQueue.clear();
    }

    private void checkReceivedSubscriptions() {
        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof SimTime) {
                handleInteractionClass((SimTime) interaction);
            }
            else if (interaction instanceof RealTimePrice) {
                handleInteractionClass((RealTimePrice) interaction);
            }
            else if (interaction instanceof DayAheadPrice) {
                handleInteractionClass((DayAheadPrice) interaction);
            }
            else {
                log.debug("unhandled interaction: {}", interaction.getClassName());
            }
        }

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof Meter) {
                handleObjectClass((Meter) object);
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
        generatePlaceholderData(); // replace

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

            ////////////////////////////////////////////////////////////
            // TODO objects that must be sent every logical time step //
            ////////////////////////////////////////////////////////////
            //    inverter.set_P_out(<YOUR VALUE HERE >);
            //    inverter.set_Q_out(<YOUR VALUE HERE >);
            //    inverter.set_name(<YOUR VALUE HERE >);
            //    inverter.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //    waterheater.set_name(<YOUR VALUE HERE >);
            //    waterheater.set_tank_setpoint(<YOUR VALUE HERE >);
            //    waterheater.set_tank_setpoint_1(<YOUR VALUE HERE >);
            //    waterheater.set_tank_setpoint_2(<YOUR VALUE HERE >);
            //    waterheater.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //    house.set_cooling_setpoint(<YOUR VALUE HERE >);
            //    house.set_name(<YOUR VALUE HERE >);
            //    house.updateAttributeValues(getLRC(), currentTime + getLookAhead());

            checkReceivedSubscriptions();

            if (!dayAheadPriceQueue.isEmpty()) {
                processDayAheadPrices();
            }

            ////////////////////////////////////////////////////////////////////
            // TODO break here if ready to resign and break out of while loop //
            ////////////////////////////////////////////////////////////////////

            if (!exitCondition) {
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

        //////////////////////////////////////////////////////////////////////
        // TODO Perform whatever cleanups are needed before exiting the app //
        //////////////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(SimTime interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(RealTimePrice interaction) {
        // TODO: check if interaction.get_time() is a reasonable value
        this.realTimePrice = interaction.get_value();
        log.info("new RTP={}", realTimePrice);
    }

    private void handleInteractionClass(DayAheadPrice interaction) {
        final LocalDateTime time = LocalDateTime.parse(interaction.get_time());
        final double price = interaction.get_value();
        dayAheadPriceQueue.put(time, price);
        log.debug("received DAP=({},{})", interaction.get_time(), price);
    }

    private void handleObjectClass(Meter object) {
        //////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object //
        //////////////////////////////////////////////////////////
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            FlexibleResourceControllerConfig federateConfig =
                federateConfigParser.parseArgs(args, FlexibleResourceControllerConfig.class);
            FlexibleResourceController federate =
                new FlexibleResourceController(federateConfig);
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
