package Grid;

import hla.rti.EventRetractionHandle;
import hla.rti.LogicalTime;
import hla.rti.ReceivedInteraction;
import hla.rti.ResignAction;

import org.cpswt.hla.C2WInteractionRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.SubscribedInteractionFilter;
import org.cpswt.hla.SynchronizedFederate;
import org.cpswt.hla.SimEnd;

import org.cpswt.config.FederateConfig;
import org.cpswt.utils.CpswtDefaults;

import org.cpswt.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class GridBase extends SynchronizedFederate {

	private static final Logger logger = LogManager.getLogger(GridBase.class);
	boolean exitCondition = false;	// set to true when SimEnd is received

	private SubscribedInteractionFilter _subscribedInteractionFilter = new SubscribedInteractionFilter();
	
	// constructor
	public GridBase(FederateConfig config) throws Exception {
		super(config);

		super.createLRC();
		super.joinFederation();

		enableTimeConstrained();

		enableTimeRegulation(getLookAhead());
		enableAsynchronousDelivery();
        // interaction pubsub
        
        		
		// object pubsub
        
        	
        gridVoltageState.publish_grid_Voltage_Imaginary_A();
        gridVoltageState.publish_grid_Voltage_Imaginary_B();
        gridVoltageState.publish_grid_Voltage_Imaginary_C();
        gridVoltageState.publish_grid_Voltage_Real_A();
        gridVoltageState.publish_grid_Voltage_Real_B();
        gridVoltageState.publish_grid_Voltage_Real_C();
        gridVoltageState.publish(getLRC());
                
        	
        resourcesPhysicalStatus.subscribe_current_Imaginary_A();
        resourcesPhysicalStatus.subscribe_current_Imaginary_B();
        resourcesPhysicalStatus.subscribe_current_Imaginary_C();
        resourcesPhysicalStatus.subscribe_current_Real_A();
        resourcesPhysicalStatus.subscribe_current_Real_B();
        resourcesPhysicalStatus.subscribe_current_Real_C();
        resourcesPhysicalStatus.subscribe_gridNodeId();
        resourcesPhysicalStatus.subscribe_impedance_Imaginary_A();
        resourcesPhysicalStatus.subscribe_impedance_Imaginary_B();
        resourcesPhysicalStatus.subscribe_impedance_Imaginary_C();
        resourcesPhysicalStatus.subscribe_impedance_Real_A();
        resourcesPhysicalStatus.subscribe_impedance_Real_B();
        resourcesPhysicalStatus.subscribe_impedance_Real_C();
        resourcesPhysicalStatus.subscribe_loadInstanceName();
        resourcesPhysicalStatus.subscribe_phases();
        resourcesPhysicalStatus.subscribe_power_Imaginary_A();
        resourcesPhysicalStatus.subscribe_power_Imaginary_B();
        resourcesPhysicalStatus.subscribe_power_Imaginary_C();
        resourcesPhysicalStatus.subscribe_power_Real_A();
        resourcesPhysicalStatus.subscribe_power_Real_B();
        resourcesPhysicalStatus.subscribe_power_Real_C();
        resourcesPhysicalStatus.subscribe_status();
        resourcesPhysicalStatus.subscribe_type();
        resourcesPhysicalStatus.subscribe_voltage_Imaginary_A();
        resourcesPhysicalStatus.subscribe_voltage_Imaginary_B();
        resourcesPhysicalStatus.subscribe_voltage_Imaginary_C();
        resourcesPhysicalStatus.subscribe_voltage_Real_A();
        resourcesPhysicalStatus.subscribe_voltage_Real_B();
        resourcesPhysicalStatus.subscribe_voltage_Real_C();
        resourcesPhysicalStatus.subscribe(getLRC());
        	}
        
	
	@Override
	public void receiveInteraction(
	 int interactionClass, ReceivedInteraction theInteraction, byte[] userSuppliedTag
	) {
		InteractionRoot interactionRoot = InteractionRoot.create_interaction( interactionClass, theInteraction );
		if ( interactionRoot instanceof C2WInteractionRoot ) {
			
			C2WInteractionRoot c2wInteractionRoot = (C2WInteractionRoot)interactionRoot;

	        // Filter interaction if src/origin fed requirements (if any) are not met
	        if (  _subscribedInteractionFilter.filterC2WInteraction( getFederateId(), c2wInteractionRoot )  ) {
	        	return;
	        } 
		}
		
		super.receiveInteraction( interactionClass, theInteraction, userSuppliedTag );			
	}

	@Override
	public void receiveInteraction(
	 int interactionClass,
	 ReceivedInteraction theInteraction,
	 byte[] userSuppliedTag,
	 LogicalTime theTime,
	 EventRetractionHandle retractionHandle
	) {
		InteractionRoot interactionRoot = InteractionRoot.create_interaction( interactionClass, theInteraction, theTime );
		if ( interactionRoot instanceof C2WInteractionRoot ) {

			C2WInteractionRoot c2wInteractionRoot = (C2WInteractionRoot)interactionRoot;

	        // Filter interaction if src/origin fed requirements (if any) are not met
	        if (  _subscribedInteractionFilter.filterC2WInteraction( getFederateId(), c2wInteractionRoot )  ) {
	        	return;
	        } 
		}

		super.receiveInteraction( interactionClass, theInteraction, userSuppliedTag, theTime, retractionHandle );			
	}

    /**
     * Handles a simEnd interaction. Overrides the SynchronizedFederate default behavior which is to abort 
     *
     * @return void
     */
	@Override
    protected void handleIfSimEnd(int interactionClass, ReceivedInteraction theInteraction, LogicalTime theTime) {
        if (SimEnd.match(interactionClass)) {
            logger.info("{}: SimEnd interaction received, exiting...", getFederateId());
            
            // this one will set flag allowing foreground federate to gracefully shut down
            exitCondition = true;
        }
    }
    
	/**
	 * Processes graceful shut-down of hla federate
	 *
	 * @return void
	 * @throws hla.rti.ConcurrentAccessAttempted 
	 */    
	public void exitGracefully() throws hla.rti.ConcurrentAccessAttempted
	{

		// notify FederationManager about resign
		super.notifyFederationOfResign();

		// Wait for 10 seconds for Federation Manager to recognize that the federate has resigned.
		try {
			Thread.sleep(CpswtDefaults.SimEndWaitingTimeMillis);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
