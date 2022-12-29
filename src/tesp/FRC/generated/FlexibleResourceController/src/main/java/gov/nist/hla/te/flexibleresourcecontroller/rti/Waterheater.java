package gov.nist.hla.te.flexibleresourcecontroller.rti;

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
* Implements ObjectRoot.Waterheater
*/
public class Waterheater extends ObjectRoot {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the Waterheater object class with default attribute values.
    */
    public Waterheater() {}

    private static int _lower_tank_setpoint_handle;
    private static int _name_handle;
    private static int _tank_setpoint_handle;
    private static int _upper_tank_setpoint_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the Waterheater object class.
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
    * Returns the fully-qualified (dot-delimited) name of the Waterheater object class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this object class
    */
    public static String get_class_name() {
        return "ObjectRoot.Waterheater";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the Waterheater object class.
    *
    * @return the name of this object class
    */
    public static String get_simple_class_name() {
        return "Waterheater";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden attributes in the
    * Waterheater object class.
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
    * Waterheater object class.
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
        _classNameSet.add("ObjectRoot.Waterheater");
        _classNameClassMap.put("ObjectRoot.Waterheater", Waterheater.class);

        _datamemberClassNameSetMap.put("ObjectRoot.Waterheater", _datamemberNames);
        _allDatamemberClassNameSetMap.put("ObjectRoot.Waterheater", _allDatamemberNames);

        _datamemberNames.add("lower_tank_setpoint");
        _datamemberNames.add("name");
        _datamemberNames.add("tank_setpoint");
        _datamemberNames.add("upper_tank_setpoint");

        _datamemberTypeMap.put("lower_tank_setpoint", "double");
        _datamemberTypeMap.put("name", "String");
        _datamemberTypeMap.put("tank_setpoint", "double");
        _datamemberTypeMap.put("upper_tank_setpoint", "double");

        _allDatamemberNames.add("lower_tank_setpoint");
        _allDatamemberNames.add("name");
        _allDatamemberNames.add("tank_setpoint");
        _allDatamemberNames.add("upper_tank_setpoint");

        _classNamePublishAttributeNameMap.put("ObjectRoot.Waterheater", _publishAttributeNameSet);
        _classNameSubscribeAttributeNameMap.put("ObjectRoot.Waterheater", _subscribeAttributeNameSet);
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        ObjectRoot.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getObjectClassHandle("ObjectRoot.Waterheater");
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

        _classNameHandleMap.put("ObjectRoot.Waterheater", get_handle());
        _classHandleNameMap.put(get_handle(), "ObjectRoot.Waterheater");
        _classHandleSimpleNameMap.put(get_handle(), "Waterheater");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _lower_tank_setpoint_handle = rti.getAttributeHandle("lower_tank_setpoint", get_handle());
                _name_handle = rti.getAttributeHandle("name", get_handle());
                _tank_setpoint_handle = rti.getAttributeHandle("tank_setpoint", get_handle());
                _upper_tank_setpoint_handle = rti.getAttributeHandle("upper_tank_setpoint", get_handle());
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

        _datamemberNameHandleMap.put("ObjectRoot.Waterheater.lower_tank_setpoint", _lower_tank_setpoint_handle);
        _datamemberNameHandleMap.put("ObjectRoot.Waterheater.name", _name_handle);
        _datamemberNameHandleMap.put("ObjectRoot.Waterheater.tank_setpoint", _tank_setpoint_handle);
        _datamemberNameHandleMap.put("ObjectRoot.Waterheater.upper_tank_setpoint", _upper_tank_setpoint_handle);

        _datamemberHandleNameMap.put(_lower_tank_setpoint_handle, "lower_tank_setpoint");
        _datamemberHandleNameMap.put(_name_handle, "name");
        _datamemberHandleNameMap.put(_tank_setpoint_handle, "tank_setpoint");
        _datamemberHandleNameMap.put(_upper_tank_setpoint_handle, "upper_tank_setpoint");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the Waterheater object class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void publish(RTIambassador rti) {
        if (_isPublished) return;

        init(rti);

        AttributeHandleSet publishedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _publishAttributeNameSet) {
            try {
                publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Waterheater." + attributeName));
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
    * Unpublishes the Waterheater object class for a federate.
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
    * Subscribes a federate to the Waterheater object class.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void subscribe(RTIambassador rti) {
        if (_isSubscribed) return;

        init(rti);

        AttributeHandleSet subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _subscribeAttributeNameSet) {
            try {
                subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Waterheater." + attributeName));
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
    * Unsubscribes a federate from the Waterheater object class.
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
    * (that is, the Waterheater object class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the Waterheater object class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the Waterheater object class).
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
        if (datamemberHandle == _lower_tank_setpoint_handle) return "lower_tank_setpoint";
        else if (datamemberHandle == _name_handle) return "name";
        else if (datamemberHandle == _tank_setpoint_handle) return "tank_setpoint";
        else if (datamemberHandle == _upper_tank_setpoint_handle) return "upper_tank_setpoint";
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
                + "lower_tank_setpoint:" + get_lower_tank_setpoint()
                + "," + "name:" + get_name()
                + "," + "tank_setpoint:" + get_tank_setpoint()
                + "," + "upper_tank_setpoint:" + get_upper_tank_setpoint()
                + ")";
    }


    /**
    * Publishes the "lower_tank_setpoint" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "lower_tank_setpoint" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_lower_tank_setpoint() {
        _publishAttributeNameSet.add( "lower_tank_setpoint" );
    }

    /**
    * Unpublishes the "lower_tank_setpoint" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "lower_tank_setpoint" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_lower_tank_setpoint() {
        _publishAttributeNameSet.remove( "lower_tank_setpoint" );
    }

    /**
    * Subscribes a federate to the "lower_tank_setpoint" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "lower_tank_setpoint" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_lower_tank_setpoint() {
        _subscribeAttributeNameSet.add( "lower_tank_setpoint" );
    }

    /**
    * Unsubscribes a federate from the "lower_tank_setpoint" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "lower_tank_setpoint" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_lower_tank_setpoint() {
        _subscribeAttributeNameSet.remove( "lower_tank_setpoint" );
    }

    /**
    * Publishes the "name" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "name" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_name() {
        _publishAttributeNameSet.add( "name" );
    }

    /**
    * Unpublishes the "name" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "name" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_name() {
        _publishAttributeNameSet.remove( "name" );
    }

    /**
    * Subscribes a federate to the "name" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "name" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_name() {
        _subscribeAttributeNameSet.add( "name" );
    }

    /**
    * Unsubscribes a federate from the "name" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "name" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_name() {
        _subscribeAttributeNameSet.remove( "name" );
    }

    /**
    * Publishes the "tank_setpoint" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "tank_setpoint" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_tank_setpoint() {
        _publishAttributeNameSet.add( "tank_setpoint" );
    }

    /**
    * Unpublishes the "tank_setpoint" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "tank_setpoint" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_tank_setpoint() {
        _publishAttributeNameSet.remove( "tank_setpoint" );
    }

    /**
    * Subscribes a federate to the "tank_setpoint" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "tank_setpoint" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_tank_setpoint() {
        _subscribeAttributeNameSet.add( "tank_setpoint" );
    }

    /**
    * Unsubscribes a federate from the "tank_setpoint" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "tank_setpoint" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_tank_setpoint() {
        _subscribeAttributeNameSet.remove( "tank_setpoint" );
    }

    /**
    * Publishes the "upper_tank_setpoint" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "upper_tank_setpoint" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_upper_tank_setpoint() {
        _publishAttributeNameSet.add( "upper_tank_setpoint" );
    }

    /**
    * Unpublishes the "upper_tank_setpoint" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "upper_tank_setpoint" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_upper_tank_setpoint() {
        _publishAttributeNameSet.remove( "upper_tank_setpoint" );
    }

    /**
    * Subscribes a federate to the "upper_tank_setpoint" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "upper_tank_setpoint" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_upper_tank_setpoint() {
        _subscribeAttributeNameSet.add( "upper_tank_setpoint" );
    }

    /**
    * Unsubscribes a federate from the "upper_tank_setpoint" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "upper_tank_setpoint" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_upper_tank_setpoint() {
        _subscribeAttributeNameSet.remove( "upper_tank_setpoint" );
    }

    protected Attribute< Double > _lower_tank_setpoint =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "lower_tank_setpoint" attribute to "value" for this object.
    *
    * @param value the new value for the "lower_tank_setpoint" attribute
    */
    public void set_lower_tank_setpoint( double value ) {
        _lower_tank_setpoint.setValue( value );
        _lower_tank_setpoint.setTime( getTime() );
    }

    /**
    * Returns the value of the "lower_tank_setpoint" attribute of this object.
    *
    * @return the value of the "lower_tank_setpoint" attribute
    */
    public double get_lower_tank_setpoint() {
        return _lower_tank_setpoint.getValue();
    }

    /**
    * Returns the current timestamp of the "lower_tank_setpoint" attribute of this object.
    *
    * @return the current timestamp of the "lower_tank_setpoint" attribute
    */
    public double get_lower_tank_setpoint_time() {
        return _lower_tank_setpoint.getTime();
    }

    protected Attribute< String > _name =
            new Attribute< String >(  new String( "" )  );

    /**
    * Set the value of the "name" attribute to "value" for this object.
    *
    * @param value the new value for the "name" attribute
    */
    public void set_name( String value ) {
        _name.setValue( value );
        _name.setTime( getTime() );
    }

    /**
    * Returns the value of the "name" attribute of this object.
    *
    * @return the value of the "name" attribute
    */
    public String get_name() {
        return _name.getValue();
    }

    /**
    * Returns the current timestamp of the "name" attribute of this object.
    *
    * @return the current timestamp of the "name" attribute
    */
    public double get_name_time() {
        return _name.getTime();
    }

    protected Attribute< Double > _tank_setpoint =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "tank_setpoint" attribute to "value" for this object.
    *
    * @param value the new value for the "tank_setpoint" attribute
    */
    public void set_tank_setpoint( double value ) {
        _tank_setpoint.setValue( value );
        _tank_setpoint.setTime( getTime() );
    }

    /**
    * Returns the value of the "tank_setpoint" attribute of this object.
    *
    * @return the value of the "tank_setpoint" attribute
    */
    public double get_tank_setpoint() {
        return _tank_setpoint.getValue();
    }

    /**
    * Returns the current timestamp of the "tank_setpoint" attribute of this object.
    *
    * @return the current timestamp of the "tank_setpoint" attribute
    */
    public double get_tank_setpoint_time() {
        return _tank_setpoint.getTime();
    }

    protected Attribute< Double > _upper_tank_setpoint =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "upper_tank_setpoint" attribute to "value" for this object.
    *
    * @param value the new value for the "upper_tank_setpoint" attribute
    */
    public void set_upper_tank_setpoint( double value ) {
        _upper_tank_setpoint.setValue( value );
        _upper_tank_setpoint.setTime( getTime() );
    }

    /**
    * Returns the value of the "upper_tank_setpoint" attribute of this object.
    *
    * @return the value of the "upper_tank_setpoint" attribute
    */
    public double get_upper_tank_setpoint() {
        return _upper_tank_setpoint.getValue();
    }

    /**
    * Returns the current timestamp of the "upper_tank_setpoint" attribute of this object.
    *
    * @return the current timestamp of the "upper_tank_setpoint" attribute
    */
    public double get_upper_tank_setpoint_time() {
        return _upper_tank_setpoint.getTime();
    }

    protected Waterheater( ReflectedAttributes datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    protected Waterheater( ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    /**
    * Creates an instance of the Waterheater object class, using
    * "datamemberMap" to initialize its attribute values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new Waterheater object class instance
    */
    public Waterheater( ReflectedAttributes datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #Waterheater( ReflectedAttributes datamemberMap )}, except this
    * new Waterheater object class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new Waterheater object class instance
    * @param logicalTime timestamp for this new Waterheater object class
    * instance
    */
    public Waterheater( ReflectedAttributes datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new Waterheater object class instance that is a duplicate
    * of the instance referred to by Waterheater_var.
    *
    * @param Waterheater_var Waterheater object class instance of which
    * this newly created Waterheater object class instance will be a
    * duplicate
    */
    public Waterheater( Waterheater Waterheater_var ) {
        super( Waterheater_var );

        set_lower_tank_setpoint( Waterheater_var.get_lower_tank_setpoint() );
        set_name( Waterheater_var.get_name() );
        set_tank_setpoint( Waterheater_var.get_tank_setpoint() );
        set_upper_tank_setpoint( Waterheater_var.get_upper_tank_setpoint() );
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
        if ( "lower_tank_setpoint".equals(datamemberName) ) return new Double(get_lower_tank_setpoint());
        else if ( "name".equals(datamemberName) ) return get_name();
        else if ( "tank_setpoint".equals(datamemberName) ) return new Double(get_tank_setpoint());
        else if ( "upper_tank_setpoint".equals(datamemberName) ) return new Double(get_upper_tank_setpoint());
        else return super.getAttribute( datamemberName );
    }

    protected boolean setAttributeAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "lower_tank_setpoint".equals( datamemberName) ) set_lower_tank_setpoint( Double.parseDouble(val) );
        else if ( "name".equals( datamemberName) ) set_name( val );
        else if ( "tank_setpoint".equals( datamemberName) ) set_tank_setpoint( Double.parseDouble(val) );
        else if ( "upper_tank_setpoint".equals( datamemberName) ) set_upper_tank_setpoint( Double.parseDouble(val) );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    protected boolean setAttributeAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "lower_tank_setpoint".equals( datamemberName) ) set_lower_tank_setpoint( (Double)val );
        else if ( "name".equals( datamemberName) ) set_name( (String)val );
        else if ( "tank_setpoint".equals( datamemberName) ) set_tank_setpoint( (Double)val );
        else if ( "upper_tank_setpoint".equals( datamemberName) ) set_upper_tank_setpoint( (Double)val );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    @Override
    protected SuppliedAttributes createSuppliedDatamembers(boolean force) {
        SuppliedAttributes datamembers = _factory.createSuppliedAttributes();
 
        if (_publishAttributeNameSet.contains("lower_tank_setpoint") && _lower_tank_setpoint.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("lower_tank_setpoint"), getAttribute("lower_tank_setpoint").toString().getBytes() );
            _lower_tank_setpoint.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("name") && _name.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("name"), getAttribute("name").toString().getBytes() );
            _name.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("tank_setpoint") && _tank_setpoint.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("tank_setpoint"), getAttribute("tank_setpoint").toString().getBytes() );
            _tank_setpoint.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("upper_tank_setpoint") && _upper_tank_setpoint.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("upper_tank_setpoint"), getAttribute("upper_tank_setpoint").toString().getBytes() );
            _upper_tank_setpoint.setHasBeenUpdated();
        }

        return datamembers;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof Waterheater ) {
            Waterheater data = (Waterheater)object;
            _lower_tank_setpoint = data._lower_tank_setpoint;
            _name = data._name;
            _tank_setpoint = data._tank_setpoint;
            _upper_tank_setpoint = data._upper_tank_setpoint;
        }
    }
}

