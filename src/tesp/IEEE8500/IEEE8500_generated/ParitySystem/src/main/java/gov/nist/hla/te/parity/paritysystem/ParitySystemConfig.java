package gov.nist.hla.te.parity.paritysystem;

import java.util.List;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateParameter;

public class ParitySystemConfig extends FederateConfig {
    @FederateParameter
    public List<Instrument> instruments;
}
