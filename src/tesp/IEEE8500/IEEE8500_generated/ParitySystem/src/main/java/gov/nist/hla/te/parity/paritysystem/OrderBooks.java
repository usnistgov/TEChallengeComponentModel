package gov.nist.hla.te.parity.paritysystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.paritytrading.parity.match.OrderBook;
import com.paritytrading.parity.match.OrderBookListener;
import com.paritytrading.parity.match.Side;

import gov.nist.hla.te.parity.paritysystem.exception.DuplicateIdentifierException;
import gov.nist.hla.te.parity.paritysystem.exception.InvalidTenderException;
import gov.nist.hla.te.parity.paritysystem.rti.Tender;

public class OrderBooks implements OrderBookListener {
    private final static Logger log = LogManager.getLogger();
    
    private final ParitySystem federate;
    
    private Map<String, Party> parties = new HashMap<String, Party>();
    
    private Map<String, Instrument> instruments = new HashMap<String, Instrument>();

    private Map<String, OrderBook> books = new HashMap<String, OrderBook>();
    
    private long nextOrderId = 0;
    
    public OrderBooks(List<Instrument> instruments, ParitySystem federate) {
        for (Instrument instrument : instruments) {
            final String instrumentName = instrument.getInstrumentName();
            
            if (this.instruments.put(instrumentName, instrument) != null) {
                log.fatal("multiple instruments with the name {}", instrumentName);
                throw new DuplicateIdentifierException(instrumentName);
            }
            log.info("new instrument {}", instrument.toString());
            
            books.put(instrumentName, new OrderBook(this));
            log.debug("created order book for {}", instrumentName);
        }
        
        // this reference is unfortunately necessary to create the HLA messages
        // it seems like the create message methods should be static...
        this.federate = federate;
    }
    
    void enterOrder(Tender tender) {
        log.trace("enterOrder {}", tender.toString());
        
        // tender.get_expireTime() not used
        
        try {
            Party party = getParty(tender);
            if (party.hasTender(tender.get_tenderId())) {
                return; // not an error
            }
            
            Instrument instrument = getInstrument(tender);
            OrderBook book = getOrderBook(instrument);
            Side side = getSide(tender);

            long price = getPrice(tender, instrument);
            long quantity = getQuantity(tender, instrument);
            
            long orderId = nextOrderId++;
            
            party.addTender(orderId, tender);
            book.enter(orderId, side, price, quantity);
        } catch (InvalidTenderException e) {
            log.error("received bad tender, {}: {}", e.getMessage(), tender.toString());
        }
    }
    
    private Party getParty(Tender tender)
            throws InvalidTenderException {
        final String partyId = tender.get_partyId();
        
        if (partyId == null || partyId.isEmpty()) {
            throw new InvalidTenderException("invalid partyId");
        }
        
        Party party = parties.get(partyId);
        if (party == null) {
            party = new Party(partyId);
            parties.put(partyId, party);
            log.info("created new party: {}", partyId);
        }
        return party;
    }
    
    private Instrument getInstrument(Tender tender)
            throws InvalidTenderException {
        final String instrumentName = getInstrumentName(tender);
        
        if (!instruments.containsKey(instrumentName)) {
            throw new InvalidTenderException("unknown instrument");
        }
        return instruments.get(instrumentName);
    }
    
    private OrderBook getOrderBook(Instrument instrument) {
        final OrderBook book = books.get(instrument.getInstrumentName());
        
        if (book == null) {
            throw new IllegalArgumentException("unknown instrument");
        }
        return book;
    }
    
    private String getInstrumentName(Tender tender)
            throws InvalidTenderException {
        final String startTime = tender.get_startTime();
        
        if (startTime == null || startTime.isEmpty()) {
            throw new InvalidTenderException("invalid startTime");
        }
        return startTime; // placeholder for something more complicated
    }
    
    private long getPrice(Tender tender, Instrument instrument)
            throws InvalidTenderException {
        if (tender.get_price() < 0) {
            throw new InvalidTenderException("negative price");
        }
        return Math.round(tender.get_price() * instrument.getPriceFractionalPrecision());
    }
    
    private long getQuantity(Tender tender, Instrument instrument)
            throws InvalidTenderException {
        if (tender.get_quantity() <= 0) {
            throw new InvalidTenderException("nonpositive quantity");
        }
        return Math.round(tender.get_quantity() * instrument.getQuantityFractionalPrecision());
    }
    
    private Side getSide(Tender tender)
            throws InvalidTenderException {
        switch (tender.get_side()) {
            case 's':
            case 'S':
                return Side.SELL;
            case 'b':
            case 'B':
                return Side.BUY;
            default:
                throw new InvalidTenderException("invalid side");
        }
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
