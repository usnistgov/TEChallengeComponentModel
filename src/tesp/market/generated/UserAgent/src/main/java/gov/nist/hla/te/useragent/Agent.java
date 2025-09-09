package gov.nist.hla.te.useragent;

interface Agent {
    String handleQuote(String marketId, String price, String quantity, boolean isBuyQuote);
}
