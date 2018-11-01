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
    // resourceControl vresourceControl = new resourceControl();
    //
    ///////////////////////////////////////////////////////////////////////

    public LocalController(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
        // vresourceControl.registerObject(getLRC());
        //
        ///////////////////////////////////////////////////////////////////////
    }

    private void checkReceivedSubscriptions() {

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof supervisoryControlSignal) {
                handleObjectClass((supervisoryControlSignal) object);
            }
            else if (object instanceof resourcesPhysicalStatus) {
                handleObjectClass((resourcesPhysicalStatus) object);
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
            //    vresourceControl.set_Resources(<YOUR VALUE HERE >);
            //    vresourceControl.set_activePowerCurve(<YOUR VALUE HERE >);
            //    vresourceControl.set_actualDemand(<YOUR VALUE HERE >);
            //    vresourceControl.set_adjustedFullDRPower(<YOUR VALUE HERE >);
            //    vresourceControl.set_adjustedNoDRPower(<YOUR VALUE HERE >);
            //    vresourceControl.set_downBeginRamp(<YOUR VALUE HERE >);
            //    vresourceControl.set_downDuration(<YOUR VALUE HERE >);
            //    vresourceControl.set_downRampToCompletion(<YOUR VALUE HERE >);
            //    vresourceControl.set_downRate(<YOUR VALUE HERE >);
            //    vresourceControl.set_loadStatusType(<YOUR VALUE HERE >);
            //    vresourceControl.set_locked(<YOUR VALUE HERE >);
            //    vresourceControl.set_maximumReactivePower(<YOUR VALUE HERE >);
            //    vresourceControl.set_maximumRealPower(<YOUR VALUE HERE >);
            //    vresourceControl.set_reactiveDesiredFractionOfFullRatedOutputBegin(<YOUR VALUE HERE >);
            //    vresourceControl.set_reactiveDesiredFractionOfFullRatedOutputEnd(<YOUR VALUE HERE >);
            //    vresourceControl.set_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin(<YOUR VALUE HERE >);
            //    vresourceControl.set_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd(<YOUR VALUE HERE >);
            //    vresourceControl.set_realDesiredFractionOfFullRatedOutputBegin(<YOUR VALUE HERE >);
            //    vresourceControl.set_realDesiredFractionOfFullRatedOutputEnd(<YOUR VALUE HERE >);
            //    vresourceControl.set_realRequiredFractionOfFullRatedInputPowerDrawnBegin(<YOUR VALUE HERE >);
            //    vresourceControl.set_realRequiredFractionOfFullRatedInputPowerDrawnEnd(<YOUR VALUE HERE >);
            //    vresourceControl.set_upBeginRamp(<YOUR VALUE HERE >);
            //    vresourceControl.set_upDuration(<YOUR VALUE HERE >);
            //    vresourceControl.set_upRampToCompletion(<YOUR VALUE HERE >);
            //    vresourceControl.set_upRate(<YOUR VALUE HERE >);
            //    vresourceControl.updateAttributeValues(getLRC(), currentTime + getLookAhead());
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

    private void handleObjectClass(supervisoryControlSignal object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(resourcesPhysicalStatus object) {
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
