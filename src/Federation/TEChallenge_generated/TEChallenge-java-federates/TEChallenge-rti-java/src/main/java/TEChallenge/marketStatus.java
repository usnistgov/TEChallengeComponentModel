
package TEChallenge;

import java.util.HashSet;
import java.util.Set;

import hla.rti.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cpswt.utils.CpswtUtils;


import org.cpswt.hla.*;

/**
* The marketStatus class implements the marketStatus object in the
* TEChallenge simulation.
*/
public class marketStatus extends ObjectRoot {

	private static final Logger logger = LogManager.getLogger(marketStatus.class);

	/**
	* Default constructor -- creates an instance of the marketStatus object
	* class with default attribute values.
	*/
	public marketStatus() { }

	
	
	private static int _price_handle;
	private static int _time_handle;
	private static int _type_handle;
	
	
	/**
	* Returns the handle (RTI assigned) of the "price" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "price" attribute
	*/
	public static int get_price_handle() { return _price_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "time" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "time" attribute
	*/
	public static int get_time_handle() { return _time_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "type" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "type" attribute
	*/
	public static int get_type_handle() { return _type_handle; }
	
	
	
	private static boolean _isInitialized = false;

	private static int _handle;

	/**
	* Returns the handle (RTI assigned) of the marketStatus object class.
	* Note: As this is a static method, it is NOT polymorphic, and so, if called on
	* a reference will return the handle of the class pertaining to the reference,\
	* rather than the handle of the class for the instance referred to by the reference.
	* For the polymorphic version of this method, use {@link #getClassHandle()}.
	*/
	public static int get_handle() { return _handle; }

	/**
	* Returns the fully-qualified (dot-delimited) name of the marketStatus
	* object class.
	* Note: As this is a static method, it is NOT polymorphic, and so, if called on
	* a reference will return the name of the class pertaining to the reference,\
	* rather than the name of the class for the instance referred to by the reference.
	* For the polymorphic version of this method, use {@link #getClassName()}.
	*/
	public static String get_class_name() { return "ObjectRoot.marketStatus"; }

	/**
	* Returns the simple name (the last name in the dot-delimited fully-qualified
	* class name) of the marketStatus object class.
	*/
	public static String get_simple_class_name() { return "marketStatus"; }

	private static Set< String > _datamemberNames = new HashSet< String >();
	private static Set< String > _allDatamemberNames = new HashSet< String >();

	/**
	* Returns a set containing the names of all of the non-hidden attributes in the
	* marketStatus object class.
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
	* marketStatus object class.
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
		_classNameSet.add("ObjectRoot.marketStatus");
		_classNameClassMap.put("ObjectRoot.marketStatus", marketStatus.class);
		
		_datamemberClassNameSetMap.put("ObjectRoot.marketStatus", _datamemberNames);
		_allDatamemberClassNameSetMap.put("ObjectRoot.marketStatus", _allDatamemberNames);

		
		
		_datamemberNames.add("price");
		_datamemberNames.add("time");
		_datamemberNames.add("type");
		
		
		_allDatamemberNames.add("price");
		_allDatamemberNames.add("time");
		_allDatamemberNames.add("type");
		
		
		_datamemberTypeMap.put("price", "float");
		_datamemberTypeMap.put("time", "int");
		_datamemberTypeMap.put("type", "String");
	

		_classNamePublishAttributeNameMap.put("ObjectRoot.marketStatus", _publishAttributeNameSet);
		_publishedAttributeHandleSet = _factory.createAttributeHandleSet();
		_classNamePublishedAttributeMap.put("ObjectRoot.marketStatus", _publishedAttributeHandleSet);

		_classNameSubscribeAttributeNameMap.put("ObjectRoot.marketStatus", _subscribeAttributeNameSet);
		_subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
		_classNameSubscribedAttributeMap.put("ObjectRoot.marketStatus", _subscribedAttributeHandleSet);
	

	}


	private static String initErrorMessage = "Error:  ObjectRoot.marketStatus:  could not initialize:  ";
	protected static void init(RTIambassador rti) {
		if (_isInitialized) return;
		_isInitialized = true;
		
		ObjectRoot.init(rti);
		
		boolean isNotInitialized = true;
		while(isNotInitialized) {
			try {
				_handle = rti.getObjectClassHandle("ObjectRoot.marketStatus");
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

		_classNameHandleMap.put("ObjectRoot.marketStatus", get_handle());
		_classHandleNameMap.put(get_handle(), "ObjectRoot.marketStatus");
		_classHandleSimpleNameMap.put(get_handle(), "marketStatus");

		
		isNotInitialized = true;
		while(isNotInitialized) {
			try {
							
				_price_handle = rti.getAttributeHandle("price", get_handle());			
				_time_handle = rti.getAttributeHandle("time", get_handle());			
				_type_handle = rti.getAttributeHandle("type", get_handle());
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
			
			
		_datamemberNameHandleMap.put("ObjectRoot.marketStatus,price", get_price_handle());
		_datamemberNameHandleMap.put("ObjectRoot.marketStatus,time", get_time_handle());
		_datamemberNameHandleMap.put("ObjectRoot.marketStatus,type", get_type_handle());
			
			
		_datamemberHandleNameMap.put(get_price_handle(), "price");
		_datamemberHandleNameMap.put(get_time_handle(), "time");
		_datamemberHandleNameMap.put(get_type_handle(), "type");
		
	}

	private static boolean _isPublished = false;
	private static String publishErrorMessage = "Error:  ObjectRoot.marketStatus:  could not publish:  ";

	/**
	* Publishes the marketStatus object class for a federate.
	*
	* @param rti handle to the Local RTI Component
	*/
	public static void publish(RTIambassador rti) {
		if (_isPublished) return;
		
		init(rti);

		
		_publishedAttributeHandleSet.empty();
		for(String attributeName : _publishAttributeNameSet) {
			try {
				_publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.marketStatus," + attributeName));
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

	private static String unpublishErrorMessage = "Error:  ObjectRoot.marketStatus:  could not unpublish:  ";
	/**
	* Unpublishes the marketStatus object class for a federate.
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
	private static String subscribeErrorMessage = "Error:  ObjectRoot.marketStatus:  could not subscribe:  ";
	/**
	* Subscribes a federate to the marketStatus object class.
	*
	* @param rti handle to the Local RTI Component
	*/
	public static void subscribe(RTIambassador rti) {
		if (_isSubscribed) return;
		
		init(rti);
		
		_subscribedAttributeHandleSet.empty();
		for(String attributeName : _subscribeAttributeNameSet) {
			try {
				_subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.marketStatus," + attributeName));
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

	private static String unsubscribeErrorMessage = "Error:  ObjectRoot.marketStatus:  could not unsubscribe:  ";
	/**
	* Unsubscribes a federate from the marketStatus object class.
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
	* (that is, the marketStatus object class).
	*
	* @param handle handle to compare to the value of the handle (RTI assigned) of
	* this class (the marketStatus object class).
	* @return "true" if "handle" matches the value of the handle of this class
	* (that is, the marketStatus object class).
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
		return "marketStatus("
			
			
			+ "price:" + get_price()
			+ "," + "time:" + get_time()
			+ "," + "type:" + get_type()
			+ ")";
	}
	



	
	
	/**
	* Publishes the "price" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "price" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_price() {
		_publishAttributeNameSet.add( "price" );
	}

	/**
	* Unpublishes the "price" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "price" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_price() {
		_publishAttributeNameSet.remove( "price" );
	}
	
	/**
	* Subscribes a federate to the "price" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "price" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_price() {
		_subscribeAttributeNameSet.add( "price" );
	}

	/**
	* Unsubscribes a federate from the "price" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "price" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_price() {
		_subscribeAttributeNameSet.remove( "price" );
	}
	
	
	/**
	* Publishes the "time" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "time" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_time() {
		_publishAttributeNameSet.add( "time" );
	}

	/**
	* Unpublishes the "time" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "time" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_time() {
		_publishAttributeNameSet.remove( "time" );
	}
	
	/**
	* Subscribes a federate to the "time" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "time" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_time() {
		_subscribeAttributeNameSet.add( "time" );
	}

	/**
	* Unsubscribes a federate from the "time" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "time" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_time() {
		_subscribeAttributeNameSet.remove( "time" );
	}
	
	
	/**
	* Publishes the "type" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "type" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_type() {
		_publishAttributeNameSet.add( "type" );
	}

	/**
	* Unpublishes the "type" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "type" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_type() {
		_publishAttributeNameSet.remove( "type" );
	}
	
	/**
	* Subscribes a federate to the "type" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "type" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_type() {
		_subscribeAttributeNameSet.add( "type" );
	}

	/**
	* Unsubscribes a federate from the "type" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "type" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_type() {
		_subscribeAttributeNameSet.remove( "type" );
	}
	

	
	
	private Attribute< Float > _price =
 		new Attribute< Float >(  new Float( 0 )  );
	
	/**
	* Set the value of the "price" attribute to "value" for this object.
	*
	* @param value the new value for the "price" attribute
	*/
	public void set_price( float value ) {
		_price.setValue( value );
		_price.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "price" attribute of this object.
	*
	* @return the value of the "price" attribute
	*/
	public float get_price() {
		return _price.getValue();
	}
	
	/**
	* Returns the current timestamp of the "price" attribute of this object.
	* 
	* @return the current timestamp of the "price" attribute
	*/
	public double get_price_time() {
		return _price.getTime();
	}
	
	
	private Attribute< Integer > _time =
 		new Attribute< Integer >(  new Integer( 0 )  );
	
	/**
	* Set the value of the "time" attribute to "value" for this object.
	*
	* @param value the new value for the "time" attribute
	*/
	public void set_time( int value ) {
		_time.setValue( value );
		_time.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "time" attribute of this object.
	*
	* @return the value of the "time" attribute
	*/
	public int get_time() {
		return _time.getValue();
	}
	
	/**
	* Returns the current timestamp of the "time" attribute of this object.
	* 
	* @return the current timestamp of the "time" attribute
	*/
	public double get_time_time() {
		return _time.getTime();
	}
	
	
	private Attribute< String > _type =
 		new Attribute< String >(  new String( "" )  );
	
	/**
	* Set the value of the "type" attribute to "value" for this object.
	*
	* @param value the new value for the "type" attribute
	*/
	public void set_type( String value ) {
		_type.setValue( value );
		_type.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "type" attribute of this object.
	*
	* @return the value of the "type" attribute
	*/
	public String get_type() {
		return _type.getValue();
	}
	
	/**
	* Returns the current timestamp of the "type" attribute of this object.
	* 
	* @return the current timestamp of the "type" attribute
	*/
	public double get_type_time() {
		return _type.getTime();
	}
	


	protected marketStatus( ReflectedAttributes datamemberMap, boolean initFlag ) {
		super( datamemberMap, false );
		if ( initFlag ) setAttributes( datamemberMap );
	}
	
	protected marketStatus( ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
		super( datamemberMap, logicalTime, false );
		if ( initFlag ) setAttributes( datamemberMap );
	}


	/**
	* Creates an instance of the marketStatus object class, using
	* "datamemberMap" to initialize its attribute values.
	* "datamemberMap" is usually acquired as an argument to an RTI federate
	* callback method, such as "receiveInteraction".
	*
	* @param datamemberMap data structure containing initial values for the
	* attributes of this new marketStatus object class instance
	*/
	public marketStatus( ReflectedAttributes datamemberMap ) {
		this( datamemberMap, true );
	}
	
	/**
	* Like {@link #marketStatus( ReflectedAttributes datamemberMap )}, except this
	* new marketStatus object class instance is given a timestamp of
	* "logicalTime".
	*
	* @param datamemberMap data structure containing initial values for the
	* attributes of this new marketStatus object class instance
	* @param logicalTime timestamp for this new marketStatus object class
	* instance
	*/
	public marketStatus( ReflectedAttributes datamemberMap, LogicalTime logicalTime ) {
		this( datamemberMap, logicalTime, true );
	}

	/**
	* Creates a new marketStatus object class instance that is a duplicate
	* of the instance referred to by marketStatus_var.
	*
	* @param marketStatus_var marketStatus object class instance of which
	* this newly created marketStatus object class instance will be a
	* duplicate
	*/
	public marketStatus( marketStatus marketStatus_var ) {
		super( marketStatus_var );
		
		
		set_price( marketStatus_var.get_price() );
		set_time( marketStatus_var.get_time() );
		set_type( marketStatus_var.get_type() );
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
		
		
		
		if (  "price".equals( datamemberName )  ) return new Float(get_price());
		else if (  "time".equals( datamemberName )  ) return new Integer(get_time());
		else if (  "type".equals( datamemberName )  ) return get_type();
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
		
				
		
		if ( get_price_handle() == datamemberHandle ) return new Float(get_price());
		else if ( get_time_handle() == datamemberHandle ) return new Integer(get_time());
		else if ( get_type_handle() == datamemberHandle ) return get_type();
		else return super.getAttribute( datamemberHandle );
	}
	
	protected boolean setAttributeAux( int param_handle, String val ) {
		boolean retval = true;		
		
			
		
		if ( param_handle == get_price_handle() ) set_price( Float.parseFloat(val) );
		else if ( param_handle == get_time_handle() ) set_time( Integer.parseInt(val) );
		else if ( param_handle == get_type_handle() ) set_type( val );
		else retval = super.setAttributeAux( param_handle, val );
		
		return retval;
	}
	
	protected boolean setAttributeAux( String datamemberName, String val ) {
		boolean retval = true;
		
			
		
		if (  "price".equals( datamemberName )  ) set_price( Float.parseFloat(val) );
		else if (  "time".equals( datamemberName )  ) set_time( Integer.parseInt(val) );
		else if (  "type".equals( datamemberName )  ) set_type( val );	
		else retval = super.setAttributeAux( datamemberName, val );
		
		return retval;
	}
	
	protected boolean setAttributeAux( String datamemberName, Object val ) {
		boolean retval = true;
		
		
		
		if (  "price".equals( datamemberName )  ) set_price( (Float)val );
		else if (  "time".equals( datamemberName )  ) set_time( (Integer)val );
		else if (  "type".equals( datamemberName )  ) set_type( (String)val );		
		else retval = super.setAttributeAux( datamemberName, val );
		
		return retval;
	}

	protected SuppliedAttributes createSuppliedDatamembers( boolean force ) {
		SuppliedAttributes datamembers = super.createSuppliedDatamembers( force );

	
		boolean isPublished = false;
		
		
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_price_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.marketStatus.createSuppliedAttributes:  could not determine if price is published.");
				isPublished = false;
			}
			if (  isPublished && _price.shouldBeUpdated( force )  ) {
				datamembers.add( get_price_handle(), Float.toString(get_price()).getBytes() );
				_price.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_time_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.marketStatus.createSuppliedAttributes:  could not determine if time is published.");
				isPublished = false;
			}
			if (  isPublished && _time.shouldBeUpdated( force )  ) {
				datamembers.add( get_time_handle(), Integer.toString(get_time()).getBytes() );
				_time.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_type_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.marketStatus.createSuppliedAttributes:  could not determine if type is published.");
				isPublished = false;
			}
			if (  isPublished && _type.shouldBeUpdated( force )  ) {
				datamembers.add( get_type_handle(), get_type().getBytes() );
				_type.setHasBeenUpdated();
			}
	
		return datamembers;
	}

	
	public void copyFrom( Object object ) {
		super.copyFrom( object );
		if ( object instanceof marketStatus ) {
			marketStatus data = (marketStatus)object;
			
			
				_price = data._price;
				_time = data._time;
				_type = data._type;
			
		}
	}
}
