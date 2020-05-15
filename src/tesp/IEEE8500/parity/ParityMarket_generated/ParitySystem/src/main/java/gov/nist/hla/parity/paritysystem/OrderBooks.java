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
import gov.nist.hla.parity.paritysystem.types.Instrument;
import gov.nist.hla.parity.paritysystem.types.InstrumentName;
import gov.nist.hla.parity.paritysystem.types.Party;
import gov.nist.hla.parity.paritysystem.types.UserName;

public class OrderBooks implements OrderBookListener {
    private final static Logger log = LogManager.getLogger();

    private Map<InstrumentName, Instrument> instruments = new HashMap<InstrumentName, Instrument>();

    private Map<InstrumentName, OrderBook> books = new HashMap<InstrumentName, OrderBook>();
    
    private Map<UserName, Party> clients = new HashMap<UserName, Party>();

    public OrderBooks(List<Instrument> instruments) {
        for (Instrument instrument : instruments) {
            final InstrumentName instrumentName = instrument.getInstrumentName();
            
            if (this.instruments.put(instrumentName, instrument) != null) {
                log.error("multiple instruments with the name {}", instrumentName);
                throw new DuplicateIdentifier(instrumentName.getValue());
            }
            log.info("new instrument {}", instrument.toString());
            
            books.put(instrumentName, new OrderBook(this));
            log.debug("created order book for {}", instrumentName);
        }
    }
    
    void enterOrder(EnterOrder message) {
        // check username not empty
        // check not duplicate order
        // check for instrument
        // check for valid price
        // check for valid quantity
    }
    
    void cancelOrder(CancelOrder message) {
        // check username not empty
        // check order exists
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
}
