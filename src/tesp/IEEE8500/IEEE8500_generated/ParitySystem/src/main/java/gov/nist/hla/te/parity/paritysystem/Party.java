package gov.nist.hla.te.parity.paritysystem;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gov.nist.hla.te.parity.paritysystem.exception.DuplicateIdentifierException;
import gov.nist.hla.te.parity.paritysystem.rti.Tender;

public class Party {
    private final static Logger log = LogManager.getLogger();
    
    private String partyId;
    
    private Map<Long, Tender> tenders = new HashMap<Long, Tender>();
    
    private Map<Long, Long> tenderToOrder = new HashMap<Long, Long>();
    
    public Party(String partyId) {
        this.partyId = partyId;
    }
    
    public String getPartyId() {
        return partyId;
    }
    
    public boolean hasTender(long tenderId) {
        return tenders.containsKey(tenderId);
    }
    
    public void addTender(long orderId, Tender tender) {
        final long tenderId = tender.get_tenderId();
        
        if (tenderToOrder.containsKey(tenderId)) {
            if (tenderToOrder.get(tenderId) != orderId) {
                throw new DuplicateIdentifierException(Long.toString(orderId));
            }
            return;
        }
        tenders.put(tenderId, tender);
        tenderToOrder.put(tenderId, orderId);
    }
}
