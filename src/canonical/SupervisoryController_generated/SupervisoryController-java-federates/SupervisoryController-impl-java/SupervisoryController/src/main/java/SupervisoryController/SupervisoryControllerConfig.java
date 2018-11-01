package SupervisoryController;   

import java.util.*;
import org.cpswt.config.FederateConfig;   
import org.cpswt.config.FederateParameter;


public class SupervisoryControllerConfig extends FederateConfig {

    @FederateParameter
    public int numberofsc;

    @FederateParameter
     public Scontroller[]  superControls;   

    @FederateParameter
    public Stender[]  tenders; 
	

	
}
