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
 * The Load type of federate for the federation designed in WebGME.
 *
 */
public class Load extends LoadBase {

    private final static Logger log = LogManager.getLogger(Load.class);

    double currentTime = 0;

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // resourcesPhysicalStatus vresourcesPhysicalStatus = new resourcesPhysicalStatus();
    //
    ///////////////////////////////////////////////////////////////////////

    public Load(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
        // vresourcesPhysicalStatus.registerObject(getLRC());
        //
        ///////////////////////////////////////////////////////////////////////
    }

    private void CheckReceivedSubscriptions(String s) {

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof gridVoltageState) {
                handleObjectClass((gridVoltageState) object);
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
            //    vresourcesPhysicalStatus.set_current_Imaginary_A(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_current_Imaginary_B(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_current_Imaginary_C(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_current_Real_A(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_current_Real_B(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_current_Real_C(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_gridNodeId(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_impedance_Imaginary_A(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_impedance_Imaginary_B(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_impedance_Imaginary_C(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_impedance_Real_A(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_impedance_Real_B(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_impedance_Real_C(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_phases(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_power_Imaginary_A(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_power_Imaginary_B(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_power_Imaginary_C(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_power_Real_A(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_power_Real_B(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_power_Real_C(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_status(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_voltage_Imaginary_A(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_voltage_Imaginary_B(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_voltage_Imaginary_C(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_voltage_Real_A(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_voltage_Real_B(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_voltage_Real_C(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.updateAttributeValues(getLRC(), currentTime);
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

    private void handleObjectClass(gridVoltageState object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            FederateConfig federateConfig = federateConfigParser.parseArgs(args, FederateConfig.class);
            Load federate = new Load(federateConfig);
            federate.execute();

            System.exit(0);
        } catch (Exception e) {
            log.error("There was a problem executing the Load federate: {}", e.getMessage());
            log.error(e);

            System.exit(1);
        }
    }
}
