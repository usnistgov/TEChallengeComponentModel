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
        private MarketAgent agent;
        private String id;
        private double capacity;

        private double[] buyTotal    = new double[24];
        private double[] buyPending  = new double[24];
        private double[] sellTotal   = new double[24];
        private double[] sellPending = new double[24];

        private double[] buyQuantity = new double[24];
        private double[] sellQuantity = new double[24];
    
        private Set<Transaction> pendingTransactions = new HashSet<Transaction>();

        MarketInfo(MarketAgent agent, String id, double capacity) {
            this.agent = agent;
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
            return (sellTotal[slot] - buyTotal[slot]) / capacity;
        }

        public void updateQuote(String buyQuantity, String sellQuantity) {
            // TODO: the buy quantity might not be valid
            String[] splitBuyQuantities = buyQuantity.split(" ");
            String[] splitSellQuantities = sellQuantity.split(" ");

            for (int i = 0; i < 24; i++) {
                this.buyQuantity[i] = Double.parseDouble(splitBuyQuantities[i]);
                this.sellQuantity[i] = Double.parseDouble(splitSellQuantities[i]);
            }
        }

        public void process(Tender tender) {
            // TODO: the quote might not exist yet
            String[] splitQuantities = tender.get_quantity().split(" ");

            for (int i = 0; i < 24; i++) {
                if (tender.get_side() == 'b') {
                    buyPending[i] += Double.parseDouble(splitQuantities[i]);
                } else {
                    sellPending[i] += Double.parseDouble(splitQuantities[i]);
                }
            }

            Transaction transaction = agent.create_Transaction();
            transaction.set_marketId(id);
            transaction.set_id(tender.get_id());
            transaction.set_partyId("utility");
            transaction.set_counterPartyId(tender.get_partyId());
            transaction.set_side(tender.get_side());
            transaction.set_interval(tender.get_interval());
            transaction.set_price(tender.get_price());
            transaction.set_quantity(tender.get_quantity());
            pendingTransactions.add(transaction);
        }

        public boolean commit() {
            boolean isChanged = false;

            double[] buyAccepted = new double[24];
            double[] sellAccepted = new double[24];

            for (int i = 0; i < 24; i++) {
                buyAccepted[i] = Math.min(buyPending[i], buyQuantity[i] + sellPending[i]);
                sellAccepted[i] = Math.min(sellPending[i], sellQuantity[i] + buyPending[i]);
            }

            for (Transaction transaction : pendingTransactions) {
                boolean nonZero = false;

                String[] splitQuantity = transaction.get_quantity().split(" ");
                String newQuantity = "";

                for (int i = 0; i < 24; i++) {
                    double quantity = Double.parseDouble(splitQuantity[i]);

                    if (quantity > 0.0) { // fails when i = 1
                        if (transaction.get_side() == 'b' && buyAccepted[i] != buyPending[i]) {
                            quantity = (quantity / buyPending[i]) * buyAccepted[i];
                        } else if (transaction.get_side() == 's' && sellAccepted[i] != sellPending[i]) {
                            quantity = (quantity / sellPending[i]) * sellAccepted[i];
                        }
                        nonZero = true;
                    }

                    if (i > 0) {
                        newQuantity += " ";
                    }
                    newQuantity += String.format("%.4f", quantity);
                }
            
                if (nonZero) {
                    transaction.set_quantity(newQuantity);
                    transaction.sendInteraction(agent.getLRC());
                    log.info("transaction for {} with quantity {}", transaction.get_counterPartyId(), newQuantity);
                }
            }
            pendingTransactions.clear();

            for (int i = 0; i < 24; i++) {
                isChanged = isChanged || buyAccepted[i] > 0 || sellAccepted[i] > 0;

                buyTotal[i]   += buyAccepted[i];
                buyPending[i]  = 0;
                sellTotal[i]  += sellAccepted[i];
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


    private static final double[] steps  = {2.00,  1.87,  1.74,  1.61,  1.48,  1.35,  1.22,  1.08,  0.93,  0.75, -0.75, -1.00, -1.25};
    private static final double[] mPrice = {5.000, 4.602, 3.870, 3.239, 2.701, 2.250, 1.877, 1.565, 1.304, 1.090, 1.000, 0.750, 0.250};

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

                MarketInfo market = new MarketInfo(this, data[0], Double.parseDouble(data[1]));
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
                    CpswtUtils.sleep(200);
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
        marketInfo.get(interaction.get_marketId()).process(interaction);

        if (pendingTenders.isEmpty()) {
            handleRoundEnd();
        }
    }

    private void handleRoundEnd() {
        boolean marketUpdated = false;
        for (MarketInfo market : marketInfo.values()) {
            // TODO: calculate quantity
            if (market.commit()) {
                marketUpdated = true;
            }
        }

        if (marketUpdated) {
            sendQuotes();
            marketRound += 1;
            readyToClear = false;
        } else {
            if (readyToClear) {
                log.info("market closed");
                for (MarketInfo market : marketInfo.values()) {
                    MarketClosed closed = create_MarketClosed();
                    closed.set_marketId(market.getId());
                    closed.sendInteraction(getLRC());

                    market.reset();
                }
                readyToClear = false;
                marketRound = 0;
            } else {
                sendQuotes();
                marketRound += 1;
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
        log.info("round {}", marketRound);
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

                int sellIndex;
                final double tolerance = 1e-4;
                for (sellIndex = 0; sellIndex < steps.length && steps[sellIndex] - tolerance > mFlow; sellIndex++);

                // TODO - swap array sequence
                if (sellIndex == steps.length) {
                    buyPrice += "0.0000";
                    buyQuantity += "0.0"; // should this have a quantity ? possible convergence error
                    sellPrice += "0.0000";
                    sellQuantity += String.format("%.4f", Math.abs(steps[sellIndex] - mFlow) * market.getCapacity());
                } else if (steps[sellIndex] + tolerance < mFlow) {
                    buyPrice += String.format("%.4f", mPrice[sellIndex] * dayAheadPrice[slot]);
                    buyQuantity += String.format("%.4f", Math.abs(mFlow - steps[sellIndex]) * market.getCapacity());
                    sellPrice += String.format("%.4f", mPrice[sellIndex] * dayAheadPrice[slot]);
                    if (sellIndex == 0) {
                        sellQuantity += "10.0"; // should this be a different quantity ?
                    } else {
                        sellQuantity += String.format("%.4f", Math.abs(steps[sellIndex-1] - mFlow) * market.getCapacity());
                    }
                } else {
                    if (sellIndex == steps.length - 1) {
                        buyPrice += "0.0000";
                        buyQuantity += "0.0"; // should this have a quantity ? possible convergence error
                    } else {
                        buyPrice += String.format("%.4f", mPrice[sellIndex+1] * dayAheadPrice[slot]);
                        buyQuantity += String.format("%.4f", Math.abs(mFlow - steps[sellIndex+1]) * market.getCapacity());
                    }
                    sellPrice += String.format("%.4f", mPrice[sellIndex] * dayAheadPrice[slot]);
                    if (sellIndex == 0) {
                        sellQuantity += "10.0"; // should this be a different quantity ?
                    } else {
                        sellQuantity += String.format("%.4f", Math.abs(steps[sellIndex-1] - mFlow) * market.getCapacity());
                    }
                }
            }

            market.updateQuote(buyQuantity, sellQuantity);

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
            log.info("{} sent buy quote with quantity {}", market.getId(), buyQuantity);
            log.info("{} sent buy quote with price {}", market.getId(), buyPrice);

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
            log.info("{} sent sell quote with quantity {}", market.getId(), sellQuantity);
            log.info("{} sent sell quote with price {}", market.getId(), sellPrice);
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
