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
import java.util.Random;
import java.util.TimeZone;

import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.utils.CpswtUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.math3.distribution.LogNormalDistribution;

// Define the FlexibleResourceController type of federate for the federation.

public class FlexibleResourceController extends FlexibleResourceControllerBase {
    class VehicleChargeProfile {
        public double charge_amount;

        public double ramp_up_rate;
        public double ramp_down_rate;
        public double max_charge_output;

        public int ramp_up_minutes;
        public int ramp_down_minutes;
        public int max_charge_minutes;

        public ZonedDateTime charge_start_time;
        public ZonedDateTime charge_end_time;
    }

    enum ChargeState {
        BASELINE,
        CONGESTION,
        NO_CONGESTION
    }

    private final static Logger log = LogManager.getLogger();

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
    private Map<String, Inverter> vehicles = new HashMap<String, Inverter>(); // represented as inverters
    private Map<String, Double> voltages = new HashMap<String, Double>();

    private String transformerConfigurationFile;
    private Map<String, TransformerDetails> transformers = new HashMap<String, TransformerDetails>();

    private boolean useCongestionControl;

    private boolean heatPumpActive;
    private boolean heatPumpRtpAdjust;

    private boolean waterHeaterActive;
    private boolean waterHeaterRtpAdjust;

    private boolean batteryActiveReal;
    private boolean batteryActiveReactive;
    private boolean batteryRtpAdjust;

    private boolean electricVehicleActive;
    private boolean electricVehicleUseDap;

    private double q_set;
    private double v_min;
    private double v_lo;
    private double v_hi;
    private double v_max;

    private long cdp_n_avg;
    private long cdp_n_check;
    private double cdp_n_backoff;
    private double cdp_pmi;

    private double evChargeCoefficient;
    private LogNormalDistribution evChargeDistribution;
    private Map<String, VehicleChargeProfile> vehicleChargeProfiles = new HashMap<String, VehicleChargeProfile>();

    private Map<String, Double> vehicleCharge = new HashMap<String, Double>();
    private Map<String, ChargeState> vehicleChargeState = new HashMap<String, ChargeState>();
    private Map<String, ZonedDateTime> vehicleCongestionStart = new HashMap<String, ZonedDateTime>();

    private Map<String, Double> batteryCharge = new HashMap<String, Double>();
    private Map<String, ChargeState> batteryChargeState = new HashMap<String, ChargeState>();
    private Map<String, ZonedDateTime> batteryCongestionStart = new HashMap<String, ZonedDateTime>();

    private Random random = new Random();

    public FlexibleResourceController(FlexibleResourceControllerConfig params) throws Exception {
        super(params);

        String status;

        useCongestionControl = params.congestionDynamicPrice.useCongestionDynamicPrice;
        cdp_n_avg = params.congestionDynamicPrice.minutesAveragePower;
        cdp_n_check = params.congestionDynamicPrice.minutesBetweenUpdates;
        cdp_n_backoff = params.congestionDynamicPrice.backoffCoefficient;
        cdp_pmi = params.congestionDynamicPrice.perMinuteIncrease;

        heatPumpActive = params.heatPump.isControlled;
        heatPumpRtpAdjust = params.heatPump.useRtpAdjust;

        if (!heatPumpActive) {
            status = "OFFLINE";
        } else if (useCongestionControl) {
            status = "CONGESTION_DYNAMIC_PRICE";
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
        } else if (useCongestionControl) {
            status = "CONGESTION_DYNAMIC_PRICE";
        } else if (!waterHeaterRtpAdjust) {
            status = "DAY_AHEAD";
        } else {
            status = "REAL_TIME";
        }
        log.info("water heater control is {}", status);

        batteryActiveReal = params.battery.isControlledReal;
        batteryActiveReactive = params.battery.isControlledReactive;
        batteryRtpAdjust = params.battery.useRtpAdjust;

        q_set = params.battery.q_set;
        v_min = params.battery.v_min;
        v_lo  = params.battery.v_lo;
        v_hi  = params.battery.v_hi;
        v_max = params.battery.v_max;

        if (!batteryActiveReal) {
            status = "OFFLINE";
        } else if (useCongestionControl) {
            status = "CONGESTION_DYNAMIC_PRICE";
        } else if (!batteryRtpAdjust) {
            status = "DAY_AHEAD";
        } else {
            status = "REAL_TIME";
        }
        if (batteryActiveReactive) {
            status = status + " with reactive power control";
        }
        log.info("battery control is {}", status);

        if (batteryActiveReactive) {
            log.info("VOLT-VAR Qset = {} Vmin = {} Vlo = {} Vhi = {} Vmax = {}", q_set, v_min, v_lo, v_hi, v_max);
        }

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

                // TODO: better method to find vehicles
                if (houseConfiguration.hasVehicle()) {
                    Inverter vehicle = new Inverter();
                    vehicle.registerObject(getLRC());
                    vehicles.put(houseConfiguration.getVehicleID(), vehicle);
                    log.debug("registered electric vehicle {}", houseConfiguration.getVehicleID());
                }
            }
        } catch (IOException e) {
            log.error("failed to process the file {}", filepath);
            throw new BadFileFormat(e);
        }

        transformerConfigurationFile = params.transformerConfigurationFile;

        double mu = params.electricVehicle.distributionMean;
        double sigma = params.electricVehicle.distributionStdDev;
        evChargeCoefficient = params.electricVehicle.distributionCoefficient;
        evChargeDistribution = new LogNormalDistribution(mu, sigma);

        // TODO : ADJUST FOR CDP!
        electricVehicleActive = !vehicles.isEmpty();
        if (electricVehicleActive) {
            if (useCongestionControl) {
                status = "CONGESTION_DYNAMIC_PRICE";
            } else if (params.electricVehicle.useDayAheadPrice) {
                electricVehicleUseDap = true;
                status = "DAP_RESPONSIVE";
            } else {
                status = "BASELINE";
            }
        } else {
            status = "DISABLED";
        }
        log.info("electric vehicle charging is {}", status);

        if (params.electricVehicle.seed != 0) {
            random.setSeed(params.electricVehicle.seed);
            log.info("using seed {} for random number generation", params.electricVehicle.seed);
        }
    }

    // assumptions:
    //  initializeTransformers is called after SimTime is received
    //  logicalTimeScale doesn't change after initializeTransformers is called
    private void initializeTransformers() {
        final String filepath = transformerConfigurationFile;
        final String delimiter = ","; // csv input file

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filepath))) {
            String line;

            // skip the header
            line = reader.readLine();
            if (line == null) {
                log.error("the file {} is empty", filepath);
                throw new BadFileFormat(filepath);
            }
            log.debug("transformer configuration header: {}", line);

            // process each line of data
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(delimiter);

                TransformerDetails transformer = new TransformerDetails(data, cdp_n_avg, logicalTimeScale);
                transformers.put(transformer.getName(), transformer);
                log.trace("initialized transformer: {}", transformer.getName());
            }
        } catch (IOException e) {
            log.error("failed to process the file {}", filepath);
            throw new BadFileFormat(e);
        }
        log.info("initialized {} transformers", transformers.size());
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

        // reset battery state (only relevant to charging from 1 a.m. to 12 p.m.)
        for (HouseConfiguration houseConfiguration : houseConfigurations.values()) {
            String id = houseConfiguration.getBatteryID();
            batteryCharge.put(id, 0.0);
            batteryChargeState.put(id, ChargeState.BASELINE);
            batteryCongestionStart.clear();
        }

        // reset batteries somehow ??

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

    private void resetVehicles() {
        vehicleCharge.clear();
        vehicleChargeState.clear();
        vehicleChargeProfiles.clear();
        vehicleCongestionStart.clear();
        log.debug("cleared existing vehicle charge profiles");

        for (String vehicleID : vehicles.keySet()) {
            double chargeAmount = generateChargeAmount();
            double chargeTimeProbability = random.nextDouble();
            double chargeOffsetMultiplier = random.nextDouble();

            VehicleChargeProfile profile = new VehicleChargeProfile();
            profile.charge_amount = chargeAmount;
            
            if (electricVehicleUseDap || useCongestionControl) { // dap responsive and congestion control
                if (chargeTimeProbability < 0.25) { // DAY CHARGE
                    if (chargeAmount > 43.2) {
                        chargeAmount = 43.2;
                    }
                    profile.ramp_up_minutes = 60;
                    profile.ramp_down_minutes = 60;
                    profile.max_charge_minutes = 300;

                    profile.ramp_up_rate = chargeAmount / 6; // kW/h
                    profile.ramp_down_rate = chargeAmount / 6; // kW/h
                    profile.max_charge_output = chargeAmount / 6; // kW

                    profile.charge_end_time = ZonedDateTime.of(scenarioTime.toLocalDate(), LocalTime.of(17,0), scenarioTime.getZone()); // extended window for congestion control
                    profile.charge_start_time = ZonedDateTime.of(scenarioTime.toLocalDate(), LocalTime.of(9,0), scenarioTime.getZone());
                } else { // NIGHT CHARGE
                    if (chargeAmount > 54) {
                        chargeAmount = 54;
                    }
                    profile.ramp_up_minutes = 180;
                    profile.ramp_down_minutes = 60;
                    profile.max_charge_minutes = 300;

                    profile.ramp_up_rate = (chargeAmount / 7) / 3; // kW/h
                    profile.ramp_down_rate = (chargeAmount / 7); // kW/h
                    profile.max_charge_output = chargeAmount / 7; // kW

                    profile.charge_end_time = ZonedDateTime.of(scenarioTime.toLocalDate().plusDays(1), LocalTime.of(7,0), scenarioTime.getZone());
                    profile.charge_start_time = ZonedDateTime.of(scenarioTime.toLocalDate(), LocalTime.of(22,0), scenarioTime.getZone());
                }
            } else { // baseline
                profile.ramp_up_rate = 21.6; // kW/h
                profile.ramp_down_rate = 3.6; // kW/h
                profile.max_charge_output = 7.2; // kW

                final double ramp_up_amount = (profile.max_charge_output * (profile.max_charge_output / profile.ramp_up_rate)) / 2;        
                final double ramp_down_amount = (profile.max_charge_output * (profile.max_charge_output / profile.ramp_down_rate)) / 2;

                double ramp_up_minutes = 0;
                double ramp_down_minutes = 0;
                double max_charge_minutes = 0;

                if (profile.charge_amount < ramp_up_amount) {
                    ramp_up_minutes = 60 * Math.sqrt(2 * profile.charge_amount / profile.ramp_up_rate);
                    ramp_down_minutes = 0;
                    max_charge_minutes = 0;
                } else if (profile.charge_amount < ramp_up_amount + ramp_down_amount) {
                    ramp_up_minutes = 60 * profile.max_charge_output / profile.ramp_up_rate;

                    double a = profile.ramp_down_rate / 2;
                    double b = -profile.max_charge_output;
                    double c = profile.charge_amount - ramp_up_amount;

                    double t1 = (-b + Math.sqrt(b*b - 4*a*c))/(2*a);
                    double t2 = (-b - Math.sqrt(b*b - 4*a*c))/(2*a);

                    if (t1 > 0 && (t2 < 0 || t1 < t2)) {
                        ramp_down_minutes = 60 * t1;
                    } if (t2 > 0) {
                        ramp_down_minutes = 60 * t2;
                    } else {
                        // oops
                    }

                    max_charge_minutes = 0;
                } else {
                    ramp_up_minutes = 60 * profile.max_charge_output / profile.ramp_up_rate;
                    ramp_down_minutes = 60 * profile.max_charge_output / profile.ramp_down_rate;
                    max_charge_minutes = 60 * (profile.charge_amount - ramp_up_amount - ramp_down_amount) / profile.max_charge_output;
                }

                // TODO - fix the charge amount to match the rounded values
                profile.ramp_up_minutes = (int)Math.ceil(ramp_up_minutes);
                profile.ramp_down_minutes = (int)Math.ceil(ramp_down_minutes);
                profile.max_charge_minutes = (int)Math.ceil(max_charge_minutes);
                final int charge_duration = profile.ramp_up_minutes + profile.ramp_down_minutes + profile.max_charge_minutes;

                if (chargeTimeProbability < 0.25) { // DAY CHARGE
                    final int max_minutes = 7 * 60; // [10:00, 17:00) Window

                    profile.charge_end_time = ZonedDateTime.of(scenarioTime.toLocalDate(), LocalTime.of(17,0), scenarioTime.getZone());
                    profile.charge_start_time = ZonedDateTime.of(scenarioTime.toLocalDate(), LocalTime.of(10,0), scenarioTime.getZone());
                    if (charge_duration > max_minutes) {
                        profile.max_charge_minutes = max_minutes - profile.ramp_up_minutes - profile.ramp_down_minutes;
                    } else if (charge_duration < max_minutes) {
                        long chargeOffset = Math.round(chargeOffsetMultiplier * (max_minutes - charge_duration));
                        profile.charge_start_time = profile.charge_start_time.plusMinutes(chargeOffset);
                    }
                } else if (chargeTimeProbability < 0.85 && !useCongestionControl) { // EVENING CHARGE
                    final int max_minutes = 14 * 60; // [17:00, 7:00) Window Possible
                    final int max_minutes_preferred = 6 * 60; // [17:00, 23:00) Window Preferred

                    profile.charge_end_time = ZonedDateTime.of(scenarioTime.toLocalDate().plusDays(1), LocalTime.of(7,0), scenarioTime.getZone());
                    profile.charge_start_time = ZonedDateTime.of(scenarioTime.toLocalDate(), LocalTime.of(17,0), scenarioTime.getZone());
                    if (charge_duration > max_minutes) {
                        profile.max_charge_minutes = max_minutes - profile.ramp_up_minutes - profile.ramp_down_minutes;
                    } else if (charge_duration > max_minutes_preferred && charge_duration < max_minutes) {
                        long chargeOffset = Math.round(chargeOffsetMultiplier * (max_minutes - charge_duration));
                        profile.charge_start_time = profile.charge_start_time.plusMinutes(chargeOffset);
                    } else if (charge_duration < max_minutes_preferred) {
                        long chargeOffset = Math.round(chargeOffsetMultiplier * (max_minutes_preferred - charge_duration));
                        profile.charge_start_time = profile.charge_start_time.plusMinutes(chargeOffset);
                    }
                } else { // NIGHT CHARGE
                    final int max_minutes = 8 * 60; // [23:00, 7:00) Window

                    profile.charge_end_time = ZonedDateTime.of(scenarioTime.toLocalDate().plusDays(1), LocalTime.of(7,0), scenarioTime.getZone());
                    profile.charge_start_time = ZonedDateTime.of(scenarioTime.toLocalDate(), LocalTime.of(23,0), scenarioTime.getZone());
                    if (charge_duration > max_minutes) {
                        profile.max_charge_minutes = max_minutes - profile.ramp_up_minutes - profile.ramp_down_minutes;
                    } else if (charge_duration < max_minutes) {
                        long chargeOffset = Math.round(chargeOffsetMultiplier * (max_minutes - charge_duration));
                        profile.charge_start_time = profile.charge_start_time.plusMinutes(chargeOffset);
                    }
                }
            }

            log.info("EV_PROFILE {} t={} amount={} ramp_up={} ramp_down={} max={}", vehicleID, profile.charge_start_time, profile.charge_amount, profile.ramp_up_minutes, profile.ramp_down_minutes, profile.max_charge_minutes);
            vehicleChargeProfiles.put(vehicleID, profile);
            vehicleChargeState.put(vehicleID, ChargeState.BASELINE);
            vehicleCharge.put(vehicleID, 0.0);
        }
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
            } else if (object instanceof Transformer) {
                handleObjectClass((Transformer) object);
            }
            else {
                log.debug("unhandled object reflection: {}", object.getClassName());
            }
        }
    }

    private boolean receivedDayAheadPrices() {
        return dayAheadPriceQueue.size() == 24; // this should check the hours as well
    }

    private double generateChargeAmount() { // uses p in [0, 1) which may affect results
        return evChargeCoefficient * evChargeDistribution.inverseCumulativeProbability(random.nextDouble());
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

        initializeTransformers(); // does this lose the first power value ?
        processDayAheadPrices();
        startNewDay();
        resetVehicles(); // OK if this is called twice (starting hour = 8)

        // update charge profiles to reflect overnight charges from prior day
        if (scenarioTime.getHour() < 8) {
            for (Map.Entry<String, VehicleChargeProfile> entry : vehicleChargeProfiles.entrySet()) {
                VehicleChargeProfile modifiedProfile = entry.getValue();
                modifiedProfile.charge_start_time = modifiedProfile.charge_start_time.minusDays(1);
                modifiedProfile.charge_end_time = modifiedProfile.charge_end_time.minusDays(1);
                entry.setValue(modifiedProfile);
                log.info("EV_PROFILE {} UPDATED t_start={} t_end={}", entry.getKey(), modifiedProfile.charge_start_time, modifiedProfile.charge_end_time);
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

                if (currentHour == 8) {
                    resetVehicles();
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

                    double priceRatio = 0;

                    if (useCongestionControl) {
                        final double dap = dayAheadPrice[scenarioTime.getHour()];
                        if (transformers.containsKey(houseConfiguration.getTransformerID())) {
                            final double cdp = transformers.get(houseConfiguration.getTransformerID()).getMPrice() * dap;
                            priceRatio = cdp / peakDayAheadPrice;
                        } else {
                            log.warn("failed to calculate CDP: transformer {} does not exist", houseConfiguration.getTransformerID());
                        }
                    } else if (heatPumpRtpAdjust) {
                        priceRatio = realTimePrice / peakDayAheadPrice;
                    }

                    if (priceRatio >= 2) {
                        setpoint = houseConfiguration.getPeakSetpoint() + 1;
                        log.debug("HEATPUMP {} ADJUST @ {}", houseConfiguration.getID(), setpoint);
                    } else if (priceRatio > 1) {
                        setpoint = setpoint + (priceRatio-1)*(houseConfiguration.getPeakSetpoint() - setpoint + 1);
                        log.debug("HEATPUMP {} ADJUST @ {}", houseConfiguration.getID(), setpoint);
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

                    double priceRatio = 0;

                    if (useCongestionControl) {
                        final double dap = dayAheadPrice[scenarioTime.getHour()];
                        if (transformers.containsKey(houseConfiguration.getTransformerID())) {
                            final double cdp = transformers.get(houseConfiguration.getTransformerID()).getMPrice() * dap;
                            priceRatio = cdp / peakDayAheadPrice;
                        } else {
                            log.warn("failed to calculate CDP: transformer {} does not exist", houseConfiguration.getTransformerID());
                        }
                    } else if (waterHeaterRtpAdjust) {
                        priceRatio = realTimePrice / peakDayAheadPrice;
                    }

                    if (priceRatio > 2) {
                        tank_setpoint = 90; // GLD lower bound
                        log.debug("WATERHEATER {} ADJUST @ {}", houseConfiguration.getID(), tank_setpoint);
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

                Inverter inverter = inverters.get(id);

                double p_out = 0;
                double q_out = 0;

                if (batteryActiveReal) {
                    boolean isDischargePossible = false;

                    final double lambda = houseConfiguration.getLambda();
                    final double mPrice = transformers.get(houseConfiguration.getTransformerID()).getMPrice();

                    if (batteryChargeState.get(id) == ChargeState.BASELINE) {
                        ZonedDateTime actualStartTime = chargeStartTime.plusMinutes(houseConfiguration.getMinuteDelay());
                        long elapsedMinutes = Duration.between(actualStartTime, scenarioTime).toMinutes();

                        if (elapsedMinutes < 0 || elapsedMinutes >= 270) { // outside charge window
                            p_out = 0;
                            isDischargePossible = true;
                        } else if (useCongestionControl && (mPrice > 1 + lambda / 2)) { // switch to congestion control
                            double e = 1 - (1 + lambda / 2) / mPrice;
                            p_out = (1 - cdp_n_backoff * e) * inverter.get_P_Out();
                            batteryChargeState.put(id, ChargeState.CONGESTION);
                            batteryCongestionStart.put(id, scenarioTime);
                        } else if (elapsedMinutes >= 30) { // ramp down
                            final double deltaPerMinute = 4800.0/240; // 4.8 kW change over 240 minutes
                            p_out = -(4800 - (elapsedMinutes - 30) * deltaPerMinute);
                        } else { // ramp up
                            final double deltaPerMinute = 4800.0/30; // 4.8 kW change over 30 minutes
                            p_out = -(elapsedMinutes * deltaPerMinute);
                        }
                    } else {
                        if (scenarioTime.getHour() >= 10 || batteryCharge.get(id) >= 10.8) { //kWh
                            p_out = 0;
                            isDischargePossible = true;
                        } else if (Duration.between(scenarioTime, batteryCongestionStart.get(id)).toMinutes() % cdp_n_check == 0) {
                            if (mPrice > 1 + lambda / 2) {
                                double e = 1 - (1 + lambda / 2) / mPrice;
                                p_out = (1 - cdp_n_backoff * e) * inverter.get_P_Out();
                                batteryChargeState.put(id, ChargeState.CONGESTION);
                            } else if (batteryChargeState.get(id) == ChargeState.CONGESTION) {
                                p_out = inverter.get_P_Out();
                                batteryChargeState.put(id, ChargeState.NO_CONGESTION);
                            } else {
                                double kw_increase = cdp_n_check * cdp_pmi;
                                p_out = -Math.min(-inverter.get_P_Out() + 1000 * kw_increase, 5000); // W
                            }
                        } else {
                            p_out = inverter.get_P_Out();
                        }
                    }

                    if (isDischargePossible) {
                        final double deltaPerMinute = 3600.0/180; // 3.6 kW change over 180 minutes
                        long elapsedMinutes = Duration.between(scenarioTime, dischargePeakTime).toMinutes();

                        if (elapsedMinutes == 0) { // peak
                            p_out = 3600;
                        } else if (elapsedMinutes > 0 && elapsedMinutes <= 180) { // ramp up
                            p_out = (180 - elapsedMinutes) * deltaPerMinute;
                        } else if (elapsedMinutes < 0 && elapsedMinutes >= -180) { // ramp down
                            p_out = 3600 + elapsedMinutes * deltaPerMinute;
                        }

                        double priceRatio = 0;

                        if (useCongestionControl) {
                            final double dap = dayAheadPrice[scenarioTime.getHour()];
                            if (transformers.containsKey(houseConfiguration.getTransformerID())) {
                                final double cdp = transformers.get(houseConfiguration.getTransformerID()).getMPrice() * dap;
                                priceRatio = cdp / peakDayAheadPrice;
                            } else {
                                log.warn("failed to calculate CDP: transformer {} does not exist", houseConfiguration.getTransformerID());
                            }
                        } else if (batteryRtpAdjust) {
                            priceRatio = realTimePrice / peakDayAheadPrice;
                        }

                        if (priceRatio >= 2) {
                            p_out = 5000;
                            log.debug("BATTERY {} ADJUST @ {}", houseConfiguration.getID(), p_out);
                        } else if (priceRatio > 1) {
                            p_out = p_out + (priceRatio - 1)*(5000 - p_out);
                            log.debug("BATTERY {} ADJUST @ {}", houseConfiguration.getID(), p_out);
                        } 
                    }
                }

                // Volt Var
                if (batteryActiveReactive) {
                    if (voltages.containsKey(houseConfiguration.getMeterID())) {
                        double voltage = voltages.get(houseConfiguration.getMeterID());
                        double v_pu = voltage / 120.0; // 120 = nominal voltage
                        double q_pu = 0;

                        if (v_lo <= v_pu && v_pu <= v_hi) {
                            q_pu = 0;
                        } else if (v_min <= v_pu && v_pu < v_lo) {
                            q_pu = q_set * (1 - (v_pu - v_min)/(v_lo - v_min));
                        } else if (v_hi < v_pu && v_pu <= v_max) {
                            q_pu = -q_set * (1 - (v_max - v_pu)/(v_max - v_hi));
                        } else if (v_pu < v_min) {
                            q_pu = q_set;
                        } else if (v_pu > v_max) {
                            q_pu = -q_set;
                        }
                        q_out = q_pu * 5000; // volts
                        log.trace("INVERTER {} : V = {} v_pu = {} q_pu = {} q_out = {}", houseConfiguration.getBatteryID(), voltage, v_pu, q_pu, q_out);
                    } else {
                        log.warn("no meter voltage available for {}", houseConfiguration.getMeterID());
                    }
                }

                if (Math.sqrt(p_out * p_out + q_out * q_out) > 5000) {
                    int sign = (p_out > 0 ? 1 : -1);
                    double newPOut = sign * Math.sqrt(5000 * 5000 - q_out * q_out);


                    log.info("PQ Request Magnitude > 5000 : p_out reduced from {} to {} for INVERTER {}", p_out, newPOut, id);
                    p_out = newPOut;
                }

                if (p_out < 0) {
                    // calculate total charge (effective next time step)
                    double charge_delta = (-p_out / 1000) * logicalTimeScale / 3600; // kWh
                    batteryCharge.put(id, batteryCharge.get(id) + charge_delta);
                    log.debug("BATTERY {}: P_OUT = {} W, TOTAL_CHARGE = {} kWh", houseConfiguration.getID(), p_out, batteryCharge.get(id));
                } else if (p_out > 0) {
                    log.debug("BATTERY {}: P_OUT = {} W", houseConfiguration.getID(), p_out);
                }

                // TODO: should this be prevented if real or reactive are disabled?
                inverter.set_name(id);
                inverter.set_P_Out(p_out);
                inverter.set_Q_Out(q_out);
                inverter.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                log.trace("id={} p={} q={}", id, p_out, q_out);
            }

            // electric vehicle control
            for (Map.Entry<String, VehicleChargeProfile> entry : vehicleChargeProfiles.entrySet()) {
                final String id = entry.getKey();
                final VehicleChargeProfile profile = entry.getValue();
                
                final int charge_duration = profile.ramp_up_minutes + profile.ramp_down_minutes + profile.max_charge_minutes;
                final long elapsedMinutes = Duration.between(profile.charge_start_time, scenarioTime).toMinutes();

                final String houseID = HouseConfiguration.getHouseFromVehicle(id);
                final double lambda = houseConfigurations.get(houseID).getLambda();

                final String transformerID = houseConfigurations.get(houseID).getTransformerID();
                final double mPrice = transformers.get(transformerID).getMPrice();
                
                Inverter inverter = vehicles.get(id);
                double p_out = 0;

               if (vehicleChargeState.get(id) == ChargeState.BASELINE) {
                    if (elapsedMinutes < 0 || elapsedMinutes >= charge_duration) { // outside of charge window
                        p_out = 0;
                    } else if (useCongestionControl && (mPrice > 1 + lambda / 2)) { // switch to congestion control
                        double e = 1 - (1 + lambda / 2) / mPrice;
                        p_out = (1 - cdp_n_backoff * e) * inverter.get_P_Out();
                        vehicleChargeState.put(id, ChargeState.CONGESTION);
                        vehicleCongestionStart.put(id, scenarioTime);
                    } else if (elapsedMinutes < profile.ramp_up_minutes) { // ramp up window
                        p_out = -1000 * (profile.ramp_up_rate / 60 * elapsedMinutes);
                    } else if (elapsedMinutes < profile.ramp_up_minutes + profile.max_charge_minutes) { // constant charge window
                        p_out = -1000 * (profile.max_charge_output);
                    } else if (elapsedMinutes < profile.ramp_up_minutes + profile.max_charge_minutes + profile.ramp_down_minutes) { // ramp down window
                        final long relevantMinutes = elapsedMinutes - profile.max_charge_minutes - profile.ramp_up_minutes;
                        p_out = -1000 * (profile.max_charge_output - profile.ramp_down_rate / 60 * relevantMinutes);
                    } else { // should be unreachable
                        log.warn("unexpected condition in electric vehicle control");
                    }
                } else {
                    if (scenarioTime.isAfter(profile.charge_end_time) || vehicleCharge.get(id) >= profile.charge_amount) { //kWh
                        p_out = 0;
                    } else if (Duration.between(scenarioTime, vehicleCongestionStart.get(id)).toMinutes() % cdp_n_check == 0) {                    
                        if (mPrice > 1 + lambda / 2) {
                            double e = 1 - (1 + lambda / 2) / mPrice;
                            p_out = (1 - cdp_n_backoff * e) * inverter.get_P_Out();
                            vehicleChargeState.put(id, ChargeState.CONGESTION);
                        } else if (vehicleChargeState.get(id) == ChargeState.CONGESTION) {
                            p_out = inverter.get_P_Out();
                            vehicleChargeState.put(id, ChargeState.NO_CONGESTION);
                        } else {
                            double kw_increase = cdp_n_check * cdp_pmi;
                            p_out = -Math.min(-inverter.get_P_Out() + 1000 * kw_increase, 1000 * profile.max_charge_output); // W
                        }
                    } else {
                        p_out = inverter.get_P_Out();
                    }
                }

                if (p_out < 0) {
                    // calculate total charge (effective next time step)
                    double charge_delta = (-p_out / 1000) * logicalTimeScale / 3600; // kWh
                    vehicleCharge.put(id, vehicleCharge.get(id) + charge_delta);
                    log.info("VEHICLE {}: P_OUT = {} W, TOTAL_CHARGE = {} kWh", id, p_out, vehicleCharge.get(id));
                }
                
                inverter.set_name(id);
                inverter.set_P_Out(p_out);
                inverter.updateAttributeValues(getLRC(), currentTime + getLookAhead());
                log.trace("id={} p={} charge={}", id, p_out, vehicleCharge.get(id));
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

    private void handleObjectClass(Transformer object) {
        final String name = object.get_name();
        final String powerComplex = object.get_power_in();

        if (powerComplex != null && !powerComplex.isEmpty()) { // format: +123+456j V where either + can be -
            String complexParts[] = powerComplex.substring(1, powerComplex.indexOf('j')).split("[-+]");

            if (transformers.containsKey(name)) {
                transformers.get(name).setRealPower(Double.parseDouble(complexParts[0]));
                log.trace("set {} real_power to {} ({})", name, complexParts[0], powerComplex);
            } else {
                log.warn("received update for unknown transformer {}", name);
            }
        } else {
            log.warn("received unusable power_in for transformer {}: {}", name, powerComplex);
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
