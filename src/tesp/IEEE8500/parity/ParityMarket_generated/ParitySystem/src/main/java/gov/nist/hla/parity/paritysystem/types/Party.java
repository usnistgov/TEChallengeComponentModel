package gov.nist.hla.parity.paritysystem.types;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gov.nist.hla.parity.paritysystem.exception.DuplicateIdentifier;

public class Party {
    private final static Logger log = LogManager.getLogger();

    private UserName userName;

    // this never shrinks in size; does it make sense to have a removeOrder equivalent?
    private Map<OrderId, Integer> orders = new HashMap<OrderId, Integer>(); 
    
    public Party(UserName userName) {
        this.userName = userName;
    }
    
    public void addOrder(OrderId orderId, int orderNumber) {
        if (hasOrder(orderId)) {
            if (getOrderNumber(orderId) != orderNumber) {
                log.error("received multiple orders with id {} from {}", orderId, userName);
                throw new DuplicateIdentifier(orderId.getValue());
            }
            log.debug("ignored existing order from {}: {}", userName, orderId);
            return;
        }
        orders.put(orderId, orderNumber);
        log.debug("recorded order {} from {} as order number {}", orderId, userName, orderNumber);
    }
    
    public UserName getUserName() {
        return userName;
    }
    
    public boolean hasOrder(OrderId orderId) {
        return orders.containsKey(orderId);
    }
    
    // can return null
    public Integer getOrderNumber(OrderId orderId) {
        return orders.get(orderId);
    }
}
