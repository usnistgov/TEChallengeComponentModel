package gov.nist.hla.te.parity.parityreporter;

import gov.nist.hla.te.parity.parityreporter.exception.InvalidTransactionException;
import gov.nist.hla.te.parity.parityreporter.rti.Transaction;

public class Trade {
    final private long matchNumber;
    
    final private double price;
    
    final private double quantity;
    
    final private String startTime;
    
    final private long durationInMinutes;
    
    private String buyer = null;
    
    private String seller = null;
    
    public Trade(Transaction transaction)
            throws InvalidTransactionException {
        this.matchNumber = transaction.get_matchNumber();
        this.price = transaction.get_price();
        this.quantity = transaction.get_quantity();
        this.startTime = transaction.get_startTime();
        this.durationInMinutes = transaction.get_durationInMinutes();
        processSide(transaction);
    }
    
    public long getMatchNumber() {
        return matchNumber;
    }
    
    public void update(Transaction transaction) 
            throws InvalidTransactionException {
        if (matchNumber != transaction.get_matchNumber()) {
            throw new IllegalArgumentException("unexpected matchNumber");
        }
        processSide(transaction);
    }
    
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof Trade)) {
            return false;
        }
        
        Trade trade = (Trade) object;
        return matchNumber == trade.matchNumber;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (matchNumber ^ (matchNumber >>> 32));
        return result;
    }
    
    @Override
    public String toString() {
        return "Trade #" + matchNumber
                + " [price=" + price + " quantity=" + quantity 
                + ", buyer=" + buyer + " seller=" + seller
                + ", startTime=" + startTime + ", durationInMinutes=" + durationInMinutes
                + "]";
    }

    private void processSide(Transaction transaction)
            throws InvalidTransactionException {
        switch (transaction.get_side()) {
        case 'b':
        case 'B':
            setBuyer(transaction.get_partyId());
            break;
        case 's':
        case 'S':
            setSeller(transaction.get_partyId());
            break;
        default:
            throw new InvalidTransactionException("invalid side");
        }
    }
    
    void setBuyer(String partyId)
            throws InvalidTransactionException {
        if (buyer != null) {
            throw new IllegalArgumentException("buyer already set");
        }
        if (partyId == null || partyId.isEmpty()) {
            throw new InvalidTransactionException("invalid partyId");
        }
        buyer = partyId;
    }
    
    void setSeller(String partyId)
            throws InvalidTransactionException {
        if (seller != null) {
            throw new IllegalArgumentException("seller already set");
        }
        if (partyId == null || partyId.isEmpty()) {
            throw new InvalidTransactionException("invalid partyId");
        }
        seller = partyId;
    }
}
