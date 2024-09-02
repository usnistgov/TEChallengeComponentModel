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
* Implements ObjectRoot.Transformer
*/
public class Transformer extends ObjectRoot {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the Transformer object class with default attribute values.
    */
    public Transformer() {}

    private static int _name_handle;
    private static int _power_in_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the Transformer object class.
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
    * Returns the fully-qualified (dot-delimited) name of the Transformer object class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this object class
    */
    public static String get_class_name() {
        return "ObjectRoot.Transformer";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the Transformer object class.
    *
    * @return the name of this object class
    */
    public static String get_simple_class_name() {
        return "Transformer";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden attributes in the
    * Transformer object class.
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
    * Transformer object class.
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
        _classNameSet.add("ObjectRoot.Transformer");
        _classNameClassMap.put("ObjectRoot.Transformer", Transformer.class);

        _datamemberClassNameSetMap.put("ObjectRoot.Transformer", _datamemberNames);
        _allDatamemberClassNameSetMap.put("ObjectRoot.Transformer", _allDatamemberNames);

        _datamemberNames.add("name");
        _datamemberNames.add("power_in");

        _datamemberTypeMap.put("name", "String");
        _datamemberTypeMap.put("power_in", "String");

        _allDatamemberNames.add("name");
        _allDatamemberNames.add("power_in");

        _classNamePublishAttributeNameMap.put("ObjectRoot.Transformer", _publishAttributeNameSet);
        _classNameSubscribeAttributeNameMap.put("ObjectRoot.Transformer", _subscribeAttributeNameSet);
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        ObjectRoot.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getObjectClassHandle("ObjectRoot.Transformer");
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

        _classNameHandleMap.put("ObjectRoot.Transformer", get_handle());
        _classHandleNameMap.put(get_handle(), "ObjectRoot.Transformer");
        _classHandleSimpleNameMap.put(get_handle(), "Transformer");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _name_handle = rti.getAttributeHandle("name", get_handle());
                _power_in_handle = rti.getAttributeHandle("power_in", get_handle());
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

        _datamemberNameHandleMap.put("ObjectRoot.Transformer.name", _name_handle);
        _datamemberNameHandleMap.put("ObjectRoot.Transformer.power_in", _power_in_handle);

        _datamemberHandleNameMap.put(_name_handle, "name");
        _datamemberHandleNameMap.put(_power_in_handle, "power_in");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the Transformer object class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void publish(RTIambassador rti) {
        if (_isPublished) return;

        init(rti);

        AttributeHandleSet publishedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _publishAttributeNameSet) {
            try {
                publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Transformer." + attributeName));
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
    * Unpublishes the Transformer object class for a federate.
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
    * Subscribes a federate to the Transformer object class.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void subscribe(RTIambassador rti) {
        if (_isSubscribed) return;

        init(rti);

        AttributeHandleSet subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _subscribeAttributeNameSet) {
            try {
                subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Transformer." + attributeName));
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
    * Unsubscribes a federate from the Transformer object class.
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
    * (that is, the Transformer object class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the Transformer object class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the Transformer object class).
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
        if (datamemberHandle == _name_handle) return "name";
        else if (datamemberHandle == _power_in_handle) return "power_in";
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
                + "name:" + get_name()
                + "," + "power_in:" + get_power_in()
                + ")";
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
    * Publishes the "power_in" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "power_in" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_power_in() {
        _publishAttributeNameSet.add( "power_in" );
    }

    /**
    * Unpublishes the "power_in" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "power_in" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_power_in() {
        _publishAttributeNameSet.remove( "power_in" );
    }

    /**
    * Subscribes a federate to the "power_in" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "power_in" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_power_in() {
        _subscribeAttributeNameSet.add( "power_in" );
    }

    /**
    * Unsubscribes a federate from the "power_in" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "power_in" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_power_in() {
        _subscribeAttributeNameSet.remove( "power_in" );
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

    protected Attribute< String > _power_in =
            new Attribute< String >(  new String( "" )  );

    /**
    * Set the value of the "power_in" attribute to "value" for this object.
    *
    * @param value the new value for the "power_in" attribute
    */
    public void set_power_in( String value ) {
        _power_in.setValue( value );
        _power_in.setTime( getTime() );
    }

    /**
    * Returns the value of the "power_in" attribute of this object.
    *
    * @return the value of the "power_in" attribute
    */
    public String get_power_in() {
        return _power_in.getValue();
    }

    /**
    * Returns the current timestamp of the "power_in" attribute of this object.
    *
    * @return the current timestamp of the "power_in" attribute
    */
    public double get_power_in_time() {
        return _power_in.getTime();
    }

    protected Transformer( ReflectedAttributes datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    protected Transformer( ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    /**
    * Creates an instance of the Transformer object class, using
    * "datamemberMap" to initialize its attribute values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new Transformer object class instance
    */
    public Transformer( ReflectedAttributes datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #Transformer( ReflectedAttributes datamemberMap )}, except this
    * new Transformer object class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new Transformer object class instance
    * @param logicalTime timestamp for this new Transformer object class
    * instance
    */
    public Transformer( ReflectedAttributes datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new Transformer object class instance that is a duplicate
    * of the instance referred to by Transformer_var.
    *
    * @param Transformer_var Transformer object class instance of which
    * this newly created Transformer object class instance will be a
    * duplicate
    */
    public Transformer( Transformer Transformer_var ) {
        super( Transformer_var );

        set_name( Transformer_var.get_name() );
        set_power_in( Transformer_var.get_power_in() );
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
        if ( "name".equals(datamemberName) ) return get_name();
        else if ( "power_in".equals(datamemberName) ) return get_power_in();
        else return super.getAttribute( datamemberName );
    }

    protected boolean setAttributeAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "name".equals( datamemberName) ) set_name( val );
        else if ( "power_in".equals( datamemberName) ) set_power_in( val );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    protected boolean setAttributeAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "name".equals( datamemberName) ) set_name( (String)val );
        else if ( "power_in".equals( datamemberName) ) set_power_in( (String)val );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    @Override
    protected SuppliedAttributes createSuppliedDatamembers(boolean force) {
        SuppliedAttributes datamembers = _factory.createSuppliedAttributes();
 
        if (_publishAttributeNameSet.contains("name") && _name.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("name"), getAttribute("name").toString().getBytes() );
            _name.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("power_in") && _power_in.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("power_in"), getAttribute("power_in").toString().getBytes() );
            _power_in.setHasBeenUpdated();
        }

        return datamembers;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof Transformer ) {
            Transformer data = (Transformer)object;
            _name = data._name;
            _power_in = data._power_in;
        }
    }
}

