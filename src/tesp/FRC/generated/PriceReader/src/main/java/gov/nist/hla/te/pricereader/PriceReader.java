package gov.nist.hla.te.pricereader;

import gov.nist.hla.te.pricereader.rti.*;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



// Define the PriceReader type of federate for the federation.

public class PriceReader extends PriceReaderBase {
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;

    public PriceReader(FederateConfig params) throws Exception {
        super(params);
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
            //    RealTimePrice realTimePrice = create_RealTimePrice();
            //    realTimePrice.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    realTimePrice.set_federateFilter( < YOUR VALUE HERE > );
            //    realTimePrice.set_originFed( < YOUR VALUE HERE > );
            //    realTimePrice.set_sourceFed( < YOUR VALUE HERE > );
            //    realTimePrice.set_time( < YOUR VALUE HERE > );
            //    realTimePrice.set_value( < YOUR VALUE HERE > );
            //    realTimePrice.sendInteraction(getLRC(), currentTime + getLookAhead());
            //    DayAheadPrice dayAheadPrice = create_DayAheadPrice();
            //    dayAheadPrice.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    dayAheadPrice.set_federateFilter( < YOUR VALUE HERE > );
            //    dayAheadPrice.set_originFed( < YOUR VALUE HERE > );
            //    dayAheadPrice.set_sourceFed( < YOUR VALUE HERE > );
            //    dayAheadPrice.set_time( < YOUR VALUE HERE > );
            //    dayAheadPrice.set_value( < YOUR VALUE HERE > );
            //    dayAheadPrice.sendInteraction(getLRC(), currentTime + getLookAhead());

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
            PriceReader federate =
                new PriceReader(federateConfig);
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
