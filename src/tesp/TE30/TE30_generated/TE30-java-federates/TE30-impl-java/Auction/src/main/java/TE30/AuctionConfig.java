package TE30;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateParameter;

public class AuctionConfig extends FederateConfig {
    @FederateParameter
    public String configFileName;

    @FederateParameter
    public boolean ieee8500;
}
