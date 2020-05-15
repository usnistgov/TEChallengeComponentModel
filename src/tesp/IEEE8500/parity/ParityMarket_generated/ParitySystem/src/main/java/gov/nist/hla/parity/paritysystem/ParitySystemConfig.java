package gov.nist.hla.parity.paritysystem;

import java.util.List;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateParameter;

import gov.nist.hla.parity.paritysystem.types.Instrument;

public class ParitySystemConfig extends FederateConfig {
    @FederateParameter
    public List<Instrument> instruments;
}
