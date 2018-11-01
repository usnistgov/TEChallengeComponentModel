package Generators;   

import java.util.*;
import org.cpswt.config.FederateConfig;   
import org.cpswt.config.FederateParameter;


public class GeneratorsConfig extends FederateConfig {

    @FederateParameter
    public int number;

    @FederateParameter
     public Generator[] generators ;

    //public Load[] loads = new Load[2];
	

 
   
	
}