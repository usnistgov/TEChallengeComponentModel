package gov.nist.hla.te.parity.parityreporter;

import gov.nist.hla.te.parity.parityreporter.exception.InvalidTransactionException;
import gov.nist.hla.te.parity.parityreporter.rti.*;
import hla.rti.ConcurrentAccessAttempted;
import hla.rti.FederateNotExecutionMember;
import hla.rti.RTIinternalError;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.ObjectRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.hla.base.ObjectReflector;
import org.cpswt.utils.CpswtUtils;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParityReporter extends ParityReporterBase {
    private final static Logger log = LogManager.getLogger();
    
    private Map<Long, Trade> trades = new HashMap<Long, Trade>();

    private Map<String, House> houses = new HashMap<String, House>();
    
    private double currentTime = 0;
    
    private long secondsPerStep;
    
    private long unixTime;
    
    private boolean receivedSimTime = false;
    
    private boolean receivedTrades = false;

    public ParityReporter(FederateConfig params) throws Exception {
        super(params);
    }

    private void checkReceivedSubscriptions() {
        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof SimTime) {
                handleInteractionClass((SimTime) interaction);
            }
            else if (interaction instanceof Transaction) {
                handleInteractionClass((Transaction) interaction);
            }
            else {
                log.debug("unhandled interaction: {}", interaction.getClassName());
            }
        }
        
        ObjectReflector reflector = null;
        while ((reflector = getNextObjectReflectorNoWait()) != null) {
            reflector.reflect();
            ObjectRoot object = reflector.getObjectRoot();
            if (object instanceof Meter) {
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

        AdvanceTimeRequest atr = new AdvanceTimeRequest(currentTime);
        putAdvanceTimeRequest(atr);

        if(!super.isLateJoiner()) {
            log.info("waiting on readyToPopulate...");
            readyToPopulate();
            log.info("...synchronized on readyToPopulate");
        }
        
        while (!receivedSimTime) {
            log.info("waiting to receive SimTime...");
            synchronized (lrc) {
                lrc.tick();
            }
            checkReceivedSubscriptions();
            if (!receivedSimTime) {
                CpswtUtils.sleep(1000);
            }
        }

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
            
            checkReceivedSubscriptions();
            
            if (receivedTrades) { // assume one bulk receive
                for (House house : houses.values()) {
                    house.clear_trades();
                }
                receivedTrades = false;
            }
            
            // TODO: external (to GLD) real time price feed
            // TODO: somehow have reports (like daily) for the bill data
            long integerTime = (long) this.getCurrentTime();
            long updatePeriod = 300 / secondsPerStep; // can lead to errors
            
            // offset by 1 to process after receiving the data from FileReader
            // delay 10 (5 minutes from previous day; 5 minutes until first bill)
            if (integerTime > 10 && integerTime % updatePeriod == 1) {
                log.info("bill update at t={}", this.getCurrentTime());
                
                for (House house : houses.values()) {
                    house.update(unixTime, 300); // nice magic number
                }
            }
            
            unixTime = unixTime + secondsPerStep; // right location ?
            
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
    }
    
    private void handleInteractionClass(SimTime interaction) {
        if (receivedSimTime) {
            // replace the old values with the latest received version
            log.warn("received duplicate SimTime");
        } else {
            log.info("received SimTime");
        }
        
        unixTime = interaction.get_unixTimeStart();
        secondsPerStep = (long) interaction.get_timeScale(); // may lead to errors
        receivedSimTime = true;
    }
    
    private void handleInteractionClass(Transaction interaction) {
        final long matchNumber = interaction.get_matchNumber();
        
        try {
            if (!trades.containsKey(matchNumber)) {
                trades.put(matchNumber, new Trade(interaction));
                log.debug("created new trade for match number {}", matchNumber);
            } else {
                Trade trade = trades.get(matchNumber);
                trade.update(interaction);
                log.info(trade.toString());
                
                // ignore seller (assumed utility)
                String houseName = trade.getBuyer();
                if (!houses.containsKey(houseName)) {
                    houses.put(houseName, new House(houseName));
                    log.info("created new house {}", houseName);
                }
                houses.get(houseName).add_trade(trade);
                receivedTrades = true;
            }
        } catch (InvalidTransactionException e) {
            log.error("failed to process transaction: {}", e.getMessage());
        }
    }
    
    private void handleObjectClass(Meter object) {
        String houseName = object.get_name() + "_house";
        
        House house = houses.get(houseName);
        if (house == null) {
            log.error("cannot find house {}", houseName);
            return;
        }
        
        house.set_measured_real_energy(object.get_measured_real_energy());
        house.set_real_time_price(object.get_price());
        log.debug("updated house {}", houseName);
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            FederateConfig federateConfig =
                federateConfigParser.parseArgs(args, FederateConfig.class);
            ParityReporter federate =
                new ParityReporter(federateConfig);
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
