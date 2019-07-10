package gov.nist.hla.te.externalload.rti;

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
* Implements ObjectRoot.Meter
*/
public class Meter extends ObjectRoot {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the Meter object class with default attribute values.
    */
    public Meter() {}

    private static int _bill_mode_handle;
    private static int _measured_voltage_1_handle;
    private static int _monthly_fee_handle;
    private static int _name_handle;
    private static int _price_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the Meter object class.
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
    * Returns the fully-qualified (dot-delimited) name of the Meter object class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this object class
    */
    public static String get_class_name() {
        return "ObjectRoot.Meter";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the Meter object class.
    *
    * @return the name of this object class
    */
    public static String get_simple_class_name() {
        return "Meter";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden attributes in the
    * Meter object class.
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
    * Meter object class.
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
        _classNameSet.add("ObjectRoot.Meter");
        _classNameClassMap.put("ObjectRoot.Meter", Meter.class);

        _datamemberClassNameSetMap.put("ObjectRoot.Meter", _datamemberNames);
        _allDatamemberClassNameSetMap.put("ObjectRoot.Meter", _allDatamemberNames);

        _datamemberNames.add("bill_mode");
        _datamemberNames.add("measured_voltage_1");
        _datamemberNames.add("monthly_fee");
        _datamemberNames.add("name");
        _datamemberNames.add("price");

        _datamemberTypeMap.put("bill_mode", "String");
        _datamemberTypeMap.put("measured_voltage_1", "String");
        _datamemberTypeMap.put("monthly_fee", "double");
        _datamemberTypeMap.put("name", "String");
        _datamemberTypeMap.put("price", "double");

        _allDatamemberNames.add("bill_mode");
        _allDatamemberNames.add("measured_voltage_1");
        _allDatamemberNames.add("monthly_fee");
        _allDatamemberNames.add("name");
        _allDatamemberNames.add("price");

        _classNamePublishAttributeNameMap.put("ObjectRoot.Meter", _publishAttributeNameSet);
        _classNameSubscribeAttributeNameMap.put("ObjectRoot.Meter", _subscribeAttributeNameSet);
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        ObjectRoot.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getObjectClassHandle("ObjectRoot.Meter");
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

        _classNameHandleMap.put("ObjectRoot.Meter", get_handle());
        _classHandleNameMap.put(get_handle(), "ObjectRoot.Meter");
        _classHandleSimpleNameMap.put(get_handle(), "Meter");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _bill_mode_handle = rti.getAttributeHandle("bill_mode", get_handle());
                _measured_voltage_1_handle = rti.getAttributeHandle("measured_voltage_1", get_handle());
                _monthly_fee_handle = rti.getAttributeHandle("monthly_fee", get_handle());
                _name_handle = rti.getAttributeHandle("name", get_handle());
                _price_handle = rti.getAttributeHandle("price", get_handle());
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

        _datamemberNameHandleMap.put("ObjectRoot.Meter.bill_mode", _bill_mode_handle);
        _datamemberNameHandleMap.put("ObjectRoot.Meter.measured_voltage_1", _measured_voltage_1_handle);
        _datamemberNameHandleMap.put("ObjectRoot.Meter.monthly_fee", _monthly_fee_handle);
        _datamemberNameHandleMap.put("ObjectRoot.Meter.name", _name_handle);
        _datamemberNameHandleMap.put("ObjectRoot.Meter.price", _price_handle);

        _datamemberHandleNameMap.put(_bill_mode_handle, "bill_mode");
        _datamemberHandleNameMap.put(_measured_voltage_1_handle, "measured_voltage_1");
        _datamemberHandleNameMap.put(_monthly_fee_handle, "monthly_fee");
        _datamemberHandleNameMap.put(_name_handle, "name");
        _datamemberHandleNameMap.put(_price_handle, "price");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the Meter object class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void publish(RTIambassador rti) {
        if (_isPublished) return;

        init(rti);

        AttributeHandleSet publishedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _publishAttributeNameSet) {
            try {
                publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Meter." + attributeName));
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
    * Unpublishes the Meter object class for a federate.
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
    * Subscribes a federate to the Meter object class.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void subscribe(RTIambassador rti) {
        if (_isSubscribed) return;

        init(rti);

        AttributeHandleSet subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _subscribeAttributeNameSet) {
            try {
                subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Meter." + attributeName));
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
    * Unsubscribes a federate from the Meter object class.
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
    * (that is, the Meter object class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the Meter object class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the Meter object class).
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
        if (datamemberHandle == _bill_mode_handle) return "bill_mode";
        else if (datamemberHandle == _measured_voltage_1_handle) return "measured_voltage_1";
        else if (datamemberHandle == _monthly_fee_handle) return "monthly_fee";
        else if (datamemberHandle == _name_handle) return "name";
        else if (datamemberHandle == _price_handle) return "price";
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
                + "bill_mode:" + get_bill_mode()
                + "," + "measured_voltage_1:" + get_measured_voltage_1()
                + "," + "monthly_fee:" + get_monthly_fee()
                + "," + "name:" + get_name()
                + "," + "price:" + get_price()
                + ")";
    }


    /**
    * Publishes the "bill_mode" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "bill_mode" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_bill_mode() {
        _publishAttributeNameSet.add( "bill_mode" );
    }

    /**
    * Unpublishes the "bill_mode" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "bill_mode" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_bill_mode() {
        _publishAttributeNameSet.remove( "bill_mode" );
    }

    /**
    * Subscribes a federate to the "bill_mode" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "bill_mode" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_bill_mode() {
        _subscribeAttributeNameSet.add( "bill_mode" );
    }

    /**
    * Unsubscribes a federate from the "bill_mode" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "bill_mode" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_bill_mode() {
        _subscribeAttributeNameSet.remove( "bill_mode" );
    }

    /**
    * Publishes the "measured_voltage_1" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "measured_voltage_1" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_measured_voltage_1() {
        _publishAttributeNameSet.add( "measured_voltage_1" );
    }

    /**
    * Unpublishes the "measured_voltage_1" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "measured_voltage_1" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_measured_voltage_1() {
        _publishAttributeNameSet.remove( "measured_voltage_1" );
    }

    /**
    * Subscribes a federate to the "measured_voltage_1" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "measured_voltage_1" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_measured_voltage_1() {
        _subscribeAttributeNameSet.add( "measured_voltage_1" );
    }

    /**
    * Unsubscribes a federate from the "measured_voltage_1" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "measured_voltage_1" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_measured_voltage_1() {
        _subscribeAttributeNameSet.remove( "measured_voltage_1" );
    }

    /**
    * Publishes the "monthly_fee" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "monthly_fee" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_monthly_fee() {
        _publishAttributeNameSet.add( "monthly_fee" );
    }

    /**
    * Unpublishes the "monthly_fee" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "monthly_fee" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_monthly_fee() {
        _publishAttributeNameSet.remove( "monthly_fee" );
    }

    /**
    * Subscribes a federate to the "monthly_fee" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "monthly_fee" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_monthly_fee() {
        _subscribeAttributeNameSet.add( "monthly_fee" );
    }

    /**
    * Unsubscribes a federate from the "monthly_fee" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "monthly_fee" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_monthly_fee() {
        _subscribeAttributeNameSet.remove( "monthly_fee" );
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

    protected Attribute< String > _bill_mode =
            new Attribute< String >(  new String( "" )  );

    /**
    * Set the value of the "bill_mode" attribute to "value" for this object.
    *
    * @param value the new value for the "bill_mode" attribute
    */
    public void set_bill_mode( String value ) {
        _bill_mode.setValue( value );
        _bill_mode.setTime( getTime() );
    }

    /**
    * Returns the value of the "bill_mode" attribute of this object.
    *
    * @return the value of the "bill_mode" attribute
    */
    public String get_bill_mode() {
        return _bill_mode.getValue();
    }

    /**
    * Returns the current timestamp of the "bill_mode" attribute of this object.
    *
    * @return the current timestamp of the "bill_mode" attribute
    */
    public double get_bill_mode_time() {
        return _bill_mode.getTime();
    }

    protected Attribute< String > _measured_voltage_1 =
            new Attribute< String >(  new String( "" )  );

    /**
    * Set the value of the "measured_voltage_1" attribute to "value" for this object.
    *
    * @param value the new value for the "measured_voltage_1" attribute
    */
    public void set_measured_voltage_1( String value ) {
        _measured_voltage_1.setValue( value );
        _measured_voltage_1.setTime( getTime() );
    }

    /**
    * Returns the value of the "measured_voltage_1" attribute of this object.
    *
    * @return the value of the "measured_voltage_1" attribute
    */
    public String get_measured_voltage_1() {
        return _measured_voltage_1.getValue();
    }

    /**
    * Returns the current timestamp of the "measured_voltage_1" attribute of this object.
    *
    * @return the current timestamp of the "measured_voltage_1" attribute
    */
    public double get_measured_voltage_1_time() {
        return _measured_voltage_1.getTime();
    }

    protected Attribute< Double > _monthly_fee =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "monthly_fee" attribute to "value" for this object.
    *
    * @param value the new value for the "monthly_fee" attribute
    */
    public void set_monthly_fee( double value ) {
        _monthly_fee.setValue( value );
        _monthly_fee.setTime( getTime() );
    }

    /**
    * Returns the value of the "monthly_fee" attribute of this object.
    *
    * @return the value of the "monthly_fee" attribute
    */
    public double get_monthly_fee() {
        return _monthly_fee.getValue();
    }

    /**
    * Returns the current timestamp of the "monthly_fee" attribute of this object.
    *
    * @return the current timestamp of the "monthly_fee" attribute
    */
    public double get_monthly_fee_time() {
        return _monthly_fee.getTime();
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

    protected Meter( ReflectedAttributes datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    protected Meter( ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    /**
    * Creates an instance of the Meter object class, using
    * "datamemberMap" to initialize its attribute values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new Meter object class instance
    */
    public Meter( ReflectedAttributes datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #Meter( ReflectedAttributes datamemberMap )}, except this
    * new Meter object class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new Meter object class instance
    * @param logicalTime timestamp for this new Meter object class
    * instance
    */
    public Meter( ReflectedAttributes datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new Meter object class instance that is a duplicate
    * of the instance referred to by Meter_var.
    *
    * @param Meter_var Meter object class instance of which
    * this newly created Meter object class instance will be a
    * duplicate
    */
    public Meter( Meter Meter_var ) {
        super( Meter_var );

        set_bill_mode( Meter_var.get_bill_mode() );
        set_measured_voltage_1( Meter_var.get_measured_voltage_1() );
        set_monthly_fee( Meter_var.get_monthly_fee() );
        set_name( Meter_var.get_name() );
        set_price( Meter_var.get_price() );
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
        if ( "bill_mode".equals(datamemberName) ) return get_bill_mode();
        else if ( "measured_voltage_1".equals(datamemberName) ) return get_measured_voltage_1();
        else if ( "monthly_fee".equals(datamemberName) ) return new Double(get_monthly_fee());
        else if ( "name".equals(datamemberName) ) return get_name();
        else if ( "price".equals(datamemberName) ) return new Double(get_price());
        else return super.getAttribute( datamemberName );
    }

    protected boolean setAttributeAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "bill_mode".equals( datamemberName) ) set_bill_mode( val );
        else if ( "measured_voltage_1".equals( datamemberName) ) set_measured_voltage_1( val );
        else if ( "monthly_fee".equals( datamemberName) ) set_monthly_fee( Double.parseDouble(val) );
        else if ( "name".equals( datamemberName) ) set_name( val );
        else if ( "price".equals( datamemberName) ) set_price( Double.parseDouble(val) );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    protected boolean setAttributeAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "bill_mode".equals( datamemberName) ) set_bill_mode( (String)val );
        else if ( "measured_voltage_1".equals( datamemberName) ) set_measured_voltage_1( (String)val );
        else if ( "monthly_fee".equals( datamemberName) ) set_monthly_fee( (Double)val );
        else if ( "name".equals( datamemberName) ) set_name( (String)val );
        else if ( "price".equals( datamemberName) ) set_price( (Double)val );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    @Override
    protected SuppliedAttributes createSuppliedDatamembers(boolean force) {
        SuppliedAttributes datamembers = _factory.createSuppliedAttributes();
 
        if (_publishAttributeNameSet.contains("bill_mode") && _bill_mode.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("bill_mode"), getAttribute("bill_mode").toString().getBytes() );
            _bill_mode.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("measured_voltage_1") && _measured_voltage_1.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("measured_voltage_1"), getAttribute("measured_voltage_1").toString().getBytes() );
            _measured_voltage_1.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("monthly_fee") && _monthly_fee.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("monthly_fee"), getAttribute("monthly_fee").toString().getBytes() );
            _monthly_fee.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("name") && _name.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("name"), getAttribute("name").toString().getBytes() );
            _name.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("price") && _price.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("price"), getAttribute("price").toString().getBytes() );
            _price.setHasBeenUpdated();
        }

        return datamembers;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof Meter ) {
            Meter data = (Meter)object;
            _bill_mode = data._bill_mode;
            _measured_voltage_1 = data._measured_voltage_1;
            _monthly_fee = data._monthly_fee;
            _name = data._name;
            _price = data._price;
        }
    }
}

