package gov.nist.hla.te.controller;

import gov.nist.hla.te.controller.rti.*;

import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



// Define the Controller type of federate for the federation.

public class Controller extends ControllerBase {
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;

    private String config;

    public Controller(ControllerConfig params) throws Exception {
        super(params);
        config = params.configFileName;
    }

    private void checkReceivedSubscriptions() {
        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof SimTime) {
                handleInteractionClass((SimTime) interaction);
            }
            else if (interaction instanceof LoadForecast) {
                handleInteractionClass((LoadForecast) interaction);
            }
            else if (interaction instanceof DataReceipt) {
                handleInteractionClass((DataReceipt) interaction);
            }
            else if (interaction instanceof Tender) {
                handleInteractionClass((Tender) interaction);
            }
            else {
                log.debug("unhandled interaction: {}", interaction.getClassName());
            }
        }

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof RealTimePrice) {
                handleObjectClass((RealTimePrice) object);
            }
            else if (object instanceof House) {
                handleObjectClass((House) object);
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

            ////////////////////////////////////////////////////////////
            // TODO send interactions that must be sent every logical //
            // time step below                                        //
            ////////////////////////////////////////////////////////////

            // Set the interaction's parameters.
            //
            //    Tender tender = create_Tender();
            //    tender.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    tender.set_durationInMinutes( < YOUR VALUE HERE > );
            //    tender.set_expireTime( < YOUR VALUE HERE > );
            //    tender.set_federateFilter( < YOUR VALUE HERE > );
            //    tender.set_originFed( < YOUR VALUE HERE > );
            //    tender.set_partyId( < YOUR VALUE HERE > );
            //    tender.set_price( < YOUR VALUE HERE > );
            //    tender.set_quantity( < YOUR VALUE HERE > );
            //    tender.set_side( < YOUR VALUE HERE > );
            //    tender.set_sourceFed( < YOUR VALUE HERE > );
            //    tender.set_startTime( < YOUR VALUE HERE > );
            //    tender.set_tenderId( < YOUR VALUE HERE > );
            //    tender.sendInteraction(getLRC(), currentTime + getLookAhead());

            ////////////////////////////////////////////////////////////
            // TODO objects that must be sent every logical time step //
            ////////////////////////////////////////////////////////////
            //    house.set_air_temperature(<YOUR VALUE HERE >);
            //    house.set_cooling_setpoint(<YOUR VALUE HERE >);
            //    house.set_heating_setpoint(<YOUR VALUE HERE >);
            //    house.set_hvac_load(<YOUR VALUE HERE >);
            //    house.set_name(<YOUR VALUE HERE >);
            //    house.set_power_state(<YOUR VALUE HERE >);
            //    house.set_thermostat_deadband(<YOUR VALUE HERE >);
            //    house.updateAttributeValues(getLRC(), currentTime + getLookAhead());

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

    private void handleInteractionClass(LoadForecast interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(DataReceipt interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(Tender interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(RealTimePrice object) {
        //////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object //
        //////////////////////////////////////////////////////////
    }

    private void handleObjectClass(House object) {
        //////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object //
        //////////////////////////////////////////////////////////
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            ControllerConfig federateConfig =
                federateConfigParser.parseArgs(args, ControllerConfig.class);
            Controller federate =
                new Controller(federateConfig);
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
