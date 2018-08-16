package gov.nist.hla.gridlabd.exception;

import java.io.IOException;

public class StatusCodeException extends IOException {
    private int statusCode;

    public StatusCodeException(int statusCode, String reasonPhrase) {
        super(reasonPhrase);
        this.statusCode = statusCode;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public String getReasonPhrase() {
        return getMessage();
    }
}
