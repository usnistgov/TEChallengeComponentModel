package Grid;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.utils.CpswtDefaults;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Grid type of federate for the federation designed in WebGME.
 *
 */
public class Grid extends GridBase {

    private final static Logger log = LogManager.getLogger(Grid.class);

    double currentTime = 0;
    double To = 0;
    int numberOfInstances =10;
    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // gridVoltageState vgridVoltageState = new gridVoltageState();
    //
    ///////////////////////////////////////////////////////////////////////

    public Grid(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
        // vgridVoltageState.registerObject(getLRC());
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
            //    vgridVoltageState.set_grid_Voltage_Imaginary_A(<YOUR VALUE HERE >);
            //    vgridVoltageState.set_grid_Voltage_Imaginary_B(<YOUR VALUE HERE >);
            //    vgridVoltageState.set_grid_Voltage_Imaginary_C(<YOUR VALUE HERE >);
            //    vgridVoltageState.set_grid_Voltage_Real_A(<YOUR VALUE HERE >);
            //    vgridVoltageState.set_grid_Voltage_Real_B(<YOUR VALUE HERE >);
            //    vgridVoltageState.set_grid_Voltage_Real_C(<YOUR VALUE HERE >);
            //    vgridVoltageState.updateAttributeValues(getLRC(), currentTime);
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
        To = object.get_voltage_Real_A();
         log.info(
                "voltage_Real_A: " + To);

        
     /*  for (int i = 0; i < numberOfInstances; i++) {
            
            
            // 1. Set the new attribute values in local memory using the WebGME code generated methods.
            
            vresourcesPhysical.get_voltage_Real_A(); // this method will be generated as set_<attributeName>
             vresourcesPhysicalStatus[i].get_voltage_Imaginary_A();
          log.info(  "voltage_Real_A: " + voltage_Real_A);
            // 2. Publish the updates to HLA for the next logical time step (currentTime has already been incremented)
           
            
        } */
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            FederateConfig federateConfig = federateConfigParser.parseArgs(args, FederateConfig.class);
            Grid federate = new Grid(federateConfig);
            federate.execute();

            System.exit(0);
        } catch (Exception e) {
            log.error("There was a problem executing the Grid federate: {}", e.getMessage());
            log.error(e);

            System.exit(1);
        }
    }
}
