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
* Implements ObjectRoot.Load
*/
public class Load extends ObjectRoot {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the Load object class with default attribute values.
    */
    public Load() {}

    private static int _constant_power_A_handle;
    private static int _constant_power_B_handle;
    private static int _constant_power_C_handle;
    private static int _name_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the Load object class.
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
    * Returns the fully-qualified (dot-delimited) name of the Load object class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this object class
    */
    public static String get_class_name() {
        return "ObjectRoot.Load";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the Load object class.
    *
    * @return the name of this object class
    */
    public static String get_simple_class_name() {
        return "Load";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden attributes in the
    * Load object class.
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
    * Load object class.
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
        _classNameSet.add("ObjectRoot.Load");
        _classNameClassMap.put("ObjectRoot.Load", Load.class);

        _datamemberClassNameSetMap.put("ObjectRoot.Load", _datamemberNames);
        _allDatamemberClassNameSetMap.put("ObjectRoot.Load", _allDatamemberNames);

        _datamemberNames.add("constant_power_A");
        _datamemberNames.add("constant_power_B");
        _datamemberNames.add("constant_power_C");
        _datamemberNames.add("name");

        _datamemberTypeMap.put("constant_power_A", "double");
        _datamemberTypeMap.put("constant_power_B", "double");
        _datamemberTypeMap.put("constant_power_C", "double");
        _datamemberTypeMap.put("name", "String");

        _allDatamemberNames.add("constant_power_A");
        _allDatamemberNames.add("constant_power_B");
        _allDatamemberNames.add("constant_power_C");
        _allDatamemberNames.add("name");

        _classNamePublishAttributeNameMap.put("ObjectRoot.Load", _publishAttributeNameSet);
        _classNameSubscribeAttributeNameMap.put("ObjectRoot.Load", _subscribeAttributeNameSet);
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        ObjectRoot.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getObjectClassHandle("ObjectRoot.Load");
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

        _classNameHandleMap.put("ObjectRoot.Load", get_handle());
        _classHandleNameMap.put(get_handle(), "ObjectRoot.Load");
        _classHandleSimpleNameMap.put(get_handle(), "Load");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _constant_power_A_handle = rti.getAttributeHandle("constant_power_A", get_handle());
                _constant_power_B_handle = rti.getAttributeHandle("constant_power_B", get_handle());
                _constant_power_C_handle = rti.getAttributeHandle("constant_power_C", get_handle());
                _name_handle = rti.getAttributeHandle("name", get_handle());
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

        _datamemberNameHandleMap.put("ObjectRoot.Load.constant_power_A", _constant_power_A_handle);
        _datamemberNameHandleMap.put("ObjectRoot.Load.constant_power_B", _constant_power_B_handle);
        _datamemberNameHandleMap.put("ObjectRoot.Load.constant_power_C", _constant_power_C_handle);
        _datamemberNameHandleMap.put("ObjectRoot.Load.name", _name_handle);

        _datamemberHandleNameMap.put(_constant_power_A_handle, "constant_power_A");
        _datamemberHandleNameMap.put(_constant_power_B_handle, "constant_power_B");
        _datamemberHandleNameMap.put(_constant_power_C_handle, "constant_power_C");
        _datamemberHandleNameMap.put(_name_handle, "name");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the Load object class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void publish(RTIambassador rti) {
        if (_isPublished) return;

        init(rti);

        AttributeHandleSet publishedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _publishAttributeNameSet) {
            try {
                publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Load." + attributeName));
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
    * Unpublishes the Load object class for a federate.
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
    * Subscribes a federate to the Load object class.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void subscribe(RTIambassador rti) {
        if (_isSubscribed) return;

        init(rti);

        AttributeHandleSet subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _subscribeAttributeNameSet) {
            try {
                subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Load." + attributeName));
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
    * Unsubscribes a federate from the Load object class.
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
    * (that is, the Load object class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the Load object class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the Load object class).
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
        if (datamemberHandle == _constant_power_A_handle) return "constant_power_A";
        else if (datamemberHandle == _constant_power_B_handle) return "constant_power_B";
        else if (datamemberHandle == _constant_power_C_handle) return "constant_power_C";
        else if (datamemberHandle == _name_handle) return "name";
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
                + "constant_power_A:" + get_constant_power_A()
                + "," + "constant_power_B:" + get_constant_power_B()
                + "," + "constant_power_C:" + get_constant_power_C()
                + "," + "name:" + get_name()
                + ")";
    }


    /**
    * Publishes the "constant_power_A" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "constant_power_A" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_constant_power_A() {
        _publishAttributeNameSet.add( "constant_power_A" );
    }

    /**
    * Unpublishes the "constant_power_A" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "constant_power_A" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_constant_power_A() {
        _publishAttributeNameSet.remove( "constant_power_A" );
    }

    /**
    * Subscribes a federate to the "constant_power_A" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "constant_power_A" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_constant_power_A() {
        _subscribeAttributeNameSet.add( "constant_power_A" );
    }

    /**
    * Unsubscribes a federate from the "constant_power_A" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "constant_power_A" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_constant_power_A() {
        _subscribeAttributeNameSet.remove( "constant_power_A" );
    }

    /**
    * Publishes the "constant_power_B" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "constant_power_B" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_constant_power_B() {
        _publishAttributeNameSet.add( "constant_power_B" );
    }

    /**
    * Unpublishes the "constant_power_B" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "constant_power_B" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_constant_power_B() {
        _publishAttributeNameSet.remove( "constant_power_B" );
    }

    /**
    * Subscribes a federate to the "constant_power_B" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "constant_power_B" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_constant_power_B() {
        _subscribeAttributeNameSet.add( "constant_power_B" );
    }

    /**
    * Unsubscribes a federate from the "constant_power_B" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "constant_power_B" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_constant_power_B() {
        _subscribeAttributeNameSet.remove( "constant_power_B" );
    }

    /**
    * Publishes the "constant_power_C" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "constant_power_C" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_constant_power_C() {
        _publishAttributeNameSet.add( "constant_power_C" );
    }

    /**
    * Unpublishes the "constant_power_C" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "constant_power_C" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_constant_power_C() {
        _publishAttributeNameSet.remove( "constant_power_C" );
    }

    /**
    * Subscribes a federate to the "constant_power_C" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "constant_power_C" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_constant_power_C() {
        _subscribeAttributeNameSet.add( "constant_power_C" );
    }

    /**
    * Unsubscribes a federate from the "constant_power_C" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "constant_power_C" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_constant_power_C() {
        _subscribeAttributeNameSet.remove( "constant_power_C" );
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

    protected Attribute< Double > _constant_power_A =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "constant_power_A" attribute to "value" for this object.
    *
    * @param value the new value for the "constant_power_A" attribute
    */
    public void set_constant_power_A( double value ) {
        _constant_power_A.setValue( value );
        _constant_power_A.setTime( getTime() );
    }

    /**
    * Returns the value of the "constant_power_A" attribute of this object.
    *
    * @return the value of the "constant_power_A" attribute
    */
    public double get_constant_power_A() {
        return _constant_power_A.getValue();
    }

    /**
    * Returns the current timestamp of the "constant_power_A" attribute of this object.
    *
    * @return the current timestamp of the "constant_power_A" attribute
    */
    public double get_constant_power_A_time() {
        return _constant_power_A.getTime();
    }

    protected Attribute< Double > _constant_power_B =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "constant_power_B" attribute to "value" for this object.
    *
    * @param value the new value for the "constant_power_B" attribute
    */
    public void set_constant_power_B( double value ) {
        _constant_power_B.setValue( value );
        _constant_power_B.setTime( getTime() );
    }

    /**
    * Returns the value of the "constant_power_B" attribute of this object.
    *
    * @return the value of the "constant_power_B" attribute
    */
    public double get_constant_power_B() {
        return _constant_power_B.getValue();
    }

    /**
    * Returns the current timestamp of the "constant_power_B" attribute of this object.
    *
    * @return the current timestamp of the "constant_power_B" attribute
    */
    public double get_constant_power_B_time() {
        return _constant_power_B.getTime();
    }

    protected Attribute< Double > _constant_power_C =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "constant_power_C" attribute to "value" for this object.
    *
    * @param value the new value for the "constant_power_C" attribute
    */
    public void set_constant_power_C( double value ) {
        _constant_power_C.setValue( value );
        _constant_power_C.setTime( getTime() );
    }

    /**
    * Returns the value of the "constant_power_C" attribute of this object.
    *
    * @return the value of the "constant_power_C" attribute
    */
    public double get_constant_power_C() {
        return _constant_power_C.getValue();
    }

    /**
    * Returns the current timestamp of the "constant_power_C" attribute of this object.
    *
    * @return the current timestamp of the "constant_power_C" attribute
    */
    public double get_constant_power_C_time() {
        return _constant_power_C.getTime();
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

    protected Load( ReflectedAttributes datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    protected Load( ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    /**
    * Creates an instance of the Load object class, using
    * "datamemberMap" to initialize its attribute values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new Load object class instance
    */
    public Load( ReflectedAttributes datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #Load( ReflectedAttributes datamemberMap )}, except this
    * new Load object class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new Load object class instance
    * @param logicalTime timestamp for this new Load object class
    * instance
    */
    public Load( ReflectedAttributes datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new Load object class instance that is a duplicate
    * of the instance referred to by Load_var.
    *
    * @param Load_var Load object class instance of which
    * this newly created Load object class instance will be a
    * duplicate
    */
    public Load( Load Load_var ) {
        super( Load_var );

        set_constant_power_A( Load_var.get_constant_power_A() );
        set_constant_power_B( Load_var.get_constant_power_B() );
        set_constant_power_C( Load_var.get_constant_power_C() );
        set_name( Load_var.get_name() );
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
        if ( "constant_power_A".equals(datamemberName) ) return new Double(get_constant_power_A());
        else if ( "constant_power_B".equals(datamemberName) ) return new Double(get_constant_power_B());
        else if ( "constant_power_C".equals(datamemberName) ) return new Double(get_constant_power_C());
        else if ( "name".equals(datamemberName) ) return get_name();
        else return super.getAttribute( datamemberName );
    }

    protected boolean setAttributeAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "constant_power_A".equals( datamemberName) ) set_constant_power_A( Double.parseDouble(val) );
        else if ( "constant_power_B".equals( datamemberName) ) set_constant_power_B( Double.parseDouble(val) );
        else if ( "constant_power_C".equals( datamemberName) ) set_constant_power_C( Double.parseDouble(val) );
        else if ( "name".equals( datamemberName) ) set_name( val );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    protected boolean setAttributeAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "constant_power_A".equals( datamemberName) ) set_constant_power_A( (Double)val );
        else if ( "constant_power_B".equals( datamemberName) ) set_constant_power_B( (Double)val );
        else if ( "constant_power_C".equals( datamemberName) ) set_constant_power_C( (Double)val );
        else if ( "name".equals( datamemberName) ) set_name( (String)val );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    @Override
    protected SuppliedAttributes createSuppliedDatamembers(boolean force) {
        SuppliedAttributes datamembers = _factory.createSuppliedAttributes();
 
        if (_publishAttributeNameSet.contains("constant_power_A") && _constant_power_A.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("constant_power_A"), getAttribute("constant_power_A").toString().getBytes() );
            _constant_power_A.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("constant_power_B") && _constant_power_B.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("constant_power_B"), getAttribute("constant_power_B").toString().getBytes() );
            _constant_power_B.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("constant_power_C") && _constant_power_C.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("constant_power_C"), getAttribute("constant_power_C").toString().getBytes() );
            _constant_power_C.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("name") && _name.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("name"), getAttribute("name").toString().getBytes() );
            _name.setHasBeenUpdated();
        }

        return datamembers;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof Load ) {
            Load data = (Load)object;
            _constant_power_A = data._constant_power_A;
            _constant_power_B = data._constant_power_B;
            _constant_power_C = data._constant_power_C;
            _name = data._name;
        }
    }
}

