  
package TE30;

import org.cpswt.config.FederateConfig;
import org.cpswt.config.FederateParameter;

/**
 * An example of how to implement custom configuration options for a federate.
 * 
 * A custom configuration file requires the definition of a class that extends from FederateConfig. Each configuration
 * option must be declared as a public member variable annotated with the FederateParameter annotation.
 * 
 * See {@link InputSource#main(String[])} and {@link InputSource#InputSource(InputSourceConfig)} for how to use the
 * configuration class to initialize a federate.
 */
public class InputSourceConfig extends FederateConfig {
    /**
     * The number of operands to generate for the adder.
     */
    @FederateParameter
    public String IP_address = "192.168.56.101";
    
    /**
     * The maximum operand value the input source can generate.
     */
    @FederateParameter
    public int Port_Number = 6789;
}