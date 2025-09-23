package gov.nist.hla.te.useragent;

import gov.nist.hla.te.useragent.rti.*;

import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.utils.CpswtUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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

    private boolean isMarketRunning = false;

    public UserAgent(UserAgentConfig params) throws Exception {
        super(params);
        
        Map<String, ArrayList<Double>> loadForecastData = new HashMap<String, ArrayList<Double>>();

        log.info("reading configuration file at {}", params.inputFilePath);
        BufferedReader fileReader = new BufferedReader(new FileReader(params.inputFilePath));

        String[] header = fileReader.readLine().split(",");
        for (String houseId : header) {
            loadForecastData.put(houseId, new ArrayList<Double>());
        }

        String line = fileReader.readLine();
        while (line != null) {
            String[] data = line.split(",");
            for (int i = 0; i < data.length; i++) {
                loadForecastData.get(header[i]).add(Double.parseDouble(data[i]));
            }
            line = fileReader.readLine();
        }
        fileReader.close();

        for (Map.Entry<String, ArrayList<Double>> entry : loadForecastData.entrySet()) {
            agents.put(entry.getKey(), new HouseAgent(entry.getKey(), entry.getValue()));
            log.info("initialized House TEUA {}", entry.getKey());
        }
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

            CpswtUtils.sleep(800); // temporary fix for race condition
            lrc.tick();

            do {
                checkReceivedSubscriptions();
                if (!activeMarkets.isEmpty()) {
                    synchronized (lrc) {
                        lrc.tick();
                    }
                    CpswtUtils.sleep(100);
                }
            } while (!activeMarkets.isEmpty());

            log.debug("thru");

            if (isMarketRunning) {
                for (Agent a : agents.values()) {
                    a.closeMarket();
                    // TODO: csv output
                }
                isMarketRunning = false;
            }

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

    private void handleInteractionClass(Quote interaction) {
        log.debug("received quote");
        final String marketId = interaction.get_marketId();
        final String priceString = interaction.get_price();
        final String quantityString = interaction.get_quantity();
        final boolean isBuyQuote = (interaction.get_side() == 'b');

        if (activeMarkets.add(marketId)) {
            isMarketRunning = true;
            log.info("Detected new market {}", marketId);
        }

        for (Agent a : agents.values()) {
            if (a.getTransformerId().equals(marketId)) {
                String tenderQuantity = a.handleQuote(priceString, quantityString, isBuyQuote);

                if (!tenderQuantity.isEmpty()) {
                    Tender tender = create_Tender();
                    tender.set_marketId(marketId);
                    tender.set_id(interaction.get_id());
                    tender.set_partyId(a.getAgentId());
                    tender.set_counterPartyId(interaction.get_partyId());
                    tender.set_side(interaction.get_side());
                    tender.set_interval(scenarioTime.toString());
                    tender.set_price(priceString);
                    tender.set_quantity(tenderQuantity);
                    tender.sendInteraction(getLRC());
                    log.info("{} sent {} tender for {}", a.getAgentId(), interaction.get_side(), tenderQuantity);
                }
            }
        }
    }

    private void handleInteractionClass(Transaction interaction) {
        Agent agent = agents.get(interaction.get_counterPartyId());

        if (agent != null) {
            final boolean isBuyQuote = (interaction.get_side() == 'b');
            agent.handleTransaction(interaction.get_price(), interaction.get_quantity(), isBuyQuote);
            log.info("{} received {} transaction for {}", agent.getAgentId(), interaction.get_side(), interaction.get_quantity());
        } else {
            log.warn("no user agent {}", interaction.get_counterPartyId());
        }
    }

    private void handleInteractionClass(MarketClosed interaction) {
        final String marketId = interaction.get_marketId();

        if (activeMarkets.remove(marketId)) {
            log.info("Processed MarketClosed for market {}", marketId);
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
            UserAgentConfig federateConfig =
                federateConfigParser.parseArgs(args, UserAgentConfig.class);
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
