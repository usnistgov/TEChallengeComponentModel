package gov.nist.hla.gridlabd;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.ieee.standards.ieee1516._2010.AttributeType;
import org.ieee.standards.ieee1516._2010.InteractionClassType;
import org.ieee.standards.ieee1516._2010.ObjectClassType;
import org.ieee.standards.ieee1516._2010.ParameterType;

import gov.nist.hla.gateway.ObjectModel;
import gov.nist.pages.ucef.AttributeConfigType;
import gov.nist.pages.ucef.InteractionClassConfigType;
import gov.nist.pages.ucef.LinearConversionType;
import gov.nist.pages.ucef.ObjectClassConfigType;
import gov.nist.pages.ucef.ParameterConfigType;

public class ObjectModelHelper {
    private static final Logger log = LogManager.getLogger();
    
    private final ObjectModel objectModel;
    
    private Set<String> rootAttributes = new HashSet<String>();
    
    private Set<String> rootParameters = new HashSet<String>();
    
    public ObjectModelHelper(ObjectModel objectModel) {
        this.objectModel = objectModel;
        
        ObjectClassType objectRoot = objectModel.getObject("ObjectRoot");
        for (AttributeType attribute : objectModel.getAttributes(objectRoot)) {
            rootAttributes.add(attribute.getName().getValue());
        }
        log.debug("rootAttributes {}", rootAttributes.toString());
        
        InteractionClassType interactionRoot = objectModel.getInteraction("InteractionRoot.C2WInteractionRoot");
        for (ParameterType parameter : objectModel.getParameters(interactionRoot)) {
            rootParameters.add(parameter.getName().getValue());
        }
        log.debug("rootParameters {}", rootParameters.toString());
    }
    
    public boolean isGlobalVariable(ObjectClassType object) {
        log.trace("isGlobalVariable {}", objectModel.getClassPath(object));
        return objectModel.getAttribute(object, "name") == null;
    }
    
    public boolean isGlobalVariable(InteractionClassType interaction) {
        log.trace("isGlobalVariable {}", objectModel.getClassPath(interaction));
        return objectModel.getParameter(interaction, "name") == null;
    }
    
    public boolean isRootParameter(String parameterName) {
        log.trace("isRootParameter {}", parameterName);
        return rootParameters.contains(parameterName);
    }
    
    public boolean isRootAttribute(String attributeName) {
        log.trace("isRootAttribute {}", attributeName);
        return rootAttributes.contains(attributeName);
    }
    
    public boolean isSubscribed(String classPath) {
        log.trace("isSubscribed {}", classPath);
        
        if (classPath.startsWith("InteractionRoot")) {
            log.trace("interaction class");
            InteractionClassType interaction = objectModel.getInteraction(classPath);
            return objectModel.getSubscribedInteractions().contains(interaction);
        }
        if (classPath.startsWith("ObjectRoot")) {
            log.trace("object class");
            ObjectClassType object = objectModel.getObject(classPath);
            return objectModel.getSubscribedObjects().contains(object);
        }
        log.warn("bad class path {}", classPath);
        return false;
    }
    
    public boolean isPublished(String classPath) {
        log.trace("isPublished {}", classPath);
        
        if (classPath.startsWith("InteractionRoot")) {
            log.trace("interaction class");
            InteractionClassType interaction = objectModel.getInteraction(classPath);
            return objectModel.getPublishedInteractions().contains(interaction);
        }
        if (classPath.startsWith("ObjectRoot")) {
            log.trace("object class");
            ObjectClassType object = objectModel.getObject(classPath);
            return objectModel.getPublishedObjects().contains(object);
        }
        log.warn("bad class path {}", classPath);
        return false;
    }
    
    public Set<String> getPublishedNames(InteractionClassType interaction) {
        log.trace("getPublishedNames {}", objectModel.getClassPath(interaction));
        
        Set<String> publishedNames = new HashSet<String>();
        
        for (FeatureMap.Entry feature : interaction.getAny()) {
            if (feature.getValue() instanceof InteractionClassConfigType) {
                InteractionClassConfigType interactionConfig = (InteractionClassConfigType) feature.getValue();
                
                if (interactionConfig.getPublishedObjects() == null) {
                    log.debug("no GridLAB-D objects defined for {}", objectModel.getClassPath(interaction));
                } else {
                    publishedNames.addAll(interactionConfig.getPublishedObjects().getObjectName());
                }
                return publishedNames;
            }
        }
        log.debug("no interaction details for {}", objectModel.getClassPath(interaction));
        return publishedNames;
    }
    
    public Set<String> getPublishedNames(ObjectClassType object) {
        log.trace("getPublishedNames {}", objectModel.getClassPath(object));
        
        Set<String> publishedNames = new HashSet<String>();
        
        for (FeatureMap.Entry feature : object.getAny()) {
            if (feature.getValue() instanceof ObjectClassConfigType) {
                ObjectClassConfigType objectConfig = (ObjectClassConfigType) feature.getValue();
                
                if (objectConfig.getPublishedObjects() == null) {
                    log.debug("no GridLAB-D objects defined for {}", objectModel.getClassPath(object));
                } else {
                    publishedNames.addAll(objectConfig.getPublishedObjects().getObjectName());
                }
                return publishedNames;
            }
        }
        log.debug("no object details for {}", objectModel.getClassPath(object));
        return publishedNames;
    }
    
    public String getNameConversion(AttributeType attribute) {
        log.trace("getNameConversion {}", attribute.getName().getValue());
        
        if (!isDouble(attribute)) {
            log.debug("skipped unit conversion: unsupported data type");
            return null;
        }
        
        for (FeatureMap.Entry feature : attribute.getAny()) {
            if (feature.getValue() instanceof AttributeConfigType) {
                AttributeConfigType attributeConfig = (AttributeConfigType) feature.getValue();
                
                if (attributeConfig.getUnitConversion() != null) {
                    log.trace("found unit conversion rule");
                    return attributeConfig.getUnitConversion().getNameConversion();
                }
            }
        }
        log.debug("skipped unit conversion: no conversion rule");
        return null;
    }
    
    public String getNameConversion(ParameterType parameter) {
        log.trace("getNameConversion {}", parameter.getName().getValue());
        
        if (!isDouble(parameter)) {
            log.debug("skipped unit conversion: unsupported data type");
            return null;
        }
        
        for (FeatureMap.Entry feature : parameter.getAny()) {
            if (feature.getValue() instanceof ParameterConfigType) {
                ParameterConfigType parameterConfig = (ParameterConfigType) feature.getValue();
                
                if (parameterConfig.getUnitConversion() != null) {
                    log.trace("found unit conversion rule");
                    return parameterConfig.getUnitConversion().getNameConversion();
                }
            }
        }
        log.debug("skipped unit conversion: no conversion rule");
        return null;
    }
    
    public LinearConversionType getLinearConversion(AttributeType attribute) {
        log.trace("getLinearConversion {}", attribute.getName().getValue());
        
        if (!isDouble(attribute)) {
            log.debug("skipped unit conversion: unsupported data type");
            return null;
        }
        
        for (FeatureMap.Entry feature : attribute.getAny()) {
            if (feature.getValue() instanceof AttributeConfigType) {
                AttributeConfigType attributeConfig = (AttributeConfigType) feature.getValue();
                
                if (attributeConfig.getUnitConversion() != null) {
                    log.trace("found unit conversion rule");
                    return attributeConfig.getUnitConversion().getLinearConversion();
                }
            }
        }
        log.debug("skipped unit conversion: no conversion rule");
        return null;
    }
    
    public LinearConversionType getLinearConversion(ParameterType parameter) {
        log.trace("getLinearConversion {}", parameter.getName().getValue());
        
        if (!isDouble(parameter)) {
            log.debug("skipped unit conversion: unsupported data type");
            return null;
        }
        
        for (FeatureMap.Entry feature : parameter.getAny()) {
            if (feature.getValue() instanceof ParameterConfigType) {
                ParameterConfigType parameterConfig = (ParameterConfigType) feature.getValue();
                
                if (parameterConfig.getUnitConversion() != null) {
                    log.trace("found unit conversion rule");
                    return parameterConfig.getUnitConversion().getLinearConversion();
                }
            }
        }
        log.debug("skipped unit conversion: no conversion rule");
        return null;
    }
    
    public double getUpdatePeriod(AttributeType attribute) {
        log.trace("getUpdatePeriod {}", attribute.getName().getValue());
        
        for (FeatureMap.Entry feature : attribute.getAny()) {
            if (feature.getValue() instanceof AttributeConfigType) {
                AttributeConfigType attributeConfig = (AttributeConfigType) feature.getValue();
                
                if (attributeConfig.isSetUpdatePeriod()) {
                    return attributeConfig.getUpdatePeriod();
                }
            }
        }
        return -1;
    }
    
    public double getUpdatePeriod(InteractionClassType interaction) {
        log.trace("getUpdatePeriod {}", objectModel.getClassPath(interaction));
        
        for (FeatureMap.Entry feature : interaction.getAny()) {
            if (feature.getValue() instanceof InteractionClassConfigType) {
                InteractionClassConfigType interactionConfig = (InteractionClassConfigType) feature.getValue();
                
                if (interactionConfig.isSetUpdatePeriod()) {
                    return interactionConfig.getUpdatePeriod();
                }
            }
        }
        return -1;
    }
    
    public boolean isDouble(ParameterType parameter) {
        log.trace("isDouble {}", parameter.getName().getValue());
        final String dataType = parameter.getDataType().getValue();
        return dataType.equals("double"); // float not supported in GridLAB-D
    }
    
    public boolean isDouble(AttributeType attribute) {
        log.trace("isDouble {}", attribute.getName().getValue());
        final String dataType = attribute.getDataType().getValue();
        return dataType.equals("double"); // float not supported in GridLAB-D
    }
}
