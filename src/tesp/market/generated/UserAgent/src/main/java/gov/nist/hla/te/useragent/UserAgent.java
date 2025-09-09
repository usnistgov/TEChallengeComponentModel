package gov.nist.hla.te.useragent;

import gov.nist.hla.te.useragent.rti.*;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.utils.CpswtUtils;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserAgent extends UserAgentBase {
    private final static Logger log = LogManager.getLogger();

    private boolean receivedSimTime = false;

    private double currentTime = 0;
    private double logicalTimeScale;

    private ZonedDateTime scenarioTime;
    private ZonedDateTime scenarioTimeStop;
    
    private Set<String> activeMarkets = new HashSet<String>();
    private Map<String, Agent> agents = new HashMap<String, Agent>();

    public UserAgent(FederateConfig params) throws Exception {
        super(params);
    }

    private void incrementScenarioTime() {
        final double scenarioTimeDelta = this.getStepSize() * logicalTimeScale;
        scenarioTime = scenarioTime.plusSeconds((long)scenarioTimeDelta);
    }

    private void checkReceivedSubscriptions() {
        InteractionRoot interaction = null;
        while ((interaction = getNextInteractionNoWait()) != null) {
            if (interaction instanceof Transaction) {
                handleInteractionClass((Transaction) interaction);
            }
            else if (interaction instanceof Quote) {
                handleInteractionClass((Quote) interaction);
            }
            else if (interaction instanceof SimTime) {
                handleInteractionClass((SimTime) interaction);
            }
            else if (interaction instanceof MarketClosed) {
                handleInteractionClass((MarketClosed) interaction);
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

            log.info("t = {} / {}", this.getCurrentTime(), scenarioTime.toString());

            do {
                checkReceivedSubscriptions();
            } while (!activeMarkets.isEmpty());

            // TODO: csv output

            if (!exitCondition) {
                incrementScenarioTime();
                currentTime += super.getStepSize();

                if (scenarioTime.isBefore(scenarioTimeStop)) {
                    AdvanceTimeRequest newATR =
                        new AdvanceTimeRequest(currentTime);
                    putAdvanceTimeRequest(newATR);
                    atr.requestSyncEnd();
                    atr = newATR;
                } else {
                    exitCondition = true;
                    log.info("Reached stop time of {}", scenarioTimeStop.toString());
                }
            }
        }

        exitGracefully();
    }

    private void handleInteractionClass(Transaction interaction) {
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
    }

    private void handleInteractionClass(Quote interaction) {
        final String marketId = interaction.get_marketId();
        final String priceString = interaction.get_price();
        final String quantityString = interaction.get_quantity();

        final boolean isBuyQuote = (interaction.get_side() == 'b');

        if (activeMarkets.add(marketId)) {
            log.info("Detected new market {}", marketId);
        }

        for (Agent a : agents.values()) {
            String tenderQuantity = a.handleQuote(marketId, priceString, quantityString, isBuyQuote);
        }
    }

    private void handleInteractionClass(MarketClosed interaction) {
        final String marketId = interaction.get_marketId();

        if (activeMarkets.remove(marketId)) {
            log.info("Processed MarketClosed for {}", marketId);
        } else {
            log.warn("MarketClosed received for unknown market {}", marketId);
        }
    }

    private void handleInteractionClass(SimTime interaction) {
        if (receivedSimTime) {
            log.debug("dropped duplicate SimTime interaction");
            return;
        }

        logicalTimeScale    = interaction.get_timeScale();
        scenarioTime        = ZonedDateTime.ofInstant(Instant.ofEpochSecond(interaction.get_unixTimeStart()), TimeZone.getTimeZone(interaction.get_timeZone()).toZoneId());
        scenarioTimeStop    = ZonedDateTime.ofInstant(Instant.ofEpochSecond(interaction.get_unixTimeStop()), TimeZone.getTimeZone(interaction.get_timeZone()).toZoneId());
        receivedSimTime     = true;

        log.info("received SimTime starting at {}", scenarioTime.toString());
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            FederateConfig federateConfig =
                federateConfigParser.parseArgs(args, FederateConfig.class);
            UserAgent federate =
                new UserAgent(federateConfig);
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
