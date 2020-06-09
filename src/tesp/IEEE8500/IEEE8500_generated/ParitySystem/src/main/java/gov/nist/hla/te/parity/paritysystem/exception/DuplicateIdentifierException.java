package gov.nist.hla.te.parity.paritysystem.exception;

public class DuplicateIdentifierException extends RuntimeException {
    public DuplicateIdentifierException(String identifier) {
        super(identifier);
    }
}
