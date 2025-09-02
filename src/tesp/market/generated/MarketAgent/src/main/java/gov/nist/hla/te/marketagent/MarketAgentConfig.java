package gov.nist.hla.te.marketagent;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateParameter;

public class MarketAgentConfig extends FederateConfig {
    @FederateParameter
    public String priceFilePath;

    @FederateParameter
    public String transformerFilePath;
}
