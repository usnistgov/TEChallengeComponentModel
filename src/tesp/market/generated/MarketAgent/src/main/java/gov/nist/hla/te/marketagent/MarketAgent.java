package gov.nist.hla.te.marketagent;

import gov.nist.hla.te.marketagent.rti.*;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



// Define the MarketAgent type of federate for the federation.

public class MarketAgent extends MarketAgentBase {
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;

    public MarketAgent(FederateConfig params) throws Exception {
        super(params);
    }

    private void checkReceivedSubscriptions() {
        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof Tender) {
                handleInteractionClass((Tender) interaction);
            }
            else if (interaction instanceof SimTime) {
                handleInteractionClass((SimTime) interaction);
            }
            else {
                log.debug("unhandled interaction: {}", interaction.getClassName());
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

            ////////////////////////////////////////////////////////////
            // TODO send interactions that must be sent every logical //
            // time step below                                        //
            ////////////////////////////////////////////////////////////

            // Set the interaction's parameters.
            //
            //    Quote quote = create_Quote();
            //    quote.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    quote.set_counterPartyId( < YOUR VALUE HERE > );
            //    quote.set_federateFilter( < YOUR VALUE HERE > );
            //    quote.set_id( < YOUR VALUE HERE > );
            //    quote.set_interval( < YOUR VALUE HERE > );
            //    quote.set_marketId( < YOUR VALUE HERE > );
            //    quote.set_originFed( < YOUR VALUE HERE > );
            //    quote.set_partyId( < YOUR VALUE HERE > );
            //    quote.set_price( < YOUR VALUE HERE > );
            //    quote.set_quantity( < YOUR VALUE HERE > );
            //    quote.set_side( < YOUR VALUE HERE > );
            //    quote.set_sourceFed( < YOUR VALUE HERE > );
            //    quote.sendInteraction(getLRC(), currentTime + getLookAhead());
            //    Transaction transaction = create_Transaction();
            //    transaction.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    transaction.set_counterPartyId( < YOUR VALUE HERE > );
            //    transaction.set_federateFilter( < YOUR VALUE HERE > );
            //    transaction.set_id( < YOUR VALUE HERE > );
            //    transaction.set_interval( < YOUR VALUE HERE > );
            //    transaction.set_marketId( < YOUR VALUE HERE > );
            //    transaction.set_originFed( < YOUR VALUE HERE > );
            //    transaction.set_partyId( < YOUR VALUE HERE > );
            //    transaction.set_price( < YOUR VALUE HERE > );
            //    transaction.set_quantity( < YOUR VALUE HERE > );
            //    transaction.set_side( < YOUR VALUE HERE > );
            //    transaction.set_sourceFed( < YOUR VALUE HERE > );
            //    transaction.sendInteraction(getLRC(), currentTime + getLookAhead());

            checkReceivedSubscriptions();

            ////////////////////////////////////////////////////////////////////
            // TODO break here if ready to resign and break out of while loop //
            ////////////////////////////////////////////////////////////////////

            if (!exitCondition) {
                currentTime += super.getStepSize();
                AdvanceTimeRequest newATR =
                    new AdvanceTimeRequest(currentTime);
                putAdvanceTimeRequest(newATR);
                atr.requestSyncEnd();
                atr = newATR;
            }
        }

        // call exitGracefully to shut down federate
        exitGracefully();

        //////////////////////////////////////////////////////////////////////
        // TODO Perform whatever cleanups are needed before exiting the app //
        //////////////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(Tender interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(SimTime interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            FederateConfig federateConfig =
                federateConfigParser.parseArgs(args, FederateConfig.class);
            MarketAgent federate =
                new MarketAgent(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        }
        catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
