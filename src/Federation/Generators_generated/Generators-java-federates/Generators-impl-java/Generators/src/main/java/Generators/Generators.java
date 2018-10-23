package Generators;

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
 * The Generators type of federate for the federation designed in WebGME.
 *
 */
public class Generators extends GeneratorsBase {
    private final static Logger log = LogManager.getLogger(Generators.class);

    private double currentTime = 0;
    int numberOfInstances;   
    private GeneratorsConfig configuration;  
    public Generator[] generators = null;                                                   
    resourcesPhysicalStatus[] vresourcesPhysicalStatus= null ;

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // resourcesPhysicalStatus vresourcesPhysicalStatus = new resourcesPhysicalStatus();
    //
    ///////////////////////////////////////////////////////////////////////

    public Generators(FederateConfig params) throws Exception {
        super(params);

        
        this.configuration = (GeneratorsConfig) params; 
        
        numberOfInstances = configuration.number;
        generators = new Generator[numberOfInstances];  
        vresourcesPhysicalStatus= new resourcesPhysicalStatus[numberOfInstances];  
     //method to register the number of Instances with RTI
       registerInstances(numberOfInstances);
    }

    private void registerInstances(int numberOfInstances ) {
           // log.trace("registerInstances {}", numberOfInstances);
            
           
            for (int i = 0; i < numberOfInstances; i++) {
                // 1. Create the object instance using the appropriate WebGME code generated Java class.
                 vresourcesPhysicalStatus[i] = new resourcesPhysicalStatus();
                // 2. Register the object instance with the HLA local runtime component (LRC).
                  generators[i] = new Generator();
                      try {
						vresourcesPhysicalStatus[i].registerObject(getLRC(), configuration.generators[i].loadInstanceName);
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
            if (object instanceof gridVoltageState) {
                handleObjectClass((gridVoltageState) object);
            }
            else if (object instanceof resourceControl) {
                handleObjectClass((resourceControl) object);
            }
            else {
                log.debug("unhandled object reflection: {}", object.getClassName());
            }
        }
    }


     private void updateInstances(int numberOfInstances) {
        log.trace("...................................updating Resourse Physical Status Instances.............................................");
        
        for (int i = 0; i < numberOfInstances; i++) {
            
            vresourcesPhysicalStatus[i].set_loadInstanceName( configuration.generators[i].loadInstanceName);
            vresourcesPhysicalStatus[i].set_name( configuration.generators[i].name);
            vresourcesPhysicalStatus[i].set_type( configuration.generators[i].type);
            vresourcesPhysicalStatus[i].set_gridNodeId( configuration.generators[i].gNodeId);
            vresourcesPhysicalStatus[i].set_phases( configuration.generators[i].phase);
            vresourcesPhysicalStatus[i].set_status( configuration.generators[i].state);

            vresourcesPhysicalStatus[i].set_voltage_Real_A( configuration.generators[i].voltageRealA+(float)currentTime); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_voltage_Imaginary_A(configuration.generators[i].voltageImaginaryA+(float)currentTime);
            vresourcesPhysicalStatus[i].set_voltage_Real_B( configuration.generators[i].voltageRealB); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_voltage_Imaginary_B(configuration.generators[i].voltageImaginaryB);
            vresourcesPhysicalStatus[i].set_voltage_Real_C( configuration.generators[i].voltageRealC); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_voltage_Imaginary_C(configuration.generators[i].voltageImaginaryC);
           
            vresourcesPhysicalStatus[i].set_current_Real_A( configuration.generators[i].currentRealA+(float)currentTime); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_current_Imaginary_A(configuration.generators[i].currentImaginaryA+(float)currentTime);
            vresourcesPhysicalStatus[i].set_current_Real_B( configuration.generators[i].currentRealB); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_current_Imaginary_B(configuration.generators[i].currentImaginaryB);
            vresourcesPhysicalStatus[i].set_current_Real_C( configuration.generators[i].currentRealC); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_current_Imaginary_C(configuration.generators[i].currentImaginaryC);
           
            vresourcesPhysicalStatus[i].set_impedance_Real_A( configuration.generators[i].impedanceRealA); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_impedance_Imaginary_A(configuration.generators[i].impedanceImaginaryA);
            vresourcesPhysicalStatus[i].set_impedance_Real_B( configuration.generators[i].impedanceRealB); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_impedance_Imaginary_B(configuration.generators[i].impedanceImaginaryB);
            vresourcesPhysicalStatus[i].set_impedance_Real_C( configuration.generators[i].impedanceRealC); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_impedance_Imaginary_C(configuration.generators[i].impedanceImaginaryC);

            vresourcesPhysicalStatus[i].set_power_Real_A( configuration.generators[i].powerRealA+(float)currentTime*(float)currentTime); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_power_Imaginary_A(configuration.generators[i].powerImaginaryA+(float)currentTime*(float)currentTime);
            vresourcesPhysicalStatus[i].set_power_Real_B( configuration.generators[i].powerRealB); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_power_Imaginary_B(configuration.generators[i].powerImaginaryB);
            vresourcesPhysicalStatus[i].set_power_Real_C( configuration.generators[i].powerRealC); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_power_Imaginary_C(configuration.generators[i].powerImaginaryC);



            // 2. Publish the updates to HLA for the next logical time step (currentTime has already been incremented)
            vresourcesPhysicalStatus[i].updateAttributeValues(getLRC(), currentTime + getLookAhead());
            
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

    private void handleObjectClass(gridVoltageState object) {
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

    private void handleObjectClass(resourceControl object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
         log.info("actualDemand: " + object.get_actualDemand());
         log.info("locked: " + object.get_locked());
         log.info("generatorstatusType: " + object.get_loadStatusType());
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
            GeneratorsConfig federateConfig = federateConfigParser.parseArgs(args, GeneratorsConfig.class);
            Generators federate = new Generators(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
