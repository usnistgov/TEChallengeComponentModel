package TEChallenge;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.utils.CpswtDefaults;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The LocalController type of federate for the federation designed in WebGME.
 *
 */
public class LocalController extends LocalControllerBase {

    private final static Logger log = LogManager.getLogger(LocalController.class);

    double currentTime = 0;

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

    private void CheckReceivedSubscriptions(String s) {

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof resourcesPhysicalStatus) {
                handleObjectClass((resourcesPhysicalStatus) object);
            }
            else if (object instanceof supervisoryControlSignal) {
                handleObjectClass((supervisoryControlSignal) object);
            }
            log.info("Object received and handled: " + s);
        }
    }

    private void execute() throws Exception {
        if(super.isLateJoiner()) {
            currentTime = super.getLBTS() - super.getLookAhead();
            super.disableTimeRegulation();
        }

        /////////////////////////////////////////////
        // TODO perform basic initialization below //
        /////////////////////////////////////////////

        AdvanceTimeRequest atr = new AdvanceTimeRequest(currentTime);
        putAdvanceTimeRequest(atr);

        if(!super.isLateJoiner()) {
            readyToPopulate();
        }

        ///////////////////////////////////////////////////////////////////////
        // Call CheckReceivedSubscriptions(<message>) here to receive
        // subscriptions published before the first time step.
        ///////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////
        // TODO perform initialization that depends on other federates below //
        ///////////////////////////////////////////////////////////////////////

        if(!super.isLateJoiner()) {
            readyToRun();
        }

        startAdvanceTimeThread();

        // this is the exit condition of the following while loop
        // it is used to break the loop so that latejoiner federates can
        // notify the federation manager that they left the federation
        boolean exitCondition = false;

        while (true) {
            currentTime += super.getStepSize();

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
            //    vresourceControl.updateAttributeValues(getLRC(), currentTime);
            //
            //////////////////////////////////////////////////////////////////////////////////////////

            CheckReceivedSubscriptions("Main Loop");

            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // DO NOT MODIFY FILE BEYOND THIS LINE
            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            AdvanceTimeRequest newATR = new AdvanceTimeRequest(currentTime);
            putAdvanceTimeRequest(newATR);
            atr.requestSyncEnd();
            atr = newATR;

            if(exitCondition) {
                break;
            }
        }

        // while loop finished, notify FederationManager about resign
        super.notifyFederationOfResign();
    }

    private void handleObjectClass(resourcesPhysicalStatus object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(supervisoryControlSignal object) {
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

            System.exit(0);
        } catch (Exception e) {
            log.error("There was a problem executing the LocalController federate: {}", e.getMessage());
            log.error(e);

            System.exit(1);
        }
    }
}
