package supervisoryController;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.utils.CpswtDefaults;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The supervisoryController type of federate for the federation designed in WebGME.
 *
 */
public class supervisoryController extends supervisoryControllerBase {

    private final static Logger log = LogManager.getLogger(supervisoryController.class);

    double currentTime = 0;
    private int numberOfSInstances,numberOfLc,numberOfObjectInstances ;   
    private supervisoryControllerConfig configuration;  
    public Scontroller[] superControls = null;  
    public SLcontroller[] localControls = null;  
   // public int[] modsig;
   // public String[] nameoflocal;
    public supervisoryControlSignal[] vsupervisoryControlSignal= null ;

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // supervisoryControlSignal vsupervisoryControlSignal = new supervisoryControlSignal();
    //
    ///////////////////////////////////////////////////////////////////////

    public supervisoryController(supervisoryControllerConfig params) throws Exception {
        super(params);
        this.configuration = params; 
        
        numberOfSInstances = configuration.numberofsc;
        registerlocalcontroller(numberOfSInstances);
        vsupervisoryControlSignal= new supervisoryControlSignal[numberOfObjectInstances];
       // modsig = new int[numberOfObjectInstances] ;
       // nameoflocal = new String[numberOfObjectInstances];
 
        registerInstances(numberOfObjectInstances);

    }
   
 private int registerlocalcontroller(int numberOfSInstances){
        superControls = new Scontroller[numberOfSInstances];
      
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
       

private void updateInstances(int numberOfSInstances) {
        log.trace("...................................updating Resourse Physical Status Instances.............................................");
      int i=0;   
      {
        	for(int j=0; j<numberOfSInstances;j++){
        		int l= configuration.superControls[j].numberOfLcControlledbySc;
        		log.info("l: "+l);
     		   for(int k=0;k<l;k++){  
            vsupervisoryControlSignal[i].set_localControllerName( configuration.superControls[j].localControls[k].localControllerName);
            vsupervisoryControlSignal[i].set_modulationSignal(configuration.superControls[j].localControls[k].modulationSignal+(float)currentTime);
              

            // 2. Publish the updates to HLA for the next logical time step (currentTime has already been incremented)
            vsupervisoryControlSignal[i].updateAttributeValues(getLRC(), currentTime);
            
            i++;
     		   }}   
        }
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
          //  createdatastructure(numberOfObjectInstances,numberOfSInstances,numberOfLc)
             updateInstances(numberOfSInstances);

            ////////////////////////////////////////////////////////////////////////////////////////
            // TODO objects that must be sent every logical time step
            //
            //    vsupervisoryControlSignal.set_localControllerName(<YOUR VALUE HERE >);
            //    vsupervisoryControlSignal.set_modulationSignal(<YOUR VALUE HERE >);
            //    vsupervisoryControlSignal.updateAttributeValues(getLRC(), currentTime);
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
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            supervisoryControllerConfig federateConfig = federateConfigParser.parseArgs(args, supervisoryControllerConfig.class);
            supervisoryController federate = new supervisoryController(federateConfig);
            federate.execute();

            System.exit(0);
        } catch (Exception e) {
            log.error("There was a problem executing the supervisoryController federate: {}", e.getMessage());
            log.error(e);

            System.exit(1);
        }
    }
}
