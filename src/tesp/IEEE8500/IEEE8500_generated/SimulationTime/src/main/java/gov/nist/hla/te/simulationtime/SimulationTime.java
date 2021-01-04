package gov.nist.hla.te.simulationtime;

import gov.nist.hla.te.simulationtime.rti.*;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A federate that provides the SimTime interaction prior to logical time progression.
 */
public class SimulationTime extends SimulationTimeBase {
    private final static Logger log = LogManager.getLogger();

    private SimulationTimeConfig configuration = null;
    
    private double currentTime = 0;

    public SimulationTime(SimulationTimeConfig params) throws Exception {
        super(params);
        this.configuration = params;
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

        SimTime simTime = create_SimTime();
        simTime.set_timeScale(configuration.timeScale);
        simTime.set_timeZone(configuration.timeZone);
        simTime.set_timeZonePosix(configuration.timeZonePosix);
        simTime.set_unixTimeStart(configuration.unixTimeStart);
        simTime.set_unixTimeStop(configuration.unixTimeStop);
        simTime.sendInteraction(getLRC()); // send RO
        log.info("sent SimTime interaction");

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
            
            // re-send if new federate joins
            // maybe make it a request-response ?

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
