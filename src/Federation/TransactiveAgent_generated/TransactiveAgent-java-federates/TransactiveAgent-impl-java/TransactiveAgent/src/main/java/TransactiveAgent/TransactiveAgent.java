package TransactiveAgent;

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
 * The TransactiveAgent type of federate for the federation designed in WebGME.
 *
 */
public class TransactiveAgent extends TransactiveAgentBase {

    private final static Logger log = LogManager.getLogger(TransactiveAgent.class);

    private double currentTime = 0;

    int numberOfInstances;   
    private TransactiveAgentConfig configuration;  
    public Stransaction[] transactions = null;  
    public Squote[] quotes = null; 

    Quote[] vQuote = null;
    Transaction[] vTransaction= null;    

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // Quote vQuote = new Quote();
     marketStatus vmarketStatus = new marketStatus();
    // Transaction vTransaction = new Transaction();
    //
    ///////////////////////////////////////////////////////////////////////

    public TransactiveAgent(TransactiveAgentConfig params) throws Exception {
        super(params);
        this.configuration = params; 
        
        numberOfInstances = configuration.numberofta;

        transactions = new Stransaction[numberOfInstances];  
        quotes = new Squote[numberOfInstances];

        vQuote = new Quote[numberOfInstances];
        vTransaction = new Transaction[numberOfInstances];

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
        // vQuote.registerObject(getLRC());
         vmarketStatus.registerObject(getLRC());
        // vTransaction.registerObject(getLRC());
        //
        ///////////////////////////////////////////////////////////////////////
         registerInstances(numberOfInstances);
    }



     private void registerInstances(int numberOfInstances ) {
           // log.trace("registerInstances {}", numberOfInstances);
            
           
            for (int i = 0; i < numberOfInstances; i++) {
                // 1. Create the object instance using the appropriate WebGME code generated Java class.
                 transactions[i] = new Stransaction();
                 quotes[i] = new Squote();
                // 2. Register the object instance with the HLA local runtime component (LRC).
                vQuote[i] = new Quote();
                vQuote[i].registerObject(getLRC()); 

                vTransaction[i] = new Transaction();
                vTransaction[i].registerObject(getLRC());
                      
                              
            }
        }

     private void updateInstancesQ(int numberOfInstances) {
        log.trace("...................................updating Quote Instances.............................................");
        
        for (int i = 0; i < numberOfInstances; i++) {
            
            vQuote[i].set_price( configuration.quotes[i].price);
            vQuote[i].set_quantity( configuration.quotes[i].quantity);
            vQuote[i].set_quoteId( configuration.quotes[i].quoteId+ (int)currentTime);
            vQuote[i].set_timeReference( configuration.quotes[i].timeReference);  
            vQuote[i].set_type( configuration.quotes[i].type);

            // 2. Publish the updates to HLA for the next logical time step (currentTime has already been incremented)
            vQuote[i].updateAttributeValues(getLRC(), currentTime + getLookAhead());
            
        }
    }    

private void updateInstancesT(int numberOfInstances) {
        log.trace("...................................updating Transaction Status Instances.............................................");
        
        for (int i = 0; i < numberOfInstances; i++) {
            
            vTransaction[i].set_accept( configuration.transactions[i].accept);
            vTransaction[i].set_tenderId( configuration.transactions[i].tenderId+ (int)currentTime);
            

            // 2. Publish the updates to HLA for the next logical time step (currentTime has already been incremented)
            vTransaction[i].updateAttributeValues(getLRC(), currentTime + getLookAhead());
            
        }
    }    



    private void checkReceivedSubscriptions() {

        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof SimTime) {
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
            if (object instanceof Tender) {
                handleObjectClass((Tender) object);
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
			
			
            updateInstancesQ(numberOfInstances);
            updateInstancesT(numberOfInstances);
            ////////////////////////////////////////////////////////////////////////////////////////
            // TODO objects that must be sent every logical time step
            //
            //    vQuote.set_price(<YOUR VALUE HERE >);
            //    vQuote.set_quantity(<YOUR VALUE HERE >);
            //    vQuote.set_quoteId(<YOUR VALUE HERE >);
            //    vQuote.set_timeReference(<YOUR VALUE HERE >);
            //    vQuote.set_type(<YOUR VALUE HERE >);
            //    vQuote.updateAttributeValues(getLRC(), currentTime);
            //
                vmarketStatus.set_price(1);
                vmarketStatus.set_time(2+(int)currentTime);
                vmarketStatus.set_type("watt");
                vmarketStatus.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //
            //    vTransaction.set_accept(<YOUR VALUE HERE >);
            //    vTransaction.set_tenderId(<YOUR VALUE HERE >);
            //    vTransaction.updateAttributeValues(getLRC(), currentTime);
            //
            //////////////////////////////////////////////////////////////////////////////////////////

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

    private void handleInteractionClass(SimTime interaction) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction            //
        //////////////////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(Tender object) {
      
    	log.info("price: " + object.get_price());
        log.info("quantity: " + object.get_quantity());
        log.info("tenderId: " + object.get_tenderId());
        log.info("timeReference: " + object.get_timeReference());
        log.info("type: " + object.get_type());


    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            TransactiveAgentConfig federateConfig = federateConfigParser.parseArgs(args, TransactiveAgentConfig.class);
            TransactiveAgent federate = new TransactiveAgent(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
