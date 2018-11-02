package TEChallenge;

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


public class GeneratorsBase extends SynchronizedFederate {

	private SubscribedInteractionFilter _subscribedInteractionFilter = new SubscribedInteractionFilter();
	
	// constructor
	public GeneratorsBase(FederateConfig config) throws Exception {
		super(config);

		super.createLRC();
		super.joinFederation();

		enableTimeConstrained();

		enableTimeRegulation(getLookAhead());
		enableAsynchronousDelivery();
        // interaction pubsub
        
        		
		// object pubsub
        
        	
        ResourcesPhysicalStatus.publish_current_Imaginary_A();
        ResourcesPhysicalStatus.publish_current_Imaginary_B();
        ResourcesPhysicalStatus.publish_current_Imaginary_C();
        ResourcesPhysicalStatus.publish_current_Real_A();
        ResourcesPhysicalStatus.publish_current_Real_B();
        ResourcesPhysicalStatus.publish_current_Real_C();
        ResourcesPhysicalStatus.publish_gridNodeId();
        ResourcesPhysicalStatus.publish_impedance_Imaginary_A();
        ResourcesPhysicalStatus.publish_impedance_Imaginary_B();
        ResourcesPhysicalStatus.publish_impedance_Imaginary_C();
        ResourcesPhysicalStatus.publish_impedance_Real_A();
        ResourcesPhysicalStatus.publish_impedance_Real_B();
        ResourcesPhysicalStatus.publish_impedance_Real_C();
        ResourcesPhysicalStatus.publish_loadInstanceName();
        ResourcesPhysicalStatus.publish_name();
        ResourcesPhysicalStatus.publish_phases();
        ResourcesPhysicalStatus.publish_power_Imaginary_A();
        ResourcesPhysicalStatus.publish_power_Imaginary_B();
        ResourcesPhysicalStatus.publish_power_Imaginary_C();
        ResourcesPhysicalStatus.publish_power_Real_A();
        ResourcesPhysicalStatus.publish_power_Real_B();
        ResourcesPhysicalStatus.publish_power_Real_C();
        ResourcesPhysicalStatus.publish_status();
        ResourcesPhysicalStatus.publish_type();
        ResourcesPhysicalStatus.publish_voltage_Imaginary_A();
        ResourcesPhysicalStatus.publish_voltage_Imaginary_B();
        ResourcesPhysicalStatus.publish_voltage_Imaginary_C();
        ResourcesPhysicalStatus.publish_voltage_Real_A();
        ResourcesPhysicalStatus.publish_voltage_Real_B();
        ResourcesPhysicalStatus.publish_voltage_Real_C();
        ResourcesPhysicalStatus.publish(getLRC());
                
        	
        ResourceControl.subscribe_Resources();
        ResourceControl.subscribe_activePowerCurve();
        ResourceControl.subscribe_actualDemand();
        ResourceControl.subscribe_adjustedFullDRPower();
        ResourceControl.subscribe_adjustedNoDRPower();
        ResourceControl.subscribe_downBeginRamp();
        ResourceControl.subscribe_downDuration();
        ResourceControl.subscribe_downRampToCompletion();
        ResourceControl.subscribe_downRate();
        ResourceControl.subscribe_loadStatusType();
        ResourceControl.subscribe_locked();
        ResourceControl.subscribe_maximumReactivePower();
        ResourceControl.subscribe_maximumRealPower();
        ResourceControl.subscribe_reactiveDesiredFractionOfFullRatedOutputBegin();
        ResourceControl.subscribe_reactiveDesiredFractionOfFullRatedOutputEnd();
        ResourceControl.subscribe_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin();
        ResourceControl.subscribe_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd();
        ResourceControl.subscribe_realDesiredFractionOfFullRatedOutputBegin();
        ResourceControl.subscribe_realDesiredFractionOfFullRatedOutputEnd();
        ResourceControl.subscribe_realRequiredFractionOfFullRatedInputPowerDrawnBegin();
        ResourceControl.subscribe_realRequiredFractionOfFullRatedInputPowerDrawnEnd();
        ResourceControl.subscribe_upBeginRamp();
        ResourceControl.subscribe_upDuration();
        ResourceControl.subscribe_upRampToCompletion();
        ResourceControl.subscribe_upRate();
        ResourceControl.subscribe(getLRC());
        
        	
        GridVoltageState.subscribe_grid_Voltage_Imaginary_A();
        GridVoltageState.subscribe_grid_Voltage_Imaginary_B();
        GridVoltageState.subscribe_grid_Voltage_Imaginary_C();
        GridVoltageState.subscribe_grid_Voltage_Real_A();
        GridVoltageState.subscribe_grid_Voltage_Real_B();
        GridVoltageState.subscribe_grid_Voltage_Real_C();
        GridVoltageState.subscribe(getLRC());
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
