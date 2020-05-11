package gov.nist.hla;

import gov.nist.hla.gateway.GatewayFederateConfig;
import gov.nist.hla.gateway.exception.ValueNotSet;

public class FileReaderConfig extends GatewayFederateConfig {
    private String inputFilePath;
    
    private String delimiter = ",";
    
    private int waitTimeMs = 1000;
    
    public void setInputFilePath(String path) {
        this.inputFilePath = path;
    }
    
    public String getInputFilePath() {
        if (inputFilePath.isEmpty()) {
            throw new ValueNotSet("inputFilePath");
        }
        return inputFilePath;
    }
    
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
    
    public String getDelimiter() {
        return delimiter;
    }
    
    public void setWaitTimeMs(int milliseconds) {
        if (milliseconds < 0) {
            throw new RuntimeException("invalid wait time " + milliseconds);
        }
        this.waitTimeMs = milliseconds;
    }
    
    public int getWaitTimeMs() {
        return waitTimeMs;
    }
}
