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
    int numberOfInstances=10 ;   
    private LoadsConfig configuration;  
    public Load[] loads = new Load[numberOfInstances];                                                   
    resourcesPhysicalStatus[] vresourcesPhysicalStatus= new resourcesPhysicalStatus[numberOfInstances];

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // resourcesPhysicalStatus vresourcesPhysicalStatus = new resourcesPhysicalStatus();
    //
    ///////////////////////////////////////////////////////////////////////

    public Loads(LoadsConfig params) throws Exception {
        super(params);

        
        this.configuration = params; 
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
            log.info("Object received and handled: " + s);
        }
    }
    
     private void updateInstances(int numberOfInstances) {
        log.trace("updateOperandInstances");
        
        for (int i = 0; i < numberOfInstances; i++) {
            
            vresourcesPhysicalStatus[i].set_loadInstanceName( configuration.loads[i].loadInstanceName);
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
