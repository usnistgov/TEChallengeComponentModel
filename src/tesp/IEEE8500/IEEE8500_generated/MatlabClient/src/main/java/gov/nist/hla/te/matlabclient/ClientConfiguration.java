package gov.nist.hla.te.matlabclient;

import java.util.ArrayList;

import gov.nist.hla.gateway.GatewayFederateConfig;
import gov.nist.hla.gateway.exception.ValueNotSet;

public class ClientConfiguration extends GatewayFederateConfig {
    private int portNumber = 1345;
    
    private int numberOfHouses = -1;
    
    private ArrayList<String> inputFormat = null;
    private ArrayList<String> outputFormat = null;
    
    private double defaultValue = 0.0;
    
    public void setPortNumber(int portNumber) {
        if (portNumber < 0 || portNumber > 65535) {
            throw new IllegalArgumentException("invalid port number " + portNumber);
        }
        this.portNumber = portNumber;
    }
    
    public int getPortNumber() {
        return portNumber;
    }
    
    public void setNumberOfHouses(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("numberOfHouses must be at least 1");
        }
        this.numberOfHouses = count;
    }
    
    public int getNumberOfHouses() {
        return numberOfHouses;
    }
    
    public void setInputFormat(ArrayList<String> format) { // what happens if length 0 ?
        if (format == null) {
            throw new IllegalArgumentException("inputFormat cannot be null");
        }
        this.inputFormat = new ArrayList<String>(format);
    }
    
    public ArrayList<String> getInputFormat() {
        if (inputFormat == null) {
            throw new ValueNotSet("inputFormat");
        }
        return inputFormat;
    }
    
    public void setOutputFormat(ArrayList<String> format) { // what happens if length 0 ?
        if (format == null) {
            throw new IllegalArgumentException("outputFormat cannot be null");
        }
        this.outputFormat = new ArrayList<String>(format);
    }
    
    public ArrayList<String> getOutputFormat() {
        if (outputFormat == null) {
            throw new ValueNotSet("outputFormat");
        }
        return outputFormat;
    }
    
    public void setDefaultValue(double value) {
        this.defaultValue = value;
    }
    
    public double getDefaultValue() {
        return defaultValue;
    }
}
