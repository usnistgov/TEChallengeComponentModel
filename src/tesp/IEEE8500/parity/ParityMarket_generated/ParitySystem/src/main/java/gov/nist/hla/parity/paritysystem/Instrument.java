package gov.nist.hla.parity.paritysystem;

import java.beans.ConstructorProperties;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Instrument {
    private final static Logger log = LogManager.getLogger();
    
    private final int PARITY_INSTRUMENT_LENGTH = 8;
    
    private final String instrumentName;
    
    private final int priceFractionalPrecision;
    
    private final int quantityFractionalPrecision;
    
    @ConstructorProperties({"instrumentName","priceFractionalPrecision","quantityFractionalPrecision"})
    public Instrument(String instrumentName, int priceFractionalPrecision, int quantityFractionalPrecision) {
        String truncatedName = instrumentName;
        if (instrumentName.length() > PARITY_INSTRUMENT_LENGTH) {
            log.warn("instrument name {} is more than {} characters and will be truncated", instrumentName, PARITY_INSTRUMENT_LENGTH);
            truncatedName = instrumentName.substring(0, PARITY_INSTRUMENT_LENGTH);
        }
        this.instrumentName = truncatedName;
        this.priceFractionalPrecision = priceFractionalPrecision;
        this.quantityFractionalPrecision = quantityFractionalPrecision;
    }
    
    public String getInstrumentName() {
        return instrumentName;
    }
    
    public int getPriceFractionalPrecision() {
        return priceFractionalPrecision;
    }
    
    public int getQuantityFractionalPrecision() {
        return quantityFractionalPrecision;
    }
    
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        
        if (!(object instanceof Instrument)) {
            return false;
        }
        
        Instrument instrument = (Instrument) object;
        return Objects.equals(instrumentName, instrument.instrumentName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(instrumentName);
    }
    
    @Override
    public String toString() {
        return "(" + instrumentName + "," + priceFractionalPrecision + "," + quantityFractionalPrecision + ")";
    }
}
