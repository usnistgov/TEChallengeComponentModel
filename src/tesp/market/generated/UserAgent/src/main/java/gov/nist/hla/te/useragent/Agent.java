package gov.nist.hla.te.useragent;

interface Agent {
    void closeMarket();
    String handleQuote(String marketId, String price, String quantity, boolean isBuyQuote);
    String handleTransaction(String marketId, String price, String quantity, boolean isBuyQuote);
}
