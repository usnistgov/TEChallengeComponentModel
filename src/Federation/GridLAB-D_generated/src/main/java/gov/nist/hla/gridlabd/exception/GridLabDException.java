package gov.nist.hla.gridlabd.exception;

public class GridLabDException extends RuntimeException {
    public GridLabDException(String message) {
        super(message);
    }

    public GridLabDException(String message, Throwable cause) {
        super(message, cause);
    }

    public GridLabDException(Throwable cause) {
        super(cause);
    }
}
