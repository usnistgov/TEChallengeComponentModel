package gov.nist.hla.te.flexibleresourcecontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HouseConfiguration {
    private final static Logger log = LogManager.getLogger();

    HouseConfiguration(String[] data) {
        if (data == null || data.length != 10) {
            throw new RuntimeException("invalid house configuration");
        }

        // data[0]; // house id
        // data[1]; // cooling setpoint
        // data[2]; // mass temperature
        // data[3]; // air temperature
        // data[4]; // lambda (comfort parameter)
        // data[5]; // tau (thermal time constant)
        // data[6]; // UA
        // data[7]; // offset limit
        // data[8]; // ramp
        // data[9]; // deadband
    }
}
