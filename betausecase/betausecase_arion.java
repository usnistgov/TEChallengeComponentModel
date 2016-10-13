import gov.pnnl.prosser.api.*;
import gov.pnnl.prosser.api.gld.*;
import gov.pnnl.prosser.api.gld.enums.*;
import gov.pnnl.prosser.api.gld.lib.*;
import gov.pnnl.prosser.api.gld.obj.*;

import java.nio.file.*;
import java.time.*;
import java.util.*;

/**
 * Implementation of simple transactive system on simple power system in GridLAB-D
 *
 * @author Jacob Hansen
 *
 */
public class GLD_test extends Experiment {

    /**
     * Generate the experiment
     */
    @Override
    public void experiment() {

        // number of houses in the system
    	final int numHouses = 30;

        // Initialize the simulator object space
        GldSimulator gldSim = this.gldSimulator("GLD_test");
        
        // Setup the clock
        final GldClock clock = gldSim.clock();
        clock.setTimezone("EST+5EDT");
        clock.setStartTime(LocalDateTime.of(2009, 07, 21, 00, 00));
        clock.setStopTime(LocalDateTime.of(2009, 07, 23, 00, 00));

        // Add settings, configures simulation
        gldSim.addSetting("profiler", "1");
        gldSim.addSetting("double_format", "%+.12lg");
        gldSim.addSetting("randomseed", "10");
        gldSim.addSetting("relax_naming_rules", "1");

        // Add includes, extra glm files that help with simulation
        gldSim.addIncludes(Paths.get("res/water_and_setpoint_schedule_v3.glm"), Paths.get("res/appliance_schedules.glm"));

        // Add a player class definition to allow references to value from player objects
        final PlayerClass playerClass = gldSim.playerClass();
        playerClass.addField("value", "double");

        // Add a auction class definition to allow references to more fields from other objects
        final AuctionClass auctionClass = gldSim.auctionClass();
        // auctionClass.addField("day_ahead_average", "double");
        // auctionClass.addField("day_ahead_std", "double");
        auctionClass.addField("current_price_mean_24h", "double");
        auctionClass.addField("current_price_stdev_24h", "double");

        // Create the auction object
        final AuctionObject auction = gldSim.auctionObject("market");
        auction.setUnit("kW");
        auction.setPeriod(300);
        auction.setPriceCap(3.78);
        auction.setNetworkAveragePriceProperty("current_price_mean_24h");
        auction.setNetworkStdevPriceProperty("current_price_stdev_24h");
        
        // Add a player to the auction for one of its values
        final PlayerObject player = auction.player();
        player.setProperty("fixed_price");
        player.setFile(Paths.get("res/AEP_RT_LMP.player"));
        player.setLoop(1);

        auction.setSpecialMode(SpecialMode.BUYERS_ONLY); 
        
        auction.setInitPrice(0.042676);
        auction.setInitStdev(0.02);
        auction.setUseFutureMeanPrice(false);
        auction.setWarmup(0);       
        
        // Specify the climate information
        final ClimateObject climate = gldSim.climateObject("Columbus OH");
        climate.setTmyFile(Paths.get("res/ColumbusWeather2009_2a.csv"));
        climate.addCsvReader("CSVREADER");
            
        // Create a transformer configuration for the substation
        final TransformerConfiguration substationConfig = gldSim.transformerConfiguration("substation_config");
        substationConfig.setConnectionType(ConnectionType.WYE_WYE);
        substationConfig.setInstallationType(InstallationType.PADMOUNT);
        substationConfig.setPrimaryVoltage(7200);
        substationConfig.setSecondaryVoltage(7200);
        substationConfig.setPowerRating(3 * numHouses * 5.0);
        substationConfig.setPhaseARating(numHouses * 5.0);
        substationConfig.setPhaseBRating(numHouses * 5.0);
        substationConfig.setPhaseCRating(numHouses * 5.0);
        substationConfig.setImpedance(0.0015, 0.00675);

        // Create the transformer configuration for each phase from the substation
        final TransformerConfiguration defaultTransformerA = gldSim.transformerConfiguration("default_transformer_A");
        defaultTransformerA.setPhaseARating(numHouses * 5.0);
        defaultTransformerA.setConnectionType(ConnectionType.SINGLE_PHASE_CENTER_TAPPED);
        defaultTransformerA.setInstallationType(InstallationType.PADMOUNT);
        defaultTransformerA.setPrimaryVoltage(7200);
        defaultTransformerA.setSecondaryVoltage(124);
        defaultTransformerA.setPowerRating(numHouses * 5.0);
        defaultTransformerA.setImpedance(0.015, 0.0675);

        final TransformerConfiguration defaultTransformerB = gldSim.transformerConfiguration("default_transformer_B");
        defaultTransformerB.setPhaseBRating(numHouses * 5.0);
        defaultTransformerB.setConnectionType(ConnectionType.SINGLE_PHASE_CENTER_TAPPED);
        defaultTransformerB.setInstallationType(InstallationType.PADMOUNT);
        defaultTransformerB.setPrimaryVoltage(7200);
        defaultTransformerB.setSecondaryVoltage(124);
        defaultTransformerB.setPowerRating(numHouses * 5.0);
        defaultTransformerB.setImpedance(0.015, 0.0675);

        final TransformerConfiguration defaultTransformerC = gldSim.transformerConfiguration("default_transformer_C");
        defaultTransformerC.setPhaseCRating(numHouses * 5.0);
        defaultTransformerC.setConnectionType(ConnectionType.SINGLE_PHASE_CENTER_TAPPED);
        defaultTransformerC.setInstallationType(InstallationType.PADMOUNT);
        defaultTransformerC.setPrimaryVoltage(7200);
        defaultTransformerC.setSecondaryVoltage(124);
        defaultTransformerC.setPowerRating(numHouses * 5.0);
        defaultTransformerC.setImpedance(0.015, 0.0675);

        // Create the triplex line conductor used everywhere
        final TriplexLineConductor tripLineCond1AA = gldSim.triplexLineConductor("Name_1_0_AA_triplex");
        tripLineCond1AA.setResistance(0.57);
        tripLineCond1AA.setGeometricMeanRadius(0.0111);

        // Create the triplex line configuration used everywhere
        tripLineConf = gldSim.triplexLineConfiguration("TLCFG");
        tripLineConf.setPhase1Conductor(tripLineCond1AA);
        tripLineConf.setPhase2Conductor(tripLineCond1AA);
        tripLineConf.setPhaseNConductor(tripLineCond1AA);
        tripLineConf.setInsulationThickness(0.08);
        tripLineConf.setDiameter(0.368);

        // Create the base substation
        final Substation substation = gldSim.substation("substation_root");
        substation.setBusType(BusType.SWING);
        substation.setNominalVoltage(7200.0);
        substation.setReferencePhase(PhaseCode.A);
        substation.setPhases(PhaseCode.ABCN);
        substation.setVoltageA(7200.0, 0.0);
        substation.setVoltageB(-3600.0, -6235.3829);
        substation.setVoltageC(-3600.0, 6235.3829);

        // Create the root meter
        final Meter rootMeter = gldSim.meter("F1_transformer_meter");
        rootMeter.setPhases(PhaseCode.ABCN);
        rootMeter.setNominalVoltage(7200.0);

        // Create the root transformer
        final Transformer root = gldSim.transformer("F1_Transformer1", substationConfig);
        root.setPhases(PhaseCode.ABCN);
        root.setGroupId("F1_Network_Trans");
        root.setFrom(substation);
        root.setTo(rootMeter);

        // Create the seperated phase meters for the house groups
        tripMeterA = gldSim.triplexMeter("F1_triplex_node_A");
        tripMeterA.setPhases(PhaseCode.AS);
        tripMeterA.setNominalVoltage(124.00);

        tripMeterB = gldSim.triplexMeter("F1_triplex_node_B");
        tripMeterB.setPhases(PhaseCode.BS);
        tripMeterB.setNominalVoltage(124.00);

        tripMeterC = gldSim.triplexMeter("F1_triplex_node_C");
        tripMeterC.setPhases(PhaseCode.CS);
        tripMeterC.setNominalVoltage(124.00);

        // Create the transformers to feed the house groups
        final Transformer centerTapTransformerA = gldSim.transformer("F1_center_tap_transformer_A", defaultTransformerA);
        centerTapTransformerA.setPhases(PhaseCode.AS);
        centerTapTransformerA.setTo(tripMeterA);
        centerTapTransformerA.setFrom(rootMeter);

        final Transformer centerTapTransformerB = gldSim.transformer("F1_center_tap_transformer_B", defaultTransformerB);
        centerTapTransformerB.setPhases(PhaseCode.BS);
        centerTapTransformerB.setTo(tripMeterB);
        centerTapTransformerB.setFrom(rootMeter);

        final Transformer centerTapTransformerC = gldSim.transformer("F1_center_tap_transformer_C", defaultTransformerC);
        centerTapTransformerC.setPhases(PhaseCode.CS);
        centerTapTransformerC.setTo(tripMeterC);
        centerTapTransformerC.setFrom(rootMeter);

        for (int id = 0; id < numHouses; id++) {
        	
            //private House createHouse(final GldSimulator sim, final int id, final AuctionObject auction, final String contPrefix) {
                // Select the phases for our meters
        	final int r = rand.nextInt(3);
            final PhaseCode phase;
            final TriplexMeter meter;
            switch (r) {
            	case 0:
            		phase = PhaseCode.A;
                    meter = tripMeterA;
                    break;
            	case 1:
                    phase = PhaseCode.B;
                    meter = tripMeterB;
                    break;
                case 2:
                    phase = PhaseCode.C;
                    meter = tripMeterC;
                    break;
                default:
                    throw new RuntimeException("Invalid random number");
            }

            GldSimulatorUtils.generateHouse(gldSim, id, meter, tripLineConf, auction, phase, false, rand, String.format("_C%d_", numHouses));
        }
    }
    
    private static final Random rand = new Random(13);
    
    private TriplexMeter tripMeterA;

    private TriplexMeter tripMeterB;

    private TriplexMeter tripMeterC;

    private TriplexLineConfiguration tripLineConf;
   
}
