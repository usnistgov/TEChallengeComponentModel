package gov.nist.hla.te.flexibleresourcecontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.Math;

public class HouseConfiguration {
    private final static Logger log = LogManager.getLogger();

    private String id;
    private double setpoint;
    private double lambda;
    private double tau;
    private double offsetLimit;

    HouseConfiguration(String[] data) {
        if (data == null || data.length != 10) {
            throw new RuntimeException("invalid house configuration");
        }

        this.id = data[0]; // house id
        this.setpoint = Double.parseDouble(data[1]); // cooling setpoint
        // data[2]; // mass temperature
        // data[3]; // air temperature
        this.lambda = Double.parseDouble(data[4]); // lambda (comfort parameter)
        this.tau = Double.parseDouble(data[5]); // tau (thermal time constant)
        // data[6]; // UA
        this.offsetLimit = Double.parseDouble(data[7]); // offset limit
        // data[8]; // ramp
        // data[9]; // deadband

        log.debug("house={} base_set={} peak_set={} precool_set={} precool_hours={}",
                getID(), getSetpoint(), getPeakSetpoint(), getPrecoolSetpoint(), getPrecoolHours());
        log.debug("processed house configuration for {}", id);
    }

    public String getID() {
        return id;
    }

    public double getSetpoint() {
        return setpoint;
    }

    public double getPeakSetpoint() {
        return setpoint + offsetLimit * (1 - lambda);
    }

    public double getPrecoolSetpoint() {
        return setpoint - offsetLimit * (1 - lambda);
    }

    public int getPrecoolHours() {
        return 2 + (int)Math.ceil(tau/6); // should this be ceiling?
    }
}
