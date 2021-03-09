package gov.nist.hla.te.controller.rti;

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
* Implements InteractionRoot.C2WInteractionRoot.DataReceipt
*/
public class DataReceipt extends C2WInteractionRoot {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the DataReceipt interaction class with default parameter values.
    */
    public DataReceipt() {}

    private static int _actualLogicalGenerationTime_handle;
    private static int _className_handle;
    private static int _dataSent_handle;
    private static int _federateFilter_handle;
    private static int _logicalTime_handle;
    private static int _originFed_handle;
    private static int _sourceFed_handle;
    private static int _uniqueId_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the DataReceipt interaction class.
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
    * Returns the fully-qualified (dot-delimited) name of the DataReceipt interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this interaction class
    */
    public static String get_class_name() {
        return "InteractionRoot.C2WInteractionRoot.DataReceipt";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the DataReceipt interaction class.
    *
    * @return the name of this interaction class
    */
    public static String get_simple_class_name() {
        return "DataReceipt";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden parameters in the
    * DataReceipt interaction class.
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
    * DataReceipt interaction class.
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
        _classNameSet.add("InteractionRoot.C2WInteractionRoot.DataReceipt");
        _classNameClassMap.put("InteractionRoot.C2WInteractionRoot.DataReceipt", DataReceipt.class);

        _datamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.DataReceipt", _datamemberNames);
        _allDatamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.DataReceipt", _allDatamemberNames);

        _datamemberNames.add("className");
        _datamemberNames.add("dataSent");
        _datamemberNames.add("logicalTime");
        _datamemberNames.add("uniqueId");

        _datamemberTypeMap.put("className", "String");
        _datamemberTypeMap.put("dataSent", "int");
        _datamemberTypeMap.put("logicalTime", "double");
        _datamemberTypeMap.put("uniqueId", "String");

        _allDatamemberNames.add("actualLogicalGenerationTime");
        _allDatamemberNames.add("className");
        _allDatamemberNames.add("dataSent");
        _allDatamemberNames.add("federateFilter");
        _allDatamemberNames.add("logicalTime");
        _allDatamemberNames.add("originFed");
        _allDatamemberNames.add("sourceFed");
        _allDatamemberNames.add("uniqueId");
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        C2WInteractionRoot.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getInteractionClassHandle("InteractionRoot.C2WInteractionRoot.DataReceipt");
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

        _classNameHandleMap.put("InteractionRoot.C2WInteractionRoot.DataReceipt", get_handle());
        _classHandleNameMap.put(get_handle(), "InteractionRoot.C2WInteractionRoot.DataReceipt");
        _classHandleSimpleNameMap.put(get_handle(), "DataReceipt");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _actualLogicalGenerationTime_handle = rti.getParameterHandle("actualLogicalGenerationTime", get_handle());
                _className_handle = rti.getParameterHandle("className", get_handle());
                _dataSent_handle = rti.getParameterHandle("dataSent", get_handle());
                _federateFilter_handle = rti.getParameterHandle("federateFilter", get_handle());
                _logicalTime_handle = rti.getParameterHandle("logicalTime", get_handle());
                _originFed_handle = rti.getParameterHandle("originFed", get_handle());
                _sourceFed_handle = rti.getParameterHandle("sourceFed", get_handle());
                _uniqueId_handle = rti.getParameterHandle("uniqueId", get_handle());
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

        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.DataReceipt.actualLogicalGenerationTime", _actualLogicalGenerationTime_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.DataReceipt.className", _className_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.DataReceipt.dataSent", _dataSent_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.DataReceipt.federateFilter", _federateFilter_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.DataReceipt.logicalTime", _logicalTime_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.DataReceipt.originFed", _originFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.DataReceipt.sourceFed", _sourceFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.DataReceipt.uniqueId", _uniqueId_handle);

        _datamemberHandleNameMap.put(_actualLogicalGenerationTime_handle, "actualLogicalGenerationTime");
        _datamemberHandleNameMap.put(_className_handle, "className");
        _datamemberHandleNameMap.put(_dataSent_handle, "dataSent");
        _datamemberHandleNameMap.put(_federateFilter_handle, "federateFilter");
        _datamemberHandleNameMap.put(_logicalTime_handle, "logicalTime");
        _datamemberHandleNameMap.put(_originFed_handle, "originFed");
        _datamemberHandleNameMap.put(_sourceFed_handle, "sourceFed");
        _datamemberHandleNameMap.put(_uniqueId_handle, "uniqueId");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the DataReceipt interaction class for a federate.
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
    * Unpublishes the DataReceipt interaction class for a federate.
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
    * Subscribes a federate to the DataReceipt interaction class.
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
    * Unsubscribes a federate from the DataReceipt interaction class.
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
    * (that is, the DataReceipt interaction class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the DataReceipt interaction class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the DataReceipt interaction class).
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
        else if (datamemberHandle == _className_handle) return "className";
        else if (datamemberHandle == _dataSent_handle) return "dataSent";
        else if (datamemberHandle == _federateFilter_handle) return "federateFilter";
        else if (datamemberHandle == _logicalTime_handle) return "logicalTime";
        else if (datamemberHandle == _originFed_handle) return "originFed";
        else if (datamemberHandle == _sourceFed_handle) return "sourceFed";
        else if (datamemberHandle == _uniqueId_handle) return "uniqueId";
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
                + "," + "className:" + get_className()
                + "," + "dataSent:" + get_dataSent()
                + "," + "federateFilter:" + get_federateFilter()
                + "," + "logicalTime:" + get_logicalTime()
                + "," + "originFed:" + get_originFed()
                + "," + "sourceFed:" + get_sourceFed()
                + "," + "uniqueId:" + get_uniqueId()
                + ")";
    }

    private String _className = "";
    private int _dataSent = 0;
    private double _logicalTime = 0;
    private String _uniqueId = "";

    /**
    * Set the value of the "className" parameter to "value" for this parameter.
    *
    * @param value the new value for the "className" parameter
    */
    public void set_className( String value ) {
        _className = value;
    }

    /**
    * Returns the value of the "className" parameter of this interaction.
    *
    * @return the value of the "className" parameter
    */
    public String get_className() {
        return _className;
    }
    /**
    * Set the value of the "dataSent" parameter to "value" for this parameter.
    *
    * @param value the new value for the "dataSent" parameter
    */
    public void set_dataSent( int value ) {
        _dataSent = value;
    }

    /**
    * Returns the value of the "dataSent" parameter of this interaction.
    *
    * @return the value of the "dataSent" parameter
    */
    public int get_dataSent() {
        return _dataSent;
    }
    /**
    * Set the value of the "logicalTime" parameter to "value" for this parameter.
    *
    * @param value the new value for the "logicalTime" parameter
    */
    public void set_logicalTime( double value ) {
        _logicalTime = value;
    }

    /**
    * Returns the value of the "logicalTime" parameter of this interaction.
    *
    * @return the value of the "logicalTime" parameter
    */
    public double get_logicalTime() {
        return _logicalTime;
    }
    /**
    * Set the value of the "uniqueId" parameter to "value" for this parameter.
    *
    * @param value the new value for the "uniqueId" parameter
    */
    public void set_uniqueId( String value ) {
        _uniqueId = value;
    }

    /**
    * Returns the value of the "uniqueId" parameter of this interaction.
    *
    * @return the value of the "uniqueId" parameter
    */
    public String get_uniqueId() {
        return _uniqueId;
    }

    protected DataReceipt( ReceivedInteraction datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    protected DataReceipt( ReceivedInteraction datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    /**
    * Creates an instance of the DataReceipt interaction class, using
    * "datamemberMap" to initialize its parameter values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new DataReceipt interaction class instance
    */
    public DataReceipt( ReceivedInteraction datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #DataReceipt( ReceivedInteraction datamemberMap )}, except this
    * new DataReceipt interaction class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new DataReceipt interaction class instance
    * @param logicalTime timestamp for this new DataReceipt interaction class
    * instance
    */
    public DataReceipt( ReceivedInteraction datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new DataReceipt interaction class instance that is a duplicate
    * of the instance referred to by DataReceipt_var.
    *
    * @param DataReceipt_var DataReceipt interaction class instance of which
    * this newly created DataReceipt interaction class instance will be a
    * duplicate
    */
    public DataReceipt( DataReceipt DataReceipt_var ) {
        super( DataReceipt_var );

        set_className( DataReceipt_var.get_className() );
        set_dataSent( DataReceipt_var.get_dataSent() );
        set_logicalTime( DataReceipt_var.get_logicalTime() );
        set_uniqueId( DataReceipt_var.get_uniqueId() );
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
        if ( "className".equals(datamemberName) ) return get_className();
        else if ( "dataSent".equals(datamemberName) ) return new Integer(get_dataSent());
        else if ( "logicalTime".equals(datamemberName) ) return new Double(get_logicalTime());
        else if ( "uniqueId".equals(datamemberName) ) return get_uniqueId();
        else return super.getParameter( datamemberName );
    }

    protected boolean setParameterAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "className".equals( datamemberName) ) set_className( val );
        else if ( "dataSent".equals( datamemberName) ) set_dataSent( Integer.parseInt(val) );
        else if ( "logicalTime".equals( datamemberName) ) set_logicalTime( Double.parseDouble(val) );
        else if ( "uniqueId".equals( datamemberName) ) set_uniqueId( val );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    protected boolean setParameterAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "className".equals( datamemberName) ) set_className( (String)val );
        else if ( "dataSent".equals( datamemberName) ) set_dataSent( (Integer)val );
        else if ( "logicalTime".equals( datamemberName) ) set_logicalTime( (Double)val );
        else if ( "uniqueId".equals( datamemberName) ) set_uniqueId( (String)val );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof DataReceipt ) {
            DataReceipt data = (DataReceipt)object;
            _className = data._className;
            _dataSent = data._dataSent;
            _logicalTime = data._logicalTime;
            _uniqueId = data._uniqueId;
        }
    }
}

