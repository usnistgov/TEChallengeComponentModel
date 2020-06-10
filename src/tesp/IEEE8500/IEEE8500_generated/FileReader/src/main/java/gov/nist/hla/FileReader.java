package gov.nist.hla;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ieee.standards.ieee1516._2010.InteractionClassType;
import org.ieee.standards.ieee1516._2010.ObjectClassType;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.nist.hla.gateway.GatewayCallback;
import gov.nist.hla.gateway.GatewayFederate;
import hla.rti.FederateNotExecutionMember;
import hla.rti.InteractionClassNotPublished;
import hla.rti.NameNotFound;

public class FileReader implements GatewayCallback {
    private static final Logger log = LogManager.getLogger();

    private static final String INTERACTION_SIM_TIME = "InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime";
    
    private static final String INTERACTION_SIM_END = "InteractionRoot.C2WInteractionRoot.SimulationControler.SimEnd";
    
    private static final String INTERACTION_RECEIPT = "InteractionRoot.C2WInteractionRoot.DataReceipt";
    
    private FileReaderConfig configuration;
    
    private GatewayFederate gateway;
    
    private List<FileData> fileData = new LinkedList<FileData>();
    
    private Iterator<FileData> fileDataIterator;
    
    private FileData nextFileData = null;
    
    private boolean receivedSimTime = false;
    
    private boolean receivedSimEnd = false;

    public static FileReaderConfig readConfiguration(String filepath)
            throws IOException {
        log.info("reading JSON configuration file {}", filepath);
        File configFile = Paths.get(filepath).toFile();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(configFile, FileReaderConfig.class);
    }
    
    public static void main(String[] args) {
        if (args.length != 1) {
            log.error("missing command line argument for JSON configuration file");
            return;
        }
        
        try {
            final String jsonFilePath = args[0];
            FileReaderConfig config = FileReader.readConfiguration(jsonFilePath);
            FileReader fileReaderFederate = new FileReader(config);
            fileReaderFederate.run();
            log.info("Done.");
        } catch (IOException e) {
            log.error(e);
        }
    }
    
    public FileReader(FileReaderConfig configuration) {
        this.configuration = configuration;
        this.gateway = new GatewayFederate(configuration, this);
    }
    
    public void run() {
        gateway.run();
    }
    
    public void initializeSelf() {
        log.trace("initializeSelf");
        readInputFile();
        verifyDataModel();
    }
    
    public void initializeWithPeers() {
        log.trace("initializeWithPeers");
        waitForSimTime();
        log.info("Initialized.");
    }
    
    public void receiveInteraction(Double timeStep, String className, Map<String, String> parameters) {
        log.trace("receiveInteraction {} as {} at t={}", className, parameters.toString(), timeStep);
        
        switch (className) {
            case INTERACTION_SIM_TIME:
                handleSimTime(parameters);
                break;
            case INTERACTION_SIM_END:
                handleSimEnd();
                break;
            default:
                handleInteraction(className, parameters);
        }
    }
    
    public void receiveObject(Double timeStep, String className, String instanceName, Map<String, String> attributes) {
        log.trace("receiveObject {}:{} as {} at t={}", className, instanceName, attributes.toString(), timeStep);
        handleObjectReflection(className, instanceName, attributes);
    }

    public void doTimeStep(Double timeStep) {
        log.trace("doTimeStep {}", timeStep);
        
        int numberOfUpdates = sendUpdates(timeStep);
        
        Map<String, String> receipt = new HashMap<String, String>();
        receipt.put("uniqueId", configuration.getFederateName());
        receipt.put("logicalTime", timeStep.toString());
        receipt.put("dataSent", Integer.toString(numberOfUpdates));
        sendInteraction(INTERACTION_RECEIPT, receipt);
        
        log.info("Completed t={}", timeStep);
    }

    public void prepareToResign() {
        log.trace("prepareToResign");
    }
    
    public void terminate() {
        log.trace("terminate");
    }
    
    private void readInputFile() {
        final String inputFilePath = configuration.getInputFilePath();
        final String delimiter = configuration.getDelimiter();
        
        log.info("reading input file {} with delimiter {}", inputFilePath, delimiter);
        
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(inputFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(delimiter);
                
                // 1 = logical time
                // 2 = interaction class name
                // 3 = parameter name
                // 4 = parameter value
                // ...
                if (data.length < 2 || data.length % 2 == 1) {
                    log.error("invalid input data: {}", data.toString());
                    throw new FileReaderException("failed to read input file");
                }
                
                FileData newData = new FileData();
                newData.logicalTime = parseTimeStamp(data[0]);
                newData.interactionClass = data[1];
                newData.parameters = new HashMap<String, String>();
                
                for (int i = 2; i < data.length; i+=2) {
                    if (newData.parameters.containsKey(data[i])) {
                        log.warn("duplicate parameter {}.{}", data[1], data[i]);
                    }
                    newData.parameters.put(data[i], data[i+1]);
                }
                
                fileData.add(newData);
                log.trace("will inject {} @ t = {}", newData.interactionClass, newData.logicalTime);
            }
            
            fileDataIterator = fileData.iterator();
            if (fileDataIterator.hasNext()) {
                nextFileData = fileDataIterator.next();
            } // otherwise nextFileData is null
        } catch (IOException e) {
            log.error(e);
            throw new FileReaderException("failed to read input file");
        }
    }
    
    private double parseTimeStamp(String timeStamp) {
        // this will need to be more complicated
        return Double.parseDouble(timeStamp);
    }
    
    private void verifyDataModel() {
        if (!isSimTimeSubscribed()) {
            log.error("no subscription for {}", INTERACTION_SIM_TIME);
            throw new FileReaderException("missing required interaction class");
        }
        if (!isReceiptPublished()) {
            log.error("no publication for {}", INTERACTION_RECEIPT);
            throw new FileReaderException("missing required interaction class");
        }
    }
    
    private boolean isSimTimeSubscribed() {
        final InteractionClassType interaction = gateway.getObjectModel().getInteraction(INTERACTION_SIM_TIME);
        return interaction != null && gateway.getObjectModel().getSubscribedInteractions().contains(interaction);
    }
    
    private boolean isReceiptPublished() {
        final InteractionClassType interaction = gateway.getObjectModel().getInteraction(INTERACTION_RECEIPT);
        return interaction != null && gateway.getObjectModel().getPublishedInteractions().contains(interaction);
    }
    
    private void waitForSimTime() {
        final int waitTimeMs = configuration.getWaitTimeMs();
        
        while (!receivedSimTime) {
            try {
                log.info("waiting {} ms to receive {}", waitTimeMs, INTERACTION_SIM_TIME);
                Thread.sleep(waitTimeMs);
                gateway.tick();
            } catch (InterruptedException | FederateNotExecutionMember e) {
                throw new FileReaderException(e);
            }
            
            if (receivedSimEnd) {
                log.error("received {} prior to initialization", INTERACTION_SIM_END);
                throw new FileReaderException("unexpected simulation end");
            }
        }
    }
    
    private void handleSimTime(Map<String, String> parameters) {
        if (receivedSimTime) {
            // replace the old values with the latest received version
            log.warn("received duplicate {}", INTERACTION_SIM_TIME);
        } else {
            log.info("received {}", INTERACTION_SIM_TIME);
        }
        
        // use these values to do something
        Long.valueOf(parameters.get("unixTimeStart"));
        Long.valueOf(parameters.get("unixTimeStop"));
        Double.valueOf(parameters.get("timeScale"));
        parameters.get("timeZone");
        
        receivedSimTime = true;
    }
    
    private void handleSimEnd() {
        log.info("received {}", INTERACTION_SIM_END);
        receivedSimEnd = true;
    }
    
    private void handleInteraction(String className, Map<String, String> parameters) {
        InteractionClassType interaction = gateway.getObjectModel().getInteraction(className);
        if (!gateway.getObjectModel().isCoreInteraction(interaction)) {
            log.warn("received unhandled interaction {}", className);
        }
    }
    
    private void handleObjectReflection(String className, String instanceName, Map<String, String> attributes) {
        ObjectClassType object = gateway.getObjectModel().getObject(className);
        if (!gateway.getObjectModel().isCoreObject(object)) {
            log.warn("received unhandled object reflection {}:{}", className, instanceName);
        }
    }
    
    private void sendInteraction(String interactionClass, Map<String, String> parameters) {
        if (!parameters.containsKey("originFed")) {
            // this probably needs to be done in a more sane way
            parameters.put("originFed", configuration.getFederateName());
        }
        
        try {
            gateway.sendInteraction(interactionClass, parameters); // as RO
            log.info("sent {} as {}", interactionClass, parameters.toString());
        } catch (InteractionClassNotPublished e) {
            log.error("interaction class not published: {}", interactionClass);
            throw new FileReaderException(e);
        } catch (NameNotFound e) {
            log.error("invalid interaction structure: {} {}", interactionClass, parameters.keySet().toString());
            throw new FileReaderException(e);
        } catch (FederateNotExecutionMember e) {
            throw new FileReaderException(e);
        }
    }
    
    private int sendUpdates(Double timeStep) {
        int numberOfUpdates = 0;
        
        while (nextFileData != null && nextFileData.logicalTime <= timeStep) {
            sendInteraction(nextFileData.interactionClass, nextFileData.parameters);
            numberOfUpdates = numberOfUpdates + 1;
            
            if (fileDataIterator.hasNext()) {
                nextFileData = fileDataIterator.next();
            } else {
                nextFileData = null;
            }
            
        }
        
        return numberOfUpdates;
    }
    
    private class FileData {
        public double logicalTime;
        public String interactionClass;
        public Map<String, String> parameters;
    }
}
