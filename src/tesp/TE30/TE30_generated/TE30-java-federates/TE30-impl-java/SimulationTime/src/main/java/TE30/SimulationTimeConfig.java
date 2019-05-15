package TE30;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateParameter;

/**
 * Additional JSON configuration options for setting the initial values of the SimTime interaction.
 */
public class SimulationTimeConfig extends FederateConfig {
    /**
     * The simulation start time represented as a unix timestamp.
     */
    @FederateParameter
    public long unixTimeStart;
    
    /**
     * The simulation stop time represented as a unix timestamp where -1 means to run forever.
     */
    @FederateParameter
    public long unixTimeStop;
    
    /**
     * The number of simulation seconds that elapse per 1 logical time.
     */
    @FederateParameter
    public double timeScale;
    
    /**
     * The default time zone for the federation.
     */
    @FederateParameter
    public String timeZone;
}
