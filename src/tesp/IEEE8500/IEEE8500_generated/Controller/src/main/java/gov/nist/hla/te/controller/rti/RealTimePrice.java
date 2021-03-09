package gov.nist.hla.te.controller.rti;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cpswt.utils.CpswtUtils;

import hla.rti.AttributeHandleSet;
import hla.rti.FederateNotExecutionMember;
import hla.rti.LogicalTime;
import hla.rti.NameNotFound;
import hla.rti.ObjectClassNotDefined;
import hla.rti.ObjectClassNotPublished;
import hla.rti.ObjectClassNotSubscribed;
import hla.rti.RTIambassador;
import hla.rti.ReflectedAttributes;
import hla.rti.SuppliedAttributes;

import org.cpswt.hla.*;

/**
* Implements ObjectRoot.RealTimePrice
*/
public class RealTimePrice extends ObjectRoot {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the RealTimePrice object class with default attribute values.
    */
    public RealTimePrice() {}

    private static int _price_handle;
    private static int _time_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the RealTimePrice object class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the handle of the class pertaining to the reference,
    * rather than the handle of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassHandle()}.
    *
    * @return the RTI assigned integer handle that represents this object class
    */
    public static int get_handle() {
        return _handle;
    }

    /**
    * Returns the fully-qualified (dot-delimited) name of the RealTimePrice object class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this object class
    */
    public static String get_class_name() {
        return "ObjectRoot.RealTimePrice";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the RealTimePrice object class.
    *
    * @return the name of this object class
    */
    public static String get_simple_class_name() {
        return "RealTimePrice";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden attributes in the
    * RealTimePrice object class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return a set of parameter names pertaining to the reference,
    * rather than the parameter names of the class for the instance referred to by
    * the reference.  For the polymorphic version of this method, use
    * {@link #getAttributeNames()}.
    *
    * @return a modifiable set of the non-hidden attribute names for this object class
    */
    public static Set< String > get_attribute_names() {
        return new HashSet< String >(_datamemberNames);
    }

    /**
    * Returns a set containing the names of all of the attributes in the
    * RealTimePrice object class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return a set of parameter names pertaining to the reference,
    * rather than the parameter names of the class for the instance referred to by
    * the reference.  For the polymorphic version of this method, use
    * {@link #getAttributeNames()}.
    *
    * @return a modifiable set of the attribute names for this object class
    */
    public static Set< String > get_all_attribute_names() {
        return new HashSet< String >(_allDatamemberNames);
    }

    private static Set< String > _publishAttributeNameSet = new HashSet< String >();
    private static Set< String > _subscribeAttributeNameSet = new HashSet< String >();

    static {
        _classNameSet.add("ObjectRoot.RealTimePrice");
        _classNameClassMap.put("ObjectRoot.RealTimePrice", RealTimePrice.class);

        _datamemberClassNameSetMap.put("ObjectRoot.RealTimePrice", _datamemberNames);
        _allDatamemberClassNameSetMap.put("ObjectRoot.RealTimePrice", _allDatamemberNames);

        _datamemberNames.add("price");
        _datamemberNames.add("time");

        _datamemberTypeMap.put("price", "double");
        _datamemberTypeMap.put("time", "String");

        _allDatamemberNames.add("price");
        _allDatamemberNames.add("time");

        _classNamePublishAttributeNameMap.put("ObjectRoot.RealTimePrice", _publishAttributeNameSet);
        _classNameSubscribeAttributeNameMap.put("ObjectRoot.RealTimePrice", _subscribeAttributeNameSet);
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        ObjectRoot.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getObjectClassHandle("ObjectRoot.RealTimePrice");
                isNotInitialized = false;
            } catch (FederateNotExecutionMember e) {
                logger.error("could not initialize: Federate Not Execution Member", e);
                return;
            } catch (NameNotFound e) {
                logger.error("could not initialize: Name Not Found", e);
                return;
            } catch (Exception e) {
                logger.error(e);
                CpswtUtils.sleepDefault();
            }
        }

        _classNameHandleMap.put("ObjectRoot.RealTimePrice", get_handle());
        _classHandleNameMap.put(get_handle(), "ObjectRoot.RealTimePrice");
        _classHandleSimpleNameMap.put(get_handle(), "RealTimePrice");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _price_handle = rti.getAttributeHandle("price", get_handle());
                _time_handle = rti.getAttributeHandle("time", get_handle());
                isNotInitialized = false;
            } catch (FederateNotExecutionMember e) {
                logger.error("could not initialize: Federate Not Execution Member", e);
                return;
            } catch (ObjectClassNotDefined e) {
                logger.error("could not initialize: Object Class Not Defined", e);
                return;
            } catch (NameNotFound e) {
                logger.error("could not initialize: Name Not Found", e);
                return;
            } catch (Exception e) {
                logger.error(e);
                CpswtUtils.sleepDefault();
            }
        }

        _datamemberNameHandleMap.put("ObjectRoot.RealTimePrice.price", _price_handle);
        _datamemberNameHandleMap.put("ObjectRoot.RealTimePrice.time", _time_handle);

        _datamemberHandleNameMap.put(_price_handle, "price");
        _datamemberHandleNameMap.put(_time_handle, "time");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the RealTimePrice object class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void publish(RTIambassador rti) {
        if (_isPublished) return;

        init(rti);

        AttributeHandleSet publishedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _publishAttributeNameSet) {
            try {
                publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.RealTimePrice." + attributeName));
                logger.trace("publish {}:{}", get_class_name(), attributeName);
            } catch (Exception e) {
                logger.error("could not publish \"" + attributeName + "\" attribute.", e);
            }
        }

        synchronized(rti) {
            boolean isNotPublished = true;
            while(isNotPublished) {
                try {
                    rti.publishObjectClass(get_handle(), publishedAttributeHandleSet);
                    isNotPublished = false;
                } catch (FederateNotExecutionMember e) {
                    logger.error("could not publish: Federate Not Execution Member", e);
                    return;
                } catch (ObjectClassNotDefined e) {
                    logger.error("could not publish: Object Class Not Defined", e);
                    return;
                } catch (Exception e) {
                    logger.error(e);
                    CpswtUtils.sleepDefault();
                }
            }
        }

        _isPublished = true;
        logger.debug("publish: {}", get_class_name());
    }

    /**
    * Unpublishes the RealTimePrice object class for a federate.
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
                } catch (FederateNotExecutionMember e) {
                    logger.error("could not unpublish: Federate Not Execution Member", e);
                    return;
                } catch (ObjectClassNotDefined e) {
                    logger.error("could not unpublish: Object Class Not Defined", e);
                    return;
                } catch (ObjectClassNotPublished e) {
                    logger.error("could not unpublish: Object Class Not Published", e);
                    return;
                } catch (Exception e) {
                    logger.error(e);
                    CpswtUtils.sleepDefault();
                }
            }
        }

        _isPublished = false;
        logger.debug("unpublish: {}", get_class_name());
    }

    private static boolean _isSubscribed = false;

    /**
    * Subscribes a federate to the RealTimePrice object class.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void subscribe(RTIambassador rti) {
        if (_isSubscribed) return;

        init(rti);

        AttributeHandleSet subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _subscribeAttributeNameSet) {
            try {
                subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.RealTimePrice." + attributeName));
                logger.trace("subscribe {}:{}", get_class_name(), attributeName);
            } catch (Exception e) {
                logger.error("could not subscribe to \"" + attributeName + "\" attribute.", e);
            }
        }

        synchronized(rti) {
            boolean isNotSubscribed = true;
            while(isNotSubscribed) {
                try {
                    rti.subscribeObjectClassAttributes(get_handle(), subscribedAttributeHandleSet);
                    isNotSubscribed = false;
                } catch (FederateNotExecutionMember e) {
                    logger.error("could not subscribe: Federate Not Execution Member", e);
                    return;
                } catch (ObjectClassNotDefined e) {
                    logger.error("could not subscribe: Object Class Not Defined", e);
                    return;
                } catch (Exception e) {
                    logger.error(e);
                    CpswtUtils.sleepDefault();
                }
            }
        }

        _isSubscribed = true;
        logger.debug("subscribe: {}", get_class_name());
    }

    /**
    * Unsubscribes a federate from the RealTimePrice object class.
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
                } catch (FederateNotExecutionMember e) {
                    logger.error("could not unsubscribe: Federate Not Execution Member", e);
                    return;
                } catch (ObjectClassNotDefined e) {
                    logger.error("could not unsubscribe: Object Class Not Defined", e);
                    return;
                } catch (ObjectClassNotSubscribed e) {
                    logger.error("could not unsubscribe: Object Class Not Subscribed", e);
                    return;
                } catch (Exception e) {
                    logger.error(e);
                    CpswtUtils.sleepDefault();
                }
            }
        }

        _isSubscribed = false;
        logger.debug("unsubscribe: {}", get_class_name());
    }

    /**
    * Return true if "handle" is equal to the handle (RTI assigned) of this class
    * (that is, the RealTimePrice object class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the RealTimePrice object class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the RealTimePrice object class).
    */
    public static boolean match(int handle) {
        return handle == get_handle();
    }

    /**
    * Returns the handle (RTI assigned) of this instance's object class .
    *
    * @return the handle (RTI assigned) if this instance's object class
    */
    public int getClassHandle() {
        return get_handle();
    }

    /**
    * Returns the fully-qualified (dot-delimited) name of this instance's object class.
    *
    * @return the fully-qualified (dot-delimited) name of this instance's object class
    */
    public String getClassName() {
        return get_class_name();
    }

    /**
    * Returns the simple name (last name in its fully-qualified dot-delimited name)
    * of this instance's object class.
    *
    * @return the simple name of this instance's object class
    */
    public String getSimpleClassName() {
        return get_simple_class_name();
    }

    /**
    * Returns a set containing the names of all of the non-hiddenattributes of an
    * object class instance.
    *
    * @return set containing the names of all of the attributes of an
    * object class instance
    */
    public Set< String > getAttributeNames() {
        return get_attribute_names();
    }

    /**
    * Returns a set containing the names of all of the attributes of an
    * object class instance.
    *
    * @return set containing the names of all of the attributes of an
    * object class instance
    */
    public Set< String > getAllAttributeNames() {
        return get_all_attribute_names();
    }

    @Override
    public String getAttributeName(int datamemberHandle) {
        if (datamemberHandle == _price_handle) return "price";
        else if (datamemberHandle == _time_handle) return "time";
        else return super.getAttributeName(datamemberHandle);
    }

    /**
    * Publishes the object class of this instance of the class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public void publishObject(RTIambassador rti) {
        publish(rti);
    }

    /**
    * Unpublishes the object class of this instance of this class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public void unpublishObject(RTIambassador rti) {
        unpublish(rti);
    }

    /**
    * Subscribes a federate to the object class of this instance of this class.
    *
    * @param rti handle to the Local RTI Component
    */
    public void subscribeObject(RTIambassador rti) {
        subscribe(rti);
    }

    /**
    * Unsubscribes a federate from the object class of this instance of this class.
    *
    * @param rti handle to the Local RTI Component
    */
    public void unsubscribeObject(RTIambassador rti) {
        unsubscribe(rti);
    }

    @Override
    public String toString() {
        return getClass().getName() + "("
                + "price:" + get_price()
                + "," + "time:" + get_time()
                + ")";
    }


    /**
    * Publishes the "price" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "price" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
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
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
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
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
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
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
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
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
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
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
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
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
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
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_time() {
        _subscribeAttributeNameSet.remove( "time" );
    }

    protected Attribute< Double > _price =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "price" attribute to "value" for this object.
    *
    * @param value the new value for the "price" attribute
    */
    public void set_price( double value ) {
        _price.setValue( value );
        _price.setTime( getTime() );
    }

    /**
    * Returns the value of the "price" attribute of this object.
    *
    * @return the value of the "price" attribute
    */
    public double get_price() {
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

    protected Attribute< String > _time =
            new Attribute< String >(  new String( "" )  );

    /**
    * Set the value of the "time" attribute to "value" for this object.
    *
    * @param value the new value for the "time" attribute
    */
    public void set_time( String value ) {
        _time.setValue( value );
        _time.setTime( getTime() );
    }

    /**
    * Returns the value of the "time" attribute of this object.
    *
    * @return the value of the "time" attribute
    */
    public String get_time() {
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

    protected RealTimePrice( ReflectedAttributes datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    protected RealTimePrice( ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    /**
    * Creates an instance of the RealTimePrice object class, using
    * "datamemberMap" to initialize its attribute values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new RealTimePrice object class instance
    */
    public RealTimePrice( ReflectedAttributes datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #RealTimePrice( ReflectedAttributes datamemberMap )}, except this
    * new RealTimePrice object class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new RealTimePrice object class instance
    * @param logicalTime timestamp for this new RealTimePrice object class
    * instance
    */
    public RealTimePrice( ReflectedAttributes datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new RealTimePrice object class instance that is a duplicate
    * of the instance referred to by RealTimePrice_var.
    *
    * @param RealTimePrice_var RealTimePrice object class instance of which
    * this newly created RealTimePrice object class instance will be a
    * duplicate
    */
    public RealTimePrice( RealTimePrice RealTimePrice_var ) {
        super( RealTimePrice_var );

        set_price( RealTimePrice_var.get_price() );
        set_time( RealTimePrice_var.get_time() );
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
        if ( "price".equals(datamemberName) ) return new Double(get_price());
        else if ( "time".equals(datamemberName) ) return get_time();
        else return super.getAttribute( datamemberName );
    }

    protected boolean setAttributeAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "price".equals( datamemberName) ) set_price( Double.parseDouble(val) );
        else if ( "time".equals( datamemberName) ) set_time( val );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    protected boolean setAttributeAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "price".equals( datamemberName) ) set_price( (Double)val );
        else if ( "time".equals( datamemberName) ) set_time( (String)val );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    @Override
    protected SuppliedAttributes createSuppliedDatamembers(boolean force) {
        SuppliedAttributes datamembers = _factory.createSuppliedAttributes();
 
        if (_publishAttributeNameSet.contains("price") && _price.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("price"), getAttribute("price").toString().getBytes() );
            _price.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("time") && _time.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("time"), getAttribute("time").toString().getBytes() );
            _time.setHasBeenUpdated();
        }

        return datamembers;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof RealTimePrice ) {
            RealTimePrice data = (RealTimePrice)object;
            _price = data._price;
            _time = data._time;
        }
    }
}

