package gov.nist.hla.te.matlabclient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.nist.hla.gateway.GatewayCallback;
import gov.nist.hla.gateway.GatewayFederate;

public class MatlabClient implements GatewayCallback {
    private static final Logger log = LogManager.getLogger();

    private ClientConfiguration configuration;
    
    private GatewayFederate gateway;
    
    public static void main(String[] args)
            throws IOException {
        if (args.length != 1) {
            log.error("missing command line argument for JSON configuration file");
            return;
        }
        
        ClientConfiguration config = MatlabClient.readConfiguration(args[0]);
        MatlabClient client = new MatlabClient(config);
        log.info(config.getInputFormat().toString());   // remove
        log.info(config.getOutputFormat().toString());  // remove
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
    }
    
    public void run() {
        gateway.run();
    }
    
    public void doTimeStep(Double timeStep) {
        // TODO Auto-generated method stub
        
    }

    public void initializeSelf() {
        // TODO Auto-generated method stub
        
    }

    public void initializeWithPeers() {
        // TODO Auto-generated method stub
        
    }

    public void prepareToResign() {
        // TODO Auto-generated method stub
        
    }

    public void receiveInteraction(Double timeStep, String className, Map<String, String> parameters) {
        // TODO Auto-generated method stub
        
    }

    public void receiveObject(Double timeStep, String className, String instanceName, Map<String, String> attributes) {
        // TODO Auto-generated method stub
        
    }

    public void terminate() {
        // TODO Auto-generated method stub
        
    }
}
