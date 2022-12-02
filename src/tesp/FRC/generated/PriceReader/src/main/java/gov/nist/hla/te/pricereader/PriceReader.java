package gov.nist.hla.te.pricereader;

import gov.nist.hla.te.pricereader.rti.*;

import org.cpswt.config.FederateConfigParser;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.base.AdvanceTimeRequest;
import org.cpswt.utils.CpswtUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Define the PriceReader type of federate for the federation.

public class PriceReader extends PriceReaderBase {
    private final static Logger log = LogManager.getLogger();

    private boolean receivedSimTime = false;
    
    private double currentTime = 0;
    private double logicalTimeScale;

    private ZonedDateTime scenarioTime;
    private LocalDate localDate = null;

    private BufferedReader dapReader;
    private BufferedReader rtpReader;

    private String dapNextLine;
    private String rtpNextLine;

    private ZonedDateTime rtpNextTime;

    public PriceReader(PriceReaderConfig params) throws Exception {
        super(params);

        log.info("opening day ahead price file at {}", params.dapFilePath);
        dapReader = new BufferedReader(new FileReader(params.dapFilePath));

        log.info("opening real time price file at {}", params.rtpFilePath);
        rtpReader = new BufferedReader(new FileReader(params.rtpFilePath));
    }

    private void incrementScenarioTime() {
        final double scenarioTimeDelta = this.getStepSize() * logicalTimeScale;
        scenarioTime = scenarioTime.plusSeconds((long)scenarioTimeDelta);
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
        initializePriceData();

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

            if (currentTime == 0 || !localDate.equals(scenarioTime.toLocalDate())) {
                localDate = scenarioTime.toLocalDate();
                log.info("Start of new day: {}", localDate.toString());
                sendDayAheadPrices(localDate.plusDays(1), currentTime + getLookAhead());
            }

            sendRealTimePrice(currentTime + getLookAhead());

            // Set the interaction's parameters.
            //
            //    RealTimePrice realTimePrice = create_RealTimePrice();
            //    realTimePrice.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    realTimePrice.set_federateFilter( < YOUR VALUE HERE > );
            //    realTimePrice.set_originFed( < YOUR VALUE HERE > );
            //    realTimePrice.set_sourceFed( < YOUR VALUE HERE > );
            //    realTimePrice.set_time( < YOUR VALUE HERE > );
            //    realTimePrice.set_value( < YOUR VALUE HERE > );
            //    realTimePrice.sendInteraction(getLRC(), currentTime + getLookAhead());

            checkReceivedSubscriptions();

            if (!exitCondition) {
                incrementScenarioTime();
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

        dapReader.close();
        rtpReader.close();
    }
    
    private ZonedDateTime convertToScenarioTimeZone(String date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date);
        return zonedDateTime.withZoneSameInstant(scenarioTime.getZone());
    }

    private void dapAdvanceToDate(LocalDate targetDate) throws IOException {
        boolean foundTargetDate = false;

        while (!foundTargetDate) {
            if (dapNextLine == null ){
                log.error("ran out of day ahead price data");
                return; // TODO: propogate error
            }

            String[] priceData = dapNextLine.split(","); // format: DateTime,DAP
            LocalDate priceDate = convertToScenarioTimeZone(priceData[0]).toLocalDate();

            if (priceDate.equals(targetDate)) {
                foundTargetDate = true;
            } else {
                dapNextLine = dapReader.readLine();
            }
        }
    }

    // assumes dapNextLine is for the targetDate
    // use dapAdvanceToDate to move to a targetDate
    void sendDayAheadPrices(LocalDate targetDate, double logicalTime) throws IOException {
        boolean afterTargetDate = false;

        while (!afterTargetDate) {
            if (dapNextLine == null ){
                log.error("ran out of day ahead price data");
                return; // TODO: propogate error
            }

            String[] priceData = dapNextLine.split(","); // format: DateTime,DAP
            ZonedDateTime priceTime = convertToScenarioTimeZone(priceData[0]);

            if (!priceTime.toLocalDate().equals(targetDate)) {
                afterTargetDate = true;
            } else {
                DayAheadPrice dayAheadPrice = create_DayAheadPrice();
                dayAheadPrice.set_time(priceTime.toString());
                dayAheadPrice.set_value(Double.parseDouble(priceData[1]));
                if (logicalTime > 0) {
                    dayAheadPrice.sendInteraction(getLRC(), logicalTime);
                } else {
                    dayAheadPrice.sendInteraction(getLRC()); // RO
                }
                log.info("sent {} {}", priceTime.toString(), priceData[1]);

                dapNextLine = dapReader.readLine();
            }
        }
    }

    private void sendRealTimePrice(double logicalTime) throws IOException {
        boolean foundMostRecent = false;

        String mostRecentTime = null;
        String mostRecentPrice = null;

        while (!foundMostRecent) {
            if (rtpNextLine == null ){
                log.error("ran out of real time price data");
                return; // TODO: propogate error
            }
            String[] priceData = rtpNextLine.split(","); // format: DateTime,RTP

            if (ZonedDateTime.parse(priceData[0]).isAfter(scenarioTime)) {
                foundMostRecent = true;
            } else {
                mostRecentTime = convertToScenarioTimeZone(priceData[0]).toString();
                mostRecentPrice = priceData[1];
                rtpNextLine = rtpReader.readLine();
            }
        }

        if (mostRecentTime == null) {
            log.debug("no new real time updates to price");
        } else {
            RealTimePrice realTimePrice = create_RealTimePrice();
            realTimePrice.set_time(mostRecentTime);
            realTimePrice.set_value(Double.parseDouble(mostRecentPrice));
            if (logicalTime > 0) {
                realTimePrice.sendInteraction(getLRC(), logicalTime);
            } else {
                realTimePrice.sendInteraction(getLRC()); // RO
            }
            log.info("sent RTP = {} {}", mostRecentTime, mostRecentPrice);
        }
    }

    private void initializePriceData() throws IOException {
        log.debug("reading first line of day ahead prices...");
        dapNextLine = dapReader.readLine();
        dapAdvanceToDate(scenarioTime.toLocalDate());
        sendDayAheadPrices(scenarioTime.toLocalDate(), 0);

        log.debug("reading first line of real time prices...");
        rtpNextLine = rtpReader.readLine();
        sendRealTimePrice(0);
    }

    private void handleInteractionClass(SimTime interaction) {
        if (receivedSimTime) { // prevent localDate overwrite
            log.debug("dropped duplicate SimTime interaction");
            return;
        }

        logicalTimeScale = interaction.get_timeScale();
        scenarioTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(interaction.get_unixTimeStart()), TimeZone.getTimeZone(interaction.get_timeZone()).toZoneId());
        localDate = scenarioTime.toLocalDate();
        receivedSimTime = true;

        log.info("received SimTime starting at {}", scenarioTime.toString());
    }

    public static void main(String[] args) {
        try {
            FederateConfigParser federateConfigParser =
                new FederateConfigParser();
            PriceReaderConfig federateConfig =
                federateConfigParser.parseArgs(args, PriceReaderConfig.class);
            PriceReader federate =
                new PriceReader(federateConfig);
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
