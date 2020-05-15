package gov.nist.hla.parity.paritysystem;

import java.util.Objects;

public class Order {
    private final String userName;
    
    private final String orderId;
    
    private final String instrumentName;

    private final long orderNumber;
    
    public Order(String userName, String orderId, String instrumentName, long orderNumber) {
        this.userName = DataConversion.parseUserName(userName);
        this.orderId = DataConversion.parseOrderId(orderId);
        this.instrumentName = DataConversion.parseInstrumentName(instrumentName);
        this.orderNumber = orderNumber;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public String getInstrumentName() {
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
