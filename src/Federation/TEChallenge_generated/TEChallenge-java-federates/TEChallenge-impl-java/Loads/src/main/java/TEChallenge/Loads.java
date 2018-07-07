package TEChallenge;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Loads type of federate for the federation designed in WebGME.
 *
 */
public class Loads extends LoadsBase {

    private final static Logger log = LogManager.getLogger(Loads.class);

    double currentTime = 0;

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // resourcesPhysicalStatus vresourcesPhysicalStatus = new resourcesPhysicalStatus();
    //
    ///////////////////////////////////////////////////////////////////////

    public Loads(FederateConfig params) throws Exception {
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

        while (exitCondition == false) {
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
            //    vresourcesPhysicalStatus.set_loadInstanceName(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_phases(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_power_Imaginary_A(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_power_Imaginary_B(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_power_Imaginary_C(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_power_Real_A(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_power_Real_B(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_power_Real_C(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_status(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_type(<YOUR VALUE HERE >);
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

            ////////////////////////////////////////////////////////////////////////////////////////
            // TODO break here if ready to resign and break out of while loop
            //	break;
            ////////////////////////////////////////////////////////////////////////////////////////


            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // DO NOT MODIFY Method BEYOND THIS LINE
            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            AdvanceTimeRequest newATR = new AdvanceTimeRequest(currentTime);
            putAdvanceTimeRequest(newATR);
            atr.requestSyncEnd();
            atr = newATR;

        }

		// call exitGracefully to shut down federate
        exitGracefully();

        ////////////////////////////////////////////////////////////////////////////////////////
        // TODO Perform whatever cleanups needed to before exiting the app
        ////////////////////////////////////////////////////////////////////////////////////////


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
            Loads federate = new Loads(federateConfig);
            federate.execute();

            System.exit(0);
        } catch (Exception e) {
            log.error("There was a problem executing the Loads federate: {}", e.getMessage());
            log.error(e);

            System.exit(1);
        }
    }
}
