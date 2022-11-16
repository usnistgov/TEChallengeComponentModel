package gov.nist.hla.te.flexibleresourcecontroller;

import gov.nist.hla.te.flexibleresourcecontroller.rti.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.utils.CpswtUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Define the FlexibleResourceController type of federate for the federation.

public class FlexibleResourceController extends FlexibleResourceControllerBase {
    private final static Logger log = LogManager.getLogger();

    private boolean receivedSimTime = false;

    private double currentTime = 0;

    private double logicalTimeScale;
    private LocalDateTime scenarioTime;

    private Map<String, HouseConfiguration> houseConfigurations = new HashMap<String, HouseConfiguration>();

    private Map<LocalDateTime, Double> dayAheadPriceQueue = new HashMap<LocalDateTime, Double>();
    private double[] dayAheadPrice = new double[24];

    private double realTimePrice;
    private double peakDayAheadPrice;

    private int peakHour;

    private boolean isPeakWindow = false;
    private int peakWindowStart;
    private int peakWindowEnd;

    private Set<String> precoolHouseSet = new HashSet<String>();

    private Map<String, House> houses = new HashMap<String, House>();

    ////////////////////////////////////////////////////////////////////////
    // TODO instantiate objects that must be sent every logical time step //
    ////////////////////////////////////////////////////////////////////////
    // Inverter inverter =
    //     new Inverter();
    // Waterheater waterheater =
    //     new Waterheater();

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

                HouseConfiguration houseConfiguration = new HouseConfiguration(data);
                houseConfigurations.put(houseConfiguration.getID(), houseConfiguration);

                House house = new House();
                house.registerObject(getLRC());
                houses.put(houseConfiguration.getID(), house);

                // inverter.registerObject(getLRC());
                // waterheater.registerObject(getLRC());
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

    private void startNewDay() {
        // TODO: check data structures
        this.peakHour = 0;
        this.peakDayAheadPrice = dayAheadPrice[0];
        for (int i = 1; i < 24; i++) {
            if (dayAheadPrice[i] > peakDayAheadPrice) {
                this.peakHour = i;
                this.peakDayAheadPrice = dayAheadPrice[i];
            }
        }
        log.info("peak hour is {} with price={}", peakHour, peakDayAheadPrice);

        this.peakWindowStart = 0;
        double maxWindow = 0;
        for (int i = 0; i < 22; i++) {
            double window = dayAheadPrice[i] + dayAheadPrice[i+1] + dayAheadPrice[i+2];
            if (window > maxWindow) {
                this.peakWindowStart = i;
                maxWindow = window;
            }
        }
        this.peakWindowEnd = peakWindowStart + 3;
        log.info("peak price window is [{},{}) with price sum of {}", peakWindowStart, peakWindowEnd, maxWindow);
    }

    private void incrementScenarioTime() {
        final double scenarioTimeDelta = this.getStepSize() * logicalTimeScale;
        scenarioTime = scenarioTime.plusSeconds((long)scenarioTimeDelta);
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

            checkReceivedSubscriptions();

            if (!dayAheadPriceQueue.isEmpty()) {
                processDayAheadPrices();
            }

            if (scenarioTime.getMinute() == 0) { // new hour
                final int currentHour = scenarioTime.getHour();
                log.info("new hour = {}", currentHour);

                if (currentHour == 0) {
                    startNewDay(); // make sure initial condition works
                }

                // heat pump control
                if (currentHour >= peakWindowStart && currentHour < peakWindowEnd) {
                    if (!isPeakWindow) {
                        log.info("started peak window at t={}", scenarioTime.toString());
                        precoolHouseSet.clear();
                    }
                    isPeakWindow = true;
                } else if (currentHour == peakWindowEnd) {
                    log.info("ended peak window at t={}", scenarioTime.toString());
                    isPeakWindow = false;
                } else if (currentHour < peakWindowStart) {
                    for (HouseConfiguration house : houseConfigurations.values()) {
                        final String id = house.getID();

                        if (!precoolHouseSet.contains(id) && currentHour >= peakWindowStart - house.getPrecoolHours()) {
                            log.debug("started precooling house {} at hour {}", id, currentHour);
                            precoolHouseSet.add(id);
                        }
                    }
                }
            }

            // heat pump setpoints
            if (scenarioTime.getMinute() % 5 == 0) { // TODO: make configurable
                for (HouseConfiguration houseConfiguration : houseConfigurations.values()) {
                    double setpoint;

                    if (isPeakWindow) {
                        setpoint = houseConfiguration.getPeakSetpoint();
                    } else if (precoolHouseSet.contains(houseConfiguration.getID())) {
                        setpoint = houseConfiguration.getPrecoolSetpoint();
                    } else {
                        setpoint = houseConfiguration.getSetpoint();
                    }

                    double priceRatio = realTimePrice / peakDayAheadPrice;
                    if (priceRatio >= 2) {
                        setpoint = houseConfiguration.getPeakSetpoint() + 1;
                    } else if (priceRatio > 1) {
                        double rtp_adjust = (priceRatio-1)*(houseConfiguration.getPeakSetpoint() - setpoint + 1);
                        setpoint = setpoint + rtp_adjust;
                    }

                    House house = houses.get(houseConfiguration.getID());
                    house.set_name(houseConfiguration.getID());
                    house.set_cooling_setpoint(setpoint);
                    house.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                    log.debug("house {} setpoint is {}", houseConfiguration.getID(), setpoint);
                }
            }

            ////////////////////////////////////////////////////////////////////
            // TODO break here if ready to resign and break out of while loop //
            ////////////////////////////////////////////////////////////////////

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

        //////////////////////////////////////////////////////////////////////
        // TODO Perform whatever cleanups are needed before exiting the app //
        //////////////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(SimTime interaction) {
        logicalTimeScale = interaction.get_timeScale();
        
        scenarioTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(interaction.get_unixTimeStart()), TimeZone.getTimeZone(interaction.get_timeZone()).toZoneId());
        log.info("received SimTime starting at {}", scenarioTime.toString());
        receivedSimTime = true;
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
