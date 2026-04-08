package gov.nist.hla.te.flexibleresourcecontroller.rti;

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
* Implements InteractionRoot.C2WInteractionRoot.Commitment
*/
public class Commitment extends C2WInteractionRoot {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the Commitment interaction class with default parameter values.
    */
    public Commitment() {}

    private static int _actualLogicalGenerationTime_handle;
    private static int _federateFilter_handle;
    private static int _interval_handle;
    private static int _originFed_handle;
    private static int _partyId_handle;
    private static int _sourceFed_handle;
    private static int _totalCost_handle;
    private static int _totalQuantity_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the Commitment interaction class.
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
    * Returns the fully-qualified (dot-delimited) name of the Commitment interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this interaction class
    */
    public static String get_class_name() {
        return "InteractionRoot.C2WInteractionRoot.Commitment";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the Commitment interaction class.
    *
    * @return the name of this interaction class
    */
    public static String get_simple_class_name() {
        return "Commitment";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden parameters in the
    * Commitment interaction class.
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
    * Commitment interaction class.
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
        _classNameSet.add("InteractionRoot.C2WInteractionRoot.Commitment");
        _classNameClassMap.put("InteractionRoot.C2WInteractionRoot.Commitment", Commitment.class);

        _datamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.Commitment", _datamemberNames);
        _allDatamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.Commitment", _allDatamemberNames);

        _datamemberNames.add("interval");
        _datamemberNames.add("partyId");
        _datamemberNames.add("totalCost");
        _datamemberNames.add("totalQuantity");

        _datamemberTypeMap.put("interval", "String");
        _datamemberTypeMap.put("partyId", "String");
        _datamemberTypeMap.put("totalCost", "String");
        _datamemberTypeMap.put("totalQuantity", "String");

        _allDatamemberNames.add("actualLogicalGenerationTime");
        _allDatamemberNames.add("federateFilter");
        _allDatamemberNames.add("interval");
        _allDatamemberNames.add("originFed");
        _allDatamemberNames.add("partyId");
        _allDatamemberNames.add("sourceFed");
        _allDatamemberNames.add("totalCost");
        _allDatamemberNames.add("totalQuantity");
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        C2WInteractionRoot.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getInteractionClassHandle("InteractionRoot.C2WInteractionRoot.Commitment");
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

        _classNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Commitment", get_handle());
        _classHandleNameMap.put(get_handle(), "InteractionRoot.C2WInteractionRoot.Commitment");
        _classHandleSimpleNameMap.put(get_handle(), "Commitment");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _actualLogicalGenerationTime_handle = rti.getParameterHandle("actualLogicalGenerationTime", get_handle());
                _federateFilter_handle = rti.getParameterHandle("federateFilter", get_handle());
                _interval_handle = rti.getParameterHandle("interval", get_handle());
                _originFed_handle = rti.getParameterHandle("originFed", get_handle());
                _partyId_handle = rti.getParameterHandle("partyId", get_handle());
                _sourceFed_handle = rti.getParameterHandle("sourceFed", get_handle());
                _totalCost_handle = rti.getParameterHandle("totalCost", get_handle());
                _totalQuantity_handle = rti.getParameterHandle("totalQuantity", get_handle());
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

        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Commitment.actualLogicalGenerationTime", _actualLogicalGenerationTime_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Commitment.federateFilter", _federateFilter_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Commitment.interval", _interval_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Commitment.originFed", _originFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Commitment.partyId", _partyId_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Commitment.sourceFed", _sourceFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Commitment.totalCost", _totalCost_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Commitment.totalQuantity", _totalQuantity_handle);

        _datamemberHandleNameMap.put(_actualLogicalGenerationTime_handle, "actualLogicalGenerationTime");
        _datamemberHandleNameMap.put(_federateFilter_handle, "federateFilter");
        _datamemberHandleNameMap.put(_interval_handle, "interval");
        _datamemberHandleNameMap.put(_originFed_handle, "originFed");
        _datamemberHandleNameMap.put(_partyId_handle, "partyId");
        _datamemberHandleNameMap.put(_sourceFed_handle, "sourceFed");
        _datamemberHandleNameMap.put(_totalCost_handle, "totalCost");
        _datamemberHandleNameMap.put(_totalQuantity_handle, "totalQuantity");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the Commitment interaction class for a federate.
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
    * Unpublishes the Commitment interaction class for a federate.
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
    * Subscribes a federate to the Commitment interaction class.
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
    * Unsubscribes a federate from the Commitment interaction class.
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
    * (that is, the Commitment interaction class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the Commitment interaction class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the Commitment interaction class).
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
        else if (datamemberHandle == _interval_handle) return "interval";
        else if (datamemberHandle == _originFed_handle) return "originFed";
        else if (datamemberHandle == _partyId_handle) return "partyId";
        else if (datamemberHandle == _sourceFed_handle) return "sourceFed";
        else if (datamemberHandle == _totalCost_handle) return "totalCost";
        else if (datamemberHandle == _totalQuantity_handle) return "totalQuantity";
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
                + "," + "interval:" + get_interval()
                + "," + "originFed:" + get_originFed()
                + "," + "partyId:" + get_partyId()
                + "," + "sourceFed:" + get_sourceFed()
                + "," + "totalCost:" + get_totalCost()
                + "," + "totalQuantity:" + get_totalQuantity()
                + ")";
    }

    private String _interval = "";
    private String _partyId = "";
    private String _totalCost = "";
    private String _totalQuantity = "";

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
    * Set the value of the "partyId" parameter to "value" for this parameter.
    *
    * @param value the new value for the "partyId" parameter
    */
    public void set_partyId( String value ) {
        _partyId = value;
    }

    /**
    * Returns the value of the "partyId" parameter of this interaction.
    *
    * @return the value of the "partyId" parameter
    */
    public String get_partyId() {
        return _partyId;
    }
    /**
    * Set the value of the "totalCost" parameter to "value" for this parameter.
    *
    * @param value the new value for the "totalCost" parameter
    */
    public void set_totalCost( String value ) {
        _totalCost = value;
    }

    /**
    * Returns the value of the "totalCost" parameter of this interaction.
    *
    * @return the value of the "totalCost" parameter
    */
    public String get_totalCost() {
        return _totalCost;
    }
    /**
    * Set the value of the "totalQuantity" parameter to "value" for this parameter.
    *
    * @param value the new value for the "totalQuantity" parameter
    */
    public void set_totalQuantity( String value ) {
        _totalQuantity = value;
    }

    /**
    * Returns the value of the "totalQuantity" parameter of this interaction.
    *
    * @return the value of the "totalQuantity" parameter
    */
    public String get_totalQuantity() {
        return _totalQuantity;
    }

    protected Commitment( ReceivedInteraction datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    protected Commitment( ReceivedInteraction datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    /**
    * Creates an instance of the Commitment interaction class, using
    * "datamemberMap" to initialize its parameter values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new Commitment interaction class instance
    */
    public Commitment( ReceivedInteraction datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #Commitment( ReceivedInteraction datamemberMap )}, except this
    * new Commitment interaction class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new Commitment interaction class instance
    * @param logicalTime timestamp for this new Commitment interaction class
    * instance
    */
    public Commitment( ReceivedInteraction datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new Commitment interaction class instance that is a duplicate
    * of the instance referred to by Commitment_var.
    *
    * @param Commitment_var Commitment interaction class instance of which
    * this newly created Commitment interaction class instance will be a
    * duplicate
    */
    public Commitment( Commitment Commitment_var ) {
        super( Commitment_var );

        set_interval( Commitment_var.get_interval() );
        set_partyId( Commitment_var.get_partyId() );
        set_totalCost( Commitment_var.get_totalCost() );
        set_totalQuantity( Commitment_var.get_totalQuantity() );
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
        if ( "interval".equals(datamemberName) ) return get_interval();
        else if ( "partyId".equals(datamemberName) ) return get_partyId();
        else if ( "totalCost".equals(datamemberName) ) return get_totalCost();
        else if ( "totalQuantity".equals(datamemberName) ) return get_totalQuantity();
        else return super.getParameter( datamemberName );
    }

    protected boolean setParameterAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "interval".equals( datamemberName) ) set_interval( val );
        else if ( "partyId".equals( datamemberName) ) set_partyId( val );
        else if ( "totalCost".equals( datamemberName) ) set_totalCost( val );
        else if ( "totalQuantity".equals( datamemberName) ) set_totalQuantity( val );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    protected boolean setParameterAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "interval".equals( datamemberName) ) set_interval( (String)val );
        else if ( "partyId".equals( datamemberName) ) set_partyId( (String)val );
        else if ( "totalCost".equals( datamemberName) ) set_totalCost( (String)val );
        else if ( "totalQuantity".equals( datamemberName) ) set_totalQuantity( (String)val );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof Commitment ) {
            Commitment data = (Commitment)object;
            _interval = data._interval;
            _partyId = data._partyId;
            _totalCost = data._totalCost;
            _totalQuantity = data._totalQuantity;
        }
    }
}

