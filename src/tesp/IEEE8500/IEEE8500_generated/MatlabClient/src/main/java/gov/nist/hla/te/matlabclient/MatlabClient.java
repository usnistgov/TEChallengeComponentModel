package gov.nist.hla.te.matlabclient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.nist.hla.gateway.GatewayCallback;
import gov.nist.hla.gateway.GatewayFederate;
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
    
    Map<String, Integer> houseToIndex = new HashMap<String, Integer>();
    
    private Map<String, Integer> inputIndexOffset = new HashMap<String, Integer>();
    
    private Map<String, Integer> outputIndexOffset = new HashMap<String, Integer>();
    
    private Map<String, Map<String, String>> houseToInstanceData = new HashMap<String, Map<String, String>>();
    
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
    
    public void doTimeStep(Double timeStep) {
        try {
            while (!client.setData(sendBuffer)) {
                log.info("waiting for client to accept data...");
                Thread.sleep(configuration.getWaitTimeMs());
            }
            
            while (!client.hasData()) {
                log.info("waiting for new data from client...");
                Thread.sleep(configuration.getWaitTimeMs());
            }
        } catch (InterruptedException e) {
            log.fatal("failed while waiting for client response", e);
            return; // better way to process this ?
        }
        
        receiveBuffer = client.getData();
        if (receiveBuffer == null) {
            // should not happen
        }
        
        for (Map.Entry<String, Integer> houseEntry : houseToIndex.entrySet()) {
            Map<String, Map<String, String>> classToParameters = new HashMap<String, Map<String, String>>();
            
            int startingIndex = houseEntry.getValue() * configuration.getReceiveFormat().size();
            
            for (String element : configuration.getReceiveFormat()) {
                int offset = inputIndexOffset.get(element);
                int delimiterIndex = element.lastIndexOf(".");
                final String className = element.substring(0, delimiterIndex);
                final String attributeName = element.substring(delimiterIndex + 1);
                final String stringValue = Double.toString(receiveBuffer[startingIndex + offset]);
                
                if (!classToParameters.containsKey(className)) {
                    Map<String, String> attributes = new HashMap<String, String>();
                    attributes.put("name", convertToGldName(className, houseEntry.getKey()));
                    classToParameters.put(className, attributes);
                }
                classToParameters.get(className).put(attributeName, stringValue); // check return value
            }
            
            for (Map.Entry<String, Map<String, String>> dataEntry : classToParameters.entrySet()) {
                if (!houseToInstanceData.containsKey(houseEntry.getKey()) ) { // first time updating this house
                    houseToInstanceData.put(houseEntry.getKey(), new HashMap<String, String>());
                }
                if (houseToInstanceData.get(houseEntry.getKey()).containsKey(dataEntry.getKey())) {
                    String instanceName;
                    try {
                        instanceName = gateway.registerObjectInstance(dataEntry.getKey());
                    } catch (FederateNotExecutionMember | NameNotFound | ObjectClassNotPublished e) {
                        // oops
                        return;
                    }
                    houseToInstanceData.get(houseEntry.getKey()).put(dataEntry.getKey(), instanceName);
                }
                final String instanceName = houseToInstanceData.get(houseEntry.getKey()).get(dataEntry.getKey());
                try {
                    gateway.updateObject(instanceName, dataEntry.getValue(), gateway.getTimeStamp());
                } catch (FederateNotExecutionMember | ObjectNotKnown | NameNotFound | AttributeNotOwned
                        | InvalidFederationTime e) {
                    // oops
                }
            }
        }
    }

    public void initializeSelf() {
        if (configuration.getNumberOfHouses() <= 0) {
            // oops
        }
        
        if (configuration.getSendFormat().isEmpty()) {
            // oops
        }
        
        for (int i = 0; i < configuration.getSendFormat().size(); i++) {
            if (outputIndexOffset.put(configuration.getSendFormat().get(i), i) != null) {
                // oops
            }
        }
        
        if (configuration.getReceiveFormat().isEmpty()) {
            // oops
        }
        
        for (int i = 0; i < configuration.getReceiveFormat().size(); i++) {
            if (inputIndexOffset.put(configuration.getReceiveFormat().get(i), i) != null) {
                // oops
            }
        }
        
        sendBuffer = new double[configuration.getNumberOfHouses() * configuration.getSendFormat().size()];
        receiveBuffer = new double[configuration.getNumberOfHouses() * configuration.getReceiveFormat().size()];
    }

    public void initializeWithPeers() {
        // publish all of our objects and give them initial values
        // retain the object names for future updates
    }

    public void prepareToResign() {
        // TODO Auto-generated method stub
    }

    public void receiveInteraction(Double timeStep, String className, Map<String, String> parameters) {
        // not supported
    }

    public void receiveObject(Double timeStep, String className, String instanceName, Map<String, String> attributes) {
        final String gldName = attributes.get("name");
        if (gldName == null) {
            // cannot process
            return;
        }
        
        final String houseName = convertToHouseName(className, gldName);
        if (houseName == null) {
            // cannot process
            return;
        }
        
        Integer houseIndex = houseToIndex.get("houseName");
        if (houseIndex == null) { // first received update for this house
            houseIndex = houseToIndex.size();
            if (houseIndex == configuration.getNumberOfHouses()) {
                // discovered too many houses; error
            }
            houseToIndex.put(houseName, houseIndex);
        }
        
        int startingIndex = houseIndex * configuration.getSendFormat().size();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            final String key = className + "." + entry.getKey();
            Integer offset = outputIndexOffset.get(key);
            if (offset == null) { // not relevant
                continue;
            }
            sendBuffer[startingIndex + offset] = Double.parseDouble(entry.getValue());
        }
    }

    public void terminate() {
        // not implemented
    }
    
    private String convertToHouseName(String className, String gldName) {
        String houseName = null;
        
        switch (className) {
            case "House":
                houseName = gldName;
                break;
            case "Meter":
                int suffixIndex = gldName.lastIndexOf("_house_hvac"); // can return -1
                houseName = gldName.substring(0, suffixIndex);
                break;
            default:
                // oops
        }
        return houseName;
    }
    
    private String convertToGldName(String className, String houseName) {
        String gldName = null;
        
        switch (className) {
            case "House":
                gldName = houseName;
                break;
            case "Meter":
                gldName = houseName + "_house_hvac";
                break;
            default:
                // oops
        }
        return gldName;
    }
}
