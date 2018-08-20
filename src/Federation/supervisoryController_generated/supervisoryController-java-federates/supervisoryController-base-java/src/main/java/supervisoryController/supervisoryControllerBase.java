package supervisoryController;

import hla.rti.EventRetractionHandle;
import hla.rti.LogicalTime;
import hla.rti.ReceivedInteraction;

import org.cpswt.hla.C2WInteractionRoot;
import org.cpswt.hla.InteractionRoot;
import org.cpswt.hla.SubscribedInteractionFilter;
import org.cpswt.hla.SynchronizedFederate;

import org.cpswt.config.FederateConfig;

import org.cpswt.*;

public class supervisoryControllerBase extends SynchronizedFederate {

	private SubscribedInteractionFilter _subscribedInteractionFilter = new SubscribedInteractionFilter();
	
	// constructor
	public supervisoryControllerBase(FederateConfig config) throws Exception {
		super(config);

		super.createLRC();
		super.joinFederation();

		enableTimeConstrained();

		enableTimeRegulation(getLookAhead());
		enableAsynchronousDelivery();
        // interaction pubsub
        
        		
		// object pubsub
        
        	
        supervisoryControlSignal.publish_localControllerName();
        supervisoryControlSignal.publish_modulationSignal();
        supervisoryControlSignal.publish(getLRC());
        
        	
        Tender.publish_price();
        Tender.publish_quantity();
        Tender.publish_tenderId();
        Tender.publish_timeReference();
        Tender.publish_type();
        Tender.publish(getLRC());
                
        	
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
        
        	
        Quote.subscribe_price();
        Quote.subscribe_quantity();
        Quote.subscribe_quoteId();
        Quote.subscribe_timeReference();
        Quote.subscribe_type();
        Quote.subscribe(getLRC());
        
        	
        Transaction.subscribe_accept();
        Transaction.subscribe_tenderId();
        Transaction.subscribe(getLRC());
        
        	
        marketStatus.subscribe_price();
        marketStatus.subscribe_time();
        marketStatus.subscribe_type();
        marketStatus.subscribe(getLRC());
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
