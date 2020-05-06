package gov.nist.hla.te.auction.rti;

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
* Implements ObjectRoot.House
*/
public class House extends ObjectRoot {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the House object class with default attribute values.
    */
    public House() {}

    private static int _air_temperature_handle;
    private static int _cooling_setpoint_handle;
    private static int _heating_setpoint_handle;
    private static int _hvac_load_handle;
    private static int _name_handle;
    private static int _power_state_handle;
    private static int _thermostat_deadband_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the House object class.
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
    * Returns the fully-qualified (dot-delimited) name of the House object class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this object class
    */
    public static String get_class_name() {
        return "ObjectRoot.House";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the House object class.
    *
    * @return the name of this object class
    */
    public static String get_simple_class_name() {
        return "House";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden attributes in the
    * House object class.
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
    * House object class.
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
        _classNameSet.add("ObjectRoot.House");
        _classNameClassMap.put("ObjectRoot.House", House.class);

        _datamemberClassNameSetMap.put("ObjectRoot.House", _datamemberNames);
        _allDatamemberClassNameSetMap.put("ObjectRoot.House", _allDatamemberNames);

        _datamemberNames.add("air_temperature");
        _datamemberNames.add("cooling_setpoint");
        _datamemberNames.add("heating_setpoint");
        _datamemberNames.add("hvac_load");
        _datamemberNames.add("name");
        _datamemberNames.add("power_state");
        _datamemberNames.add("thermostat_deadband");

        _datamemberTypeMap.put("air_temperature", "double");
        _datamemberTypeMap.put("cooling_setpoint", "double");
        _datamemberTypeMap.put("heating_setpoint", "double");
        _datamemberTypeMap.put("hvac_load", "double");
        _datamemberTypeMap.put("name", "String");
        _datamemberTypeMap.put("power_state", "String");
        _datamemberTypeMap.put("thermostat_deadband", "double");

        _allDatamemberNames.add("air_temperature");
        _allDatamemberNames.add("cooling_setpoint");
        _allDatamemberNames.add("heating_setpoint");
        _allDatamemberNames.add("hvac_load");
        _allDatamemberNames.add("name");
        _allDatamemberNames.add("power_state");
        _allDatamemberNames.add("thermostat_deadband");

        _classNamePublishAttributeNameMap.put("ObjectRoot.House", _publishAttributeNameSet);
        _classNameSubscribeAttributeNameMap.put("ObjectRoot.House", _subscribeAttributeNameSet);
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        ObjectRoot.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getObjectClassHandle("ObjectRoot.House");
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

        _classNameHandleMap.put("ObjectRoot.House", get_handle());
        _classHandleNameMap.put(get_handle(), "ObjectRoot.House");
        _classHandleSimpleNameMap.put(get_handle(), "House");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _air_temperature_handle = rti.getAttributeHandle("air_temperature", get_handle());
                _cooling_setpoint_handle = rti.getAttributeHandle("cooling_setpoint", get_handle());
                _heating_setpoint_handle = rti.getAttributeHandle("heating_setpoint", get_handle());
                _hvac_load_handle = rti.getAttributeHandle("hvac_load", get_handle());
                _name_handle = rti.getAttributeHandle("name", get_handle());
                _power_state_handle = rti.getAttributeHandle("power_state", get_handle());
                _thermostat_deadband_handle = rti.getAttributeHandle("thermostat_deadband", get_handle());
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

        _datamemberNameHandleMap.put("ObjectRoot.House.air_temperature", _air_temperature_handle);
        _datamemberNameHandleMap.put("ObjectRoot.House.cooling_setpoint", _cooling_setpoint_handle);
        _datamemberNameHandleMap.put("ObjectRoot.House.heating_setpoint", _heating_setpoint_handle);
        _datamemberNameHandleMap.put("ObjectRoot.House.hvac_load", _hvac_load_handle);
        _datamemberNameHandleMap.put("ObjectRoot.House.name", _name_handle);
        _datamemberNameHandleMap.put("ObjectRoot.House.power_state", _power_state_handle);
        _datamemberNameHandleMap.put("ObjectRoot.House.thermostat_deadband", _thermostat_deadband_handle);

        _datamemberHandleNameMap.put(_air_temperature_handle, "air_temperature");
        _datamemberHandleNameMap.put(_cooling_setpoint_handle, "cooling_setpoint");
        _datamemberHandleNameMap.put(_heating_setpoint_handle, "heating_setpoint");
        _datamemberHandleNameMap.put(_hvac_load_handle, "hvac_load");
        _datamemberHandleNameMap.put(_name_handle, "name");
        _datamemberHandleNameMap.put(_power_state_handle, "power_state");
        _datamemberHandleNameMap.put(_thermostat_deadband_handle, "thermostat_deadband");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the House object class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void publish(RTIambassador rti) {
        if (_isPublished) return;

        init(rti);

        AttributeHandleSet publishedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _publishAttributeNameSet) {
            try {
                publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.House." + attributeName));
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
    * Unpublishes the House object class for a federate.
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
    * Subscribes a federate to the House object class.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void subscribe(RTIambassador rti) {
        if (_isSubscribed) return;

        init(rti);

        AttributeHandleSet subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _subscribeAttributeNameSet) {
            try {
                subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.House." + attributeName));
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
    * Unsubscribes a federate from the House object class.
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
    * (that is, the House object class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the House object class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the House object class).
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
        if (datamemberHandle == _air_temperature_handle) return "air_temperature";
        else if (datamemberHandle == _cooling_setpoint_handle) return "cooling_setpoint";
        else if (datamemberHandle == _heating_setpoint_handle) return "heating_setpoint";
        else if (datamemberHandle == _hvac_load_handle) return "hvac_load";
        else if (datamemberHandle == _name_handle) return "name";
        else if (datamemberHandle == _power_state_handle) return "power_state";
        else if (datamemberHandle == _thermostat_deadband_handle) return "thermostat_deadband";
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
                + "air_temperature:" + get_air_temperature()
                + "," + "cooling_setpoint:" + get_cooling_setpoint()
                + "," + "heating_setpoint:" + get_heating_setpoint()
                + "," + "hvac_load:" + get_hvac_load()
                + "," + "name:" + get_name()
                + "," + "power_state:" + get_power_state()
                + "," + "thermostat_deadband:" + get_thermostat_deadband()
                + ")";
    }


    /**
    * Publishes the "air_temperature" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "air_temperature" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_air_temperature() {
        _publishAttributeNameSet.add( "air_temperature" );
    }

    /**
    * Unpublishes the "air_temperature" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "air_temperature" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_air_temperature() {
        _publishAttributeNameSet.remove( "air_temperature" );
    }

    /**
    * Subscribes a federate to the "air_temperature" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "air_temperature" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_air_temperature() {
        _subscribeAttributeNameSet.add( "air_temperature" );
    }

    /**
    * Unsubscribes a federate from the "air_temperature" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "air_temperature" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_air_temperature() {
        _subscribeAttributeNameSet.remove( "air_temperature" );
    }

    /**
    * Publishes the "cooling_setpoint" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "cooling_setpoint" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_cooling_setpoint() {
        _publishAttributeNameSet.add( "cooling_setpoint" );
    }

    /**
    * Unpublishes the "cooling_setpoint" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "cooling_setpoint" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_cooling_setpoint() {
        _publishAttributeNameSet.remove( "cooling_setpoint" );
    }

    /**
    * Subscribes a federate to the "cooling_setpoint" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "cooling_setpoint" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_cooling_setpoint() {
        _subscribeAttributeNameSet.add( "cooling_setpoint" );
    }

    /**
    * Unsubscribes a federate from the "cooling_setpoint" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "cooling_setpoint" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_cooling_setpoint() {
        _subscribeAttributeNameSet.remove( "cooling_setpoint" );
    }

    /**
    * Publishes the "heating_setpoint" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "heating_setpoint" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_heating_setpoint() {
        _publishAttributeNameSet.add( "heating_setpoint" );
    }

    /**
    * Unpublishes the "heating_setpoint" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "heating_setpoint" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_heating_setpoint() {
        _publishAttributeNameSet.remove( "heating_setpoint" );
    }

    /**
    * Subscribes a federate to the "heating_setpoint" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "heating_setpoint" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_heating_setpoint() {
        _subscribeAttributeNameSet.add( "heating_setpoint" );
    }

    /**
    * Unsubscribes a federate from the "heating_setpoint" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "heating_setpoint" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_heating_setpoint() {
        _subscribeAttributeNameSet.remove( "heating_setpoint" );
    }

    /**
    * Publishes the "hvac_load" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "hvac_load" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_hvac_load() {
        _publishAttributeNameSet.add( "hvac_load" );
    }

    /**
    * Unpublishes the "hvac_load" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "hvac_load" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_hvac_load() {
        _publishAttributeNameSet.remove( "hvac_load" );
    }

    /**
    * Subscribes a federate to the "hvac_load" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "hvac_load" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_hvac_load() {
        _subscribeAttributeNameSet.add( "hvac_load" );
    }

    /**
    * Unsubscribes a federate from the "hvac_load" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "hvac_load" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_hvac_load() {
        _subscribeAttributeNameSet.remove( "hvac_load" );
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
    * Publishes the "power_state" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "power_state" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_power_state() {
        _publishAttributeNameSet.add( "power_state" );
    }

    /**
    * Unpublishes the "power_state" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "power_state" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_power_state() {
        _publishAttributeNameSet.remove( "power_state" );
    }

    /**
    * Subscribes a federate to the "power_state" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "power_state" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_power_state() {
        _subscribeAttributeNameSet.add( "power_state" );
    }

    /**
    * Unsubscribes a federate from the "power_state" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "power_state" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_power_state() {
        _subscribeAttributeNameSet.remove( "power_state" );
    }

    /**
    * Publishes the "thermostat_deadband" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "thermostat_deadband" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_thermostat_deadband() {
        _publishAttributeNameSet.add( "thermostat_deadband" );
    }

    /**
    * Unpublishes the "thermostat_deadband" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "thermostat_deadband" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_thermostat_deadband() {
        _publishAttributeNameSet.remove( "thermostat_deadband" );
    }

    /**
    * Subscribes a federate to the "thermostat_deadband" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "thermostat_deadband" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_thermostat_deadband() {
        _subscribeAttributeNameSet.add( "thermostat_deadband" );
    }

    /**
    * Unsubscribes a federate from the "thermostat_deadband" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "thermostat_deadband" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_thermostat_deadband() {
        _subscribeAttributeNameSet.remove( "thermostat_deadband" );
    }

    protected Attribute< Double > _air_temperature =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "air_temperature" attribute to "value" for this object.
    *
    * @param value the new value for the "air_temperature" attribute
    */
    public void set_air_temperature( double value ) {
        _air_temperature.setValue( value );
        _air_temperature.setTime( getTime() );
    }

    /**
    * Returns the value of the "air_temperature" attribute of this object.
    *
    * @return the value of the "air_temperature" attribute
    */
    public double get_air_temperature() {
        return _air_temperature.getValue();
    }

    /**
    * Returns the current timestamp of the "air_temperature" attribute of this object.
    *
    * @return the current timestamp of the "air_temperature" attribute
    */
    public double get_air_temperature_time() {
        return _air_temperature.getTime();
    }

    protected Attribute< Double > _cooling_setpoint =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "cooling_setpoint" attribute to "value" for this object.
    *
    * @param value the new value for the "cooling_setpoint" attribute
    */
    public void set_cooling_setpoint( double value ) {
        _cooling_setpoint.setValue( value );
        _cooling_setpoint.setTime( getTime() );
    }

    /**
    * Returns the value of the "cooling_setpoint" attribute of this object.
    *
    * @return the value of the "cooling_setpoint" attribute
    */
    public double get_cooling_setpoint() {
        return _cooling_setpoint.getValue();
    }

    /**
    * Returns the current timestamp of the "cooling_setpoint" attribute of this object.
    *
    * @return the current timestamp of the "cooling_setpoint" attribute
    */
    public double get_cooling_setpoint_time() {
        return _cooling_setpoint.getTime();
    }

    protected Attribute< Double > _heating_setpoint =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "heating_setpoint" attribute to "value" for this object.
    *
    * @param value the new value for the "heating_setpoint" attribute
    */
    public void set_heating_setpoint( double value ) {
        _heating_setpoint.setValue( value );
        _heating_setpoint.setTime( getTime() );
    }

    /**
    * Returns the value of the "heating_setpoint" attribute of this object.
    *
    * @return the value of the "heating_setpoint" attribute
    */
    public double get_heating_setpoint() {
        return _heating_setpoint.getValue();
    }

    /**
    * Returns the current timestamp of the "heating_setpoint" attribute of this object.
    *
    * @return the current timestamp of the "heating_setpoint" attribute
    */
    public double get_heating_setpoint_time() {
        return _heating_setpoint.getTime();
    }

    protected Attribute< Double > _hvac_load =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "hvac_load" attribute to "value" for this object.
    *
    * @param value the new value for the "hvac_load" attribute
    */
    public void set_hvac_load( double value ) {
        _hvac_load.setValue( value );
        _hvac_load.setTime( getTime() );
    }

    /**
    * Returns the value of the "hvac_load" attribute of this object.
    *
    * @return the value of the "hvac_load" attribute
    */
    public double get_hvac_load() {
        return _hvac_load.getValue();
    }

    /**
    * Returns the current timestamp of the "hvac_load" attribute of this object.
    *
    * @return the current timestamp of the "hvac_load" attribute
    */
    public double get_hvac_load_time() {
        return _hvac_load.getTime();
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

    protected Attribute< String > _power_state =
            new Attribute< String >(  new String( "" )  );

    /**
    * Set the value of the "power_state" attribute to "value" for this object.
    *
    * @param value the new value for the "power_state" attribute
    */
    public void set_power_state( String value ) {
        _power_state.setValue( value );
        _power_state.setTime( getTime() );
    }

    /**
    * Returns the value of the "power_state" attribute of this object.
    *
    * @return the value of the "power_state" attribute
    */
    public String get_power_state() {
        return _power_state.getValue();
    }

    /**
    * Returns the current timestamp of the "power_state" attribute of this object.
    *
    * @return the current timestamp of the "power_state" attribute
    */
    public double get_power_state_time() {
        return _power_state.getTime();
    }

    protected Attribute< Double > _thermostat_deadband =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "thermostat_deadband" attribute to "value" for this object.
    *
    * @param value the new value for the "thermostat_deadband" attribute
    */
    public void set_thermostat_deadband( double value ) {
        _thermostat_deadband.setValue( value );
        _thermostat_deadband.setTime( getTime() );
    }

    /**
    * Returns the value of the "thermostat_deadband" attribute of this object.
    *
    * @return the value of the "thermostat_deadband" attribute
    */
    public double get_thermostat_deadband() {
        return _thermostat_deadband.getValue();
    }

    /**
    * Returns the current timestamp of the "thermostat_deadband" attribute of this object.
    *
    * @return the current timestamp of the "thermostat_deadband" attribute
    */
    public double get_thermostat_deadband_time() {
        return _thermostat_deadband.getTime();
    }

    protected House( ReflectedAttributes datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    protected House( ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    /**
    * Creates an instance of the House object class, using
    * "datamemberMap" to initialize its attribute values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new House object class instance
    */
    public House( ReflectedAttributes datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #House( ReflectedAttributes datamemberMap )}, except this
    * new House object class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new House object class instance
    * @param logicalTime timestamp for this new House object class
    * instance
    */
    public House( ReflectedAttributes datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new House object class instance that is a duplicate
    * of the instance referred to by House_var.
    *
    * @param House_var House object class instance of which
    * this newly created House object class instance will be a
    * duplicate
    */
    public House( House House_var ) {
        super( House_var );

        set_air_temperature( House_var.get_air_temperature() );
        set_cooling_setpoint( House_var.get_cooling_setpoint() );
        set_heating_setpoint( House_var.get_heating_setpoint() );
        set_hvac_load( House_var.get_hvac_load() );
        set_name( House_var.get_name() );
        set_power_state( House_var.get_power_state() );
        set_thermostat_deadband( House_var.get_thermostat_deadband() );
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
        if ( "air_temperature".equals(datamemberName) ) return new Double(get_air_temperature());
        else if ( "cooling_setpoint".equals(datamemberName) ) return new Double(get_cooling_setpoint());
        else if ( "heating_setpoint".equals(datamemberName) ) return new Double(get_heating_setpoint());
        else if ( "hvac_load".equals(datamemberName) ) return new Double(get_hvac_load());
        else if ( "name".equals(datamemberName) ) return get_name();
        else if ( "power_state".equals(datamemberName) ) return get_power_state();
        else if ( "thermostat_deadband".equals(datamemberName) ) return new Double(get_thermostat_deadband());
        else return super.getAttribute( datamemberName );
    }

    protected boolean setAttributeAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "air_temperature".equals( datamemberName) ) set_air_temperature( Double.parseDouble(val) );
        else if ( "cooling_setpoint".equals( datamemberName) ) set_cooling_setpoint( Double.parseDouble(val) );
        else if ( "heating_setpoint".equals( datamemberName) ) set_heating_setpoint( Double.parseDouble(val) );
        else if ( "hvac_load".equals( datamemberName) ) set_hvac_load( Double.parseDouble(val) );
        else if ( "name".equals( datamemberName) ) set_name( val );
        else if ( "power_state".equals( datamemberName) ) set_power_state( val );
        else if ( "thermostat_deadband".equals( datamemberName) ) set_thermostat_deadband( Double.parseDouble(val) );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    protected boolean setAttributeAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "air_temperature".equals( datamemberName) ) set_air_temperature( (Double)val );
        else if ( "cooling_setpoint".equals( datamemberName) ) set_cooling_setpoint( (Double)val );
        else if ( "heating_setpoint".equals( datamemberName) ) set_heating_setpoint( (Double)val );
        else if ( "hvac_load".equals( datamemberName) ) set_hvac_load( (Double)val );
        else if ( "name".equals( datamemberName) ) set_name( (String)val );
        else if ( "power_state".equals( datamemberName) ) set_power_state( (String)val );
        else if ( "thermostat_deadband".equals( datamemberName) ) set_thermostat_deadband( (Double)val );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    @Override
    protected SuppliedAttributes createSuppliedDatamembers(boolean force) {
        SuppliedAttributes datamembers = _factory.createSuppliedAttributes();
 
        if (_publishAttributeNameSet.contains("air_temperature") && _air_temperature.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("air_temperature"), getAttribute("air_temperature").toString().getBytes() );
            _air_temperature.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("cooling_setpoint") && _cooling_setpoint.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("cooling_setpoint"), getAttribute("cooling_setpoint").toString().getBytes() );
            _cooling_setpoint.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("heating_setpoint") && _heating_setpoint.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("heating_setpoint"), getAttribute("heating_setpoint").toString().getBytes() );
            _heating_setpoint.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("hvac_load") && _hvac_load.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("hvac_load"), getAttribute("hvac_load").toString().getBytes() );
            _hvac_load.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("name") && _name.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("name"), getAttribute("name").toString().getBytes() );
            _name.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("power_state") && _power_state.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("power_state"), getAttribute("power_state").toString().getBytes() );
            _power_state.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("thermostat_deadband") && _thermostat_deadband.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("thermostat_deadband"), getAttribute("thermostat_deadband").toString().getBytes() );
            _thermostat_deadband.setHasBeenUpdated();
        }

        return datamembers;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof House ) {
            House data = (House)object;
            _air_temperature = data._air_temperature;
            _cooling_setpoint = data._cooling_setpoint;
            _heating_setpoint = data._heating_setpoint;
            _hvac_load = data._hvac_load;
            _name = data._name;
            _power_state = data._power_state;
            _thermostat_deadband = data._thermostat_deadband;
        }
    }
}

