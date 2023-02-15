package gov.nist.hla.te.flexibleresourcecontroller;

import gov.nist.hla.te.flexibleresourcecontroller.rti.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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

    // IEEE 1547 Cat.B default values for Volt Var
    private final static double Q_SET = 0.44;
    private final static double V_MIN = 0.92;
    private final static double V_LO  = 0.98;
    private final static double V_HI  = 1.02;
    private final static double V_MAX = 1.08;

    private boolean receivedSimTime = false;
    private boolean receivedInitialPrices = false;
    private boolean firstTimeStep = true;

    private double currentTime = 0;

    private double logicalTimeScale;
    private ZonedDateTime scenarioTime;

    private Map<String, HouseConfiguration> houseConfigurations = new HashMap<String, HouseConfiguration>();

    private Map<ZonedDateTime, Double> dayAheadPriceQueue = new HashMap<ZonedDateTime, Double>();
    private double[] dayAheadPrice = new double[24];

    private double realTimePrice;
    private double peakDayAheadPrice;

    private int peakHour;
    private ZonedDateTime peakTime;

    private Map<String, House> houses = new HashMap<String, House>();
    private Map<String, Inverter> inverters = new HashMap<String, Inverter>();
    private Map<String, Waterheater> waterheaters = new HashMap<String, Waterheater>();
    private Map<String, Double> voltages = new HashMap<String, Double>();

    private boolean heatPumpActive;
    private boolean heatPumpRtpAdjust;

    private boolean waterHeaterActive;
    private boolean waterHeaterRtpAdjust;

    private boolean batteryActiveReal;
    private boolean batteryActiveReactive;
    private boolean batteryRtpAdjust;

    public FlexibleResourceController(FlexibleResourceControllerConfig params) throws Exception {
        super(params);

        String status;

        heatPumpActive = params.heatPump.isControlled;
        heatPumpRtpAdjust = params.heatPump.useRtpAdjust;

        if (!heatPumpActive) {
            status = "OFFLINE";
        } else if (!heatPumpRtpAdjust) {
            status = "DAY_AHEAD";
        } else {
            status = "REAL_TIME";
        }
        log.info("heat pump control is {}", status);

        waterHeaterActive = params.waterHeater.isControlled;
        waterHeaterRtpAdjust = params.waterHeater.useRtpAdjust;

        if (!waterHeaterActive) {
            status = "OFFLINE";
        } else if (!waterHeaterRtpAdjust) {
            status = "DAY_AHEAD";
        } else {
            status = "REAL_TIME";
        }
        log.info("water heater control is {}", status);

        batteryActiveReal = params.battery.isControlledReal;
        batteryActiveReactive = params.battery.isControlledReactive;
        batteryRtpAdjust = params.battery.useRtpAdjust;

        if (!batteryActiveReal) {
            status = "OFFLINE";
        } else if (!batteryRtpAdjust) {
            status = "DAY_AHEAD";
        } else {
            status = "REAL_TIME";
        }
        if (batteryActiveReactive) {
            status = status + " with reactive power control";
        }
        log.info("battery control is {}", status);


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

                // TODO: check if the house has a battery
                Inverter inverter = new Inverter();
                inverter.registerObject(getLRC());
                inverters.put(houseConfiguration.getBatteryID(), inverter);

                Waterheater waterheater = new Waterheater();
                waterheater.registerObject(getLRC());
                waterheaters.put(houseConfiguration.getWaterHeaterID(), waterheater);
            }
        } catch (IOException e) {
            log.error("failed to process the file {}", filepath);
            throw new BadFileFormat(e);
        }
    }

    private void processDayAheadPrices() {
        // TODO: make atomic
        boolean[] isSet = new boolean[24];
        Arrays.fill(isSet, false);

        for (Map.Entry<ZonedDateTime, Double> entry : dayAheadPriceQueue.entrySet()) {
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
        peakTime = ZonedDateTime.of(scenarioTime.toLocalDate(), LocalTime.of(peakHour,30), scenarioTime.getZone());

        // this.peakWindowStart = 0;
        // double maxWindow = 0;
        // for (int i = 0; i < 22; i++) {
        //     double window = dayAheadPrice[i] + dayAheadPrice[i+1] + dayAheadPrice[i+2];
        //     if (window > maxWindow) {
        //         this.peakWindowStart = i;
        //         maxWindow = window;
        //     }
        // }
        // this.peakWindowEnd = peakWindowStart + 3;
        // log.info("peak price window is [{},{}) with price sum of {}", peakWindowStart, peakWindowEnd, maxWindow);
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

    private boolean receivedDayAheadPrices() {
        return dayAheadPriceQueue.size() == 24; // this should check the hours as well
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

        while (!receivedSimTime || !receivedInitialPrices) {
            if (!receivedSimTime) {
                log.info("waiting to receive SimTime...");
            } else {
                log.info("waiting to receive initial prices...");
            }
            synchronized (lrc) {
                lrc.tick();
            }
            checkReceivedSubscriptions();
            receivedInitialPrices = receivedDayAheadPrices();
            if (!receivedSimTime || !receivedInitialPrices) {
                CpswtUtils.sleep(1000);
            }
        }

        processDayAheadPrices();
        startNewDay();

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

            checkReceivedSubscriptions();

            if (!dayAheadPriceQueue.isEmpty()) {
                processDayAheadPrices();
            }

            if (scenarioTime.getMinute() == 0) { // new hour
                final int currentHour = scenarioTime.getHour();
                log.info("new hour = {}", currentHour);

                if (currentHour == 0 && currentTime > 0) {
                    // prevent double execution if midnight start
                    // should make this instead based on current stored day
                    startNewDay();
                }
            }

            // heat pump control
            if (heatPumpActive) {
                for (HouseConfiguration houseConfiguration : houseConfigurations.values()) {
                    double setpoint;

                    double peak_width = 0.75 * houseConfiguration.getPrecoolMinutes();
                    ZonedDateTime peak_start = peakTime.minusMinutes((int)(peak_width/2));
                    ZonedDateTime peak_end = peakTime.plusMinutes((int)(peak_width/2));
                    ZonedDateTime precool_start = peak_start.minusMinutes(houseConfiguration.getPrecoolMinutes());

                    if (scenarioTime.isBefore(precool_start) || scenarioTime.isAfter(peak_end)) {
                        setpoint = houseConfiguration.getSetpoint();
                    } else if (scenarioTime.isBefore(peak_start)) {
                        setpoint = houseConfiguration.getPrecoolSetpoint();
                        log.debug("HEATPUMP {} PRECOOL @ {}", houseConfiguration.getID(), setpoint);
                    } else {
                        setpoint = houseConfiguration.getPeakSetpoint();
                        log.debug("HEATPUMP {} PEAK @ {}", houseConfiguration.getID(), setpoint);
                    }

                    if (heatPumpRtpAdjust) {
                        double priceRatio = realTimePrice / peakDayAheadPrice;
                        if (priceRatio >= 2) {
                            setpoint = houseConfiguration.getPeakSetpoint() + 1;
                            log.debug("HEATPUMP {} ADJUST @ {}", houseConfiguration.getID(), setpoint);
                        } else if (priceRatio > 1) {
                            double rtp_adjust = (priceRatio-1)*(houseConfiguration.getPeakSetpoint() - setpoint + 1);
                            setpoint = setpoint + rtp_adjust;
                            log.debug("HEATPUMP {} ADJUST @ {}", houseConfiguration.getID(), setpoint);
                        }
                    }

                    House house = houses.get(houseConfiguration.getID());
                    house.set_name(houseConfiguration.getID());
                    house.set_cooling_setpoint(setpoint);
                    house.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                    log.trace("house {} setpoint is {}", houseConfiguration.getID(), setpoint);
                }
            } else if (firstTimeStep) {
                for (HouseConfiguration houseConfiguration : houseConfigurations.values()) {
                    House house = houses.get(houseConfiguration.getID());
                    house.set_name(houseConfiguration.getID());
                    house.set_cooling_setpoint(houseConfiguration.getSetpoint());
                    house.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                    log.trace("house {} setpoint is {}", houseConfiguration.getID(), houseConfiguration.getSetpoint());
                }
            }

            // waterheater control
            if (waterHeaterActive) {
                ZonedDateTime morningSwitchTime = ZonedDateTime.of(scenarioTime.toLocalDate(), LocalTime.of(2,0), scenarioTime.getZone());
                ZonedDateTime afternoonSwitchTime = ZonedDateTime.of(scenarioTime.toLocalDate(), LocalTime.of(12,0), scenarioTime.getZone());
                for (HouseConfiguration houseConfiguration : houseConfigurations.values()) {
                    double tank_setpoint;

                    ZonedDateTime morningSwitchTimeAdjusted = morningSwitchTime.plusMinutes(houseConfiguration.getMinuteDelay());
                    ZonedDateTime afternoonSwitchTimeAdjusted = afternoonSwitchTime; // should this also plusMinutes?

                    if (scenarioTime.isBefore(morningSwitchTimeAdjusted) || scenarioTime.isAfter(afternoonSwitchTimeAdjusted)) {
                        tank_setpoint = houseConfiguration.getWaterHeaterSetpointMin();
                    } else {
                        tank_setpoint = houseConfiguration.getWaterHeaterSetpointMax();
                    }

                    if (waterHeaterRtpAdjust) {
                        double priceRatio = realTimePrice / peakDayAheadPrice;
                        if (priceRatio > 2) {
                            tank_setpoint = 90; // GLD lower bound
                            log.debug("WATERHEATER {} ADJUST @ {}", houseConfiguration.getID(), tank_setpoint);
                        }
                    }

                    Waterheater waterheater = waterheaters.get(houseConfiguration.getWaterHeaterID());
                    waterheater.set_name(houseConfiguration.getWaterHeaterID());
                    waterheater.set_tank_setpoint(tank_setpoint);
                    waterheater.set_lower_tank_setpoint(tank_setpoint);
                    waterheater.set_upper_tank_setpoint(tank_setpoint);
                    waterheater.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                }
            }

            // battery control
            // TODO: determine if house has battery ?
            // TODO: what happens if a simulation starts mid-charge?
            ZonedDateTime chargeStartTime = ZonedDateTime.of(scenarioTime.toLocalDate(), LocalTime.of(1,0), scenarioTime.getZone());
            ZonedDateTime dischargePeakTime = ZonedDateTime.of(scenarioTime.toLocalDate(), LocalTime.of(peakHour,30), scenarioTime.getZone());
            for (HouseConfiguration houseConfiguration : houseConfigurations.values()) {
                String id = houseConfiguration.getBatteryID();
                double p_out = 0;
                double q_out = 0;

                if (batteryActiveReal) {
                    if (scenarioTime.getHour() >= 1 && scenarioTime.getHour() < 8) { // charge window (negative p_out values)
                        ZonedDateTime actualStartTime = chargeStartTime.plusMinutes(houseConfiguration.getMinuteDelay());
                        long elapsedMinutes = Duration.between(actualStartTime, scenarioTime).toMinutes();

                        if (elapsedMinutes < 0 || elapsedMinutes >= 270) {
                            p_out = 0;
                        } else if (elapsedMinutes >= 30) { // ramp down
                            final double deltaPerMinute = 4800.0/240; // 4.8 kW change over 240 minutes
                            p_out = -(4800 - (elapsedMinutes - 30) * deltaPerMinute);
                            log.debug("BATTERY {} CHARGE @ {}", houseConfiguration.getID(), p_out);
                        } else { // ramp up
                            final double deltaPerMinute = 4800.0/30; // 4.8 kW change over 30 minutes
                            p_out = -(elapsedMinutes * deltaPerMinute);
                            log.debug("BATTERY {} CHARGE @ {}", houseConfiguration.getID(), p_out);
                        }
                    } else { // discharge possible
                        final double deltaPerMinute = 3600.0/180; // 3.6 kW change over 180 minutes
                        long elapsedMinutes = Duration.between(scenarioTime, dischargePeakTime).toMinutes();

                        if (elapsedMinutes == 0) { // peak
                            p_out = 3600;
                            log.debug("BATTERY {} DISCHARGE @ {}", houseConfiguration.getID(), p_out);
                        } else if (elapsedMinutes > 0 && elapsedMinutes <= 180) { // ramp up
                            p_out = (180 - elapsedMinutes) * deltaPerMinute;
                            log.debug("BATTERY {} DISCHARGE @ {}", houseConfiguration.getID(), p_out);
                        } else if (elapsedMinutes < 0 && elapsedMinutes >= -180) { // ramp down
                            p_out = 3600 + elapsedMinutes * deltaPerMinute;
                            log.debug("BATTERY {} DISCHARGE @ {}", houseConfiguration.getID(), p_out);
                        }

                        // RTP Adjust
                        if (batteryRtpAdjust) {
                            double priceRatio = realTimePrice / peakDayAheadPrice;
                            if (1 < priceRatio && priceRatio < 2) {
                                p_out = p_out + (priceRatio - 1)*(5000 - p_out);
                                log.debug("BATTERY {} ADJUST @ {}", houseConfiguration.getID(), p_out);
                            } else if (priceRatio >= 2) {
                                p_out = 5000;
                                log.debug("BATTERY {} ADJUST @ {}", houseConfiguration.getID(), p_out);
                            }
                        }
                    }
                }

                // Volt Var
                if (batteryActiveReactive) {
                    if (voltages.containsKey(houseConfiguration.getMeterID())) {
                        double voltage = voltages.get(houseConfiguration.getMeterID());
                        double v_pu = voltage / 120.0; // 120 = nominal voltage
                        double q_pu = 0;

                        if (V_LO <= v_pu && v_pu <= V_HI) {
                            q_pu = 0;
                        } else if (V_MIN <= v_pu && v_pu < V_LO) {
                            q_pu = Q_SET * (1 - (v_pu - V_MIN)/(V_LO - V_MIN));
                        } else if (V_HI < v_pu && v_pu <= V_MAX) {
                            q_pu = -Q_SET * (1 - (V_MAX - v_pu)/(V_MAX - V_HI));
                        } else if (v_pu < V_MIN) {
                            q_pu = Q_SET;
                        } else if (v_pu > V_MAX) {
                            q_pu = -Q_SET;
                        }
                        q_out = q_pu * 5000; // volts
                    } else {
                        log.warn("no meter voltage available for {}", houseConfiguration.getMeterID());
                    }
                }

                // TODO: should this be prevented if real or reactive are disabled?
                Inverter inverter = inverters.get(id);
                inverter.set_name(id);
                inverter.set_P_Out(p_out);
                inverter.set_Q_Out(q_out);
                inverter.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                log.trace("id={} p={} q={}", id, p_out, q_out);
            }

            firstTimeStep = false;

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
        
        scenarioTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(interaction.get_unixTimeStart()), TimeZone.getTimeZone(interaction.get_timeZone()).toZoneId());
        log.info("received SimTime starting at {}", scenarioTime.toString());
        receivedSimTime = true;
    }

    private void handleInteractionClass(RealTimePrice interaction) {
        // TODO: check if interaction.get_time() is a reasonable value
        this.realTimePrice = interaction.get_value();
        log.info("new RTP={}", realTimePrice);
    }

    private void handleInteractionClass(DayAheadPrice interaction) {
        final ZonedDateTime time = ZonedDateTime.parse(interaction.get_time());
        final double price = interaction.get_value();
        dayAheadPriceQueue.put(time, price);
        log.debug("received DAP=({},{})", interaction.get_time(), price);
    }

    private void handleObjectClass(Meter object) {
        final String name = object.get_name();
        final String voltageComplex = object.get_measured_voltage_1();
        
        if (voltageComplex != null && !voltageComplex.isEmpty()) { // format: +123+456j V // either + can be -
            // String complexParts[] = voltageComplex.substring(1, voltageComplex.indexOf('j')).split("[-+]");

            // double realPart = Double.parseDouble(complexParts[0]);
            // double complexPart = Double.parseDouble(complexParts[1]);
            // double voltageMagnitude = Math.sqrt(realPart * realPart + complexPart * complexPart);

            double voltageMagnitude = Double.parseDouble(voltageComplex.substring(1).split("[-+]")[0]);

            voltages.put(name, voltageMagnitude);
            log.trace("magnitude={} for {}", voltageMagnitude, voltageComplex);
        } else {
            log.warn("received unusable voltage for meter {}: {}", name, voltageComplex);
        }
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
