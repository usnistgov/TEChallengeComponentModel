package gov.nist.hla.te.parity.paritysystem;

import java.beans.ConstructorProperties;
import java.util.Objects;

public class Instrument {
    private final String instrumentName;
    
    private final int priceFractionalPrecision;
    
    private final int quantityFractionalPrecision;
    
    @ConstructorProperties({"instrumentName","priceFractionalPrecision","quantityFractionalPrecision"})
    public Instrument(String instrumentName, int priceFractionalPrecision, int quantityFractionalPrecision) {
        this.instrumentName = instrumentName;
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
