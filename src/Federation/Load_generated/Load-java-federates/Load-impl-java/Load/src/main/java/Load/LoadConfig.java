package Load;   

import java.util.*;
import org.cpswt.config.FederateConfig;   
import org.cpswt.config.FederateParameter;


public class LoadConfig extends FederateConfig {

	

	@FederateParameter
	public int id;

    @FederateParameter
    public List<House> houses = new ArrayList<House>();
	

 
   
	
}
