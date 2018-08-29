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
    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
     gridVoltageState vgridVoltageState = new gridVoltageState();
    //
    ///////////////////////////////////////////////////////////////////////

    public Grid(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
         vgridVoltageState.registerObject(getLRC());
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



        boolean exitCondition = false;

        while (true) {
            currentTime += super.getStepSize();

            atr.requestSyncStart();
            enteredTimeGrantedState();
            
                vgridVoltageState.set_grid_Voltage_Imaginary_A(00+(float)currentTime);
                vgridVoltageState.set_grid_Voltage_Imaginary_B(11+(float)currentTime);
                vgridVoltageState.set_grid_Voltage_Imaginary_C(22+(float)currentTime);
                vgridVoltageState.set_grid_Voltage_Real_A(33+(float)currentTime);
                vgridVoltageState.set_grid_Voltage_Real_B(44+(float)currentTime);
                vgridVoltageState.set_grid_Voltage_Real_C(55+(float)currentTime);
                vgridVoltageState.updateAttributeValues(getLRC(), currentTime);
            
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
         log.info("name: " + object.get_name());
         log.info("loadInstance: " + object.get_loadInstanceName());
         log.info("gridNodeId: " + object.get_gridNodeId());
         log.info("phases: " + object.get_phases());
         log.info("status: " + object.get_status());
         log.info("type: " + object.get_type());

         log.info("voltage_Real_A: " + object.get_voltage_Real_A());
         log.info("voltage_Imaginary_A: " + object.get_voltage_Imaginary_A());
         log.info("voltage_Real_B: " + object.get_voltage_Real_B());
         log.info("voltage_Imaginary_B: " + object.get_voltage_Imaginary_B());
         log.info("voltage_Real_C: " + object.get_voltage_Real_C());
         log.info("voltage_Imaginary_C: " + object.get_voltage_Imaginary_C());

         log.info("current_Real_A: " + object.get_current_Real_A());
         log.info("current_Imaginary_A: " + object.get_current_Imaginary_A());
         log.info("current_Real_B: " + object.get_current_Real_B());
         log.info("current_Imaginary_B: " + object.get_current_Imaginary_B());
         log.info("current_Real_C: " + object.get_current_Real_C());
         log.info("current_Imaginary_C: " + object.get_current_Imaginary_C());

         log.info("impedance_Real_A: " + object.get_impedance_Real_A());
         log.info("impedance_Imaginary_A: " + object.get_impedance_Imaginary_A());
         log.info("impedance_Real_B: " + object.get_impedance_Real_B());
         log.info("impedance_Imaginary_B: " + object.get_impedance_Imaginary_B());
         log.info("impedance_Real_C: " + object.get_impedance_Real_C());
         log.info("impedance_Imaginary_C: " + object.get_impedance_Imaginary_C());

         log.info("power_Real_A: " + object.get_power_Real_A());
         log.info("power_Imaginary_A: " + object.get_power_Imaginary_A());
         log.info("power_Real_B: " + object.get_power_Real_B());
         log.info("power_Imaginary_B: " + object.get_power_Imaginary_B());
         log.info("power_Real_C: " + object.get_power_Real_C());
         log.info("power_Imaginary_C: " + object.get_power_Imaginary_C());

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
