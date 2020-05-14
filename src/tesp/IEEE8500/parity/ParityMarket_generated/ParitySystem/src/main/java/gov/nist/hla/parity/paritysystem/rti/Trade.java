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
* Implements InteractionRoot.C2WInteractionRoot.ParityReporting.Trade
*/
public class Trade extends ParityReporting {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the Trade interaction class with default parameter values.
    */
    public Trade() {}

    private static int _actualLogicalGenerationTime_handle;
    private static int _federateFilter_handle;
    private static int _incomingOrderNumber_handle;
    private static int _matchNumber_handle;
    private static int _messageType_handle;
    private static int _originFed_handle;
    private static int _quantity_handle;
    private static int _restingOrderNumber_handle;
    private static int _sourceFed_handle;
    private static int _timeStamp_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the Trade interaction class.
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
    * Returns the fully-qualified (dot-delimited) name of the Trade interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this interaction class
    */
    public static String get_class_name() {
        return "InteractionRoot.C2WInteractionRoot.ParityReporting.Trade";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the Trade interaction class.
    *
    * @return the name of this interaction class
    */
    public static String get_simple_class_name() {
        return "Trade";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden parameters in the
    * Trade interaction class.
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
    * Trade interaction class.
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
        _classNameSet.add("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade");
        _classNameClassMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade", Trade.class);

        _datamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade", _datamemberNames);
        _allDatamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade", _allDatamemberNames);

        _datamemberNames.add("incomingOrderNumber");
        _datamemberNames.add("matchNumber");
        _datamemberNames.add("quantity");
        _datamemberNames.add("restingOrderNumber");
        _datamemberNames.add("timeStamp");

        _datamemberTypeMap.put("incomingOrderNumber", "long");
        _datamemberTypeMap.put("matchNumber", "int");
        _datamemberTypeMap.put("quantity", "long");
        _datamemberTypeMap.put("restingOrderNumber", "long");
        _datamemberTypeMap.put("timeStamp", "long");

        _allDatamemberNames.add("actualLogicalGenerationTime");
        _allDatamemberNames.add("federateFilter");
        _allDatamemberNames.add("incomingOrderNumber");
        _allDatamemberNames.add("matchNumber");
        _allDatamemberNames.add("messageType");
        _allDatamemberNames.add("originFed");
        _allDatamemberNames.add("quantity");
        _allDatamemberNames.add("restingOrderNumber");
        _allDatamemberNames.add("sourceFed");
        _allDatamemberNames.add("timeStamp");
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        ParityReporting.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getInteractionClassHandle("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade");
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

        _classNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade", get_handle());
        _classHandleNameMap.put(get_handle(), "InteractionRoot.C2WInteractionRoot.ParityReporting.Trade");
        _classHandleSimpleNameMap.put(get_handle(), "Trade");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _actualLogicalGenerationTime_handle = rti.getParameterHandle("actualLogicalGenerationTime", get_handle());
                _federateFilter_handle = rti.getParameterHandle("federateFilter", get_handle());
                _incomingOrderNumber_handle = rti.getParameterHandle("incomingOrderNumber", get_handle());
                _matchNumber_handle = rti.getParameterHandle("matchNumber", get_handle());
                _messageType_handle = rti.getParameterHandle("messageType", get_handle());
                _originFed_handle = rti.getParameterHandle("originFed", get_handle());
                _quantity_handle = rti.getParameterHandle("quantity", get_handle());
                _restingOrderNumber_handle = rti.getParameterHandle("restingOrderNumber", get_handle());
                _sourceFed_handle = rti.getParameterHandle("sourceFed", get_handle());
                _timeStamp_handle = rti.getParameterHandle("timeStamp", get_handle());
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

        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade.actualLogicalGenerationTime", _actualLogicalGenerationTime_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade.federateFilter", _federateFilter_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade.incomingOrderNumber", _incomingOrderNumber_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade.matchNumber", _matchNumber_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade.messageType", _messageType_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade.originFed", _originFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade.quantity", _quantity_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade.restingOrderNumber", _restingOrderNumber_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade.sourceFed", _sourceFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParityReporting.Trade.timeStamp", _timeStamp_handle);

        _datamemberHandleNameMap.put(_actualLogicalGenerationTime_handle, "actualLogicalGenerationTime");
        _datamemberHandleNameMap.put(_federateFilter_handle, "federateFilter");
        _datamemberHandleNameMap.put(_incomingOrderNumber_handle, "incomingOrderNumber");
        _datamemberHandleNameMap.put(_matchNumber_handle, "matchNumber");
        _datamemberHandleNameMap.put(_messageType_handle, "messageType");
        _datamemberHandleNameMap.put(_originFed_handle, "originFed");
        _datamemberHandleNameMap.put(_quantity_handle, "quantity");
        _datamemberHandleNameMap.put(_restingOrderNumber_handle, "restingOrderNumber");
        _datamemberHandleNameMap.put(_sourceFed_handle, "sourceFed");
        _datamemberHandleNameMap.put(_timeStamp_handle, "timeStamp");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the Trade interaction class for a federate.
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
    * Unpublishes the Trade interaction class for a federate.
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
    * Subscribes a federate to the Trade interaction class.
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
    * Unsubscribes a federate from the Trade interaction class.
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
    * (that is, the Trade interaction class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the Trade interaction class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the Trade interaction class).
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
        else if (datamemberHandle == _federateFilter_handle) return "federateFilter";
        else if (datamemberHandle == _incomingOrderNumber_handle) return "incomingOrderNumber";
        else if (datamemberHandle == _matchNumber_handle) return "matchNumber";
        else if (datamemberHandle == _messageType_handle) return "messageType";
        else if (datamemberHandle == _originFed_handle) return "originFed";
        else if (datamemberHandle == _quantity_handle) return "quantity";
        else if (datamemberHandle == _restingOrderNumber_handle) return "restingOrderNumber";
        else if (datamemberHandle == _sourceFed_handle) return "sourceFed";
        else if (datamemberHandle == _timeStamp_handle) return "timeStamp";
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
                + "," + "federateFilter:" + get_federateFilter()
                + "," + "incomingOrderNumber:" + get_incomingOrderNumber()
                + "," + "matchNumber:" + get_matchNumber()
                + "," + "messageType:" + get_messageType()
                + "," + "originFed:" + get_originFed()
                + "," + "quantity:" + get_quantity()
                + "," + "restingOrderNumber:" + get_restingOrderNumber()
                + "," + "sourceFed:" + get_sourceFed()
                + "," + "timeStamp:" + get_timeStamp()
                + ")";
    }

    private long _incomingOrderNumber = 0;
    private int _matchNumber = 0;
    private long _quantity = 0;
    private long _restingOrderNumber = 0;
    private long _timeStamp = 0;

    /**
    * Set the value of the "incomingOrderNumber" parameter to "value" for this parameter.
    *
    * @param value the new value for the "incomingOrderNumber" parameter
    */
    public void set_incomingOrderNumber( long value ) {
        _incomingOrderNumber = value;
    }

    /**
    * Returns the value of the "incomingOrderNumber" parameter of this interaction.
    *
    * @return the value of the "incomingOrderNumber" parameter
    */
    public long get_incomingOrderNumber() {
        return _incomingOrderNumber;
    }
    /**
    * Set the value of the "matchNumber" parameter to "value" for this parameter.
    *
    * @param value the new value for the "matchNumber" parameter
    */
    public void set_matchNumber( int value ) {
        _matchNumber = value;
    }

    /**
    * Returns the value of the "matchNumber" parameter of this interaction.
    *
    * @return the value of the "matchNumber" parameter
    */
    public int get_matchNumber() {
        return _matchNumber;
    }
    /**
    * Set the value of the "quantity" parameter to "value" for this parameter.
    *
    * @param value the new value for the "quantity" parameter
    */
    public void set_quantity( long value ) {
        _quantity = value;
    }

    /**
    * Returns the value of the "quantity" parameter of this interaction.
    *
    * @return the value of the "quantity" parameter
    */
    public long get_quantity() {
        return _quantity;
    }
    /**
    * Set the value of the "restingOrderNumber" parameter to "value" for this parameter.
    *
    * @param value the new value for the "restingOrderNumber" parameter
    */
    public void set_restingOrderNumber( long value ) {
        _restingOrderNumber = value;
    }

    /**
    * Returns the value of the "restingOrderNumber" parameter of this interaction.
    *
    * @return the value of the "restingOrderNumber" parameter
    */
    public long get_restingOrderNumber() {
        return _restingOrderNumber;
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

    protected Trade( ReceivedInteraction datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    protected Trade( ReceivedInteraction datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    /**
    * Creates an instance of the Trade interaction class, using
    * "datamemberMap" to initialize its parameter values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new Trade interaction class instance
    */
    public Trade( ReceivedInteraction datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #Trade( ReceivedInteraction datamemberMap )}, except this
    * new Trade interaction class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new Trade interaction class instance
    * @param logicalTime timestamp for this new Trade interaction class
    * instance
    */
    public Trade( ReceivedInteraction datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new Trade interaction class instance that is a duplicate
    * of the instance referred to by Trade_var.
    *
    * @param Trade_var Trade interaction class instance of which
    * this newly created Trade interaction class instance will be a
    * duplicate
    */
    public Trade( Trade Trade_var ) {
        super( Trade_var );

        set_incomingOrderNumber( Trade_var.get_incomingOrderNumber() );
        set_matchNumber( Trade_var.get_matchNumber() );
        set_quantity( Trade_var.get_quantity() );
        set_restingOrderNumber( Trade_var.get_restingOrderNumber() );
        set_timeStamp( Trade_var.get_timeStamp() );
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
        if ( "incomingOrderNumber".equals(datamemberName) ) return new Long(get_incomingOrderNumber());
        else if ( "matchNumber".equals(datamemberName) ) return new Integer(get_matchNumber());
        else if ( "quantity".equals(datamemberName) ) return new Long(get_quantity());
        else if ( "restingOrderNumber".equals(datamemberName) ) return new Long(get_restingOrderNumber());
        else if ( "timeStamp".equals(datamemberName) ) return new Long(get_timeStamp());
        else return super.getParameter( datamemberName );
    }

    protected boolean setParameterAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "incomingOrderNumber".equals( datamemberName) ) set_incomingOrderNumber( Long.parseLong(val) );
        else if ( "matchNumber".equals( datamemberName) ) set_matchNumber( Integer.parseInt(val) );
        else if ( "quantity".equals( datamemberName) ) set_quantity( Long.parseLong(val) );
        else if ( "restingOrderNumber".equals( datamemberName) ) set_restingOrderNumber( Long.parseLong(val) );
        else if ( "timeStamp".equals( datamemberName) ) set_timeStamp( Long.parseLong(val) );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    protected boolean setParameterAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "incomingOrderNumber".equals( datamemberName) ) set_incomingOrderNumber( (Long)val );
        else if ( "matchNumber".equals( datamemberName) ) set_matchNumber( (Integer)val );
        else if ( "quantity".equals( datamemberName) ) set_quantity( (Long)val );
        else if ( "restingOrderNumber".equals( datamemberName) ) set_restingOrderNumber( (Long)val );
        else if ( "timeStamp".equals( datamemberName) ) set_timeStamp( (Long)val );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof Trade ) {
            Trade data = (Trade)object;
            _incomingOrderNumber = data._incomingOrderNumber;
            _matchNumber = data._matchNumber;
            _quantity = data._quantity;
            _restingOrderNumber = data._restingOrderNumber;
            _timeStamp = data._timeStamp;
        }
    }
}

