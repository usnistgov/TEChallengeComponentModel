package gov.nist.hla.te.simulationtime.rti;

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
* Implements InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime
*/
public class SimTime extends SimulationControl {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the SimTime interaction class with default parameter values.
    */
    public SimTime() {}

    private static int _actualLogicalGenerationTime_handle;
    private static int _federateFilter_handle;
    private static int _originFed_handle;
    private static int _sourceFed_handle;
    private static int _timeScale_handle;
    private static int _timeZone_handle;
    private static int _timeZonePosix_handle;
    private static int _unixTimeStart_handle;
    private static int _unixTimeStop_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the SimTime interaction class.
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
    * Returns the fully-qualified (dot-delimited) name of the SimTime interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this interaction class
    */
    public static String get_class_name() {
        return "InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the SimTime interaction class.
    *
    * @return the name of this interaction class
    */
    public static String get_simple_class_name() {
        return "SimTime";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden parameters in the
    * SimTime interaction class.
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
    * SimTime interaction class.
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
        _classNameSet.add("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime");
        _classNameClassMap.put("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime", SimTime.class);

        _datamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime", _datamemberNames);
        _allDatamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime", _allDatamemberNames);

        _datamemberNames.add("timeScale");
        _datamemberNames.add("timeZone");
        _datamemberNames.add("timeZonePosix");
        _datamemberNames.add("unixTimeStart");
        _datamemberNames.add("unixTimeStop");

        _datamemberTypeMap.put("timeScale", "double");
        _datamemberTypeMap.put("timeZone", "String");
        _datamemberTypeMap.put("timeZonePosix", "String");
        _datamemberTypeMap.put("unixTimeStart", "long");
        _datamemberTypeMap.put("unixTimeStop", "long");

        _allDatamemberNames.add("actualLogicalGenerationTime");
        _allDatamemberNames.add("federateFilter");
        _allDatamemberNames.add("originFed");
        _allDatamemberNames.add("sourceFed");
        _allDatamemberNames.add("timeScale");
        _allDatamemberNames.add("timeZone");
        _allDatamemberNames.add("timeZonePosix");
        _allDatamemberNames.add("unixTimeStart");
        _allDatamemberNames.add("unixTimeStop");
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        SimulationControl.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getInteractionClassHandle("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime");
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

        _classNameHandleMap.put("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime", get_handle());
        _classHandleNameMap.put(get_handle(), "InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime");
        _classHandleSimpleNameMap.put(get_handle(), "SimTime");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _actualLogicalGenerationTime_handle = rti.getParameterHandle("actualLogicalGenerationTime", get_handle());
                _federateFilter_handle = rti.getParameterHandle("federateFilter", get_handle());
                _originFed_handle = rti.getParameterHandle("originFed", get_handle());
                _sourceFed_handle = rti.getParameterHandle("sourceFed", get_handle());
                _timeScale_handle = rti.getParameterHandle("timeScale", get_handle());
                _timeZone_handle = rti.getParameterHandle("timeZone", get_handle());
                _timeZonePosix_handle = rti.getParameterHandle("timeZonePosix", get_handle());
                _unixTimeStart_handle = rti.getParameterHandle("unixTimeStart", get_handle());
                _unixTimeStop_handle = rti.getParameterHandle("unixTimeStop", get_handle());
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

        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime.actualLogicalGenerationTime", _actualLogicalGenerationTime_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime.federateFilter", _federateFilter_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime.originFed", _originFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime.sourceFed", _sourceFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime.timeScale", _timeScale_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime.timeZone", _timeZone_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime.timeZonePosix", _timeZonePosix_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime.unixTimeStart", _unixTimeStart_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.SimulationControl.SimTime.unixTimeStop", _unixTimeStop_handle);

        _datamemberHandleNameMap.put(_actualLogicalGenerationTime_handle, "actualLogicalGenerationTime");
        _datamemberHandleNameMap.put(_federateFilter_handle, "federateFilter");
        _datamemberHandleNameMap.put(_originFed_handle, "originFed");
        _datamemberHandleNameMap.put(_sourceFed_handle, "sourceFed");
        _datamemberHandleNameMap.put(_timeScale_handle, "timeScale");
        _datamemberHandleNameMap.put(_timeZone_handle, "timeZone");
        _datamemberHandleNameMap.put(_timeZonePosix_handle, "timeZonePosix");
        _datamemberHandleNameMap.put(_unixTimeStart_handle, "unixTimeStart");
        _datamemberHandleNameMap.put(_unixTimeStop_handle, "unixTimeStop");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the SimTime interaction class for a federate.
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
    * Unpublishes the SimTime interaction class for a federate.
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
    * Subscribes a federate to the SimTime interaction class.
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
    * Unsubscribes a federate from the SimTime interaction class.
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
    * (that is, the SimTime interaction class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the SimTime interaction class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the SimTime interaction class).
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
        else if (datamemberHandle == _originFed_handle) return "originFed";
        else if (datamemberHandle == _sourceFed_handle) return "sourceFed";
        else if (datamemberHandle == _timeScale_handle) return "timeScale";
        else if (datamemberHandle == _timeZone_handle) return "timeZone";
        else if (datamemberHandle == _timeZonePosix_handle) return "timeZonePosix";
        else if (datamemberHandle == _unixTimeStart_handle) return "unixTimeStart";
        else if (datamemberHandle == _unixTimeStop_handle) return "unixTimeStop";
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
                + "," + "originFed:" + get_originFed()
                + "," + "sourceFed:" + get_sourceFed()
                + "," + "timeScale:" + get_timeScale()
                + "," + "timeZone:" + get_timeZone()
                + "," + "timeZonePosix:" + get_timeZonePosix()
                + "," + "unixTimeStart:" + get_unixTimeStart()
                + "," + "unixTimeStop:" + get_unixTimeStop()
                + ")";
    }

    private double _timeScale = 0;
    private String _timeZone = "";
    private String _timeZonePosix = "";
    private long _unixTimeStart = 0;
    private long _unixTimeStop = 0;

    /**
    * Set the value of the "timeScale" parameter to "value" for this parameter.
    *
    * @param value the new value for the "timeScale" parameter
    */
    public void set_timeScale( double value ) {
        _timeScale = value;
    }

    /**
    * Returns the value of the "timeScale" parameter of this interaction.
    *
    * @return the value of the "timeScale" parameter
    */
    public double get_timeScale() {
        return _timeScale;
    }
    /**
    * Set the value of the "timeZone" parameter to "value" for this parameter.
    *
    * @param value the new value for the "timeZone" parameter
    */
    public void set_timeZone( String value ) {
        _timeZone = value;
    }

    /**
    * Returns the value of the "timeZone" parameter of this interaction.
    *
    * @return the value of the "timeZone" parameter
    */
    public String get_timeZone() {
        return _timeZone;
    }
    /**
    * Set the value of the "timeZonePosix" parameter to "value" for this parameter.
    *
    * @param value the new value for the "timeZonePosix" parameter
    */
    public void set_timeZonePosix( String value ) {
        _timeZonePosix = value;
    }

    /**
    * Returns the value of the "timeZonePosix" parameter of this interaction.
    *
    * @return the value of the "timeZonePosix" parameter
    */
    public String get_timeZonePosix() {
        return _timeZonePosix;
    }
    /**
    * Set the value of the "unixTimeStart" parameter to "value" for this parameter.
    *
    * @param value the new value for the "unixTimeStart" parameter
    */
    public void set_unixTimeStart( long value ) {
        _unixTimeStart = value;
    }

    /**
    * Returns the value of the "unixTimeStart" parameter of this interaction.
    *
    * @return the value of the "unixTimeStart" parameter
    */
    public long get_unixTimeStart() {
        return _unixTimeStart;
    }
    /**
    * Set the value of the "unixTimeStop" parameter to "value" for this parameter.
    *
    * @param value the new value for the "unixTimeStop" parameter
    */
    public void set_unixTimeStop( long value ) {
        _unixTimeStop = value;
    }

    /**
    * Returns the value of the "unixTimeStop" parameter of this interaction.
    *
    * @return the value of the "unixTimeStop" parameter
    */
    public long get_unixTimeStop() {
        return _unixTimeStop;
    }

    protected SimTime( ReceivedInteraction datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    protected SimTime( ReceivedInteraction datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    /**
    * Creates an instance of the SimTime interaction class, using
    * "datamemberMap" to initialize its parameter values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new SimTime interaction class instance
    */
    public SimTime( ReceivedInteraction datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #SimTime( ReceivedInteraction datamemberMap )}, except this
    * new SimTime interaction class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new SimTime interaction class instance
    * @param logicalTime timestamp for this new SimTime interaction class
    * instance
    */
    public SimTime( ReceivedInteraction datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new SimTime interaction class instance that is a duplicate
    * of the instance referred to by SimTime_var.
    *
    * @param SimTime_var SimTime interaction class instance of which
    * this newly created SimTime interaction class instance will be a
    * duplicate
    */
    public SimTime( SimTime SimTime_var ) {
        super( SimTime_var );

        set_timeScale( SimTime_var.get_timeScale() );
        set_timeZone( SimTime_var.get_timeZone() );
        set_timeZonePosix( SimTime_var.get_timeZonePosix() );
        set_unixTimeStart( SimTime_var.get_unixTimeStart() );
        set_unixTimeStop( SimTime_var.get_unixTimeStop() );
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
        if ( "timeScale".equals(datamemberName) ) return new Double(get_timeScale());
        else if ( "timeZone".equals(datamemberName) ) return get_timeZone();
        else if ( "timeZonePosix".equals(datamemberName) ) return get_timeZonePosix();
        else if ( "unixTimeStart".equals(datamemberName) ) return new Long(get_unixTimeStart());
        else if ( "unixTimeStop".equals(datamemberName) ) return new Long(get_unixTimeStop());
        else return super.getParameter( datamemberName );
    }

    protected boolean setParameterAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "timeScale".equals( datamemberName) ) set_timeScale( Double.parseDouble(val) );
        else if ( "timeZone".equals( datamemberName) ) set_timeZone( val );
        else if ( "timeZonePosix".equals( datamemberName) ) set_timeZonePosix( val );
        else if ( "unixTimeStart".equals( datamemberName) ) set_unixTimeStart( Long.parseLong(val) );
        else if ( "unixTimeStop".equals( datamemberName) ) set_unixTimeStop( Long.parseLong(val) );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    protected boolean setParameterAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "timeScale".equals( datamemberName) ) set_timeScale( (Double)val );
        else if ( "timeZone".equals( datamemberName) ) set_timeZone( (String)val );
        else if ( "timeZonePosix".equals( datamemberName) ) set_timeZonePosix( (String)val );
        else if ( "unixTimeStart".equals( datamemberName) ) set_unixTimeStart( (Long)val );
        else if ( "unixTimeStop".equals( datamemberName) ) set_unixTimeStop( (Long)val );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof SimTime ) {
            SimTime data = (SimTime)object;
            _timeScale = data._timeScale;
            _timeZone = data._timeZone;
            _timeZonePosix = data._timeZonePosix;
            _unixTimeStart = data._unixTimeStart;
            _unixTimeStop = data._unixTimeStop;
        }
    }
}

