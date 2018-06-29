
package Load;

import java.util.HashSet;
import java.util.Set;

import hla.rti.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cpswt.utils.CpswtUtils;


import org.cpswt.hla.*;

/**
* The resourcesPhysicalStatus class implements the resourcesPhysicalStatus object in the
* Load simulation.
*/
public class resourcesPhysicalStatus extends ObjectRoot {

	private static final Logger logger = LogManager.getLogger(resourcesPhysicalStatus.class);

	/**
	* Default constructor -- creates an instance of the resourcesPhysicalStatus object
	* class with default attribute values.
	*/
	public resourcesPhysicalStatus() { }

	
	
	private static int _current_Imaginary_A_handle;
	private static int _current_Imaginary_B_handle;
	private static int _current_Imaginary_C_handle;
	private static int _current_Real_A_handle;
	private static int _current_Real_B_handle;
	private static int _current_Real_C_handle;
	private static int _gridNodeId_handle;
	private static int _impedance_Imaginary_A_handle;
	private static int _impedance_Imaginary_B_handle;
	private static int _impedance_Imaginary_C_handle;
	private static int _impedance_Real_A_handle;
	private static int _impedance_Real_B_handle;
	private static int _impedance_Real_C_handle;
	private static int _phases_handle;
	private static int _power_Imaginary_A_handle;
	private static int _power_Imaginary_B_handle;
	private static int _power_Imaginary_C_handle;
	private static int _power_Real_A_handle;
	private static int _power_Real_B_handle;
	private static int _power_Real_C_handle;
	private static int _status_handle;
	private static int _voltage_Imaginary_A_handle;
	private static int _voltage_Imaginary_B_handle;
	private static int _voltage_Imaginary_C_handle;
	private static int _voltage_Real_A_handle;
	private static int _voltage_Real_B_handle;
	private static int _voltage_Real_C_handle;
	
	
	/**
	* Returns the handle (RTI assigned) of the "current_Imaginary_A" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "current_Imaginary_A" attribute
	*/
	public static int get_current_Imaginary_A_handle() { return _current_Imaginary_A_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "current_Imaginary_B" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "current_Imaginary_B" attribute
	*/
	public static int get_current_Imaginary_B_handle() { return _current_Imaginary_B_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "current_Imaginary_C" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "current_Imaginary_C" attribute
	*/
	public static int get_current_Imaginary_C_handle() { return _current_Imaginary_C_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "current_Real_A" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "current_Real_A" attribute
	*/
	public static int get_current_Real_A_handle() { return _current_Real_A_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "current_Real_B" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "current_Real_B" attribute
	*/
	public static int get_current_Real_B_handle() { return _current_Real_B_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "current_Real_C" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "current_Real_C" attribute
	*/
	public static int get_current_Real_C_handle() { return _current_Real_C_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "gridNodeId" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "gridNodeId" attribute
	*/
	public static int get_gridNodeId_handle() { return _gridNodeId_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "impedance_Imaginary_A" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "impedance_Imaginary_A" attribute
	*/
	public static int get_impedance_Imaginary_A_handle() { return _impedance_Imaginary_A_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "impedance_Imaginary_B" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "impedance_Imaginary_B" attribute
	*/
	public static int get_impedance_Imaginary_B_handle() { return _impedance_Imaginary_B_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "impedance_Imaginary_C" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "impedance_Imaginary_C" attribute
	*/
	public static int get_impedance_Imaginary_C_handle() { return _impedance_Imaginary_C_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "impedance_Real_A" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "impedance_Real_A" attribute
	*/
	public static int get_impedance_Real_A_handle() { return _impedance_Real_A_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "impedance_Real_B" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "impedance_Real_B" attribute
	*/
	public static int get_impedance_Real_B_handle() { return _impedance_Real_B_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "impedance_Real_C" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "impedance_Real_C" attribute
	*/
	public static int get_impedance_Real_C_handle() { return _impedance_Real_C_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "phases" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "phases" attribute
	*/
	public static int get_phases_handle() { return _phases_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "power_Imaginary_A" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "power_Imaginary_A" attribute
	*/
	public static int get_power_Imaginary_A_handle() { return _power_Imaginary_A_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "power_Imaginary_B" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "power_Imaginary_B" attribute
	*/
	public static int get_power_Imaginary_B_handle() { return _power_Imaginary_B_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "power_Imaginary_C" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "power_Imaginary_C" attribute
	*/
	public static int get_power_Imaginary_C_handle() { return _power_Imaginary_C_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "power_Real_A" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "power_Real_A" attribute
	*/
	public static int get_power_Real_A_handle() { return _power_Real_A_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "power_Real_B" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "power_Real_B" attribute
	*/
	public static int get_power_Real_B_handle() { return _power_Real_B_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "power_Real_C" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "power_Real_C" attribute
	*/
	public static int get_power_Real_C_handle() { return _power_Real_C_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "status" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "status" attribute
	*/
	public static int get_status_handle() { return _status_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "voltage_Imaginary_A" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "voltage_Imaginary_A" attribute
	*/
	public static int get_voltage_Imaginary_A_handle() { return _voltage_Imaginary_A_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "voltage_Imaginary_B" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "voltage_Imaginary_B" attribute
	*/
	public static int get_voltage_Imaginary_B_handle() { return _voltage_Imaginary_B_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "voltage_Imaginary_C" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "voltage_Imaginary_C" attribute
	*/
	public static int get_voltage_Imaginary_C_handle() { return _voltage_Imaginary_C_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "voltage_Real_A" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "voltage_Real_A" attribute
	*/
	public static int get_voltage_Real_A_handle() { return _voltage_Real_A_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "voltage_Real_B" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "voltage_Real_B" attribute
	*/
	public static int get_voltage_Real_B_handle() { return _voltage_Real_B_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "voltage_Real_C" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "voltage_Real_C" attribute
	*/
	public static int get_voltage_Real_C_handle() { return _voltage_Real_C_handle; }
	
	
	
	private static boolean _isInitialized = false;

	private static int _handle;

	/**
	* Returns the handle (RTI assigned) of the resourcesPhysicalStatus object class.
	* Note: As this is a static method, it is NOT polymorphic, and so, if called on
	* a reference will return the handle of the class pertaining to the reference,\
	* rather than the handle of the class for the instance referred to by the reference.
	* For the polymorphic version of this method, use {@link #getClassHandle()}.
	*/
	public static int get_handle() { return _handle; }

	/**
	* Returns the fully-qualified (dot-delimited) name of the resourcesPhysicalStatus
	* object class.
	* Note: As this is a static method, it is NOT polymorphic, and so, if called on
	* a reference will return the name of the class pertaining to the reference,\
	* rather than the name of the class for the instance referred to by the reference.
	* For the polymorphic version of this method, use {@link #getClassName()}.
	*/
	public static String get_class_name() { return "ObjectRoot.resourcesPhysicalStatus"; }

	/**
	* Returns the simple name (the last name in the dot-delimited fully-qualified
	* class name) of the resourcesPhysicalStatus object class.
	*/
	public static String get_simple_class_name() { return "resourcesPhysicalStatus"; }

	private static Set< String > _datamemberNames = new HashSet< String >();
	private static Set< String > _allDatamemberNames = new HashSet< String >();

	/**
	* Returns a set containing the names of all of the non-hidden attributes in the
	* resourcesPhysicalStatus object class.
	* Note: As this is a static method, it is NOT polymorphic, and so, if called on
	* a reference will return a set of parameter names pertaining to the reference,\
	* rather than the parameter names of the class for the instance referred to by
	* the reference.  For the polymorphic version of this method, use
	* {@link #getAttributeNames()}.
	*/
	public static Set< String > get_attribute_names() {
		return new HashSet< String >(_datamemberNames);
	}


	/**
	* Returns a set containing the names of all of the attributes in the
	* resourcesPhysicalStatus object class.
	* Note: As this is a static method, it is NOT polymorphic, and so, if called on
	* a reference will return a set of parameter names pertaining to the reference,\
	* rather than the parameter names of the class for the instance referred to by
	* the reference.  For the polymorphic version of this method, use
	* {@link #getAttributeNames()}.
	*/
	public static Set< String > get_all_attribute_names() {
		return new HashSet< String >(_allDatamemberNames);
	}


	
	private static AttributeHandleSet _publishedAttributeHandleSet;
	private static Set< String > _publishAttributeNameSet = new HashSet< String >();

	private static AttributeHandleSet _subscribedAttributeHandleSet; 
	private static Set< String > _subscribeAttributeNameSet = new HashSet< String >();

	

	static {
		_classNameSet.add("ObjectRoot.resourcesPhysicalStatus");
		_classNameClassMap.put("ObjectRoot.resourcesPhysicalStatus", resourcesPhysicalStatus.class);
		
		_datamemberClassNameSetMap.put("ObjectRoot.resourcesPhysicalStatus", _datamemberNames);
		_allDatamemberClassNameSetMap.put("ObjectRoot.resourcesPhysicalStatus", _allDatamemberNames);

		
		
		_datamemberNames.add("current_Imaginary_A");
		_datamemberNames.add("current_Imaginary_B");
		_datamemberNames.add("current_Imaginary_C");
		_datamemberNames.add("current_Real_A");
		_datamemberNames.add("current_Real_B");
		_datamemberNames.add("current_Real_C");
		_datamemberNames.add("gridNodeId");
		_datamemberNames.add("impedance_Imaginary_A");
		_datamemberNames.add("impedance_Imaginary_B");
		_datamemberNames.add("impedance_Imaginary_C");
		_datamemberNames.add("impedance_Real_A");
		_datamemberNames.add("impedance_Real_B");
		_datamemberNames.add("impedance_Real_C");
		_datamemberNames.add("phases");
		_datamemberNames.add("power_Imaginary_A");
		_datamemberNames.add("power_Imaginary_B");
		_datamemberNames.add("power_Imaginary_C");
		_datamemberNames.add("power_Real_A");
		_datamemberNames.add("power_Real_B");
		_datamemberNames.add("power_Real_C");
		_datamemberNames.add("status");
		_datamemberNames.add("voltage_Imaginary_A");
		_datamemberNames.add("voltage_Imaginary_B");
		_datamemberNames.add("voltage_Imaginary_C");
		_datamemberNames.add("voltage_Real_A");
		_datamemberNames.add("voltage_Real_B");
		_datamemberNames.add("voltage_Real_C");
		
		
		_allDatamemberNames.add("current_Imaginary_A");
		_allDatamemberNames.add("current_Imaginary_B");
		_allDatamemberNames.add("current_Imaginary_C");
		_allDatamemberNames.add("current_Real_A");
		_allDatamemberNames.add("current_Real_B");
		_allDatamemberNames.add("current_Real_C");
		_allDatamemberNames.add("gridNodeId");
		_allDatamemberNames.add("impedance_Imaginary_A");
		_allDatamemberNames.add("impedance_Imaginary_B");
		_allDatamemberNames.add("impedance_Imaginary_C");
		_allDatamemberNames.add("impedance_Real_A");
		_allDatamemberNames.add("impedance_Real_B");
		_allDatamemberNames.add("impedance_Real_C");
		_allDatamemberNames.add("phases");
		_allDatamemberNames.add("power_Imaginary_A");
		_allDatamemberNames.add("power_Imaginary_B");
		_allDatamemberNames.add("power_Imaginary_C");
		_allDatamemberNames.add("power_Real_A");
		_allDatamemberNames.add("power_Real_B");
		_allDatamemberNames.add("power_Real_C");
		_allDatamemberNames.add("status");
		_allDatamemberNames.add("voltage_Imaginary_A");
		_allDatamemberNames.add("voltage_Imaginary_B");
		_allDatamemberNames.add("voltage_Imaginary_C");
		_allDatamemberNames.add("voltage_Real_A");
		_allDatamemberNames.add("voltage_Real_B");
		_allDatamemberNames.add("voltage_Real_C");
		
		
		_datamemberTypeMap.put("current_Imaginary_A", "float");
		_datamemberTypeMap.put("current_Imaginary_B", "float");
		_datamemberTypeMap.put("current_Imaginary_C", "float");
		_datamemberTypeMap.put("current_Real_A", "float");
		_datamemberTypeMap.put("current_Real_B", "float");
		_datamemberTypeMap.put("current_Real_C", "float");
		_datamemberTypeMap.put("gridNodeId", "String");
		_datamemberTypeMap.put("impedance_Imaginary_A", "float");
		_datamemberTypeMap.put("impedance_Imaginary_B", "float");
		_datamemberTypeMap.put("impedance_Imaginary_C", "float");
		_datamemberTypeMap.put("impedance_Real_A", "float");
		_datamemberTypeMap.put("impedance_Real_B", "float");
		_datamemberTypeMap.put("impedance_Real_C", "float");
		_datamemberTypeMap.put("phases", "String");
		_datamemberTypeMap.put("power_Imaginary_A", "float");
		_datamemberTypeMap.put("power_Imaginary_B", "float");
		_datamemberTypeMap.put("power_Imaginary_C", "float");
		_datamemberTypeMap.put("power_Real_A", "float");
		_datamemberTypeMap.put("power_Real_B", "float");
		_datamemberTypeMap.put("power_Real_C", "float");
		_datamemberTypeMap.put("status", "boolean");
		_datamemberTypeMap.put("voltage_Imaginary_A", "float");
		_datamemberTypeMap.put("voltage_Imaginary_B", "float");
		_datamemberTypeMap.put("voltage_Imaginary_C", "float");
		_datamemberTypeMap.put("voltage_Real_A", "float");
		_datamemberTypeMap.put("voltage_Real_B", "float");
		_datamemberTypeMap.put("voltage_Real_C", "float");
	

		_classNamePublishAttributeNameMap.put("ObjectRoot.resourcesPhysicalStatus", _publishAttributeNameSet);
		_publishedAttributeHandleSet = _factory.createAttributeHandleSet();
		_classNamePublishedAttributeMap.put("ObjectRoot.resourcesPhysicalStatus", _publishedAttributeHandleSet);

		_classNameSubscribeAttributeNameMap.put("ObjectRoot.resourcesPhysicalStatus", _subscribeAttributeNameSet);
		_subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
		_classNameSubscribedAttributeMap.put("ObjectRoot.resourcesPhysicalStatus", _subscribedAttributeHandleSet);
	

	}


	private static String initErrorMessage = "Error:  ObjectRoot.resourcesPhysicalStatus:  could not initialize:  ";
	protected static void init(RTIambassador rti) {
		if (_isInitialized) return;
		_isInitialized = true;
		
		ObjectRoot.init(rti);
		
		boolean isNotInitialized = true;
		while(isNotInitialized) {
			try {
				_handle = rti.getObjectClassHandle("ObjectRoot.resourcesPhysicalStatus");
				isNotInitialized = false;
			} catch (FederateNotExecutionMember f) {
				logger.error("{} Federate Not Execution Member", initErrorMessage);
				logger.error(f);
				return;				
			} catch (NameNotFound n) {
				logger.error("{} Name Not Found", initErrorMessage);
				logger.error(n);
				return;				
			} catch (Exception e) {
				logger.error(e);
				CpswtUtils.sleepDefault();
			}
		}

		_classNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus", get_handle());
		_classHandleNameMap.put(get_handle(), "ObjectRoot.resourcesPhysicalStatus");
		_classHandleSimpleNameMap.put(get_handle(), "resourcesPhysicalStatus");

		
		isNotInitialized = true;
		while(isNotInitialized) {
			try {
							
				_current_Imaginary_A_handle = rti.getAttributeHandle("current_Imaginary_A", get_handle());			
				_current_Imaginary_B_handle = rti.getAttributeHandle("current_Imaginary_B", get_handle());			
				_current_Imaginary_C_handle = rti.getAttributeHandle("current_Imaginary_C", get_handle());			
				_current_Real_A_handle = rti.getAttributeHandle("current_Real_A", get_handle());			
				_current_Real_B_handle = rti.getAttributeHandle("current_Real_B", get_handle());			
				_current_Real_C_handle = rti.getAttributeHandle("current_Real_C", get_handle());			
				_gridNodeId_handle = rti.getAttributeHandle("gridNodeId", get_handle());			
				_impedance_Imaginary_A_handle = rti.getAttributeHandle("impedance_Imaginary_A", get_handle());			
				_impedance_Imaginary_B_handle = rti.getAttributeHandle("impedance_Imaginary_B", get_handle());			
				_impedance_Imaginary_C_handle = rti.getAttributeHandle("impedance_Imaginary_C", get_handle());			
				_impedance_Real_A_handle = rti.getAttributeHandle("impedance_Real_A", get_handle());			
				_impedance_Real_B_handle = rti.getAttributeHandle("impedance_Real_B", get_handle());			
				_impedance_Real_C_handle = rti.getAttributeHandle("impedance_Real_C", get_handle());			
				_phases_handle = rti.getAttributeHandle("phases", get_handle());			
				_power_Imaginary_A_handle = rti.getAttributeHandle("power_Imaginary_A", get_handle());			
				_power_Imaginary_B_handle = rti.getAttributeHandle("power_Imaginary_B", get_handle());			
				_power_Imaginary_C_handle = rti.getAttributeHandle("power_Imaginary_C", get_handle());			
				_power_Real_A_handle = rti.getAttributeHandle("power_Real_A", get_handle());			
				_power_Real_B_handle = rti.getAttributeHandle("power_Real_B", get_handle());			
				_power_Real_C_handle = rti.getAttributeHandle("power_Real_C", get_handle());			
				_status_handle = rti.getAttributeHandle("status", get_handle());			
				_voltage_Imaginary_A_handle = rti.getAttributeHandle("voltage_Imaginary_A", get_handle());			
				_voltage_Imaginary_B_handle = rti.getAttributeHandle("voltage_Imaginary_B", get_handle());			
				_voltage_Imaginary_C_handle = rti.getAttributeHandle("voltage_Imaginary_C", get_handle());			
				_voltage_Real_A_handle = rti.getAttributeHandle("voltage_Real_A", get_handle());			
				_voltage_Real_B_handle = rti.getAttributeHandle("voltage_Real_B", get_handle());			
				_voltage_Real_C_handle = rti.getAttributeHandle("voltage_Real_C", get_handle());
				isNotInitialized = false;
			} catch (FederateNotExecutionMember f) {
				logger.error("{} Federate Not Execution Member", initErrorMessage);
				logger.error(f);
				return;
			} catch (ObjectClassNotDefined i) {
				logger.error("{} Object Class Not Defined", initErrorMessage);
				logger.error(i);
				return;
			} catch (NameNotFound n) {
				logger.error("{} Name Not Found", initErrorMessage);
				logger.error(n);
				return;
			} catch (Exception e) {
				logger.error(e);
				CpswtUtils.sleepDefault();
			}
		}
			
			
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,current_Imaginary_A", get_current_Imaginary_A_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,current_Imaginary_B", get_current_Imaginary_B_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,current_Imaginary_C", get_current_Imaginary_C_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,current_Real_A", get_current_Real_A_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,current_Real_B", get_current_Real_B_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,current_Real_C", get_current_Real_C_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,gridNodeId", get_gridNodeId_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,impedance_Imaginary_A", get_impedance_Imaginary_A_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,impedance_Imaginary_B", get_impedance_Imaginary_B_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,impedance_Imaginary_C", get_impedance_Imaginary_C_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,impedance_Real_A", get_impedance_Real_A_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,impedance_Real_B", get_impedance_Real_B_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,impedance_Real_C", get_impedance_Real_C_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,phases", get_phases_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,power_Imaginary_A", get_power_Imaginary_A_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,power_Imaginary_B", get_power_Imaginary_B_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,power_Imaginary_C", get_power_Imaginary_C_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,power_Real_A", get_power_Real_A_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,power_Real_B", get_power_Real_B_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,power_Real_C", get_power_Real_C_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,status", get_status_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,voltage_Imaginary_A", get_voltage_Imaginary_A_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,voltage_Imaginary_B", get_voltage_Imaginary_B_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,voltage_Imaginary_C", get_voltage_Imaginary_C_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,voltage_Real_A", get_voltage_Real_A_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,voltage_Real_B", get_voltage_Real_B_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourcesPhysicalStatus,voltage_Real_C", get_voltage_Real_C_handle());
			
			
		_datamemberHandleNameMap.put(get_current_Imaginary_A_handle(), "current_Imaginary_A");
		_datamemberHandleNameMap.put(get_current_Imaginary_B_handle(), "current_Imaginary_B");
		_datamemberHandleNameMap.put(get_current_Imaginary_C_handle(), "current_Imaginary_C");
		_datamemberHandleNameMap.put(get_current_Real_A_handle(), "current_Real_A");
		_datamemberHandleNameMap.put(get_current_Real_B_handle(), "current_Real_B");
		_datamemberHandleNameMap.put(get_current_Real_C_handle(), "current_Real_C");
		_datamemberHandleNameMap.put(get_gridNodeId_handle(), "gridNodeId");
		_datamemberHandleNameMap.put(get_impedance_Imaginary_A_handle(), "impedance_Imaginary_A");
		_datamemberHandleNameMap.put(get_impedance_Imaginary_B_handle(), "impedance_Imaginary_B");
		_datamemberHandleNameMap.put(get_impedance_Imaginary_C_handle(), "impedance_Imaginary_C");
		_datamemberHandleNameMap.put(get_impedance_Real_A_handle(), "impedance_Real_A");
		_datamemberHandleNameMap.put(get_impedance_Real_B_handle(), "impedance_Real_B");
		_datamemberHandleNameMap.put(get_impedance_Real_C_handle(), "impedance_Real_C");
		_datamemberHandleNameMap.put(get_phases_handle(), "phases");
		_datamemberHandleNameMap.put(get_power_Imaginary_A_handle(), "power_Imaginary_A");
		_datamemberHandleNameMap.put(get_power_Imaginary_B_handle(), "power_Imaginary_B");
		_datamemberHandleNameMap.put(get_power_Imaginary_C_handle(), "power_Imaginary_C");
		_datamemberHandleNameMap.put(get_power_Real_A_handle(), "power_Real_A");
		_datamemberHandleNameMap.put(get_power_Real_B_handle(), "power_Real_B");
		_datamemberHandleNameMap.put(get_power_Real_C_handle(), "power_Real_C");
		_datamemberHandleNameMap.put(get_status_handle(), "status");
		_datamemberHandleNameMap.put(get_voltage_Imaginary_A_handle(), "voltage_Imaginary_A");
		_datamemberHandleNameMap.put(get_voltage_Imaginary_B_handle(), "voltage_Imaginary_B");
		_datamemberHandleNameMap.put(get_voltage_Imaginary_C_handle(), "voltage_Imaginary_C");
		_datamemberHandleNameMap.put(get_voltage_Real_A_handle(), "voltage_Real_A");
		_datamemberHandleNameMap.put(get_voltage_Real_B_handle(), "voltage_Real_B");
		_datamemberHandleNameMap.put(get_voltage_Real_C_handle(), "voltage_Real_C");
		
	}

	private static boolean _isPublished = false;
	private static String publishErrorMessage = "Error:  ObjectRoot.resourcesPhysicalStatus:  could not publish:  ";

	/**
	* Publishes the resourcesPhysicalStatus object class for a federate.
	*
	* @param rti handle to the Local RTI Component
	*/
	public static void publish(RTIambassador rti) {
		if (_isPublished) return;
		
		init(rti);

		
		_publishedAttributeHandleSet.empty();
		for(String attributeName : _publishAttributeNameSet) {
			try {
				_publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.resourcesPhysicalStatus," + attributeName));
			} catch (Exception e) {
				logger.error("{} Could not publish \"" + attributeName + "\" attribute.", publishErrorMessage);
			}
		}
	

		synchronized(rti) {
			boolean isNotPublished = true;
			while(isNotPublished) {
				try {
					rti.publishObjectClass(get_handle(), _publishedAttributeHandleSet);
					isNotPublished = false;
				} catch (FederateNotExecutionMember f) {
					logger.error("{} Federate Not Execution Member", publishErrorMessage);
					logger.error(f);
					return;
				} catch (ObjectClassNotDefined i) {
					logger.error("{} Object Class Not Defined", publishErrorMessage);
					logger.error(i);
					return;
				} catch (Exception e) {
					logger.error(e);
					CpswtUtils.sleepDefault();
				}
			}
		}
		
		_isPublished = true;
	}

	private static String unpublishErrorMessage = "Error:  ObjectRoot.resourcesPhysicalStatus:  could not unpublish:  ";
	/**
	* Unpublishes the resourcesPhysicalStatus object class for a federate.
	*
	* @param rti handle to the Local RTI Component
	*/
	public static void unpublish(RTIambassador rti) {
		if (!_isPublished) return;
		
		init(rti);
		synchronized(rti) {
			boolean isNotUnpublished = true;
			while(isNotUnpublished) {
				try {
					rti.unpublishObjectClass(get_handle());
					isNotUnpublished = false;
				} catch (FederateNotExecutionMember f) {
					logger.error("{} Federate Not Execution Member", unpublishErrorMessage);
					logger.error(f);
					return;
				} catch (ObjectClassNotDefined i) {
					logger.error("{} Object Class Not Defined", unpublishErrorMessage);
					logger.error(i);
					return;
				} catch (ObjectClassNotPublished i) {
					logger.error("{} Object Class Not Published", unpublishErrorMessage);
					logger.error(i);
					return;
				} catch (Exception e) {
					logger.error(e);
					CpswtUtils.sleepDefault();
				}
			}
		}
		
		_isPublished = false;
	}

	private static boolean _isSubscribed = false;
	private static String subscribeErrorMessage = "Error:  ObjectRoot.resourcesPhysicalStatus:  could not subscribe:  ";
	/**
	* Subscribes a federate to the resourcesPhysicalStatus object class.
	*
	* @param rti handle to the Local RTI Component
	*/
	public static void subscribe(RTIambassador rti) {
		if (_isSubscribed) return;
		
		init(rti);
		
		_subscribedAttributeHandleSet.empty();
		for(String attributeName : _subscribeAttributeNameSet) {
			try {
				_subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.resourcesPhysicalStatus," + attributeName));
			} catch (Exception e) {
				logger.error("{} Could not subscribe to \"" + attributeName + "\" attribute.", subscribeErrorMessage);
			}
		}
	
		
		synchronized(rti) {
			boolean isNotSubscribed = true;
			while(isNotSubscribed) {
				try {
					rti.subscribeObjectClassAttributes(get_handle(), _subscribedAttributeHandleSet);
					isNotSubscribed = false;
				} catch (FederateNotExecutionMember f) {
					logger.error("{} Federate Not Execution Member", subscribeErrorMessage);
					logger.error(f);
					return;
				} catch (ObjectClassNotDefined i) {
					logger.error("{} Object Class Not Defined", subscribeErrorMessage);
					logger.error(i);
					return;
				} catch (Exception e) {
					logger.error(e);
					CpswtUtils.sleepDefault();
				}
			}
		}
		
		_isSubscribed = true;
	}

	private static String unsubscribeErrorMessage = "Error:  ObjectRoot.resourcesPhysicalStatus:  could not unsubscribe:  ";
	/**
	* Unsubscribes a federate from the resourcesPhysicalStatus object class.
	*
	* @param rti handle to the Local RTI Component
	*/
	public static void unsubscribe(RTIambassador rti) {
		if (!_isSubscribed) return;

		init(rti);
		synchronized(rti) {
			boolean isNotUnsubscribed = true;
			while(isNotUnsubscribed) {
				try {
					rti.unsubscribeObjectClass(get_handle());
					isNotUnsubscribed = false;
				} catch (FederateNotExecutionMember f) {
					logger.error("{} Federate Not Execution Member", unsubscribeErrorMessage);
					logger.error(f);
					return;
				} catch (ObjectClassNotDefined i) {
					logger.error("{} Object Class Not Defined", unsubscribeErrorMessage);
					logger.error(i);
					return;
				} catch (ObjectClassNotSubscribed i) {
					logger.error("{} Object Class Not Subscribed", unsubscribeErrorMessage);
					logger.error(i);
					return;
				} catch (Exception e) {
					logger.error(e);
					CpswtUtils.sleepDefault();
				}
			}
		}
		
		_isSubscribed = false;
	}

	/**
	* Return true if "handle" is equal to the handle (RTI assigned) of this class
	* (that is, the resourcesPhysicalStatus object class).
	*
	* @param handle handle to compare to the value of the handle (RTI assigned) of
	* this class (the resourcesPhysicalStatus object class).
	* @return "true" if "handle" matches the value of the handle of this class
	* (that is, the resourcesPhysicalStatus object class).
	*/
	public static boolean match(int handle) { return handle == get_handle(); }

	/**
	* Returns the handle (RTI assigned) of this instance's object class .
	* 
	* @return the handle (RTI assigned) if this instance's object class
	*/
	public int getClassHandle() { return get_handle(); }

	/**
	* Returns the fully-qualified (dot-delimited) name of this instance's object class.
	* 
	* @return the fully-qualified (dot-delimited) name of this instance's object class
	*/
	public String getClassName() { return get_class_name(); }

	/**
	* Returns the simple name (last name in its fully-qualified dot-delimited name)
	* of this instance's object class.
	* 
	* @return the simple name of this instance's object class 
	*/
	public String getSimpleClassName() { return get_simple_class_name(); }

	/**
	* Returns a set containing the names of all of the non-hiddenattributes of an
	* object class instance.
	*
	* @return set containing the names of all of the attributes of an
	* object class instance
	*/
	public Set< String > getAttributeNames() { return get_attribute_names(); }

	/**
	* Returns a set containing the names of all of the attributes of an
	* object class instance.
	*
	* @return set containing the names of all of the attributes of an
	* object class instance
	*/
	public Set< String > getAllAttributeNames() { return get_all_attribute_names(); }

	/**
	* Publishes the object class of this instance of the class for a federate.
	*
	* @param rti handle to the Local RTI Component
	*/
	public void publishObject(RTIambassador rti) { publish(rti); }

	/**
	* Unpublishes the object class of this instance of this class for a federate.
	*
	* @param rti handle to the Local RTI Component
	*/
	public void unpublishObject(RTIambassador rti) { unpublish(rti); }

	/**
	* Subscribes a federate to the object class of this instance of this class.
	*
	* @param rti handle to the Local RTI Component
	*/
	public void subscribeObject(RTIambassador rti) { subscribe(rti); }

	/**
	* Unsubscribes a federate from the object class of this instance of this class.
	*
	* @param rti handle to the Local RTI Component
	*/
	public void unsubscribeObject(RTIambassador rti) { unsubscribe(rti); }

	
	/**
	* Returns a data structure containing the handles of all attributes for this object
	* class that are currently marked for subscription.  To actually subscribe to these
	* attributes, a federate must call <objectclassname>.subscribe(RTIambassador rti).
	*
	* @return data structure containing the handles of all attributes for this object
	* class that are currently marked for subscription
	*/
	public AttributeHandleSet getSubscribedAttributeHandleSet() { return _subscribedAttributeHandleSet; }
	

	public String toString() {
		return "resourcesPhysicalStatus("
			
			
			+ "current_Imaginary_A:" + get_current_Imaginary_A()
			+ "," + "current_Imaginary_B:" + get_current_Imaginary_B()
			+ "," + "current_Imaginary_C:" + get_current_Imaginary_C()
			+ "," + "current_Real_A:" + get_current_Real_A()
			+ "," + "current_Real_B:" + get_current_Real_B()
			+ "," + "current_Real_C:" + get_current_Real_C()
			+ "," + "gridNodeId:" + get_gridNodeId()
			+ "," + "impedance_Imaginary_A:" + get_impedance_Imaginary_A()
			+ "," + "impedance_Imaginary_B:" + get_impedance_Imaginary_B()
			+ "," + "impedance_Imaginary_C:" + get_impedance_Imaginary_C()
			+ "," + "impedance_Real_A:" + get_impedance_Real_A()
			+ "," + "impedance_Real_B:" + get_impedance_Real_B()
			+ "," + "impedance_Real_C:" + get_impedance_Real_C()
			+ "," + "phases:" + get_phases()
			+ "," + "power_Imaginary_A:" + get_power_Imaginary_A()
			+ "," + "power_Imaginary_B:" + get_power_Imaginary_B()
			+ "," + "power_Imaginary_C:" + get_power_Imaginary_C()
			+ "," + "power_Real_A:" + get_power_Real_A()
			+ "," + "power_Real_B:" + get_power_Real_B()
			+ "," + "power_Real_C:" + get_power_Real_C()
			+ "," + "status:" + get_status()
			+ "," + "voltage_Imaginary_A:" + get_voltage_Imaginary_A()
			+ "," + "voltage_Imaginary_B:" + get_voltage_Imaginary_B()
			+ "," + "voltage_Imaginary_C:" + get_voltage_Imaginary_C()
			+ "," + "voltage_Real_A:" + get_voltage_Real_A()
			+ "," + "voltage_Real_B:" + get_voltage_Real_B()
			+ "," + "voltage_Real_C:" + get_voltage_Real_C()
			+ ")";
	}
	



	
	
	/**
	* Publishes the "current_Imaginary_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "current_Imaginary_A" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_current_Imaginary_A() {
		_publishAttributeNameSet.add( "current_Imaginary_A" );
	}

	/**
	* Unpublishes the "current_Imaginary_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "current_Imaginary_A" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_current_Imaginary_A() {
		_publishAttributeNameSet.remove( "current_Imaginary_A" );
	}
	
	/**
	* Subscribes a federate to the "current_Imaginary_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "current_Imaginary_A" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_current_Imaginary_A() {
		_subscribeAttributeNameSet.add( "current_Imaginary_A" );
	}

	/**
	* Unsubscribes a federate from the "current_Imaginary_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "current_Imaginary_A" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_current_Imaginary_A() {
		_subscribeAttributeNameSet.remove( "current_Imaginary_A" );
	}
	
	
	/**
	* Publishes the "current_Imaginary_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "current_Imaginary_B" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_current_Imaginary_B() {
		_publishAttributeNameSet.add( "current_Imaginary_B" );
	}

	/**
	* Unpublishes the "current_Imaginary_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "current_Imaginary_B" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_current_Imaginary_B() {
		_publishAttributeNameSet.remove( "current_Imaginary_B" );
	}
	
	/**
	* Subscribes a federate to the "current_Imaginary_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "current_Imaginary_B" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_current_Imaginary_B() {
		_subscribeAttributeNameSet.add( "current_Imaginary_B" );
	}

	/**
	* Unsubscribes a federate from the "current_Imaginary_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "current_Imaginary_B" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_current_Imaginary_B() {
		_subscribeAttributeNameSet.remove( "current_Imaginary_B" );
	}
	
	
	/**
	* Publishes the "current_Imaginary_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "current_Imaginary_C" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_current_Imaginary_C() {
		_publishAttributeNameSet.add( "current_Imaginary_C" );
	}

	/**
	* Unpublishes the "current_Imaginary_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "current_Imaginary_C" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_current_Imaginary_C() {
		_publishAttributeNameSet.remove( "current_Imaginary_C" );
	}
	
	/**
	* Subscribes a federate to the "current_Imaginary_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "current_Imaginary_C" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_current_Imaginary_C() {
		_subscribeAttributeNameSet.add( "current_Imaginary_C" );
	}

	/**
	* Unsubscribes a federate from the "current_Imaginary_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "current_Imaginary_C" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_current_Imaginary_C() {
		_subscribeAttributeNameSet.remove( "current_Imaginary_C" );
	}
	
	
	/**
	* Publishes the "current_Real_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "current_Real_A" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_current_Real_A() {
		_publishAttributeNameSet.add( "current_Real_A" );
	}

	/**
	* Unpublishes the "current_Real_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "current_Real_A" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_current_Real_A() {
		_publishAttributeNameSet.remove( "current_Real_A" );
	}
	
	/**
	* Subscribes a federate to the "current_Real_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "current_Real_A" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_current_Real_A() {
		_subscribeAttributeNameSet.add( "current_Real_A" );
	}

	/**
	* Unsubscribes a federate from the "current_Real_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "current_Real_A" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_current_Real_A() {
		_subscribeAttributeNameSet.remove( "current_Real_A" );
	}
	
	
	/**
	* Publishes the "current_Real_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "current_Real_B" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_current_Real_B() {
		_publishAttributeNameSet.add( "current_Real_B" );
	}

	/**
	* Unpublishes the "current_Real_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "current_Real_B" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_current_Real_B() {
		_publishAttributeNameSet.remove( "current_Real_B" );
	}
	
	/**
	* Subscribes a federate to the "current_Real_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "current_Real_B" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_current_Real_B() {
		_subscribeAttributeNameSet.add( "current_Real_B" );
	}

	/**
	* Unsubscribes a federate from the "current_Real_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "current_Real_B" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_current_Real_B() {
		_subscribeAttributeNameSet.remove( "current_Real_B" );
	}
	
	
	/**
	* Publishes the "current_Real_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "current_Real_C" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_current_Real_C() {
		_publishAttributeNameSet.add( "current_Real_C" );
	}

	/**
	* Unpublishes the "current_Real_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "current_Real_C" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_current_Real_C() {
		_publishAttributeNameSet.remove( "current_Real_C" );
	}
	
	/**
	* Subscribes a federate to the "current_Real_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "current_Real_C" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_current_Real_C() {
		_subscribeAttributeNameSet.add( "current_Real_C" );
	}

	/**
	* Unsubscribes a federate from the "current_Real_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "current_Real_C" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_current_Real_C() {
		_subscribeAttributeNameSet.remove( "current_Real_C" );
	}
	
	
	/**
	* Publishes the "gridNodeId" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "gridNodeId" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_gridNodeId() {
		_publishAttributeNameSet.add( "gridNodeId" );
	}

	/**
	* Unpublishes the "gridNodeId" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "gridNodeId" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_gridNodeId() {
		_publishAttributeNameSet.remove( "gridNodeId" );
	}
	
	/**
	* Subscribes a federate to the "gridNodeId" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "gridNodeId" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_gridNodeId() {
		_subscribeAttributeNameSet.add( "gridNodeId" );
	}

	/**
	* Unsubscribes a federate from the "gridNodeId" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "gridNodeId" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_gridNodeId() {
		_subscribeAttributeNameSet.remove( "gridNodeId" );
	}
	
	
	/**
	* Publishes the "impedance_Imaginary_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "impedance_Imaginary_A" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_impedance_Imaginary_A() {
		_publishAttributeNameSet.add( "impedance_Imaginary_A" );
	}

	/**
	* Unpublishes the "impedance_Imaginary_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "impedance_Imaginary_A" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_impedance_Imaginary_A() {
		_publishAttributeNameSet.remove( "impedance_Imaginary_A" );
	}
	
	/**
	* Subscribes a federate to the "impedance_Imaginary_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "impedance_Imaginary_A" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_impedance_Imaginary_A() {
		_subscribeAttributeNameSet.add( "impedance_Imaginary_A" );
	}

	/**
	* Unsubscribes a federate from the "impedance_Imaginary_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "impedance_Imaginary_A" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_impedance_Imaginary_A() {
		_subscribeAttributeNameSet.remove( "impedance_Imaginary_A" );
	}
	
	
	/**
	* Publishes the "impedance_Imaginary_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "impedance_Imaginary_B" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_impedance_Imaginary_B() {
		_publishAttributeNameSet.add( "impedance_Imaginary_B" );
	}

	/**
	* Unpublishes the "impedance_Imaginary_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "impedance_Imaginary_B" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_impedance_Imaginary_B() {
		_publishAttributeNameSet.remove( "impedance_Imaginary_B" );
	}
	
	/**
	* Subscribes a federate to the "impedance_Imaginary_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "impedance_Imaginary_B" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_impedance_Imaginary_B() {
		_subscribeAttributeNameSet.add( "impedance_Imaginary_B" );
	}

	/**
	* Unsubscribes a federate from the "impedance_Imaginary_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "impedance_Imaginary_B" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_impedance_Imaginary_B() {
		_subscribeAttributeNameSet.remove( "impedance_Imaginary_B" );
	}
	
	
	/**
	* Publishes the "impedance_Imaginary_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "impedance_Imaginary_C" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_impedance_Imaginary_C() {
		_publishAttributeNameSet.add( "impedance_Imaginary_C" );
	}

	/**
	* Unpublishes the "impedance_Imaginary_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "impedance_Imaginary_C" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_impedance_Imaginary_C() {
		_publishAttributeNameSet.remove( "impedance_Imaginary_C" );
	}
	
	/**
	* Subscribes a federate to the "impedance_Imaginary_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "impedance_Imaginary_C" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_impedance_Imaginary_C() {
		_subscribeAttributeNameSet.add( "impedance_Imaginary_C" );
	}

	/**
	* Unsubscribes a federate from the "impedance_Imaginary_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "impedance_Imaginary_C" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_impedance_Imaginary_C() {
		_subscribeAttributeNameSet.remove( "impedance_Imaginary_C" );
	}
	
	
	/**
	* Publishes the "impedance_Real_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "impedance_Real_A" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_impedance_Real_A() {
		_publishAttributeNameSet.add( "impedance_Real_A" );
	}

	/**
	* Unpublishes the "impedance_Real_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "impedance_Real_A" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_impedance_Real_A() {
		_publishAttributeNameSet.remove( "impedance_Real_A" );
	}
	
	/**
	* Subscribes a federate to the "impedance_Real_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "impedance_Real_A" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_impedance_Real_A() {
		_subscribeAttributeNameSet.add( "impedance_Real_A" );
	}

	/**
	* Unsubscribes a federate from the "impedance_Real_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "impedance_Real_A" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_impedance_Real_A() {
		_subscribeAttributeNameSet.remove( "impedance_Real_A" );
	}
	
	
	/**
	* Publishes the "impedance_Real_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "impedance_Real_B" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_impedance_Real_B() {
		_publishAttributeNameSet.add( "impedance_Real_B" );
	}

	/**
	* Unpublishes the "impedance_Real_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "impedance_Real_B" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_impedance_Real_B() {
		_publishAttributeNameSet.remove( "impedance_Real_B" );
	}
	
	/**
	* Subscribes a federate to the "impedance_Real_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "impedance_Real_B" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_impedance_Real_B() {
		_subscribeAttributeNameSet.add( "impedance_Real_B" );
	}

	/**
	* Unsubscribes a federate from the "impedance_Real_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "impedance_Real_B" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_impedance_Real_B() {
		_subscribeAttributeNameSet.remove( "impedance_Real_B" );
	}
	
	
	/**
	* Publishes the "impedance_Real_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "impedance_Real_C" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_impedance_Real_C() {
		_publishAttributeNameSet.add( "impedance_Real_C" );
	}

	/**
	* Unpublishes the "impedance_Real_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "impedance_Real_C" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_impedance_Real_C() {
		_publishAttributeNameSet.remove( "impedance_Real_C" );
	}
	
	/**
	* Subscribes a federate to the "impedance_Real_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "impedance_Real_C" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_impedance_Real_C() {
		_subscribeAttributeNameSet.add( "impedance_Real_C" );
	}

	/**
	* Unsubscribes a federate from the "impedance_Real_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "impedance_Real_C" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_impedance_Real_C() {
		_subscribeAttributeNameSet.remove( "impedance_Real_C" );
	}
	
	
	/**
	* Publishes the "phases" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "phases" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_phases() {
		_publishAttributeNameSet.add( "phases" );
	}

	/**
	* Unpublishes the "phases" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "phases" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_phases() {
		_publishAttributeNameSet.remove( "phases" );
	}
	
	/**
	* Subscribes a federate to the "phases" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "phases" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_phases() {
		_subscribeAttributeNameSet.add( "phases" );
	}

	/**
	* Unsubscribes a federate from the "phases" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "phases" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_phases() {
		_subscribeAttributeNameSet.remove( "phases" );
	}
	
	
	/**
	* Publishes the "power_Imaginary_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "power_Imaginary_A" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_power_Imaginary_A() {
		_publishAttributeNameSet.add( "power_Imaginary_A" );
	}

	/**
	* Unpublishes the "power_Imaginary_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "power_Imaginary_A" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_power_Imaginary_A() {
		_publishAttributeNameSet.remove( "power_Imaginary_A" );
	}
	
	/**
	* Subscribes a federate to the "power_Imaginary_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "power_Imaginary_A" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_power_Imaginary_A() {
		_subscribeAttributeNameSet.add( "power_Imaginary_A" );
	}

	/**
	* Unsubscribes a federate from the "power_Imaginary_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "power_Imaginary_A" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_power_Imaginary_A() {
		_subscribeAttributeNameSet.remove( "power_Imaginary_A" );
	}
	
	
	/**
	* Publishes the "power_Imaginary_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "power_Imaginary_B" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_power_Imaginary_B() {
		_publishAttributeNameSet.add( "power_Imaginary_B" );
	}

	/**
	* Unpublishes the "power_Imaginary_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "power_Imaginary_B" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_power_Imaginary_B() {
		_publishAttributeNameSet.remove( "power_Imaginary_B" );
	}
	
	/**
	* Subscribes a federate to the "power_Imaginary_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "power_Imaginary_B" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_power_Imaginary_B() {
		_subscribeAttributeNameSet.add( "power_Imaginary_B" );
	}

	/**
	* Unsubscribes a federate from the "power_Imaginary_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "power_Imaginary_B" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_power_Imaginary_B() {
		_subscribeAttributeNameSet.remove( "power_Imaginary_B" );
	}
	
	
	/**
	* Publishes the "power_Imaginary_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "power_Imaginary_C" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_power_Imaginary_C() {
		_publishAttributeNameSet.add( "power_Imaginary_C" );
	}

	/**
	* Unpublishes the "power_Imaginary_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "power_Imaginary_C" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_power_Imaginary_C() {
		_publishAttributeNameSet.remove( "power_Imaginary_C" );
	}
	
	/**
	* Subscribes a federate to the "power_Imaginary_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "power_Imaginary_C" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_power_Imaginary_C() {
		_subscribeAttributeNameSet.add( "power_Imaginary_C" );
	}

	/**
	* Unsubscribes a federate from the "power_Imaginary_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "power_Imaginary_C" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_power_Imaginary_C() {
		_subscribeAttributeNameSet.remove( "power_Imaginary_C" );
	}
	
	
	/**
	* Publishes the "power_Real_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "power_Real_A" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_power_Real_A() {
		_publishAttributeNameSet.add( "power_Real_A" );
	}

	/**
	* Unpublishes the "power_Real_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "power_Real_A" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_power_Real_A() {
		_publishAttributeNameSet.remove( "power_Real_A" );
	}
	
	/**
	* Subscribes a federate to the "power_Real_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "power_Real_A" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_power_Real_A() {
		_subscribeAttributeNameSet.add( "power_Real_A" );
	}

	/**
	* Unsubscribes a federate from the "power_Real_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "power_Real_A" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_power_Real_A() {
		_subscribeAttributeNameSet.remove( "power_Real_A" );
	}
	
	
	/**
	* Publishes the "power_Real_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "power_Real_B" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_power_Real_B() {
		_publishAttributeNameSet.add( "power_Real_B" );
	}

	/**
	* Unpublishes the "power_Real_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "power_Real_B" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_power_Real_B() {
		_publishAttributeNameSet.remove( "power_Real_B" );
	}
	
	/**
	* Subscribes a federate to the "power_Real_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "power_Real_B" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_power_Real_B() {
		_subscribeAttributeNameSet.add( "power_Real_B" );
	}

	/**
	* Unsubscribes a federate from the "power_Real_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "power_Real_B" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_power_Real_B() {
		_subscribeAttributeNameSet.remove( "power_Real_B" );
	}
	
	
	/**
	* Publishes the "power_Real_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "power_Real_C" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_power_Real_C() {
		_publishAttributeNameSet.add( "power_Real_C" );
	}

	/**
	* Unpublishes the "power_Real_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "power_Real_C" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_power_Real_C() {
		_publishAttributeNameSet.remove( "power_Real_C" );
	}
	
	/**
	* Subscribes a federate to the "power_Real_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "power_Real_C" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_power_Real_C() {
		_subscribeAttributeNameSet.add( "power_Real_C" );
	}

	/**
	* Unsubscribes a federate from the "power_Real_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "power_Real_C" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_power_Real_C() {
		_subscribeAttributeNameSet.remove( "power_Real_C" );
	}
	
	
	/**
	* Publishes the "status" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "status" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_status() {
		_publishAttributeNameSet.add( "status" );
	}

	/**
	* Unpublishes the "status" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "status" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_status() {
		_publishAttributeNameSet.remove( "status" );
	}
	
	/**
	* Subscribes a federate to the "status" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "status" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_status() {
		_subscribeAttributeNameSet.add( "status" );
	}

	/**
	* Unsubscribes a federate from the "status" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "status" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_status() {
		_subscribeAttributeNameSet.remove( "status" );
	}
	
	
	/**
	* Publishes the "voltage_Imaginary_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "voltage_Imaginary_A" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_voltage_Imaginary_A() {
		_publishAttributeNameSet.add( "voltage_Imaginary_A" );
	}

	/**
	* Unpublishes the "voltage_Imaginary_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "voltage_Imaginary_A" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_voltage_Imaginary_A() {
		_publishAttributeNameSet.remove( "voltage_Imaginary_A" );
	}
	
	/**
	* Subscribes a federate to the "voltage_Imaginary_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "voltage_Imaginary_A" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_voltage_Imaginary_A() {
		_subscribeAttributeNameSet.add( "voltage_Imaginary_A" );
	}

	/**
	* Unsubscribes a federate from the "voltage_Imaginary_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "voltage_Imaginary_A" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_voltage_Imaginary_A() {
		_subscribeAttributeNameSet.remove( "voltage_Imaginary_A" );
	}
	
	
	/**
	* Publishes the "voltage_Imaginary_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "voltage_Imaginary_B" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_voltage_Imaginary_B() {
		_publishAttributeNameSet.add( "voltage_Imaginary_B" );
	}

	/**
	* Unpublishes the "voltage_Imaginary_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "voltage_Imaginary_B" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_voltage_Imaginary_B() {
		_publishAttributeNameSet.remove( "voltage_Imaginary_B" );
	}
	
	/**
	* Subscribes a federate to the "voltage_Imaginary_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "voltage_Imaginary_B" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_voltage_Imaginary_B() {
		_subscribeAttributeNameSet.add( "voltage_Imaginary_B" );
	}

	/**
	* Unsubscribes a federate from the "voltage_Imaginary_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "voltage_Imaginary_B" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_voltage_Imaginary_B() {
		_subscribeAttributeNameSet.remove( "voltage_Imaginary_B" );
	}
	
	
	/**
	* Publishes the "voltage_Imaginary_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "voltage_Imaginary_C" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_voltage_Imaginary_C() {
		_publishAttributeNameSet.add( "voltage_Imaginary_C" );
	}

	/**
	* Unpublishes the "voltage_Imaginary_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "voltage_Imaginary_C" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_voltage_Imaginary_C() {
		_publishAttributeNameSet.remove( "voltage_Imaginary_C" );
	}
	
	/**
	* Subscribes a federate to the "voltage_Imaginary_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "voltage_Imaginary_C" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_voltage_Imaginary_C() {
		_subscribeAttributeNameSet.add( "voltage_Imaginary_C" );
	}

	/**
	* Unsubscribes a federate from the "voltage_Imaginary_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "voltage_Imaginary_C" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_voltage_Imaginary_C() {
		_subscribeAttributeNameSet.remove( "voltage_Imaginary_C" );
	}
	
	
	/**
	* Publishes the "voltage_Real_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "voltage_Real_A" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_voltage_Real_A() {
		_publishAttributeNameSet.add( "voltage_Real_A" );
	}

	/**
	* Unpublishes the "voltage_Real_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "voltage_Real_A" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_voltage_Real_A() {
		_publishAttributeNameSet.remove( "voltage_Real_A" );
	}
	
	/**
	* Subscribes a federate to the "voltage_Real_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "voltage_Real_A" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_voltage_Real_A() {
		_subscribeAttributeNameSet.add( "voltage_Real_A" );
	}

	/**
	* Unsubscribes a federate from the "voltage_Real_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "voltage_Real_A" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_voltage_Real_A() {
		_subscribeAttributeNameSet.remove( "voltage_Real_A" );
	}
	
	
	/**
	* Publishes the "voltage_Real_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "voltage_Real_B" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_voltage_Real_B() {
		_publishAttributeNameSet.add( "voltage_Real_B" );
	}

	/**
	* Unpublishes the "voltage_Real_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "voltage_Real_B" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_voltage_Real_B() {
		_publishAttributeNameSet.remove( "voltage_Real_B" );
	}
	
	/**
	* Subscribes a federate to the "voltage_Real_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "voltage_Real_B" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_voltage_Real_B() {
		_subscribeAttributeNameSet.add( "voltage_Real_B" );
	}

	/**
	* Unsubscribes a federate from the "voltage_Real_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "voltage_Real_B" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_voltage_Real_B() {
		_subscribeAttributeNameSet.remove( "voltage_Real_B" );
	}
	
	
	/**
	* Publishes the "voltage_Real_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "voltage_Real_C" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_voltage_Real_C() {
		_publishAttributeNameSet.add( "voltage_Real_C" );
	}

	/**
	* Unpublishes the "voltage_Real_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "voltage_Real_C" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_voltage_Real_C() {
		_publishAttributeNameSet.remove( "voltage_Real_C" );
	}
	
	/**
	* Subscribes a federate to the "voltage_Real_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "voltage_Real_C" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_voltage_Real_C() {
		_subscribeAttributeNameSet.add( "voltage_Real_C" );
	}

	/**
	* Unsubscribes a federate from the "voltage_Real_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "voltage_Real_C" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_voltage_Real_C() {
		_subscribeAttributeNameSet.remove( "voltage_Real_C" );
	}
	

	
	
	private Attribute< Float > _current_Imaginary_A =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "current_Imaginary_A" attribute to "value" for this object.
	*
	* @param value the new value for the "current_Imaginary_A" attribute
	*/
	public void set_current_Imaginary_A( float value ) {
		_current_Imaginary_A.setValue( value );
		_current_Imaginary_A.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "current_Imaginary_A" attribute of this object.
	*
	* @return the value of the "current_Imaginary_A" attribute
	*/
	public float get_current_Imaginary_A() {
		return _current_Imaginary_A.getValue();
	}
	
	/**
	* Returns the current timestamp of the "current_Imaginary_A" attribute of this object.
	* 
	* @return the current timestamp of the "current_Imaginary_A" attribute
	*/
	public double get_current_Imaginary_A_time() {
		return _current_Imaginary_A.getTime();
	}
	
	
	private Attribute< Float > _current_Imaginary_B =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "current_Imaginary_B" attribute to "value" for this object.
	*
	* @param value the new value for the "current_Imaginary_B" attribute
	*/
	public void set_current_Imaginary_B( float value ) {
		_current_Imaginary_B.setValue( value );
		_current_Imaginary_B.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "current_Imaginary_B" attribute of this object.
	*
	* @return the value of the "current_Imaginary_B" attribute
	*/
	public float get_current_Imaginary_B() {
		return _current_Imaginary_B.getValue();
	}
	
	/**
	* Returns the current timestamp of the "current_Imaginary_B" attribute of this object.
	* 
	* @return the current timestamp of the "current_Imaginary_B" attribute
	*/
	public double get_current_Imaginary_B_time() {
		return _current_Imaginary_B.getTime();
	}
	
	
	private Attribute< Float > _current_Imaginary_C =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "current_Imaginary_C" attribute to "value" for this object.
	*
	* @param value the new value for the "current_Imaginary_C" attribute
	*/
	public void set_current_Imaginary_C( float value ) {
		_current_Imaginary_C.setValue( value );
		_current_Imaginary_C.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "current_Imaginary_C" attribute of this object.
	*
	* @return the value of the "current_Imaginary_C" attribute
	*/
	public float get_current_Imaginary_C() {
		return _current_Imaginary_C.getValue();
	}
	
	/**
	* Returns the current timestamp of the "current_Imaginary_C" attribute of this object.
	* 
	* @return the current timestamp of the "current_Imaginary_C" attribute
	*/
	public double get_current_Imaginary_C_time() {
		return _current_Imaginary_C.getTime();
	}
	
	
	private Attribute< Float > _current_Real_A =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "current_Real_A" attribute to "value" for this object.
	*
	* @param value the new value for the "current_Real_A" attribute
	*/
	public void set_current_Real_A( float value ) {
		_current_Real_A.setValue( value );
		_current_Real_A.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "current_Real_A" attribute of this object.
	*
	* @return the value of the "current_Real_A" attribute
	*/
	public float get_current_Real_A() {
		return _current_Real_A.getValue();
	}
	
	/**
	* Returns the current timestamp of the "current_Real_A" attribute of this object.
	* 
	* @return the current timestamp of the "current_Real_A" attribute
	*/
	public double get_current_Real_A_time() {
		return _current_Real_A.getTime();
	}
	
	
	private Attribute< Float > _current_Real_B =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "current_Real_B" attribute to "value" for this object.
	*
	* @param value the new value for the "current_Real_B" attribute
	*/
	public void set_current_Real_B( float value ) {
		_current_Real_B.setValue( value );
		_current_Real_B.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "current_Real_B" attribute of this object.
	*
	* @return the value of the "current_Real_B" attribute
	*/
	public float get_current_Real_B() {
		return _current_Real_B.getValue();
	}
	
	/**
	* Returns the current timestamp of the "current_Real_B" attribute of this object.
	* 
	* @return the current timestamp of the "current_Real_B" attribute
	*/
	public double get_current_Real_B_time() {
		return _current_Real_B.getTime();
	}
	
	
	private Attribute< Float > _current_Real_C =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "current_Real_C" attribute to "value" for this object.
	*
	* @param value the new value for the "current_Real_C" attribute
	*/
	public void set_current_Real_C( float value ) {
		_current_Real_C.setValue( value );
		_current_Real_C.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "current_Real_C" attribute of this object.
	*
	* @return the value of the "current_Real_C" attribute
	*/
	public float get_current_Real_C() {
		return _current_Real_C.getValue();
	}
	
	/**
	* Returns the current timestamp of the "current_Real_C" attribute of this object.
	* 
	* @return the current timestamp of the "current_Real_C" attribute
	*/
	public double get_current_Real_C_time() {
		return _current_Real_C.getTime();
	}
	
	
	private Attribute< String > _gridNodeId =
 		new Attribute< String >(  new String( "" )  );
	
	/**
	* Set the value of the "gridNodeId" attribute to "value" for this object.
	*
	* @param value the new value for the "gridNodeId" attribute
	*/
	public void set_gridNodeId( String value ) {
		_gridNodeId.setValue( value );
		_gridNodeId.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "gridNodeId" attribute of this object.
	*
	* @return the value of the "gridNodeId" attribute
	*/
	public String get_gridNodeId() {
		return _gridNodeId.getValue();
	}
	
	/**
	* Returns the current timestamp of the "gridNodeId" attribute of this object.
	* 
	* @return the current timestamp of the "gridNodeId" attribute
	*/
	public double get_gridNodeId_time() {
		return _gridNodeId.getTime();
	}
	
	
	private Attribute< Float > _impedance_Imaginary_A =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "impedance_Imaginary_A" attribute to "value" for this object.
	*
	* @param value the new value for the "impedance_Imaginary_A" attribute
	*/
	public void set_impedance_Imaginary_A( float value ) {
		_impedance_Imaginary_A.setValue( value );
		_impedance_Imaginary_A.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "impedance_Imaginary_A" attribute of this object.
	*
	* @return the value of the "impedance_Imaginary_A" attribute
	*/
	public float get_impedance_Imaginary_A() {
		return _impedance_Imaginary_A.getValue();
	}
	
	/**
	* Returns the current timestamp of the "impedance_Imaginary_A" attribute of this object.
	* 
	* @return the current timestamp of the "impedance_Imaginary_A" attribute
	*/
	public double get_impedance_Imaginary_A_time() {
		return _impedance_Imaginary_A.getTime();
	}
	
	
	private Attribute< Float > _impedance_Imaginary_B =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "impedance_Imaginary_B" attribute to "value" for this object.
	*
	* @param value the new value for the "impedance_Imaginary_B" attribute
	*/
	public void set_impedance_Imaginary_B( float value ) {
		_impedance_Imaginary_B.setValue( value );
		_impedance_Imaginary_B.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "impedance_Imaginary_B" attribute of this object.
	*
	* @return the value of the "impedance_Imaginary_B" attribute
	*/
	public float get_impedance_Imaginary_B() {
		return _impedance_Imaginary_B.getValue();
	}
	
	/**
	* Returns the current timestamp of the "impedance_Imaginary_B" attribute of this object.
	* 
	* @return the current timestamp of the "impedance_Imaginary_B" attribute
	*/
	public double get_impedance_Imaginary_B_time() {
		return _impedance_Imaginary_B.getTime();
	}
	
	
	private Attribute< Float > _impedance_Imaginary_C =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "impedance_Imaginary_C" attribute to "value" for this object.
	*
	* @param value the new value for the "impedance_Imaginary_C" attribute
	*/
	public void set_impedance_Imaginary_C( float value ) {
		_impedance_Imaginary_C.setValue( value );
		_impedance_Imaginary_C.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "impedance_Imaginary_C" attribute of this object.
	*
	* @return the value of the "impedance_Imaginary_C" attribute
	*/
	public float get_impedance_Imaginary_C() {
		return _impedance_Imaginary_C.getValue();
	}
	
	/**
	* Returns the current timestamp of the "impedance_Imaginary_C" attribute of this object.
	* 
	* @return the current timestamp of the "impedance_Imaginary_C" attribute
	*/
	public double get_impedance_Imaginary_C_time() {
		return _impedance_Imaginary_C.getTime();
	}
	
	
	private Attribute< Float > _impedance_Real_A =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "impedance_Real_A" attribute to "value" for this object.
	*
	* @param value the new value for the "impedance_Real_A" attribute
	*/
	public void set_impedance_Real_A( float value ) {
		_impedance_Real_A.setValue( value );
		_impedance_Real_A.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "impedance_Real_A" attribute of this object.
	*
	* @return the value of the "impedance_Real_A" attribute
	*/
	public float get_impedance_Real_A() {
		return _impedance_Real_A.getValue();
	}
	
	/**
	* Returns the current timestamp of the "impedance_Real_A" attribute of this object.
	* 
	* @return the current timestamp of the "impedance_Real_A" attribute
	*/
	public double get_impedance_Real_A_time() {
		return _impedance_Real_A.getTime();
	}
	
	
	private Attribute< Float > _impedance_Real_B =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "impedance_Real_B" attribute to "value" for this object.
	*
	* @param value the new value for the "impedance_Real_B" attribute
	*/
	public void set_impedance_Real_B( float value ) {
		_impedance_Real_B.setValue( value );
		_impedance_Real_B.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "impedance_Real_B" attribute of this object.
	*
	* @return the value of the "impedance_Real_B" attribute
	*/
	public float get_impedance_Real_B() {
		return _impedance_Real_B.getValue();
	}
	
	/**
	* Returns the current timestamp of the "impedance_Real_B" attribute of this object.
	* 
	* @return the current timestamp of the "impedance_Real_B" attribute
	*/
	public double get_impedance_Real_B_time() {
		return _impedance_Real_B.getTime();
	}
	
	
	private Attribute< Float > _impedance_Real_C =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "impedance_Real_C" attribute to "value" for this object.
	*
	* @param value the new value for the "impedance_Real_C" attribute
	*/
	public void set_impedance_Real_C( float value ) {
		_impedance_Real_C.setValue( value );
		_impedance_Real_C.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "impedance_Real_C" attribute of this object.
	*
	* @return the value of the "impedance_Real_C" attribute
	*/
	public float get_impedance_Real_C() {
		return _impedance_Real_C.getValue();
	}
	
	/**
	* Returns the current timestamp of the "impedance_Real_C" attribute of this object.
	* 
	* @return the current timestamp of the "impedance_Real_C" attribute
	*/
	public double get_impedance_Real_C_time() {
		return _impedance_Real_C.getTime();
	}
	
	
	private Attribute< String > _phases =
 		new Attribute< String >(  new String( "" )  );
	
	/**
	* Set the value of the "phases" attribute to "value" for this object.
	*
	* @param value the new value for the "phases" attribute
	*/
	public void set_phases( String value ) {
		_phases.setValue( value );
		_phases.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "phases" attribute of this object.
	*
	* @return the value of the "phases" attribute
	*/
	public String get_phases() {
		return _phases.getValue();
	}
	
	/**
	* Returns the current timestamp of the "phases" attribute of this object.
	* 
	* @return the current timestamp of the "phases" attribute
	*/
	public double get_phases_time() {
		return _phases.getTime();
	}
	
	
	private Attribute< Float > _power_Imaginary_A =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "power_Imaginary_A" attribute to "value" for this object.
	*
	* @param value the new value for the "power_Imaginary_A" attribute
	*/
	public void set_power_Imaginary_A( float value ) {
		_power_Imaginary_A.setValue( value );
		_power_Imaginary_A.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "power_Imaginary_A" attribute of this object.
	*
	* @return the value of the "power_Imaginary_A" attribute
	*/
	public float get_power_Imaginary_A() {
		return _power_Imaginary_A.getValue();
	}
	
	/**
	* Returns the current timestamp of the "power_Imaginary_A" attribute of this object.
	* 
	* @return the current timestamp of the "power_Imaginary_A" attribute
	*/
	public double get_power_Imaginary_A_time() {
		return _power_Imaginary_A.getTime();
	}
	
	
	private Attribute< Float > _power_Imaginary_B =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "power_Imaginary_B" attribute to "value" for this object.
	*
	* @param value the new value for the "power_Imaginary_B" attribute
	*/
	public void set_power_Imaginary_B( float value ) {
		_power_Imaginary_B.setValue( value );
		_power_Imaginary_B.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "power_Imaginary_B" attribute of this object.
	*
	* @return the value of the "power_Imaginary_B" attribute
	*/
	public float get_power_Imaginary_B() {
		return _power_Imaginary_B.getValue();
	}
	
	/**
	* Returns the current timestamp of the "power_Imaginary_B" attribute of this object.
	* 
	* @return the current timestamp of the "power_Imaginary_B" attribute
	*/
	public double get_power_Imaginary_B_time() {
		return _power_Imaginary_B.getTime();
	}
	
	
	private Attribute< Float > _power_Imaginary_C =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "power_Imaginary_C" attribute to "value" for this object.
	*
	* @param value the new value for the "power_Imaginary_C" attribute
	*/
	public void set_power_Imaginary_C( float value ) {
		_power_Imaginary_C.setValue( value );
		_power_Imaginary_C.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "power_Imaginary_C" attribute of this object.
	*
	* @return the value of the "power_Imaginary_C" attribute
	*/
	public float get_power_Imaginary_C() {
		return _power_Imaginary_C.getValue();
	}
	
	/**
	* Returns the current timestamp of the "power_Imaginary_C" attribute of this object.
	* 
	* @return the current timestamp of the "power_Imaginary_C" attribute
	*/
	public double get_power_Imaginary_C_time() {
		return _power_Imaginary_C.getTime();
	}
	
	
	private Attribute< Float > _power_Real_A =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "power_Real_A" attribute to "value" for this object.
	*
	* @param value the new value for the "power_Real_A" attribute
	*/
	public void set_power_Real_A( float value ) {
		_power_Real_A.setValue( value );
		_power_Real_A.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "power_Real_A" attribute of this object.
	*
	* @return the value of the "power_Real_A" attribute
	*/
	public float get_power_Real_A() {
		return _power_Real_A.getValue();
	}
	
	/**
	* Returns the current timestamp of the "power_Real_A" attribute of this object.
	* 
	* @return the current timestamp of the "power_Real_A" attribute
	*/
	public double get_power_Real_A_time() {
		return _power_Real_A.getTime();
	}
	
	
	private Attribute< Float > _power_Real_B =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "power_Real_B" attribute to "value" for this object.
	*
	* @param value the new value for the "power_Real_B" attribute
	*/
	public void set_power_Real_B( float value ) {
		_power_Real_B.setValue( value );
		_power_Real_B.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "power_Real_B" attribute of this object.
	*
	* @return the value of the "power_Real_B" attribute
	*/
	public float get_power_Real_B() {
		return _power_Real_B.getValue();
	}
	
	/**
	* Returns the current timestamp of the "power_Real_B" attribute of this object.
	* 
	* @return the current timestamp of the "power_Real_B" attribute
	*/
	public double get_power_Real_B_time() {
		return _power_Real_B.getTime();
	}
	
	
	private Attribute< Float > _power_Real_C =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "power_Real_C" attribute to "value" for this object.
	*
	* @param value the new value for the "power_Real_C" attribute
	*/
	public void set_power_Real_C( float value ) {
		_power_Real_C.setValue( value );
		_power_Real_C.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "power_Real_C" attribute of this object.
	*
	* @return the value of the "power_Real_C" attribute
	*/
	public float get_power_Real_C() {
		return _power_Real_C.getValue();
	}
	
	/**
	* Returns the current timestamp of the "power_Real_C" attribute of this object.
	* 
	* @return the current timestamp of the "power_Real_C" attribute
	*/
	public double get_power_Real_C_time() {
		return _power_Real_C.getTime();
	}
	
	
	private Attribute< Boolean > _status =
 		new Attribute< Boolean >(  new Boolean( false )  );
	
	/**
	* Set the value of the "status" attribute to "value" for this object.
	*
	* @param value the new value for the "status" attribute
	*/
	public void set_status( boolean value ) {
		_status.setValue( value );
		_status.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "status" attribute of this object.
	*
	* @return the value of the "status" attribute
	*/
	public boolean get_status() {
		return _status.getValue();
	}
	
	/**
	* Returns the current timestamp of the "status" attribute of this object.
	* 
	* @return the current timestamp of the "status" attribute
	*/
	public double get_status_time() {
		return _status.getTime();
	}
	
	
	private Attribute< Float > _voltage_Imaginary_A =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "voltage_Imaginary_A" attribute to "value" for this object.
	*
	* @param value the new value for the "voltage_Imaginary_A" attribute
	*/
	public void set_voltage_Imaginary_A( float value ) {
		_voltage_Imaginary_A.setValue( value );
		_voltage_Imaginary_A.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "voltage_Imaginary_A" attribute of this object.
	*
	* @return the value of the "voltage_Imaginary_A" attribute
	*/
	public float get_voltage_Imaginary_A() {
		return _voltage_Imaginary_A.getValue();
	}
	
	/**
	* Returns the current timestamp of the "voltage_Imaginary_A" attribute of this object.
	* 
	* @return the current timestamp of the "voltage_Imaginary_A" attribute
	*/
	public double get_voltage_Imaginary_A_time() {
		return _voltage_Imaginary_A.getTime();
	}
	
	
	private Attribute< Float > _voltage_Imaginary_B =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "voltage_Imaginary_B" attribute to "value" for this object.
	*
	* @param value the new value for the "voltage_Imaginary_B" attribute
	*/
	public void set_voltage_Imaginary_B( float value ) {
		_voltage_Imaginary_B.setValue( value );
		_voltage_Imaginary_B.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "voltage_Imaginary_B" attribute of this object.
	*
	* @return the value of the "voltage_Imaginary_B" attribute
	*/
	public float get_voltage_Imaginary_B() {
		return _voltage_Imaginary_B.getValue();
	}
	
	/**
	* Returns the current timestamp of the "voltage_Imaginary_B" attribute of this object.
	* 
	* @return the current timestamp of the "voltage_Imaginary_B" attribute
	*/
	public double get_voltage_Imaginary_B_time() {
		return _voltage_Imaginary_B.getTime();
	}
	
	
	private Attribute< Float > _voltage_Imaginary_C =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "voltage_Imaginary_C" attribute to "value" for this object.
	*
	* @param value the new value for the "voltage_Imaginary_C" attribute
	*/
	public void set_voltage_Imaginary_C( float value ) {
		_voltage_Imaginary_C.setValue( value );
		_voltage_Imaginary_C.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "voltage_Imaginary_C" attribute of this object.
	*
	* @return the value of the "voltage_Imaginary_C" attribute
	*/
	public float get_voltage_Imaginary_C() {
		return _voltage_Imaginary_C.getValue();
	}
	
	/**
	* Returns the current timestamp of the "voltage_Imaginary_C" attribute of this object.
	* 
	* @return the current timestamp of the "voltage_Imaginary_C" attribute
	*/
	public double get_voltage_Imaginary_C_time() {
		return _voltage_Imaginary_C.getTime();
	}
	
	
	private Attribute< Float > _voltage_Real_A =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "voltage_Real_A" attribute to "value" for this object.
	*
	* @param value the new value for the "voltage_Real_A" attribute
	*/
	public void set_voltage_Real_A( float value ) {
		_voltage_Real_A.setValue( value );
		_voltage_Real_A.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "voltage_Real_A" attribute of this object.
	*
	* @return the value of the "voltage_Real_A" attribute
	*/
	public float get_voltage_Real_A() {
		return _voltage_Real_A.getValue();
	}
	
	/**
	* Returns the current timestamp of the "voltage_Real_A" attribute of this object.
	* 
	* @return the current timestamp of the "voltage_Real_A" attribute
	*/
	public double get_voltage_Real_A_time() {
		return _voltage_Real_A.getTime();
	}
	
	
	private Attribute< Float > _voltage_Real_B =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "voltage_Real_B" attribute to "value" for this object.
	*
	* @param value the new value for the "voltage_Real_B" attribute
	*/
	public void set_voltage_Real_B( float value ) {
		_voltage_Real_B.setValue( value );
		_voltage_Real_B.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "voltage_Real_B" attribute of this object.
	*
	* @return the value of the "voltage_Real_B" attribute
	*/
	public float get_voltage_Real_B() {
		return _voltage_Real_B.getValue();
	}
	
	/**
	* Returns the current timestamp of the "voltage_Real_B" attribute of this object.
	* 
	* @return the current timestamp of the "voltage_Real_B" attribute
	*/
	public double get_voltage_Real_B_time() {
		return _voltage_Real_B.getTime();
	}
	
	
	private Attribute< Float > _voltage_Real_C =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "voltage_Real_C" attribute to "value" for this object.
	*
	* @param value the new value for the "voltage_Real_C" attribute
	*/
	public void set_voltage_Real_C( float value ) {
		_voltage_Real_C.setValue( value );
		_voltage_Real_C.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "voltage_Real_C" attribute of this object.
	*
	* @return the value of the "voltage_Real_C" attribute
	*/
	public float get_voltage_Real_C() {
		return _voltage_Real_C.getValue();
	}
	
	/**
	* Returns the current timestamp of the "voltage_Real_C" attribute of this object.
	* 
	* @return the current timestamp of the "voltage_Real_C" attribute
	*/
	public double get_voltage_Real_C_time() {
		return _voltage_Real_C.getTime();
	}
	


	protected resourcesPhysicalStatus( ReflectedAttributes datamemberMap, boolean initFlag ) {
		super( datamemberMap, false );
		if ( initFlag ) setAttributes( datamemberMap );
	}
	
	protected resourcesPhysicalStatus( ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
		super( datamemberMap, logicalTime, false );
		if ( initFlag ) setAttributes( datamemberMap );
	}


	/**
	* Creates an instance of the resourcesPhysicalStatus object class, using
	* "datamemberMap" to initialize its attribute values.
	* "datamemberMap" is usually acquired as an argument to an RTI federate
	* callback method, such as "receiveInteraction".
	*
	* @param datamemberMap data structure containing initial values for the
	* attributes of this new resourcesPhysicalStatus object class instance
	*/
	public resourcesPhysicalStatus( ReflectedAttributes datamemberMap ) {
		this( datamemberMap, true );
	}
	
	/**
	* Like {@link #resourcesPhysicalStatus( ReflectedAttributes datamemberMap )}, except this
	* new resourcesPhysicalStatus object class instance is given a timestamp of
	* "logicalTime".
	*
	* @param datamemberMap data structure containing initial values for the
	* attributes of this new resourcesPhysicalStatus object class instance
	* @param logicalTime timestamp for this new resourcesPhysicalStatus object class
	* instance
	*/
	public resourcesPhysicalStatus( ReflectedAttributes datamemberMap, LogicalTime logicalTime ) {
		this( datamemberMap, logicalTime, true );
	}

	/**
	* Creates a new resourcesPhysicalStatus object class instance that is a duplicate
	* of the instance referred to by resourcesPhysicalStatus_var.
	*
	* @param resourcesPhysicalStatus_var resourcesPhysicalStatus object class instance of which
	* this newly created resourcesPhysicalStatus object class instance will be a
	* duplicate
	*/
	public resourcesPhysicalStatus( resourcesPhysicalStatus resourcesPhysicalStatus_var ) {
		super( resourcesPhysicalStatus_var );
		
		
		set_current_Imaginary_A( resourcesPhysicalStatus_var.get_current_Imaginary_A() );
		set_current_Imaginary_B( resourcesPhysicalStatus_var.get_current_Imaginary_B() );
		set_current_Imaginary_C( resourcesPhysicalStatus_var.get_current_Imaginary_C() );
		set_current_Real_A( resourcesPhysicalStatus_var.get_current_Real_A() );
		set_current_Real_B( resourcesPhysicalStatus_var.get_current_Real_B() );
		set_current_Real_C( resourcesPhysicalStatus_var.get_current_Real_C() );
		set_gridNodeId( resourcesPhysicalStatus_var.get_gridNodeId() );
		set_impedance_Imaginary_A( resourcesPhysicalStatus_var.get_impedance_Imaginary_A() );
		set_impedance_Imaginary_B( resourcesPhysicalStatus_var.get_impedance_Imaginary_B() );
		set_impedance_Imaginary_C( resourcesPhysicalStatus_var.get_impedance_Imaginary_C() );
		set_impedance_Real_A( resourcesPhysicalStatus_var.get_impedance_Real_A() );
		set_impedance_Real_B( resourcesPhysicalStatus_var.get_impedance_Real_B() );
		set_impedance_Real_C( resourcesPhysicalStatus_var.get_impedance_Real_C() );
		set_phases( resourcesPhysicalStatus_var.get_phases() );
		set_power_Imaginary_A( resourcesPhysicalStatus_var.get_power_Imaginary_A() );
		set_power_Imaginary_B( resourcesPhysicalStatus_var.get_power_Imaginary_B() );
		set_power_Imaginary_C( resourcesPhysicalStatus_var.get_power_Imaginary_C() );
		set_power_Real_A( resourcesPhysicalStatus_var.get_power_Real_A() );
		set_power_Real_B( resourcesPhysicalStatus_var.get_power_Real_B() );
		set_power_Real_C( resourcesPhysicalStatus_var.get_power_Real_C() );
		set_status( resourcesPhysicalStatus_var.get_status() );
		set_voltage_Imaginary_A( resourcesPhysicalStatus_var.get_voltage_Imaginary_A() );
		set_voltage_Imaginary_B( resourcesPhysicalStatus_var.get_voltage_Imaginary_B() );
		set_voltage_Imaginary_C( resourcesPhysicalStatus_var.get_voltage_Imaginary_C() );
		set_voltage_Real_A( resourcesPhysicalStatus_var.get_voltage_Real_A() );
		set_voltage_Real_B( resourcesPhysicalStatus_var.get_voltage_Real_B() );
		set_voltage_Real_C( resourcesPhysicalStatus_var.get_voltage_Real_C() );
	}


	/**
	* Returns the value of the attribute whose name is "datamemberName"
	* for this object.
	*
	* @param datamemberName name of attribute whose value is to be
	* returned
	* @return value of the attribute whose name is "datamemberName"
	* for this object
	*/
	public Object getAttribute( String datamemberName ) {
		
		
		
		if (  "current_Imaginary_A".equals( datamemberName )  ) return new Float(get_current_Imaginary_A());
		else if (  "current_Imaginary_B".equals( datamemberName )  ) return new Float(get_current_Imaginary_B());
		else if (  "current_Imaginary_C".equals( datamemberName )  ) return new Float(get_current_Imaginary_C());
		else if (  "current_Real_A".equals( datamemberName )  ) return new Float(get_current_Real_A());
		else if (  "current_Real_B".equals( datamemberName )  ) return new Float(get_current_Real_B());
		else if (  "current_Real_C".equals( datamemberName )  ) return new Float(get_current_Real_C());
		else if (  "gridNodeId".equals( datamemberName )  ) return get_gridNodeId();
		else if (  "impedance_Imaginary_A".equals( datamemberName )  ) return new Float(get_impedance_Imaginary_A());
		else if (  "impedance_Imaginary_B".equals( datamemberName )  ) return new Float(get_impedance_Imaginary_B());
		else if (  "impedance_Imaginary_C".equals( datamemberName )  ) return new Float(get_impedance_Imaginary_C());
		else if (  "impedance_Real_A".equals( datamemberName )  ) return new Float(get_impedance_Real_A());
		else if (  "impedance_Real_B".equals( datamemberName )  ) return new Float(get_impedance_Real_B());
		else if (  "impedance_Real_C".equals( datamemberName )  ) return new Float(get_impedance_Real_C());
		else if (  "phases".equals( datamemberName )  ) return get_phases();
		else if (  "power_Imaginary_A".equals( datamemberName )  ) return new Float(get_power_Imaginary_A());
		else if (  "power_Imaginary_B".equals( datamemberName )  ) return new Float(get_power_Imaginary_B());
		else if (  "power_Imaginary_C".equals( datamemberName )  ) return new Float(get_power_Imaginary_C());
		else if (  "power_Real_A".equals( datamemberName )  ) return new Float(get_power_Real_A());
		else if (  "power_Real_B".equals( datamemberName )  ) return new Float(get_power_Real_B());
		else if (  "power_Real_C".equals( datamemberName )  ) return new Float(get_power_Real_C());
		else if (  "status".equals( datamemberName )  ) return new Boolean(get_status());
		else if (  "voltage_Imaginary_A".equals( datamemberName )  ) return new Float(get_voltage_Imaginary_A());
		else if (  "voltage_Imaginary_B".equals( datamemberName )  ) return new Float(get_voltage_Imaginary_B());
		else if (  "voltage_Imaginary_C".equals( datamemberName )  ) return new Float(get_voltage_Imaginary_C());
		else if (  "voltage_Real_A".equals( datamemberName )  ) return new Float(get_voltage_Real_A());
		else if (  "voltage_Real_B".equals( datamemberName )  ) return new Float(get_voltage_Real_B());
		else if (  "voltage_Real_C".equals( datamemberName )  ) return new Float(get_voltage_Real_C());
		else return super.getAttribute( datamemberName );
	}
	
	/**
	* Returns the value of the attribute whose handle (RTI assigned)
	* is "datamemberHandle" for this object.
	*
	* @param datamemberHandle handle (RTI assigned) of attribute whose
	* value is to be returned
	* @return value of the attribute whose handle (RTI assigned) is
	* "datamemberHandle" for this object
	*/
	public Object getAttribute( int datamemberHandle ) {
		
				
		
		if ( get_current_Imaginary_A_handle() == datamemberHandle ) return new Float(get_current_Imaginary_A());
		else if ( get_current_Imaginary_B_handle() == datamemberHandle ) return new Float(get_current_Imaginary_B());
		else if ( get_current_Imaginary_C_handle() == datamemberHandle ) return new Float(get_current_Imaginary_C());
		else if ( get_current_Real_A_handle() == datamemberHandle ) return new Float(get_current_Real_A());
		else if ( get_current_Real_B_handle() == datamemberHandle ) return new Float(get_current_Real_B());
		else if ( get_current_Real_C_handle() == datamemberHandle ) return new Float(get_current_Real_C());
		else if ( get_gridNodeId_handle() == datamemberHandle ) return get_gridNodeId();
		else if ( get_impedance_Imaginary_A_handle() == datamemberHandle ) return new Float(get_impedance_Imaginary_A());
		else if ( get_impedance_Imaginary_B_handle() == datamemberHandle ) return new Float(get_impedance_Imaginary_B());
		else if ( get_impedance_Imaginary_C_handle() == datamemberHandle ) return new Float(get_impedance_Imaginary_C());
		else if ( get_impedance_Real_A_handle() == datamemberHandle ) return new Float(get_impedance_Real_A());
		else if ( get_impedance_Real_B_handle() == datamemberHandle ) return new Float(get_impedance_Real_B());
		else if ( get_impedance_Real_C_handle() == datamemberHandle ) return new Float(get_impedance_Real_C());
		else if ( get_phases_handle() == datamemberHandle ) return get_phases();
		else if ( get_power_Imaginary_A_handle() == datamemberHandle ) return new Float(get_power_Imaginary_A());
		else if ( get_power_Imaginary_B_handle() == datamemberHandle ) return new Float(get_power_Imaginary_B());
		else if ( get_power_Imaginary_C_handle() == datamemberHandle ) return new Float(get_power_Imaginary_C());
		else if ( get_power_Real_A_handle() == datamemberHandle ) return new Float(get_power_Real_A());
		else if ( get_power_Real_B_handle() == datamemberHandle ) return new Float(get_power_Real_B());
		else if ( get_power_Real_C_handle() == datamemberHandle ) return new Float(get_power_Real_C());
		else if ( get_status_handle() == datamemberHandle ) return new Boolean(get_status());
		else if ( get_voltage_Imaginary_A_handle() == datamemberHandle ) return new Float(get_voltage_Imaginary_A());
		else if ( get_voltage_Imaginary_B_handle() == datamemberHandle ) return new Float(get_voltage_Imaginary_B());
		else if ( get_voltage_Imaginary_C_handle() == datamemberHandle ) return new Float(get_voltage_Imaginary_C());
		else if ( get_voltage_Real_A_handle() == datamemberHandle ) return new Float(get_voltage_Real_A());
		else if ( get_voltage_Real_B_handle() == datamemberHandle ) return new Float(get_voltage_Real_B());
		else if ( get_voltage_Real_C_handle() == datamemberHandle ) return new Float(get_voltage_Real_C());
		else return super.getAttribute( datamemberHandle );
	}
	
	protected boolean setAttributeAux( int param_handle, String val ) {
		boolean retval = true;		
		
			
		
		if ( param_handle == get_current_Imaginary_A_handle() ) set_current_Imaginary_A( Float.parseFloat(val) );
		else if ( param_handle == get_current_Imaginary_B_handle() ) set_current_Imaginary_B( Float.parseFloat(val) );
		else if ( param_handle == get_current_Imaginary_C_handle() ) set_current_Imaginary_C( Float.parseFloat(val) );
		else if ( param_handle == get_current_Real_A_handle() ) set_current_Real_A( Float.parseFloat(val) );
		else if ( param_handle == get_current_Real_B_handle() ) set_current_Real_B( Float.parseFloat(val) );
		else if ( param_handle == get_current_Real_C_handle() ) set_current_Real_C( Float.parseFloat(val) );
		else if ( param_handle == get_gridNodeId_handle() ) set_gridNodeId( val );
		else if ( param_handle == get_impedance_Imaginary_A_handle() ) set_impedance_Imaginary_A( Float.parseFloat(val) );
		else if ( param_handle == get_impedance_Imaginary_B_handle() ) set_impedance_Imaginary_B( Float.parseFloat(val) );
		else if ( param_handle == get_impedance_Imaginary_C_handle() ) set_impedance_Imaginary_C( Float.parseFloat(val) );
		else if ( param_handle == get_impedance_Real_A_handle() ) set_impedance_Real_A( Float.parseFloat(val) );
		else if ( param_handle == get_impedance_Real_B_handle() ) set_impedance_Real_B( Float.parseFloat(val) );
		else if ( param_handle == get_impedance_Real_C_handle() ) set_impedance_Real_C( Float.parseFloat(val) );
		else if ( param_handle == get_phases_handle() ) set_phases( val );
		else if ( param_handle == get_power_Imaginary_A_handle() ) set_power_Imaginary_A( Float.parseFloat(val) );
		else if ( param_handle == get_power_Imaginary_B_handle() ) set_power_Imaginary_B( Float.parseFloat(val) );
		else if ( param_handle == get_power_Imaginary_C_handle() ) set_power_Imaginary_C( Float.parseFloat(val) );
		else if ( param_handle == get_power_Real_A_handle() ) set_power_Real_A( Float.parseFloat(val) );
		else if ( param_handle == get_power_Real_B_handle() ) set_power_Real_B( Float.parseFloat(val) );
		else if ( param_handle == get_power_Real_C_handle() ) set_power_Real_C( Float.parseFloat(val) );
		else if ( param_handle == get_status_handle() ) set_status( Boolean.parseBoolean(val) );
		else if ( param_handle == get_voltage_Imaginary_A_handle() ) set_voltage_Imaginary_A( Float.parseFloat(val) );
		else if ( param_handle == get_voltage_Imaginary_B_handle() ) set_voltage_Imaginary_B( Float.parseFloat(val) );
		else if ( param_handle == get_voltage_Imaginary_C_handle() ) set_voltage_Imaginary_C( Float.parseFloat(val) );
		else if ( param_handle == get_voltage_Real_A_handle() ) set_voltage_Real_A( Float.parseFloat(val) );
		else if ( param_handle == get_voltage_Real_B_handle() ) set_voltage_Real_B( Float.parseFloat(val) );
		else if ( param_handle == get_voltage_Real_C_handle() ) set_voltage_Real_C( Float.parseFloat(val) );
		else retval = super.setAttributeAux( param_handle, val );
		
		return retval;
	}
	
	protected boolean setAttributeAux( String datamemberName, String val ) {
		boolean retval = true;
		
			
		
		if (  "current_Imaginary_A".equals( datamemberName )  ) set_current_Imaginary_A( Float.parseFloat(val) );
		else if (  "current_Imaginary_B".equals( datamemberName )  ) set_current_Imaginary_B( Float.parseFloat(val) );
		else if (  "current_Imaginary_C".equals( datamemberName )  ) set_current_Imaginary_C( Float.parseFloat(val) );
		else if (  "current_Real_A".equals( datamemberName )  ) set_current_Real_A( Float.parseFloat(val) );
		else if (  "current_Real_B".equals( datamemberName )  ) set_current_Real_B( Float.parseFloat(val) );
		else if (  "current_Real_C".equals( datamemberName )  ) set_current_Real_C( Float.parseFloat(val) );
		else if (  "gridNodeId".equals( datamemberName )  ) set_gridNodeId( val );
		else if (  "impedance_Imaginary_A".equals( datamemberName )  ) set_impedance_Imaginary_A( Float.parseFloat(val) );
		else if (  "impedance_Imaginary_B".equals( datamemberName )  ) set_impedance_Imaginary_B( Float.parseFloat(val) );
		else if (  "impedance_Imaginary_C".equals( datamemberName )  ) set_impedance_Imaginary_C( Float.parseFloat(val) );
		else if (  "impedance_Real_A".equals( datamemberName )  ) set_impedance_Real_A( Float.parseFloat(val) );
		else if (  "impedance_Real_B".equals( datamemberName )  ) set_impedance_Real_B( Float.parseFloat(val) );
		else if (  "impedance_Real_C".equals( datamemberName )  ) set_impedance_Real_C( Float.parseFloat(val) );
		else if (  "phases".equals( datamemberName )  ) set_phases( val );
		else if (  "power_Imaginary_A".equals( datamemberName )  ) set_power_Imaginary_A( Float.parseFloat(val) );
		else if (  "power_Imaginary_B".equals( datamemberName )  ) set_power_Imaginary_B( Float.parseFloat(val) );
		else if (  "power_Imaginary_C".equals( datamemberName )  ) set_power_Imaginary_C( Float.parseFloat(val) );
		else if (  "power_Real_A".equals( datamemberName )  ) set_power_Real_A( Float.parseFloat(val) );
		else if (  "power_Real_B".equals( datamemberName )  ) set_power_Real_B( Float.parseFloat(val) );
		else if (  "power_Real_C".equals( datamemberName )  ) set_power_Real_C( Float.parseFloat(val) );
		else if (  "status".equals( datamemberName )  ) set_status( Boolean.parseBoolean(val) );
		else if (  "voltage_Imaginary_A".equals( datamemberName )  ) set_voltage_Imaginary_A( Float.parseFloat(val) );
		else if (  "voltage_Imaginary_B".equals( datamemberName )  ) set_voltage_Imaginary_B( Float.parseFloat(val) );
		else if (  "voltage_Imaginary_C".equals( datamemberName )  ) set_voltage_Imaginary_C( Float.parseFloat(val) );
		else if (  "voltage_Real_A".equals( datamemberName )  ) set_voltage_Real_A( Float.parseFloat(val) );
		else if (  "voltage_Real_B".equals( datamemberName )  ) set_voltage_Real_B( Float.parseFloat(val) );
		else if (  "voltage_Real_C".equals( datamemberName )  ) set_voltage_Real_C( Float.parseFloat(val) );	
		else retval = super.setAttributeAux( datamemberName, val );
		
		return retval;
	}
	
	protected boolean setAttributeAux( String datamemberName, Object val ) {
		boolean retval = true;
		
		
		
		if (  "current_Imaginary_A".equals( datamemberName )  ) set_current_Imaginary_A( (Float)val );
		else if (  "current_Imaginary_B".equals( datamemberName )  ) set_current_Imaginary_B( (Float)val );
		else if (  "current_Imaginary_C".equals( datamemberName )  ) set_current_Imaginary_C( (Float)val );
		else if (  "current_Real_A".equals( datamemberName )  ) set_current_Real_A( (Float)val );
		else if (  "current_Real_B".equals( datamemberName )  ) set_current_Real_B( (Float)val );
		else if (  "current_Real_C".equals( datamemberName )  ) set_current_Real_C( (Float)val );
		else if (  "gridNodeId".equals( datamemberName )  ) set_gridNodeId( (String)val );
		else if (  "impedance_Imaginary_A".equals( datamemberName )  ) set_impedance_Imaginary_A( (Float)val );
		else if (  "impedance_Imaginary_B".equals( datamemberName )  ) set_impedance_Imaginary_B( (Float)val );
		else if (  "impedance_Imaginary_C".equals( datamemberName )  ) set_impedance_Imaginary_C( (Float)val );
		else if (  "impedance_Real_A".equals( datamemberName )  ) set_impedance_Real_A( (Float)val );
		else if (  "impedance_Real_B".equals( datamemberName )  ) set_impedance_Real_B( (Float)val );
		else if (  "impedance_Real_C".equals( datamemberName )  ) set_impedance_Real_C( (Float)val );
		else if (  "phases".equals( datamemberName )  ) set_phases( (String)val );
		else if (  "power_Imaginary_A".equals( datamemberName )  ) set_power_Imaginary_A( (Float)val );
		else if (  "power_Imaginary_B".equals( datamemberName )  ) set_power_Imaginary_B( (Float)val );
		else if (  "power_Imaginary_C".equals( datamemberName )  ) set_power_Imaginary_C( (Float)val );
		else if (  "power_Real_A".equals( datamemberName )  ) set_power_Real_A( (Float)val );
		else if (  "power_Real_B".equals( datamemberName )  ) set_power_Real_B( (Float)val );
		else if (  "power_Real_C".equals( datamemberName )  ) set_power_Real_C( (Float)val );
		else if (  "status".equals( datamemberName )  ) set_status( (Boolean)val );
		else if (  "voltage_Imaginary_A".equals( datamemberName )  ) set_voltage_Imaginary_A( (Float)val );
		else if (  "voltage_Imaginary_B".equals( datamemberName )  ) set_voltage_Imaginary_B( (Float)val );
		else if (  "voltage_Imaginary_C".equals( datamemberName )  ) set_voltage_Imaginary_C( (Float)val );
		else if (  "voltage_Real_A".equals( datamemberName )  ) set_voltage_Real_A( (Float)val );
		else if (  "voltage_Real_B".equals( datamemberName )  ) set_voltage_Real_B( (Float)val );
		else if (  "voltage_Real_C".equals( datamemberName )  ) set_voltage_Real_C( (Float)val );		
		else retval = super.setAttributeAux( datamemberName, val );
		
		return retval;
	}

	protected SuppliedAttributes createSuppliedDatamembers( boolean force ) {
		SuppliedAttributes datamembers = super.createSuppliedDatamembers( force );

	
		boolean isPublished = false;
		
		
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_current_Imaginary_A_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if current_Imaginary_A is published.");
				isPublished = false;
			}
			if (  isPublished && _current_Imaginary_A.shouldBeUpdated( force )  ) {
				datamembers.add( get_current_Imaginary_A_handle(), Float.toString(get_current_Imaginary_A()).getBytes() );
				_current_Imaginary_A.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_current_Imaginary_B_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if current_Imaginary_B is published.");
				isPublished = false;
			}
			if (  isPublished && _current_Imaginary_B.shouldBeUpdated( force )  ) {
				datamembers.add( get_current_Imaginary_B_handle(), Float.toString(get_current_Imaginary_B()).getBytes() );
				_current_Imaginary_B.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_current_Imaginary_C_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if current_Imaginary_C is published.");
				isPublished = false;
			}
			if (  isPublished && _current_Imaginary_C.shouldBeUpdated( force )  ) {
				datamembers.add( get_current_Imaginary_C_handle(), Float.toString(get_current_Imaginary_C()).getBytes() );
				_current_Imaginary_C.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_current_Real_A_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if current_Real_A is published.");
				isPublished = false;
			}
			if (  isPublished && _current_Real_A.shouldBeUpdated( force )  ) {
				datamembers.add( get_current_Real_A_handle(), Float.toString(get_current_Real_A()).getBytes() );
				_current_Real_A.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_current_Real_B_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if current_Real_B is published.");
				isPublished = false;
			}
			if (  isPublished && _current_Real_B.shouldBeUpdated( force )  ) {
				datamembers.add( get_current_Real_B_handle(), Float.toString(get_current_Real_B()).getBytes() );
				_current_Real_B.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_current_Real_C_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if current_Real_C is published.");
				isPublished = false;
			}
			if (  isPublished && _current_Real_C.shouldBeUpdated( force )  ) {
				datamembers.add( get_current_Real_C_handle(), Float.toString(get_current_Real_C()).getBytes() );
				_current_Real_C.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_gridNodeId_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if gridNodeId is published.");
				isPublished = false;
			}
			if (  isPublished && _gridNodeId.shouldBeUpdated( force )  ) {
				datamembers.add( get_gridNodeId_handle(), get_gridNodeId().getBytes() );
				_gridNodeId.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_impedance_Imaginary_A_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if impedance_Imaginary_A is published.");
				isPublished = false;
			}
			if (  isPublished && _impedance_Imaginary_A.shouldBeUpdated( force )  ) {
				datamembers.add( get_impedance_Imaginary_A_handle(), Float.toString(get_impedance_Imaginary_A()).getBytes() );
				_impedance_Imaginary_A.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_impedance_Imaginary_B_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if impedance_Imaginary_B is published.");
				isPublished = false;
			}
			if (  isPublished && _impedance_Imaginary_B.shouldBeUpdated( force )  ) {
				datamembers.add( get_impedance_Imaginary_B_handle(), Float.toString(get_impedance_Imaginary_B()).getBytes() );
				_impedance_Imaginary_B.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_impedance_Imaginary_C_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if impedance_Imaginary_C is published.");
				isPublished = false;
			}
			if (  isPublished && _impedance_Imaginary_C.shouldBeUpdated( force )  ) {
				datamembers.add( get_impedance_Imaginary_C_handle(), Float.toString(get_impedance_Imaginary_C()).getBytes() );
				_impedance_Imaginary_C.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_impedance_Real_A_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if impedance_Real_A is published.");
				isPublished = false;
			}
			if (  isPublished && _impedance_Real_A.shouldBeUpdated( force )  ) {
				datamembers.add( get_impedance_Real_A_handle(), Float.toString(get_impedance_Real_A()).getBytes() );
				_impedance_Real_A.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_impedance_Real_B_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if impedance_Real_B is published.");
				isPublished = false;
			}
			if (  isPublished && _impedance_Real_B.shouldBeUpdated( force )  ) {
				datamembers.add( get_impedance_Real_B_handle(), Float.toString(get_impedance_Real_B()).getBytes() );
				_impedance_Real_B.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_impedance_Real_C_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if impedance_Real_C is published.");
				isPublished = false;
			}
			if (  isPublished && _impedance_Real_C.shouldBeUpdated( force )  ) {
				datamembers.add( get_impedance_Real_C_handle(), Float.toString(get_impedance_Real_C()).getBytes() );
				_impedance_Real_C.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_phases_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if phases is published.");
				isPublished = false;
			}
			if (  isPublished && _phases.shouldBeUpdated( force )  ) {
				datamembers.add( get_phases_handle(), get_phases().getBytes() );
				_phases.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_power_Imaginary_A_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if power_Imaginary_A is published.");
				isPublished = false;
			}
			if (  isPublished && _power_Imaginary_A.shouldBeUpdated( force )  ) {
				datamembers.add( get_power_Imaginary_A_handle(), Float.toString(get_power_Imaginary_A()).getBytes() );
				_power_Imaginary_A.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_power_Imaginary_B_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if power_Imaginary_B is published.");
				isPublished = false;
			}
			if (  isPublished && _power_Imaginary_B.shouldBeUpdated( force )  ) {
				datamembers.add( get_power_Imaginary_B_handle(), Float.toString(get_power_Imaginary_B()).getBytes() );
				_power_Imaginary_B.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_power_Imaginary_C_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if power_Imaginary_C is published.");
				isPublished = false;
			}
			if (  isPublished && _power_Imaginary_C.shouldBeUpdated( force )  ) {
				datamembers.add( get_power_Imaginary_C_handle(), Float.toString(get_power_Imaginary_C()).getBytes() );
				_power_Imaginary_C.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_power_Real_A_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if power_Real_A is published.");
				isPublished = false;
			}
			if (  isPublished && _power_Real_A.shouldBeUpdated( force )  ) {
				datamembers.add( get_power_Real_A_handle(), Float.toString(get_power_Real_A()).getBytes() );
				_power_Real_A.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_power_Real_B_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if power_Real_B is published.");
				isPublished = false;
			}
			if (  isPublished && _power_Real_B.shouldBeUpdated( force )  ) {
				datamembers.add( get_power_Real_B_handle(), Float.toString(get_power_Real_B()).getBytes() );
				_power_Real_B.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_power_Real_C_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if power_Real_C is published.");
				isPublished = false;
			}
			if (  isPublished && _power_Real_C.shouldBeUpdated( force )  ) {
				datamembers.add( get_power_Real_C_handle(), Float.toString(get_power_Real_C()).getBytes() );
				_power_Real_C.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_status_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if status is published.");
				isPublished = false;
			}
			if (  isPublished && _status.shouldBeUpdated( force )  ) {
				datamembers.add( get_status_handle(), Boolean.toString(get_status()).getBytes() );
				_status.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_voltage_Imaginary_A_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if voltage_Imaginary_A is published.");
				isPublished = false;
			}
			if (  isPublished && _voltage_Imaginary_A.shouldBeUpdated( force )  ) {
				datamembers.add( get_voltage_Imaginary_A_handle(), Float.toString(get_voltage_Imaginary_A()).getBytes() );
				_voltage_Imaginary_A.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_voltage_Imaginary_B_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if voltage_Imaginary_B is published.");
				isPublished = false;
			}
			if (  isPublished && _voltage_Imaginary_B.shouldBeUpdated( force )  ) {
				datamembers.add( get_voltage_Imaginary_B_handle(), Float.toString(get_voltage_Imaginary_B()).getBytes() );
				_voltage_Imaginary_B.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_voltage_Imaginary_C_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if voltage_Imaginary_C is published.");
				isPublished = false;
			}
			if (  isPublished && _voltage_Imaginary_C.shouldBeUpdated( force )  ) {
				datamembers.add( get_voltage_Imaginary_C_handle(), Float.toString(get_voltage_Imaginary_C()).getBytes() );
				_voltage_Imaginary_C.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_voltage_Real_A_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if voltage_Real_A is published.");
				isPublished = false;
			}
			if (  isPublished && _voltage_Real_A.shouldBeUpdated( force )  ) {
				datamembers.add( get_voltage_Real_A_handle(), Float.toString(get_voltage_Real_A()).getBytes() );
				_voltage_Real_A.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_voltage_Real_B_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if voltage_Real_B is published.");
				isPublished = false;
			}
			if (  isPublished && _voltage_Real_B.shouldBeUpdated( force )  ) {
				datamembers.add( get_voltage_Real_B_handle(), Float.toString(get_voltage_Real_B()).getBytes() );
				_voltage_Real_B.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_voltage_Real_C_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourcesPhysicalStatus.createSuppliedAttributes:  could not determine if voltage_Real_C is published.");
				isPublished = false;
			}
			if (  isPublished && _voltage_Real_C.shouldBeUpdated( force )  ) {
				datamembers.add( get_voltage_Real_C_handle(), Float.toString(get_voltage_Real_C()).getBytes() );
				_voltage_Real_C.setHasBeenUpdated();
			}
	
		return datamembers;
	}

	
	public void copyFrom( Object object ) {
		super.copyFrom( object );
		if ( object instanceof resourcesPhysicalStatus ) {
			resourcesPhysicalStatus data = (resourcesPhysicalStatus)object;
			
			
				_current_Imaginary_A = data._current_Imaginary_A;
				_current_Imaginary_B = data._current_Imaginary_B;
				_current_Imaginary_C = data._current_Imaginary_C;
				_current_Real_A = data._current_Real_A;
				_current_Real_B = data._current_Real_B;
				_current_Real_C = data._current_Real_C;
				_gridNodeId = data._gridNodeId;
				_impedance_Imaginary_A = data._impedance_Imaginary_A;
				_impedance_Imaginary_B = data._impedance_Imaginary_B;
				_impedance_Imaginary_C = data._impedance_Imaginary_C;
				_impedance_Real_A = data._impedance_Real_A;
				_impedance_Real_B = data._impedance_Real_B;
				_impedance_Real_C = data._impedance_Real_C;
				_phases = data._phases;
				_power_Imaginary_A = data._power_Imaginary_A;
				_power_Imaginary_B = data._power_Imaginary_B;
				_power_Imaginary_C = data._power_Imaginary_C;
				_power_Real_A = data._power_Real_A;
				_power_Real_B = data._power_Real_B;
				_power_Real_C = data._power_Real_C;
				_status = data._status;
				_voltage_Imaginary_A = data._voltage_Imaginary_A;
				_voltage_Imaginary_B = data._voltage_Imaginary_B;
				_voltage_Imaginary_C = data._voltage_Imaginary_C;
				_voltage_Real_A = data._voltage_Real_A;
				_voltage_Real_B = data._voltage_Real_B;
				_voltage_Real_C = data._voltage_Real_C;
			
		}
	}
}
