package gov.nist.hla.te.auction;

import gov.nist.hla.te.auction.rti.*;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Define the Auction type of federate for the federation.

public class Auction extends AuctionBase {
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;

    ////////////////////////////////////////////////////////////////////////
    // TODO instantiate objects that must be sent every logical time step //
    ////////////////////////////////////////////////////////////////////////
    // Meter vMeter =
    //     new Meter();
    // PhysicalStatus vPhysicalStatus =
    //     new PhysicalStatus();
    // Market vMarket =
    //     new Market();
    // House vHouse =
    //     new House();

    public Auction(FederateConfig params) throws Exception {
        super(params);

        //////////////////////////////////////////////////////
        // TODO register object instances after super(args) //
        //////////////////////////////////////////////////////
        // vMeter.registerObject(getLRC());
        // vPhysicalStatus.registerObject(getLRC());
        // vMarket.registerObject(getLRC());
        // vHouse.registerObject(getLRC());
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
            if (object instanceof Substation) {
                handleObjectClass((Substation) object);
            }
            else if (object instanceof House) {
                handleObjectClass((House) object);
            }
            else if (object instanceof Meter) {
                handleObjectClass((Meter) object);
            }
            else if (object instanceof LMP) {
                handleObjectClass((LMP) object);
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
            // TODO objects that must be sent every logical time step //
            ////////////////////////////////////////////////////////////
            //    vMeter.set_bill_mode(<YOUR VALUE HERE >);
            //    vMeter.set_measured_voltage_1(<YOUR VALUE HERE >);
            //    vMeter.set_monthly_fee(<YOUR VALUE HERE >);
            //    vMeter.set_name(<YOUR VALUE HERE >);
            //    vMeter.set_price(<YOUR VALUE HERE >);
            //    vMeter.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //    vPhysicalStatus.set_responsive_c1(<YOUR VALUE HERE >);
            //    vPhysicalStatus.set_responsive_c2(<YOUR VALUE HERE >);
            //    vPhysicalStatus.set_responsive_deg(<YOUR VALUE HERE >);
            //    vPhysicalStatus.set_responsive_max_mw(<YOUR VALUE HERE >);
            //    vPhysicalStatus.set_unresponsive_mw(<YOUR VALUE HERE >);
            //    vPhysicalStatus.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //    vMarket.set_clearing_price(<YOUR VALUE HERE >);
            //    vMarket.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //    vHouse.set_air_temperature(<YOUR VALUE HERE >);
            //    vHouse.set_cooling_setpoint(<YOUR VALUE HERE >);
            //    vHouse.set_heating_setpoint(<YOUR VALUE HERE >);
            //    vHouse.set_hvac_load(<YOUR VALUE HERE >);
            //    vHouse.set_name(<YOUR VALUE HERE >);
            //    vHouse.set_power_state(<YOUR VALUE HERE >);
            //    vHouse.set_thermostat_deadband(<YOUR VALUE HERE >);
            //    vHouse.updateAttributeValues(getLRC(), currentTime + getLookAhead());

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

    private void handleObjectClass(Substation object) {
        //////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object //
        //////////////////////////////////////////////////////////
    }

    private void handleObjectClass(House object) {
        //////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object //
        //////////////////////////////////////////////////////////
    }

    private void handleObjectClass(Meter object) {
        //////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object //
        //////////////////////////////////////////////////////////
    }

    private void handleObjectClass(LMP object) {
        //////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object //
        //////////////////////////////////////////////////////////
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            FederateConfig federateConfig =
                federateConfigParser.parseArgs(args, FederateConfig.class);
            Auction federate =
                new Auction(federateConfig);
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
