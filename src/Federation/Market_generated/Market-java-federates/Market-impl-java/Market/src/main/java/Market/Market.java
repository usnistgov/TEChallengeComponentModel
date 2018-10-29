package Market;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Market type of federate for the federation designed in WebGME.
 *
 */
public class Market extends MarketBase {
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // Tender vTender = new Tender();
    // Transaction vTransaction = new Transaction();
    // Quote vQuote = new Quote();
    // MarketStatus vMarketStatus = new MarketStatus();
    //
    ///////////////////////////////////////////////////////////////////////

    public Market(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
        // vTender.registerObject(getLRC());
        // vTransaction.registerObject(getLRC());
        // vQuote.registerObject(getLRC());
        // vMarketStatus.registerObject(getLRC());
        //
        ///////////////////////////////////////////////////////////////////////
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
            if (object instanceof Transaction) {
                handleObjectClass((Transaction) object);
            }
            else if (object instanceof Tender) {
                handleObjectClass((Tender) object);
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

            ////////////////////////////////////////////////////////////////////////////////////////
            // TODO objects that must be sent every logical time step
            //
            //    vTender.set_price(<YOUR VALUE HERE >);
            //    vTender.set_quantity(<YOUR VALUE HERE >);
            //    vTender.set_tenderId(<YOUR VALUE HERE >);
            //    vTender.set_timeReference(<YOUR VALUE HERE >);
            //    vTender.set_type(<YOUR VALUE HERE >);
            //    vTender.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //
            //    vTransaction.set_accept(<YOUR VALUE HERE >);
            //    vTransaction.set_tenderId(<YOUR VALUE HERE >);
            //    vTransaction.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //
            //    vQuote.set_price(<YOUR VALUE HERE >);
            //    vQuote.set_quantity(<YOUR VALUE HERE >);
            //    vQuote.set_quoteId(<YOUR VALUE HERE >);
            //    vQuote.set_timeReference(<YOUR VALUE HERE >);
            //    vQuote.set_type(<YOUR VALUE HERE >);
            //    vQuote.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //
            //    vMarketStatus.set_price(<YOUR VALUE HERE >);
            //    vMarketStatus.set_time(<YOUR VALUE HERE >);
            //    vMarketStatus.set_type(<YOUR VALUE HERE >);
            //    vMarketStatus.updateAttributeValues(getLRC(), currentTime + getLookAhead());
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

    private void handleObjectClass(Transaction object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(Tender object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(Quote object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            FederateConfig federateConfig = federateConfigParser.parseArgs(args, FederateConfig.class);
            Market federate = new Market(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
