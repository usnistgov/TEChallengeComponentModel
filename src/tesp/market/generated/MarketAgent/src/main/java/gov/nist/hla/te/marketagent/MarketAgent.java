package gov.nist.hla.te.marketagent;

import gov.nist.hla.te.marketagent.rti.*;

import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.utils.CpswtUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MarketAgent extends MarketAgentBase {
    public class MarketInfo {
        private String id;
        private double capacity;

        private double[] buyTotal    = new double[24];
        private double[] buyPending  = new double[24];
        private double[] sellTotal   = new double[24];
        private double[] sellPending = new double[24];

        MarketInfo(String id, double capacity) {
            this.id = id;
            this.capacity = capacity;

            reset();
        }

        public String getId() {
            return id;
        }

        public double getCapacity() {
            return capacity;
        }

        public double getTransformerFlow(int slot) {
            return (buyTotal[slot] - sellTotal[slot]) / capacity;
        }

        public void process(char side, String quantity) {
            String[] splitQuantities = quantity.split(" ");

            for (int i = 0; i < 24; i++) {
                if (side == 'b') {
                    buyPending[i] += Double.parseDouble(splitQuantities[i]);
                } else {
                    sellPending[i] += Double.parseDouble(splitQuantities[i]);
                }
            }
        }

        public boolean commit() {
            boolean isChanged = false;
            for (int i = 0; i < 24; i++) {
                isChanged = isChanged || buyPending[i] > 0 || sellPending[i] > 0;

                buyTotal[i]   += buyPending[i];
                buyPending[i]  = 0;
                sellTotal[i]  += sellPending[i];
                sellPending[i] = 0;
            }
            return isChanged;
        }

        public void reset() {
            for (int i = 0; i < 24; i++) {
                buyTotal[i]    = 0;
                buyPending[i]  = 0;
                sellTotal[i]   = 0;
                sellPending[i] = 0;
            }
        }
    }

    private final static Logger log = LogManager.getLogger();

    private boolean receivedSimTime = false;
    
    private double currentTime = 0;
    private double logicalTimeScale;

    private ZonedDateTime scenarioTime;
    private ZonedDateTime scenarioTimeStop;

    private BufferedReader fileReader;
    private String fileReaderNextLine;

    private Double[] dayAheadPrice = new Double[24];
    private Map<String, MarketInfo> marketInfo = new HashMap<String, MarketInfo>();

    private String[] userAgents;

    private int marketRound = 0;
    private boolean readyToClear = false;
    private Set<String> pendingTenders = new HashSet<String>();

    private static final double[] steps  = {-1.25, -1.00, -0.75, 0.75,  0.93,  1.08,  1.22,  1.35,  1.48,  1.61,  1.74,  1.87,  2.00};
    private static final double[] mPrice = {0.250, 0.750, 1.000, 1.090, 1.304, 1.565, 1.877, 2.250, 2.701, 3.239, 3.870, 4.602, 5.000};

    public MarketAgent(MarketAgentConfig params) throws Exception {
        super(params);

        this.userAgents = params.userAgents;

        log.info("opening day ahead price file at {}", params.priceFilePath);
        fileReader = new BufferedReader(new FileReader(params.priceFilePath));
        fileReaderNextLine = fileReader.readLine();

        initializeMarkets(params.transformerFilePath);
    }

    private void initializeMarkets(String filePath) throws IOException {
        log.info("opening transformer file at {}", filePath);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        boolean endOfFile = false;
        while (!endOfFile) {
            String nextLine = reader.readLine();

            if (nextLine == null) {
                endOfFile = true;
            } else {
                String[] data = nextLine.split(",");
                if (marketInfo.containsKey(data[0])) {
                    log.warn("multiple transformers defined with id = {}", data[0]);
                }

                MarketInfo market = new MarketInfo(data[0], Double.parseDouble(data[1]));
                marketInfo.put(market.getId(), market);
                log.info("initialized new market with id = {}", market.getId());
            }
        }
    }

    private void resetPendingTenders() {
        pendingTenders.clear();
        for (String id : userAgents) {
            pendingTenders.add(id + ":s");
            pendingTenders.add(id + ":b");
        }
    }

    private void incrementScenarioTime() {
        final double scenarioTimeDelta = this.getStepSize() * logicalTimeScale;
        scenarioTime = scenarioTime.plusSeconds((long)scenarioTimeDelta);
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

        boolean marketInitialized = false;

        while (!exitCondition) {
            atr.requestSyncStart();
            enteredTimeGrantedState();

            log.info("t = {} / {}", this.getCurrentTime(), scenarioTime.toString());

            if ((scenarioTime.getHour() == 17 && scenarioTime.getMinute() == 0) || !marketInitialized) {
                startNextMarket();
                
                marketRound = 1;
                sendQuotes();

                marketInitialized = true;
            }

            do {
                checkReceivedSubscriptions();
                if (marketRound > 0) {
                    synchronized (lrc) {
                        lrc.tick();
                    }
                    CpswtUtils.sleep(100);
                }
            } while (marketRound > 0);

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

        fileReader.close();
        exitGracefully();
    }

    private void handleInteractionClass(Tender interaction) {
        final String tenderId = interaction.get_partyId() + ":" + interaction.get_side();
        if (!pendingTenders.remove(tenderId)) {
            log.warn("received unexpected tender with id {}", tenderId);
        }
        // TODO: have market track each party
        marketInfo.get(interaction.get_marketId()).process(interaction.get_side(), interaction.get_quantity());

        /// temporary code until transactions exist
        Transaction transaction = create_Transaction();
        transaction.set_marketId(interaction.get_marketId());
        transaction.set_id(Integer.toString(marketRound));
        transaction.set_partyId("utility");
        transaction.set_counterPartyId(interaction.get_partyId());
        transaction.set_side(interaction.get_side());
        transaction.set_interval(interaction.get_interval());
        transaction.set_price(interaction.get_price());
        transaction.set_quantity(interaction.get_quantity());
        transaction.sendInteraction(getLRC());
        ///
            log.warn("received unexpected tender with id {}", tenderId);

        if (pendingTenders.isEmpty()) {
            handleRoundEnd();
        }
    }

    private void handleRoundEnd() {
        boolean marketUpdated = false;
        for (MarketInfo market : marketInfo.values()) {
            // TODO: calculate quantity
            marketUpdated = marketUpdated || market.commit();
        }

        if (marketUpdated) {
            sendQuotes();
            readyToClear = false;
        } else {
            if (readyToClear) {
                for (MarketInfo market : marketInfo.values()) {
                    MarketClosed closed = create_MarketClosed();
                    closed.set_marketId(market.getId());
                    closed.sendInteraction(getLRC());

                    market.reset();
                }
                readyToClear = false;
                marketRound = 0;
            } else {
                readyToClear = true;
            }
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

    private ZonedDateTime convertToScenarioTimeZone(String date) {
        return ZonedDateTime.parse(date).withZoneSameInstant(scenarioTime.getZone());
    }

    private void startNextMarket() throws IOException {
        LocalDate marketLocalDate = scenarioTime.toLocalDate();
        if (scenarioTime.getHour() < 17) {
            marketLocalDate.minusDays(1);
        }
        ZonedDateTime marketStartTime = ZonedDateTime.of(marketLocalDate, LocalTime.of(17,0), scenarioTime.getZone());
        log.info("starting new market for {}", marketStartTime);

        boolean reachedMarketStartTime = false;
        while (!reachedMarketStartTime) {
            if (fileReaderNextLine == null) {
                log.error("missing price data for {}", marketStartTime);
                return;
            }

            ZonedDateTime nextDateTime = convertToScenarioTimeZone(fileReaderNextLine.split(",")[0]);
            if (nextDateTime.equals(marketStartTime)) {
                reachedMarketStartTime = true;
            } else if (nextDateTime.isBefore(marketStartTime)) {
                fileReaderNextLine = fileReader.readLine();
            } else { // isAfter
                log.error("missing price data for {}", marketStartTime);
                return;
            }
        }

        for (int hour_offset = 0; hour_offset < 24; hour_offset++) {
            if (fileReaderNextLine == null) {
                log.error("missing price data for {}", marketStartTime);
                return;
            }

            String[] priceData = fileReaderNextLine.split(","); // format: DateTime,Price
            if (Duration.between(marketStartTime, convertToScenarioTimeZone(priceData[0])).toHours() != hour_offset) {
                log.error("missing hourly data for {}", marketStartTime);
                return;
            }

            dayAheadPrice[hour_offset] = Double.parseDouble(priceData[1]);
            log.debug("day ahead price = {} for t = {}", priceData[1], priceData[0]);

            fileReaderNextLine = fileReader.readLine();
        }
    }

    private void sendQuotes() {
        resetPendingTenders();

        for (MarketInfo market : marketInfo.values()) {
            String buyPrice = "";
            String buyQuantity = "";
            String sellPrice = "";
            String sellQuantity = "";

            boolean validBuyQuote = true;

            for (int slot = 0; slot < 24; slot++) {
                if (slot > 0) {
                    buyPrice += " ";
                    buyQuantity += " ";
                    sellPrice += " ";
                    sellQuantity += " ";
                }

                double mFlow = market.getTransformerFlow(slot);

                int risingIndex;
                for (risingIndex = 0; risingIndex < steps.length && mFlow >= steps[risingIndex]; risingIndex++);

                if (risingIndex == steps.length) {
                    log.error("cannot generate buy quote for mFlow = {}", mFlow);
                    validBuyQuote = false;
                } else {
                    buyPrice += String.format("%.4f", mPrice[risingIndex] * dayAheadPrice[slot]);
                    buyQuantity += String.format("%.4f", Math.abs(steps[risingIndex] - mFlow) * market.getCapacity());
                }

                if (risingIndex == 0) {
                    sellPrice += "0.0000";
                    sellQuantity += "10.0"; // TODO: how to indicate infinite ?
                } else {
                    sellPrice += String.format("%.4f", mPrice[risingIndex-1] * dayAheadPrice[slot]);
                    sellQuantity += String.format("%.4f", Math.abs(mFlow - steps[risingIndex-1]) * market.getCapacity());
                }
            }

            if (validBuyQuote) {
                Quote buyQuote = create_Quote();
                buyQuote.set_marketId(market.getId());
                buyQuote.set_id(Integer.toString(marketRound));
                buyQuote.set_partyId("utility");
                buyQuote.set_counterPartyId(market.getId());
                buyQuote.set_side('b');
                buyQuote.set_interval(scenarioTime.toString());
                buyQuote.set_price(buyPrice);
                buyQuote.set_quantity(buyQuantity);
                buyQuote.sendInteraction(getLRC());
                log.debug("buy price: {}", buyPrice);
                log.debug("buy quantity: {}", buyQuantity);
            }

            Quote sellQuote = create_Quote();
            sellQuote.set_marketId(market.getId());
            sellQuote.set_id(Integer.toString(marketRound));
            sellQuote.set_partyId("utility");
            sellQuote.set_counterPartyId(market.getId());
            sellQuote.set_side('s');
            sellQuote.set_interval(scenarioTime.toString());
            sellQuote.set_price(sellPrice);
            sellQuote.set_quantity(sellQuantity);
            sellQuote.sendInteraction(getLRC());
            log.debug("sell price: {}", sellPrice);
            log.debug("sell quantity: {}", sellQuantity);
        }
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            MarketAgentConfig federateConfig =
                federateConfigParser.parseArgs(args, MarketAgentConfig.class);
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
