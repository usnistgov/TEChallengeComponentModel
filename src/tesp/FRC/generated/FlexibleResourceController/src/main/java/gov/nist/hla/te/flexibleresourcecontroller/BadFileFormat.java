package gov.nist.hla.te.flexibleresourcecontroller;

public class BadFileFormat extends RuntimeException {
    public BadFileFormat(String message) {
        super(message);
    }
    
    public BadFileFormat(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BadFileFormat(Throwable cause) {
        super(cause);
    }
}