package Loads;   

import java.util.*;
import org.cpswt.config.FederateConfig;   
import org.cpswt.config.FederateParameter;


public class LoadsConfig extends FederateConfig {

    @FederateParameter
    public int number;

    @FederateParameter
     public Load[] loads ;

    //public Load[] loads = new Load[2];
	

 
   
	
}