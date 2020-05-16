package gov.nist.hla.parity.paritysystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.paritytrading.parity.match.OrderBook;
import com.paritytrading.parity.match.OrderBookListener;
import com.paritytrading.parity.match.Side;

import gov.nist.hla.parity.paritysystem.exception.DuplicateIdentifier;
import gov.nist.hla.parity.paritysystem.rti.CancelOrder;
import gov.nist.hla.parity.paritysystem.rti.EnterOrder;
import gov.nist.hla.parity.paritysystem.rti.OrderRejected;
import gov.nist.hla.parity.paritysystem.types.Instrument;
import gov.nist.hla.parity.paritysystem.types.InstrumentName;
import gov.nist.hla.parity.paritysystem.types.MessageType;
import gov.nist.hla.parity.paritysystem.types.OrderId;
import gov.nist.hla.parity.paritysystem.types.Party;
import gov.nist.hla.parity.paritysystem.types.UserName;

public class OrderBooks implements OrderBookListener {
    private final static Logger log = LogManager.getLogger();
    
    private final static char SIDE_BUY = 'B';
    private final static char SIDE_SELL = 'S';
    
    private final static char ADDED_LIQUIDITY = 'A';
    private final static char REMOVED_LIQUIDITY = 'R';
    
    private final static char CANCEL_REASON_REQUEST = 'R';
    private final static char CANCEL_REASON_SUPERVISORY = 'S';
    
    private final static char REJECTED_REASON_INSTRUMENT = 'I';
    private final static char REJECTED_REASON_PRICE = 'P';
    private final static char REJECTED_REASON_QUANTITY = 'Q';

    private final ParitySystem federate;
    
    private Map<InstrumentName, Instrument> instruments = new HashMap<InstrumentName, Instrument>();

    private Map<InstrumentName, OrderBook> books = new HashMap<InstrumentName, OrderBook>();
    
    private Map<UserName, Party> clients = new HashMap<UserName, Party>();

    public OrderBooks(List<Instrument> instruments, ParitySystem federate) {
        for (Instrument instrument : instruments) {
            final InstrumentName instrumentName = instrument.getInstrumentName();
            
            if (this.instruments.put(instrumentName, instrument) != null) {
                log.fatal("multiple instruments with the name {}", instrumentName);
                throw new DuplicateIdentifier(instrumentName.getValue());
            }
            log.info("new instrument {}", instrument.toString());
            
            books.put(instrumentName, new OrderBook(this));
            log.debug("created order book for {}", instrumentName);
        }
        
        // this reference is unfortunately necessary to create the HLA messages
        // it seems like the create message methods should be static...
        this.federate = federate;
    }
    
    void enterOrder(EnterOrder message) {
        if (message.get_messageType() != MessageType.ENTER.asChar()) {
            log.error("wrong message type for enter order: {}", message.get_messageType());
            return; // parity defines no mechanism to report this to the client
        }
        
        if (message.get_orderId() == null || message.get_orderId().isEmpty()) {
            log.error("bad order id for enter order: {}", message.get_orderId());
            return; // parity defines no mechanism to report this to the client
        }
        
        if (message.get_side() != SIDE_BUY && message.get_side() != SIDE_SELL) {
            log.error("bad side for enter order: {}", message.get_side());
            return; // parity defines no mechanism to report this to the client
        }
        
        if (message.get_userName() == null || message.get_userName().isEmpty()) {
            log.error("bad user name for enter order: {}", message.get_userName());
            return; // parity defines no mechanism to report this to the client
        }
        UserName userName = new UserName(message.get_userName());

        Party client = clients.get(userName);
        if (client == null) {
            log.info("discovered new party: {}", userName);
            clients.put(userName, new Party(userName));
        }
        
        OrderId orderId = new OrderId(message.get_orderId());
        if (client.hasOrder(orderId)) {
            log.warn("duplicate order {} from client {}", orderId, userName);
            return;
        }
        
        InstrumentName instrumentName = new InstrumentName(message.get_instrument());
        OrderBook book = books.get(instrumentName);
        if (book == null ) {
            log.warn("unknown instrument {} from client {}", instrumentName, userName);
            reject(orderId, REJECTED_REASON_INSTRUMENT);
            return;
        }
        
        if (message.get_price() < 0) { // parity allows 0 price
            log.warn("bad price {} from client {}", message.get_price(), userName);
            reject(orderId, REJECTED_REASON_PRICE);
            return;
        }
        
        if (message.get_quantity() <= 0 ) {
            log.warn("bad quantity {} from client {}", message.get_quantity(), userName);
            reject(orderId, REJECTED_REASON_QUANTITY);
            return;
        }
        
        // generate new order number
        // send accepted to client
        // send entered to reporting
        // enter order into book (callbacks)
    }
    
    void cancelOrder(CancelOrder message) {
        // check username not empty
        // check order exists
        
        // maybe client should store the Order instead of just the number ?
    }
    
    @Override
    public void match(long restingOrderId, long incomingOrderId, Side incomingSide, long price, long executedQuantity, long remainingQuantity) {
        // TODO Auto-generated method stub
    }

    @Override
    public void add(long orderId, Side side, long price, long size) {
        // TODO Auto-generated method stub
    }

    @Override
    public void cancel(long orderId, long canceledQuantity, long remainingQuantity) {
        // TODO Auto-generated method stub
    }
    
    private long timestamp() {
        // timestamps are supposed to be represented as nanoseconds since the midnight of the first day of trading
        // this should be based on logical time, but use SimTime to generate the correct value
        return (long) federate.getCurrentTime();
    }
    
    private void reject(OrderId orderId, char reasonCode) {
        OrderRejected orderRejected = federate.create_OrderRejected();
        orderRejected.set_messageType(MessageType.REJECTED.asChar());
        orderRejected.set_timeStamp(timestamp());
        orderRejected.set_orderId(orderId.getValue());
        orderRejected.set_reason(reasonCode);
        orderRejected.sendInteraction(federate.getLRC()); // TSO or RO?
    }
}
