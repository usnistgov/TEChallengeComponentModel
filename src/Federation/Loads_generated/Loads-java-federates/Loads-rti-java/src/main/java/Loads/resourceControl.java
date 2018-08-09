
package Loads;

import java.util.HashSet;
import java.util.Set;

import hla.rti.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cpswt.utils.CpswtUtils;


import org.cpswt.hla.*;

/**
* The resourceControl class implements the resourceControl object in the
* Loads simulation.
*/
public class resourceControl extends ObjectRoot {

	private static final Logger logger = LogManager.getLogger(resourceControl.class);

	/**
	* Default constructor -- creates an instance of the resourceControl object
	* class with default attribute values.
	*/
	public resourceControl() { }

	
	
	private static int _Resources_handle;
	private static int _activePowerCurve_handle;
	private static int _actualDemand_handle;
	private static int _adjustedFullDRPower_handle;
	private static int _adjustedNoDRPower_handle;
	private static int _downBeginRamp_handle;
	private static int _downDuration_handle;
	private static int _downRampToCompletion_handle;
	private static int _downRate_handle;
	private static int _loadStatusType_handle;
	private static int _locked_handle;
	private static int _maximumReactivePower_handle;
	private static int _maximumRealPower_handle;
	private static int _reactiveDesiredFractionOfFullRatedOutputBegin_handle;
	private static int _reactiveDesiredFractionOfFullRatedOutputEnd_handle;
	private static int _reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin_handle;
	private static int _reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd_handle;
	private static int _realDesiredFractionOfFullRatedOutputBegin_handle;
	private static int _realDesiredFractionOfFullRatedOutputEnd_handle;
	private static int _realRequiredFractionOfFullRatedInputPowerDrawnBegin_handle;
	private static int _realRequiredFractionOfFullRatedInputPowerDrawnEnd_handle;
	private static int _upBeginRamp_handle;
	private static int _upDuration_handle;
	private static int _upRampToCompletion_handle;
	private static int _upRate_handle;
	
	
	/**
	* Returns the handle (RTI assigned) of the "Resources" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "Resources" attribute
	*/
	public static int get_Resources_handle() { return _Resources_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "activePowerCurve" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "activePowerCurve" attribute
	*/
	public static int get_activePowerCurve_handle() { return _activePowerCurve_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "actualDemand" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "actualDemand" attribute
	*/
	public static int get_actualDemand_handle() { return _actualDemand_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "adjustedFullDRPower" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "adjustedFullDRPower" attribute
	*/
	public static int get_adjustedFullDRPower_handle() { return _adjustedFullDRPower_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "adjustedNoDRPower" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "adjustedNoDRPower" attribute
	*/
	public static int get_adjustedNoDRPower_handle() { return _adjustedNoDRPower_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "downBeginRamp" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "downBeginRamp" attribute
	*/
	public static int get_downBeginRamp_handle() { return _downBeginRamp_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "downDuration" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "downDuration" attribute
	*/
	public static int get_downDuration_handle() { return _downDuration_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "downRampToCompletion" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "downRampToCompletion" attribute
	*/
	public static int get_downRampToCompletion_handle() { return _downRampToCompletion_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "downRate" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "downRate" attribute
	*/
	public static int get_downRate_handle() { return _downRate_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "loadStatusType" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "loadStatusType" attribute
	*/
	public static int get_loadStatusType_handle() { return _loadStatusType_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "locked" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "locked" attribute
	*/
	public static int get_locked_handle() { return _locked_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "maximumReactivePower" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "maximumReactivePower" attribute
	*/
	public static int get_maximumReactivePower_handle() { return _maximumReactivePower_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "maximumRealPower" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "maximumRealPower" attribute
	*/
	public static int get_maximumRealPower_handle() { return _maximumRealPower_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute
	*/
	public static int get_reactiveDesiredFractionOfFullRatedOutputBegin_handle() { return _reactiveDesiredFractionOfFullRatedOutputBegin_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute
	*/
	public static int get_reactiveDesiredFractionOfFullRatedOutputEnd_handle() { return _reactiveDesiredFractionOfFullRatedOutputEnd_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute
	*/
	public static int get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin_handle() { return _reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute
	*/
	public static int get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd_handle() { return _reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "realDesiredFractionOfFullRatedOutputBegin" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "realDesiredFractionOfFullRatedOutputBegin" attribute
	*/
	public static int get_realDesiredFractionOfFullRatedOutputBegin_handle() { return _realDesiredFractionOfFullRatedOutputBegin_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "realDesiredFractionOfFullRatedOutputEnd" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "realDesiredFractionOfFullRatedOutputEnd" attribute
	*/
	public static int get_realDesiredFractionOfFullRatedOutputEnd_handle() { return _realDesiredFractionOfFullRatedOutputEnd_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute
	*/
	public static int get_realRequiredFractionOfFullRatedInputPowerDrawnBegin_handle() { return _realRequiredFractionOfFullRatedInputPowerDrawnBegin_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute
	*/
	public static int get_realRequiredFractionOfFullRatedInputPowerDrawnEnd_handle() { return _realRequiredFractionOfFullRatedInputPowerDrawnEnd_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "upBeginRamp" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "upBeginRamp" attribute
	*/
	public static int get_upBeginRamp_handle() { return _upBeginRamp_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "upDuration" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "upDuration" attribute
	*/
	public static int get_upDuration_handle() { return _upDuration_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "upRampToCompletion" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "upRampToCompletion" attribute
	*/
	public static int get_upRampToCompletion_handle() { return _upRampToCompletion_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "upRate" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "upRate" attribute
	*/
	public static int get_upRate_handle() { return _upRate_handle; }
	
	
	
	private static boolean _isInitialized = false;

	private static int _handle;

	/**
	* Returns the handle (RTI assigned) of the resourceControl object class.
	* Note: As this is a static method, it is NOT polymorphic, and so, if called on
	* a reference will return the handle of the class pertaining to the reference,\
	* rather than the handle of the class for the instance referred to by the reference.
	* For the polymorphic version of this method, use {@link #getClassHandle()}.
	*/
	public static int get_handle() { return _handle; }

	/**
	* Returns the fully-qualified (dot-delimited) name of the resourceControl
	* object class.
	* Note: As this is a static method, it is NOT polymorphic, and so, if called on
	* a reference will return the name of the class pertaining to the reference,\
	* rather than the name of the class for the instance referred to by the reference.
	* For the polymorphic version of this method, use {@link #getClassName()}.
	*/
	public static String get_class_name() { return "ObjectRoot.resourceControl"; }

	/**
	* Returns the simple name (the last name in the dot-delimited fully-qualified
	* class name) of the resourceControl object class.
	*/
	public static String get_simple_class_name() { return "resourceControl"; }

	private static Set< String > _datamemberNames = new HashSet< String >();
	private static Set< String > _allDatamemberNames = new HashSet< String >();

	/**
	* Returns a set containing the names of all of the non-hidden attributes in the
	* resourceControl object class.
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
	* resourceControl object class.
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
		_classNameSet.add("ObjectRoot.resourceControl");
		_classNameClassMap.put("ObjectRoot.resourceControl", resourceControl.class);
		
		_datamemberClassNameSetMap.put("ObjectRoot.resourceControl", _datamemberNames);
		_allDatamemberClassNameSetMap.put("ObjectRoot.resourceControl", _allDatamemberNames);

		
		
		_datamemberNames.add("Resources");
		_datamemberNames.add("activePowerCurve");
		_datamemberNames.add("actualDemand");
		_datamemberNames.add("adjustedFullDRPower");
		_datamemberNames.add("adjustedNoDRPower");
		_datamemberNames.add("downBeginRamp");
		_datamemberNames.add("downDuration");
		_datamemberNames.add("downRampToCompletion");
		_datamemberNames.add("downRate");
		_datamemberNames.add("loadStatusType");
		_datamemberNames.add("locked");
		_datamemberNames.add("maximumReactivePower");
		_datamemberNames.add("maximumRealPower");
		_datamemberNames.add("reactiveDesiredFractionOfFullRatedOutputBegin");
		_datamemberNames.add("reactiveDesiredFractionOfFullRatedOutputEnd");
		_datamemberNames.add("reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin");
		_datamemberNames.add("reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd");
		_datamemberNames.add("realDesiredFractionOfFullRatedOutputBegin");
		_datamemberNames.add("realDesiredFractionOfFullRatedOutputEnd");
		_datamemberNames.add("realRequiredFractionOfFullRatedInputPowerDrawnBegin");
		_datamemberNames.add("realRequiredFractionOfFullRatedInputPowerDrawnEnd");
		_datamemberNames.add("upBeginRamp");
		_datamemberNames.add("upDuration");
		_datamemberNames.add("upRampToCompletion");
		_datamemberNames.add("upRate");
		
		
		_allDatamemberNames.add("Resources");
		_allDatamemberNames.add("activePowerCurve");
		_allDatamemberNames.add("actualDemand");
		_allDatamemberNames.add("adjustedFullDRPower");
		_allDatamemberNames.add("adjustedNoDRPower");
		_allDatamemberNames.add("downBeginRamp");
		_allDatamemberNames.add("downDuration");
		_allDatamemberNames.add("downRampToCompletion");
		_allDatamemberNames.add("downRate");
		_allDatamemberNames.add("loadStatusType");
		_allDatamemberNames.add("locked");
		_allDatamemberNames.add("maximumReactivePower");
		_allDatamemberNames.add("maximumRealPower");
		_allDatamemberNames.add("reactiveDesiredFractionOfFullRatedOutputBegin");
		_allDatamemberNames.add("reactiveDesiredFractionOfFullRatedOutputEnd");
		_allDatamemberNames.add("reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin");
		_allDatamemberNames.add("reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd");
		_allDatamemberNames.add("realDesiredFractionOfFullRatedOutputBegin");
		_allDatamemberNames.add("realDesiredFractionOfFullRatedOutputEnd");
		_allDatamemberNames.add("realRequiredFractionOfFullRatedInputPowerDrawnBegin");
		_allDatamemberNames.add("realRequiredFractionOfFullRatedInputPowerDrawnEnd");
		_allDatamemberNames.add("upBeginRamp");
		_allDatamemberNames.add("upDuration");
		_allDatamemberNames.add("upRampToCompletion");
		_allDatamemberNames.add("upRate");
		
		
		_datamemberTypeMap.put("Resources", "String");
		_datamemberTypeMap.put("activePowerCurve", "int");
		_datamemberTypeMap.put("actualDemand", "float");
		_datamemberTypeMap.put("adjustedFullDRPower", "float");
		_datamemberTypeMap.put("adjustedNoDRPower", "float");
		_datamemberTypeMap.put("downBeginRamp", "float");
		_datamemberTypeMap.put("downDuration", "int");
		_datamemberTypeMap.put("downRampToCompletion", "boolean");
		_datamemberTypeMap.put("downRate", "float");
		_datamemberTypeMap.put("loadStatusType", "String");
		_datamemberTypeMap.put("locked", "boolean");
		_datamemberTypeMap.put("maximumReactivePower", "float");
		_datamemberTypeMap.put("maximumRealPower", "float");
		_datamemberTypeMap.put("reactiveDesiredFractionOfFullRatedOutputBegin", "float");
		_datamemberTypeMap.put("reactiveDesiredFractionOfFullRatedOutputEnd", "float");
		_datamemberTypeMap.put("reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin", "float");
		_datamemberTypeMap.put("reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd", "float");
		_datamemberTypeMap.put("realDesiredFractionOfFullRatedOutputBegin", "float");
		_datamemberTypeMap.put("realDesiredFractionOfFullRatedOutputEnd", "float");
		_datamemberTypeMap.put("realRequiredFractionOfFullRatedInputPowerDrawnBegin", "float");
		_datamemberTypeMap.put("realRequiredFractionOfFullRatedInputPowerDrawnEnd", "float");
		_datamemberTypeMap.put("upBeginRamp", "float");
		_datamemberTypeMap.put("upDuration", "int");
		_datamemberTypeMap.put("upRampToCompletion", "boolean");
		_datamemberTypeMap.put("upRate", "float");
	

		_classNamePublishAttributeNameMap.put("ObjectRoot.resourceControl", _publishAttributeNameSet);
		_publishedAttributeHandleSet = _factory.createAttributeHandleSet();
		_classNamePublishedAttributeMap.put("ObjectRoot.resourceControl", _publishedAttributeHandleSet);

		_classNameSubscribeAttributeNameMap.put("ObjectRoot.resourceControl", _subscribeAttributeNameSet);
		_subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
		_classNameSubscribedAttributeMap.put("ObjectRoot.resourceControl", _subscribedAttributeHandleSet);
	

	}


	private static String initErrorMessage = "Error:  ObjectRoot.resourceControl:  could not initialize:  ";
	protected static void init(RTIambassador rti) {
		if (_isInitialized) return;
		_isInitialized = true;
		
		ObjectRoot.init(rti);
		
		boolean isNotInitialized = true;
		while(isNotInitialized) {
			try {
				_handle = rti.getObjectClassHandle("ObjectRoot.resourceControl");
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

		_classNameHandleMap.put("ObjectRoot.resourceControl", get_handle());
		_classHandleNameMap.put(get_handle(), "ObjectRoot.resourceControl");
		_classHandleSimpleNameMap.put(get_handle(), "resourceControl");

		
		isNotInitialized = true;
		while(isNotInitialized) {
			try {
							
				_Resources_handle = rti.getAttributeHandle("Resources", get_handle());			
				_activePowerCurve_handle = rti.getAttributeHandle("activePowerCurve", get_handle());			
				_actualDemand_handle = rti.getAttributeHandle("actualDemand", get_handle());			
				_adjustedFullDRPower_handle = rti.getAttributeHandle("adjustedFullDRPower", get_handle());			
				_adjustedNoDRPower_handle = rti.getAttributeHandle("adjustedNoDRPower", get_handle());			
				_downBeginRamp_handle = rti.getAttributeHandle("downBeginRamp", get_handle());			
				_downDuration_handle = rti.getAttributeHandle("downDuration", get_handle());			
				_downRampToCompletion_handle = rti.getAttributeHandle("downRampToCompletion", get_handle());			
				_downRate_handle = rti.getAttributeHandle("downRate", get_handle());			
				_loadStatusType_handle = rti.getAttributeHandle("loadStatusType", get_handle());			
				_locked_handle = rti.getAttributeHandle("locked", get_handle());			
				_maximumReactivePower_handle = rti.getAttributeHandle("maximumReactivePower", get_handle());			
				_maximumRealPower_handle = rti.getAttributeHandle("maximumRealPower", get_handle());			
				_reactiveDesiredFractionOfFullRatedOutputBegin_handle = rti.getAttributeHandle("reactiveDesiredFractionOfFullRatedOutputBegin", get_handle());			
				_reactiveDesiredFractionOfFullRatedOutputEnd_handle = rti.getAttributeHandle("reactiveDesiredFractionOfFullRatedOutputEnd", get_handle());			
				_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin_handle = rti.getAttributeHandle("reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin", get_handle());			
				_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd_handle = rti.getAttributeHandle("reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd", get_handle());			
				_realDesiredFractionOfFullRatedOutputBegin_handle = rti.getAttributeHandle("realDesiredFractionOfFullRatedOutputBegin", get_handle());			
				_realDesiredFractionOfFullRatedOutputEnd_handle = rti.getAttributeHandle("realDesiredFractionOfFullRatedOutputEnd", get_handle());			
				_realRequiredFractionOfFullRatedInputPowerDrawnBegin_handle = rti.getAttributeHandle("realRequiredFractionOfFullRatedInputPowerDrawnBegin", get_handle());			
				_realRequiredFractionOfFullRatedInputPowerDrawnEnd_handle = rti.getAttributeHandle("realRequiredFractionOfFullRatedInputPowerDrawnEnd", get_handle());			
				_upBeginRamp_handle = rti.getAttributeHandle("upBeginRamp", get_handle());			
				_upDuration_handle = rti.getAttributeHandle("upDuration", get_handle());			
				_upRampToCompletion_handle = rti.getAttributeHandle("upRampToCompletion", get_handle());			
				_upRate_handle = rti.getAttributeHandle("upRate", get_handle());
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
			
			
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,Resources", get_Resources_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,activePowerCurve", get_activePowerCurve_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,actualDemand", get_actualDemand_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,adjustedFullDRPower", get_adjustedFullDRPower_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,adjustedNoDRPower", get_adjustedNoDRPower_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,downBeginRamp", get_downBeginRamp_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,downDuration", get_downDuration_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,downRampToCompletion", get_downRampToCompletion_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,downRate", get_downRate_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,loadStatusType", get_loadStatusType_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,locked", get_locked_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,maximumReactivePower", get_maximumReactivePower_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,maximumRealPower", get_maximumRealPower_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,reactiveDesiredFractionOfFullRatedOutputBegin", get_reactiveDesiredFractionOfFullRatedOutputBegin_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,reactiveDesiredFractionOfFullRatedOutputEnd", get_reactiveDesiredFractionOfFullRatedOutputEnd_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin", get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd", get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,realDesiredFractionOfFullRatedOutputBegin", get_realDesiredFractionOfFullRatedOutputBegin_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,realDesiredFractionOfFullRatedOutputEnd", get_realDesiredFractionOfFullRatedOutputEnd_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,realRequiredFractionOfFullRatedInputPowerDrawnBegin", get_realRequiredFractionOfFullRatedInputPowerDrawnBegin_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,realRequiredFractionOfFullRatedInputPowerDrawnEnd", get_realRequiredFractionOfFullRatedInputPowerDrawnEnd_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,upBeginRamp", get_upBeginRamp_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,upDuration", get_upDuration_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,upRampToCompletion", get_upRampToCompletion_handle());
		_datamemberNameHandleMap.put("ObjectRoot.resourceControl,upRate", get_upRate_handle());
			
			
		_datamemberHandleNameMap.put(get_Resources_handle(), "Resources");
		_datamemberHandleNameMap.put(get_activePowerCurve_handle(), "activePowerCurve");
		_datamemberHandleNameMap.put(get_actualDemand_handle(), "actualDemand");
		_datamemberHandleNameMap.put(get_adjustedFullDRPower_handle(), "adjustedFullDRPower");
		_datamemberHandleNameMap.put(get_adjustedNoDRPower_handle(), "adjustedNoDRPower");
		_datamemberHandleNameMap.put(get_downBeginRamp_handle(), "downBeginRamp");
		_datamemberHandleNameMap.put(get_downDuration_handle(), "downDuration");
		_datamemberHandleNameMap.put(get_downRampToCompletion_handle(), "downRampToCompletion");
		_datamemberHandleNameMap.put(get_downRate_handle(), "downRate");
		_datamemberHandleNameMap.put(get_loadStatusType_handle(), "loadStatusType");
		_datamemberHandleNameMap.put(get_locked_handle(), "locked");
		_datamemberHandleNameMap.put(get_maximumReactivePower_handle(), "maximumReactivePower");
		_datamemberHandleNameMap.put(get_maximumRealPower_handle(), "maximumRealPower");
		_datamemberHandleNameMap.put(get_reactiveDesiredFractionOfFullRatedOutputBegin_handle(), "reactiveDesiredFractionOfFullRatedOutputBegin");
		_datamemberHandleNameMap.put(get_reactiveDesiredFractionOfFullRatedOutputEnd_handle(), "reactiveDesiredFractionOfFullRatedOutputEnd");
		_datamemberHandleNameMap.put(get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin_handle(), "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin");
		_datamemberHandleNameMap.put(get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd_handle(), "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd");
		_datamemberHandleNameMap.put(get_realDesiredFractionOfFullRatedOutputBegin_handle(), "realDesiredFractionOfFullRatedOutputBegin");
		_datamemberHandleNameMap.put(get_realDesiredFractionOfFullRatedOutputEnd_handle(), "realDesiredFractionOfFullRatedOutputEnd");
		_datamemberHandleNameMap.put(get_realRequiredFractionOfFullRatedInputPowerDrawnBegin_handle(), "realRequiredFractionOfFullRatedInputPowerDrawnBegin");
		_datamemberHandleNameMap.put(get_realRequiredFractionOfFullRatedInputPowerDrawnEnd_handle(), "realRequiredFractionOfFullRatedInputPowerDrawnEnd");
		_datamemberHandleNameMap.put(get_upBeginRamp_handle(), "upBeginRamp");
		_datamemberHandleNameMap.put(get_upDuration_handle(), "upDuration");
		_datamemberHandleNameMap.put(get_upRampToCompletion_handle(), "upRampToCompletion");
		_datamemberHandleNameMap.put(get_upRate_handle(), "upRate");
		
	}

	private static boolean _isPublished = false;
	private static String publishErrorMessage = "Error:  ObjectRoot.resourceControl:  could not publish:  ";

	/**
	* Publishes the resourceControl object class for a federate.
	*
	* @param rti handle to the Local RTI Component
	*/
	public static void publish(RTIambassador rti) {
		if (_isPublished) return;
		
		init(rti);

		
		_publishedAttributeHandleSet.empty();
		for(String attributeName : _publishAttributeNameSet) {
			try {
				_publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.resourceControl," + attributeName));
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

	private static String unpublishErrorMessage = "Error:  ObjectRoot.resourceControl:  could not unpublish:  ";
	/**
	* Unpublishes the resourceControl object class for a federate.
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
	private static String subscribeErrorMessage = "Error:  ObjectRoot.resourceControl:  could not subscribe:  ";
	/**
	* Subscribes a federate to the resourceControl object class.
	*
	* @param rti handle to the Local RTI Component
	*/
	public static void subscribe(RTIambassador rti) {
		if (_isSubscribed) return;
		
		init(rti);
		
		_subscribedAttributeHandleSet.empty();
		for(String attributeName : _subscribeAttributeNameSet) {
			try {
				_subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.resourceControl," + attributeName));
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

	private static String unsubscribeErrorMessage = "Error:  ObjectRoot.resourceControl:  could not unsubscribe:  ";
	/**
	* Unsubscribes a federate from the resourceControl object class.
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
	* (that is, the resourceControl object class).
	*
	* @param handle handle to compare to the value of the handle (RTI assigned) of
	* this class (the resourceControl object class).
	* @return "true" if "handle" matches the value of the handle of this class
	* (that is, the resourceControl object class).
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
		return "resourceControl("
			
			
			+ "Resources:" + get_Resources()
			+ "," + "activePowerCurve:" + get_activePowerCurve()
			+ "," + "actualDemand:" + get_actualDemand()
			+ "," + "adjustedFullDRPower:" + get_adjustedFullDRPower()
			+ "," + "adjustedNoDRPower:" + get_adjustedNoDRPower()
			+ "," + "downBeginRamp:" + get_downBeginRamp()
			+ "," + "downDuration:" + get_downDuration()
			+ "," + "downRampToCompletion:" + get_downRampToCompletion()
			+ "," + "downRate:" + get_downRate()
			+ "," + "loadStatusType:" + get_loadStatusType()
			+ "," + "locked:" + get_locked()
			+ "," + "maximumReactivePower:" + get_maximumReactivePower()
			+ "," + "maximumRealPower:" + get_maximumRealPower()
			+ "," + "reactiveDesiredFractionOfFullRatedOutputBegin:" + get_reactiveDesiredFractionOfFullRatedOutputBegin()
			+ "," + "reactiveDesiredFractionOfFullRatedOutputEnd:" + get_reactiveDesiredFractionOfFullRatedOutputEnd()
			+ "," + "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin:" + get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin()
			+ "," + "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd:" + get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd()
			+ "," + "realDesiredFractionOfFullRatedOutputBegin:" + get_realDesiredFractionOfFullRatedOutputBegin()
			+ "," + "realDesiredFractionOfFullRatedOutputEnd:" + get_realDesiredFractionOfFullRatedOutputEnd()
			+ "," + "realRequiredFractionOfFullRatedInputPowerDrawnBegin:" + get_realRequiredFractionOfFullRatedInputPowerDrawnBegin()
			+ "," + "realRequiredFractionOfFullRatedInputPowerDrawnEnd:" + get_realRequiredFractionOfFullRatedInputPowerDrawnEnd()
			+ "," + "upBeginRamp:" + get_upBeginRamp()
			+ "," + "upDuration:" + get_upDuration()
			+ "," + "upRampToCompletion:" + get_upRampToCompletion()
			+ "," + "upRate:" + get_upRate()
			+ ")";
	}
	



	
	
	/**
	* Publishes the "Resources" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "Resources" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_Resources() {
		_publishAttributeNameSet.add( "Resources" );
	}

	/**
	* Unpublishes the "Resources" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "Resources" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_Resources() {
		_publishAttributeNameSet.remove( "Resources" );
	}
	
	/**
	* Subscribes a federate to the "Resources" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "Resources" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_Resources() {
		_subscribeAttributeNameSet.add( "Resources" );
	}

	/**
	* Unsubscribes a federate from the "Resources" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "Resources" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_Resources() {
		_subscribeAttributeNameSet.remove( "Resources" );
	}
	
	
	/**
	* Publishes the "activePowerCurve" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "activePowerCurve" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_activePowerCurve() {
		_publishAttributeNameSet.add( "activePowerCurve" );
	}

	/**
	* Unpublishes the "activePowerCurve" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "activePowerCurve" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_activePowerCurve() {
		_publishAttributeNameSet.remove( "activePowerCurve" );
	}
	
	/**
	* Subscribes a federate to the "activePowerCurve" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "activePowerCurve" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_activePowerCurve() {
		_subscribeAttributeNameSet.add( "activePowerCurve" );
	}

	/**
	* Unsubscribes a federate from the "activePowerCurve" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "activePowerCurve" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_activePowerCurve() {
		_subscribeAttributeNameSet.remove( "activePowerCurve" );
	}
	
	
	/**
	* Publishes the "actualDemand" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "actualDemand" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_actualDemand() {
		_publishAttributeNameSet.add( "actualDemand" );
	}

	/**
	* Unpublishes the "actualDemand" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "actualDemand" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_actualDemand() {
		_publishAttributeNameSet.remove( "actualDemand" );
	}
	
	/**
	* Subscribes a federate to the "actualDemand" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "actualDemand" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_actualDemand() {
		_subscribeAttributeNameSet.add( "actualDemand" );
	}

	/**
	* Unsubscribes a federate from the "actualDemand" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "actualDemand" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_actualDemand() {
		_subscribeAttributeNameSet.remove( "actualDemand" );
	}
	
	
	/**
	* Publishes the "adjustedFullDRPower" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "adjustedFullDRPower" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_adjustedFullDRPower() {
		_publishAttributeNameSet.add( "adjustedFullDRPower" );
	}

	/**
	* Unpublishes the "adjustedFullDRPower" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "adjustedFullDRPower" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_adjustedFullDRPower() {
		_publishAttributeNameSet.remove( "adjustedFullDRPower" );
	}
	
	/**
	* Subscribes a federate to the "adjustedFullDRPower" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "adjustedFullDRPower" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_adjustedFullDRPower() {
		_subscribeAttributeNameSet.add( "adjustedFullDRPower" );
	}

	/**
	* Unsubscribes a federate from the "adjustedFullDRPower" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "adjustedFullDRPower" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_adjustedFullDRPower() {
		_subscribeAttributeNameSet.remove( "adjustedFullDRPower" );
	}
	
	
	/**
	* Publishes the "adjustedNoDRPower" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "adjustedNoDRPower" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_adjustedNoDRPower() {
		_publishAttributeNameSet.add( "adjustedNoDRPower" );
	}

	/**
	* Unpublishes the "adjustedNoDRPower" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "adjustedNoDRPower" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_adjustedNoDRPower() {
		_publishAttributeNameSet.remove( "adjustedNoDRPower" );
	}
	
	/**
	* Subscribes a federate to the "adjustedNoDRPower" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "adjustedNoDRPower" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_adjustedNoDRPower() {
		_subscribeAttributeNameSet.add( "adjustedNoDRPower" );
	}

	/**
	* Unsubscribes a federate from the "adjustedNoDRPower" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "adjustedNoDRPower" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_adjustedNoDRPower() {
		_subscribeAttributeNameSet.remove( "adjustedNoDRPower" );
	}
	
	
	/**
	* Publishes the "downBeginRamp" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "downBeginRamp" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_downBeginRamp() {
		_publishAttributeNameSet.add( "downBeginRamp" );
	}

	/**
	* Unpublishes the "downBeginRamp" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "downBeginRamp" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_downBeginRamp() {
		_publishAttributeNameSet.remove( "downBeginRamp" );
	}
	
	/**
	* Subscribes a federate to the "downBeginRamp" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "downBeginRamp" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_downBeginRamp() {
		_subscribeAttributeNameSet.add( "downBeginRamp" );
	}

	/**
	* Unsubscribes a federate from the "downBeginRamp" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "downBeginRamp" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_downBeginRamp() {
		_subscribeAttributeNameSet.remove( "downBeginRamp" );
	}
	
	
	/**
	* Publishes the "downDuration" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "downDuration" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_downDuration() {
		_publishAttributeNameSet.add( "downDuration" );
	}

	/**
	* Unpublishes the "downDuration" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "downDuration" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_downDuration() {
		_publishAttributeNameSet.remove( "downDuration" );
	}
	
	/**
	* Subscribes a federate to the "downDuration" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "downDuration" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_downDuration() {
		_subscribeAttributeNameSet.add( "downDuration" );
	}

	/**
	* Unsubscribes a federate from the "downDuration" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "downDuration" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_downDuration() {
		_subscribeAttributeNameSet.remove( "downDuration" );
	}
	
	
	/**
	* Publishes the "downRampToCompletion" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "downRampToCompletion" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_downRampToCompletion() {
		_publishAttributeNameSet.add( "downRampToCompletion" );
	}

	/**
	* Unpublishes the "downRampToCompletion" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "downRampToCompletion" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_downRampToCompletion() {
		_publishAttributeNameSet.remove( "downRampToCompletion" );
	}
	
	/**
	* Subscribes a federate to the "downRampToCompletion" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "downRampToCompletion" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_downRampToCompletion() {
		_subscribeAttributeNameSet.add( "downRampToCompletion" );
	}

	/**
	* Unsubscribes a federate from the "downRampToCompletion" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "downRampToCompletion" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_downRampToCompletion() {
		_subscribeAttributeNameSet.remove( "downRampToCompletion" );
	}
	
	
	/**
	* Publishes the "downRate" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "downRate" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_downRate() {
		_publishAttributeNameSet.add( "downRate" );
	}

	/**
	* Unpublishes the "downRate" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "downRate" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_downRate() {
		_publishAttributeNameSet.remove( "downRate" );
	}
	
	/**
	* Subscribes a federate to the "downRate" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "downRate" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_downRate() {
		_subscribeAttributeNameSet.add( "downRate" );
	}

	/**
	* Unsubscribes a federate from the "downRate" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "downRate" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_downRate() {
		_subscribeAttributeNameSet.remove( "downRate" );
	}
	
	
	/**
	* Publishes the "loadStatusType" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "loadStatusType" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_loadStatusType() {
		_publishAttributeNameSet.add( "loadStatusType" );
	}

	/**
	* Unpublishes the "loadStatusType" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "loadStatusType" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_loadStatusType() {
		_publishAttributeNameSet.remove( "loadStatusType" );
	}
	
	/**
	* Subscribes a federate to the "loadStatusType" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "loadStatusType" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_loadStatusType() {
		_subscribeAttributeNameSet.add( "loadStatusType" );
	}

	/**
	* Unsubscribes a federate from the "loadStatusType" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "loadStatusType" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_loadStatusType() {
		_subscribeAttributeNameSet.remove( "loadStatusType" );
	}
	
	
	/**
	* Publishes the "locked" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "locked" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_locked() {
		_publishAttributeNameSet.add( "locked" );
	}

	/**
	* Unpublishes the "locked" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "locked" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_locked() {
		_publishAttributeNameSet.remove( "locked" );
	}
	
	/**
	* Subscribes a federate to the "locked" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "locked" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_locked() {
		_subscribeAttributeNameSet.add( "locked" );
	}

	/**
	* Unsubscribes a federate from the "locked" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "locked" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_locked() {
		_subscribeAttributeNameSet.remove( "locked" );
	}
	
	
	/**
	* Publishes the "maximumReactivePower" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "maximumReactivePower" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_maximumReactivePower() {
		_publishAttributeNameSet.add( "maximumReactivePower" );
	}

	/**
	* Unpublishes the "maximumReactivePower" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "maximumReactivePower" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_maximumReactivePower() {
		_publishAttributeNameSet.remove( "maximumReactivePower" );
	}
	
	/**
	* Subscribes a federate to the "maximumReactivePower" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "maximumReactivePower" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_maximumReactivePower() {
		_subscribeAttributeNameSet.add( "maximumReactivePower" );
	}

	/**
	* Unsubscribes a federate from the "maximumReactivePower" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "maximumReactivePower" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_maximumReactivePower() {
		_subscribeAttributeNameSet.remove( "maximumReactivePower" );
	}
	
	
	/**
	* Publishes the "maximumRealPower" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "maximumRealPower" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_maximumRealPower() {
		_publishAttributeNameSet.add( "maximumRealPower" );
	}

	/**
	* Unpublishes the "maximumRealPower" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "maximumRealPower" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_maximumRealPower() {
		_publishAttributeNameSet.remove( "maximumRealPower" );
	}
	
	/**
	* Subscribes a federate to the "maximumRealPower" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "maximumRealPower" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_maximumRealPower() {
		_subscribeAttributeNameSet.add( "maximumRealPower" );
	}

	/**
	* Unsubscribes a federate from the "maximumRealPower" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "maximumRealPower" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_maximumRealPower() {
		_subscribeAttributeNameSet.remove( "maximumRealPower" );
	}
	
	
	/**
	* Publishes the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_reactiveDesiredFractionOfFullRatedOutputBegin() {
		_publishAttributeNameSet.add( "reactiveDesiredFractionOfFullRatedOutputBegin" );
	}

	/**
	* Unpublishes the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_reactiveDesiredFractionOfFullRatedOutputBegin() {
		_publishAttributeNameSet.remove( "reactiveDesiredFractionOfFullRatedOutputBegin" );
	}
	
	/**
	* Subscribes a federate to the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_reactiveDesiredFractionOfFullRatedOutputBegin() {
		_subscribeAttributeNameSet.add( "reactiveDesiredFractionOfFullRatedOutputBegin" );
	}

	/**
	* Unsubscribes a federate from the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_reactiveDesiredFractionOfFullRatedOutputBegin() {
		_subscribeAttributeNameSet.remove( "reactiveDesiredFractionOfFullRatedOutputBegin" );
	}
	
	
	/**
	* Publishes the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_reactiveDesiredFractionOfFullRatedOutputEnd() {
		_publishAttributeNameSet.add( "reactiveDesiredFractionOfFullRatedOutputEnd" );
	}

	/**
	* Unpublishes the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_reactiveDesiredFractionOfFullRatedOutputEnd() {
		_publishAttributeNameSet.remove( "reactiveDesiredFractionOfFullRatedOutputEnd" );
	}
	
	/**
	* Subscribes a federate to the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_reactiveDesiredFractionOfFullRatedOutputEnd() {
		_subscribeAttributeNameSet.add( "reactiveDesiredFractionOfFullRatedOutputEnd" );
	}

	/**
	* Unsubscribes a federate from the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_reactiveDesiredFractionOfFullRatedOutputEnd() {
		_subscribeAttributeNameSet.remove( "reactiveDesiredFractionOfFullRatedOutputEnd" );
	}
	
	
	/**
	* Publishes the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin() {
		_publishAttributeNameSet.add( "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" );
	}

	/**
	* Unpublishes the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin() {
		_publishAttributeNameSet.remove( "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" );
	}
	
	/**
	* Subscribes a federate to the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin() {
		_subscribeAttributeNameSet.add( "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" );
	}

	/**
	* Unsubscribes a federate from the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin() {
		_subscribeAttributeNameSet.remove( "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" );
	}
	
	
	/**
	* Publishes the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd() {
		_publishAttributeNameSet.add( "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" );
	}

	/**
	* Unpublishes the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd() {
		_publishAttributeNameSet.remove( "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" );
	}
	
	/**
	* Subscribes a federate to the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd() {
		_subscribeAttributeNameSet.add( "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" );
	}

	/**
	* Unsubscribes a federate from the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd() {
		_subscribeAttributeNameSet.remove( "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" );
	}
	
	
	/**
	* Publishes the "realDesiredFractionOfFullRatedOutputBegin" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "realDesiredFractionOfFullRatedOutputBegin" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_realDesiredFractionOfFullRatedOutputBegin() {
		_publishAttributeNameSet.add( "realDesiredFractionOfFullRatedOutputBegin" );
	}

	/**
	* Unpublishes the "realDesiredFractionOfFullRatedOutputBegin" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "realDesiredFractionOfFullRatedOutputBegin" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_realDesiredFractionOfFullRatedOutputBegin() {
		_publishAttributeNameSet.remove( "realDesiredFractionOfFullRatedOutputBegin" );
	}
	
	/**
	* Subscribes a federate to the "realDesiredFractionOfFullRatedOutputBegin" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "realDesiredFractionOfFullRatedOutputBegin" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_realDesiredFractionOfFullRatedOutputBegin() {
		_subscribeAttributeNameSet.add( "realDesiredFractionOfFullRatedOutputBegin" );
	}

	/**
	* Unsubscribes a federate from the "realDesiredFractionOfFullRatedOutputBegin" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "realDesiredFractionOfFullRatedOutputBegin" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_realDesiredFractionOfFullRatedOutputBegin() {
		_subscribeAttributeNameSet.remove( "realDesiredFractionOfFullRatedOutputBegin" );
	}
	
	
	/**
	* Publishes the "realDesiredFractionOfFullRatedOutputEnd" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "realDesiredFractionOfFullRatedOutputEnd" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_realDesiredFractionOfFullRatedOutputEnd() {
		_publishAttributeNameSet.add( "realDesiredFractionOfFullRatedOutputEnd" );
	}

	/**
	* Unpublishes the "realDesiredFractionOfFullRatedOutputEnd" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "realDesiredFractionOfFullRatedOutputEnd" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_realDesiredFractionOfFullRatedOutputEnd() {
		_publishAttributeNameSet.remove( "realDesiredFractionOfFullRatedOutputEnd" );
	}
	
	/**
	* Subscribes a federate to the "realDesiredFractionOfFullRatedOutputEnd" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "realDesiredFractionOfFullRatedOutputEnd" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_realDesiredFractionOfFullRatedOutputEnd() {
		_subscribeAttributeNameSet.add( "realDesiredFractionOfFullRatedOutputEnd" );
	}

	/**
	* Unsubscribes a federate from the "realDesiredFractionOfFullRatedOutputEnd" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "realDesiredFractionOfFullRatedOutputEnd" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_realDesiredFractionOfFullRatedOutputEnd() {
		_subscribeAttributeNameSet.remove( "realDesiredFractionOfFullRatedOutputEnd" );
	}
	
	
	/**
	* Publishes the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_realRequiredFractionOfFullRatedInputPowerDrawnBegin() {
		_publishAttributeNameSet.add( "realRequiredFractionOfFullRatedInputPowerDrawnBegin" );
	}

	/**
	* Unpublishes the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_realRequiredFractionOfFullRatedInputPowerDrawnBegin() {
		_publishAttributeNameSet.remove( "realRequiredFractionOfFullRatedInputPowerDrawnBegin" );
	}
	
	/**
	* Subscribes a federate to the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_realRequiredFractionOfFullRatedInputPowerDrawnBegin() {
		_subscribeAttributeNameSet.add( "realRequiredFractionOfFullRatedInputPowerDrawnBegin" );
	}

	/**
	* Unsubscribes a federate from the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_realRequiredFractionOfFullRatedInputPowerDrawnBegin() {
		_subscribeAttributeNameSet.remove( "realRequiredFractionOfFullRatedInputPowerDrawnBegin" );
	}
	
	
	/**
	* Publishes the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_realRequiredFractionOfFullRatedInputPowerDrawnEnd() {
		_publishAttributeNameSet.add( "realRequiredFractionOfFullRatedInputPowerDrawnEnd" );
	}

	/**
	* Unpublishes the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_realRequiredFractionOfFullRatedInputPowerDrawnEnd() {
		_publishAttributeNameSet.remove( "realRequiredFractionOfFullRatedInputPowerDrawnEnd" );
	}
	
	/**
	* Subscribes a federate to the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_realRequiredFractionOfFullRatedInputPowerDrawnEnd() {
		_subscribeAttributeNameSet.add( "realRequiredFractionOfFullRatedInputPowerDrawnEnd" );
	}

	/**
	* Unsubscribes a federate from the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_realRequiredFractionOfFullRatedInputPowerDrawnEnd() {
		_subscribeAttributeNameSet.remove( "realRequiredFractionOfFullRatedInputPowerDrawnEnd" );
	}
	
	
	/**
	* Publishes the "upBeginRamp" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "upBeginRamp" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_upBeginRamp() {
		_publishAttributeNameSet.add( "upBeginRamp" );
	}

	/**
	* Unpublishes the "upBeginRamp" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "upBeginRamp" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_upBeginRamp() {
		_publishAttributeNameSet.remove( "upBeginRamp" );
	}
	
	/**
	* Subscribes a federate to the "upBeginRamp" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "upBeginRamp" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_upBeginRamp() {
		_subscribeAttributeNameSet.add( "upBeginRamp" );
	}

	/**
	* Unsubscribes a federate from the "upBeginRamp" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "upBeginRamp" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_upBeginRamp() {
		_subscribeAttributeNameSet.remove( "upBeginRamp" );
	}
	
	
	/**
	* Publishes the "upDuration" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "upDuration" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_upDuration() {
		_publishAttributeNameSet.add( "upDuration" );
	}

	/**
	* Unpublishes the "upDuration" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "upDuration" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_upDuration() {
		_publishAttributeNameSet.remove( "upDuration" );
	}
	
	/**
	* Subscribes a federate to the "upDuration" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "upDuration" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_upDuration() {
		_subscribeAttributeNameSet.add( "upDuration" );
	}

	/**
	* Unsubscribes a federate from the "upDuration" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "upDuration" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_upDuration() {
		_subscribeAttributeNameSet.remove( "upDuration" );
	}
	
	
	/**
	* Publishes the "upRampToCompletion" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "upRampToCompletion" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_upRampToCompletion() {
		_publishAttributeNameSet.add( "upRampToCompletion" );
	}

	/**
	* Unpublishes the "upRampToCompletion" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "upRampToCompletion" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_upRampToCompletion() {
		_publishAttributeNameSet.remove( "upRampToCompletion" );
	}
	
	/**
	* Subscribes a federate to the "upRampToCompletion" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "upRampToCompletion" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_upRampToCompletion() {
		_subscribeAttributeNameSet.add( "upRampToCompletion" );
	}

	/**
	* Unsubscribes a federate from the "upRampToCompletion" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "upRampToCompletion" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_upRampToCompletion() {
		_subscribeAttributeNameSet.remove( "upRampToCompletion" );
	}
	
	
	/**
	* Publishes the "upRate" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "upRate" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_upRate() {
		_publishAttributeNameSet.add( "upRate" );
	}

	/**
	* Unpublishes the "upRate" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "upRate" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_upRate() {
		_publishAttributeNameSet.remove( "upRate" );
	}
	
	/**
	* Subscribes a federate to the "upRate" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "upRate" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_upRate() {
		_subscribeAttributeNameSet.add( "upRate" );
	}

	/**
	* Unsubscribes a federate from the "upRate" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "upRate" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_upRate() {
		_subscribeAttributeNameSet.remove( "upRate" );
	}
	

	
	
	private Attribute< String > _Resources =
 		new Attribute< String >(  new String( "" )  );
	
	/**
	* Set the value of the "Resources" attribute to "value" for this object.
	*
	* @param value the new value for the "Resources" attribute
	*/
	public void set_Resources( String value ) {
		_Resources.setValue( value );
		_Resources.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "Resources" attribute of this object.
	*
	* @return the value of the "Resources" attribute
	*/
	public String get_Resources() {
		return _Resources.getValue();
	}
	
	/**
	* Returns the current timestamp of the "Resources" attribute of this object.
	* 
	* @return the current timestamp of the "Resources" attribute
	*/
	public double get_Resources_time() {
		return _Resources.getTime();
	}
	
	
	private Attribute< Integer > _activePowerCurve =
 		new Attribute< Integer >(  new Integer( 0 )  );
	
	/**
	* Set the value of the "activePowerCurve" attribute to "value" for this object.
	*
	* @param value the new value for the "activePowerCurve" attribute
	*/
	public void set_activePowerCurve( int value ) {
		_activePowerCurve.setValue( value );
		_activePowerCurve.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "activePowerCurve" attribute of this object.
	*
	* @return the value of the "activePowerCurve" attribute
	*/
	public int get_activePowerCurve() {
		return _activePowerCurve.getValue();
	}
	
	/**
	* Returns the current timestamp of the "activePowerCurve" attribute of this object.
	* 
	* @return the current timestamp of the "activePowerCurve" attribute
	*/
	public double get_activePowerCurve_time() {
		return _activePowerCurve.getTime();
	}
	
	
	private Attribute< Float > _actualDemand =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "actualDemand" attribute to "value" for this object.
	*
	* @param value the new value for the "actualDemand" attribute
	*/
	public void set_actualDemand( float value ) {
		_actualDemand.setValue( value );
		_actualDemand.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "actualDemand" attribute of this object.
	*
	* @return the value of the "actualDemand" attribute
	*/
	public float get_actualDemand() {
		return _actualDemand.getValue();
	}
	
	/**
	* Returns the current timestamp of the "actualDemand" attribute of this object.
	* 
	* @return the current timestamp of the "actualDemand" attribute
	*/
	public double get_actualDemand_time() {
		return _actualDemand.getTime();
	}
	
	
	private Attribute< Float > _adjustedFullDRPower =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "adjustedFullDRPower" attribute to "value" for this object.
	*
	* @param value the new value for the "adjustedFullDRPower" attribute
	*/
	public void set_adjustedFullDRPower( float value ) {
		_adjustedFullDRPower.setValue( value );
		_adjustedFullDRPower.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "adjustedFullDRPower" attribute of this object.
	*
	* @return the value of the "adjustedFullDRPower" attribute
	*/
	public float get_adjustedFullDRPower() {
		return _adjustedFullDRPower.getValue();
	}
	
	/**
	* Returns the current timestamp of the "adjustedFullDRPower" attribute of this object.
	* 
	* @return the current timestamp of the "adjustedFullDRPower" attribute
	*/
	public double get_adjustedFullDRPower_time() {
		return _adjustedFullDRPower.getTime();
	}
	
	
	private Attribute< Float > _adjustedNoDRPower =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "adjustedNoDRPower" attribute to "value" for this object.
	*
	* @param value the new value for the "adjustedNoDRPower" attribute
	*/
	public void set_adjustedNoDRPower( float value ) {
		_adjustedNoDRPower.setValue( value );
		_adjustedNoDRPower.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "adjustedNoDRPower" attribute of this object.
	*
	* @return the value of the "adjustedNoDRPower" attribute
	*/
	public float get_adjustedNoDRPower() {
		return _adjustedNoDRPower.getValue();
	}
	
	/**
	* Returns the current timestamp of the "adjustedNoDRPower" attribute of this object.
	* 
	* @return the current timestamp of the "adjustedNoDRPower" attribute
	*/
	public double get_adjustedNoDRPower_time() {
		return _adjustedNoDRPower.getTime();
	}
	
	
	private Attribute< Float > _downBeginRamp =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "downBeginRamp" attribute to "value" for this object.
	*
	* @param value the new value for the "downBeginRamp" attribute
	*/
	public void set_downBeginRamp( float value ) {
		_downBeginRamp.setValue( value );
		_downBeginRamp.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "downBeginRamp" attribute of this object.
	*
	* @return the value of the "downBeginRamp" attribute
	*/
	public float get_downBeginRamp() {
		return _downBeginRamp.getValue();
	}
	
	/**
	* Returns the current timestamp of the "downBeginRamp" attribute of this object.
	* 
	* @return the current timestamp of the "downBeginRamp" attribute
	*/
	public double get_downBeginRamp_time() {
		return _downBeginRamp.getTime();
	}
	
	
	private Attribute< Integer > _downDuration =
 		new Attribute< Integer >(  new Integer( 0 )  );
	
	/**
	* Set the value of the "downDuration" attribute to "value" for this object.
	*
	* @param value the new value for the "downDuration" attribute
	*/
	public void set_downDuration( int value ) {
		_downDuration.setValue( value );
		_downDuration.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "downDuration" attribute of this object.
	*
	* @return the value of the "downDuration" attribute
	*/
	public int get_downDuration() {
		return _downDuration.getValue();
	}
	
	/**
	* Returns the current timestamp of the "downDuration" attribute of this object.
	* 
	* @return the current timestamp of the "downDuration" attribute
	*/
	public double get_downDuration_time() {
		return _downDuration.getTime();
	}
	
	
	private Attribute< Boolean > _downRampToCompletion =
 		new Attribute< Boolean >(  new Boolean( false )  );
	
	/**
	* Set the value of the "downRampToCompletion" attribute to "value" for this object.
	*
	* @param value the new value for the "downRampToCompletion" attribute
	*/
	public void set_downRampToCompletion( boolean value ) {
		_downRampToCompletion.setValue( value );
		_downRampToCompletion.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "downRampToCompletion" attribute of this object.
	*
	* @return the value of the "downRampToCompletion" attribute
	*/
	public boolean get_downRampToCompletion() {
		return _downRampToCompletion.getValue();
	}
	
	/**
	* Returns the current timestamp of the "downRampToCompletion" attribute of this object.
	* 
	* @return the current timestamp of the "downRampToCompletion" attribute
	*/
	public double get_downRampToCompletion_time() {
		return _downRampToCompletion.getTime();
	}
	
	
	private Attribute< Float > _downRate =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "downRate" attribute to "value" for this object.
	*
	* @param value the new value for the "downRate" attribute
	*/
	public void set_downRate( float value ) {
		_downRate.setValue( value );
		_downRate.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "downRate" attribute of this object.
	*
	* @return the value of the "downRate" attribute
	*/
	public float get_downRate() {
		return _downRate.getValue();
	}
	
	/**
	* Returns the current timestamp of the "downRate" attribute of this object.
	* 
	* @return the current timestamp of the "downRate" attribute
	*/
	public double get_downRate_time() {
		return _downRate.getTime();
	}
	
	
	private Attribute< String > _loadStatusType =
 		new Attribute< String >(  new String( "" )  );
	
	/**
	* Set the value of the "loadStatusType" attribute to "value" for this object.
	*
	* @param value the new value for the "loadStatusType" attribute
	*/
	public void set_loadStatusType( String value ) {
		_loadStatusType.setValue( value );
		_loadStatusType.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "loadStatusType" attribute of this object.
	*
	* @return the value of the "loadStatusType" attribute
	*/
	public String get_loadStatusType() {
		return _loadStatusType.getValue();
	}
	
	/**
	* Returns the current timestamp of the "loadStatusType" attribute of this object.
	* 
	* @return the current timestamp of the "loadStatusType" attribute
	*/
	public double get_loadStatusType_time() {
		return _loadStatusType.getTime();
	}
	
	
	private Attribute< Boolean > _locked =
 		new Attribute< Boolean >(  new Boolean( false )  );
	
	/**
	* Set the value of the "locked" attribute to "value" for this object.
	*
	* @param value the new value for the "locked" attribute
	*/
	public void set_locked( boolean value ) {
		_locked.setValue( value );
		_locked.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "locked" attribute of this object.
	*
	* @return the value of the "locked" attribute
	*/
	public boolean get_locked() {
		return _locked.getValue();
	}
	
	/**
	* Returns the current timestamp of the "locked" attribute of this object.
	* 
	* @return the current timestamp of the "locked" attribute
	*/
	public double get_locked_time() {
		return _locked.getTime();
	}
	
	
	private Attribute< Float > _maximumReactivePower =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "maximumReactivePower" attribute to "value" for this object.
	*
	* @param value the new value for the "maximumReactivePower" attribute
	*/
	public void set_maximumReactivePower( float value ) {
		_maximumReactivePower.setValue( value );
		_maximumReactivePower.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "maximumReactivePower" attribute of this object.
	*
	* @return the value of the "maximumReactivePower" attribute
	*/
	public float get_maximumReactivePower() {
		return _maximumReactivePower.getValue();
	}
	
	/**
	* Returns the current timestamp of the "maximumReactivePower" attribute of this object.
	* 
	* @return the current timestamp of the "maximumReactivePower" attribute
	*/
	public double get_maximumReactivePower_time() {
		return _maximumReactivePower.getTime();
	}
	
	
	private Attribute< Float > _maximumRealPower =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "maximumRealPower" attribute to "value" for this object.
	*
	* @param value the new value for the "maximumRealPower" attribute
	*/
	public void set_maximumRealPower( float value ) {
		_maximumRealPower.setValue( value );
		_maximumRealPower.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "maximumRealPower" attribute of this object.
	*
	* @return the value of the "maximumRealPower" attribute
	*/
	public float get_maximumRealPower() {
		return _maximumRealPower.getValue();
	}
	
	/**
	* Returns the current timestamp of the "maximumRealPower" attribute of this object.
	* 
	* @return the current timestamp of the "maximumRealPower" attribute
	*/
	public double get_maximumRealPower_time() {
		return _maximumRealPower.getTime();
	}
	
	
	private Attribute< Float > _reactiveDesiredFractionOfFullRatedOutputBegin =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute to "value" for this object.
	*
	* @param value the new value for the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute
	*/
	public void set_reactiveDesiredFractionOfFullRatedOutputBegin( float value ) {
		_reactiveDesiredFractionOfFullRatedOutputBegin.setValue( value );
		_reactiveDesiredFractionOfFullRatedOutputBegin.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute of this object.
	*
	* @return the value of the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute
	*/
	public float get_reactiveDesiredFractionOfFullRatedOutputBegin() {
		return _reactiveDesiredFractionOfFullRatedOutputBegin.getValue();
	}
	
	/**
	* Returns the current timestamp of the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute of this object.
	* 
	* @return the current timestamp of the "reactiveDesiredFractionOfFullRatedOutputBegin" attribute
	*/
	public double get_reactiveDesiredFractionOfFullRatedOutputBegin_time() {
		return _reactiveDesiredFractionOfFullRatedOutputBegin.getTime();
	}
	
	
	private Attribute< Float > _reactiveDesiredFractionOfFullRatedOutputEnd =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute to "value" for this object.
	*
	* @param value the new value for the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute
	*/
	public void set_reactiveDesiredFractionOfFullRatedOutputEnd( float value ) {
		_reactiveDesiredFractionOfFullRatedOutputEnd.setValue( value );
		_reactiveDesiredFractionOfFullRatedOutputEnd.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute of this object.
	*
	* @return the value of the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute
	*/
	public float get_reactiveDesiredFractionOfFullRatedOutputEnd() {
		return _reactiveDesiredFractionOfFullRatedOutputEnd.getValue();
	}
	
	/**
	* Returns the current timestamp of the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute of this object.
	* 
	* @return the current timestamp of the "reactiveDesiredFractionOfFullRatedOutputEnd" attribute
	*/
	public double get_reactiveDesiredFractionOfFullRatedOutputEnd_time() {
		return _reactiveDesiredFractionOfFullRatedOutputEnd.getTime();
	}
	
	
	private Attribute< Float > _reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute to "value" for this object.
	*
	* @param value the new value for the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute
	*/
	public void set_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin( float value ) {
		_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin.setValue( value );
		_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of this object.
	*
	* @return the value of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute
	*/
	public float get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin() {
		return _reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin.getValue();
	}
	
	/**
	* Returns the current timestamp of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of this object.
	* 
	* @return the current timestamp of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute
	*/
	public double get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin_time() {
		return _reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin.getTime();
	}
	
	
	private Attribute< Float > _reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute to "value" for this object.
	*
	* @param value the new value for the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute
	*/
	public void set_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd( float value ) {
		_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd.setValue( value );
		_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of this object.
	*
	* @return the value of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute
	*/
	public float get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd() {
		return _reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd.getValue();
	}
	
	/**
	* Returns the current timestamp of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of this object.
	* 
	* @return the current timestamp of the "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute
	*/
	public double get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd_time() {
		return _reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd.getTime();
	}
	
	
	private Attribute< Float > _realDesiredFractionOfFullRatedOutputBegin =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "realDesiredFractionOfFullRatedOutputBegin" attribute to "value" for this object.
	*
	* @param value the new value for the "realDesiredFractionOfFullRatedOutputBegin" attribute
	*/
	public void set_realDesiredFractionOfFullRatedOutputBegin( float value ) {
		_realDesiredFractionOfFullRatedOutputBegin.setValue( value );
		_realDesiredFractionOfFullRatedOutputBegin.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "realDesiredFractionOfFullRatedOutputBegin" attribute of this object.
	*
	* @return the value of the "realDesiredFractionOfFullRatedOutputBegin" attribute
	*/
	public float get_realDesiredFractionOfFullRatedOutputBegin() {
		return _realDesiredFractionOfFullRatedOutputBegin.getValue();
	}
	
	/**
	* Returns the current timestamp of the "realDesiredFractionOfFullRatedOutputBegin" attribute of this object.
	* 
	* @return the current timestamp of the "realDesiredFractionOfFullRatedOutputBegin" attribute
	*/
	public double get_realDesiredFractionOfFullRatedOutputBegin_time() {
		return _realDesiredFractionOfFullRatedOutputBegin.getTime();
	}
	
	
	private Attribute< Float > _realDesiredFractionOfFullRatedOutputEnd =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "realDesiredFractionOfFullRatedOutputEnd" attribute to "value" for this object.
	*
	* @param value the new value for the "realDesiredFractionOfFullRatedOutputEnd" attribute
	*/
	public void set_realDesiredFractionOfFullRatedOutputEnd( float value ) {
		_realDesiredFractionOfFullRatedOutputEnd.setValue( value );
		_realDesiredFractionOfFullRatedOutputEnd.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "realDesiredFractionOfFullRatedOutputEnd" attribute of this object.
	*
	* @return the value of the "realDesiredFractionOfFullRatedOutputEnd" attribute
	*/
	public float get_realDesiredFractionOfFullRatedOutputEnd() {
		return _realDesiredFractionOfFullRatedOutputEnd.getValue();
	}
	
	/**
	* Returns the current timestamp of the "realDesiredFractionOfFullRatedOutputEnd" attribute of this object.
	* 
	* @return the current timestamp of the "realDesiredFractionOfFullRatedOutputEnd" attribute
	*/
	public double get_realDesiredFractionOfFullRatedOutputEnd_time() {
		return _realDesiredFractionOfFullRatedOutputEnd.getTime();
	}
	
	
	private Attribute< Float > _realRequiredFractionOfFullRatedInputPowerDrawnBegin =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute to "value" for this object.
	*
	* @param value the new value for the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute
	*/
	public void set_realRequiredFractionOfFullRatedInputPowerDrawnBegin( float value ) {
		_realRequiredFractionOfFullRatedInputPowerDrawnBegin.setValue( value );
		_realRequiredFractionOfFullRatedInputPowerDrawnBegin.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of this object.
	*
	* @return the value of the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute
	*/
	public float get_realRequiredFractionOfFullRatedInputPowerDrawnBegin() {
		return _realRequiredFractionOfFullRatedInputPowerDrawnBegin.getValue();
	}
	
	/**
	* Returns the current timestamp of the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute of this object.
	* 
	* @return the current timestamp of the "realRequiredFractionOfFullRatedInputPowerDrawnBegin" attribute
	*/
	public double get_realRequiredFractionOfFullRatedInputPowerDrawnBegin_time() {
		return _realRequiredFractionOfFullRatedInputPowerDrawnBegin.getTime();
	}
	
	
	private Attribute< Float > _realRequiredFractionOfFullRatedInputPowerDrawnEnd =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute to "value" for this object.
	*
	* @param value the new value for the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute
	*/
	public void set_realRequiredFractionOfFullRatedInputPowerDrawnEnd( float value ) {
		_realRequiredFractionOfFullRatedInputPowerDrawnEnd.setValue( value );
		_realRequiredFractionOfFullRatedInputPowerDrawnEnd.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of this object.
	*
	* @return the value of the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute
	*/
	public float get_realRequiredFractionOfFullRatedInputPowerDrawnEnd() {
		return _realRequiredFractionOfFullRatedInputPowerDrawnEnd.getValue();
	}
	
	/**
	* Returns the current timestamp of the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute of this object.
	* 
	* @return the current timestamp of the "realRequiredFractionOfFullRatedInputPowerDrawnEnd" attribute
	*/
	public double get_realRequiredFractionOfFullRatedInputPowerDrawnEnd_time() {
		return _realRequiredFractionOfFullRatedInputPowerDrawnEnd.getTime();
	}
	
	
	private Attribute< Float > _upBeginRamp =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "upBeginRamp" attribute to "value" for this object.
	*
	* @param value the new value for the "upBeginRamp" attribute
	*/
	public void set_upBeginRamp( float value ) {
		_upBeginRamp.setValue( value );
		_upBeginRamp.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "upBeginRamp" attribute of this object.
	*
	* @return the value of the "upBeginRamp" attribute
	*/
	public float get_upBeginRamp() {
		return _upBeginRamp.getValue();
	}
	
	/**
	* Returns the current timestamp of the "upBeginRamp" attribute of this object.
	* 
	* @return the current timestamp of the "upBeginRamp" attribute
	*/
	public double get_upBeginRamp_time() {
		return _upBeginRamp.getTime();
	}
	
	
	private Attribute< Integer > _upDuration =
 		new Attribute< Integer >(  new Integer( 0 )  );
	
	/**
	* Set the value of the "upDuration" attribute to "value" for this object.
	*
	* @param value the new value for the "upDuration" attribute
	*/
	public void set_upDuration( int value ) {
		_upDuration.setValue( value );
		_upDuration.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "upDuration" attribute of this object.
	*
	* @return the value of the "upDuration" attribute
	*/
	public int get_upDuration() {
		return _upDuration.getValue();
	}
	
	/**
	* Returns the current timestamp of the "upDuration" attribute of this object.
	* 
	* @return the current timestamp of the "upDuration" attribute
	*/
	public double get_upDuration_time() {
		return _upDuration.getTime();
	}
	
	
	private Attribute< Boolean > _upRampToCompletion =
 		new Attribute< Boolean >(  new Boolean( false )  );
	
	/**
	* Set the value of the "upRampToCompletion" attribute to "value" for this object.
	*
	* @param value the new value for the "upRampToCompletion" attribute
	*/
	public void set_upRampToCompletion( boolean value ) {
		_upRampToCompletion.setValue( value );
		_upRampToCompletion.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "upRampToCompletion" attribute of this object.
	*
	* @return the value of the "upRampToCompletion" attribute
	*/
	public boolean get_upRampToCompletion() {
		return _upRampToCompletion.getValue();
	}
	
	/**
	* Returns the current timestamp of the "upRampToCompletion" attribute of this object.
	* 
	* @return the current timestamp of the "upRampToCompletion" attribute
	*/
	public double get_upRampToCompletion_time() {
		return _upRampToCompletion.getTime();
	}
	
	
	private Attribute< Float > _upRate =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "upRate" attribute to "value" for this object.
	*
	* @param value the new value for the "upRate" attribute
	*/
	public void set_upRate( float value ) {
		_upRate.setValue( value );
		_upRate.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "upRate" attribute of this object.
	*
	* @return the value of the "upRate" attribute
	*/
	public float get_upRate() {
		return _upRate.getValue();
	}
	
	/**
	* Returns the current timestamp of the "upRate" attribute of this object.
	* 
	* @return the current timestamp of the "upRate" attribute
	*/
	public double get_upRate_time() {
		return _upRate.getTime();
	}
	


	protected resourceControl( ReflectedAttributes datamemberMap, boolean initFlag ) {
		super( datamemberMap, false );
		if ( initFlag ) setAttributes( datamemberMap );
	}
	
	protected resourceControl( ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
		super( datamemberMap, logicalTime, false );
		if ( initFlag ) setAttributes( datamemberMap );
	}


	/**
	* Creates an instance of the resourceControl object class, using
	* "datamemberMap" to initialize its attribute values.
	* "datamemberMap" is usually acquired as an argument to an RTI federate
	* callback method, such as "receiveInteraction".
	*
	* @param datamemberMap data structure containing initial values for the
	* attributes of this new resourceControl object class instance
	*/
	public resourceControl( ReflectedAttributes datamemberMap ) {
		this( datamemberMap, true );
	}
	
	/**
	* Like {@link #resourceControl( ReflectedAttributes datamemberMap )}, except this
	* new resourceControl object class instance is given a timestamp of
	* "logicalTime".
	*
	* @param datamemberMap data structure containing initial values for the
	* attributes of this new resourceControl object class instance
	* @param logicalTime timestamp for this new resourceControl object class
	* instance
	*/
	public resourceControl( ReflectedAttributes datamemberMap, LogicalTime logicalTime ) {
		this( datamemberMap, logicalTime, true );
	}

	/**
	* Creates a new resourceControl object class instance that is a duplicate
	* of the instance referred to by resourceControl_var.
	*
	* @param resourceControl_var resourceControl object class instance of which
	* this newly created resourceControl object class instance will be a
	* duplicate
	*/
	public resourceControl( resourceControl resourceControl_var ) {
		super( resourceControl_var );
		
		
		set_Resources( resourceControl_var.get_Resources() );
		set_activePowerCurve( resourceControl_var.get_activePowerCurve() );
		set_actualDemand( resourceControl_var.get_actualDemand() );
		set_adjustedFullDRPower( resourceControl_var.get_adjustedFullDRPower() );
		set_adjustedNoDRPower( resourceControl_var.get_adjustedNoDRPower() );
		set_downBeginRamp( resourceControl_var.get_downBeginRamp() );
		set_downDuration( resourceControl_var.get_downDuration() );
		set_downRampToCompletion( resourceControl_var.get_downRampToCompletion() );
		set_downRate( resourceControl_var.get_downRate() );
		set_loadStatusType( resourceControl_var.get_loadStatusType() );
		set_locked( resourceControl_var.get_locked() );
		set_maximumReactivePower( resourceControl_var.get_maximumReactivePower() );
		set_maximumRealPower( resourceControl_var.get_maximumRealPower() );
		set_reactiveDesiredFractionOfFullRatedOutputBegin( resourceControl_var.get_reactiveDesiredFractionOfFullRatedOutputBegin() );
		set_reactiveDesiredFractionOfFullRatedOutputEnd( resourceControl_var.get_reactiveDesiredFractionOfFullRatedOutputEnd() );
		set_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin( resourceControl_var.get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin() );
		set_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd( resourceControl_var.get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd() );
		set_realDesiredFractionOfFullRatedOutputBegin( resourceControl_var.get_realDesiredFractionOfFullRatedOutputBegin() );
		set_realDesiredFractionOfFullRatedOutputEnd( resourceControl_var.get_realDesiredFractionOfFullRatedOutputEnd() );
		set_realRequiredFractionOfFullRatedInputPowerDrawnBegin( resourceControl_var.get_realRequiredFractionOfFullRatedInputPowerDrawnBegin() );
		set_realRequiredFractionOfFullRatedInputPowerDrawnEnd( resourceControl_var.get_realRequiredFractionOfFullRatedInputPowerDrawnEnd() );
		set_upBeginRamp( resourceControl_var.get_upBeginRamp() );
		set_upDuration( resourceControl_var.get_upDuration() );
		set_upRampToCompletion( resourceControl_var.get_upRampToCompletion() );
		set_upRate( resourceControl_var.get_upRate() );
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
		
		
		
		if (  "Resources".equals( datamemberName )  ) return get_Resources();
		else if (  "activePowerCurve".equals( datamemberName )  ) return new Integer(get_activePowerCurve());
		else if (  "actualDemand".equals( datamemberName )  ) return new Float(get_actualDemand());
		else if (  "adjustedFullDRPower".equals( datamemberName )  ) return new Float(get_adjustedFullDRPower());
		else if (  "adjustedNoDRPower".equals( datamemberName )  ) return new Float(get_adjustedNoDRPower());
		else if (  "downBeginRamp".equals( datamemberName )  ) return new Float(get_downBeginRamp());
		else if (  "downDuration".equals( datamemberName )  ) return new Integer(get_downDuration());
		else if (  "downRampToCompletion".equals( datamemberName )  ) return new Boolean(get_downRampToCompletion());
		else if (  "downRate".equals( datamemberName )  ) return new Float(get_downRate());
		else if (  "loadStatusType".equals( datamemberName )  ) return get_loadStatusType();
		else if (  "locked".equals( datamemberName )  ) return new Boolean(get_locked());
		else if (  "maximumReactivePower".equals( datamemberName )  ) return new Float(get_maximumReactivePower());
		else if (  "maximumRealPower".equals( datamemberName )  ) return new Float(get_maximumRealPower());
		else if (  "reactiveDesiredFractionOfFullRatedOutputBegin".equals( datamemberName )  ) return new Float(get_reactiveDesiredFractionOfFullRatedOutputBegin());
		else if (  "reactiveDesiredFractionOfFullRatedOutputEnd".equals( datamemberName )  ) return new Float(get_reactiveDesiredFractionOfFullRatedOutputEnd());
		else if (  "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin".equals( datamemberName )  ) return new Float(get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin());
		else if (  "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd".equals( datamemberName )  ) return new Float(get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd());
		else if (  "realDesiredFractionOfFullRatedOutputBegin".equals( datamemberName )  ) return new Float(get_realDesiredFractionOfFullRatedOutputBegin());
		else if (  "realDesiredFractionOfFullRatedOutputEnd".equals( datamemberName )  ) return new Float(get_realDesiredFractionOfFullRatedOutputEnd());
		else if (  "realRequiredFractionOfFullRatedInputPowerDrawnBegin".equals( datamemberName )  ) return new Float(get_realRequiredFractionOfFullRatedInputPowerDrawnBegin());
		else if (  "realRequiredFractionOfFullRatedInputPowerDrawnEnd".equals( datamemberName )  ) return new Float(get_realRequiredFractionOfFullRatedInputPowerDrawnEnd());
		else if (  "upBeginRamp".equals( datamemberName )  ) return new Float(get_upBeginRamp());
		else if (  "upDuration".equals( datamemberName )  ) return new Integer(get_upDuration());
		else if (  "upRampToCompletion".equals( datamemberName )  ) return new Boolean(get_upRampToCompletion());
		else if (  "upRate".equals( datamemberName )  ) return new Float(get_upRate());
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
		
				
		
		if ( get_Resources_handle() == datamemberHandle ) return get_Resources();
		else if ( get_activePowerCurve_handle() == datamemberHandle ) return new Integer(get_activePowerCurve());
		else if ( get_actualDemand_handle() == datamemberHandle ) return new Float(get_actualDemand());
		else if ( get_adjustedFullDRPower_handle() == datamemberHandle ) return new Float(get_adjustedFullDRPower());
		else if ( get_adjustedNoDRPower_handle() == datamemberHandle ) return new Float(get_adjustedNoDRPower());
		else if ( get_downBeginRamp_handle() == datamemberHandle ) return new Float(get_downBeginRamp());
		else if ( get_downDuration_handle() == datamemberHandle ) return new Integer(get_downDuration());
		else if ( get_downRampToCompletion_handle() == datamemberHandle ) return new Boolean(get_downRampToCompletion());
		else if ( get_downRate_handle() == datamemberHandle ) return new Float(get_downRate());
		else if ( get_loadStatusType_handle() == datamemberHandle ) return get_loadStatusType();
		else if ( get_locked_handle() == datamemberHandle ) return new Boolean(get_locked());
		else if ( get_maximumReactivePower_handle() == datamemberHandle ) return new Float(get_maximumReactivePower());
		else if ( get_maximumRealPower_handle() == datamemberHandle ) return new Float(get_maximumRealPower());
		else if ( get_reactiveDesiredFractionOfFullRatedOutputBegin_handle() == datamemberHandle ) return new Float(get_reactiveDesiredFractionOfFullRatedOutputBegin());
		else if ( get_reactiveDesiredFractionOfFullRatedOutputEnd_handle() == datamemberHandle ) return new Float(get_reactiveDesiredFractionOfFullRatedOutputEnd());
		else if ( get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin_handle() == datamemberHandle ) return new Float(get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin());
		else if ( get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd_handle() == datamemberHandle ) return new Float(get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd());
		else if ( get_realDesiredFractionOfFullRatedOutputBegin_handle() == datamemberHandle ) return new Float(get_realDesiredFractionOfFullRatedOutputBegin());
		else if ( get_realDesiredFractionOfFullRatedOutputEnd_handle() == datamemberHandle ) return new Float(get_realDesiredFractionOfFullRatedOutputEnd());
		else if ( get_realRequiredFractionOfFullRatedInputPowerDrawnBegin_handle() == datamemberHandle ) return new Float(get_realRequiredFractionOfFullRatedInputPowerDrawnBegin());
		else if ( get_realRequiredFractionOfFullRatedInputPowerDrawnEnd_handle() == datamemberHandle ) return new Float(get_realRequiredFractionOfFullRatedInputPowerDrawnEnd());
		else if ( get_upBeginRamp_handle() == datamemberHandle ) return new Float(get_upBeginRamp());
		else if ( get_upDuration_handle() == datamemberHandle ) return new Integer(get_upDuration());
		else if ( get_upRampToCompletion_handle() == datamemberHandle ) return new Boolean(get_upRampToCompletion());
		else if ( get_upRate_handle() == datamemberHandle ) return new Float(get_upRate());
		else return super.getAttribute( datamemberHandle );
	}
	
	protected boolean setAttributeAux( int param_handle, String val ) {
		boolean retval = true;		
		
			
		
		if ( param_handle == get_Resources_handle() ) set_Resources( val );
		else if ( param_handle == get_activePowerCurve_handle() ) set_activePowerCurve( Integer.parseInt(val) );
		else if ( param_handle == get_actualDemand_handle() ) set_actualDemand( Float.parseFloat(val) );
		else if ( param_handle == get_adjustedFullDRPower_handle() ) set_adjustedFullDRPower( Float.parseFloat(val) );
		else if ( param_handle == get_adjustedNoDRPower_handle() ) set_adjustedNoDRPower( Float.parseFloat(val) );
		else if ( param_handle == get_downBeginRamp_handle() ) set_downBeginRamp( Float.parseFloat(val) );
		else if ( param_handle == get_downDuration_handle() ) set_downDuration( Integer.parseInt(val) );
		else if ( param_handle == get_downRampToCompletion_handle() ) set_downRampToCompletion( Boolean.parseBoolean(val) );
		else if ( param_handle == get_downRate_handle() ) set_downRate( Float.parseFloat(val) );
		else if ( param_handle == get_loadStatusType_handle() ) set_loadStatusType( val );
		else if ( param_handle == get_locked_handle() ) set_locked( Boolean.parseBoolean(val) );
		else if ( param_handle == get_maximumReactivePower_handle() ) set_maximumReactivePower( Float.parseFloat(val) );
		else if ( param_handle == get_maximumRealPower_handle() ) set_maximumRealPower( Float.parseFloat(val) );
		else if ( param_handle == get_reactiveDesiredFractionOfFullRatedOutputBegin_handle() ) set_reactiveDesiredFractionOfFullRatedOutputBegin( Float.parseFloat(val) );
		else if ( param_handle == get_reactiveDesiredFractionOfFullRatedOutputEnd_handle() ) set_reactiveDesiredFractionOfFullRatedOutputEnd( Float.parseFloat(val) );
		else if ( param_handle == get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin_handle() ) set_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin( Float.parseFloat(val) );
		else if ( param_handle == get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd_handle() ) set_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd( Float.parseFloat(val) );
		else if ( param_handle == get_realDesiredFractionOfFullRatedOutputBegin_handle() ) set_realDesiredFractionOfFullRatedOutputBegin( Float.parseFloat(val) );
		else if ( param_handle == get_realDesiredFractionOfFullRatedOutputEnd_handle() ) set_realDesiredFractionOfFullRatedOutputEnd( Float.parseFloat(val) );
		else if ( param_handle == get_realRequiredFractionOfFullRatedInputPowerDrawnBegin_handle() ) set_realRequiredFractionOfFullRatedInputPowerDrawnBegin( Float.parseFloat(val) );
		else if ( param_handle == get_realRequiredFractionOfFullRatedInputPowerDrawnEnd_handle() ) set_realRequiredFractionOfFullRatedInputPowerDrawnEnd( Float.parseFloat(val) );
		else if ( param_handle == get_upBeginRamp_handle() ) set_upBeginRamp( Float.parseFloat(val) );
		else if ( param_handle == get_upDuration_handle() ) set_upDuration( Integer.parseInt(val) );
		else if ( param_handle == get_upRampToCompletion_handle() ) set_upRampToCompletion( Boolean.parseBoolean(val) );
		else if ( param_handle == get_upRate_handle() ) set_upRate( Float.parseFloat(val) );
		else retval = super.setAttributeAux( param_handle, val );
		
		return retval;
	}
	
	protected boolean setAttributeAux( String datamemberName, String val ) {
		boolean retval = true;
		
			
		
		if (  "Resources".equals( datamemberName )  ) set_Resources( val );
		else if (  "activePowerCurve".equals( datamemberName )  ) set_activePowerCurve( Integer.parseInt(val) );
		else if (  "actualDemand".equals( datamemberName )  ) set_actualDemand( Float.parseFloat(val) );
		else if (  "adjustedFullDRPower".equals( datamemberName )  ) set_adjustedFullDRPower( Float.parseFloat(val) );
		else if (  "adjustedNoDRPower".equals( datamemberName )  ) set_adjustedNoDRPower( Float.parseFloat(val) );
		else if (  "downBeginRamp".equals( datamemberName )  ) set_downBeginRamp( Float.parseFloat(val) );
		else if (  "downDuration".equals( datamemberName )  ) set_downDuration( Integer.parseInt(val) );
		else if (  "downRampToCompletion".equals( datamemberName )  ) set_downRampToCompletion( Boolean.parseBoolean(val) );
		else if (  "downRate".equals( datamemberName )  ) set_downRate( Float.parseFloat(val) );
		else if (  "loadStatusType".equals( datamemberName )  ) set_loadStatusType( val );
		else if (  "locked".equals( datamemberName )  ) set_locked( Boolean.parseBoolean(val) );
		else if (  "maximumReactivePower".equals( datamemberName )  ) set_maximumReactivePower( Float.parseFloat(val) );
		else if (  "maximumRealPower".equals( datamemberName )  ) set_maximumRealPower( Float.parseFloat(val) );
		else if (  "reactiveDesiredFractionOfFullRatedOutputBegin".equals( datamemberName )  ) set_reactiveDesiredFractionOfFullRatedOutputBegin( Float.parseFloat(val) );
		else if (  "reactiveDesiredFractionOfFullRatedOutputEnd".equals( datamemberName )  ) set_reactiveDesiredFractionOfFullRatedOutputEnd( Float.parseFloat(val) );
		else if (  "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin".equals( datamemberName )  ) set_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin( Float.parseFloat(val) );
		else if (  "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd".equals( datamemberName )  ) set_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd( Float.parseFloat(val) );
		else if (  "realDesiredFractionOfFullRatedOutputBegin".equals( datamemberName )  ) set_realDesiredFractionOfFullRatedOutputBegin( Float.parseFloat(val) );
		else if (  "realDesiredFractionOfFullRatedOutputEnd".equals( datamemberName )  ) set_realDesiredFractionOfFullRatedOutputEnd( Float.parseFloat(val) );
		else if (  "realRequiredFractionOfFullRatedInputPowerDrawnBegin".equals( datamemberName )  ) set_realRequiredFractionOfFullRatedInputPowerDrawnBegin( Float.parseFloat(val) );
		else if (  "realRequiredFractionOfFullRatedInputPowerDrawnEnd".equals( datamemberName )  ) set_realRequiredFractionOfFullRatedInputPowerDrawnEnd( Float.parseFloat(val) );
		else if (  "upBeginRamp".equals( datamemberName )  ) set_upBeginRamp( Float.parseFloat(val) );
		else if (  "upDuration".equals( datamemberName )  ) set_upDuration( Integer.parseInt(val) );
		else if (  "upRampToCompletion".equals( datamemberName )  ) set_upRampToCompletion( Boolean.parseBoolean(val) );
		else if (  "upRate".equals( datamemberName )  ) set_upRate( Float.parseFloat(val) );	
		else retval = super.setAttributeAux( datamemberName, val );
		
		return retval;
	}
	
	protected boolean setAttributeAux( String datamemberName, Object val ) {
		boolean retval = true;
		
		
		
		if (  "Resources".equals( datamemberName )  ) set_Resources( (String)val );
		else if (  "activePowerCurve".equals( datamemberName )  ) set_activePowerCurve( (Integer)val );
		else if (  "actualDemand".equals( datamemberName )  ) set_actualDemand( (Float)val );
		else if (  "adjustedFullDRPower".equals( datamemberName )  ) set_adjustedFullDRPower( (Float)val );
		else if (  "adjustedNoDRPower".equals( datamemberName )  ) set_adjustedNoDRPower( (Float)val );
		else if (  "downBeginRamp".equals( datamemberName )  ) set_downBeginRamp( (Float)val );
		else if (  "downDuration".equals( datamemberName )  ) set_downDuration( (Integer)val );
		else if (  "downRampToCompletion".equals( datamemberName )  ) set_downRampToCompletion( (Boolean)val );
		else if (  "downRate".equals( datamemberName )  ) set_downRate( (Float)val );
		else if (  "loadStatusType".equals( datamemberName )  ) set_loadStatusType( (String)val );
		else if (  "locked".equals( datamemberName )  ) set_locked( (Boolean)val );
		else if (  "maximumReactivePower".equals( datamemberName )  ) set_maximumReactivePower( (Float)val );
		else if (  "maximumRealPower".equals( datamemberName )  ) set_maximumRealPower( (Float)val );
		else if (  "reactiveDesiredFractionOfFullRatedOutputBegin".equals( datamemberName )  ) set_reactiveDesiredFractionOfFullRatedOutputBegin( (Float)val );
		else if (  "reactiveDesiredFractionOfFullRatedOutputEnd".equals( datamemberName )  ) set_reactiveDesiredFractionOfFullRatedOutputEnd( (Float)val );
		else if (  "reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin".equals( datamemberName )  ) set_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin( (Float)val );
		else if (  "reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd".equals( datamemberName )  ) set_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd( (Float)val );
		else if (  "realDesiredFractionOfFullRatedOutputBegin".equals( datamemberName )  ) set_realDesiredFractionOfFullRatedOutputBegin( (Float)val );
		else if (  "realDesiredFractionOfFullRatedOutputEnd".equals( datamemberName )  ) set_realDesiredFractionOfFullRatedOutputEnd( (Float)val );
		else if (  "realRequiredFractionOfFullRatedInputPowerDrawnBegin".equals( datamemberName )  ) set_realRequiredFractionOfFullRatedInputPowerDrawnBegin( (Float)val );
		else if (  "realRequiredFractionOfFullRatedInputPowerDrawnEnd".equals( datamemberName )  ) set_realRequiredFractionOfFullRatedInputPowerDrawnEnd( (Float)val );
		else if (  "upBeginRamp".equals( datamemberName )  ) set_upBeginRamp( (Float)val );
		else if (  "upDuration".equals( datamemberName )  ) set_upDuration( (Integer)val );
		else if (  "upRampToCompletion".equals( datamemberName )  ) set_upRampToCompletion( (Boolean)val );
		else if (  "upRate".equals( datamemberName )  ) set_upRate( (Float)val );		
		else retval = super.setAttributeAux( datamemberName, val );
		
		return retval;
	}

	protected SuppliedAttributes createSuppliedDatamembers( boolean force ) {
		SuppliedAttributes datamembers = super.createSuppliedDatamembers( force );

	
		boolean isPublished = false;
		
		
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_Resources_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if Resources is published.");
				isPublished = false;
			}
			if (  isPublished && _Resources.shouldBeUpdated( force )  ) {
				datamembers.add( get_Resources_handle(), get_Resources().getBytes() );
				_Resources.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_activePowerCurve_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if activePowerCurve is published.");
				isPublished = false;
			}
			if (  isPublished && _activePowerCurve.shouldBeUpdated( force )  ) {
				datamembers.add( get_activePowerCurve_handle(), Integer.toString(get_activePowerCurve()).getBytes() );
				_activePowerCurve.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_actualDemand_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if actualDemand is published.");
				isPublished = false;
			}
			if (  isPublished && _actualDemand.shouldBeUpdated( force )  ) {
				datamembers.add( get_actualDemand_handle(), Float.toString(get_actualDemand()).getBytes() );
				_actualDemand.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_adjustedFullDRPower_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if adjustedFullDRPower is published.");
				isPublished = false;
			}
			if (  isPublished && _adjustedFullDRPower.shouldBeUpdated( force )  ) {
				datamembers.add( get_adjustedFullDRPower_handle(), Float.toString(get_adjustedFullDRPower()).getBytes() );
				_adjustedFullDRPower.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_adjustedNoDRPower_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if adjustedNoDRPower is published.");
				isPublished = false;
			}
			if (  isPublished && _adjustedNoDRPower.shouldBeUpdated( force )  ) {
				datamembers.add( get_adjustedNoDRPower_handle(), Float.toString(get_adjustedNoDRPower()).getBytes() );
				_adjustedNoDRPower.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_downBeginRamp_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if downBeginRamp is published.");
				isPublished = false;
			}
			if (  isPublished && _downBeginRamp.shouldBeUpdated( force )  ) {
				datamembers.add( get_downBeginRamp_handle(), Float.toString(get_downBeginRamp()).getBytes() );
				_downBeginRamp.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_downDuration_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if downDuration is published.");
				isPublished = false;
			}
			if (  isPublished && _downDuration.shouldBeUpdated( force )  ) {
				datamembers.add( get_downDuration_handle(), Integer.toString(get_downDuration()).getBytes() );
				_downDuration.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_downRampToCompletion_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if downRampToCompletion is published.");
				isPublished = false;
			}
			if (  isPublished && _downRampToCompletion.shouldBeUpdated( force )  ) {
				datamembers.add( get_downRampToCompletion_handle(), Boolean.toString(get_downRampToCompletion()).getBytes() );
				_downRampToCompletion.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_downRate_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if downRate is published.");
				isPublished = false;
			}
			if (  isPublished && _downRate.shouldBeUpdated( force )  ) {
				datamembers.add( get_downRate_handle(), Float.toString(get_downRate()).getBytes() );
				_downRate.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_loadStatusType_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if loadStatusType is published.");
				isPublished = false;
			}
			if (  isPublished && _loadStatusType.shouldBeUpdated( force )  ) {
				datamembers.add( get_loadStatusType_handle(), get_loadStatusType().getBytes() );
				_loadStatusType.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_locked_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if locked is published.");
				isPublished = false;
			}
			if (  isPublished && _locked.shouldBeUpdated( force )  ) {
				datamembers.add( get_locked_handle(), Boolean.toString(get_locked()).getBytes() );
				_locked.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_maximumReactivePower_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if maximumReactivePower is published.");
				isPublished = false;
			}
			if (  isPublished && _maximumReactivePower.shouldBeUpdated( force )  ) {
				datamembers.add( get_maximumReactivePower_handle(), Float.toString(get_maximumReactivePower()).getBytes() );
				_maximumReactivePower.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_maximumRealPower_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if maximumRealPower is published.");
				isPublished = false;
			}
			if (  isPublished && _maximumRealPower.shouldBeUpdated( force )  ) {
				datamembers.add( get_maximumRealPower_handle(), Float.toString(get_maximumRealPower()).getBytes() );
				_maximumRealPower.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_reactiveDesiredFractionOfFullRatedOutputBegin_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if reactiveDesiredFractionOfFullRatedOutputBegin is published.");
				isPublished = false;
			}
			if (  isPublished && _reactiveDesiredFractionOfFullRatedOutputBegin.shouldBeUpdated( force )  ) {
				datamembers.add( get_reactiveDesiredFractionOfFullRatedOutputBegin_handle(), Float.toString(get_reactiveDesiredFractionOfFullRatedOutputBegin()).getBytes() );
				_reactiveDesiredFractionOfFullRatedOutputBegin.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_reactiveDesiredFractionOfFullRatedOutputEnd_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if reactiveDesiredFractionOfFullRatedOutputEnd is published.");
				isPublished = false;
			}
			if (  isPublished && _reactiveDesiredFractionOfFullRatedOutputEnd.shouldBeUpdated( force )  ) {
				datamembers.add( get_reactiveDesiredFractionOfFullRatedOutputEnd_handle(), Float.toString(get_reactiveDesiredFractionOfFullRatedOutputEnd()).getBytes() );
				_reactiveDesiredFractionOfFullRatedOutputEnd.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin is published.");
				isPublished = false;
			}
			if (  isPublished && _reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin.shouldBeUpdated( force )  ) {
				datamembers.add( get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin_handle(), Float.toString(get_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin()).getBytes() );
				_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd is published.");
				isPublished = false;
			}
			if (  isPublished && _reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd.shouldBeUpdated( force )  ) {
				datamembers.add( get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd_handle(), Float.toString(get_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd()).getBytes() );
				_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_realDesiredFractionOfFullRatedOutputBegin_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if realDesiredFractionOfFullRatedOutputBegin is published.");
				isPublished = false;
			}
			if (  isPublished && _realDesiredFractionOfFullRatedOutputBegin.shouldBeUpdated( force )  ) {
				datamembers.add( get_realDesiredFractionOfFullRatedOutputBegin_handle(), Float.toString(get_realDesiredFractionOfFullRatedOutputBegin()).getBytes() );
				_realDesiredFractionOfFullRatedOutputBegin.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_realDesiredFractionOfFullRatedOutputEnd_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if realDesiredFractionOfFullRatedOutputEnd is published.");
				isPublished = false;
			}
			if (  isPublished && _realDesiredFractionOfFullRatedOutputEnd.shouldBeUpdated( force )  ) {
				datamembers.add( get_realDesiredFractionOfFullRatedOutputEnd_handle(), Float.toString(get_realDesiredFractionOfFullRatedOutputEnd()).getBytes() );
				_realDesiredFractionOfFullRatedOutputEnd.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_realRequiredFractionOfFullRatedInputPowerDrawnBegin_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if realRequiredFractionOfFullRatedInputPowerDrawnBegin is published.");
				isPublished = false;
			}
			if (  isPublished && _realRequiredFractionOfFullRatedInputPowerDrawnBegin.shouldBeUpdated( force )  ) {
				datamembers.add( get_realRequiredFractionOfFullRatedInputPowerDrawnBegin_handle(), Float.toString(get_realRequiredFractionOfFullRatedInputPowerDrawnBegin()).getBytes() );
				_realRequiredFractionOfFullRatedInputPowerDrawnBegin.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_realRequiredFractionOfFullRatedInputPowerDrawnEnd_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if realRequiredFractionOfFullRatedInputPowerDrawnEnd is published.");
				isPublished = false;
			}
			if (  isPublished && _realRequiredFractionOfFullRatedInputPowerDrawnEnd.shouldBeUpdated( force )  ) {
				datamembers.add( get_realRequiredFractionOfFullRatedInputPowerDrawnEnd_handle(), Float.toString(get_realRequiredFractionOfFullRatedInputPowerDrawnEnd()).getBytes() );
				_realRequiredFractionOfFullRatedInputPowerDrawnEnd.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_upBeginRamp_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if upBeginRamp is published.");
				isPublished = false;
			}
			if (  isPublished && _upBeginRamp.shouldBeUpdated( force )  ) {
				datamembers.add( get_upBeginRamp_handle(), Float.toString(get_upBeginRamp()).getBytes() );
				_upBeginRamp.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_upDuration_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if upDuration is published.");
				isPublished = false;
			}
			if (  isPublished && _upDuration.shouldBeUpdated( force )  ) {
				datamembers.add( get_upDuration_handle(), Integer.toString(get_upDuration()).getBytes() );
				_upDuration.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_upRampToCompletion_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if upRampToCompletion is published.");
				isPublished = false;
			}
			if (  isPublished && _upRampToCompletion.shouldBeUpdated( force )  ) {
				datamembers.add( get_upRampToCompletion_handle(), Boolean.toString(get_upRampToCompletion()).getBytes() );
				_upRampToCompletion.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_upRate_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.resourceControl.createSuppliedAttributes:  could not determine if upRate is published.");
				isPublished = false;
			}
			if (  isPublished && _upRate.shouldBeUpdated( force )  ) {
				datamembers.add( get_upRate_handle(), Float.toString(get_upRate()).getBytes() );
				_upRate.setHasBeenUpdated();
			}
	
		return datamembers;
	}

	
	public void copyFrom( Object object ) {
		super.copyFrom( object );
		if ( object instanceof resourceControl ) {
			resourceControl data = (resourceControl)object;
			
			
				_Resources = data._Resources;
				_activePowerCurve = data._activePowerCurve;
				_actualDemand = data._actualDemand;
				_adjustedFullDRPower = data._adjustedFullDRPower;
				_adjustedNoDRPower = data._adjustedNoDRPower;
				_downBeginRamp = data._downBeginRamp;
				_downDuration = data._downDuration;
				_downRampToCompletion = data._downRampToCompletion;
				_downRate = data._downRate;
				_loadStatusType = data._loadStatusType;
				_locked = data._locked;
				_maximumReactivePower = data._maximumReactivePower;
				_maximumRealPower = data._maximumRealPower;
				_reactiveDesiredFractionOfFullRatedOutputBegin = data._reactiveDesiredFractionOfFullRatedOutputBegin;
				_reactiveDesiredFractionOfFullRatedOutputEnd = data._reactiveDesiredFractionOfFullRatedOutputEnd;
				_reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin = data._reactiveRequiredFractionOfFullRatedInputPowerDrawnBegin;
				_reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd = data._reactiveRequiredFractionOfFullRatedInputPowerDrawnEnd;
				_realDesiredFractionOfFullRatedOutputBegin = data._realDesiredFractionOfFullRatedOutputBegin;
				_realDesiredFractionOfFullRatedOutputEnd = data._realDesiredFractionOfFullRatedOutputEnd;
				_realRequiredFractionOfFullRatedInputPowerDrawnBegin = data._realRequiredFractionOfFullRatedInputPowerDrawnBegin;
				_realRequiredFractionOfFullRatedInputPowerDrawnEnd = data._realRequiredFractionOfFullRatedInputPowerDrawnEnd;
				_upBeginRamp = data._upBeginRamp;
				_upDuration = data._upDuration;
				_upRampToCompletion = data._upRampToCompletion;
				_upRate = data._upRate;
			
		}
	}
}
