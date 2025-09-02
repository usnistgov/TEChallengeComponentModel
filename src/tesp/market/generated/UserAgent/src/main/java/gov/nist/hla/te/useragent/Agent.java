package gov.nist.hla.te.useragent;

interface Agent {
    void setMarketInterval(String interval);
    void handleQuote(String price, String quantity, char side);
    void handleTransaction(String price, String quantity, char side);
    String getId();
    String getPrice(char side);
    String getQuantity(char side);
}
