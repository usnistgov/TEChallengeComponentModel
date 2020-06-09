package gov.nist.hla.te.matlabclient.exception;

public class ObjectModelException extends RuntimeException {
    public ObjectModelException(String message) {
        super(message);
    }
    
    public ObjectModelException(String message, Throwable e) {
        super(message, e);
    }
    
    public ObjectModelException(Throwable e) {
        super(e);
    }
}
