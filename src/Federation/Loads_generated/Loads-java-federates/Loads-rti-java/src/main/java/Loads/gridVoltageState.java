
package Loads;

import java.util.HashSet;
import java.util.Set;

import hla.rti.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cpswt.utils.CpswtUtils;


import org.cpswt.hla.*;

/**
* The gridVoltageState class implements the gridVoltageState object in the
* Loads simulation.
*/
public class gridVoltageState extends ObjectRoot {

	private static final Logger logger = LogManager.getLogger(gridVoltageState.class);

	/**
	* Default constructor -- creates an instance of the gridVoltageState object
	* class with default attribute values.
	*/
	public gridVoltageState() { }

	
	
	private static int _grid_Voltage_Imaginary_A_handle;
	private static int _grid_Voltage_Imaginary_B_handle;
	private static int _grid_Voltage_Imaginary_C_handle;
	private static int _grid_Voltage_Real_A_handle;
	private static int _grid_Voltage_Real_B_handle;
	private static int _grid_Voltage_Real_C_handle;
	
	
	/**
	* Returns the handle (RTI assigned) of the "grid_Voltage_Imaginary_A" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "grid_Voltage_Imaginary_A" attribute
	*/
	public static int get_grid_Voltage_Imaginary_A_handle() { return _grid_Voltage_Imaginary_A_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "grid_Voltage_Imaginary_B" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "grid_Voltage_Imaginary_B" attribute
	*/
	public static int get_grid_Voltage_Imaginary_B_handle() { return _grid_Voltage_Imaginary_B_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "grid_Voltage_Imaginary_C" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "grid_Voltage_Imaginary_C" attribute
	*/
	public static int get_grid_Voltage_Imaginary_C_handle() { return _grid_Voltage_Imaginary_C_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "grid_Voltage_Real_A" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "grid_Voltage_Real_A" attribute
	*/
	public static int get_grid_Voltage_Real_A_handle() { return _grid_Voltage_Real_A_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "grid_Voltage_Real_B" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "grid_Voltage_Real_B" attribute
	*/
	public static int get_grid_Voltage_Real_B_handle() { return _grid_Voltage_Real_B_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "grid_Voltage_Real_C" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "grid_Voltage_Real_C" attribute
	*/
	public static int get_grid_Voltage_Real_C_handle() { return _grid_Voltage_Real_C_handle; }
	
	
	
	private static boolean _isInitialized = false;

	private static int _handle;

	/**
	* Returns the handle (RTI assigned) of the gridVoltageState object class.
	* Note: As this is a static method, it is NOT polymorphic, and so, if called on
	* a reference will return the handle of the class pertaining to the reference,\
	* rather than the handle of the class for the instance referred to by the reference.
	* For the polymorphic version of this method, use {@link #getClassHandle()}.
	*/
	public static int get_handle() { return _handle; }

	/**
	* Returns the fully-qualified (dot-delimited) name of the gridVoltageState
	* object class.
	* Note: As this is a static method, it is NOT polymorphic, and so, if called on
	* a reference will return the name of the class pertaining to the reference,\
	* rather than the name of the class for the instance referred to by the reference.
	* For the polymorphic version of this method, use {@link #getClassName()}.
	*/
	public static String get_class_name() { return "ObjectRoot.gridVoltageState"; }

	/**
	* Returns the simple name (the last name in the dot-delimited fully-qualified
	* class name) of the gridVoltageState object class.
	*/
	public static String get_simple_class_name() { return "gridVoltageState"; }

	private static Set< String > _datamemberNames = new HashSet< String >();
	private static Set< String > _allDatamemberNames = new HashSet< String >();

	/**
	* Returns a set containing the names of all of the non-hidden attributes in the
	* gridVoltageState object class.
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
	* gridVoltageState object class.
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
		_classNameSet.add("ObjectRoot.gridVoltageState");
		_classNameClassMap.put("ObjectRoot.gridVoltageState", gridVoltageState.class);
		
		_datamemberClassNameSetMap.put("ObjectRoot.gridVoltageState", _datamemberNames);
		_allDatamemberClassNameSetMap.put("ObjectRoot.gridVoltageState", _allDatamemberNames);

		
		
		_datamemberNames.add("grid_Voltage_Imaginary_A");
		_datamemberNames.add("grid_Voltage_Imaginary_B");
		_datamemberNames.add("grid_Voltage_Imaginary_C");
		_datamemberNames.add("grid_Voltage_Real_A");
		_datamemberNames.add("grid_Voltage_Real_B");
		_datamemberNames.add("grid_Voltage_Real_C");
		
		
		_allDatamemberNames.add("grid_Voltage_Imaginary_A");
		_allDatamemberNames.add("grid_Voltage_Imaginary_B");
		_allDatamemberNames.add("grid_Voltage_Imaginary_C");
		_allDatamemberNames.add("grid_Voltage_Real_A");
		_allDatamemberNames.add("grid_Voltage_Real_B");
		_allDatamemberNames.add("grid_Voltage_Real_C");
		
		
		_datamemberTypeMap.put("grid_Voltage_Imaginary_A", "float");
		_datamemberTypeMap.put("grid_Voltage_Imaginary_B", "float");
		_datamemberTypeMap.put("grid_Voltage_Imaginary_C", "float");
		_datamemberTypeMap.put("grid_Voltage_Real_A", "float");
		_datamemberTypeMap.put("grid_Voltage_Real_B", "float");
		_datamemberTypeMap.put("grid_Voltage_Real_C", "float");
	

		_classNamePublishAttributeNameMap.put("ObjectRoot.gridVoltageState", _publishAttributeNameSet);
		_publishedAttributeHandleSet = _factory.createAttributeHandleSet();
		_classNamePublishedAttributeMap.put("ObjectRoot.gridVoltageState", _publishedAttributeHandleSet);

		_classNameSubscribeAttributeNameMap.put("ObjectRoot.gridVoltageState", _subscribeAttributeNameSet);
		_subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
		_classNameSubscribedAttributeMap.put("ObjectRoot.gridVoltageState", _subscribedAttributeHandleSet);
	

	}


	private static String initErrorMessage = "Error:  ObjectRoot.gridVoltageState:  could not initialize:  ";
	protected static void init(RTIambassador rti) {
		if (_isInitialized) return;
		_isInitialized = true;
		
		ObjectRoot.init(rti);
		
		boolean isNotInitialized = true;
		while(isNotInitialized) {
			try {
				_handle = rti.getObjectClassHandle("ObjectRoot.gridVoltageState");
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

		_classNameHandleMap.put("ObjectRoot.gridVoltageState", get_handle());
		_classHandleNameMap.put(get_handle(), "ObjectRoot.gridVoltageState");
		_classHandleSimpleNameMap.put(get_handle(), "gridVoltageState");

		
		isNotInitialized = true;
		while(isNotInitialized) {
			try {
							
				_grid_Voltage_Imaginary_A_handle = rti.getAttributeHandle("grid_Voltage_Imaginary_A", get_handle());			
				_grid_Voltage_Imaginary_B_handle = rti.getAttributeHandle("grid_Voltage_Imaginary_B", get_handle());			
				_grid_Voltage_Imaginary_C_handle = rti.getAttributeHandle("grid_Voltage_Imaginary_C", get_handle());			
				_grid_Voltage_Real_A_handle = rti.getAttributeHandle("grid_Voltage_Real_A", get_handle());			
				_grid_Voltage_Real_B_handle = rti.getAttributeHandle("grid_Voltage_Real_B", get_handle());			
				_grid_Voltage_Real_C_handle = rti.getAttributeHandle("grid_Voltage_Real_C", get_handle());
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
			
			
		_datamemberNameHandleMap.put("ObjectRoot.gridVoltageState,grid_Voltage_Imaginary_A", get_grid_Voltage_Imaginary_A_handle());
		_datamemberNameHandleMap.put("ObjectRoot.gridVoltageState,grid_Voltage_Imaginary_B", get_grid_Voltage_Imaginary_B_handle());
		_datamemberNameHandleMap.put("ObjectRoot.gridVoltageState,grid_Voltage_Imaginary_C", get_grid_Voltage_Imaginary_C_handle());
		_datamemberNameHandleMap.put("ObjectRoot.gridVoltageState,grid_Voltage_Real_A", get_grid_Voltage_Real_A_handle());
		_datamemberNameHandleMap.put("ObjectRoot.gridVoltageState,grid_Voltage_Real_B", get_grid_Voltage_Real_B_handle());
		_datamemberNameHandleMap.put("ObjectRoot.gridVoltageState,grid_Voltage_Real_C", get_grid_Voltage_Real_C_handle());
			
			
		_datamemberHandleNameMap.put(get_grid_Voltage_Imaginary_A_handle(), "grid_Voltage_Imaginary_A");
		_datamemberHandleNameMap.put(get_grid_Voltage_Imaginary_B_handle(), "grid_Voltage_Imaginary_B");
		_datamemberHandleNameMap.put(get_grid_Voltage_Imaginary_C_handle(), "grid_Voltage_Imaginary_C");
		_datamemberHandleNameMap.put(get_grid_Voltage_Real_A_handle(), "grid_Voltage_Real_A");
		_datamemberHandleNameMap.put(get_grid_Voltage_Real_B_handle(), "grid_Voltage_Real_B");
		_datamemberHandleNameMap.put(get_grid_Voltage_Real_C_handle(), "grid_Voltage_Real_C");
		
	}

	private static boolean _isPublished = false;
	private static String publishErrorMessage = "Error:  ObjectRoot.gridVoltageState:  could not publish:  ";

	/**
	* Publishes the gridVoltageState object class for a federate.
	*
	* @param rti handle to the Local RTI Component
	*/
	public static void publish(RTIambassador rti) {
		if (_isPublished) return;
		
		init(rti);

		
		_publishedAttributeHandleSet.empty();
		for(String attributeName : _publishAttributeNameSet) {
			try {
				_publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.gridVoltageState," + attributeName));
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

	private static String unpublishErrorMessage = "Error:  ObjectRoot.gridVoltageState:  could not unpublish:  ";
	/**
	* Unpublishes the gridVoltageState object class for a federate.
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
	private static String subscribeErrorMessage = "Error:  ObjectRoot.gridVoltageState:  could not subscribe:  ";
	/**
	* Subscribes a federate to the gridVoltageState object class.
	*
	* @param rti handle to the Local RTI Component
	*/
	public static void subscribe(RTIambassador rti) {
		if (_isSubscribed) return;
		
		init(rti);
		
		_subscribedAttributeHandleSet.empty();
		for(String attributeName : _subscribeAttributeNameSet) {
			try {
				_subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.gridVoltageState," + attributeName));
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

	private static String unsubscribeErrorMessage = "Error:  ObjectRoot.gridVoltageState:  could not unsubscribe:  ";
	/**
	* Unsubscribes a federate from the gridVoltageState object class.
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
	* (that is, the gridVoltageState object class).
	*
	* @param handle handle to compare to the value of the handle (RTI assigned) of
	* this class (the gridVoltageState object class).
	* @return "true" if "handle" matches the value of the handle of this class
	* (that is, the gridVoltageState object class).
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
		return "gridVoltageState("
			
			
			+ "grid_Voltage_Imaginary_A:" + get_grid_Voltage_Imaginary_A()
			+ "," + "grid_Voltage_Imaginary_B:" + get_grid_Voltage_Imaginary_B()
			+ "," + "grid_Voltage_Imaginary_C:" + get_grid_Voltage_Imaginary_C()
			+ "," + "grid_Voltage_Real_A:" + get_grid_Voltage_Real_A()
			+ "," + "grid_Voltage_Real_B:" + get_grid_Voltage_Real_B()
			+ "," + "grid_Voltage_Real_C:" + get_grid_Voltage_Real_C()
			+ ")";
	}
	



	
	
	/**
	* Publishes the "grid_Voltage_Imaginary_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "grid_Voltage_Imaginary_A" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_grid_Voltage_Imaginary_A() {
		_publishAttributeNameSet.add( "grid_Voltage_Imaginary_A" );
	}

	/**
	* Unpublishes the "grid_Voltage_Imaginary_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "grid_Voltage_Imaginary_A" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_grid_Voltage_Imaginary_A() {
		_publishAttributeNameSet.remove( "grid_Voltage_Imaginary_A" );
	}
	
	/**
	* Subscribes a federate to the "grid_Voltage_Imaginary_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "grid_Voltage_Imaginary_A" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_grid_Voltage_Imaginary_A() {
		_subscribeAttributeNameSet.add( "grid_Voltage_Imaginary_A" );
	}

	/**
	* Unsubscribes a federate from the "grid_Voltage_Imaginary_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "grid_Voltage_Imaginary_A" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_grid_Voltage_Imaginary_A() {
		_subscribeAttributeNameSet.remove( "grid_Voltage_Imaginary_A" );
	}
	
	
	/**
	* Publishes the "grid_Voltage_Imaginary_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "grid_Voltage_Imaginary_B" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_grid_Voltage_Imaginary_B() {
		_publishAttributeNameSet.add( "grid_Voltage_Imaginary_B" );
	}

	/**
	* Unpublishes the "grid_Voltage_Imaginary_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "grid_Voltage_Imaginary_B" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_grid_Voltage_Imaginary_B() {
		_publishAttributeNameSet.remove( "grid_Voltage_Imaginary_B" );
	}
	
	/**
	* Subscribes a federate to the "grid_Voltage_Imaginary_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "grid_Voltage_Imaginary_B" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_grid_Voltage_Imaginary_B() {
		_subscribeAttributeNameSet.add( "grid_Voltage_Imaginary_B" );
	}

	/**
	* Unsubscribes a federate from the "grid_Voltage_Imaginary_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "grid_Voltage_Imaginary_B" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_grid_Voltage_Imaginary_B() {
		_subscribeAttributeNameSet.remove( "grid_Voltage_Imaginary_B" );
	}
	
	
	/**
	* Publishes the "grid_Voltage_Imaginary_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "grid_Voltage_Imaginary_C" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_grid_Voltage_Imaginary_C() {
		_publishAttributeNameSet.add( "grid_Voltage_Imaginary_C" );
	}

	/**
	* Unpublishes the "grid_Voltage_Imaginary_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "grid_Voltage_Imaginary_C" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_grid_Voltage_Imaginary_C() {
		_publishAttributeNameSet.remove( "grid_Voltage_Imaginary_C" );
	}
	
	/**
	* Subscribes a federate to the "grid_Voltage_Imaginary_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "grid_Voltage_Imaginary_C" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_grid_Voltage_Imaginary_C() {
		_subscribeAttributeNameSet.add( "grid_Voltage_Imaginary_C" );
	}

	/**
	* Unsubscribes a federate from the "grid_Voltage_Imaginary_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "grid_Voltage_Imaginary_C" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_grid_Voltage_Imaginary_C() {
		_subscribeAttributeNameSet.remove( "grid_Voltage_Imaginary_C" );
	}
	
	
	/**
	* Publishes the "grid_Voltage_Real_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "grid_Voltage_Real_A" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_grid_Voltage_Real_A() {
		_publishAttributeNameSet.add( "grid_Voltage_Real_A" );
	}

	/**
	* Unpublishes the "grid_Voltage_Real_A" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "grid_Voltage_Real_A" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_grid_Voltage_Real_A() {
		_publishAttributeNameSet.remove( "grid_Voltage_Real_A" );
	}
	
	/**
	* Subscribes a federate to the "grid_Voltage_Real_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "grid_Voltage_Real_A" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_grid_Voltage_Real_A() {
		_subscribeAttributeNameSet.add( "grid_Voltage_Real_A" );
	}

	/**
	* Unsubscribes a federate from the "grid_Voltage_Real_A" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "grid_Voltage_Real_A" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_grid_Voltage_Real_A() {
		_subscribeAttributeNameSet.remove( "grid_Voltage_Real_A" );
	}
	
	
	/**
	* Publishes the "grid_Voltage_Real_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "grid_Voltage_Real_B" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_grid_Voltage_Real_B() {
		_publishAttributeNameSet.add( "grid_Voltage_Real_B" );
	}

	/**
	* Unpublishes the "grid_Voltage_Real_B" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "grid_Voltage_Real_B" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_grid_Voltage_Real_B() {
		_publishAttributeNameSet.remove( "grid_Voltage_Real_B" );
	}
	
	/**
	* Subscribes a federate to the "grid_Voltage_Real_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "grid_Voltage_Real_B" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_grid_Voltage_Real_B() {
		_subscribeAttributeNameSet.add( "grid_Voltage_Real_B" );
	}

	/**
	* Unsubscribes a federate from the "grid_Voltage_Real_B" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "grid_Voltage_Real_B" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_grid_Voltage_Real_B() {
		_subscribeAttributeNameSet.remove( "grid_Voltage_Real_B" );
	}
	
	
	/**
	* Publishes the "grid_Voltage_Real_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "grid_Voltage_Real_C" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_grid_Voltage_Real_C() {
		_publishAttributeNameSet.add( "grid_Voltage_Real_C" );
	}

	/**
	* Unpublishes the "grid_Voltage_Real_C" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "grid_Voltage_Real_C" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_grid_Voltage_Real_C() {
		_publishAttributeNameSet.remove( "grid_Voltage_Real_C" );
	}
	
	/**
	* Subscribes a federate to the "grid_Voltage_Real_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "grid_Voltage_Real_C" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_grid_Voltage_Real_C() {
		_subscribeAttributeNameSet.add( "grid_Voltage_Real_C" );
	}

	/**
	* Unsubscribes a federate from the "grid_Voltage_Real_C" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "grid_Voltage_Real_C" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_grid_Voltage_Real_C() {
		_subscribeAttributeNameSet.remove( "grid_Voltage_Real_C" );
	}
	

	
	
	private Attribute< Float > _grid_Voltage_Imaginary_A =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "grid_Voltage_Imaginary_A" attribute to "value" for this object.
	*
	* @param value the new value for the "grid_Voltage_Imaginary_A" attribute
	*/
	public void set_grid_Voltage_Imaginary_A( float value ) {
		_grid_Voltage_Imaginary_A.setValue( value );
		_grid_Voltage_Imaginary_A.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "grid_Voltage_Imaginary_A" attribute of this object.
	*
	* @return the value of the "grid_Voltage_Imaginary_A" attribute
	*/
	public float get_grid_Voltage_Imaginary_A() {
		return _grid_Voltage_Imaginary_A.getValue();
	}
	
	/**
	* Returns the current timestamp of the "grid_Voltage_Imaginary_A" attribute of this object.
	* 
	* @return the current timestamp of the "grid_Voltage_Imaginary_A" attribute
	*/
	public double get_grid_Voltage_Imaginary_A_time() {
		return _grid_Voltage_Imaginary_A.getTime();
	}
	
	
	private Attribute< Float > _grid_Voltage_Imaginary_B =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "grid_Voltage_Imaginary_B" attribute to "value" for this object.
	*
	* @param value the new value for the "grid_Voltage_Imaginary_B" attribute
	*/
	public void set_grid_Voltage_Imaginary_B( float value ) {
		_grid_Voltage_Imaginary_B.setValue( value );
		_grid_Voltage_Imaginary_B.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "grid_Voltage_Imaginary_B" attribute of this object.
	*
	* @return the value of the "grid_Voltage_Imaginary_B" attribute
	*/
	public float get_grid_Voltage_Imaginary_B() {
		return _grid_Voltage_Imaginary_B.getValue();
	}
	
	/**
	* Returns the current timestamp of the "grid_Voltage_Imaginary_B" attribute of this object.
	* 
	* @return the current timestamp of the "grid_Voltage_Imaginary_B" attribute
	*/
	public double get_grid_Voltage_Imaginary_B_time() {
		return _grid_Voltage_Imaginary_B.getTime();
	}
	
	
	private Attribute< Float > _grid_Voltage_Imaginary_C =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "grid_Voltage_Imaginary_C" attribute to "value" for this object.
	*
	* @param value the new value for the "grid_Voltage_Imaginary_C" attribute
	*/
	public void set_grid_Voltage_Imaginary_C( float value ) {
		_grid_Voltage_Imaginary_C.setValue( value );
		_grid_Voltage_Imaginary_C.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "grid_Voltage_Imaginary_C" attribute of this object.
	*
	* @return the value of the "grid_Voltage_Imaginary_C" attribute
	*/
	public float get_grid_Voltage_Imaginary_C() {
		return _grid_Voltage_Imaginary_C.getValue();
	}
	
	/**
	* Returns the current timestamp of the "grid_Voltage_Imaginary_C" attribute of this object.
	* 
	* @return the current timestamp of the "grid_Voltage_Imaginary_C" attribute
	*/
	public double get_grid_Voltage_Imaginary_C_time() {
		return _grid_Voltage_Imaginary_C.getTime();
	}
	
	
	private Attribute< Float > _grid_Voltage_Real_A =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "grid_Voltage_Real_A" attribute to "value" for this object.
	*
	* @param value the new value for the "grid_Voltage_Real_A" attribute
	*/
	public void set_grid_Voltage_Real_A( float value ) {
		_grid_Voltage_Real_A.setValue( value );
		_grid_Voltage_Real_A.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "grid_Voltage_Real_A" attribute of this object.
	*
	* @return the value of the "grid_Voltage_Real_A" attribute
	*/
	public float get_grid_Voltage_Real_A() {
		return _grid_Voltage_Real_A.getValue();
	}
	
	/**
	* Returns the current timestamp of the "grid_Voltage_Real_A" attribute of this object.
	* 
	* @return the current timestamp of the "grid_Voltage_Real_A" attribute
	*/
	public double get_grid_Voltage_Real_A_time() {
		return _grid_Voltage_Real_A.getTime();
	}
	
	
	private Attribute< Float > _grid_Voltage_Real_B =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "grid_Voltage_Real_B" attribute to "value" for this object.
	*
	* @param value the new value for the "grid_Voltage_Real_B" attribute
	*/
	public void set_grid_Voltage_Real_B( float value ) {
		_grid_Voltage_Real_B.setValue( value );
		_grid_Voltage_Real_B.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "grid_Voltage_Real_B" attribute of this object.
	*
	* @return the value of the "grid_Voltage_Real_B" attribute
	*/
	public float get_grid_Voltage_Real_B() {
		return _grid_Voltage_Real_B.getValue();
	}
	
	/**
	* Returns the current timestamp of the "grid_Voltage_Real_B" attribute of this object.
	* 
	* @return the current timestamp of the "grid_Voltage_Real_B" attribute
	*/
	public double get_grid_Voltage_Real_B_time() {
		return _grid_Voltage_Real_B.getTime();
	}
	
	
	private Attribute< Float > _grid_Voltage_Real_C =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "grid_Voltage_Real_C" attribute to "value" for this object.
	*
	* @param value the new value for the "grid_Voltage_Real_C" attribute
	*/
	public void set_grid_Voltage_Real_C( float value ) {
		_grid_Voltage_Real_C.setValue( value );
		_grid_Voltage_Real_C.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "grid_Voltage_Real_C" attribute of this object.
	*
	* @return the value of the "grid_Voltage_Real_C" attribute
	*/
	public float get_grid_Voltage_Real_C() {
		return _grid_Voltage_Real_C.getValue();
	}
	
	/**
	* Returns the current timestamp of the "grid_Voltage_Real_C" attribute of this object.
	* 
	* @return the current timestamp of the "grid_Voltage_Real_C" attribute
	*/
	public double get_grid_Voltage_Real_C_time() {
		return _grid_Voltage_Real_C.getTime();
	}
	


	protected gridVoltageState( ReflectedAttributes datamemberMap, boolean initFlag ) {
		super( datamemberMap, false );
		if ( initFlag ) setAttributes( datamemberMap );
	}
	
	protected gridVoltageState( ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
		super( datamemberMap, logicalTime, false );
		if ( initFlag ) setAttributes( datamemberMap );
	}


	/**
	* Creates an instance of the gridVoltageState object class, using
	* "datamemberMap" to initialize its attribute values.
	* "datamemberMap" is usually acquired as an argument to an RTI federate
	* callback method, such as "receiveInteraction".
	*
	* @param datamemberMap data structure containing initial values for the
	* attributes of this new gridVoltageState object class instance
	*/
	public gridVoltageState( ReflectedAttributes datamemberMap ) {
		this( datamemberMap, true );
	}
	
	/**
	* Like {@link #gridVoltageState( ReflectedAttributes datamemberMap )}, except this
	* new gridVoltageState object class instance is given a timestamp of
	* "logicalTime".
	*
	* @param datamemberMap data structure containing initial values for the
	* attributes of this new gridVoltageState object class instance
	* @param logicalTime timestamp for this new gridVoltageState object class
	* instance
	*/
	public gridVoltageState( ReflectedAttributes datamemberMap, LogicalTime logicalTime ) {
		this( datamemberMap, logicalTime, true );
	}

	/**
	* Creates a new gridVoltageState object class instance that is a duplicate
	* of the instance referred to by gridVoltageState_var.
	*
	* @param gridVoltageState_var gridVoltageState object class instance of which
	* this newly created gridVoltageState object class instance will be a
	* duplicate
	*/
	public gridVoltageState( gridVoltageState gridVoltageState_var ) {
		super( gridVoltageState_var );
		
		
		set_grid_Voltage_Imaginary_A( gridVoltageState_var.get_grid_Voltage_Imaginary_A() );
		set_grid_Voltage_Imaginary_B( gridVoltageState_var.get_grid_Voltage_Imaginary_B() );
		set_grid_Voltage_Imaginary_C( gridVoltageState_var.get_grid_Voltage_Imaginary_C() );
		set_grid_Voltage_Real_A( gridVoltageState_var.get_grid_Voltage_Real_A() );
		set_grid_Voltage_Real_B( gridVoltageState_var.get_grid_Voltage_Real_B() );
		set_grid_Voltage_Real_C( gridVoltageState_var.get_grid_Voltage_Real_C() );
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
		
		
		
		if (  "grid_Voltage_Imaginary_A".equals( datamemberName )  ) return new Float(get_grid_Voltage_Imaginary_A());
		else if (  "grid_Voltage_Imaginary_B".equals( datamemberName )  ) return new Float(get_grid_Voltage_Imaginary_B());
		else if (  "grid_Voltage_Imaginary_C".equals( datamemberName )  ) return new Float(get_grid_Voltage_Imaginary_C());
		else if (  "grid_Voltage_Real_A".equals( datamemberName )  ) return new Float(get_grid_Voltage_Real_A());
		else if (  "grid_Voltage_Real_B".equals( datamemberName )  ) return new Float(get_grid_Voltage_Real_B());
		else if (  "grid_Voltage_Real_C".equals( datamemberName )  ) return new Float(get_grid_Voltage_Real_C());
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
		
				
		
		if ( get_grid_Voltage_Imaginary_A_handle() == datamemberHandle ) return new Float(get_grid_Voltage_Imaginary_A());
		else if ( get_grid_Voltage_Imaginary_B_handle() == datamemberHandle ) return new Float(get_grid_Voltage_Imaginary_B());
		else if ( get_grid_Voltage_Imaginary_C_handle() == datamemberHandle ) return new Float(get_grid_Voltage_Imaginary_C());
		else if ( get_grid_Voltage_Real_A_handle() == datamemberHandle ) return new Float(get_grid_Voltage_Real_A());
		else if ( get_grid_Voltage_Real_B_handle() == datamemberHandle ) return new Float(get_grid_Voltage_Real_B());
		else if ( get_grid_Voltage_Real_C_handle() == datamemberHandle ) return new Float(get_grid_Voltage_Real_C());
		else return super.getAttribute( datamemberHandle );
	}
	
	protected boolean setAttributeAux( int param_handle, String val ) {
		boolean retval = true;		
		
			
		
		if ( param_handle == get_grid_Voltage_Imaginary_A_handle() ) set_grid_Voltage_Imaginary_A( Float.parseFloat(val) );
		else if ( param_handle == get_grid_Voltage_Imaginary_B_handle() ) set_grid_Voltage_Imaginary_B( Float.parseFloat(val) );
		else if ( param_handle == get_grid_Voltage_Imaginary_C_handle() ) set_grid_Voltage_Imaginary_C( Float.parseFloat(val) );
		else if ( param_handle == get_grid_Voltage_Real_A_handle() ) set_grid_Voltage_Real_A( Float.parseFloat(val) );
		else if ( param_handle == get_grid_Voltage_Real_B_handle() ) set_grid_Voltage_Real_B( Float.parseFloat(val) );
		else if ( param_handle == get_grid_Voltage_Real_C_handle() ) set_grid_Voltage_Real_C( Float.parseFloat(val) );
		else retval = super.setAttributeAux( param_handle, val );
		
		return retval;
	}
	
	protected boolean setAttributeAux( String datamemberName, String val ) {
		boolean retval = true;
		
			
		
		if (  "grid_Voltage_Imaginary_A".equals( datamemberName )  ) set_grid_Voltage_Imaginary_A( Float.parseFloat(val) );
		else if (  "grid_Voltage_Imaginary_B".equals( datamemberName )  ) set_grid_Voltage_Imaginary_B( Float.parseFloat(val) );
		else if (  "grid_Voltage_Imaginary_C".equals( datamemberName )  ) set_grid_Voltage_Imaginary_C( Float.parseFloat(val) );
		else if (  "grid_Voltage_Real_A".equals( datamemberName )  ) set_grid_Voltage_Real_A( Float.parseFloat(val) );
		else if (  "grid_Voltage_Real_B".equals( datamemberName )  ) set_grid_Voltage_Real_B( Float.parseFloat(val) );
		else if (  "grid_Voltage_Real_C".equals( datamemberName )  ) set_grid_Voltage_Real_C( Float.parseFloat(val) );	
		else retval = super.setAttributeAux( datamemberName, val );
		
		return retval;
	}
	
	protected boolean setAttributeAux( String datamemberName, Object val ) {
		boolean retval = true;
		
		
		
		if (  "grid_Voltage_Imaginary_A".equals( datamemberName )  ) set_grid_Voltage_Imaginary_A( (Float)val );
		else if (  "grid_Voltage_Imaginary_B".equals( datamemberName )  ) set_grid_Voltage_Imaginary_B( (Float)val );
		else if (  "grid_Voltage_Imaginary_C".equals( datamemberName )  ) set_grid_Voltage_Imaginary_C( (Float)val );
		else if (  "grid_Voltage_Real_A".equals( datamemberName )  ) set_grid_Voltage_Real_A( (Float)val );
		else if (  "grid_Voltage_Real_B".equals( datamemberName )  ) set_grid_Voltage_Real_B( (Float)val );
		else if (  "grid_Voltage_Real_C".equals( datamemberName )  ) set_grid_Voltage_Real_C( (Float)val );		
		else retval = super.setAttributeAux( datamemberName, val );
		
		return retval;
	}

	protected SuppliedAttributes createSuppliedDatamembers( boolean force ) {
		SuppliedAttributes datamembers = super.createSuppliedDatamembers( force );

	
		boolean isPublished = false;
		
		
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_grid_Voltage_Imaginary_A_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.gridVoltageState.createSuppliedAttributes:  could not determine if grid_Voltage_Imaginary_A is published.");
				isPublished = false;
			}
			if (  isPublished && _grid_Voltage_Imaginary_A.shouldBeUpdated( force )  ) {
				datamembers.add( get_grid_Voltage_Imaginary_A_handle(), Float.toString(get_grid_Voltage_Imaginary_A()).getBytes() );
				_grid_Voltage_Imaginary_A.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_grid_Voltage_Imaginary_B_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.gridVoltageState.createSuppliedAttributes:  could not determine if grid_Voltage_Imaginary_B is published.");
				isPublished = false;
			}
			if (  isPublished && _grid_Voltage_Imaginary_B.shouldBeUpdated( force )  ) {
				datamembers.add( get_grid_Voltage_Imaginary_B_handle(), Float.toString(get_grid_Voltage_Imaginary_B()).getBytes() );
				_grid_Voltage_Imaginary_B.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_grid_Voltage_Imaginary_C_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.gridVoltageState.createSuppliedAttributes:  could not determine if grid_Voltage_Imaginary_C is published.");
				isPublished = false;
			}
			if (  isPublished && _grid_Voltage_Imaginary_C.shouldBeUpdated( force )  ) {
				datamembers.add( get_grid_Voltage_Imaginary_C_handle(), Float.toString(get_grid_Voltage_Imaginary_C()).getBytes() );
				_grid_Voltage_Imaginary_C.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_grid_Voltage_Real_A_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.gridVoltageState.createSuppliedAttributes:  could not determine if grid_Voltage_Real_A is published.");
				isPublished = false;
			}
			if (  isPublished && _grid_Voltage_Real_A.shouldBeUpdated( force )  ) {
				datamembers.add( get_grid_Voltage_Real_A_handle(), Float.toString(get_grid_Voltage_Real_A()).getBytes() );
				_grid_Voltage_Real_A.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_grid_Voltage_Real_B_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.gridVoltageState.createSuppliedAttributes:  could not determine if grid_Voltage_Real_B is published.");
				isPublished = false;
			}
			if (  isPublished && _grid_Voltage_Real_B.shouldBeUpdated( force )  ) {
				datamembers.add( get_grid_Voltage_Real_B_handle(), Float.toString(get_grid_Voltage_Real_B()).getBytes() );
				_grid_Voltage_Real_B.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_grid_Voltage_Real_C_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.gridVoltageState.createSuppliedAttributes:  could not determine if grid_Voltage_Real_C is published.");
				isPublished = false;
			}
			if (  isPublished && _grid_Voltage_Real_C.shouldBeUpdated( force )  ) {
				datamembers.add( get_grid_Voltage_Real_C_handle(), Float.toString(get_grid_Voltage_Real_C()).getBytes() );
				_grid_Voltage_Real_C.setHasBeenUpdated();
			}
	
		return datamembers;
	}

	
	public void copyFrom( Object object ) {
		super.copyFrom( object );
		if ( object instanceof gridVoltageState ) {
			gridVoltageState data = (gridVoltageState)object;
			
			
				_grid_Voltage_Imaginary_A = data._grid_Voltage_Imaginary_A;
				_grid_Voltage_Imaginary_B = data._grid_Voltage_Imaginary_B;
				_grid_Voltage_Imaginary_C = data._grid_Voltage_Imaginary_C;
				_grid_Voltage_Real_A = data._grid_Voltage_Real_A;
				_grid_Voltage_Real_B = data._grid_Voltage_Real_B;
				_grid_Voltage_Real_C = data._grid_Voltage_Real_C;
			
		}
	}
}
