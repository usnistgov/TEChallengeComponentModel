
package TEChallenge;

import java.util.HashSet;
import java.util.Set;

import hla.rti.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cpswt.utils.CpswtUtils;


import org.cpswt.hla.*;

/**
* The Transaction class implements the Transaction object in the
* TEChallenge simulation.
*/
public class Transaction extends ObjectRoot {

	private static final Logger logger = LogManager.getLogger(Transaction.class);

	/**
	* Default constructor -- creates an instance of the Transaction object
	* class with default attribute values.
	*/
	public Transaction() { }

	
	
	private static int _accept_handle;
	private static int _tenderId_handle;
	
	
	/**
	* Returns the handle (RTI assigned) of the "accept" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "accept" attribute
	*/
	public static int get_accept_handle() { return _accept_handle; }
	
	/**
	* Returns the handle (RTI assigned) of the "tenderId" attribute of
	* its containing object class.
	*
	* @return the handle (RTI assigned) of the "tenderId" attribute
	*/
	public static int get_tenderId_handle() { return _tenderId_handle; }
	
	
	
	private static boolean _isInitialized = false;

	private static int _handle;

	/**
	* Returns the handle (RTI assigned) of the Transaction object class.
	* Note: As this is a static method, it is NOT polymorphic, and so, if called on
	* a reference will return the handle of the class pertaining to the reference,\
	* rather than the handle of the class for the instance referred to by the reference.
	* For the polymorphic version of this method, use {@link #getClassHandle()}.
	*/
	public static int get_handle() { return _handle; }

	/**
	* Returns the fully-qualified (dot-delimited) name of the Transaction
	* object class.
	* Note: As this is a static method, it is NOT polymorphic, and so, if called on
	* a reference will return the name of the class pertaining to the reference,\
	* rather than the name of the class for the instance referred to by the reference.
	* For the polymorphic version of this method, use {@link #getClassName()}.
	*/
	public static String get_class_name() { return "ObjectRoot.Transaction"; }

	/**
	* Returns the simple name (the last name in the dot-delimited fully-qualified
	* class name) of the Transaction object class.
	*/
	public static String get_simple_class_name() { return "Transaction"; }

	private static Set< String > _datamemberNames = new HashSet< String >();
	private static Set< String > _allDatamemberNames = new HashSet< String >();

	/**
	* Returns a set containing the names of all of the non-hidden attributes in the
	* Transaction object class.
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
	* Transaction object class.
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
		_classNameSet.add("ObjectRoot.Transaction");
		_classNameClassMap.put("ObjectRoot.Transaction", Transaction.class);
		
		_datamemberClassNameSetMap.put("ObjectRoot.Transaction", _datamemberNames);
		_allDatamemberClassNameSetMap.put("ObjectRoot.Transaction", _allDatamemberNames);

		
		
		_datamemberNames.add("accept");
		_datamemberNames.add("tenderId");
		
		
		_allDatamemberNames.add("accept");
		_allDatamemberNames.add("tenderId");
		
		
		_datamemberTypeMap.put("accept", "boolean");
		_datamemberTypeMap.put("tenderId", "int");
	

		_classNamePublishAttributeNameMap.put("ObjectRoot.Transaction", _publishAttributeNameSet);
		_publishedAttributeHandleSet = _factory.createAttributeHandleSet();
		_classNamePublishedAttributeMap.put("ObjectRoot.Transaction", _publishedAttributeHandleSet);

		_classNameSubscribeAttributeNameMap.put("ObjectRoot.Transaction", _subscribeAttributeNameSet);
		_subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
		_classNameSubscribedAttributeMap.put("ObjectRoot.Transaction", _subscribedAttributeHandleSet);
	

	}


	private static String initErrorMessage = "Error:  ObjectRoot.Transaction:  could not initialize:  ";
	protected static void init(RTIambassador rti) {
		if (_isInitialized) return;
		_isInitialized = true;
		
		ObjectRoot.init(rti);
		
		boolean isNotInitialized = true;
		while(isNotInitialized) {
			try {
				_handle = rti.getObjectClassHandle("ObjectRoot.Transaction");
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

		_classNameHandleMap.put("ObjectRoot.Transaction", get_handle());
		_classHandleNameMap.put(get_handle(), "ObjectRoot.Transaction");
		_classHandleSimpleNameMap.put(get_handle(), "Transaction");

		
		isNotInitialized = true;
		while(isNotInitialized) {
			try {
							
				_accept_handle = rti.getAttributeHandle("accept", get_handle());			
				_tenderId_handle = rti.getAttributeHandle("tenderId", get_handle());
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
			
			
		_datamemberNameHandleMap.put("ObjectRoot.Transaction,accept", get_accept_handle());
		_datamemberNameHandleMap.put("ObjectRoot.Transaction,tenderId", get_tenderId_handle());
			
			
		_datamemberHandleNameMap.put(get_accept_handle(), "accept");
		_datamemberHandleNameMap.put(get_tenderId_handle(), "tenderId");
		
	}

	private static boolean _isPublished = false;
	private static String publishErrorMessage = "Error:  ObjectRoot.Transaction:  could not publish:  ";

	/**
	* Publishes the Transaction object class for a federate.
	*
	* @param rti handle to the Local RTI Component
	*/
	public static void publish(RTIambassador rti) {
		if (_isPublished) return;
		
		init(rti);

		
		_publishedAttributeHandleSet.empty();
		for(String attributeName : _publishAttributeNameSet) {
			try {
				_publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Transaction," + attributeName));
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

	private static String unpublishErrorMessage = "Error:  ObjectRoot.Transaction:  could not unpublish:  ";
	/**
	* Unpublishes the Transaction object class for a federate.
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
	private static String subscribeErrorMessage = "Error:  ObjectRoot.Transaction:  could not subscribe:  ";
	/**
	* Subscribes a federate to the Transaction object class.
	*
	* @param rti handle to the Local RTI Component
	*/
	public static void subscribe(RTIambassador rti) {
		if (_isSubscribed) return;
		
		init(rti);
		
		_subscribedAttributeHandleSet.empty();
		for(String attributeName : _subscribeAttributeNameSet) {
			try {
				_subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Transaction," + attributeName));
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

	private static String unsubscribeErrorMessage = "Error:  ObjectRoot.Transaction:  could not unsubscribe:  ";
	/**
	* Unsubscribes a federate from the Transaction object class.
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
	* (that is, the Transaction object class).
	*
	* @param handle handle to compare to the value of the handle (RTI assigned) of
	* this class (the Transaction object class).
	* @return "true" if "handle" matches the value of the handle of this class
	* (that is, the Transaction object class).
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
		return "Transaction("
			
			
			+ "accept:" + get_accept()
			+ "," + "tenderId:" + get_tenderId()
			+ ")";
	}
	



	
	
	/**
	* Publishes the "accept" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "accept" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_accept() {
		_publishAttributeNameSet.add( "accept" );
	}

	/**
	* Unpublishes the "accept" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "accept" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_accept() {
		_publishAttributeNameSet.remove( "accept" );
	}
	
	/**
	* Subscribes a federate to the "accept" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "accept" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_accept() {
		_subscribeAttributeNameSet.add( "accept" );
	}

	/**
	* Unsubscribes a federate from the "accept" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "accept" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_accept() {
		_subscribeAttributeNameSet.remove( "accept" );
	}
	
	
	/**
	* Publishes the "tenderId" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "tenderId" attribute for publication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void publish_tenderId() {
		_publishAttributeNameSet.add( "tenderId" );
	}

	/**
	* Unpublishes the "tenderId" attribute of the attribute's containing object
	* class for a federate.
	* Note:  This method only marks the "tenderId" attribute for unpublication.
	* To actually publish the attribute, the federate must (re)publish its containing
	* object class.
	* (using <objectClassName>.publish( RTIambassador rti ) ).
	*/
	public static void unpublish_tenderId() {
		_publishAttributeNameSet.remove( "tenderId" );
	}
	
	/**
	* Subscribes a federate to the "tenderId" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "tenderId" attribute for subscription.
	* To actually subscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void subscribe_tenderId() {
		_subscribeAttributeNameSet.add( "tenderId" );
	}

	/**
	* Unsubscribes a federate from the "tenderId" attribute of the attribute's
	* containing object class.
	* Note:  This method only marks the "tenderId" attribute for unsubscription.
	* To actually unsubscribe to the attribute, the federate must (re)subscribe to its
	* containing object class.
	* (using <objectClassName>.subscribe( RTIambassador rti ) ).
	*/
	public static void unsubscribe_tenderId() {
		_subscribeAttributeNameSet.remove( "tenderId" );
	}
	

	
	
	private Attribute< Boolean > _accept =
 		new Attribute< Boolean >(  new Boolean( false )  );
	
	/**
	* Set the value of the "accept" attribute to "value" for this object.
	*
	* @param value the new value for the "accept" attribute
	*/
	public void set_accept( boolean value ) {
		_accept.setValue( value );
		_accept.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "accept" attribute of this object.
	*
	* @return the value of the "accept" attribute
	*/
	public boolean get_accept() {
		return _accept.getValue();
	}
	
	/**
	* Returns the current timestamp of the "accept" attribute of this object.
	* 
	* @return the current timestamp of the "accept" attribute
	*/
	public double get_accept_time() {
		return _accept.getTime();
	}
	
	
	private Attribute< Integer > _tenderId =
 		new Attribute< Integer >(  new Integer( 0 )  );
	
	/**
	* Set the value of the "tenderId" attribute to "value" for this object.
	*
	* @param value the new value for the "tenderId" attribute
	*/
	public void set_tenderId( int value ) {
		_tenderId.setValue( value );
		_tenderId.setTime( getTime() );
	}
	
	/**
	* Returns the value of the "tenderId" attribute of this object.
	*
	* @return the value of the "tenderId" attribute
	*/
	public int get_tenderId() {
		return _tenderId.getValue();
	}
	
	/**
	* Returns the current timestamp of the "tenderId" attribute of this object.
	* 
	* @return the current timestamp of the "tenderId" attribute
	*/
	public double get_tenderId_time() {
		return _tenderId.getTime();
	}
	


	protected Transaction( ReflectedAttributes datamemberMap, boolean initFlag ) {
		super( datamemberMap, false );
		if ( initFlag ) setAttributes( datamemberMap );
	}
	
	protected Transaction( ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
		super( datamemberMap, logicalTime, false );
		if ( initFlag ) setAttributes( datamemberMap );
	}


	/**
	* Creates an instance of the Transaction object class, using
	* "datamemberMap" to initialize its attribute values.
	* "datamemberMap" is usually acquired as an argument to an RTI federate
	* callback method, such as "receiveInteraction".
	*
	* @param datamemberMap data structure containing initial values for the
	* attributes of this new Transaction object class instance
	*/
	public Transaction( ReflectedAttributes datamemberMap ) {
		this( datamemberMap, true );
	}
	
	/**
	* Like {@link #Transaction( ReflectedAttributes datamemberMap )}, except this
	* new Transaction object class instance is given a timestamp of
	* "logicalTime".
	*
	* @param datamemberMap data structure containing initial values for the
	* attributes of this new Transaction object class instance
	* @param logicalTime timestamp for this new Transaction object class
	* instance
	*/
	public Transaction( ReflectedAttributes datamemberMap, LogicalTime logicalTime ) {
		this( datamemberMap, logicalTime, true );
	}

	/**
	* Creates a new Transaction object class instance that is a duplicate
	* of the instance referred to by Transaction_var.
	*
	* @param Transaction_var Transaction object class instance of which
	* this newly created Transaction object class instance will be a
	* duplicate
	*/
	public Transaction( Transaction Transaction_var ) {
		super( Transaction_var );
		
		
		set_accept( Transaction_var.get_accept() );
		set_tenderId( Transaction_var.get_tenderId() );
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
		
		
		
		if (  "accept".equals( datamemberName )  ) return new Boolean(get_accept());
		else if (  "tenderId".equals( datamemberName )  ) return new Integer(get_tenderId());
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
		
				
		
		if ( get_accept_handle() == datamemberHandle ) return new Boolean(get_accept());
		else if ( get_tenderId_handle() == datamemberHandle ) return new Integer(get_tenderId());
		else return super.getAttribute( datamemberHandle );
	}
	
	protected boolean setAttributeAux( int param_handle, String val ) {
		boolean retval = true;		
		
			
		
		if ( param_handle == get_accept_handle() ) set_accept( Boolean.parseBoolean(val) );
		else if ( param_handle == get_tenderId_handle() ) set_tenderId( Integer.parseInt(val) );
		else retval = super.setAttributeAux( param_handle, val );
		
		return retval;
	}
	
	protected boolean setAttributeAux( String datamemberName, String val ) {
		boolean retval = true;
		
			
		
		if (  "accept".equals( datamemberName )  ) set_accept( Boolean.parseBoolean(val) );
		else if (  "tenderId".equals( datamemberName )  ) set_tenderId( Integer.parseInt(val) );	
		else retval = super.setAttributeAux( datamemberName, val );
		
		return retval;
	}
	
	protected boolean setAttributeAux( String datamemberName, Object val ) {
		boolean retval = true;
		
		
		
		if (  "accept".equals( datamemberName )  ) set_accept( (Boolean)val );
		else if (  "tenderId".equals( datamemberName )  ) set_tenderId( (Integer)val );		
		else retval = super.setAttributeAux( datamemberName, val );
		
		return retval;
	}

	protected SuppliedAttributes createSuppliedDatamembers( boolean force ) {
		SuppliedAttributes datamembers = super.createSuppliedDatamembers( force );

	
		boolean isPublished = false;
		
		
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_accept_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.Transaction.createSuppliedAttributes:  could not determine if accept is published.");
				isPublished = false;
			}
			if (  isPublished && _accept.shouldBeUpdated( force )  ) {
				datamembers.add( get_accept_handle(), Boolean.toString(get_accept()).getBytes() );
				_accept.setHasBeenUpdated();
			}
			try {
				isPublished = _publishedAttributeHandleSet.isMember( get_tenderId_handle() );
			} catch ( Exception e ) {
				logger.error("ERROR:  ObjectRoot.Transaction.createSuppliedAttributes:  could not determine if tenderId is published.");
				isPublished = false;
			}
			if (  isPublished && _tenderId.shouldBeUpdated( force )  ) {
				datamembers.add( get_tenderId_handle(), Integer.toString(get_tenderId()).getBytes() );
				_tenderId.setHasBeenUpdated();
			}
	
		return datamembers;
	}

	
	public void copyFrom( Object object ) {
		super.copyFrom( object );
		if ( object instanceof Transaction ) {
			Transaction data = (Transaction)object;
			
			
				_accept = data._accept;
				_tenderId = data._tenderId;
			
		}
	}
}
