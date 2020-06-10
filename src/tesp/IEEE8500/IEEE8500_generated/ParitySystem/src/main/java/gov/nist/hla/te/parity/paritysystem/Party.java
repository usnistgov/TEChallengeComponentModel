package gov.nist.hla.te.parity.paritysystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gov.nist.hla.te.parity.paritysystem.exception.DuplicateIdentifierException;
import gov.nist.hla.te.parity.paritysystem.rti.Tender;

public class Party {
    private String partyId;
    
    private Map<Long, Tender> currentTenders = new HashMap<Long, Tender>();
    
    private Set<Long> usedTenderId = new HashSet<Long>();
    
    public Party(String partyId) {
        this.partyId = partyId;
    }
    
    public String getPartyId() {
        return partyId;
    }
    
    public boolean isKnownTender(long tenderId) {
        return usedTenderId.contains(tenderId);
    }
    
    public boolean hasOrder(long orderId) {
        return currentTenders.containsKey(orderId);
    }
    
    public void addTender(long orderId, Tender tender) {
        final long tenderId = tender.get_tenderId();
        
        if (currentTenders.containsKey(orderId)) {
            throw new DuplicateIdentifierException(Long.toString(orderId));
        }
        if (isKnownTender(tenderId)) {
            throw new DuplicateIdentifierException(Long.toString(tenderId));
        }
        
        currentTenders.put(orderId, tender);
        usedTenderId.add(tenderId);
    }
    
    public Tender getTender(long orderId) {
        return currentTenders.get(orderId);
    }
    
    public Tender removeTender(long orderId) {
        return currentTenders.remove(orderId);
    }
}
