package gov.nist.hla.te.flexibleresourcecontroller;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateParameter;

public class FlexibleResourceControllerConfig extends FederateConfig {
    @FederateParameter
    public String houseConfigurationFile;

    @FederateParameter
    public String transformerConfigurationFile;

    @FederateParameter
    public HeatPump heatPump;

    @FederateParameter
    public WaterHeater waterHeater;

    @FederateParameter
    public Battery battery;

    @FederateParameter
    public ElectricVehicle electricVehicle;

    @FederateParameter
    public CongestionDynamicPrice congestionDynamicPrice;

    public class CongestionDynamicPrice {  
        public boolean useCongestionDynamicPrice;
        public double backoffCoefficient;
        public double perMinuteIncrease;
        public long minutesBetweenUpdates;
        public long minutesAveragePower;
    }

    public class HeatPump {
        public boolean isControlled;
        public boolean useRtpAdjust;
    }

    public class WaterHeater {
        public boolean isControlled;
        public boolean useRtpAdjust;
    }

    public class Battery {
        public boolean isControlledReal;
        public boolean isControlledReactive;
        public boolean useRtpAdjust;
        // IEEE 1547 Cat.B default values for Volt Var
        public double q_set = 0.44;
        public double v_min = 0.92;
        public double v_lo  = 0.98;
        public double v_hi  = 1.02;
        public double v_max = 1.08;
        public double priceDifferentialEcon = 0.03;
        public double priceDifferentialRange = 0.01;
    }

    public class ElectricVehicle {
        public boolean useDayAheadPrice;
        public double distributionCoefficient = 3.75;
        public double distributionMean = 0.9;
        public double distributionStdDev = 1.1;
        public long seed = 0;
    }
}