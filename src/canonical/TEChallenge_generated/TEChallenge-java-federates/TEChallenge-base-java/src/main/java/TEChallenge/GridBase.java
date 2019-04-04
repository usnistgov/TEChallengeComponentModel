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


public class GridBase extends SynchronizedFederate {

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
        
        
        SimTime.subscribe(getLRC());
        _subscribedInteractionFilter.setFedFilters( 
			SimTime.get_handle(), 
			SubscribedInteractionFilter.OriginFedFilter.ORIGIN_FILTER_DISABLED, 
			SubscribedInteractionFilter.SourceFedFilter.SOURCE_FILTER_DISABLED 
		);
        TMYWeather.subscribe(getLRC());
        _subscribedInteractionFilter.setFedFilters( 
			TMYWeather.get_handle(), 
			SubscribedInteractionFilter.OriginFedFilter.ORIGIN_FILTER_DISABLED, 
			SubscribedInteractionFilter.SourceFedFilter.SOURCE_FILTER_DISABLED 
		);		
		// object pubsub
        
        	
        GridVoltageState.publish_grid_Voltage_Imaginary_A();
        GridVoltageState.publish_grid_Voltage_Imaginary_B();
        GridVoltageState.publish_grid_Voltage_Imaginary_C();
        GridVoltageState.publish_grid_Voltage_Real_A();
        GridVoltageState.publish_grid_Voltage_Real_B();
        GridVoltageState.publish_grid_Voltage_Real_C();
        GridVoltageState.publish(getLRC());
                
        	
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
