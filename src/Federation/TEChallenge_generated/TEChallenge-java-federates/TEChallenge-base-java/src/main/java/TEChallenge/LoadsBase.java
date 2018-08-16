package TEChallenge;

import hla.rti.EventRetractionHandle;
import hla.rti.LogicalTime;
import hla.rti.ReceivedInteraction;

import org.cpswt.hla.C2WInteractionRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.SubscribedInteractionFilter;
import org.cpswt.hla.SynchronizedFederate;

import org.cpswt.config.FederateConfig;

import org.cpswt.*;

public class LoadsBase extends SynchronizedFederate {

	private SubscribedInteractionFilter _subscribedInteractionFilter = new SubscribedInteractionFilter();
	
	// constructor
	public LoadsBase(FederateConfig config) throws Exception {
		super(config);

		super.createLRC();
		super.joinFederation();

		enableTimeConstrained();

		enableTimeRegulation(getLookAhead());
		enableAsynchronousDelivery();
        // interaction pubsub
        
        		
		// object pubsub
        
        	
        resourcesPhysicalStatus.publish_current_Imaginary_A();
        resourcesPhysicalStatus.publish_current_Imaginary_B();
        resourcesPhysicalStatus.publish_current_Imaginary_C();
        resourcesPhysicalStatus.publish_current_Real_A();
        resourcesPhysicalStatus.publish_current_Real_B();
        resourcesPhysicalStatus.publish_current_Real_C();
        resourcesPhysicalStatus.publish_gldName();
        resourcesPhysicalStatus.publish_gridNodeId();
        resourcesPhysicalStatus.publish_impedance_Imaginary_A();
        resourcesPhysicalStatus.publish_impedance_Imaginary_B();
        resourcesPhysicalStatus.publish_impedance_Imaginary_C();
        resourcesPhysicalStatus.publish_impedance_Real_A();
        resourcesPhysicalStatus.publish_impedance_Real_B();
        resourcesPhysicalStatus.publish_impedance_Real_C();
        resourcesPhysicalStatus.publish_loadInstanceName();
        resourcesPhysicalStatus.publish_phases();
        resourcesPhysicalStatus.publish_power_Imaginary_A();
        resourcesPhysicalStatus.publish_power_Imaginary_B();
        resourcesPhysicalStatus.publish_power_Imaginary_C();
        resourcesPhysicalStatus.publish_power_Real_A();
        resourcesPhysicalStatus.publish_power_Real_B();
        resourcesPhysicalStatus.publish_power_Real_C();
        resourcesPhysicalStatus.publish_status();
        resourcesPhysicalStatus.publish_type();
        resourcesPhysicalStatus.publish_voltage_Imaginary_A();
        resourcesPhysicalStatus.publish_voltage_Imaginary_B();
        resourcesPhysicalStatus.publish_voltage_Imaginary_C();
        resourcesPhysicalStatus.publish_voltage_Real_A();
        resourcesPhysicalStatus.publish_voltage_Real_B();
        resourcesPhysicalStatus.publish_voltage_Real_C();
        resourcesPhysicalStatus.publish(getLRC());
                
        	
        gridVoltageState.subscribe_grid_Voltage_Imaginary_A();
        gridVoltageState.subscribe_grid_Voltage_Imaginary_B();
        gridVoltageState.subscribe_grid_Voltage_Imaginary_C();
        gridVoltageState.subscribe_grid_Voltage_Real_A();
        gridVoltageState.subscribe_grid_Voltage_Real_B();
        gridVoltageState.subscribe_grid_Voltage_Real_C();
        gridVoltageState.subscribe(getLRC());
        
        	
        resourceControl.subscribe_Resources();
        resourceControl.subscribe_activePowerCurve();
        resourceControl.subscribe_actualDemand();
        resourceControl.subscribe_adjustedFullDRPower();
        resourceControl.subscribe_adjustedNoDRPower();
        resourceControl.subscribe_downBeginRamp();
        resourceControl.subscribe_downDuration();
        resourceControl.subscribe_downRampToCompletion();
        resourceControl.subscribe_downRate();
        resourceControl.subscribe_loadStatusType();
        resourceControl.subscribe_locked();
        resourceControl.subscribe_maximumReactivePower();
        resourceControl.subscribe_maximumRealPower();
        resourceControl.subscribe_reactiveDesiredFractionOfFullRatedOutputBegin();
        resourceControl.subscribe_reactiveDesiredFractionOfFullRatedOutputEnd();
        resourceControl.subscribe_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin();
        resourceControl.subscribe_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd();
        resourceControl.subscribe_realDesiredFractionOfFullRatedOutputBegin();
        resourceControl.subscribe_realDesiredFractionOfFullRatedOutputEnd();
        resourceControl.subscribe_realRequiredFractionOfFullRatedInputPowerDrawnBegin();
        resourceControl.subscribe_realRequiredFractionOfFullRatedInputPowerDrawnEnd();
        resourceControl.subscribe_upBeginRamp();
        resourceControl.subscribe_upDuration();
        resourceControl.subscribe_upRampToCompletion();
        resourceControl.subscribe_upRate();
        resourceControl.subscribe(getLRC());
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
}
