package gov.nist.hla.gridlabd;

import gov.nist.hla.gateway.GatewayFederateConfig;
import gov.nist.hla.gateway.exception.ValueNotSet;

public class GridLabDConfig extends GatewayFederateConfig {
    private String modelFilePath;
    private boolean modelFilePathSet = false;
    
    private String workingDirectory = null;
    
    private int serverPortNumber = 6267;
    
    private int waitTimeMs = 500;
    
    private boolean useSimTime = true;
    
    private long unixTimeStart;
    private boolean unixTimeStartSet = false;
    
    private long unixTimeStop;
    private boolean unixTimestopSet = false;
    
    private double simulationTimeScale;
    private boolean simulationTimeScaleSet = false;
    
    private String simulationTimeZone;
    private boolean simulationTimeZoneSet = false;

    public void setModelFilePath(String filepath) {
        this.modelFilePath = filepath;
        this.modelFilePathSet = true;
    }
    
    public String getModelFilePath() {
        if (!modelFilePathSet) {
            throw new ValueNotSet("modelFilePath");
        }
        return modelFilePath;
    }
    
    public void setWorkingDirectory(String filepath) {
        this.workingDirectory = filepath;
    }
    
    public String getWorkingDirectory() {
        return workingDirectory;
    }
    
    public void setServerPortNumber(int portNumber) {
        if (portNumber < 0 || portNumber > 65535) {
            throw new RuntimeException("invalid port number " + portNumber);
        }
        this.serverPortNumber = portNumber;
    }
    
    public int getServerPortNumber() {
        return serverPortNumber;
    }
    
    public void setWaitTimeMs(int wait) {
        if (wait < 0) {
            throw new RuntimeException("invalid wait time " + wait);
        }
        this.waitTimeMs = wait;
    }
    
    public int getWaitTimeMs() {
        return waitTimeMs;
    }
    
    public void setUseSimTime(boolean flag) {
        this.useSimTime = flag;
    }
    
    public boolean getUseSimTime() {
        return useSimTime;
    }
    
    public void setUnixTimeStart(long unixTime) {
        if (unixTime < 0) {
            throw new RuntimeException("invalid unix time start " + unixTime);
        }
        this.unixTimeStart = unixTime;
        this.unixTimeStartSet = true;
    }
    
    public long getUnixTimeStart() {
        if (!unixTimeStartSet) {
            throw new ValueNotSet("unixTimeStart");
        }
        return unixTimeStart;
    }
    
    public void setUnixTimeStop(long unixTime) {
        this.unixTimeStop = unixTime;
        this.unixTimestopSet = true;
    }
    
    public long getUnixTimeStop() {
        if (!unixTimestopSet) {
            throw new ValueNotSet("unixTimeStop");
        }
        return unixTimeStop;
    }
    
    public void setSimulationTimeScale(double scale) {
        if (scale <= 0) {
            throw new RuntimeException("invalid simulation time scale " + scale);
        }
        this.simulationTimeScale = scale;
        this.simulationTimeScaleSet = true;
    }
    
    public double getSimulationTimeScale() {
        if (!simulationTimeScaleSet) {
            throw new ValueNotSet("simulationTimeScale");
        }
        return simulationTimeScale;
    }
    
    public void setSimulationTimeZone(String timeZone) {
        this.simulationTimeZone = timeZone;
        this.simulationTimeZoneSet = true;
    }
    
    public String getSimulationTimeZone() {
        if (!simulationTimeZoneSet) {
            throw new ValueNotSet("simulationTimeZone");
        }
        return simulationTimeZone;
    }
}
