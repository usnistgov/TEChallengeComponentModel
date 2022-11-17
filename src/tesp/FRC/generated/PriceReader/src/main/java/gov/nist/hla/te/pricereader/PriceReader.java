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
import java.time.ZoneId;
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

    private LocalDate currentDate;
    private ZonedDateTime scenarioTime;

    private BufferedReader dapReader;
    private BufferedReader rtpReader;

    private String dapNextLine;
    private String rtpNextLine;

    private ZonedDateTime rtpNextTime;
    private boolean dapSent = false;

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
        dapInitialize();
        rtpInitialize();

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

            if (!currentDate.equals(scenarioTime.toLocalDate())) {
                currentDate = scenarioTime.toLocalDate();
                dapSent = false;
                log.info("Start of new day: {}", currentDate.toString());
            }

            if (!dapSent && scenarioTime.getHour() > 12) {
                while (!dapSent && dapNextLine != null) {
                    String[] priceData = dapNextLine.split(","); // format: DateTime,DAP
                    if (!isSameDate(ZonedDateTime.parse(priceData[0]), scenarioTime, scenarioTime.getZone())) {
                        dapSent = true;
                        continue;
                    }
        
                    DayAheadPrice dayAheadPrice = create_DayAheadPrice();
                    dayAheadPrice.set_time(priceData[0]);
                    dayAheadPrice.set_value(Double.parseDouble(priceData[1]));
                    dayAheadPrice.sendInteraction(getLRC(), currentTime + getLookAhead());
                    log.info("sent {} {}", priceData[0], priceData[1]);
        
                    dapNextLine = dapReader.readLine();
                }
                if (dapNextLine == null) {
                    log.error("DAP input doesn't contain data for {}", scenarioTime.toLocalDate());
                    //throw new IOException("DAP data out of range");
                }
            }

            while (rtpNextTime.isBefore(scenarioTime) && rtpNextLine != null) {
                while ((rtpNextLine = rtpReader.readLine()) != null) {
                    String[] priceData = rtpNextLine.split(","); // format: DateTime,RTP
                    
                    if (ZonedDateTime.parse(priceData[0]).isAfter(scenarioTime)) {
                        RealTimePrice realTimePrice = create_RealTimePrice();
                        realTimePrice.set_time(priceData[0]);
                        realTimePrice.set_value(Double.parseDouble(priceData[1]));
                        realTimePrice.sendInteraction(getLRC()); // RO
                        log.info("sent RTP = {} {}", priceData[0], priceData[1]);
                        break;
                    }
        
                    rtpNextTime = ZonedDateTime.parse(priceData[0]); // must be here don't ask
                }
            }
            if (rtpNextLine == null) {
                log.error("RTP input doesn't contain data for {}", scenarioTime.toLocalDate());
                //throw new IOException("DAP data out of range");
            }

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

    boolean isSameDate(ZonedDateTime a, ZonedDateTime b, ZoneId timezone) {
        ZonedDateTime a_timezone = a.withZoneSameInstant(timezone);
        ZonedDateTime b_timezone = b.withZoneSameInstant(timezone);
        return a_timezone.toLocalDate().equals(b_timezone.toLocalDate());
    }

    private void dapInitialize() throws IOException {
        while ((dapNextLine = dapReader.readLine()) != null) {
            String[] priceData = dapNextLine.split(","); // format: DateTime,DAP
            if (isSameDate(ZonedDateTime.parse(priceData[0]), scenarioTime, scenarioTime.getZone())) {
                log.debug("starting DAP from {}", dapNextLine);
                break;
            }
        }

        boolean isInitialized = false;
        while (!isInitialized && dapNextLine != null) {
            String[] priceData = dapNextLine.split(","); // format: DateTime,DAP
            if (!isSameDate(ZonedDateTime.parse(priceData[0]), scenarioTime, scenarioTime.getZone())) {
                isInitialized = true;
                continue;
            }

            DayAheadPrice dayAheadPrice = create_DayAheadPrice();
            dayAheadPrice.set_time(priceData[0]);
            dayAheadPrice.set_value(Double.parseDouble(priceData[1]));
            dayAheadPrice.sendInteraction(getLRC()); // RO
            log.info("sent {} {}", priceData[0], priceData[1]);

            dapNextLine = dapReader.readLine();
        }
        if (dapNextLine == null) {
            log.error("DAP input doesn't contain data for {}", scenarioTime.toLocalDate());
            throw new IOException("DAP data out of range");
        }
    }

    private void rtpInitialize() throws IOException { // doesn't work if input file hs no values <= scenarioTime
        while ((rtpNextLine = rtpReader.readLine()) != null) {
            String[] priceData = rtpNextLine.split(","); // format: DateTime,RTP
            
            if (ZonedDateTime.parse(priceData[0]).isAfter(scenarioTime)) {
                RealTimePrice realTimePrice = create_RealTimePrice();
                realTimePrice.set_time(priceData[0]);
                realTimePrice.set_value(Double.parseDouble(priceData[1]));
                realTimePrice.sendInteraction(getLRC()); // RO
                log.info("sent RTP = {} {}", priceData[0], priceData[1]);
                break;
            }

            rtpNextTime = ZonedDateTime.parse(priceData[0]); // must be here don't ask
        }
    }

    private void handleInteractionClass(SimTime interaction) {
        logicalTimeScale = interaction.get_timeScale();
        scenarioTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(interaction.get_unixTimeStart()), TimeZone.getTimeZone(interaction.get_timeZone()).toZoneId());
        currentDate = scenarioTime.toLocalDate();
        log.info("received SimTime starting at {}", scenarioTime.toString());
        receivedSimTime = true;
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
