package gov.nist.hla.te.parity.parityreporter.rti;

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
* Implements InteractionRoot.C2WInteractionRoot.Tender
*/
public class Tender extends C2WInteractionRoot {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the Tender interaction class with default parameter values.
    */
    public Tender() {}

    private static int _actualLogicalGenerationTime_handle;
    private static int _durationInMinutes_handle;
    private static int _expireTime_handle;
    private static int _federateFilter_handle;
    private static int _originFed_handle;
    private static int _partyId_handle;
    private static int _price_handle;
    private static int _quantity_handle;
    private static int _side_handle;
    private static int _sourceFed_handle;
    private static int _startTime_handle;
    private static int _tenderId_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the Tender interaction class.
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
    * Returns the fully-qualified (dot-delimited) name of the Tender interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this interaction class
    */
    public static String get_class_name() {
        return "InteractionRoot.C2WInteractionRoot.Tender";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the Tender interaction class.
    *
    * @return the name of this interaction class
    */
    public static String get_simple_class_name() {
        return "Tender";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden parameters in the
    * Tender interaction class.
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
    * Tender interaction class.
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
        _classNameSet.add("InteractionRoot.C2WInteractionRoot.Tender");
        _classNameClassMap.put("InteractionRoot.C2WInteractionRoot.Tender", Tender.class);

        _datamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.Tender", _datamemberNames);
        _allDatamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.Tender", _allDatamemberNames);

        _datamemberNames.add("durationInMinutes");
        _datamemberNames.add("expireTime");
        _datamemberNames.add("partyId");
        _datamemberNames.add("price");
        _datamemberNames.add("quantity");
        _datamemberNames.add("side");
        _datamemberNames.add("startTime");
        _datamemberNames.add("tenderId");

        _datamemberTypeMap.put("durationInMinutes", "long");
        _datamemberTypeMap.put("expireTime", "String");
        _datamemberTypeMap.put("partyId", "String");
        _datamemberTypeMap.put("price", "double");
        _datamemberTypeMap.put("quantity", "double");
        _datamemberTypeMap.put("side", "char");
        _datamemberTypeMap.put("startTime", "String");
        _datamemberTypeMap.put("tenderId", "long");

        _allDatamemberNames.add("actualLogicalGenerationTime");
        _allDatamemberNames.add("durationInMinutes");
        _allDatamemberNames.add("expireTime");
        _allDatamemberNames.add("federateFilter");
        _allDatamemberNames.add("originFed");
        _allDatamemberNames.add("partyId");
        _allDatamemberNames.add("price");
        _allDatamemberNames.add("quantity");
        _allDatamemberNames.add("side");
        _allDatamemberNames.add("sourceFed");
        _allDatamemberNames.add("startTime");
        _allDatamemberNames.add("tenderId");
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        C2WInteractionRoot.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getInteractionClassHandle("InteractionRoot.C2WInteractionRoot.Tender");
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

        _classNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Tender", get_handle());
        _classHandleNameMap.put(get_handle(), "InteractionRoot.C2WInteractionRoot.Tender");
        _classHandleSimpleNameMap.put(get_handle(), "Tender");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _actualLogicalGenerationTime_handle = rti.getParameterHandle("actualLogicalGenerationTime", get_handle());
                _durationInMinutes_handle = rti.getParameterHandle("durationInMinutes", get_handle());
                _expireTime_handle = rti.getParameterHandle("expireTime", get_handle());
                _federateFilter_handle = rti.getParameterHandle("federateFilter", get_handle());
                _originFed_handle = rti.getParameterHandle("originFed", get_handle());
                _partyId_handle = rti.getParameterHandle("partyId", get_handle());
                _price_handle = rti.getParameterHandle("price", get_handle());
                _quantity_handle = rti.getParameterHandle("quantity", get_handle());
                _side_handle = rti.getParameterHandle("side", get_handle());
                _sourceFed_handle = rti.getParameterHandle("sourceFed", get_handle());
                _startTime_handle = rti.getParameterHandle("startTime", get_handle());
                _tenderId_handle = rti.getParameterHandle("tenderId", get_handle());
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

        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Tender.actualLogicalGenerationTime", _actualLogicalGenerationTime_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Tender.durationInMinutes", _durationInMinutes_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Tender.expireTime", _expireTime_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Tender.federateFilter", _federateFilter_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Tender.originFed", _originFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Tender.partyId", _partyId_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Tender.price", _price_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Tender.quantity", _quantity_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Tender.side", _side_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Tender.sourceFed", _sourceFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Tender.startTime", _startTime_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.Tender.tenderId", _tenderId_handle);

        _datamemberHandleNameMap.put(_actualLogicalGenerationTime_handle, "actualLogicalGenerationTime");
        _datamemberHandleNameMap.put(_durationInMinutes_handle, "durationInMinutes");
        _datamemberHandleNameMap.put(_expireTime_handle, "expireTime");
        _datamemberHandleNameMap.put(_federateFilter_handle, "federateFilter");
        _datamemberHandleNameMap.put(_originFed_handle, "originFed");
        _datamemberHandleNameMap.put(_partyId_handle, "partyId");
        _datamemberHandleNameMap.put(_price_handle, "price");
        _datamemberHandleNameMap.put(_quantity_handle, "quantity");
        _datamemberHandleNameMap.put(_side_handle, "side");
        _datamemberHandleNameMap.put(_sourceFed_handle, "sourceFed");
        _datamemberHandleNameMap.put(_startTime_handle, "startTime");
        _datamemberHandleNameMap.put(_tenderId_handle, "tenderId");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the Tender interaction class for a federate.
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
    * Unpublishes the Tender interaction class for a federate.
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
    * Subscribes a federate to the Tender interaction class.
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
    * Unsubscribes a federate from the Tender interaction class.
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
    * (that is, the Tender interaction class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the Tender interaction class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the Tender interaction class).
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
        else if (datamemberHandle == _durationInMinutes_handle) return "durationInMinutes";
        else if (datamemberHandle == _expireTime_handle) return "expireTime";
        else if (datamemberHandle == _federateFilter_handle) return "federateFilter";
        else if (datamemberHandle == _originFed_handle) return "originFed";
        else if (datamemberHandle == _partyId_handle) return "partyId";
        else if (datamemberHandle == _price_handle) return "price";
        else if (datamemberHandle == _quantity_handle) return "quantity";
        else if (datamemberHandle == _side_handle) return "side";
        else if (datamemberHandle == _sourceFed_handle) return "sourceFed";
        else if (datamemberHandle == _startTime_handle) return "startTime";
        else if (datamemberHandle == _tenderId_handle) return "tenderId";
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
                + "," + "durationInMinutes:" + get_durationInMinutes()
                + "," + "expireTime:" + get_expireTime()
                + "," + "federateFilter:" + get_federateFilter()
                + "," + "originFed:" + get_originFed()
                + "," + "partyId:" + get_partyId()
                + "," + "price:" + get_price()
                + "," + "quantity:" + get_quantity()
                + "," + "side:" + get_side()
                + "," + "sourceFed:" + get_sourceFed()
                + "," + "startTime:" + get_startTime()
                + "," + "tenderId:" + get_tenderId()
                + ")";
    }

    private long _durationInMinutes = 0;
    private String _expireTime = "";
    private String _partyId = "";
    private double _price = 0;
    private double _quantity = 0;
    private char _side = '\0';
    private String _startTime = "";
    private long _tenderId = 0;

    /**
    * Set the value of the "durationInMinutes" parameter to "value" for this parameter.
    *
    * @param value the new value for the "durationInMinutes" parameter
    */
    public void set_durationInMinutes( long value ) {
        _durationInMinutes = value;
    }

    /**
    * Returns the value of the "durationInMinutes" parameter of this interaction.
    *
    * @return the value of the "durationInMinutes" parameter
    */
    public long get_durationInMinutes() {
        return _durationInMinutes;
    }
    /**
    * Set the value of the "expireTime" parameter to "value" for this parameter.
    *
    * @param value the new value for the "expireTime" parameter
    */
    public void set_expireTime( String value ) {
        _expireTime = value;
    }

    /**
    * Returns the value of the "expireTime" parameter of this interaction.
    *
    * @return the value of the "expireTime" parameter
    */
    public String get_expireTime() {
        return _expireTime;
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
    * Set the value of the "price" parameter to "value" for this parameter.
    *
    * @param value the new value for the "price" parameter
    */
    public void set_price( double value ) {
        _price = value;
    }

    /**
    * Returns the value of the "price" parameter of this interaction.
    *
    * @return the value of the "price" parameter
    */
    public double get_price() {
        return _price;
    }
    /**
    * Set the value of the "quantity" parameter to "value" for this parameter.
    *
    * @param value the new value for the "quantity" parameter
    */
    public void set_quantity( double value ) {
        _quantity = value;
    }

    /**
    * Returns the value of the "quantity" parameter of this interaction.
    *
    * @return the value of the "quantity" parameter
    */
    public double get_quantity() {
        return _quantity;
    }
    /**
    * Set the value of the "side" parameter to "value" for this parameter.
    *
    * @param value the new value for the "side" parameter
    */
    public void set_side( char value ) {
        _side = value;
    }

    /**
    * Returns the value of the "side" parameter of this interaction.
    *
    * @return the value of the "side" parameter
    */
    public char get_side() {
        return _side;
    }
    /**
    * Set the value of the "startTime" parameter to "value" for this parameter.
    *
    * @param value the new value for the "startTime" parameter
    */
    public void set_startTime( String value ) {
        _startTime = value;
    }

    /**
    * Returns the value of the "startTime" parameter of this interaction.
    *
    * @return the value of the "startTime" parameter
    */
    public String get_startTime() {
        return _startTime;
    }
    /**
    * Set the value of the "tenderId" parameter to "value" for this parameter.
    *
    * @param value the new value for the "tenderId" parameter
    */
    public void set_tenderId( long value ) {
        _tenderId = value;
    }

    /**
    * Returns the value of the "tenderId" parameter of this interaction.
    *
    * @return the value of the "tenderId" parameter
    */
    public long get_tenderId() {
        return _tenderId;
    }

    protected Tender( ReceivedInteraction datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    protected Tender( ReceivedInteraction datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    /**
    * Creates an instance of the Tender interaction class, using
    * "datamemberMap" to initialize its parameter values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new Tender interaction class instance
    */
    public Tender( ReceivedInteraction datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #Tender( ReceivedInteraction datamemberMap )}, except this
    * new Tender interaction class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new Tender interaction class instance
    * @param logicalTime timestamp for this new Tender interaction class
    * instance
    */
    public Tender( ReceivedInteraction datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new Tender interaction class instance that is a duplicate
    * of the instance referred to by Tender_var.
    *
    * @param Tender_var Tender interaction class instance of which
    * this newly created Tender interaction class instance will be a
    * duplicate
    */
    public Tender( Tender Tender_var ) {
        super( Tender_var );

        set_durationInMinutes( Tender_var.get_durationInMinutes() );
        set_expireTime( Tender_var.get_expireTime() );
        set_partyId( Tender_var.get_partyId() );
        set_price( Tender_var.get_price() );
        set_quantity( Tender_var.get_quantity() );
        set_side( Tender_var.get_side() );
        set_startTime( Tender_var.get_startTime() );
        set_tenderId( Tender_var.get_tenderId() );
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
        if ( "durationInMinutes".equals(datamemberName) ) return new Long(get_durationInMinutes());
        else if ( "expireTime".equals(datamemberName) ) return get_expireTime();
        else if ( "partyId".equals(datamemberName) ) return get_partyId();
        else if ( "price".equals(datamemberName) ) return new Double(get_price());
        else if ( "quantity".equals(datamemberName) ) return new Double(get_quantity());
        else if ( "side".equals(datamemberName) ) return new Character(get_side());
        else if ( "startTime".equals(datamemberName) ) return get_startTime();
        else if ( "tenderId".equals(datamemberName) ) return new Long(get_tenderId());
        else return super.getParameter( datamemberName );
    }

    protected boolean setParameterAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "durationInMinutes".equals( datamemberName) ) set_durationInMinutes( Long.parseLong(val) );
        else if ( "expireTime".equals( datamemberName) ) set_expireTime( val );
        else if ( "partyId".equals( datamemberName) ) set_partyId( val );
        else if ( "price".equals( datamemberName) ) set_price( Double.parseDouble(val) );
        else if ( "quantity".equals( datamemberName) ) set_quantity( Double.parseDouble(val) );
        else if ( "side".equals( datamemberName) ) set_side( val.charAt(0) );
        else if ( "startTime".equals( datamemberName) ) set_startTime( val );
        else if ( "tenderId".equals( datamemberName) ) set_tenderId( Long.parseLong(val) );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    protected boolean setParameterAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "durationInMinutes".equals( datamemberName) ) set_durationInMinutes( (Long)val );
        else if ( "expireTime".equals( datamemberName) ) set_expireTime( (String)val );
        else if ( "partyId".equals( datamemberName) ) set_partyId( (String)val );
        else if ( "price".equals( datamemberName) ) set_price( (Double)val );
        else if ( "quantity".equals( datamemberName) ) set_quantity( (Double)val );
        else if ( "side".equals( datamemberName) ) set_side( (Character)val );
        else if ( "startTime".equals( datamemberName) ) set_startTime( (String)val );
        else if ( "tenderId".equals( datamemberName) ) set_tenderId( (Long)val );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof Tender ) {
            Tender data = (Tender)object;
            _durationInMinutes = data._durationInMinutes;
            _expireTime = data._expireTime;
            _partyId = data._partyId;
            _price = data._price;
            _quantity = data._quantity;
            _side = data._side;
            _startTime = data._startTime;
            _tenderId = data._tenderId;
        }
    }
}

