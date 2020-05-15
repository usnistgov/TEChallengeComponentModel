package gov.nist.hla.parity.paritysystem.types;

public class InstrumentName extends FixedLengthString {
    public final static int MAX_LENGTH = 8;
    
    public InstrumentName(String name) {
        super(name, MAX_LENGTH);
    }
}
