package gov.nist.hla.parity.paritysystem;

import gov.nist.hla.parity.paritysystem.exception.DuplicateInstrumentName;
import gov.nist.hla.parity.paritysystem.rti.*;

import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// CodeGeneratedName is used if necessary. Otherwise, name is used.
// The following interaction classes have both CodeGeneratedName and name.
// CodeGeneratedName = OrderCanceledReport   name = OrderCanceled


// Define the ParitySystem type of federate for the federation.

public class ParitySystem extends ParitySystemBase {
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;
    
    private Map<String, Instrument> instruments = new HashMap<String, Instrument>();

    public ParitySystem(ParitySystemConfig params) throws Exception {
        super(params);
        
        for (Instrument instrument : params.instruments) {
            if (instruments.put(instrument.getInstrumentName(), instrument) != null) {
                log.error("multiple instruments with the name {}", instrument.getInstrumentName());
                throw new DuplicateInstrumentName(instrument.getInstrumentName());
            }
        }
    }

    private void checkReceivedSubscriptions() {
        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof CancelOrder) {
                handleInteractionClass((CancelOrder) interaction);
            }
            else if (interaction instanceof EnterOrder) {
                handleInteractionClass((EnterOrder) interaction);
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
            //    Trade trade = create_Trade();
            //    trade.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    trade.set_federateFilter( < YOUR VALUE HERE > );
            //    trade.set_incomingOrderNumber( < YOUR VALUE HERE > );
            //    trade.set_matchNumber( < YOUR VALUE HERE > );
            //    trade.set_messageType( < YOUR VALUE HERE > );
            //    trade.set_originFed( < YOUR VALUE HERE > );
            //    trade.set_quantity( < YOUR VALUE HERE > );
            //    trade.set_restingOrderNumber( < YOUR VALUE HERE > );
            //    trade.set_sourceFed( < YOUR VALUE HERE > );
            //    trade.set_timeStamp( < YOUR VALUE HERE > );
            //    trade.sendInteraction(getLRC(), currentTime + getLookAhead());
            //    Version version = create_Version();
            //    version.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    version.set_federateFilter( < YOUR VALUE HERE > );
            //    version.set_messageType( < YOUR VALUE HERE > );
            //    version.set_originFed( < YOUR VALUE HERE > );
            //    version.set_sourceFed( < YOUR VALUE HERE > );
            //    version.set_version( < YOUR VALUE HERE > );
            //    version.sendInteraction(getLRC(), currentTime + getLookAhead());
            //    OrderAccepted orderAccepted = create_OrderAccepted();
            //    orderAccepted.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    orderAccepted.set_federateFilter( < YOUR VALUE HERE > );
            //    orderAccepted.set_instrument( < YOUR VALUE HERE > );
            //    orderAccepted.set_messageType( < YOUR VALUE HERE > );
            //    orderAccepted.set_orderId( < YOUR VALUE HERE > );
            //    orderAccepted.set_orderNumber( < YOUR VALUE HERE > );
            //    orderAccepted.set_originFed( < YOUR VALUE HERE > );
            //    orderAccepted.set_price( < YOUR VALUE HERE > );
            //    orderAccepted.set_quantity( < YOUR VALUE HERE > );
            //    orderAccepted.set_side( < YOUR VALUE HERE > );
            //    orderAccepted.set_sourceFed( < YOUR VALUE HERE > );
            //    orderAccepted.set_timeStamp( < YOUR VALUE HERE > );
            //    orderAccepted.set_userName( < YOUR VALUE HERE > );
            //    orderAccepted.sendInteraction(getLRC(), currentTime + getLookAhead());
            //    OrderCanceled orderCanceled = create_OrderCanceled();
            //    orderCanceled.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    orderCanceled.set_canceledQuantity( < YOUR VALUE HERE > );
            //    orderCanceled.set_federateFilter( < YOUR VALUE HERE > );
            //    orderCanceled.set_messageType( < YOUR VALUE HERE > );
            //    orderCanceled.set_orderId( < YOUR VALUE HERE > );
            //    orderCanceled.set_originFed( < YOUR VALUE HERE > );
            //    orderCanceled.set_reason( < YOUR VALUE HERE > );
            //    orderCanceled.set_sourceFed( < YOUR VALUE HERE > );
            //    orderCanceled.set_timeStamp( < YOUR VALUE HERE > );
            //    orderCanceled.set_userName( < YOUR VALUE HERE > );
            //    orderCanceled.sendInteraction(getLRC(), currentTime + getLookAhead());
            //    OrderExecuted orderExecuted = create_OrderExecuted();
            //    orderExecuted.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    orderExecuted.set_federateFilter( < YOUR VALUE HERE > );
            //    orderExecuted.set_liquidityFlag( < YOUR VALUE HERE > );
            //    orderExecuted.set_matchNumber( < YOUR VALUE HERE > );
            //    orderExecuted.set_messageType( < YOUR VALUE HERE > );
            //    orderExecuted.set_orderId( < YOUR VALUE HERE > );
            //    orderExecuted.set_originFed( < YOUR VALUE HERE > );
            //    orderExecuted.set_price( < YOUR VALUE HERE > );
            //    orderExecuted.set_quantity( < YOUR VALUE HERE > );
            //    orderExecuted.set_sourceFed( < YOUR VALUE HERE > );
            //    orderExecuted.set_timeStamp( < YOUR VALUE HERE > );
            //    orderExecuted.set_userName( < YOUR VALUE HERE > );
            //    orderExecuted.sendInteraction(getLRC(), currentTime + getLookAhead());
            //    OrderRejected orderRejected = create_OrderRejected();
            //    orderRejected.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    orderRejected.set_federateFilter( < YOUR VALUE HERE > );
            //    orderRejected.set_messageType( < YOUR VALUE HERE > );
            //    orderRejected.set_orderId( < YOUR VALUE HERE > );
            //    orderRejected.set_originFed( < YOUR VALUE HERE > );
            //    orderRejected.set_reason( < YOUR VALUE HERE > );
            //    orderRejected.set_sourceFed( < YOUR VALUE HERE > );
            //    orderRejected.set_timeStamp( < YOUR VALUE HERE > );
            //    orderRejected.set_userName( < YOUR VALUE HERE > );
            //    orderRejected.sendInteraction(getLRC(), currentTime + getLookAhead());
            //    OrderEntered orderEntered = create_OrderEntered();
            //    orderEntered.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    orderEntered.set_federateFilter( < YOUR VALUE HERE > );
            //    orderEntered.set_instrument( < YOUR VALUE HERE > );
            //    orderEntered.set_messageType( < YOUR VALUE HERE > );
            //    orderEntered.set_orderNumber( < YOUR VALUE HERE > );
            //    orderEntered.set_originFed( < YOUR VALUE HERE > );
            //    orderEntered.set_price( < YOUR VALUE HERE > );
            //    orderEntered.set_quantity( < YOUR VALUE HERE > );
            //    orderEntered.set_side( < YOUR VALUE HERE > );
            //    orderEntered.set_sourceFed( < YOUR VALUE HERE > );
            //    orderEntered.set_timeStamp( < YOUR VALUE HERE > );
            //    orderEntered.set_userName( < YOUR VALUE HERE > );
            //    orderEntered.sendInteraction(getLRC(), currentTime + getLookAhead());
            //    OrderAdded orderAdded = create_OrderAdded();
            //    orderAdded.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    orderAdded.set_federateFilter( < YOUR VALUE HERE > );
            //    orderAdded.set_messageType( < YOUR VALUE HERE > );
            //    orderAdded.set_orderNumber( < YOUR VALUE HERE > );
            //    orderAdded.set_originFed( < YOUR VALUE HERE > );
            //    orderAdded.set_sourceFed( < YOUR VALUE HERE > );
            //    orderAdded.set_timeStamp( < YOUR VALUE HERE > );
            //    orderAdded.sendInteraction(getLRC(), currentTime + getLookAhead());
            //    OrderCanceledReport orderCanceledReport = create_OrderCanceledReport();
            //    orderCanceledReport.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    orderCanceledReport.set_canceledQuantity( < YOUR VALUE HERE > );
            //    orderCanceledReport.set_federateFilter( < YOUR VALUE HERE > );
            //    orderCanceledReport.set_messageType( < YOUR VALUE HERE > );
            //    orderCanceledReport.set_orderNumber( < YOUR VALUE HERE > );
            //    orderCanceledReport.set_originFed( < YOUR VALUE HERE > );
            //    orderCanceledReport.set_sourceFed( < YOUR VALUE HERE > );
            //    orderCanceledReport.set_timeStamp( < YOUR VALUE HERE > );
            //    orderCanceledReport.sendInteraction(getLRC(), currentTime + getLookAhead());

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

    private void handleInteractionClass(CancelOrder interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(EnterOrder interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            ParitySystemConfig federateConfig =
                federateConfigParser.parseArgs(args, ParitySystemConfig.class);
            ParitySystem federate =
                new ParitySystem(federateConfig);
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
