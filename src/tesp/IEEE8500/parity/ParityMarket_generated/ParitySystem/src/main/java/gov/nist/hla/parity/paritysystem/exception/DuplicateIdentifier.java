package gov.nist.hla.parity.paritysystem.exception;

public class DuplicateInstrumentName extends RuntimeException {
    public DuplicateInstrumentName(String instrumentName) {
        super(instrumentName);
    }
}
