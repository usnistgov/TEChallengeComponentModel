package gov.nist.hla.te.marketagent.rti;

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
* Implements InteractionRoot.C2WInteractionRoot.MarketClosed
*/
public class MarketClosed extends C2WInteractionRoot {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the MarketClosed interaction class with default parameter values.
    */
    public MarketClosed() {}

    private static int _actualLogicalGenerationTime_handle;
    private static int _federateFilter_handle;
    private static int _id_handle;
    private static int _interval_handle;
    private static int _marketId_handle;
    private static int _originFed_handle;
    private static int _sourceFed_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the MarketClosed interaction class.
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
    * Returns the fully-qualified (dot-delimited) name of the MarketClosed interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this interaction class
    */
    public static String get_class_name() {
        return "InteractionRoot.C2WInteractionRoot.MarketClosed";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the MarketClosed interaction class.
    *
    * @return the name of this interaction class
    */
    public static String get_simple_class_name() {
        return "MarketClosed";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden parameters in the
    * MarketClosed interaction class.
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
    * MarketClosed interaction class.
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
        _classNameSet.add("InteractionRoot.C2WInteractionRoot.MarketClosed");
        _classNameClassMap.put("InteractionRoot.C2WInteractionRoot.MarketClosed", MarketClosed.class);

        _datamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.MarketClosed", _datamemberNames);
        _allDatamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.MarketClosed", _allDatamemberNames);

        _datamemberNames.add("id");
        _datamemberNames.add("interval");
        _datamemberNames.add("marketId");

        _datamemberTypeMap.put("id", "String");
        _datamemberTypeMap.put("interval", "String");
        _datamemberTypeMap.put("marketId", "String");

        _allDatamemberNames.add("actualLogicalGenerationTime");
        _allDatamemberNames.add("federateFilter");
        _allDatamemberNames.add("id");
        _allDatamemberNames.add("interval");
        _allDatamemberNames.add("marketId");
        _allDatamemberNames.add("originFed");
        _allDatamemberNames.add("sourceFed");
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        C2WInteractionRoot.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getInteractionClassHandle("InteractionRoot.C2WInteractionRoot.MarketClosed");
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

        _classNameHandleMap.put("InteractionRoot.C2WInteractionRoot.MarketClosed", get_handle());
        _classHandleNameMap.put(get_handle(), "InteractionRoot.C2WInteractionRoot.MarketClosed");
        _classHandleSimpleNameMap.put(get_handle(), "MarketClosed");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _actualLogicalGenerationTime_handle = rti.getParameterHandle("actualLogicalGenerationTime", get_handle());
                _federateFilter_handle = rti.getParameterHandle("federateFilter", get_handle());
                _id_handle = rti.getParameterHandle("id", get_handle());
                _interval_handle = rti.getParameterHandle("interval", get_handle());
                _marketId_handle = rti.getParameterHandle("marketId", get_handle());
                _originFed_handle = rti.getParameterHandle("originFed", get_handle());
                _sourceFed_handle = rti.getParameterHandle("sourceFed", get_handle());
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

        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.MarketClosed.actualLogicalGenerationTime", _actualLogicalGenerationTime_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.MarketClosed.federateFilter", _federateFilter_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.MarketClosed.id", _id_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.MarketClosed.interval", _interval_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.MarketClosed.marketId", _marketId_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.MarketClosed.originFed", _originFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.MarketClosed.sourceFed", _sourceFed_handle);

        _datamemberHandleNameMap.put(_actualLogicalGenerationTime_handle, "actualLogicalGenerationTime");
        _datamemberHandleNameMap.put(_federateFilter_handle, "federateFilter");
        _datamemberHandleNameMap.put(_id_handle, "id");
        _datamemberHandleNameMap.put(_interval_handle, "interval");
        _datamemberHandleNameMap.put(_marketId_handle, "marketId");
        _datamemberHandleNameMap.put(_originFed_handle, "originFed");
        _datamemberHandleNameMap.put(_sourceFed_handle, "sourceFed");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the MarketClosed interaction class for a federate.
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
    * Unpublishes the MarketClosed interaction class for a federate.
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
    * Subscribes a federate to the MarketClosed interaction class.
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
    * Unsubscribes a federate from the MarketClosed interaction class.
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
    * (that is, the MarketClosed interaction class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the MarketClosed interaction class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the MarketClosed interaction class).
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
        else if (datamemberHandle == _id_handle) return "id";
        else if (datamemberHandle == _interval_handle) return "interval";
        else if (datamemberHandle == _marketId_handle) return "marketId";
        else if (datamemberHandle == _originFed_handle) return "originFed";
        else if (datamemberHandle == _sourceFed_handle) return "sourceFed";
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
                + "," + "id:" + get_id()
                + "," + "interval:" + get_interval()
                + "," + "marketId:" + get_marketId()
                + "," + "originFed:" + get_originFed()
                + "," + "sourceFed:" + get_sourceFed()
                + ")";
    }

    private String _id = "";
    private String _interval = "";
    private String _marketId = "";

    /**
    * Set the value of the "id" parameter to "value" for this parameter.
    *
    * @param value the new value for the "id" parameter
    */
    public void set_id( String value ) {
        _id = value;
    }

    /**
    * Returns the value of the "id" parameter of this interaction.
    *
    * @return the value of the "id" parameter
    */
    public String get_id() {
        return _id;
    }
    /**
    * Set the value of the "interval" parameter to "value" for this parameter.
    *
    * @param value the new value for the "interval" parameter
    */
    public void set_interval( String value ) {
        _interval = value;
    }

    /**
    * Returns the value of the "interval" parameter of this interaction.
    *
    * @return the value of the "interval" parameter
    */
    public String get_interval() {
        return _interval;
    }
    /**
    * Set the value of the "marketId" parameter to "value" for this parameter.
    *
    * @param value the new value for the "marketId" parameter
    */
    public void set_marketId( String value ) {
        _marketId = value;
    }

    /**
    * Returns the value of the "marketId" parameter of this interaction.
    *
    * @return the value of the "marketId" parameter
    */
    public String get_marketId() {
        return _marketId;
    }

    protected MarketClosed( ReceivedInteraction datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    protected MarketClosed( ReceivedInteraction datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    /**
    * Creates an instance of the MarketClosed interaction class, using
    * "datamemberMap" to initialize its parameter values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new MarketClosed interaction class instance
    */
    public MarketClosed( ReceivedInteraction datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #MarketClosed( ReceivedInteraction datamemberMap )}, except this
    * new MarketClosed interaction class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new MarketClosed interaction class instance
    * @param logicalTime timestamp for this new MarketClosed interaction class
    * instance
    */
    public MarketClosed( ReceivedInteraction datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new MarketClosed interaction class instance that is a duplicate
    * of the instance referred to by MarketClosed_var.
    *
    * @param MarketClosed_var MarketClosed interaction class instance of which
    * this newly created MarketClosed interaction class instance will be a
    * duplicate
    */
    public MarketClosed( MarketClosed MarketClosed_var ) {
        super( MarketClosed_var );

        set_id( MarketClosed_var.get_id() );
        set_interval( MarketClosed_var.get_interval() );
        set_marketId( MarketClosed_var.get_marketId() );
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
        if ( "id".equals(datamemberName) ) return get_id();
        else if ( "interval".equals(datamemberName) ) return get_interval();
        else if ( "marketId".equals(datamemberName) ) return get_marketId();
        else return super.getParameter( datamemberName );
    }

    protected boolean setParameterAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "id".equals( datamemberName) ) set_id( val );
        else if ( "interval".equals( datamemberName) ) set_interval( val );
        else if ( "marketId".equals( datamemberName) ) set_marketId( val );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    protected boolean setParameterAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "id".equals( datamemberName) ) set_id( (String)val );
        else if ( "interval".equals( datamemberName) ) set_interval( (String)val );
        else if ( "marketId".equals( datamemberName) ) set_marketId( (String)val );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof MarketClosed ) {
            MarketClosed data = (MarketClosed)object;
            _id = data._id;
            _interval = data._interval;
            _marketId = data._marketId;
        }
    }
}

