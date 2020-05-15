package gov.nist.hla.parity.paritysystem.exception;

public class DuplicateIdentifier extends RuntimeException {
    public DuplicateIdentifier(String instrumentName) {
        super(instrumentName);
    }
}
