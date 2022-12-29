package gov.nist.hla.te.flexibleresourcecontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.Math;

public class HouseConfiguration {
    private final static Logger log = LogManager.getLogger();

    private String id;
    private String waterheaterID;
    private String batteryID;
    private String meterID;

    private double setpoint;
    private double lambda;
    private double tau;
    private double offsetLimit;
    private double waterheater_setpoint;

    private int minuteDelay;

    HouseConfiguration(String[] data) {
        if (data == null || data.length != 12) {
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
        this.minuteDelay = Integer.parseInt(data[10]);
        this.waterheater_setpoint = Double.parseDouble(data[11]);

        String[] idParts = id.split("_hse_");
        this.waterheaterID = idParts[0] + "_wh_" + idParts[1]; // need to be able to deactive this
        this.batteryID = idParts[0] + "_ibat_" + idParts[1];
        this.meterID = idParts[0] + "_mtr_" + idParts[1];

        log.debug("house={} base_set={} peak_set={} precool_set={} precool_hours={}",
                getID(), getSetpoint(), getPeakSetpoint(), getPrecoolSetpoint(), getPrecoolHours());
        log.debug("processed house configuration for {}", id);
    }

    public String getID() {
        return id;
    }

    public String getWaterHeaterID() {
        return waterheaterID;
    }

    public String getBatteryID() {
        return batteryID;
    }

    public String getMeterID() {
        return meterID;
    }

    public double getSetpoint() {
        return setpoint;
    }

    public double getWaterHeaterSetpointMax() {
        return waterheater_setpoint;
    }

    public double getWaterHeaterSetpointMin() {
        return 120 - (1 - lambda) * 20;
    }

    public double getPeakSetpoint() {
        return setpoint + offsetLimit * (1 - lambda);
    }

    public double getPrecoolSetpoint() {
        return setpoint - offsetLimit * (1 - lambda);
    }

    public int getPrecoolHours() {
        return 2 + (int)Math.round(tau/6);
    }

    public int getMinuteDelay() {
        return minuteDelay;
    }
}
