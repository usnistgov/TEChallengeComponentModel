package Grid;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.InteractionRoot;
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

    private double currentTime = 0;

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // GridVoltageState vGridVoltageState = new GridVoltageState();
    //
    ///////////////////////////////////////////////////////////////////////

    public Grid(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
        // vGridVoltageState.registerObject(getLRC());
        //
        ///////////////////////////////////////////////////////////////////////
    }

    private void checkReceivedSubscriptions() {

        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof TMYWeather) {
                handleInteractionClass((TMYWeather) interaction);
            }
            else if (interaction instanceof SimTime) {
                handleInteractionClass((SimTime) interaction);
            }
            else {
                log.debug("unhandled interaction: {}", interaction.getClassName());
            }
        }
 
        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof ResourcesPhysicalStatus) {
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
            
                vgridVoltageState.set_grid_Voltage_Imaginary_A(00+(float)currentTime);
                vgridVoltageState.set_grid_Voltage_Imaginary_B(11+(float)currentTime);
                vgridVoltageState.set_grid_Voltage_Imaginary_C(22+(float)currentTime);
                vgridVoltageState.set_grid_Voltage_Real_A(33+(float)currentTime);
                vgridVoltageState.set_grid_Voltage_Real_B(44+(float)currentTime);
                vgridVoltageState.set_grid_Voltage_Real_C(55+(float)currentTime);
                vgridVoltageState.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            

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

    private void handleInteractionClass(TMYWeather interaction) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction            //
        //////////////////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(SimTime interaction) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction            //
        //////////////////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(ResourcesPhysicalStatus object) {
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
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);

            System.exit(1);
        }
    }
}
