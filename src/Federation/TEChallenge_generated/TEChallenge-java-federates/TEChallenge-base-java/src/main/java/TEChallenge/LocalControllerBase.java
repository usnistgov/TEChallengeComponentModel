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

public class LocalControllerBase extends SynchronizedFederate {

	private SubscribedInteractionFilter _subscribedInteractionFilter = new SubscribedInteractionFilter();
	
	// constructor
	public LocalControllerBase(FederateConfig config) throws Exception {
		super(config);

		super.createLRC();
		super.joinFederation();

		enableTimeConstrained();

		enableTimeRegulation(getLookAhead());
		enableAsynchronousDelivery();
        // interaction pubsub
        
        		
		// object pubsub
        
        	
        resourceControl.publish_Resources();
        resourceControl.publish_activePowerCurve();
        resourceControl.publish_actualDemand();
        resourceControl.publish_adjustedFullDRPower();
        resourceControl.publish_adjustedNoDRPower();
        resourceControl.publish_downBeginRamp();
        resourceControl.publish_downDuration();
        resourceControl.publish_downRampToCompletion();
        resourceControl.publish_downRate();
        resourceControl.publish_loadStatusType();
        resourceControl.publish_locked();
        resourceControl.publish_maximumReactivePower();
        resourceControl.publish_maximumRealPower();
        resourceControl.publish_reactiveDesiredFractionOfFullRatedOutputBegin();
        resourceControl.publish_reactiveDesiredFractionOfFullRatedOutputEnd();
        resourceControl.publish_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin();
        resourceControl.publish_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd();
        resourceControl.publish_realDesiredFractionOfFullRatedOutputBegin();
        resourceControl.publish_realDesiredFractionOfFullRatedOutputEnd();
        resourceControl.publish_realRequiredFractionOfFullRatedInputPowerDrawnBegin();
        resourceControl.publish_realRequiredFractionOfFullRatedInputPowerDrawnEnd();
        resourceControl.publish_upBeginRamp();
        resourceControl.publish_upDuration();
        resourceControl.publish_upRampToCompletion();
        resourceControl.publish_upRate();
        resourceControl.publish(getLRC());
                
        	
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
        resourcesPhysicalStatus.subscribe_name();
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
        
        	
        supervisoryControlSignal.subscribe_localControllerName();
        supervisoryControlSignal.subscribe_modulationSignal();
        supervisoryControlSignal.subscribe(getLRC());
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
