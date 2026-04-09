package gov.nist.hla.te.useragent;

interface Agent {
    class MarketDetails {
        public String cost = "";
        public String quantity = "";
    }

    String getAgentId();
    String getTransformerId();
    String getBuyQuoteResponse();
    String getSellQuoteResponse();
    void handleBuyQuote(String id, String priceString, String quantityString, boolean hasMarketActivity);
    void handleSellQuote(String id, String priceString, String quantityString, boolean hasMarketActivity);
    void handleTransaction(String priceString, String quantityString, boolean isBuyTransaction);
    MarketDetails closeMarket();
}
