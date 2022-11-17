package gov.nist.hla.te.pricereader;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateParameter;

public class PriceReaderConfig extends FederateConfig {
    @FederateParameter
    public String dapFilePath;

    @FederateParameter
    public String rtpFilePath;
}