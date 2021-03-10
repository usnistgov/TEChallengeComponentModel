package gov.nist.hla.te.controller;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateParameter;

public class ControllerConfig extends FederateConfig {
    @FederateParameter
    public String configFileName;
}