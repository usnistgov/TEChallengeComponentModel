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
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // ResourcesPhysicalStatus vResourcesPhysicalStatus = new ResourcesPhysicalStatus();
    //
    ///////////////////////////////////////////////////////////////////////

    public Loads(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
        // vResourcesPhysicalStatus.registerObject(getLRC());
        //
        ///////////////////////////////////////////////////////////////////////
    }

    private void checkReceivedSubscriptions() {

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof GridVoltageState) {
                handleObjectClass((GridVoltageState) object);
            }
            else if (object instanceof ResourceControl) {
                handleObjectClass((ResourceControl) object);
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
            //    vResourcesPhysicalStatus.set_current_Imaginary_A(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_current_Imaginary_B(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_current_Imaginary_C(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_current_Real_A(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_current_Real_B(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_current_Real_C(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_gridNodeId(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_impedance_Imaginary_A(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_impedance_Imaginary_B(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_impedance_Imaginary_C(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_impedance_Real_A(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_impedance_Real_B(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_impedance_Real_C(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_loadInstanceName(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_name(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_phases(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_power_Imaginary_A(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_power_Imaginary_B(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_power_Imaginary_C(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_power_Real_A(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_power_Real_B(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_power_Real_C(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_status(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_type(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_voltage_Imaginary_A(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_voltage_Imaginary_B(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_voltage_Imaginary_C(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_voltage_Real_A(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_voltage_Real_B(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.set_voltage_Real_C(<YOUR VALUE HERE >);
            //    vResourcesPhysicalStatus.updateAttributeValues(getLRC(), currentTime + getLookAhead());
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

    private void handleObjectClass(GridVoltageState object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(ResourceControl object) {
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
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
