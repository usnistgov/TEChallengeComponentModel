package supervisoryController;   

import java.util.*;
import org.cpswt.config.FederateConfig;   
import org.cpswt.config.FederateParameter;


public class supervisoryControllerConfig extends FederateConfig {

    @FederateParameter
    public int numberofsc;

    @FederateParameter
     public Scontroller[]  superControls;   
	

	
}