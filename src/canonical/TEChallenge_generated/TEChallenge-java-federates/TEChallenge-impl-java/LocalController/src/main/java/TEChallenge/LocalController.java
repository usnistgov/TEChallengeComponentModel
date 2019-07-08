package TEChallenge;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The LocalController type of federate for the federation designed in WebGME.
 *
 */
public class LocalController extends LocalControllerBase {
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // ResourceControl vResourceControl = new ResourceControl();
    //
    ///////////////////////////////////////////////////////////////////////

    public LocalController(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
        // vResourceControl.registerObject(getLRC());
        //
        ///////////////////////////////////////////////////////////////////////
    }

    private void checkReceivedSubscriptions() {

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof SupervisoryControlSignal) {
                handleObjectClass((SupervisoryControlSignal) object);
            }
            else if (object instanceof ResourcesPhysicalStatus) {
                handleObjectClass((ResourcesPhysicalStatus) object);
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

        /////////////////////////////////////////////
        // TODO perform basic initialization below //
        /////////////////////////////////////////////

        AdvanceTimeRequest atr = new AdvanceTimeRequest(currentTime);
        putAdvanceTimeRequest(atr);

        if(!super.isLateJoiner()) {
            log.info("waiting on readyToPopulate...");
            readyToPopulate();
            log.info("...synchronized on readyToPopulate");
        }

        ///////////////////////////////////////////////////////////////////////
        // TODO perform initialization that depends on other federates below //
        ///////////////////////////////////////////////////////////////////////

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

            ////////////////////////////////////////////////////////////////////////////////////////
            // TODO objects that must be sent every logical time step
            //
            //    vResourceControl.set_Resources(<YOUR VALUE HERE >);
            //    vResourceControl.set_activePowerCurve(<YOUR VALUE HERE >);
            //    vResourceControl.set_actualDemand(<YOUR VALUE HERE >);
            //    vResourceControl.set_adjustedFullDRPower(<YOUR VALUE HERE >);
            //    vResourceControl.set_adjustedNoDRPower(<YOUR VALUE HERE >);
            //    vResourceControl.set_downBeginRamp(<YOUR VALUE HERE >);
            //    vResourceControl.set_downDuration(<YOUR VALUE HERE >);
            //    vResourceControl.set_downRampToCompletion(<YOUR VALUE HERE >);
            //    vResourceControl.set_downRate(<YOUR VALUE HERE >);
            //    vResourceControl.set_loadStatusType(<YOUR VALUE HERE >);
            //    vResourceControl.set_locked(<YOUR VALUE HERE >);
            //    vResourceControl.set_maximumReactivePower(<YOUR VALUE HERE >);
            //    vResourceControl.set_maximumRealPower(<YOUR VALUE HERE >);
            //    vResourceControl.set_reactiveDesiredFractionOfFullRatedOutputBegin(<YOUR VALUE HERE >);
            //    vResourceControl.set_reactiveDesiredFractionOfFullRatedOutputEnd(<YOUR VALUE HERE >);
            //    vResourceControl.set_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin(<YOUR VALUE HERE >);
            //    vResourceControl.set_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd(<YOUR VALUE HERE >);
            //    vResourceControl.set_realDesiredFractionOfFullRatedOutputBegin(<YOUR VALUE HERE >);
            //    vResourceControl.set_realDesiredFractionOfFullRatedOutputEnd(<YOUR VALUE HERE >);
            //    vResourceControl.set_realRequiredFractionOfFullRatedInputPowerDrawnBegin(<YOUR VALUE HERE >);
            //    vResourceControl.set_realRequiredFractionOfFullRatedInputPowerDrawnEnd(<YOUR VALUE HERE >);
            //    vResourceControl.set_upBeginRamp(<YOUR VALUE HERE >);
            //    vResourceControl.set_upDuration(<YOUR VALUE HERE >);
            //    vResourceControl.set_upRampToCompletion(<YOUR VALUE HERE >);
            //    vResourceControl.set_upRate(<YOUR VALUE HERE >);
            //    vResourceControl.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //
            //////////////////////////////////////////////////////////////////////////////////////////

            checkReceivedSubscriptions();

            ////////////////////////////////////////////////////////////////////////////////////////
            // TODO break here if ready to resign and break out of while loop
            ////////////////////////////////////////////////////////////////////////////////////////


            if (!exitCondition) {
                currentTime += super.getStepSize();
                AdvanceTimeRequest newATR = new AdvanceTimeRequest(currentTime);
                putAdvanceTimeRequest(newATR);
                atr.requestSyncEnd();
                atr = newATR;
            }
        }

        // call exitGracefully to shut down federate
        exitGracefully();

        ////////////////////////////////////////////////////////////////////////////////////////
        // TODO Perform whatever cleanups needed before exiting the app
        ////////////////////////////////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(SupervisoryControlSignal object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(ResourcesPhysicalStatus object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            FederateConfig federateConfig = federateConfigParser.parseArgs(args, FederateConfig.class);
            LocalController federate = new LocalController(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
