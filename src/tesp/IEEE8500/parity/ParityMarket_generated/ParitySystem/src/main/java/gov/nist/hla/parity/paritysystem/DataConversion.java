package gov.nist.hla.parity.paritysystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataConversion {
    private final static Logger log = LogManager.getLogger();

    private final static int INSTRUMENT_NAME_LENGTH = 8;
    
    private final static int USER_NAME_LENGTH = 8;
    
    private final static int ORDER_ID_LENGTH = 16;

    public static String parseInstrumentName(String instrumentName) {
        return parseGeneric(instrumentName, INSTRUMENT_NAME_LENGTH, "instrument name");
    }
    
    public static String parseUserName(String userName) {
        return parseGeneric(userName, USER_NAME_LENGTH, "user name");
    }
    
    public static String parseOrderId(String orderId) {
        return parseGeneric(orderId, ORDER_ID_LENGTH, "order id");
    }
    
    private static String parseGeneric(String value, int maxLength, String variableName) {
        if (value.length() > maxLength) {
            log.warn("{} {} is more than {} characters and will be truncated", variableName, value, maxLength);
            return value.substring(0, maxLength);
        }
        return value;
    }
}
