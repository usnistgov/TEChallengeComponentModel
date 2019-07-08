package LocalController;

import hla.rti.EventRetractionHandle;
import hla.rti.LogicalTime;
import hla.rti.ReceivedInteraction;

import org.cpswt.hla.C2WInteractionRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.SubscribedInteractionFilter;
import org.cpswt.hla.SynchronizedFederate;

import org.cpswt.config.FederateConfig;
import org.cpswt.utils.CpswtDefaults;

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
        
        	
        ResourceControl.publish_Resources();
        ResourceControl.publish_activePowerCurve();
        ResourceControl.publish_actualDemand();
        ResourceControl.publish_adjustedFullDRPower();
        ResourceControl.publish_adjustedNoDRPower();
        ResourceControl.publish_downBeginRamp();
        ResourceControl.publish_downDuration();
        ResourceControl.publish_downRampToCompletion();
        ResourceControl.publish_downRate();
        ResourceControl.publish_loadStatusType();
        ResourceControl.publish_locked();
        ResourceControl.publish_maximumReactivePower();
        ResourceControl.publish_maximumRealPower();
        ResourceControl.publish_reactiveDesiredFractionOfFullRatedOutputBegin();
        ResourceControl.publish_reactiveDesiredFractionOfFullRatedOutputEnd();
        ResourceControl.publish_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin();
        ResourceControl.publish_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd();
        ResourceControl.publish_realDesiredFractionOfFullRatedOutputBegin();
        ResourceControl.publish_realDesiredFractionOfFullRatedOutputEnd();
        ResourceControl.publish_realRequiredFractionOfFullRatedInputPowerDrawnBegin();
        ResourceControl.publish_realRequiredFractionOfFullRatedInputPowerDrawnEnd();
        ResourceControl.publish_upBeginRamp();
        ResourceControl.publish_upDuration();
        ResourceControl.publish_upRampToCompletion();
        ResourceControl.publish_upRate();
        ResourceControl.publish(getLRC());
                
        	
        ResourcesPhysicalStatus.subscribe_current_Imaginary_A();
        ResourcesPhysicalStatus.subscribe_current_Imaginary_B();
        ResourcesPhysicalStatus.subscribe_current_Imaginary_C();
        ResourcesPhysicalStatus.subscribe_current_Real_A();
        ResourcesPhysicalStatus.subscribe_current_Real_B();
        ResourcesPhysicalStatus.subscribe_current_Real_C();
        ResourcesPhysicalStatus.subscribe_gridNodeId();
        ResourcesPhysicalStatus.subscribe_impedance_Imaginary_A();
        ResourcesPhysicalStatus.subscribe_impedance_Imaginary_B();
        ResourcesPhysicalStatus.subscribe_impedance_Imaginary_C();
        ResourcesPhysicalStatus.subscribe_impedance_Real_A();
        ResourcesPhysicalStatus.subscribe_impedance_Real_B();
        ResourcesPhysicalStatus.subscribe_impedance_Real_C();
        ResourcesPhysicalStatus.subscribe_loadInstanceName();
        ResourcesPhysicalStatus.subscribe_name();
        ResourcesPhysicalStatus.subscribe_phases();
        ResourcesPhysicalStatus.subscribe_power_Imaginary_A();
        ResourcesPhysicalStatus.subscribe_power_Imaginary_B();
        ResourcesPhysicalStatus.subscribe_power_Imaginary_C();
        ResourcesPhysicalStatus.subscribe_power_Real_A();
        ResourcesPhysicalStatus.subscribe_power_Real_B();
        ResourcesPhysicalStatus.subscribe_power_Real_C();
        ResourcesPhysicalStatus.subscribe_status();
        ResourcesPhysicalStatus.subscribe_type();
        ResourcesPhysicalStatus.subscribe_voltage_Imaginary_A();
        ResourcesPhysicalStatus.subscribe_voltage_Imaginary_B();
        ResourcesPhysicalStatus.subscribe_voltage_Imaginary_C();
        ResourcesPhysicalStatus.subscribe_voltage_Real_A();
        ResourcesPhysicalStatus.subscribe_voltage_Real_B();
        ResourcesPhysicalStatus.subscribe_voltage_Real_C();
        ResourcesPhysicalStatus.subscribe(getLRC());
        
        	
        SupervisoryControlSignal.subscribe_localControllerName();
        SupervisoryControlSignal.subscribe_modulationSignal();
        SupervisoryControlSignal.subscribe(getLRC());
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
