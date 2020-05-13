package gov.nist.hla.te.parity.paritysystem.rti;

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
* Implements InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder
*/
public class EnterOrder extends ParitySystem {

    private static final Logger logger = LogManager.getLogger();

    /**
    * Creates an instance of the EnterOrder interaction class with default parameter values.
    */
    public EnterOrder() {}

    private static int _actualLogicalGenerationTime_handle;
    private static int _federateFilter_handle;
    private static int _instrument_handle;
    private static int _orderId_handle;
    private static int _originFed_handle;
    private static int _price_handle;
    private static int _quantity_handle;
    private static int _side_handle;
    private static int _sourceFed_handle;
    private static int _username_handle;

    private static boolean _isInitialized = false;

    private static int _handle;

    /**
    * Returns the handle (RTI assigned) of the EnterOrder interaction class.
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
    * Returns the fully-qualified (dot-delimited) name of the EnterOrder interaction class.
    * Note: As this is a static method, it is NOT polymorphic, and so, if called on
    * a reference will return the name of the class pertaining to the reference,
    * rather than the name of the class for the instance referred to by the reference.
    * For the polymorphic version of this method, use {@link #getClassName()}.
    *
    * @return the fully-qualified HLA class path for this interaction class
    */
    public static String get_class_name() {
        return "InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder";
    }

    /**
    * Returns the simple name (the last name in the dot-delimited fully-qualified
    * class name) of the EnterOrder interaction class.
    *
    * @return the name of this interaction class
    */
    public static String get_simple_class_name() {
        return "EnterOrder";
    }

    private static Set< String > _datamemberNames = new HashSet< String >();
    private static Set< String > _allDatamemberNames = new HashSet< String >();

    /**
    * Returns a set containing the names of all of the non-hidden parameters in the
    * EnterOrder interaction class.
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
    * EnterOrder interaction class.
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
        _classNameSet.add("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder");
        _classNameClassMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder", EnterOrder.class);

        _datamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder", _datamemberNames);
        _allDatamemberClassNameSetMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder", _allDatamemberNames);

        _datamemberNames.add("instrument");
        _datamemberNames.add("orderId");
        _datamemberNames.add("price");
        _datamemberNames.add("quantity");
        _datamemberNames.add("side");

        _datamemberTypeMap.put("instrument", "String");
        _datamemberTypeMap.put("orderId", "String");
        _datamemberTypeMap.put("price", "long");
        _datamemberTypeMap.put("quantity", "long");
        _datamemberTypeMap.put("side", "char");

        _allDatamemberNames.add("actualLogicalGenerationTime");
        _allDatamemberNames.add("federateFilter");
        _allDatamemberNames.add("instrument");
        _allDatamemberNames.add("orderId");
        _allDatamemberNames.add("originFed");
        _allDatamemberNames.add("price");
        _allDatamemberNames.add("quantity");
        _allDatamemberNames.add("side");
        _allDatamemberNames.add("sourceFed");
        _allDatamemberNames.add("username");
    }

    protected static void init(RTIambassador rti) {
        if (_isInitialized) return;
        _isInitialized = true;

        ParitySystem.init(rti);

        boolean isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _handle = rti.getInteractionClassHandle("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder");
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

        _classNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder", get_handle());
        _classHandleNameMap.put(get_handle(), "InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder");
        _classHandleSimpleNameMap.put(get_handle(), "EnterOrder");

        isNotInitialized = true;
        while(isNotInitialized) {
            try {
                _actualLogicalGenerationTime_handle = rti.getParameterHandle("actualLogicalGenerationTime", get_handle());
                _federateFilter_handle = rti.getParameterHandle("federateFilter", get_handle());
                _instrument_handle = rti.getParameterHandle("instrument", get_handle());
                _orderId_handle = rti.getParameterHandle("orderId", get_handle());
                _originFed_handle = rti.getParameterHandle("originFed", get_handle());
                _price_handle = rti.getParameterHandle("price", get_handle());
                _quantity_handle = rti.getParameterHandle("quantity", get_handle());
                _side_handle = rti.getParameterHandle("side", get_handle());
                _sourceFed_handle = rti.getParameterHandle("sourceFed", get_handle());
                _username_handle = rti.getParameterHandle("username", get_handle());
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

        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder.actualLogicalGenerationTime", _actualLogicalGenerationTime_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder.federateFilter", _federateFilter_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder.instrument", _instrument_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder.orderId", _orderId_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder.originFed", _originFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder.price", _price_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder.quantity", _quantity_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder.side", _side_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder.sourceFed", _sourceFed_handle);
        _datamemberNameHandleMap.put("InteractionRoot.C2WInteractionRoot.ParitySystem.EnterOrder.username", _username_handle);

        _datamemberHandleNameMap.put(_actualLogicalGenerationTime_handle, "actualLogicalGenerationTime");
        _datamemberHandleNameMap.put(_federateFilter_handle, "federateFilter");
        _datamemberHandleNameMap.put(_instrument_handle, "instrument");
        _datamemberHandleNameMap.put(_orderId_handle, "orderId");
        _datamemberHandleNameMap.put(_originFed_handle, "originFed");
        _datamemberHandleNameMap.put(_price_handle, "price");
        _datamemberHandleNameMap.put(_quantity_handle, "quantity");
        _datamemberHandleNameMap.put(_side_handle, "side");
        _datamemberHandleNameMap.put(_sourceFed_handle, "sourceFed");
        _datamemberHandleNameMap.put(_username_handle, "username");
    }

    private static boolean _isPublished = false;

    /**
    * Publishes the EnterOrder interaction class for a federate.
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
    * Unpublishes the EnterOrder interaction class for a federate.
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
    * Subscribes a federate to the EnterOrder interaction class.
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
    * Unsubscribes a federate from the EnterOrder interaction class.
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
    * (that is, the EnterOrder interaction class).
    *
    * @param handle handle to compare to the value of the handle (RTI assigned) of
    * this class (the EnterOrder interaction class).
    * @return "true" if "handle" matches the value of the handle of this class
    * (that is, the EnterOrder interaction class).
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
        else if (datamemberHandle == _instrument_handle) return "instrument";
        else if (datamemberHandle == _orderId_handle) return "orderId";
        else if (datamemberHandle == _originFed_handle) return "originFed";
        else if (datamemberHandle == _price_handle) return "price";
        else if (datamemberHandle == _quantity_handle) return "quantity";
        else if (datamemberHandle == _side_handle) return "side";
        else if (datamemberHandle == _sourceFed_handle) return "sourceFed";
        else if (datamemberHandle == _username_handle) return "username";
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
                + "," + "instrument:" + get_instrument()
                + "," + "orderId:" + get_orderId()
                + "," + "originFed:" + get_originFed()
                + "," + "price:" + get_price()
                + "," + "quantity:" + get_quantity()
                + "," + "side:" + get_side()
                + "," + "sourceFed:" + get_sourceFed()
                + "," + "username:" + get_username()
                + ")";
    }

    private String _instrument = "";
    private String _orderId = "";
    private long _price = 0;
    private long _quantity = 0;
    private char _side = '\0';

    /**
    * Set the value of the "instrument" parameter to "value" for this parameter.
    *
    * @param value the new value for the "instrument" parameter
    */
    public void set_instrument( String value ) {
        _instrument = value;
    }

    /**
    * Returns the value of the "instrument" parameter of this interaction.
    *
    * @return the value of the "instrument" parameter
    */
    public String get_instrument() {
        return _instrument;
    }
    /**
    * Set the value of the "orderId" parameter to "value" for this parameter.
    *
    * @param value the new value for the "orderId" parameter
    */
    public void set_orderId( String value ) {
        _orderId = value;
    }

    /**
    * Returns the value of the "orderId" parameter of this interaction.
    *
    * @return the value of the "orderId" parameter
    */
    public String get_orderId() {
        return _orderId;
    }
    /**
    * Set the value of the "price" parameter to "value" for this parameter.
    *
    * @param value the new value for the "price" parameter
    */
    public void set_price( long value ) {
        _price = value;
    }

    /**
    * Returns the value of the "price" parameter of this interaction.
    *
    * @return the value of the "price" parameter
    */
    public long get_price() {
        return _price;
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

    protected EnterOrder( ReceivedInteraction datamemberMap, boolean initFlag ) {
        super( datamemberMap, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    protected EnterOrder( ReceivedInteraction datamemberMap, LogicalTime logicalTime, boolean initFlag ) {
        super( datamemberMap, logicalTime, false );
        if ( initFlag ) setParameters( datamemberMap );
    }

    /**
    * Creates an instance of the EnterOrder interaction class, using
    * "datamemberMap" to initialize its parameter values.
    * "datamemberMap" is usually acquired as an argument to an RTI federate
    * callback method, such as "receiveInteraction".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new EnterOrder interaction class instance
    */
    public EnterOrder( ReceivedInteraction datamemberMap ) {
        this( datamemberMap, true );
    }

    /**
    * Like {@link #EnterOrder( ReceivedInteraction datamemberMap )}, except this
    * new EnterOrder interaction class instance is given a timestamp of
    * "logicalTime".
    *
    * @param datamemberMap data structure containing initial values for the
    * parameters of this new EnterOrder interaction class instance
    * @param logicalTime timestamp for this new EnterOrder interaction class
    * instance
    */
    public EnterOrder( ReceivedInteraction datamemberMap, LogicalTime logicalTime ) {
        this( datamemberMap, logicalTime, true );
    }

    /**
    * Creates a new EnterOrder interaction class instance that is a duplicate
    * of the instance referred to by EnterOrder_var.
    *
    * @param EnterOrder_var EnterOrder interaction class instance of which
    * this newly created EnterOrder interaction class instance will be a
    * duplicate
    */
    public EnterOrder( EnterOrder EnterOrder_var ) {
        super( EnterOrder_var );

        set_instrument( EnterOrder_var.get_instrument() );
        set_orderId( EnterOrder_var.get_orderId() );
        set_price( EnterOrder_var.get_price() );
        set_quantity( EnterOrder_var.get_quantity() );
        set_side( EnterOrder_var.get_side() );
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
        if ( "instrument".equals(datamemberName) ) return get_instrument();
        else if ( "orderId".equals(datamemberName) ) return get_orderId();
        else if ( "price".equals(datamemberName) ) return new Long(get_price());
        else if ( "quantity".equals(datamemberName) ) return new Long(get_quantity());
        else if ( "side".equals(datamemberName) ) return new Character(get_side());
        else return super.getParameter( datamemberName );
    }

    protected boolean setParameterAux( String datamemberName, String val ) {
        boolean retval = true;
        if ( "instrument".equals( datamemberName) ) set_instrument( val );
        else if ( "orderId".equals( datamemberName) ) set_orderId( val );
        else if ( "price".equals( datamemberName) ) set_price( Long.parseLong(val) );
        else if ( "quantity".equals( datamemberName) ) set_quantity( Long.parseLong(val) );
        else if ( "side".equals( datamemberName) ) set_side( val.charAt(0) );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    protected boolean setParameterAux( String datamemberName, Object val ) {
        boolean retval = true;
        if ( "instrument".equals( datamemberName) ) set_instrument( (String)val );
        else if ( "orderId".equals( datamemberName) ) set_orderId( (String)val );
        else if ( "price".equals( datamemberName) ) set_price( (Long)val );
        else if ( "quantity".equals( datamemberName) ) set_quantity( (Long)val );
        else if ( "side".equals( datamemberName) ) set_side( (Character)val );
        else retval = super.setParameterAux( datamemberName, val );

        return retval;
    }

    public void copyFrom( Object object ) {
        super.copyFrom( object );
        if ( object instanceof EnterOrder ) {
            EnterOrder data = (EnterOrder)object;
            _instrument = data._instrument;
            _orderId = data._orderId;
            _price = data._price;
            _quantity = data._quantity;
            _side = data._side;
        }
    }
}

