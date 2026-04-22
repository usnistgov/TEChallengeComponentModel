package gov.nist.hla.te.useragent;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateParameter;

public class UserAgentConfig extends FederateConfig {
    @FederateParameter
    public String houseFilePath;

    @FederateParameter
    public String vehicleFilePath;

    @FederateParameter
    public Battery battery;

    @FederateParameter
    public ElectricVehicle electricVehicle;

    @FederateParameter
    public double acceptablePriceDifference;

    public class Battery {
        public double capacity = 13.5;
        public double maxChargeRate = 5.0;
    }

    public class ElectricVehicle {
        public double distributionCoefficient = 3.75;
        public double distributionMean = 0.9;
        public double distributionStdDev = 1.1;
    }
}
