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
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MarketAgent extends MarketAgentBase {
    private final static Logger log = LogManager.getLogger();

    private boolean receivedSimTime = false;
    
    private double currentTime = 0;
    private double logicalTimeScale;

    private ZonedDateTime scenarioTime;
    private ZonedDateTime scenarioTimeStop;

    private BufferedReader fileReader;
    private String fileReaderNextLine;

    private Double[] dayAheadPrice = new Double[24];
    private Map<String, Double> capacity = new HashMap<String, Double>();

    private static final double[] steps  = {-1.25, -1.00, -0.75, 0.75,  0.93,  1.08,  1.22,  1.35,  1.48,  1.61,  1.74,  1.87,  2.00};
    private static final double[] mPrice = {0.250, 0.750, 1.000, 1.090, 1.304, 1.565, 1.877, 2.250, 2.701, 3.239, 3.870, 4.602, 5.000};

    public MarketAgent(MarketAgentConfig params) throws Exception {
        super(params);

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
                if (capacity.containsKey(data[0])) {
                    log.warn("multiple transformers defined with id = {}", data[0]);
                }
                capacity.put(data[0], Double.parseDouble(data[1]));
                log.info("initialized new market with id = {}", data[0]);
            }
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
                sendQuotes("R4_12_47_1_xfmr_123");
                marketInitialized = true;
            }

            checkReceivedSubscriptions();

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
        ///////////////////////////////////////////////////////////////
        // TODO implement how to handle reception of the interaction //
        ///////////////////////////////////////////////////////////////
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

    private double getTransformerFlow(String marketId, int interval) {
        double powerFlow = 0;

        // calculate powerFlow for settled transactions

        return powerFlow / capacity.get(marketId);
    }

    private void sendQuotes(String marketId) {
        for (int interval = 0; interval < 24; interval++) {
            double mFlow = getTransformerFlow(marketId, interval);

            int sellIndex;
            for (sellIndex = 0; sellIndex < steps.length && mFlow >= steps[sellIndex]; sellIndex++);

            if (sellIndex == steps.length) {
                // infinite sell ?
            } else  {
                double sellPrice = mPrice[sellIndex] * dayAheadPrice[interval];
                double sellQuantity = Math.abs(steps[sellIndex] - mFlow) * capacity.get(marketId);
                log.debug("{} sell {} for {}", interval, sellQuantity, sellPrice);
                // send quote
            }

            if (sellIndex == 0) {
                // no buy ?
            } else {
                double buyPrice = mPrice[sellIndex-1] * dayAheadPrice[interval];
                double buyQuantity = Math.abs(mFlow - steps[sellIndex-1]) * capacity.get(marketId);
                log.debug("{} buy {} for {}", interval, buyQuantity, buyPrice);
                // send quote
            }
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
