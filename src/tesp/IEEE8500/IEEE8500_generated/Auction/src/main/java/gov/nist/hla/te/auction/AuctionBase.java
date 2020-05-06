package gov.nist.hla.te.auction;

import gov.nist.hla.te.auction.rti.*;

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


public class AuctionBase extends SynchronizedFederate {
    private SubscribedInteractionFilter _subscribedInteractionFilter =
        new SubscribedInteractionFilter();

    // constructor
    public AuctionBase(FederateConfig config) throws Exception {
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
           SubscribedInteractionFilter.SourceFedFilter.SOURCE_FILTER_DISABLED);

        // object pubsub
        Meter.publish_bill_mode();
        Meter.publish_measured_voltage_1();
        Meter.publish_monthly_fee();
        Meter.publish_name();
        Meter.publish_price();
        Meter.publish(getLRC());
        House.publish_air_temperature();
        House.publish_cooling_setpoint();
        House.publish_heating_setpoint();
        House.publish_hvac_load();
        House.publish_name();
        House.publish_power_state();
        House.publish_thermostat_deadband();
        House.publish(getLRC());
        House.subscribe_air_temperature();
        House.subscribe_cooling_setpoint();
        House.subscribe_heating_setpoint();
        House.subscribe_hvac_load();
        House.subscribe_name();
        House.subscribe_power_state();
        House.subscribe_thermostat_deadband();
        House.subscribe(getLRC());
        Meter.subscribe_bill_mode();
        Meter.subscribe_measured_voltage_1();
        Meter.subscribe_monthly_fee();
        Meter.subscribe_name();
        Meter.subscribe_price();
        Meter.subscribe(getLRC());
        Substation.subscribe_distribution_load();
        Substation.subscribe_name();
        Substation.subscribe(getLRC());
    }


    @Override
    public void receiveInteraction(int interactionClass,
                                   ReceivedInteraction theInteraction,
                                   byte[] userSuppliedTag) {
        InteractionRoot interactionRoot =
            InteractionRoot.create_interaction(interactionClass,
                                               theInteraction);
        if (interactionRoot instanceof C2WInteractionRoot) {
            C2WInteractionRoot c2wInteractionRoot =
                (C2WInteractionRoot)interactionRoot;

            // Filter interaction if src/origin fed requirements (if any)
            // are not met
            if (_subscribedInteractionFilter.filterC2WInteraction
                (getFederateId(), c2wInteractionRoot)) {
                return;
            }
        }
        super.receiveInteraction(interactionClass, theInteraction,
                                 userSuppliedTag);
    }

    @Override
    public void receiveInteraction(int interactionClass,
                                   ReceivedInteraction theInteraction,
                                   byte[] userSuppliedTag,
                                   LogicalTime theTime,
                                   EventRetractionHandle retractionHandle) {
        InteractionRoot interactionRoot =
            InteractionRoot.create_interaction(interactionClass,
                                               theInteraction, theTime);
        if (interactionRoot instanceof C2WInteractionRoot) {
            C2WInteractionRoot c2wInteractionRoot =
                (C2WInteractionRoot)interactionRoot;

            // Filter interaction if src/origin fed requirements (if any)
            // are not met
            if (_subscribedInteractionFilter.filterC2WInteraction
                (getFederateId(), c2wInteractionRoot)) {
                return;
            }
        }
        super.receiveInteraction(interactionClass, theInteraction,
                                 userSuppliedTag, theTime, retractionHandle);
    }
}
