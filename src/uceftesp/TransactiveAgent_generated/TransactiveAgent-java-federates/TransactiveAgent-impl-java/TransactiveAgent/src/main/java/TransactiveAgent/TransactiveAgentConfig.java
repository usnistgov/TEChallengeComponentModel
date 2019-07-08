package TransactiveAgent;   

import java.util.*;
import org.cpswt.config.FederateConfig;   
import org.cpswt.config.FederateParameter;


public class TransactiveAgentConfig extends FederateConfig {

    @FederateParameter
    public int numberofta;

    @FederateParameter
     public Squote[] quotes ;

     @FederateParameter
     public Stransaction[] transactions ;

  
	

 
   
	
}