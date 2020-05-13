package gov.nist.hla.te.parity.parityclient;

import gov.nist.hla.te.parity.parityclient.rti.*;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



// Define the ParityClient type of federate for the federation.

public class ParityClient extends ParityClientBase {
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;

    public ParityClient(FederateConfig params) throws Exception {
        super(params);
    }

    private void checkReceivedSubscriptions() {
        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof OrderCanceled) {
                handleInteractionClass((OrderCanceled) interaction);
            }
            else if (interaction instanceof OrderAccepted) {
                handleInteractionClass((OrderAccepted) interaction);
            }
            else if (interaction instanceof OrderRejected) {
                handleInteractionClass((OrderRejected) interaction);
            }
            else if (interaction instanceof OrderExecuted) {
                handleInteractionClass((OrderExecuted) interaction);
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
            //    EnterOrder enterOrder = create_EnterOrder();
            //    enterOrder.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    enterOrder.set_federateFilter( < YOUR VALUE HERE > );
            //    enterOrder.set_instrument( < YOUR VALUE HERE > );
            //    enterOrder.set_orderId( < YOUR VALUE HERE > );
            //    enterOrder.set_originFed( < YOUR VALUE HERE > );
            //    enterOrder.set_price( < YOUR VALUE HERE > );
            //    enterOrder.set_quantity( < YOUR VALUE HERE > );
            //    enterOrder.set_side( < YOUR VALUE HERE > );
            //    enterOrder.set_sourceFed( < YOUR VALUE HERE > );
            //    enterOrder.set_username( < YOUR VALUE HERE > );
            //    enterOrder.sendInteraction(getLRC(), currentTime + getLookAhead());
            //    CancelOrder cancelOrder = create_CancelOrder();
            //    cancelOrder.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    cancelOrder.set_federateFilter( < YOUR VALUE HERE > );
            //    cancelOrder.set_orderId( < YOUR VALUE HERE > );
            //    cancelOrder.set_originFed( < YOUR VALUE HERE > );
            //    cancelOrder.set_quantity( < YOUR VALUE HERE > );
            //    cancelOrder.set_sourceFed( < YOUR VALUE HERE > );
            //    cancelOrder.set_username( < YOUR VALUE HERE > );
            //    cancelOrder.sendInteraction(getLRC(), currentTime + getLookAhead());

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

    private void handleInteractionClass(OrderCanceled interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(OrderAccepted interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(OrderRejected interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(OrderExecuted interaction) {
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
            ParityClient federate =
                new ParityClient(federateConfig);
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
