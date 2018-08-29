package Loads;

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
 * The Loads type of federate for the federation designed in WebGME.
 *
 */
public class Loads extends LoadsBase {

    private final static Logger log = LogManager.getLogger(Loads.class);

    double currentTime = 0;
    int numberOfInstances;   
    private LoadsConfig configuration;  
    public Load[] loads = null;                                                   
    resourcesPhysicalStatus[] vresourcesPhysicalStatus= null ;


    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // resourcesPhysicalStatus vresourcesPhysicalStatus = new resourcesPhysicalStatus();
    //
    ///////////////////////////////////////////////////////////////////////

    public Loads(LoadsConfig params) throws Exception {
        super(params);

        
        this.configuration = params; 
        
        numberOfInstances = configuration.number;
        loads = new Load[numberOfInstances];  
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
                  loads[i] = new Load();
                      try {
						vresourcesPhysicalStatus[i].registerObject(getLRC(), configuration.loads[i].loadInstanceName);
					} catch (ObjectAlreadyRegistered e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                      
                              
            }
        }
        

    private void CheckReceivedSubscriptions(String s) {

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof gridVoltageState) {
                handleObjectClass((gridVoltageState) object);
            }
            if (object instanceof resourceControl) {
                handleObjectClass((resourceControl) object);
            }
            log.info("---------------------------Object received and handled:------------------------------- " + s);
        }
    }
    
     private void updateInstances(int numberOfInstances) {
        log.trace("...................................updating Resourse Physical Status Instances.............................................");
        
        for (int i = 0; i < numberOfInstances; i++) {
            
            vresourcesPhysicalStatus[i].set_loadInstanceName( configuration.loads[i].loadInstanceName);
            vresourcesPhysicalStatus[i].set_name( configuration.loads[i].name);
            vresourcesPhysicalStatus[i].set_type( configuration.loads[i].type);
            vresourcesPhysicalStatus[i].set_gridNodeId( configuration.loads[i].gNodeId);
            vresourcesPhysicalStatus[i].set_phases( configuration.loads[i].phase);
            vresourcesPhysicalStatus[i].set_status( configuration.loads[i].state);

            vresourcesPhysicalStatus[i].set_voltage_Real_A( configuration.loads[i].voltageRealA+(float)currentTime); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_voltage_Imaginary_A(configuration.loads[i].voltageImaginaryA+(float)currentTime);
            vresourcesPhysicalStatus[i].set_voltage_Real_B( configuration.loads[i].voltageRealB); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_voltage_Imaginary_B(configuration.loads[i].voltageImaginaryB);
            vresourcesPhysicalStatus[i].set_voltage_Real_C( configuration.loads[i].voltageRealC); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_voltage_Imaginary_C(configuration.loads[i].voltageImaginaryC);
           
            vresourcesPhysicalStatus[i].set_current_Real_A( configuration.loads[i].currentRealA+(float)currentTime); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_current_Imaginary_A(configuration.loads[i].currentImaginaryA+(float)currentTime);
            vresourcesPhysicalStatus[i].set_current_Real_B( configuration.loads[i].currentRealB); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_current_Imaginary_B(configuration.loads[i].currentImaginaryB);
            vresourcesPhysicalStatus[i].set_current_Real_C( configuration.loads[i].currentRealC); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_current_Imaginary_C(configuration.loads[i].currentImaginaryC);
           
            vresourcesPhysicalStatus[i].set_impedance_Real_A( configuration.loads[i].impedanceRealA); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_impedance_Imaginary_A(configuration.loads[i].impedanceImaginaryA);
            vresourcesPhysicalStatus[i].set_impedance_Real_B( configuration.loads[i].impedanceRealB); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_impedance_Imaginary_B(configuration.loads[i].impedanceImaginaryB);
            vresourcesPhysicalStatus[i].set_impedance_Real_C( configuration.loads[i].impedanceRealC); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_impedance_Imaginary_C(configuration.loads[i].impedanceImaginaryC);

            vresourcesPhysicalStatus[i].set_power_Real_A( configuration.loads[i].powerRealA+(float)currentTime*(float)currentTime); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_power_Imaginary_A(configuration.loads[i].powerImaginaryA+(float)currentTime*(float)currentTime);
            vresourcesPhysicalStatus[i].set_power_Real_B( configuration.loads[i].powerRealB); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_power_Imaginary_B(configuration.loads[i].powerImaginaryB);
            vresourcesPhysicalStatus[i].set_power_Real_C( configuration.loads[i].powerRealC); // this method will be generated as set_<attributeName>
            vresourcesPhysicalStatus[i].set_power_Imaginary_C(configuration.loads[i].powerImaginaryC);



            // 2. Publish the updates to HLA for the next logical time step (currentTime has already been incremented)
            vresourcesPhysicalStatus[i].updateAttributeValues(getLRC(), currentTime);
            
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

        if(!super.isLateJoiner()) {
            readyToRun();
        }

        startAdvanceTimeThread();


        boolean exitCondition = false;

        while (true) {
            currentTime += super.getStepSize();

            atr.requestSyncStart();
            enteredTimeGrantedState();
            updateInstances(numberOfInstances);
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
         log.info("loadStatusType: " + object.get_loadStatusType());
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
            LoadsConfig federateConfig = federateConfigParser.parseArgs(args, LoadsConfig.class);
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
