package gov.nist.hla.te.pypower.rti;

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
* Implements ObjectRoot.PhysicalStatus
*/
public class PhysicalStatus extends ObjectRoot {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the PhysicalStatus object class with default attribute values.
    */
    public PhysicalStatus() {}

    private static int _responsive_c1_handle;
    private static int _responsive_c2_handle;
    private static int _responsive_deg_handle;
    private static int _responsive_max_mw_handle;
    private static int _unresponsive_mw_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the PhysicalStatus object class.
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
    * Returns the fully-qualified (dot-delimited) name of the PhysicalStatus object class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this object class
    */
    public static String get_class_name() {
        return "ObjectRoot.PhysicalStatus";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the PhysicalStatus object class.
    *
    * @return the name of this object class
    */
    public static String get_simple_class_name() {
        return "PhysicalStatus";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden attributes in the
    * PhysicalStatus object class.
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
    * PhysicalStatus object class.
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
        _classNameSet.add("ObjectRoot.PhysicalStatus");
        _classNameClassMap.put("ObjectRoot.PhysicalStatus", PhysicalStatus.class);

        _datamemberClassNameSetMap.put("ObjectRoot.PhysicalStatus", _datamemberNames);
        _allDatamemberClassNameSetMap.put("ObjectRoot.PhysicalStatus", _allDatamemberNames);

        _datamemberNames.add("responsive_c1");
        _datamemberNames.add("responsive_c2");
        _datamemberNames.add("responsive_deg");
        _datamemberNames.add("responsive_max_mw");
        _datamemberNames.add("unresponsive_mw");

        _datamemberTypeMap.put("responsive_c1", "double");
        _datamemberTypeMap.put("responsive_c2", "double");
        _datamemberTypeMap.put("responsive_deg", "int");
        _datamemberTypeMap.put("responsive_max_mw", "double");
        _datamemberTypeMap.put("unresponsive_mw", "double");

        _allDatamemberNames.add("responsive_c1");
        _allDatamemberNames.add("responsive_c2");
        _allDatamemberNames.add("responsive_deg");
        _allDatamemberNames.add("responsive_max_mw");
        _allDatamemberNames.add("unresponsive_mw");

        _classNamePublishAttributeNameMap.put("ObjectRoot.PhysicalStatus", _publishAttributeNameSet);
        _classNameSubscribeAttributeNameMap.put("ObjectRoot.PhysicalStatus", _subscribeAttributeNameSet);
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        ObjectRoot.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getObjectClassHandle("ObjectRoot.PhysicalStatus");
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

        _classNameHandleMap.put("ObjectRoot.PhysicalStatus", get_handle());
        _classHandleNameMap.put(get_handle(), "ObjectRoot.PhysicalStatus");
        _classHandleSimpleNameMap.put(get_handle(), "PhysicalStatus");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _responsive_c1_handle = rti.getAttributeHandle("responsive_c1", get_handle());
                _responsive_c2_handle = rti.getAttributeHandle("responsive_c2", get_handle());
                _responsive_deg_handle = rti.getAttributeHandle("responsive_deg", get_handle());
                _responsive_max_mw_handle = rti.getAttributeHandle("responsive_max_mw", get_handle());
                _unresponsive_mw_handle = rti.getAttributeHandle("unresponsive_mw", get_handle());
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

        _datamemberNameHandleMap.put("ObjectRoot.PhysicalStatus.responsive_c1", _responsive_c1_handle);
        _datamemberNameHandleMap.put("ObjectRoot.PhysicalStatus.responsive_c2", _responsive_c2_handle);
        _datamemberNameHandleMap.put("ObjectRoot.PhysicalStatus.responsive_deg", _responsive_deg_handle);
        _datamemberNameHandleMap.put("ObjectRoot.PhysicalStatus.responsive_max_mw", _responsive_max_mw_handle);
        _datamemberNameHandleMap.put("ObjectRoot.PhysicalStatus.unresponsive_mw", _unresponsive_mw_handle);

        _datamemberHandleNameMap.put(_responsive_c1_handle, "responsive_c1");
        _datamemberHandleNameMap.put(_responsive_c2_handle, "responsive_c2");
        _datamemberHandleNameMap.put(_responsive_deg_handle, "responsive_deg");
        _datamemberHandleNameMap.put(_responsive_max_mw_handle, "responsive_max_mw");
        _datamemberHandleNameMap.put(_unresponsive_mw_handle, "unresponsive_mw");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the PhysicalStatus object class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void publish(RTIambassador rti) {
        if (_isPublished) return;

        init(rti);

        AttributeHandleSet publishedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _publishAttributeNameSet) {
            try {
                publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.PhysicalStatus." + attributeName));
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
    * Unpublishes the PhysicalStatus object class for a federate.
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
    * Subscribes a federate to the PhysicalStatus object class.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void subscribe(RTIambassador rti) {
        if (_isSubscribed) return;

        init(rti);

        AttributeHandleSet subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _subscribeAttributeNameSet) {
            try {
                subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.PhysicalStatus." + attributeName));
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
    * Unsubscribes a federate from the PhysicalStatus object class.
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
    * (that is, the PhysicalStatus object class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the PhysicalStatus object class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the PhysicalStatus object class).
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
        if (datamemberHandle == _responsive_c1_handle) return "responsive_c1";
        else if (datamemberHandle == _responsive_c2_handle) return "responsive_c2";
        else if (datamemberHandle == _responsive_deg_handle) return "responsive_deg";
        else if (datamemberHandle == _responsive_max_mw_handle) return "responsive_max_mw";
        else if (datamemberHandle == _unresponsive_mw_handle) return "unresponsive_mw";
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
                + "responsive_c1:" + get_responsive_c1()
                + "," + "responsive_c2:" + get_responsive_c2()
                + "," + "responsive_deg:" + get_responsive_deg()
                + "," + "responsive_max_mw:" + get_responsive_max_mw()
                + "," + "unresponsive_mw:" + get_unresponsive_mw()
                + ")";
    }


    /**
    * Publishes the "responsive_c1" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "responsive_c1" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_responsive_c1() {
        _publishAttributeNameSet.add( "responsive_c1" );
    }

    /**
    * Unpublishes the "responsive_c1" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "responsive_c1" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_responsive_c1() {
        _publishAttributeNameSet.remove( "responsive_c1" );
    }

    /**
    * Subscribes a federate to the "responsive_c1" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "responsive_c1" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_responsive_c1() {
        _subscribeAttributeNameSet.add( "responsive_c1" );
    }

    /**
    * Unsubscribes a federate from the "responsive_c1" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "responsive_c1" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_responsive_c1() {
        _subscribeAttributeNameSet.remove( "responsive_c1" );
    }

    /**
    * Publishes the "responsive_c2" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "responsive_c2" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_responsive_c2() {
        _publishAttributeNameSet.add( "responsive_c2" );
    }

    /**
    * Unpublishes the "responsive_c2" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "responsive_c2" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_responsive_c2() {
        _publishAttributeNameSet.remove( "responsive_c2" );
    }

    /**
    * Subscribes a federate to the "responsive_c2" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "responsive_c2" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_responsive_c2() {
        _subscribeAttributeNameSet.add( "responsive_c2" );
    }

    /**
    * Unsubscribes a federate from the "responsive_c2" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "responsive_c2" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_responsive_c2() {
        _subscribeAttributeNameSet.remove( "responsive_c2" );
    }

    /**
    * Publishes the "responsive_deg" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "responsive_deg" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_responsive_deg() {
        _publishAttributeNameSet.add( "responsive_deg" );
    }

    /**
    * Unpublishes the "responsive_deg" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "responsive_deg" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_responsive_deg() {
        _publishAttributeNameSet.remove( "responsive_deg" );
    }

    /**
    * Subscribes a federate to the "responsive_deg" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "responsive_deg" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_responsive_deg() {
        _subscribeAttributeNameSet.add( "responsive_deg" );
    }

    /**
    * Unsubscribes a federate from the "responsive_deg" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "responsive_deg" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_responsive_deg() {
        _subscribeAttributeNameSet.remove( "responsive_deg" );
    }

    /**
    * Publishes the "responsive_max_mw" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "responsive_max_mw" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_responsive_max_mw() {
        _publishAttributeNameSet.add( "responsive_max_mw" );
    }

    /**
    * Unpublishes the "responsive_max_mw" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "responsive_max_mw" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_responsive_max_mw() {
        _publishAttributeNameSet.remove( "responsive_max_mw" );
    }

    /**
    * Subscribes a federate to the "responsive_max_mw" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "responsive_max_mw" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_responsive_max_mw() {
        _subscribeAttributeNameSet.add( "responsive_max_mw" );
    }

    /**
    * Unsubscribes a federate from the "responsive_max_mw" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "responsive_max_mw" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_responsive_max_mw() {
        _subscribeAttributeNameSet.remove( "responsive_max_mw" );
    }

    /**
    * Publishes the "unresponsive_mw" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "unresponsive_mw" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_unresponsive_mw() {
        _publishAttributeNameSet.add( "unresponsive_mw" );
    }

    /**
    * Unpublishes the "unresponsive_mw" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "unresponsive_mw" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_unresponsive_mw() {
        _publishAttributeNameSet.remove( "unresponsive_mw" );
    }

    /**
    * Subscribes a federate to the "unresponsive_mw" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "unresponsive_mw" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_unresponsive_mw() {
        _subscribeAttributeNameSet.add( "unresponsive_mw" );
    }

    /**
    * Unsubscribes a federate from the "unresponsive_mw" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "unresponsive_mw" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_unresponsive_mw() {
        _subscribeAttributeNameSet.remove( "unresponsive_mw" );
    }

    protected Attribute< Double > _responsive_c1 =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "responsive_c1" attribute to "value" for this object.
    *
    * @param value the new value for the "responsive_c1" attribute
    */
    public void set_responsive_c1( double value ) {
        _responsive_c1.setValue( value );
        _responsive_c1.setTime( getTime() );
    }

    /**
    * Returns the value of the "responsive_c1" attribute of this object.
    *
    * @return the value of the "responsive_c1" attribute
    */
    public double get_responsive_c1() {
        return _responsive_c1.getValue();
    }

    /**
    * Returns the current timestamp of the "responsive_c1" attribute of this object.
    *
    * @return the current timestamp of the "responsive_c1" attribute
    */
    public double get_responsive_c1_time() {
        return _responsive_c1.getTime();
    }

    protected Attribute< Double > _responsive_c2 =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "responsive_c2" attribute to "value" for this object.
    *
    * @param value the new value for the "responsive_c2" attribute
    */
    public void set_responsive_c2( double value ) {
        _responsive_c2.setValue( value );
        _responsive_c2.setTime( getTime() );
    }

    /**
    * Returns the value of the "responsive_c2" attribute of this object.
    *
    * @return the value of the "responsive_c2" attribute
    */
    public double get_responsive_c2() {
        return _responsive_c2.getValue();
    }

    /**
    * Returns the current timestamp of the "responsive_c2" attribute of this object.
    *
    * @return the current timestamp of the "responsive_c2" attribute
    */
    public double get_responsive_c2_time() {
        return _responsive_c2.getTime();
    }

    protected Attribute< Integer > _responsive_deg =
            new Attribute< Integer >(  new Integer( 0 )  );

    /**
    * Set the value of the "responsive_deg" attribute to "value" for this object.
    *
    * @param value the new value for the "responsive_deg" attribute
    */
    public void set_responsive_deg( int value ) {
        _responsive_deg.setValue( value );
        _responsive_deg.setTime( getTime() );
    }

    /**
    * Returns the value of the "responsive_deg" attribute of this object.
    *
    * @return the value of the "responsive_deg" attribute
    */
    public int get_responsive_deg() {
        return _responsive_deg.getValue();
    }

    /**
    * Returns the current timestamp of the "responsive_deg" attribute of this object.
    *
    * @return the current timestamp of the "responsive_deg" attribute
    */
    public double get_responsive_deg_time() {
        return _responsive_deg.getTime();
    }

    protected Attribute< Double > _responsive_max_mw =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "responsive_max_mw" attribute to "value" for this object.
    *
    * @param value the new value for the "responsive_max_mw" attribute
    */
    public void set_responsive_max_mw( double value ) {
        _responsive_max_mw.setValue( value );
        _responsive_max_mw.setTime( getTime() );
    }

    /**
    * Returns the value of the "responsive_max_mw" attribute of this object.
    *
    * @return the value of the "responsive_max_mw" attribute
    */
    public double get_responsive_max_mw() {
        return _responsive_max_mw.getValue();
    }

    /**
    * Returns the current timestamp of the "responsive_max_mw" attribute of this object.
    *
    * @return the current timestamp of the "responsive_max_mw" attribute
    */
    public double get_responsive_max_mw_time() {
        return _responsive_max_mw.getTime();
    }

    protected Attribute< Double > _unresponsive_mw =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "unresponsive_mw" attribute to "value" for this object.
    *
    * @param value the new value for the "unresponsive_mw" attribute
    */
    public void set_unresponsive_mw( double value ) {
        _unresponsive_mw.setValue( value );
        _unresponsive_mw.setTime( getTime() );
    }

    /**
    * Returns the value of the "unresponsive_mw" attribute of this object.
    *
    * @return the value of the "unresponsive_mw" attribute
    */
    public double get_unresponsive_mw() {
        return _unresponsive_mw.getValue();
    }

    /**
    * Returns the current timestamp of the "unresponsive_mw" attribute of this object.
    *
    * @return the current timestamp of the "unresponsive_mw" attribute
    */
    public double get_unresponsive_mw_time() {
        return _unresponsive_mw.getTime();
    }

    protected PhysicalStatus( ReflectedAttributes datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    protected PhysicalStatus( ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    /**
    * Creates an instance of the PhysicalStatus object class, using
    * "datamemberMap" to initialize its attribute values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new PhysicalStatus object class instance
    */
    public PhysicalStatus( ReflectedAttributes datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #PhysicalStatus( ReflectedAttributes datamemberMap )}, except this
    * new PhysicalStatus object class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new PhysicalStatus object class instance
    * @param logicalTime timestamp for this new PhysicalStatus object class
    * instance
    */
    public PhysicalStatus( ReflectedAttributes datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new PhysicalStatus object class instance that is a duplicate
    * of the instance referred to by PhysicalStatus_var.
    *
    * @param PhysicalStatus_var PhysicalStatus object class instance of which
    * this newly created PhysicalStatus object class instance will be a
    * duplicate
    */
    public PhysicalStatus( PhysicalStatus PhysicalStatus_var ) {
        super( PhysicalStatus_var );

        set_responsive_c1( PhysicalStatus_var.get_responsive_c1() );
        set_responsive_c2( PhysicalStatus_var.get_responsive_c2() );
        set_responsive_deg( PhysicalStatus_var.get_responsive_deg() );
        set_responsive_max_mw( PhysicalStatus_var.get_responsive_max_mw() );
        set_unresponsive_mw( PhysicalStatus_var.get_unresponsive_mw() );
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
        if ( "responsive_c1".equals(datamemberName) ) return new Double(get_responsive_c1());
        else if ( "responsive_c2".equals(datamemberName) ) return new Double(get_responsive_c2());
        else if ( "responsive_deg".equals(datamemberName) ) return new Integer(get_responsive_deg());
        else if ( "responsive_max_mw".equals(datamemberName) ) return new Double(get_responsive_max_mw());
        else if ( "unresponsive_mw".equals(datamemberName) ) return new Double(get_unresponsive_mw());
        else return super.getAttribute( datamemberName );
    }

    protected boolean setAttributeAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "responsive_c1".equals( datamemberName) ) set_responsive_c1( Double.parseDouble(val) );
        else if ( "responsive_c2".equals( datamemberName) ) set_responsive_c2( Double.parseDouble(val) );
        else if ( "responsive_deg".equals( datamemberName) ) set_responsive_deg( Integer.parseInt(val) );
        else if ( "responsive_max_mw".equals( datamemberName) ) set_responsive_max_mw( Double.parseDouble(val) );
        else if ( "unresponsive_mw".equals( datamemberName) ) set_unresponsive_mw( Double.parseDouble(val) );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    protected boolean setAttributeAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "responsive_c1".equals( datamemberName) ) set_responsive_c1( (Double)val );
        else if ( "responsive_c2".equals( datamemberName) ) set_responsive_c2( (Double)val );
        else if ( "responsive_deg".equals( datamemberName) ) set_responsive_deg( (Integer)val );
        else if ( "responsive_max_mw".equals( datamemberName) ) set_responsive_max_mw( (Double)val );
        else if ( "unresponsive_mw".equals( datamemberName) ) set_unresponsive_mw( (Double)val );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    @Override
    protected SuppliedAttributes createSuppliedDatamembers(boolean force) {
        SuppliedAttributes datamembers = _factory.createSuppliedAttributes();
 
        if (_publishAttributeNameSet.contains("responsive_c1") && _responsive_c1.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("responsive_c1"), getAttribute("responsive_c1").toString().getBytes() );
            _responsive_c1.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("responsive_c2") && _responsive_c2.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("responsive_c2"), getAttribute("responsive_c2").toString().getBytes() );
            _responsive_c2.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("responsive_deg") && _responsive_deg.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("responsive_deg"), getAttribute("responsive_deg").toString().getBytes() );
            _responsive_deg.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("responsive_max_mw") && _responsive_max_mw.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("responsive_max_mw"), getAttribute("responsive_max_mw").toString().getBytes() );
            _responsive_max_mw.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("unresponsive_mw") && _unresponsive_mw.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("unresponsive_mw"), getAttribute("unresponsive_mw").toString().getBytes() );
            _unresponsive_mw.setHasBeenUpdated();
        }

        return datamembers;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof PhysicalStatus ) {
            PhysicalStatus data = (PhysicalStatus)object;
            _responsive_c1 = data._responsive_c1;
            _responsive_c2 = data._responsive_c2;
            _responsive_deg = data._responsive_deg;
            _responsive_max_mw = data._responsive_max_mw;
            _unresponsive_mw = data._unresponsive_mw;
        }
    }
}

