package gov.nist.hla.te.simulationtime;

import gov.nist.hla.te.simulationtime.rti.*;

import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.FederateObject;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * This federate sends a SimTime interaction using the values specified in its JSON configuration file.
 * 
 * One SimTime interaction will be sent after the federation synchronizes on readyToPopulate. An additional SimTime
 * interaction - identical to the first - will be sent for each federate that joins after readyToPopulate. All the
 * SimTime interactions are sent as Receive Order (RO).
 */
public class SimulationTime extends SimulationTimeBase {
    private final static Logger log = LogManager.getLogger();

    private final SimulationTimeConfig configuration;

    private boolean isFederationPopulated = false;
    
    private double currentTime = 0;

    public SimulationTime(SimulationTimeConfig params) throws Exception {
        super(params);
        this.configuration = params;
    }
    
    @Override
    public void discoverObjectInstance(int objectHandle, int objectClassHandle, String objectName) {
        super.discoverObjectInstance(objectHandle, objectClassHandle, objectName);
        
        if (FederateObject.match(objectClassHandle)) {
            handleDiscoveredFederate(objectHandle, objectName);
        }
    }

    private void checkReceivedSubscriptions() {

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof FederateObject) {
                /*
                 * The SimulationTime federate only cares that a new FederateObject has been discovered. The details of
                 * the discovered federate are irrelevant, and these updates are discarded. This method has been kept
                 * for the sole purpose of emptying the object reflector queue.
                 * 
                 * NOTE that in most federations the following statement will never execute. Portico will only provide
                 * updates for a FederateObject on request. The SimulationTime federate does not request an update, and
                 * this code will only be executed when another federate makes such a request.
                 */
                log.debug("dropped update to a FederateObject");
            }
            else {
                log.debug("unhandled object reflection: {}", object.getClassName());
            }
        }
    }
    
    private void execute() throws Exception {
        if(super.isLateJoiner()) {
            log.info("turning off time regulation (late joiner)");
            currentTime = super.getLBTS() - super.getLookAhead();
            super.disableTimeRegulation();
        }

        AdvanceTimeRequest atr = new AdvanceTimeRequest(currentTime);
        putAdvanceTimeRequest(atr);

        if(!super.isLateJoiner()) {
            log.info("waiting on readyToPopulate...");
            readyToPopulate();
            log.info("...synchronized on readyToPopulate");
        }
        
        sendSimTimeInteraction();
        isFederationPopulated = true;
        log.info("sent SimTime interaction after readyToPopulate");
        
        /*
         * There is an edge case when a federate joins after this statement but before readyToRun. The behavior of the
         * federation in this case is undefined. The late federate will receive a SimTime interaction, but it could be
         * received either before readyToRun or during the first logical time step.
         */
        
        if(!super.isLateJoiner()) {
            log.info("waiting on readyToRun...");
            readyToRun();
            log.info("...synchronized on readyToRun");
        }

        startAdvanceTimeThread();
        log.info("started logical time progression");

        while (!exitCondition) {
            atr.requestSyncStart();
            enteredTimeGrantedState();

            checkReceivedSubscriptions();

            if (!exitCondition) {
                currentTime += super.getStepSize();
                AdvanceTimeRequest newATR =
                    new AdvanceTimeRequest(currentTime);
                putAdvanceTimeRequest(newATR);
                atr.requestSyncEnd();
                atr = newATR;
            }
        }

        // call exitGracefully to shut down federate
        exitGracefully();
    }
    
    private void handleDiscoveredFederate(int objectHandle, String objectName) {
        if (isFederationPopulated) {
            // a federate has joined late and may need the SimTime interaction
            sendSimTimeInteraction();
            log.info("sent SimTime interaction for federate {} ({})", objectHandle, objectName);
        } else {
            // a federate has joined in time to receive the first SimTime interaction
            log.debug("skipped federate {} ({})", objectHandle, objectName);
        }
    }
    
    private void sendSimTimeInteraction() {
        SimTime simTime = create_SimTime();
        
        simTime.set_timeScale(configuration.timeScale);
        simTime.set_timeZone(configuration.timeZone);
        simTime.set_unixTimeStart(configuration.unixTimeStart);
        simTime.set_unixTimeStop(configuration.unixTimeStop);
        simTime.sendInteraction(getLRC()); // send as Receive Order (RO)
        
        log.debug("sent {}", simTime);
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            SimulationTimeConfig federateConfig =
                federateConfigParser.parseArgs(args, SimulationTimeConfig.class);
            SimulationTime federate =
                new SimulationTime(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        }
        catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
