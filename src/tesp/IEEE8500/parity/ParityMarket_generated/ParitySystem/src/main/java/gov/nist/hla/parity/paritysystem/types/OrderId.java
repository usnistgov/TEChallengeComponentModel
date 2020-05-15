package gov.nist.hla.parity.paritysystem.types;

public class OrderId extends FixedLengthString {
    public final static int MAX_LENGTH = 16;
    
    public OrderId(String id) {
        super(id, MAX_LENGTH);
    }
}
