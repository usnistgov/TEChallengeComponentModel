package gov.nist.hla.parity.paritysystem.types;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// required for parity which uses fixed-length buffers
public class FixedLengthString {
    private final static Logger log = LogManager.getLogger();
    
    private final String value;
    
    private final int maxLength;
    
    public FixedLengthString(String value, int maxLength) {
        if (value == null) {
            log.error("received null string in FixedLengthString constructor");
            throw new NullPointerException("String value cannot be null");
        }
        
        if (value.length() > maxLength) {
            this.value = value.substring(0, maxLength);
            log.warn("truncated the string {} to {} characters: {}", value, maxLength, this.value);
        } else {
            this.value = value;
        }
        this.maxLength = maxLength;
    }
    
    public String getValue() {
        return value;
    }
    
    public int getMaxLength() {
        return maxLength;
    }
    
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        
        if (!(object instanceof FixedLengthString)) {
            return false;
        }
        
        FixedLengthString string = (FixedLengthString) object;
        return value == string.value;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return value;
    }
}
