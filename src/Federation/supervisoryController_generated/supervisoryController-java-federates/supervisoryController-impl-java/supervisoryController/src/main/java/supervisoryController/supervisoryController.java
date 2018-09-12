package supervisoryController;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The supervisoryController type of federate for the federation designed in WebGME.
 *
 */
public class supervisoryController extends supervisoryControllerBase {

    private final static Logger log = LogManager.getLogger(supervisoryController.class);

    private double currentTime = 0;
    private int numberOfSInstances,numberOfLc,numberOfObjectInstances ;   
    private supervisoryControllerConfig configuration;  
    public Scontroller[] superControls = null;  
    public SLcontroller[] localControls = null;  
    public Stender[] tenders = null;
   
    public supervisoryControlSignal[] vsupervisoryControlSignal= null ;
    public Tender[] vTender = null;

   

    public supervisoryController(supervisoryControllerConfig params) throws Exception {
        super(params);
        this.configuration = params; 
        
        numberOfSInstances = configuration.numberofsc;
        registerlocalcontroller(numberOfSInstances);
        vsupervisoryControlSignal= new supervisoryControlSignal[numberOfObjectInstances];
        vTender = new Tender[numberOfSInstances];
       // modsig = new int[numberOfObjectInstances] ;
       // nameoflocal = new String[numberOfObjectInstances];
        registerInstances(numberOfObjectInstances);
        registertq(numberOfSInstances);
       
    }


 private void registertq(int numberOfSInstances)   {
     for (int i = 0; i < numberOfSInstances; i++){ 
         tenders[i] = new Stender(); 
         vTender[i]= new Tender(); 
         vTender[i].registerObject(getLRC());
     }
     }   
   
 private int registerlocalcontroller(int numberOfSInstances){
        superControls = new Scontroller[numberOfSInstances];
        tenders = new Stender[numberOfSInstances]; 

        for (int i = 0; i < numberOfSInstances; i++){
           superControls[i]=new Scontroller();
           numberOfLc= configuration.superControls[i].numberOfLcControlledbySc; 
           numberOfObjectInstances+=numberOfLc;
           log.info("numberofInstance: "+numberOfObjectInstances);
           localControls=new SLcontroller[numberOfLc]; 
           for (int j=0;j<numberOfLc;j++)
           {
           localControls[j] = new SLcontroller();
        
        superControls[i].localControls=localControls;
    }
         }
         return numberOfObjectInstances;
    }
 private void registerInstances(int numberOfObjectInstances ) {
           // log.trace("registerInstances {}", numberOfSInstances);
           for (int i = 0; i < numberOfObjectInstances; i++) {
        	   
                // 1. Create the object instance using the appropriate WebGME code generated Java class.
                 vsupervisoryControlSignal[i] = new supervisoryControlSignal();
                // 2. Register the object instance with the HLA local runtime component (LRC).
                 vsupervisoryControlSignal[i].registerObject(getLRC());
        	   } }




private void updateInstancesqt(int numberOfSInstances)  {

   for (int i = 0; i < numberOfSInstances; i++) {
            
            vTender[i].set_price( configuration.tenders[i].price+(float)currentTime);
            vTender[i].set_quantity( configuration.tenders[i].quantity);
            vTender[i].set_tenderId( configuration.tenders[i].tenderId);
            vTender[i].set_timeReference( configuration.tenders[i].timeReference);
            vTender[i].set_type( configuration.tenders[i].type);

            vTender[i].updateAttributeValues(getLRC(), currentTime + getLookAhead());
            
    } 
 }  

       

private void updateInstances(int numberOfSInstances) {
        log.trace("...................................updating Super control signal Instances.............................................");
      int i=0;   
      {
        	for(int j=0; j<numberOfSInstances;j++){
        		int l= configuration.superControls[j].numberOfLcControlledbySc;
        		log.info("l: "+l);
     		   for(int k=0;k<l;k++){  
            vsupervisoryControlSignal[i].set_localControllerName( configuration.superControls[j].localControls[k].localControllerName);
            vsupervisoryControlSignal[i].set_modulationSignal(configuration.superControls[j].localControls[k].modulationSignal+(float)currentTime);
              

            // 2. Publish the updates to HLA for the next logical time step (currentTime has already been incremented)
            vsupervisoryControlSignal[i].updateAttributeValues(getLRC(), currentTime + getLookAhead());
            
            i++;
     		   }}   
        }
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
            if (object instanceof resourcesPhysicalStatus) {
                handleObjectClass((resourcesPhysicalStatus) object);
            }
            else if (object instanceof marketStatus) {
                handleObjectClass((marketStatus) object);
            }
            else if (object instanceof Transaction) {
                handleObjectClass((Transaction) object);
            }
            else if (object instanceof Quote) {
                handleObjectClass((Quote) object);
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
			
			
          //  createdatastructure(numberOfObjectInstances,numberOfSInstances,numberOfLc)
            updateInstances(numberOfSInstances);

  // divide by the integer value of second to set federate to run transaction part with that many second interval(current value is 20) 
         if(currentTime%20 == 0.0 )                    
            updateInstancesqt(numberOfSInstances);

            
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

    private void handleObjectClass(resourcesPhysicalStatus object) {
       /*
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
         log.info("power_Imaginary_C: " + object.get_power_Imaginary_C());     */
    }

     private void handleObjectClass(Quote object) {
        log.info("price: " + object.get_price());
        log.info("quantity: " + object.get_quantity());
        log.info("quoteId: " + object.get_quoteId());
        log.info("timeReference: " + object.get_timeReference());
        log.info("type: " + object.get_type());
    }

     private void handleObjectClass(Transaction object) {
        log.info("accept: " + object.get_accept());
        log.info("tenderId: " + object.get_tenderId());
    }
 
     private void handleObjectClass(marketStatus object) {
        log.info("mprice: " + object.get_price());
        log.info("mquantity: " + object.get_time());
        log.info("mType: " + object.get_type());
        
    }


    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            supervisoryControllerConfig federateConfig = federateConfigParser.parseArgs(args, supervisoryControllerConfig.class);
            supervisoryController federate = new supervisoryController(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
