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
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Define the PriceReader type of federate for the federation.

public class PriceReader extends PriceReaderBase {
    private final static Logger log = LogManager.getLogger();

    private boolean receivedSimTime = false;
    
    private double currentTime = 0;
    private double logicalTimeScale;

    private ZonedDateTime scenarioTime;

    private BufferedReader dapReader;
    private BufferedReader rtpReader;

    private String dapNextLine;
    private String rtpNextLine;

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

        // seek both buffers to current day somehow
        seek(dapReader, scenarioTime);

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

            ////////////////////////////////////////////////////////////
            // TODO send interactions that must be sent every logical //
            // time step below                                        //
            ////////////////////////////////////////////////////////////

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
            //    DayAheadPrice dayAheadPrice = create_DayAheadPrice();
            //    dayAheadPrice.set_actualLogicalGenerationTime( < YOUR VALUE HERE > );
            //    dayAheadPrice.set_federateFilter( < YOUR VALUE HERE > );
            //    dayAheadPrice.set_originFed( < YOUR VALUE HERE > );
            //    dayAheadPrice.set_sourceFed( < YOUR VALUE HERE > );
            //    dayAheadPrice.set_time( < YOUR VALUE HERE > );
            //    dayAheadPrice.set_value( < YOUR VALUE HERE > );
            //    dayAheadPrice.sendInteraction(getLRC(), currentTime + getLookAhead());

            checkReceivedSubscriptions();

            ////////////////////////////////////////////////////////////////////
            // TODO break here if ready to resign and break out of while loop //
            ////////////////////////////////////////////////////////////////////

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

        //////////////////////////////////////////////////////////////////////
        // TODO Perform whatever cleanups are needed before exiting the app //
        //////////////////////////////////////////////////////////////////////
    }

    private String seek(BufferedReader reader, ZonedDateTime timestamp) throws IOException
    {
        String row;
        while ((row = reader.readLine()) != null) {
            String[] column = row.split(",");
            ZonedDateTime ts = ZonedDateTime.parse(column[0]).withZoneSameInstant(timestamp.getZone());
            if (timestamp.toLocalDate().equals(ts.toLocalDate())) {
                log.info("{} {}", column[0], ts.toString());
            }
            //DateUtils.isSameDay(timestamp, LocalDateTime.parse(column[0]))
        }
        return row; // can be null
    }

    private void handleInteractionClass(SimTime interaction) {
        logicalTimeScale = interaction.get_timeScale();
        scenarioTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(interaction.get_unixTimeStart()), TimeZone.getTimeZone(interaction.get_timeZone()).toZoneId());
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
