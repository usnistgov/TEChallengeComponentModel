package gov.nist.hla.parity.paritysystem.rti;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cpswt.utils.CpswtUtils;

import hla.rti.FederateNotExecutionMember;
import hla.rti.InteractionClassNotDefined;
import hla.rti.InteractionClassNotPublished;
import hla.rti.InteractionClassNotSubscribed;
import hla.rti.LogicalTime;
import hla.rti.NameNotFound;
import hla.rti.RTIambassador;
import hla.rti.ReceivedInteraction;

import org.cpswt.hla.*;

/**
* Implements InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled
*/
public class OrderCanceled extends ParitySystem {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the OrderCanceled interaction class with default parameter values.
    */
    public OrderCanceled() {}

    private static int _actualLogicalGenerationTime_handle;
    private static int _canceledQuantity_handle;
    private static int _federateFilter_handle;
    private static int _messageType_handle;
    private static int _orderId_handle;
    private static int _originFed_handle;
    private static int _reason_handle;
    private static int _sourceFed_handle;
    private static int _timeStamp_handle;
    private static int _userName_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the OrderCanceled interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the handle of the class pertaining to the reference,
    * rather than the handle of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassHandle()}.
    *
    * @return the RTI assigned integer handle that represents this interaction class
    */
    public static int get_handle() {
        return _handle;
    }

    /**
    * Returns the fully-qualified (dot-delimited) name of the OrderCanceled interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this interaction class
    */
    public static String get_class_name() {
        return "InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the OrderCanceled interaction class.
    *
    * @return the name of this interaction class
    */
    public static String get_simple_class_name() {
        return "OrderCanceled";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden parameters in the
    * OrderCanceled interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return a set of parameter names pertaining to the reference,
    * rather than the parameter names of the class for the instance referred to by
    * the reference.  For the polymorphic version of this method, use
    * {@link #getParameterNames()}.
    *
    * @return a modifiable set of the non-hidden parameter names for this interaction class
    */
    public static Set< String > get_parameter_names() {
        return new HashSet< String >(_datamemberNames);
    }

    /**
    * Returns a set containing the names of all of the parameters in the
    * OrderCanceled interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return a set of parameter names pertaining to the reference,
    * rather than the parameter names of the class for the instance referred to by
    * the reference.  For the polymorphic version of this method, use
    * {@link #getParameterNames()}.
    *
    * @return a modifiable set of the parameter names for this interaction class
    */
    public static Set< String > get_all_parameter_names() {
        return new HashSet< String >(_allDatamemberNames);
    }

    static {
        _classNameSet.add("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled");
        _classNameClassMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled", OrderCanceled.class);

        _datamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled", _datamemberNames);
        _allDatamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled", _allDatamemberNames);

        _datamemberNames.add("canceledQuantity");
        _datamemberNames.add("reason");
        _datamemberNames.add("timeStamp");

        _datamemberTypeMap.put("canceledQuantity", "long");
        _datamemberTypeMap.put("reason", "char");
        _datamemberTypeMap.put("timeStamp", "long");

        _allDatamemberNames.add("actualLogicalGenerationTime");
        _allDatamemberNames.add("canceledQuantity");
        _allDatamemberNames.add("federateFilter");
        _allDatamemberNames.add("messageType");
        _allDatamemberNames.add("orderId");
        _allDatamemberNames.add("originFed");
        _allDatamemberNames.add("reason");
        _allDatamemberNames.add("sourceFed");
        _allDatamemberNames.add("timeStamp");
        _allDatamemberNames.add("userName");
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        ParitySystem.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getInteractionClassHandle("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled");
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

        _classNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled", get_handle());
        _classHandleNameMap.put(get_handle(), "InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled");
        _classHandleSimpleNameMap.put(get_handle(), "OrderCanceled");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _actualLogicalGenerationTime_handle = rti.getParameterHandle("actualLogicalGenerationTime", get_handle());
                _canceledQuantity_handle = rti.getParameterHandle("canceledQuantity", get_handle());
                _federateFilter_handle = rti.getParameterHandle("federateFilter", get_handle());
                _messageType_handle = rti.getParameterHandle("messageType", get_handle());
                _orderId_handle = rti.getParameterHandle("orderId", get_handle());
                _originFed_handle = rti.getParameterHandle("originFed", get_handle());
                _reason_handle = rti.getParameterHandle("reason", get_handle());
                _sourceFed_handle = rti.getParameterHandle("sourceFed", get_handle());
                _timeStamp_handle = rti.getParameterHandle("timeStamp", get_handle());
                _userName_handle = rti.getParameterHandle("userName", get_handle());
                isNotInitialized = false;
            } catch (FederateNotExecutionMember e) {
                logger.error("could not initialize: Federate Not Execution Member", e);
                return;
            } catch (InteractionClassNotDefined e) {
                logger.error("could not initialize: Interaction Class Not Defined", e);
                return;
            } catch (NameNotFound e) {
                logger.error("could not initialize: Name Not Found", e);
                return;
            } catch (Exception e) {
                logger.error(e);
                CpswtUtils.sleepDefault();
            }
        }

        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled.actualLogicalGenerationTime", _actualLogicalGenerationTime_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled.canceledQuantity", _canceledQuantity_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled.federateFilter", _federateFilter_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled.messageType", _messageType_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled.orderId", _orderId_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled.originFed", _originFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled.reason", _reason_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled.sourceFed", _sourceFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled.timeStamp", _timeStamp_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.OrderCanceled.userName", _userName_handle);

        _datamemberHandleNameMap.put(_actualLogicalGenerationTime_handle, "actualLogicalGenerationTime");
        _datamemberHandleNameMap.put(_canceledQuantity_handle, "canceledQuantity");
        _datamemberHandleNameMap.put(_federateFilter_handle, "federateFilter");
        _datamemberHandleNameMap.put(_messageType_handle, "messageType");
        _datamemberHandleNameMap.put(_orderId_handle, "orderId");
        _datamemberHandleNameMap.put(_originFed_handle, "originFed");
        _datamemberHandleNameMap.put(_reason_handle, "reason");
        _datamemberHandleNameMap.put(_sourceFed_handle, "sourceFed");
        _datamemberHandleNameMap.put(_timeStamp_handle, "timeStamp");
        _datamemberHandleNameMap.put(_userName_handle, "userName");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the OrderCanceled interaction class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void publish(RTIambassador rti) {
        if (_isPublished) return;

        init(rti);

        synchronized(rti) {
            boolean isNotPublished = true;
            while(isNotPublished) {
                try {
                    rti.publishInteractionClass(get_handle());
                    isNotPublished = false;
                } catch (FederateNotExecutionMember e) {
                    logger.error("could not publish: Federate Not Execution Member", e);
                    return;
                } catch (InteractionClassNotDefined e) {
                    logger.error("could not publish: Interaction Class Not Defined", e);
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
    * Unpublishes the OrderCanceled interaction class for a federate.
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
                    rti.unpublishInteractionClass(get_handle());
                    isNotUnpublished = false;
                } catch (FederateNotExecutionMember e) {
                    logger.error("could not unpublish: Federate Not Execution Member", e);
                    return;
                } catch (InteractionClassNotDefined e) {
                    logger.error("could not unpublish: Interaction Class Not Defined", e);
                    return;
                } catch (InteractionClassNotPublished e) {
                    logger.error("could not unpublish: Interaction Class Not Published", e);
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
    * Subscribes a federate to the OrderCanceled interaction class.
    *
    * @param rti handle to the Local RTI Component
    */
    public static void subscribe(RTIambassador rti) {
        if (_isSubscribed) return;

        init(rti);

        synchronized(rti) {
            boolean isNotSubscribed = true;
            while(isNotSubscribed) {
                try {
                    rti.subscribeInteractionClass(get_handle());
                    isNotSubscribed = false;
                } catch (FederateNotExecutionMember e) {
                    logger.error("could not subscribe: Federate Not Execution Member", e);
                    return;
                } catch (InteractionClassNotDefined e) {
                    logger.error("could not subscribe: Interaction Class Not Defined", e);
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
    * Unsubscribes a federate from the OrderCanceled interaction class.
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
                    rti.unsubscribeInteractionClass(get_handle());
                    isNotUnsubscribed = false;
                } catch (FederateNotExecutionMember e) {
                    logger.error("could not unsubscribe: Federate Not Execution Member", e);
                    return;
                } catch (InteractionClassNotDefined e) {
                    logger.error("could not unsubscribe: Interaction Class Not Defined", e);
                    return;
                } catch (InteractionClassNotSubscribed e) {
                    logger.error("could not unsubscribe: Interaction Class Not Subscribed", e);
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
    * (that is, the OrderCanceled interaction class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the OrderCanceled interaction class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the OrderCanceled interaction class).
    */
    public static boolean match(int handle) {
        return handle == get_handle();
    }

    /**
    * Returns the handle (RTI assigned) of this instance's interaction class .
    *
    * @return the handle (RTI assigned) if this instance's interaction class
    */
    public int getClassHandle() {
        return get_handle();
    }

    /**
    * Returns the fully-qualified (dot-delimited) name of this instance's interaction class.
    *
    * @return the fully-qualified (dot-delimited) name of this instance's interaction class
    */
    public String getClassName() {
        return get_class_name();
    }

    /**
    * Returns the simple name (last name in its fully-qualified dot-delimited name)
    * of this instance's interaction class.
    *
    * @return the simple name of this instance's interaction class
    */
    public String getSimpleClassName() {
        return get_simple_class_name();
    }

    /**
    * Returns a set containing the names of all of the non-hiddenparameters of an
    * interaction class instance.
    *
    * @return set containing the names of all of the parameters of an
    * interaction class instance
    */
    public Set< String > getParameterNames() {
        return get_parameter_names();
    }

    /**
    * Returns a set containing the names of all of the parameters of an
    * interaction class instance.
    *
    * @return set containing the names of all of the parameters of an
    * interaction class instance
    */
    public Set< String > getAllParameterNames() {
        return get_all_parameter_names();
    }

    @Override
    public String getParameterName(int datamemberHandle) {
        if (datamemberHandle == _actualLogicalGenerationTime_handle) return "actualLogicalGenerationTime";
        else if (datamemberHandle == _canceledQuantity_handle) return "canceledQuantity";
        else if (datamemberHandle == _federateFilter_handle) return "federateFilter";
        else if (datamemberHandle == _messageType_handle) return "messageType";
        else if (datamemberHandle == _orderId_handle) return "orderId";
        else if (datamemberHandle == _originFed_handle) return "originFed";
        else if (datamemberHandle == _reason_handle) return "reason";
        else if (datamemberHandle == _sourceFed_handle) return "sourceFed";
        else if (datamemberHandle == _timeStamp_handle) return "timeStamp";
        else if (datamemberHandle == _userName_handle) return "userName";
        else return super.getParameterName(datamemberHandle);
    }

    /**
    * Publishes the interaction class of this instance of the class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public void publishInteraction(RTIambassador rti) {
        publish(rti);
    }

    /**
    * Unpublishes the interaction class of this instance of this class for a federate.
    *
    * @param rti handle to the Local RTI Component
    */
    public void unpublishInteraction(RTIambassador rti) {
        unpublish(rti);
    }

    /**
    * Subscribes a federate to the interaction class of this instance of this class.
    *
    * @param rti handle to the Local RTI Component
    */
    public void subscribeInteraction(RTIambassador rti) {
        subscribe(rti);
    }

    /**
    * Unsubscribes a federate from the interaction class of this instance of this class.
    *
    * @param rti handle to the Local RTI Component
    */
    public void unsubscribeInteraction(RTIambassador rti) {
        unsubscribe(rti);
    }

    @Override
    public String toString() {
        return getClass().getName() + "("
                + "actualLogicalGenerationTime:" + get_actualLogicalGenerationTime()
                + "," + "canceledQuantity:" + get_canceledQuantity()
                + "," + "federateFilter:" + get_federateFilter()
                + "," + "messageType:" + get_messageType()
                + "," + "orderId:" + get_orderId()
                + "," + "originFed:" + get_originFed()
                + "," + "reason:" + get_reason()
                + "," + "sourceFed:" + get_sourceFed()
                + "," + "timeStamp:" + get_timeStamp()
                + "," + "userName:" + get_userName()
                + ")";
    }

    private long _canceledQuantity = 0;
    private char _reason = '\0';
    private long _timeStamp = 0;

    /**
    * Set the value of the "canceledQuantity" parameter to "value" for this parameter.
    *
    * @param value the new value for the "canceledQuantity" parameter
    */
    public void set_canceledQuantity( long value ) {
        _canceledQuantity = value;
    }

    /**
    * Returns the value of the "canceledQuantity" parameter of this interaction.
    *
    * @return the value of the "canceledQuantity" parameter
    */
    public long get_canceledQuantity() {
        return _canceledQuantity;
    }
    /**
    * Set the value of the "reason" parameter to "value" for this parameter.
    *
    * @param value the new value for the "reason" parameter
    */
    public void set_reason( char value ) {
        _reason = value;
    }

    /**
    * Returns the value of the "reason" parameter of this interaction.
    *
    * @return the value of the "reason" parameter
    */
    public char get_reason() {
        return _reason;
    }
    /**
    * Set the value of the "timeStamp" parameter to "value" for this parameter.
    *
    * @param value the new value for the "timeStamp" parameter
    */
    public void set_timeStamp( long value ) {
        _timeStamp = value;
    }

    /**
    * Returns the value of the "timeStamp" parameter of this interaction.
    *
    * @return the value of the "timeStamp" parameter
    */
    public long get_timeStamp() {
        return _timeStamp;
    }

    protected OrderCanceled( ReceivedInteraction datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    protected OrderCanceled( ReceivedInteraction datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    /**
    * Creates an instance of the OrderCanceled interaction class, using
    * "datamemberMap" to initialize its parameter values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new OrderCanceled interaction class instance
    */
    public OrderCanceled( ReceivedInteraction datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #OrderCanceled( ReceivedInteraction datamemberMap )}, except this
    * new OrderCanceled interaction class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new OrderCanceled interaction class instance
    * @param logicalTime timestamp for this new OrderCanceled interaction class
    * instance
    */
    public OrderCanceled( ReceivedInteraction datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new OrderCanceled interaction class instance that is a duplicate
    * of the instance referred to by OrderCanceled_var.
    *
    * @param OrderCanceled_var OrderCanceled interaction class instance of which
    * this newly created OrderCanceled interaction class instance will be a
    * duplicate
    */
    public OrderCanceled( OrderCanceled OrderCanceled_var ) {
        super( OrderCanceled_var );

        set_canceledQuantity( OrderCanceled_var.get_canceledQuantity() );
        set_reason( OrderCanceled_var.get_reason() );
        set_timeStamp( OrderCanceled_var.get_timeStamp() );
    }

    /**
    * Returns the value of the parameter whose name is "datamemberName"
    * for this interaction.
    *
    * @param datamemberName name of parameter whose value is to be
    * returned
    * @return value of the parameter whose name is "datamemberName"
    * for this interaction
    */
    public Object getParameter( String datamemberName ) {
        if ( "canceledQuantity".equals(datamemberName) ) return new Long(get_canceledQuantity());
        else if ( "reason".equals(datamemberName) ) return new Character(get_reason());
        else if ( "timeStamp".equals(datamemberName) ) return new Long(get_timeStamp());
        else return super.getParameter( datamemberName );
    }

    protected boolean setParameterAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "canceledQuantity".equals( datamemberName) ) set_canceledQuantity( Long.parseLong(val) );
        else if ( "reason".equals( datamemberName) ) set_reason( val.charAt(0) );
        else if ( "timeStamp".equals( datamemberName) ) set_timeStamp( Long.parseLong(val) );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    protected boolean setParameterAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "canceledQuantity".equals( datamemberName) ) set_canceledQuantity( (Long)val );
        else if ( "reason".equals( datamemberName) ) set_reason( (Character)val );
        else if ( "timeStamp".equals( datamemberName) ) set_timeStamp( (Long)val );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof OrderCanceled ) {
            OrderCanceled data = (OrderCanceled)object;
            _canceledQuantity = data._canceledQuantity;
            _reason = data._reason;
            _timeStamp = data._timeStamp;
        }
    }
}

