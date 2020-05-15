package gov.nist.hla.parity.paritysystem.types;

import java.util.Objects;

public class Order {
    private final UserName userName;
    
    private final OrderId orderId;
    
    private final InstrumentName instrumentName;

    private final long orderNumber;
    
    public Order(UserName userName, OrderId orderId, InstrumentName instrumentName, long orderNumber) {
        this.userName = userName;
        this.orderId = orderId;
        this.instrumentName = instrumentName;
        this.orderNumber = orderNumber;
    }
    
    public UserName getUserName() {
        return userName;
    }
    
    public OrderId getOrderId() {
        return orderId;
    }
    
    public InstrumentName getInstrumentName() {
        return instrumentName;
    }
    
    public long getOrderNumber() {
        return orderNumber;
    }
    
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        
        if (!(object instanceof Order)) {
            return false;
        }
        
        Order order = (Order) object;
        return orderNumber == order.orderNumber;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(orderNumber);
    }
    
    @Override
    public String toString() {
        return "(" + userName + "," + orderId + "," + instrumentName + "," + orderNumber + ")";
    }
}
