package TE30;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Auction type of federate for the federation designed in WebGME.
 *
 */
public class Auction extends AuctionBase {
    private final static Logger log = LogManager.getLogger();

    private double currentTime = 0;

    ///////////////////////////////////////////////////////////////////////
    // TODO Instantiate objects that must be sent every logical time step
    //
    // Meter vMeter = new Meter();
    // House vHouse = new House();
    // Market vMarket = new Market();
    //
    ///////////////////////////////////////////////////////////////////////

    public Auction(FederateConfig params) throws Exception {
        super(params);

        ///////////////////////////////////////////////////////////////////////
        // TODO Must register object instances after super(args)
        //
        // vMeter.registerObject(getLRC());
        // vHouse.registerObject(getLRC());
        // vMarket.registerObject(getLRC());
        //
        ///////////////////////////////////////////////////////////////////////
    }

    private void checkReceivedSubscriptions() {

        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof House) {
                handleObjectClass((House) object);
            }
            else if (object instanceof Meter) {
                handleObjectClass((Meter) object);
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
            //    vMeter.set_bill_mode(<YOUR VALUE HERE >);
            //    vMeter.set_measured_voltage_1(<YOUR VALUE HERE >);
            //    vMeter.set_monthly_fee(<YOUR VALUE HERE >);
            //    vMeter.set_name(<YOUR VALUE HERE >);
            //    vMeter.set_price(<YOUR VALUE HERE >);
            //    vMeter.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //
            //    vHouse.set_air_temperature(<YOUR VALUE HERE >);
            //    vHouse.set_cooling_setpoint(<YOUR VALUE HERE >);
            //    vHouse.set_heating_setpoint(<YOUR VALUE HERE >);
            //    vHouse.set_hvac_load(<YOUR VALUE HERE >);
            //    vHouse.set_name(<YOUR VALUE HERE >);
            //    vHouse.set_power_state(<YOUR VALUE HERE >);
            //    vHouse.set_thermostat_deadband(<YOUR VALUE HERE >);
            //    vHouse.updateAttributeValues(getLRC(), currentTime + getLookAhead());
            //
            //    vMarket.set_clearing_price(<YOUR VALUE HERE >);
            //    vMarket.updateAttributeValues(getLRC(), currentTime + getLookAhead());
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

    private void handleObjectClass(House object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    private void handleObjectClass(Meter object) {
        //////////////////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the object                 //
        //////////////////////////////////////////////////////////////////////////
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser = new FederateConfigParser();
            FederateConfig federateConfig = federateConfigParser.parseArgs(args, FederateConfig.class);
            Auction federate = new Auction(federateConfig);
            federate.execute();
            log.info("Done.");
            System.exit(0);
        } catch (Exception e) {
            log.error(e);
            System.exit(1);
        }
    }
}
