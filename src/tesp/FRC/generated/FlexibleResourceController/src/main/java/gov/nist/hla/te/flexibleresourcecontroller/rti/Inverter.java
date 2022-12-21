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
* Implements ObjectRoot.Inverter
*/
public class Inverter extends ObjectRoot {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the Inverter object class with default attribute values.
    */
    public Inverter() {}

    private static int _P_Out_handle;
    private static int _Q_Out_handle;
    private static int _name_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the Inverter object class.
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
    * Returns the fully-qualified (dot-delimited) name of the Inverter object class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this object class
    */
    public static String get_class_name() {
        return "ObjectRoot.Inverter";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the Inverter object class.
    *
    * @return the name of this object class
    */
    public static String get_simple_class_name() {
        return "Inverter";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden attributes in the
    * Inverter object class.
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
    * Inverter object class.
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
        _classNameSet.add("ObjectRoot.Inverter");
        _classNameClassMap.put("ObjectRoot.Inverter", Inverter.class);

        _datamemberClassNameSetMap.put("ObjectRoot.Inverter", _datamemberNames);
        _allDatamemberClassNameSetMap.put("ObjectRoot.Inverter", _allDatamemberNames);

        _datamemberNames.add("P_Out");
        _datamemberNames.add("Q_Out");
        _datamemberNames.add("name");

        _datamemberTypeMap.put("P_Out", "double");
        _datamemberTypeMap.put("Q_Out", "double");
        _datamemberTypeMap.put("name", "String");

        _allDatamemberNames.add("P_Out");
        _allDatamemberNames.add("Q_Out");
        _allDatamemberNames.add("name");

        _classNamePublishAttributeNameMap.put("ObjectRoot.Inverter", _publishAttributeNameSet);
        _classNameSubscribeAttributeNameMap.put("ObjectRoot.Inverter", _subscribeAttributeNameSet);
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        ObjectRoot.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getObjectClassHandle("ObjectRoot.Inverter");
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

        _classNameHandleMap.put("ObjectRoot.Inverter", get_handle());
        _classHandleNameMap.put(get_handle(), "ObjectRoot.Inverter");
        _classHandleSimpleNameMap.put(get_handle(), "Inverter");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _P_Out_handle = rti.getAttributeHandle("P_Out", get_handle());
                _Q_Out_handle = rti.getAttributeHandle("Q_Out", get_handle());
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

        _datamemberNameHandleMap.put("ObjectRoot.Inverter.P_Out", _P_Out_handle);
        _datamemberNameHandleMap.put("ObjectRoot.Inverter.Q_Out", _Q_Out_handle);
        _datamemberNameHandleMap.put("ObjectRoot.Inverter.name", _name_handle);

        _datamemberHandleNameMap.put(_P_Out_handle, "P_Out");
        _datamemberHandleNameMap.put(_Q_Out_handle, "Q_Out");
        _datamemberHandleNameMap.put(_name_handle, "name");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the Inverter object class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void publish(RTIambassador rti) {
        if (_isPublished) return;

        init(rti);

        AttributeHandleSet publishedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _publishAttributeNameSet) {
            try {
                publishedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Inverter." + attributeName));
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
    * Unpublishes the Inverter object class for a federate.
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
    * Subscribes a federate to the Inverter object class.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void subscribe(RTIambassador rti) {
        if (_isSubscribed) return;

        init(rti);

        AttributeHandleSet subscribedAttributeHandleSet = _factory.createAttributeHandleSet();
        for(String attributeName : _subscribeAttributeNameSet) {
            try {
                subscribedAttributeHandleSet.add(_datamemberNameHandleMap.get("ObjectRoot.Inverter." + attributeName));
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
    * Unsubscribes a federate from the Inverter object class.
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
    * (that is, the Inverter object class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the Inverter object class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the Inverter object class).
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
        if (datamemberHandle == _P_Out_handle) return "P_Out";
        else if (datamemberHandle == _Q_Out_handle) return "Q_Out";
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
                + "P_Out:" + get_P_Out()
                + "," + "Q_Out:" + get_Q_Out()
                + "," + "name:" + get_name()
                + ")";
    }


    /**
    * Publishes the "P_Out" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "P_Out" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_P_Out() {
        _publishAttributeNameSet.add( "P_Out" );
    }

    /**
    * Unpublishes the "P_Out" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "P_Out" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_P_Out() {
        _publishAttributeNameSet.remove( "P_Out" );
    }

    /**
    * Subscribes a federate to the "P_Out" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "P_Out" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_P_Out() {
        _subscribeAttributeNameSet.add( "P_Out" );
    }

    /**
    * Unsubscribes a federate from the "P_Out" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "P_Out" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_P_Out() {
        _subscribeAttributeNameSet.remove( "P_Out" );
    }

    /**
    * Publishes the "Q_Out" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "Q_Out" attribute for publication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void publish_Q_Out() {
        _publishAttributeNameSet.add( "Q_Out" );
    }

    /**
    * Unpublishes the "Q_Out" attribute of the attribute's containing object
    * class for a federate.
    * Note:  This method only marks the "Q_Out" attribute for unpublication.
    * To actually publish the attribute, the federate must (re)publish its containing
    * object class.
    * (using &lt;objectClassName&gt;.publish( RTIambassador rti ) ).
    */
    public static void unpublish_Q_Out() {
        _publishAttributeNameSet.remove( "Q_Out" );
    }

    /**
    * Subscribes a federate to the "Q_Out" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "Q_Out" attribute for subscription.
    * To actually subscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void subscribe_Q_Out() {
        _subscribeAttributeNameSet.add( "Q_Out" );
    }

    /**
    * Unsubscribes a federate from the "Q_Out" attribute of the attribute's
    * containing object class.
    * Note:  This method only marks the "Q_Out" attribute for unsubscription.
    * To actually unsubscribe to the attribute, the federate must (re)subscribe to its
    * containing object class.
    * (using &lt;objectClassName&gt;.subscribe( RTIambassador rti ) ).
    */
    public static void unsubscribe_Q_Out() {
        _subscribeAttributeNameSet.remove( "Q_Out" );
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

    protected Attribute< Double > _P_Out =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "P_Out" attribute to "value" for this object.
    *
    * @param value the new value for the "P_Out" attribute
    */
    public void set_P_Out( double value ) {
        _P_Out.setValue( value );
        _P_Out.setTime( getTime() );
    }

    /**
    * Returns the value of the "P_Out" attribute of this object.
    *
    * @return the value of the "P_Out" attribute
    */
    public double get_P_Out() {
        return _P_Out.getValue();
    }

    /**
    * Returns the current timestamp of the "P_Out" attribute of this object.
    *
    * @return the current timestamp of the "P_Out" attribute
    */
    public double get_P_Out_time() {
        return _P_Out.getTime();
    }

    protected Attribute< Double > _Q_Out =
            new Attribute< Double >(  new Double( 0 )  );

    /**
    * Set the value of the "Q_Out" attribute to "value" for this object.
    *
    * @param value the new value for the "Q_Out" attribute
    */
    public void set_Q_Out( double value ) {
        _Q_Out.setValue( value );
        _Q_Out.setTime( getTime() );
    }

    /**
    * Returns the value of the "Q_Out" attribute of this object.
    *
    * @return the value of the "Q_Out" attribute
    */
    public double get_Q_Out() {
        return _Q_Out.getValue();
    }

    /**
    * Returns the current timestamp of the "Q_Out" attribute of this object.
    *
    * @return the current timestamp of the "Q_Out" attribute
    */
    public double get_Q_Out_time() {
        return _Q_Out.getTime();
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

    protected Inverter( ReflectedAttributes datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    protected Inverter( ReflectedAttributes datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setAttributes( datamemberMap );
    }

    /**
    * Creates an instance of the Inverter object class, using
    * "datamemberMap" to initialize its attribute values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new Inverter object class instance
    */
    public Inverter( ReflectedAttributes datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #Inverter( ReflectedAttributes datamemberMap )}, except this
    * new Inverter object class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * attributes of this new Inverter object class instance
    * @param logicalTime timestamp for this new Inverter object class
    * instance
    */
    public Inverter( ReflectedAttributes datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new Inverter object class instance that is a duplicate
    * of the instance referred to by Inverter_var.
    *
    * @param Inverter_var Inverter object class instance of which
    * this newly created Inverter object class instance will be a
    * duplicate
    */
    public Inverter( Inverter Inverter_var ) {
        super( Inverter_var );

        set_P_Out( Inverter_var.get_P_Out() );
        set_Q_Out( Inverter_var.get_Q_Out() );
        set_name( Inverter_var.get_name() );
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
        if ( "P_Out".equals(datamemberName) ) return new Double(get_P_Out());
        else if ( "Q_Out".equals(datamemberName) ) return new Double(get_Q_Out());
        else if ( "name".equals(datamemberName) ) return get_name();
        else return super.getAttribute( datamemberName );
    }

    protected boolean setAttributeAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "P_Out".equals( datamemberName) ) set_P_Out( Double.parseDouble(val) );
        else if ( "Q_Out".equals( datamemberName) ) set_Q_Out( Double.parseDouble(val) );
        else if ( "name".equals( datamemberName) ) set_name( val );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    protected boolean setAttributeAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "P_Out".equals( datamemberName) ) set_P_Out( (Double)val );
        else if ( "Q_Out".equals( datamemberName) ) set_Q_Out( (Double)val );
        else if ( "name".equals( datamemberName) ) set_name( (String)val );
        else retval = super.setAttributeAux( datamemberName, val );

        return retval;
    }

    @Override
    protected SuppliedAttributes createSuppliedDatamembers(boolean force) {
        SuppliedAttributes datamembers = _factory.createSuppliedAttributes();
 
        if (_publishAttributeNameSet.contains("P_Out") && _P_Out.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("P_Out"), getAttribute("P_Out").toString().getBytes() );
            _P_Out.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("Q_Out") && _Q_Out.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("Q_Out"), getAttribute("Q_Out").toString().getBytes() );
            _Q_Out.setHasBeenUpdated();
        }

        if (_publishAttributeNameSet.contains("name") && _name.shouldBeUpdated(force)) {
            datamembers.add( getAttributeHandle("name"), getAttribute("name").toString().getBytes() );
            _name.setHasBeenUpdated();
        }

        return datamembers;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof Inverter ) {
            Inverter data = (Inverter)object;
            _P_Out = data._P_Out;
            _Q_Out = data._Q_Out;
            _name = data._name;
        }
    }
}

