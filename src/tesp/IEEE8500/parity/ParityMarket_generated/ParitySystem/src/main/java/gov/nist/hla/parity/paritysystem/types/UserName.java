package gov.nist.hla.parity.paritysystem.types;

public class UserName extends FixedLengthString {
    public final static int MAX_LENGTH = 8;
    
    public UserName(String name) {
        super(name, MAX_LENGTH);
    }
}
