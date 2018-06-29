package Load;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.utils.CpswtDefaults;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * The Load type of federate for the federation designed in WebGME.
 *
 */
public class Load extends LoadBase {

    private final static Logger log = LogManager.getLogger(Load.class);

    double currentTime = 0;
    int numberOfInstances =2;
    resourcesPhysicalStatus[] vresourcesPhysicalStatus= new resourcesPhysicalStatus[10];
   private LoadConfig configuration;  
   public List<House> houses = new ArrayList<House>();                                                             //NA
   // private LoadConfig.houses[] y= new LoadConfig.houses[2] ;  
     /*for(int j =0 ;j<2; j++){  
        y[j] = new LoadConfig.houses();
       }*/
    
    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
   // resourcesPhysicalStatus vresourcesPhysicalStatus = new resourcesPhysicalStatus();
    //
    ///////////////////////////////////////////////////////////////////////
    public float voltageRA,num = 0;
    public Load(LoadConfig params) throws Exception {
        super(params);

             
       this.configuration = params; 
       registerInstances(numberOfInstances);                                                  // NA
    }
   
   //int numberOfInstances=(int) configuration.number;
   //resourcesPhysicalStatus[] vresourcesPhysicalStatus= new resourcesPhysicalStatus[numberOfInstances];

    private void registerInstances(int numberOfInstances ) {
            //log.trace("registerOperandInstances {}", numberOfInstances);
            
            for (int i = 0; i < numberOfInstances; i++) {
                // 1. Create the object instance using the appropriate WebGME code generated Java class.
            	 vresourcesPhysicalStatus[i] = new resourcesPhysicalStatus();
                // 2. Register the object instance with the HLA local runtime component (LRC).
                vresourcesPhysicalStatus[i].registerObject(getLRC());
                
            }
         //   log.info("will generate {} operands with a maximum value of {}", operands.size(), this.maxValue);
        }
        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
       // vresourcesPhysicalStatus.registerObject(getLRC());
        //
        ///////////////////////////////////////////////////////////////////////
    
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
       // log.trace("updateOperandInstances");
        
        for (int i = 0; i < numberOfInstances; i++) {
            
          //  y[i] = new LoadConfig.houses();
            // 1. Set the new attribute values in local memory using the WebGME code generated methods.
            
            vresourcesPhysicalStatus[i].set_voltage_Real_A(10); // this method will be generated as set_<attributeName>
             vresourcesPhysicalStatus[i].set_voltage_Imaginary_A(11);

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
      //  voltageRA = (float)configuration.voltageRealA;
      //  num= (float)configuration.number;
        log.info("voltage_A: " + voltageRA);
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
             //   vresourcesPhysicalStatus.set_voltage_Real_A(voltageRA);
            //    vresourcesPhysicalStatus.set_voltage_Real_B(<YOUR VALUE HERE >);
            //    vresourcesPhysicalStatus.set_voltage_Real_C(<YOUR VALUE HERE >);
            //   vresourcesPhysicalStatus(1).updateAttributeValues(getLRC(), currentTime);
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
            LoadConfig federateConfig = federateConfigParser.parseArgs(args, LoadConfig.class);
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
