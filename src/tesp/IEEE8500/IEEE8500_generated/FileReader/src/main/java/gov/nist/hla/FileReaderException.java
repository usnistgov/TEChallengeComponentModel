package gov.nist.hla;

public class FileReaderException extends RuntimeException {
    public FileReaderException(String message) {
        super(message);
    }
    
    public FileReaderException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public FileReaderException(Throwable cause) {
        super(cause);
    }
}
