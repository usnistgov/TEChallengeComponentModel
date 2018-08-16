package gov.nist.hla.gridlabd;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import gov.nist.hla.gateway.GatewayCallback;
import gov.nist.hla.gateway.GatewayFederate;
import gov.nist.hla.gateway.exception.RTIAmbassadorException;
import gov.nist.hla.gridlabd.exception.GridLabDException;
import gov.nist.hla.gridlabd.exception.SchemaValidationException;
import gov.nist.pages.ucef.LinearConversionType;
import gov.nist.pages.ucef.ucefPackage;
import gov.nist.sds4emf.Deserialize;
import hla.rti.AttributeNotOwned;
import hla.rti.FederateNotExecutionMember;
import hla.rti.InteractionClassNotPublished;
import hla.rti.InvalidFederationTime;
import hla.rti.NameNotFound;
import hla.rti.ObjectClassNotPublished;
import hla.rti.ObjectNotKnown;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ieee.standards.ieee1516._2010.AttributeType;
import org.ieee.standards.ieee1516._2010.InteractionClassType;
import org.ieee.standards.ieee1516._2010.ObjectClassType;
import org.ieee.standards.ieee1516._2010.ParameterType;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GridLabDFederate implements GatewayCallback {    
    private static final Logger log = LogManager.getLogger();
    
    private static final String INTERACTION_SIM_TIME = "InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime";
    
    private static final String INTERACTION_SIM_END = "InteractionRoot.C2WInteractionRoot.SimulationControl.SimEnd";
    
    final private GridLabDConfig configuration;
    
    final private SimpleDateFormat dateFormat;
    
    final private GatewayFederate gateway;
    
    final private ObjectModelHelper objectModelHelper;
    
    final private GridLabDClient client;
    
    private Process gridlabd = null;
    
    private boolean isGridLabDRunning = false;
    
    private boolean receivedSimTime = false;
    
    private boolean receivedSimEnd = false;
    
    private Map<String, String> registeredGlobals = new HashMap<String, String>();
    
    private Map<String, String> registeredObjects = new HashMap<String, String>();
    
    private Map<String, String> discoveredInstances = new HashMap<String, String>();
    
    private Map<String, String> discoveredInstanceClass = new HashMap<String, String>();
    
    private Map<String, Map<String, String>> pendingInstances = new HashMap<String, Map<String, String>>();
    
    private class InteractionData {
        public String className;
        public Map<String, String> parameters;
    }
    
    private List<InteractionData> pendingInteractions = new LinkedList<InteractionData>();
    
    private Map<String, Double> nextUpdateTime = new HashMap<String, Double>();
    
    private Set<String> thingsToUpdate = new HashSet<String>(); // need a better way
    
    public static GridLabDConfig readConfiguration(String filePath)
            throws IOException {
        log.info("reading JSON configuration file " + filePath);
        File configFile = Paths.get(filePath).toFile();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(configFile, GridLabDConfig.class);
    }
    
    public static void main(String args[]) {
        if (args.length != 1) {
            log.error("missing argument for JSON configuration file");
            return;
        }
         
        try {
            GridLabDConfig config = GridLabDFederate.readConfiguration(args[0]);
            GridLabDFederate gridlabdFederate = new GridLabDFederate(config);
            gridlabdFederate.run();
        } catch (Exception e) {
            log.error(e);
            return;
        }
        
        log.info("Done.");
    }
    
    public GridLabDFederate(GridLabDConfig configuration)
            throws SchemaValidationException {
        this.configuration = configuration;
        
        registerUcefSchema();
        validateAgainstSchema(configuration.getFomFilepath());
        
        // future GridLAB-D releases will continue to support GMT
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        this.dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        this.gateway = new GatewayFederate(configuration, this);
        this.objectModelHelper = new ObjectModelHelper(gateway.getObjectModel());
        this.client = new GridLabDClient("localhost", configuration.getServerPortNumber());
    }
    
    private void registerUcefSchema() {
        log.info("registering schema {}", ucefPackage.eNS_URI);
        Deserialize.registerPackage(ucefPackage.eNS_URI, ucefPackage.eINSTANCE);
    }
    
    private void validateAgainstSchema(String fomFilePath)
            throws SchemaValidationException {
        log.info("validating FOM {}", fomFilePath);
        
        Source fomFile = new StreamSource(new File(fomFilePath));
        InputStream hlaSchema = this.getClass().getClassLoader().getResourceAsStream("IEEE1516-DIF-2010.xsd");
        InputStream ucefSchema = this.getClass().getClassLoader().getResourceAsStream("ucef.xsd");
        
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new Source[] {
                        new StreamSource(hlaSchema),
                        new StreamSource(ucefSchema)});
            Validator validator = schema.newValidator();
            validator.validate(fomFile);
        } catch (IOException | SAXException e) {
            throw new SchemaValidationException(e);
        }
    }
    
    public void run() {
        log.trace("run");
        gateway.run();
    }
    
    @Override
    public void initializeSelf() {
        log.trace("initializeSelf");
    }
    
    @Override
    public void initializeWithPeers() {
        log.trace("initializeWithPeers");
        
        registerObjectInstances();
        
        if (configuration.getUseSimTime()) {
            waitForSimTime();
        } else {
            log.info("configured to run without " + INTERACTION_SIM_TIME);
        }
        
        if (configuration.getUnixTimeStop() < 0) {
            log.info("configured to run with stoptime=NEVER");
        }
        if (!objectModelHelper.isSubscribed(INTERACTION_SIM_END)) {
            log.warn("configured to run without " + INTERACTION_SIM_END);
            if (configuration.getUnixTimeStop() < 0) {
                throw new GridLabDException("no exit condition");
            }
        }
        
        try {
            startGld();
            connectToGld();
            isGridLabDRunning = true;
        } catch (IOException | InterruptedException e) {
            throw new GridLabDException(e);
        }
        
        doPendingObjectUpdates();
        
        log.info("Initialized.");
    }
    
    private void registerObjectInstances() {
        log.trace("registerObjectInstances");
        
        for (ObjectClassType object : gateway.getObjectModel().getPublishedObjects()) {
            if (objectModelHelper.isGlobalVariable(object)) {
                registerGlobalVariable(object);
            } else {
                registerObjectInstances(object);
            }
        }
    }
    
    private void registerGlobalVariable(ObjectClassType object) {
        final String classPath = gateway.getObjectModel().getClassPath(object);
        log.trace("registerGlobalVariable {}", classPath);
        
        try {
            String instanceName = gateway.registerObjectInstance(classPath);
            registeredGlobals.put(classPath, instanceName);
            log.info("registered object {} with class {} to publish global variables", instanceName, classPath);
        } catch (FederateNotExecutionMember | NameNotFound | ObjectClassNotPublished e) {
            throw new RTIAmbassadorException(e);
        }
    }
    
    private void registerObjectInstances(ObjectClassType object) {
        final String classPath = gateway.getObjectModel().getClassPath(object);
        log.trace("registerObjectInstances {}", classPath);
        
        
        Set<String> publishedObjectNames = objectModelHelper.getPublishedNames(object);
        
        if (publishedObjectNames.isEmpty()) {
            log.warn("no GridLAB-D object names were defined for published object {}", classPath);
        }
        for (String objectName : publishedObjectNames) {
            try {
                String instanceName = gateway.registerObjectInstance(classPath);
                Map<String, String> initialValues = new HashMap<String, String>();
                initialValues.put("name", objectName);
                gateway.updateObject(instanceName, initialValues);
                registeredObjects.put(classPath + ":" + objectName, instanceName);
                log.info("registered object {} with class {} to publish {}", instanceName, classPath, objectName);
            } catch (FederateNotExecutionMember | NameNotFound | ObjectClassNotPublished | ObjectNotKnown
                    | AttributeNotOwned e) {
                throw new RTIAmbassadorException(e);
            }
        }
    }
    
    private void waitForSimTime() {
        log.trace("waitForSimTime");
        
        if (!objectModelHelper.isSubscribed(INTERACTION_SIM_TIME)) {
            throw new GridLabDException("no subscription for " + INTERACTION_SIM_TIME);
        }
        
        while (!receivedSimTime) {
            try {
                log.info("waiting {} ms to receive {}", configuration.getWaitTimeMs(), INTERACTION_SIM_TIME);
                Thread.sleep(configuration.getWaitTimeMs());
                gateway.tick();
                
                if (receivedSimEnd) {
                    log.error("received {} prior to initialization", INTERACTION_SIM_END);
                    throw new GridLabDException("unexpected " + INTERACTION_SIM_END);
                }
            } catch (FederateNotExecutionMember e) {
                throw new RTIAmbassadorException(e);
            } catch (InterruptedException e) {
                throw new GridLabDException(e);
            }
        }
    }
    
    private void startGld()
            throws IOException {
        log.trace("startGld");
        
        String timeStart = toTimeStamp(configuration.getUnixTimeStart());
        String timeStop  = configuration.getUnixTimeStop() < 0 ? "NEVER" : toTimeStamp(configuration.getUnixTimeStop());
        String timeZone  = configuration.getSimulationTimeZone();
        configuration.getSimulationTimeScale(); // to trigger early exception if missing
        
        log.debug("creating process builder for GridLAB-D");
        ProcessBuilder builder = new ProcessBuilder();
        builder.inheritIO(); // maybe replace
        
        builder.directory(new File(configuration.getWorkingDirectory())); // null is okay
        log.debug("directory: {}", configuration.getWorkingDirectory());
        
        builder.command(
                "gridlabd",
                configuration.getModelFilePath(),
                "--server",
                "--server_portnum",
                Integer.toString(configuration.getServerPortNumber()), // no guarantee this port gets used
                "--define",
                "starttime=" + timeStart,
                "--define",
                "stoptime=" + timeStop,
                "--define",
                "pauseat=" + timeStart
                );
        log.debug("command: {}", Arrays.toString(builder.command().toArray()));
        
        Map<String, String> environment = builder.environment();
        environment.put("TZ", timeZone);
        log.debug("timezone: {}", timeZone);
        
        log.info("starting GridLAB-D process");
        gridlabd = builder.start();
        
        // this will handle SIGINT; pkill java will be required for other halt conditions 
        log.info("registering shutdown hook");
        Thread gldShutdown = new Thread() {
            public void run() {
                try {
                    client.shutdown();
                } catch (IOException e) {
                    log.info("destroying the GridLAB-D process");
                    gridlabd.destroy();
                }
            }
        };
        Runtime.getRuntime().addShutdownHook(gldShutdown);
    }
    
    private void connectToGld()
            throws InterruptedException {
        log.trace("connectToGld");
        
        boolean connected = false;
        while (!connected) {
            try {
                log.info("waiting {} ms on connection to {}", configuration.getWaitTimeMs(), client);
                Thread.sleep(configuration.getWaitTimeMs());
                connected = client.isPaused(); // true when GridLAB-D model initialized
            } catch (IOException e) {
                log.warn("failed to connect to GridLAB-D server");
            }
        }
    }
    
    private String toTimeStamp(long unixTime) {
        log.trace("toTimeStamp {}", unixTime);
        return dateFormat.format(new Date(unixTime*1000));
    }
    
    private long toUnixTime(String timeStamp)
            throws ParseException {
        log.trace("toUnixTime {}", timeStamp);
        return dateFormat.parse(timeStamp).getTime()/1000;
    }
    
    @Override
    public void receiveInteraction(Double timeStep, String className, Map<String, String> parameters) {
        log.trace("receiveInteraction {} {} {}", timeStep, className, parameters.toString());
        
        if (className.equals(INTERACTION_SIM_END)) {
            receivedSimEnd = true;
        } else if (className.equals(INTERACTION_SIM_TIME)) {
            if (receivedSimTime) {
                log.warn("received duplicate {}", INTERACTION_SIM_TIME);
            }
            configuration.setUnixTimeStart(Long.valueOf(parameters.get("unixTimeStart")));
            configuration.setUnixTimeStop(Long.valueOf(parameters.get("unixTimeStop")));
            configuration.setSimulationTimeScale(Double.valueOf(parameters.get("timeScale")));
            configuration.setSimulationTimeZone(parameters.get("timeZone"));
            receivedSimTime = true;
        } else {
            handleInteraction(className, parameters);
        }
    }
    
    private void handleInteraction(String className, Map<String, String> parameters) {
        log.trace("handleInteraction {} {}", className, parameters.toString());
        
        if (!isGridLabDRunning) {
            InteractionData data = new InteractionData();
            data.className = className;
            data.parameters = new HashMap(parameters);
            pendingInteractions.add(data);
            log.debug("stored interaction {}: GridLAB-D simulation not started", className);
            return;
        }
        
        InteractionClassType interaction = gateway.getObjectModel().getInteraction(className);
        if (objectModelHelper.isGlobalVariable(interaction)) {
            log.warn("subscriptions for global variables not supported: {}", className);
            return;
        }
        
        String gldObjectName = parameters.get("name");
        log.debug("received update for {}", gldObjectName);
        
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            final String parameterName = parameter.getKey();
            log.trace("on parameter {}", parameterName);
            
            if (objectModelHelper.isRootParameter(parameterName) || parameterName.equals("name")) {
                log.debug("skipping parameter {}", parameterName);
                continue;
            }
            
            try {
                ParameterType parameterType = gateway.getObjectModel().getParameter(interaction, parameterName);
                String unit = objectModelHelper.getNameConversion(parameterType);
                if (unit != null) {
                    log.trace("unit conversion with unit {}", unit);
                    double value = Double.parseDouble(parameter.getValue());
                    client.setObjectProperty(gldObjectName, parameterName, value, unit);
                    log.debug("set {}:{}={} [{}]", gldObjectName, parameterName, value, unit);
                } else {
                    LinearConversionType conversionRule = objectModelHelper.getLinearConversion(parameterType);
                    if (conversionRule != null) {
                        log.trace("linear conversion");
                        double value = Double.parseDouble(parameter.getValue());
                        value = convertToGld(conversionRule, value);
                        client.setObjectProperty(gldObjectName, parameterName, value, null);
                        log.debug("set {}:{}={}", gldObjectName, parameterName, value);
                    } else {
                        log.trace("no conversion");
                        client.setObjectProperty(gldObjectName, parameterName, parameter.getValue());
                        log.debug("set {}:{}={}", gldObjectName, parameterName, parameter.getValue());
                    }
                }
            } catch (IOException e) {
                log.error("unable to set {}:{}", gldObjectName, parameterName);
            }
        }
    }

    @Override
    public void receiveObject(Double timeStep, String className, String instanceName, Map<String, String> attributes) {
        log.trace("receiveObject {} {} {} {}", timeStep, className, instanceName, attributes.toString());
        handleObjectReflection(className, instanceName, attributes);
    }
    
    private void handleObjectReflection(String className, String instanceName, Map<String, String> attributes) {
        log.trace("handleObjectReflection {} {} {}", className, instanceName, attributes.toString());
        
        ObjectClassType object = gateway.getObjectModel().getObject(className);
        if (objectModelHelper.isGlobalVariable(object)) {
            log.warn("subscriptions for global variables not supported: {}", className);
            return;
        }
        
        if (!discoveredInstanceClass.containsKey(instanceName)) {
            discoveredInstanceClass.put(instanceName, className);
        }
        
        Map<String, String> previousUpdate = pendingInstances.get(instanceName);
        
        if (!isGridLabDRunning) {
            if (previousUpdate == null) {
                log.info("delayed update for {}:{} until GridLAB-D is started", className, instanceName);
                pendingInstances.put(instanceName, new HashMap<String, String>(attributes));
            } else {
                log.debug("received update values for delayed update {}:{}", className, instanceName);
                previousUpdate.putAll(attributes);
            }
            return;
        }
        
        // maybe race condition here
        if (!discoveredInstances.containsKey(instanceName) && !attributes.containsKey("name")) {
            log.info("delayed update for {}:{} until GridLAB-D object name received", className, instanceName);
            if (previousUpdate == null) {
                pendingInstances.put(instanceName, new HashMap<String, String>(attributes));
            } else {
                previousUpdate.putAll(attributes);
            }
            return;
        }
        
        if (attributes.containsKey("name")) {
            discoveredInstances.put(instanceName, attributes.get("name"));
            log.debug("using name {} for object instance {}", attributes.get("name"), instanceName);
        }
        
        String gldObjectName = discoveredInstances.get(instanceName);
        log.debug("received update for {}", gldObjectName);
        
        Map<String, String> updatedAttributes = new HashMap<String, String>();
        if (previousUpdate != null) {
            updatedAttributes.putAll(previousUpdate);
        }
        updatedAttributes.putAll(attributes);
        updateGridLabDObject(className, gldObjectName, updatedAttributes);
    }
    
    private void updateGridLabDObject(String className, String gldObjectName, Map<String, String> attributes) {
        log.trace("updateGridLabDObject {} {} {}", className, gldObjectName, attributes.toString());
        
        for (Map.Entry<String, String> attribute : attributes.entrySet()) {
            final String attributeName = attribute.getKey();
            log.trace("on attribute {}", attributeName);
            
            if (attributeName.equals("name")) {
                log.debug("skipping attribute {}", attributeName);
                continue;
            }
            
            try {
                ObjectClassType object = gateway.getObjectModel().getObject(className);
                AttributeType attributeType = gateway.getObjectModel().getAttribute(object, attributeName);
                String unit = objectModelHelper.getNameConversion(attributeType);
                if (unit != null) {
                    log.trace("unit conversion with unit {}", unit);
                    double value = Double.parseDouble(attribute.getValue());
                    client.setObjectProperty(gldObjectName, attributeName, value, unit);
                    log.debug("set {}:{}={} [{}]", gldObjectName, attributeName, value, unit);
                } else {
                    LinearConversionType conversionRule = objectModelHelper.getLinearConversion(attributeType);
                    if (conversionRule != null) {
                        log.trace("linear conversion");
                        double value = Double.parseDouble(attribute.getValue());
                        value = convertToGld(conversionRule, value);
                        client.setObjectProperty(gldObjectName, attributeName, value, null);
                        log.debug("set {}:{}={}", gldObjectName, attributeName, value);
                    } else {
                        log.trace("no conversion");
                        client.setObjectProperty(gldObjectName, attributeName, attribute.getValue());
                        log.debug("set {}:{}={}", gldObjectName, attributeName, attribute.getValue());
                    }
                }
            } catch (IOException e) {
                log.error("unable to set {}:{}", gldObjectName, attributeName);
            }
        }
    }
    
    private void doPendingObjectUpdates() {
        log.trace("doPendingObjectUpdates");
        
        Set<String> processedInstances = new HashSet<String>();
        for (Map.Entry<String, Map<String, String>> entry : pendingInstances.entrySet()) {
            final String instanceName = entry.getKey();
            final Map<String, String> attributes = entry.getValue();
            
            if (attributes.containsKey("name")) {
                discoveredInstances.put(instanceName, attributes.get("name"));
                log.debug("using name {} for object instance {}", attributes.get("name"), instanceName);
                updateGridLabDObject(discoveredInstanceClass.get(instanceName), attributes.get("name"), attributes);
                processedInstances.add(instanceName);
            }
        }
        
        for (String instanceName : processedInstances) {
            pendingInstances.remove(instanceName);
        }
        
        for (InteractionData data : pendingInteractions) {
            handleInteraction(data.className, data.parameters);
        }
        pendingInteractions.clear();
    }

    @Override
    public void prepareToResign() {
        log.trace("prepareToResign");
    }

    @Override
    public void terminate() {
        log.trace("terminate");
        // clean shutdown for early exit
    }
    
    private void advanceSimulationTime(long unixTime)
            throws InterruptedException, IOException {
        log.trace("advanceSimulationTime {}", unixTime);
        
        final String timeStamp = toTimeStamp(unixTime);
        
        if (unixTime >= configuration.getUnixTimeStop()) {
            log.info("running last timestep (GridLAB-D will crash)");
            try {
                client.pauseat(timeStamp);
                // the GridLAB-D server will not respond to this HTTP GET request
                // the pauseat command should throw an exception in response to this
                throw new GridLabDException("unreachable code");
            } catch (IOException e) {
                log.info("GridLAB-D simulation complete");
            }
            return;
        }
        
        client.pauseat(timeStamp);
        
        while (!client.isPaused()) {
            log.debug("waiting {} ms for GridLAB-D clock to advance", configuration.getWaitTimeMs());
            Thread.sleep(configuration.getWaitTimeMs());
        }
        log.info("advanced GridLAB-D simulation to {}", timeStamp);
    }
    
    private double convertToGld(LinearConversionType conversion, double value){
        log.trace("convertToGld");
        double scale = conversion.getScale();
        double offset = conversion.getOffset();
        double result = (value - offset) / scale;
        log.debug("linear conversion {} = ({} - {}) / {}", result, value, offset, scale);
        return result;
    }
    
    private double convertToHla(LinearConversionType conversion, double value){
        log.trace("convertToHla");
        double scale = conversion.getScale();
        double offset = conversion.getOffset();
        double result = scale * value + offset;
        log.debug("linear conversion {} = {} * {} - {}", result, scale, value, offset);
        return result;
    }
    
    @Override
    public void doTimeStep(Double timeStep) {
        log.trace("doTimeStep {}", timeStep);
        
        try {
            int code = gridlabd.exitValue();
            log.info("GridLAB-D done with exit value {}", code);
            gateway.requestExit();
        } catch (IllegalThreadStateException e) {
            // maybe check if GridLAB-D advanced to the expected time ?
            
            sendPublications();
            thingsToUpdate.clear();
            
            double elapsedTime = (timeStep + configuration.getStepSize()) * configuration.getSimulationTimeScale();
            long nextPauseTime = configuration.getUnixTimeStart() + Double.valueOf(elapsedTime).longValue();
            if (nextPauseTime > configuration.getUnixTimeStop()) {
                nextPauseTime = configuration.getUnixTimeStop();
            }
            try {
                advanceSimulationTime(nextPauseTime);
            } catch (InterruptedException | IOException e2) {
                throw new GridLabDException(e2);
            } 
        }
    }

    private void sendPublications() {
        log.trace("sendPublications");
        
        for (InteractionClassType interaction : gateway.getObjectModel().getPublishedInteractions()) {
            String classPath = gateway.getObjectModel().getClassPath(interaction);
            if (classPath.equals("InteractionRoot.C2WInteractionRoot.FederateJoinInteraction")
                    || classPath.equals("InteractionRoot.C2WInteractionRoot.FederateResignInteraction")) {
                log.debug("skipping gateway interaction {}", classPath);
                continue;
            }
            if (objectModelHelper.isGlobalVariable(interaction)) {
                publishGlobalVariable(interaction);
            } else {
                publishGldObject(interaction);
            }
        }
        
        for (ObjectClassType object : gateway.getObjectModel().getPublishedObjects()) {
            if (objectModelHelper.isGlobalVariable(object)) {
                publishGlobalVariable(object);
            } else {
                publishGldObject(object);
            }
        }
    }
    
    private void publishGlobalVariable(InteractionClassType interaction) {
        final String classPath = gateway.getObjectModel().getClassPath(interaction);
        log.trace("publishGlobalVariable {}", classPath);
        
        if (!nextUpdateTime.containsKey(classPath)) {
            nextUpdateTime.put(classPath, gateway.getLogicalTime());
        }
        
        if (!thingsToUpdate.contains(classPath)) {
            double updateTime = nextUpdateTime.get(classPath);
            if (updateTime > gateway.getLogicalTime()) {
                log.debug("skipping {} until {}", classPath, updateTime);
                return;
            }
            
            double updatePeriod = objectModelHelper.getUpdatePeriod(interaction);
            nextUpdateTime.put(classPath, updateTime + updatePeriod);
            thingsToUpdate.add(classPath);
        }
        
        Map<String, String> updatedValues = new HashMap<String, String>();
        for (ParameterType parameter : gateway.getObjectModel().getParameters(interaction)) {
            final String parameterName = parameter.getName().getValue();
            log.trace("on parameter {}", parameterName);
            
            if (objectModelHelper.isRootParameter(parameterName)) {
                // these should be set to a reasonable default value
                log.debug("skipping parameter {}", parameterName);
                continue;
            }
            
            try {
                // no support for double or unit conversion for global variables
                String value = client.getGlobalVariable(parameterName);
                updatedValues.put(parameterName, value);
                log.debug("got :{}={}", parameterName, value);
            } catch (IOException e) {
                log.error("unable to get global variable {}", parameterName);
            }
        }
        
        if (updatedValues.isEmpty()) {
            log.warn("no values to update for {}", classPath);
            return;
        }
        
        try {
            gateway.sendInteraction(classPath, updatedValues, gateway.getTimeStamp());
        } catch (FederateNotExecutionMember | NameNotFound | InteractionClassNotPublished | InvalidFederationTime e) {
            throw new RTIAmbassadorException(e);
        } 
    }
    
    private void publishGlobalVariable(ObjectClassType object) {
        final String classPath = gateway.getObjectModel().getClassPath(object);
        log.trace("publishGlobalVariable {}", classPath);
        
        Map<String, String> updatedValues = new HashMap<String, String>();
        for (AttributeType attribute : gateway.getObjectModel().getAttributes(object)) {
            final String attributeName = attribute.getName().getValue();
            log.trace("on attribute {}", attributeName);
            
            if (objectModelHelper.isRootAttribute(attributeName)) {
                log.debug("skipping attribute {}", attributeName);
                continue;
            }
            
            if (!nextUpdateTime.containsKey(classPath + ":" + attributeName)) {
                nextUpdateTime.put(classPath + ":" + attributeName, gateway.getLogicalTime());
            }
            
            if (!thingsToUpdate.contains(classPath + ":" + attributeName)) {
                double updateTime = nextUpdateTime.get(classPath + ":" + attributeName);
                if (updateTime > gateway.getLogicalTime()) {
                    log.debug("skipping {}:{} until {}", classPath, attributeName, updateTime);
                    continue;
                }
                
                double updatePeriod = objectModelHelper.getUpdatePeriod(attribute);
                nextUpdateTime.put(classPath + ":" + attributeName, updateTime + updatePeriod);
                thingsToUpdate.add(classPath + ":" + attributeName);
            }
            
            try {
                // no support for double or unit conversion for global variables
                String value = client.getGlobalVariable(attributeName);
                updatedValues.put(attributeName, value);
                log.debug("got :{}={}", attributeName, value);
            } catch (IOException e) {
                log.error("unable to get global variable {}", attributeName);
            }
        }
        
        if (updatedValues.isEmpty()) {
            log.warn("no values to update for {}", classPath);
            return;
        }
        
        try {
            String instanceName = registeredGlobals.get(classPath);
            gateway.updateObject(instanceName, updatedValues, gateway.getTimeStamp());
        } catch (FederateNotExecutionMember | ObjectNotKnown | NameNotFound | AttributeNotOwned
                | InvalidFederationTime e) {
            throw new RTIAmbassadorException(e);
        } 
    }
    
    private void publishGldObject(InteractionClassType interaction) {
        final String classPath = gateway.getObjectModel().getClassPath(interaction);
        log.trace("publishGldObject {}", classPath);
        
        if (!nextUpdateTime.containsKey(classPath)) {
            nextUpdateTime.put(classPath, gateway.getLogicalTime());
        }
        
        if (!thingsToUpdate.contains(classPath)) {
            double updateTime = nextUpdateTime.get(classPath);
            if (updateTime > gateway.getLogicalTime()) {
                log.debug("skipping {} until {}", classPath, updateTime);
                return;
            }
            
            double updatePeriod = objectModelHelper.getUpdatePeriod(interaction);
            nextUpdateTime.put(classPath, updateTime + updatePeriod);
            thingsToUpdate.add(classPath);
        }
        
        for (String gldObjectName : objectModelHelper.getPublishedNames(interaction)) {
            Map<String, String> updatedValues = new HashMap<String, String>();
            for (ParameterType parameter : gateway.getObjectModel().getParameters(interaction)) {
                final String parameterName = parameter.getName().getValue();
                log.trace("on parameter {}", parameterName);
                
                if (objectModelHelper.isRootParameter(parameterName) || parameterName.equals("name")) {
                    // these should be set to a reasonable default value
                    log.debug("skipping parameter {}", parameterName);
                    continue;
                }
                
                try {
                    String unit = objectModelHelper.getNameConversion(parameter);
                    if (unit != null) {
                        log.trace("unit conversion with unit {}", unit);
                        double value = client.getObjectProperty(gldObjectName, parameterName, unit);
                        updatedValues.put(parameterName, Double.toString(value));
                        log.debug("got {}:{}={} [{}]", gldObjectName, parameterName, value, unit);
                    } else if (objectModelHelper.isDouble(parameter)) {
                        log.trace("flagged as double");
                        double value = client.getObjectProperty(gldObjectName, parameterName, null);
                        LinearConversionType conversionRule = objectModelHelper.getLinearConversion(parameter);
                        if (conversionRule != null) {
                            log.trace("linear conversion");
                            value = convertToHla(conversionRule, value);
                        }
                        updatedValues.put(parameterName, Double.toString(value));
                        log.debug("got {}:{}={}", gldObjectName, parameterName, value);
                    } else {
                        String value = client.getObjectProperty(gldObjectName, parameterName);
                        updatedValues.put(parameterName, value);
                        log.debug("got {}:{}={}", gldObjectName, parameterName, value);
                    }
                } catch (IOException e) {
                    log.error("unable to get {}:{}", gldObjectName, parameterName);
                }
            }
            
            if (updatedValues.isEmpty()) {
                log.warn("no values to update for {} ({})", classPath, gldObjectName);
                return;
            }
            
            updatedValues.put("name", gldObjectName);
            
            try {
                gateway.sendInteraction(classPath, updatedValues, gateway.getTimeStamp());
            } catch (FederateNotExecutionMember | NameNotFound | InteractionClassNotPublished
                    | InvalidFederationTime e) {
                throw new RTIAmbassadorException(e);
            } 
        }
    }
    
    private void publishGldObject(ObjectClassType object) {
        final String classPath = gateway.getObjectModel().getClassPath(object);
        log.trace("publishGldObject {}", classPath);
        
        for (String gldObjectName : objectModelHelper.getPublishedNames(object)) {
            Map<String, String> updatedValues = new HashMap<String, String>();
            for (AttributeType attribute : gateway.getObjectModel().getAttributes(object)) {
                final String attributeName = attribute.getName().getValue();
                log.trace("on attribute {}", attributeName);
                
                if (objectModelHelper.isRootAttribute(attributeName) || attributeName.equals("name")) {
                    log.debug("skipping attribute {}", attributeName);
                    continue;
                }
                
                if (!nextUpdateTime.containsKey(classPath + ":" + attributeName)) {
                    nextUpdateTime.put(classPath + ":" + attributeName, gateway.getLogicalTime());
                }
                
                if (!thingsToUpdate.contains(classPath + ":" + attributeName)) {
                    double updateTime = nextUpdateTime.get(classPath + ":" + attributeName);
                    if (updateTime > gateway.getLogicalTime()) {
                        log.debug("skipping {}:{} until {}", classPath, attributeName, updateTime);
                        continue;
                    }
                    
                    double updatePeriod = objectModelHelper.getUpdatePeriod(attribute);
                    nextUpdateTime.put(classPath + ":" + attributeName, updateTime + updatePeriod);
                    thingsToUpdate.add(classPath + ":" + attributeName);
                }
                
                try {
                    String unit = objectModelHelper.getNameConversion(attribute);
                    if (unit != null) {
                        log.trace("unit conversion with unit {}", unit);
                        double value = client.getObjectProperty(gldObjectName, attributeName, unit);
                        updatedValues.put(attributeName, Double.toString(value));
                        log.debug("got {}:{}={} [{}]", gldObjectName, attributeName, value, unit);
                    } else if (objectModelHelper.isDouble(attribute)) {
                        log.trace("flagged as double");
                        double value = client.getObjectProperty(gldObjectName, attributeName, null);
                        LinearConversionType conversionRule = objectModelHelper.getLinearConversion(attribute);
                        if (conversionRule != null) {
                            log.trace("linear conversion");
                            value = convertToHla(conversionRule, value);
                        }
                        updatedValues.put(attributeName, Double.toString(value));
                        log.debug("got {}:{}={}", gldObjectName, attributeName, value);
                    } else {
                        String value = client.getObjectProperty(gldObjectName, attributeName);
                        updatedValues.put(attributeName, value);
                        log.debug("got {}:{}={}", gldObjectName, attributeName, value);
                    }
                } catch (IOException e) {
                    log.error("unable to get {}:{}", gldObjectName, attributeName);
                }
            }
            
            if (updatedValues.isEmpty()) {
                log.warn("no values to update for {} ({})", classPath, gldObjectName);
                return;
            }
            
            try {
                String instanceName = registeredObjects.get(classPath + ":" + gldObjectName);
                gateway.updateObject(instanceName, updatedValues, gateway.getTimeStamp());
            } catch (FederateNotExecutionMember | ObjectNotKnown | NameNotFound | AttributeNotOwned
                    | InvalidFederationTime e) {
                throw new RTIAmbassadorException(e);
            } 
        }
    }
}
