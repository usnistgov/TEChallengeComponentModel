package gov.nist.hla.te.flexibleresourcecontroller;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateParameter;

public class FlexibleResourceControllerConfig extends FederateConfig {
    @FederateParameter
    public String houseConfigurationFile;

    @FederateParameter
    public HeatPump heatPump;

    public class HeatPump {
        public boolean isControlled;
        public boolean useRtpAdjust;
    }
}