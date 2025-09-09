package gov.nist.hla.te.useragent;

interface Agent {
    String getAgentId();
    String getTransformerId();
    String handleQuote(String priceString, String quantityString, boolean isBuyQuote);
    void handleTransaction(String priceString, String quantityString, boolean isBuyTransaction);
    void closeMarket();
}
