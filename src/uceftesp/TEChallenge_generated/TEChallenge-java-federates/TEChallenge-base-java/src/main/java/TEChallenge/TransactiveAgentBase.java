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


public class TransactiveAgentBase extends SynchronizedFederate {

	private SubscribedInteractionFilter _subscribedInteractionFilter = new SubscribedInteractionFilter();
	
	// constructor
	public TransactiveAgentBase(FederateConfig config) throws Exception {
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
		// object pubsub
        
        	
        Tender.publish_price();
        Tender.publish_quantity();
        Tender.publish_tenderId();
        Tender.publish_timeReference();
        Tender.publish_type();
        Tender.publish(getLRC());
        
        	
        MarketStatus.publish_price();
        MarketStatus.publish_time();
        MarketStatus.publish_type();
        MarketStatus.publish(getLRC());
        
        	
        Quote.publish_price();
        Quote.publish_quantity();
        Quote.publish_quoteId();
        Quote.publish_timeReference();
        Quote.publish_type();
        Quote.publish(getLRC());
        
        	
        Transaction.publish_accept();
        Transaction.publish_tenderId();
        Transaction.publish(getLRC());
                
        	
        MarketStatus.subscribe_price();
        MarketStatus.subscribe_time();
        MarketStatus.subscribe_type();
        MarketStatus.subscribe(getLRC());
        
        	
        Quote.subscribe_price();
        Quote.subscribe_quantity();
        Quote.subscribe_quoteId();
        Quote.subscribe_timeReference();
        Quote.subscribe_type();
        Quote.subscribe(getLRC());
        
        	
        Transaction.subscribe_accept();
        Transaction.subscribe_tenderId();
        Transaction.subscribe(getLRC());
        
        	
        Tender.subscribe_price();
        Tender.subscribe_quantity();
        Tender.subscribe_tenderId();
        Tender.subscribe_timeReference();
        Tender.subscribe_type();
        Tender.subscribe(getLRC());
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