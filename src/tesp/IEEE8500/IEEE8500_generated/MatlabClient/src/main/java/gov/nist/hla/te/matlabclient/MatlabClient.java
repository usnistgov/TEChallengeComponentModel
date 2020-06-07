package gov.nist.hla.te.matlabclient;

import java.io.File;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.file.Paths;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.nist.hla.gateway.GatewayCallback;
import gov.nist.hla.gateway.GatewayFederate;
import gov.nist.hla.te.matlabclient.exception.ConfigurationException;
import gov.nist.hla.te.matlabclient.exception.DuplicateKeyException;
import gov.nist.hla.te.matlabclient.exception.ObjectModelException;
import hla.rti.AttributeNotOwned;
import hla.rti.FederateNotExecutionMember;
import hla.rti.InvalidFederationTime;
import hla.rti.NameNotFound;
import hla.rti.ObjectClassNotPublished;
import hla.rti.ObjectNotKnown;

public class MatlabClient implements GatewayCallback {
    private static final Logger log = LogManager.getLogger();

    private ClientConfiguration configuration;
    
    private GatewayFederate gateway;
    
    private ClientRunnable client;
    
    private double[] sendBuffer = null;
    
    private double[] receiveBuffer = null;
    
    private Map<String, Integer> sendBufferOffset = new HashMap<String, Integer>();
    
    private Map<String, Integer> receiveBufferOffset = new HashMap<String, Integer>();
    
    Map<String, Integer> houseNameToHandle = new HashMap<String, Integer>();
    
    private Map<String, String> registeredObjectNames = new HashMap<String, String>();
    
    public static void main(String[] args)
            throws IOException {
        if (args.length != 1) {
            log.error("missing command line argument for JSON configuration file");
            return;
        }
        
        ClientConfiguration config = MatlabClient.readConfiguration(args[0]);
        MatlabClient client = new MatlabClient(config);
        client.run();
    }
    
    private static ClientConfiguration readConfiguration(String filepath)
            throws IOException {
        log.info("reading JSON configuration file at " + filepath);
        File configFile = Paths.get(filepath).toFile();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(configFile, ClientConfiguration.class);
    }
    
    public MatlabClient(ClientConfiguration configuration) {
        this.configuration = configuration;
        this.gateway = new GatewayFederate(configuration, this);
        this.client = new ClientRunnable(configuration.getPortNumber());
    }
    
    public void run() {
        gateway.run();
    }
    
    public void initializeSelf() {
        log.trace("initializeSelf");
        initializeSendBuffer();
        initializeReceiveBuffer();
    }

    public void initializeWithPeers() {
        log.trace("initializeWithPeers");
        client.start();
    }
    
    public void doTimeStep(Double timeStep) {
        log.trace("doTimeStep {}", timeStep);
        
        try {
            synchronizeWithClient();
        } catch (InterruptedException e) {
            log.error("time step " + timeStep + " failed", e);
            return; // is there a better way to handle this?
        }
        
        processReceiveBuffer();
    }

    public void prepareToResign() {
        log.trace("prepareToResign");
        // do nothing
    }

    public void receiveInteraction(Double timeStep, String className, Map<String, String> parameters) {
        log.trace("receiveInteraction {} {} {}", timeStep, className, parameters.toString());
        // do nothing
    }

    public void receiveObject(Double timeStep, String className, String instanceName, Map<String, String> attributes) {
        log.trace("receiveObject {} {} {} {}", timeStep, className, instanceName, attributes.toString());
        
        final String glmObjectName = attributes.get("name");
        if (glmObjectName == null) {
            log.debug("skipped object update for {}:{}", className, instanceName);
            return;
        }
        
        final String houseName = convertToHouseName(className, glmObjectName);
        if (houseName == null) {
            log.warn("could not convert {}:{} with name {} to a house", className, instanceName, glmObjectName);
            return;
        }
        
        updateSendBuffer(className, attributes, getHouseHandle(houseName));
    }

    public void terminate() {
        log.trace("terminate");
        client.interrupt();
    }
    
    private void initializeSendBuffer() {
        final int numberOfAttributes = configuration.getSendFormat().size();
        
        for (int i = 0; i < numberOfAttributes; i++) {
            final String attributeClassPath = configuration.getSendFormat().get(i);
            
            if (sendBufferOffset.put(attributeClassPath, i) != null) {
                log.fatal("duplicate key {} in send format", attributeClassPath);
                throw new DuplicateKeyException(attributeClassPath);
            }
            log.debug("sendBuffer[{}] = {}", i, attributeClassPath);
        }
        sendBuffer = new double[configuration.getNumberOfHouses() * numberOfAttributes];
        log.info("sendBuffer length={} with {} attributes", sendBuffer.length, numberOfAttributes);
    }
    
    private void initializeReceiveBuffer() {
        final int numberOfAttributes = configuration.getReceiveFormat().size();
        
        for (int i = 0; i < numberOfAttributes; i++) {
            final String attributeClassPath = configuration.getReceiveFormat().get(i);
            
            if (receiveBufferOffset.put(attributeClassPath, i) != null) {
                log.fatal("duplicate key {} in receive format", attributeClassPath);
                throw new DuplicateKeyException(attributeClassPath);
            }
            log.debug("receiveBuffer[{}] = {}", i, attributeClassPath);
        }
        receiveBuffer = new double[configuration.getNumberOfHouses() * numberOfAttributes];
        log.info("receiveBuffer length={} with {} attributes", receiveBuffer.length, numberOfAttributes);
    }
    
    private void synchronizeWithClient()
            throws InterruptedException {
        // send
        while (!client.setData(sendBuffer)) {
            log.debug("waiting for client to accept data...");
            Thread.sleep(configuration.getWaitTimeMs());
        }
        
        // receive
        while (!client.hasData()) {
            log.debug("waiting for response from client...");
            Thread.sleep(configuration.getWaitTimeMs());
        }
        receiveBuffer = client.getData();
        
        if (receiveBuffer == null) {
            log.fatal("client returned null for receiveBuffer");
            throw new ConcurrentModificationException("concurrent call to client.getData()");
        }
    }
    
    private int getHouseHandle(String houseName) {
        Integer houseHandle = houseNameToHandle.get(houseName);
        
        if (houseHandle == null) {
            // first received update for this house
            houseHandle = houseNameToHandle.size();
            if (houseHandle == configuration.getNumberOfHouses()) {
                log.fatal("exceeded maximum number of houses {}", configuration.getNumberOfHouses());
                throw new BufferOverflowException();
            }
            houseNameToHandle.put(houseName, houseHandle);
            log.debug("house {} => {}", houseName, houseHandle);
        }
        return houseHandle.intValue();
    }
    
    private void updateSendBuffer(String className, Map<String, String> attributes, int houseHandle) {
        int startingIndex = houseHandle * configuration.getSendFormat().size();
        
        for (Map.Entry<String, String> attributesEntry : attributes.entrySet()) {
            final String key = className + "." + attributesEntry.getKey();
            final double value = Double.parseDouble(attributesEntry.getValue());
            
            Integer offset = sendBufferOffset.get(key);
            if (offset == null) {
                continue; // not a relevant attribute
            }
            sendBuffer[startingIndex + offset.intValue()] = value;
            log.trace("{}={} at index {}", key, value, startingIndex + offset.intValue());
        }
    }
    
    private void processReceiveBuffer() {
        for (Map.Entry<String, Integer> houseEntry : houseNameToHandle.entrySet()) {
            processReceiveBuffer(houseEntry.getKey(), houseEntry.getValue()); // handle each house
        }
    }
    
    private void processReceiveBuffer(String houseName, int houseHandle) {
        Map<String, Map<String, String>> objectClassToParameters = getHouseData(houseName, houseHandle);
        
        for (Map.Entry<String, Map<String, String>> entry : objectClassToParameters.entrySet()) {
            final String className = entry.getKey();
            final String key = houseName + ":" + className;
            
            String instanceName = registeredObjectNames.get(key);
            if (instanceName == null) {
                try {
                    instanceName = gateway.registerObjectInstance(className);
                } catch (FederateNotExecutionMember e) {
                    log.fatal("unhandled RTI exception", e);
                    throw new RuntimeException(e);
                } catch (NameNotFound e) {
                    log.fatal("unknown object class {}", className);
                    throw new ConfigurationException(e);
                } catch (ObjectClassNotPublished e) {
                    log.fatal("publications for {} incorrect in SOM", className);
                    throw new ObjectModelException(e);
                }
                registeredObjectNames.put(key, instanceName);
            }
            
            try {
                gateway.updateObject(instanceName, entry.getValue(), gateway.getTimeStamp());
            } catch (FederateNotExecutionMember | InvalidFederationTime | AttributeNotOwned | ObjectNotKnown e) {
                log.fatal("unhandled RTI exception", e);
                throw new RuntimeException(e);
            } catch (NameNotFound e) {
                log.fatal("unknown attribute for object class {} in the set {}", className, entry.getValue().keySet());
                throw new ConfigurationException(e);
            }
        }
    }
    
    // each house can have multiple associated HLA object instances
    // return a map from HLA Class Name => House Attributes
    private Map<String, Map<String, String>> getHouseData(String houseName, int houseHandle) {
        Map<String, Map<String, String>> houseData = new HashMap<String, Map<String, String>>();
        int startingIndex = houseHandle * configuration.getReceiveFormat().size();
        
        for (String attributeClassPath : configuration.getReceiveFormat()) {
            int offset = receiveBufferOffset.get(attributeClassPath);
            int delimiterIndex = attributeClassPath.lastIndexOf(".");
            
            final String className = attributeClassPath.substring(0, delimiterIndex);
            final String attributeName = attributeClassPath.substring(delimiterIndex + 1);
            final String stringValue = Double.toString(receiveBuffer[startingIndex + offset]);
            
            if (!houseData.containsKey(className)) {
                // first entry for this HLA object class; pre-populate with name attribute
                final String glmObjectName = convertToGlmObjectName(className, houseName);
                if (glmObjectName == null) {
                    log.fatal("convertToGlmObjectName({}, {}) returned null", className, houseName);
                    throw new NullPointerException("glmObjectName");
                }
                
                Map<String, String> attributes = new HashMap<String, String>();
                attributes.put("name", glmObjectName);
                houseData.put(className, attributes);
            }
            if (houseData.get(className).put(attributeName, stringValue) != null) {
                // this should not happen because it's checked in initializeReceiveBuffer
                log.fatal("duplicate key {} in receive format", attributeClassPath);
                throw new DuplicateKeyException(attributeClassPath);
            }
        }
        return houseData;
    }
    
    private String convertToHouseName(String className, String glmObjectName) {
        switch (className) {
            case "House":
                return glmObjectName;
            case "Meter":
                // throws runtime exception if _house_hvac is not a substring
                int suffixIndex = glmObjectName.lastIndexOf("_house_hvac");
                log.debug("Meter suffixIndex={} for {}", suffixIndex, glmObjectName);
                return glmObjectName.substring(0, suffixIndex);
            default:
                log.debug("unhandled className {} in convertToHouseName", className);
                return null;
        }
    }
    
    private String convertToGlmObjectName(String className, String houseName) {
        switch (className) {
            case "House":
                return houseName;
            case "Meter":
                return houseName + "_house_hvac";
            default:
                log.debug("unhandled className {} in convertToGlmObjectName", className);
                return null;
        }
    }
}
