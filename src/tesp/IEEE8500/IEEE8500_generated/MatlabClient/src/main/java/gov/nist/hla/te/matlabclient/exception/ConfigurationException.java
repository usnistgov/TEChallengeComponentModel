package gov.nist.hla.te.matlabclient.exception;

public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String message) {
        super(message);
    }
    
    public ConfigurationException(String message, Throwable e) {
        super(message, e);
    }
    
    public ConfigurationException(Throwable e) {
        super(e);
    }
}
