package Storage;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.utils.CpswtDefaults;

import hla.rti.ObjectAlreadyRegistered;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Storage type of federate for the federation designed in WebGME.
 *
 */
public class Storage extends StorageBase {
    private final static Logger log = LogManager.getLogger(Storage.class);

    private double currentTime = 0;
    int numberOfInstances;   
    private StorageConfig configuration;  
    public Store[] stores = null;                                                   
    ResourcesPhysicalStatus[] vResourcesPhysicalStatus= null ;

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // ResourcesPhysicalStatus vResourcesPhysicalStatus = new ResourcesPhysicalStatus();
    //
    ///////////////////////////////////////////////////////////////////////

    public Storage(StorageConfig params) throws Exception {
        super(params);

        this.configuration =  params; 
        
        numberOfInstances = configuration.number;
        stores = new Store[numberOfInstances];  
        vResourcesPhysicalStatus= new ResourcesPhysicalStatus[numberOfInstances];  
        
     //method to register the number of Instances with RTI
       registerInstances(numberOfInstances);
    }

	private void registerInstances(int numberOfInstances) {
		// log.trace("registerInstances {}", numberOfInstances);

		for (int i = 0; i < numberOfInstances; i++) {
			// 1. Create the object instance using the appropriate WebGME code
			// generated Java class.
			vResourcesPhysicalStatus[i] = new ResourcesPhysicalStatus();
			// 2. Register the object instance with the HLA local runtime
			// component (LRC).
			stores[i] = new Store();
			try {
				vResourcesPhysicalStatus[i].registerObject(getLRC(), configuration.stores[i].loadInstanceName);
			} catch (ObjectAlreadyRegistered e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
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


     private void updateInstances(int numberOfInstances) {
        log.trace("...................................updating Resourse Physical Status Instances.............................................");
        
        for (int i = 0; i < numberOfInstances; i++) {
            
            vResourcesPhysicalStatus[i].set_loadInstanceName( configuration.stores[i].loadInstanceName);
            vResourcesPhysicalStatus[i].set_name( configuration.stores[i].name);
            vResourcesPhysicalStatus[i].set_type( configuration.stores[i].type);
            vResourcesPhysicalStatus[i].set_gridNodeId( configuration.stores[i].gNodeId);
            vResourcesPhysicalStatus[i].set_phases( configuration.stores[i].phase);
            vResourcesPhysicalStatus[i].set_status( configuration.stores[i].state);

            vResourcesPhysicalStatus[i].set_voltage_Real_A( configuration.stores[i].voltageRealA+(float)currentTime); // this method will be generated as set_<attributeName>
            vResourcesPhysicalStatus[i].set_voltage_Imaginary_A(configuration.stores[i].voltageImaginaryA+(float)currentTime);
            vResourcesPhysicalStatus[i].set_voltage_Real_B( configuration.stores[i].voltageRealB); // this method will be generated as set_<attributeName>
            vResourcesPhysicalStatus[i].set_voltage_Imaginary_B(configuration.stores[i].voltageImaginaryB);
            vResourcesPhysicalStatus[i].set_voltage_Real_C( configuration.stores[i].voltageRealC); // this method will be generated as set_<attributeName>
            vResourcesPhysicalStatus[i].set_voltage_Imaginary_C(configuration.stores[i].voltageImaginaryC);
           
            vResourcesPhysicalStatus[i].set_current_Real_A( configuration.stores[i].currentRealA+(float)currentTime); // this method will be generated as set_<attributeName>
            vResourcesPhysicalStatus[i].set_current_Imaginary_A(configuration.stores[i].currentImaginaryA+(float)currentTime);
            vResourcesPhysicalStatus[i].set_current_Real_B( configuration.stores[i].currentRealB); // this method will be generated as set_<attributeName>
            vResourcesPhysicalStatus[i].set_current_Imaginary_B(configuration.stores[i].currentImaginaryB);
            vResourcesPhysicalStatus[i].set_current_Real_C( configuration.stores[i].currentRealC); // this method will be generated as set_<attributeName>
            vResourcesPhysicalStatus[i].set_current_Imaginary_C(configuration.stores[i].currentImaginaryC);
           
            vResourcesPhysicalStatus[i].set_impedance_Real_A( configuration.stores[i].impedanceRealA); // this method will be generated as set_<attributeName>
            vResourcesPhysicalStatus[i].set_impedance_Imaginary_A(configuration.stores[i].impedanceImaginaryA);
            vResourcesPhysicalStatus[i].set_impedance_Real_B( configuration.stores[i].impedanceRealB); // this method will be generated as set_<attributeName>
            vResourcesPhysicalStatus[i].set_impedance_Imaginary_B(configuration.stores[i].impedanceImaginaryB);
            vResourcesPhysicalStatus[i].set_impedance_Real_C( configuration.stores[i].impedanceRealC); // this method will be generated as set_<attributeName>
            vResourcesPhysicalStatus[i].set_impedance_Imaginary_C(configuration.stores[i].impedanceImaginaryC);

            vResourcesPhysicalStatus[i].set_power_Real_A( configuration.stores[i].powerRealA+(float)currentTime*(float)currentTime); // this method will be generated as set_<attributeName>
            vResourcesPhysicalStatus[i].set_power_Imaginary_A(configuration.stores[i].powerImaginaryA+(float)currentTime*(float)currentTime);
            vResourcesPhysicalStatus[i].set_power_Real_B( configuration.stores[i].powerRealB); // this method will be generated as set_<attributeName>
            vResourcesPhysicalStatus[i].set_power_Imaginary_B(configuration.stores[i].powerImaginaryB);
            vResourcesPhysicalStatus[i].set_power_Real_C( configuration.stores[i].powerRealC); // this method will be generated as set_<attributeName>
            vResourcesPhysicalStatus[i].set_power_Imaginary_C(configuration.stores[i].powerImaginaryC);



            // 2. Publish the updates to HLA for the next logical time step (currentTime has already been incremented)
            vResourcesPhysicalStatus[i].updateAttributeValues(getLRC(), currentTime + getLookAhead());
            
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
            updateInstances(numberOfInstances);

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
         log.info("grid_Voltage_Imaginary_A: " + object.get_grid_Voltage_Imaginary_A());
         log.info("grid_Voltage_Imaginary_B: " + object.get_grid_Voltage_Imaginary_B());
         log.info("grid_Voltage_Imaginary_C: " + object.get_grid_Voltage_Imaginary_C());
         log.info("grid_Voltage_Real_A: " + object.get_grid_Voltage_Real_A());
         log.info("grid_Voltage_Real_B: " + object.get_grid_Voltage_Real_B());
         log.info("grid_Voltage_Real_C: " + object.get_grid_Voltage_Real_C());

    }

    private void handleObjectClass(ResourceControl object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
         log.info("actualDemand: " + object.get_actualDemand());
         log.info("locked: " + object.get_locked());
         log.info("storestatusType: " + object.get_loadStatusType());
         log.info("Resources: " + object.get_Resources());

         log.info("activePowerCurve: " + object.get_activePowerCurve());
         log.info("adjustedFullDRPower: " + object.get_adjustedFullDRPower());
         log.info("adjustedNoDRPower: " + object.get_adjustedNoDRPower());

         log.info("maximumReactivePower: " + object.get_maximumReactivePower());
         log.info("maximumRealPower: " + object.get_maximumRealPower());

         log.info("downBeginRamp: " + object.get_downBeginRamp());
         log.info("downDuration: " + object.get_downDuration());
         log.info("downRampToCompletion: " + object.get_downRampToCompletion());
         log.info("downRate: " + object.get_downRate());

         log.info("reactiveDesiredFractionOfFullRatedOutputBegin: " + object.get_reactiveDesiredFractionOfFullRatedOutputBegin());
         log.info("reactiveDesiredFractionOfFullRatedOutputEnd: " + object.get_reactiveDesiredFractionOfFullRatedOutputEnd());
         log.info("reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin: " + object.get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin());
         log.info("reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd: " + object.get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd());
         
         log.info("realDesiredFractionOfFullRatedOutputBegin: " + object.get_realDesiredFractionOfFullRatedOutputBegin());
         log.info("realDesiredFractionOfFullRatedOutputEnd: " + object.get_realDesiredFractionOfFullRatedOutputEnd());
         log.info("realRequiredFractionOfFullRatedInputPowerDrawnBegin: " + object.get_realRequiredFractionOfFullRatedInputPowerDrawnBegin());
         log.info("realRequiredFractionOfFullRatedInputPowerDrawnEnd: " + object.get_realRequiredFractionOfFullRatedInputPowerDrawnEnd());

         log.info("upBeginRamp: " + object.get_upBeginRamp());
         log.info("upDuration: " + object.get_upDuration());
         log.info("upRampToCompletion: " + object.get_upRampToCompletion());
         log.info("upRate: " + object.get_upRate());
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            StorageConfig federateConfig = federateConfigParser.parseArgs(args, StorageConfig.class);
            Storage federate = new Storage(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
