package gov.nist.hla.parity.paritysystem.types;

public enum MessageType {
    ENTER('E'),
    CANCEL('X'),
    ACCEPTED('A'),
    REJECTED('R'),
    EXECUTED('E'),
    CANCELED('X');
    
    private final char value;
    
    private MessageType(char value) {
        this.value = value;
    }
    
    public char asChar() {
        return value;
    }
    
    public static boolean isValid(char value) {
        for (MessageType messageType : MessageType.values()) {
            if (messageType.value == value) {
                return true;
            }
        }
        return false;
    }
}
