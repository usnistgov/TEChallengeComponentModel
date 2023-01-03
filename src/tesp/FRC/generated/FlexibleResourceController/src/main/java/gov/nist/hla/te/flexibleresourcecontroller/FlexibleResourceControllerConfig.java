package gov.nist.hla.te.flexibleresourcecontroller;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateParameter;

public class FlexibleResourceControllerConfig extends FederateConfig {
    @FederateParameter
    public String houseConfigurationFile;

    @FederateParameter
    public HeatPump heatPump;

    @FederateParameter
    public WaterHeater waterHeater;

    @FederateParameter
    public Battery battery;

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
    }
}