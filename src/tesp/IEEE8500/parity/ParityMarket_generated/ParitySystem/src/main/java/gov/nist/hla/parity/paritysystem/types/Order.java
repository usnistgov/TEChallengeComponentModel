package gov.nist.hla.parity.paritysystem.types;

import java.util.Objects;

public class Order {
    private final FixedLengthString userName;
    
    private final FixedLengthString orderId;
    
    private final FixedLengthString instrumentName;

    private final long orderNumber;
    
    public Order(String userName, String orderId, String instrumentName, long orderNumber) {
        this.userName = new FixedLengthString(instrumentName, FixedLengthString.USER_NAME_LENGTH);
        this.orderId = new FixedLengthString(instrumentName, FixedLengthString.ORDER_ID_LENGTH);
        this.instrumentName = new FixedLengthString(instrumentName, FixedLengthString.INSTRUMENT_NAME_LENGTH);
        this.orderNumber = orderNumber;
    }
    
    public String getUserName() {
        return userName.getValue();
    }
    
    public String getOrderId() {
        return orderId.getValue();
    }
    
    public String getInstrumentName() {
        return instrumentName.getValue();
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
