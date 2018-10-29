package LocalController;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.utils.CpswtDefaults;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The LocalController type of federate for the federation designed in WebGME.
 *
 */
public class LocalController extends LocalControllerBase {

    private final static Logger log = LogManager.getLogger(LocalController.class);

    private double currentTime = 0;
    private int numberOfInstances;   
    private LocalControllerConfig configuration;  
    public Lcontroller[] localControl = null;                                                   
    resourceControl[] vresourceControl= null ;
    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // resourceControl vresourceControl = new resourceControl();
    //
    ///////////////////////////////////////////////////////////////////////

    public LocalController(LocalControllerConfig params) throws Exception {
        super(params);
        this.configuration = params; 
        
        numberOfInstances = configuration.numberoflc;
        localControl = new Lcontroller[numberOfInstances];  
        vresourceControl= new resourceControl[numberOfInstances];  
     //method to register the number of Instances with RTI
       registerInstances(numberOfInstances);
        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
        // vresourceControl.registerObject(getLRC());
        //
        ///////////////////////////////////////////////////////////////////////
    }


    private void registerInstances(int numberOfInstances ) {
            log.trace("registerInstances {}", numberOfInstances);
            
           
            for (int i = 0; i < numberOfInstances; i++) {
                // 1. Create the object instance using the appropriate WebGME code generated Java class.
                 vresourceControl[i] = new resourceControl();
                // 2. Register the object instance with the HLA local runtime component (LRC).
                  localControl[i] = new Lcontroller();
                      
                  vresourceControl[i].registerObject(getLRC());
                    
                      
                              
            }
        }

    private void checkReceivedSubscriptions() {

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof resourcesPhysicalStatus) {
                handleObjectClass((resourcesPhysicalStatus) object);
            }
            if (object instanceof SupervisoryControlSignal) {
                handleObjectClass((SupervisoryControlSignal) object);
            }
            else if (object instanceof ResourcesPhysicalStatus) {
                handleObjectClass((ResourcesPhysicalStatus) object);
            }
            else {
                log.debug("unhandled object reflection: {}", object.getClassName());
            }
        }
    }

 private void updateInstances(int numberOfInstances) {
        log.trace("......................updating Local Controller Instances................................................");
             
        for (int i = 0; i < numberOfInstances; i++) {
            vresourceControl[i].set_actualDemand( configuration.localControl[i].actualDemand);
            vresourceControl[i].set_locked(configuration.localControl[i].locked);
            vresourceControl[i].set_loadStatusType( configuration.localControl[i].loadStatusType); // this method will be generated as set_<attributeName>
            vresourceControl[i].set_Resources( configuration.localControl[i].Resources);
            
            vresourceControl[i].set_activePowerCurve( configuration.localControl[i].activePowerCurve);
            vresourceControl[i].set_adjustedFullDRPower( configuration.localControl[i].adjustedFullDRPower);
            vresourceControl[i].set_adjustedNoDRPower( configuration.localControl[i].adjustedNoDRPower);

            vresourceControl[i].set_maximumReactivePower( configuration.localControl[i].maximumReactivePower+(float)currentTime); // this method will be generated as set_<attributeName>
            vresourceControl[i].set_maximumRealPower(configuration.localControl[i].maximumRealPower);

            vresourceControl[i].set_downBeginRamp( configuration.localControl[i].downBeginRamp); // this method will be generated as set_<attributeName>
            vresourceControl[i].set_downDuration(configuration.localControl[i].downDuration);
            vresourceControl[i].set_downRampToCompletion( configuration.localControl[i].downRampToCompletion); // this method will be generated as set_<attributeName>
            vresourceControl[i].set_downRate(configuration.localControl[i].downRate);
                                 
            vresourceControl[i].set_reactiveDesiredFractionOfFullRatedOutputBegin( configuration.localControl[i].reactiveDesiredFractionOfFullRatedOutputBegin); // this method will be generated as set_<attributeName>
            vresourceControl[i].set_reactiveDesiredFractionOfFullRatedOutputEnd(configuration.localControl[i].reactiveDesiredFractionOfFullRatedOutputEnd);
            vresourceControl[i].set_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin( configuration.localControl[i].reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin); // this method will be generated as set_<attributeName>
            vresourceControl[i].set_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd(configuration.localControl[i].reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd);
           
            vresourceControl[i].set_realDesiredFractionOfFullRatedOutputBegin( configuration.localControl[i].realDesiredFractionOfFullRatedOutputBegin); // this method will be generated as set_<attributeName>
            vresourceControl[i].set_realDesiredFractionOfFullRatedOutputEnd(configuration.localControl[i].realDesiredFractionOfFullRatedOutputEnd);
            vresourceControl[i].set_realRequiredFractionOfFullRatedInputPowerDrawnBegin( configuration.localControl[i].realRequiredFractionOfFullRatedInputPowerDrawnBegin); // this method will be generated as set_<attributeName>
            vresourceControl[i].set_realRequiredFractionOfFullRatedInputPowerDrawnEnd(configuration.localControl[i].realRequiredFractionOfFullRatedInputPowerDrawnEnd);
            
            vresourceControl[i].set_upBeginRamp( configuration.localControl[i].upBeginRamp); // this method will be generated as set_<attributeName>
            vresourceControl[i].set_upDuration(configuration.localControl[i].upDuration);
            vresourceControl[i].set_upRampToCompletion( configuration.localControl[i].upRampToCompletion); // this method will be generated as set_<attributeName>
            vresourceControl[i].set_upRate(configuration.localControl[i].upRate);
            

           // 2. Publish the updates to HLA for the next logical time step (currentTime has already been incremented)
            vresourceControl[i].updateAttributeValues(getLRC(), currentTime + getLookAhead());
            
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
        // Call CheckReceivedSubscriptions(<message>) here to receive
        // subscriptions published before the first time step.
        ///////////////////////////////////////////////////////////////////////

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

    private void handleObjectClass(ResourcesPhysicalStatus object) {

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

    private void handleObjectClass(SupervisoryControlSignal object) {
        
         log.info("LocalControllerName: " + object.get_localControllerName());
         log.info("modulation Signal: " + object.get_modulationSignal());
        
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            LocalControllerConfig federateConfig = federateConfigParser.parseArgs(args, LocalControllerConfig.class);
            LocalController federate = new LocalController(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
