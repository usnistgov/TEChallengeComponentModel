package gov.nist.hla.te.matlabclient;

import java.util.ArrayList;

import gov.nist.hla.gateway.GatewayFederateConfig;
import gov.nist.hla.gateway.exception.ValueNotSet;

public class ClientConfiguration extends GatewayFederateConfig {
    private int portNumber = 1345;
    
    private int waitTimeMs = 1000;
    
    private int numberOfHouses = -1;
    
    private ArrayList<String> sendFormat = null;
    private ArrayList<String> receiveFormat = null;
    
    public void setPortNumber(int portNumber) {
        if (portNumber < 0 || portNumber > 65535) {
            throw new IllegalArgumentException("invalid port number " + portNumber);
        }
        this.portNumber = portNumber;
    }
    
    public int getPortNumber() {
        return portNumber;
    }
    
    public void setWaitTimeMs(int ms) {
        if (ms <= 0) {
            throw new IllegalArgumentException("invalid wait time " + ms);
        }
        this.waitTimeMs = ms;
    }
    
    public int getWaitTimeMs() {
        return waitTimeMs;
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
    
    public void setSendFormat(ArrayList<String> format) { // what happens if length 0 ?
        if (format == null) {
            throw new IllegalArgumentException("SendFormat cannot be null");
        }
        this.sendFormat = new ArrayList<String>(format);
    }
    
    public ArrayList<String> getSendFormat() {
        if (sendFormat == null) {
            throw new ValueNotSet("SendFormat");
        }
        return sendFormat;
    }
    
    public void setReceiveFormat(ArrayList<String> format) { // what happens if length 0 ?
        if (format == null) {
            throw new IllegalArgumentException("ReceiveFormat cannot be null");
        }
        this.receiveFormat = new ArrayList<String>(format);
    }
    
    public ArrayList<String> getReceiveFormat() {
        if (receiveFormat == null) {
            throw new ValueNotSet("ReceiveFormat");
        }
        return receiveFormat;
    }
}
