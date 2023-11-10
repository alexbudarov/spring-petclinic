package org.springframework.samples.petclinic.rest.rasupport;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RaPatchUtil {

    private final ObjectMapper objectMapper;

    public RaPatchUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SuppressWarnings("unchecked")
    public <T> T patch(String patchJson, T target) {
        // 1. deserialize json into DTO_2
        // 2. deserialize json into Set of patched property names
        // 3. gather Map of properties - take from target
        // 4. patch Map of properties - copy from DTO_2 only those that existed in json
        // 5. construct DTO_3 from patched properties.
        // 6. return DTO_3 as a result.

        // todo cache beanDescription

        BeanDescription beanDescription = getBeanDescription(target.getClass());
        List<BeanPropertyDefinition> beanProperties = beanDescription.findProperties();

        // optimisation for safely mutable beans
        if (canBePatchedInPlace(beanDescription)) {
            patchBeanInPlace(patchJson, target);
            return target;
        }

        // 1.
        T patchObject;
        try {
            patchObject = (T) objectMapper.readValue(patchJson, target.getClass());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 2.
        Set<String> patchedPropertyNames = determinePatchedProperties(patchJson);

        // 3.
        Map<String, Object> allPropertyValues = extractPropertyValues(target, beanProperties);

        // 4.
        for (String patchedPropertyName: patchedPropertyNames) {
            Object patchedValue = beanProperties.stream()
                    .filter(p -> p.getName().equals(patchedPropertyName) && p.hasGetter())
                    .findAny()
                    .map(def -> def.getGetter().getValue(patchObject))
                    .orElseThrow();
            allPropertyValues.put(patchedPropertyName, patchedValue);
        }

        // 5.
        T patchedObject = constructBean(allPropertyValues, beanDescription);

        return patchedObject;
    }

    private <T> void patchBeanInPlace(String patchJson, T target) {
        try {
            objectMapper.readerForUpdating(target).readValue(patchJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean canBePatchedInPlace(BeanDescription beanDescription) {
        // Consider the bean - able to be patched in place
        // if it does NOT contain properties
        //   that CAN be set via constructor, but NOT via setter.
        // If the property doesn't have neither constructor nor setter - than it is read-only and doesn't interest us.
        return beanDescription.findProperties().stream()
                .noneMatch(p -> p.hasConstructorParameter() && !p.hasSetter());
    }

    private <T> T constructBean(Map<String, Object> propertyValues, BeanDescription beanDescription)  {
        try {
            JsonParser parser = objectMapper.createParser("{}");// dummy

            DeserializationContext ctxt = objectMapper.getDeserializationContext();
            BeanDeserializer deser = (BeanDeserializer) ctxt.findRootValueDeserializer(beanDescription.getType());

            ValueInstantiator valueInstantiator = deser.getValueInstantiator();
            SettableBeanProperty[] creatorProps = valueInstantiator.getFromObjectArguments(ctxt.getConfig());

            BeanPropertyMap beanProperties = null; // todo
            PropertyBasedCreator creator = PropertyBasedCreator.construct(ctxt, valueInstantiator, creatorProps, beanProperties);

            PropertyValueBuffer buffer = creator.startBuilding(parser, ctxt, null);

            // todo final SettableBeanProperty creatorProp = creator.findCreatorProperty(propName);
            // todo buffer.assignParameter(creatorProp, value)
            // todo buffer.bufferProperty(prop, _deserializeWithErrorWrapping(p, ctxt, prop));
            T bean = (T) creator.build(ctxt, buffer);
            return bean;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BeanDescription getBeanDescription(Class<?> targetClass) {
        DeserializationConfig config = objectMapper.getDeserializationConfig();
        ClassIntrospector introspector = config.getClassIntrospector();
        BeanDescription description = introspector.forDeserialization(config, objectMapper.constructType(targetClass),
                config);
        return description;
    }

    private Set<String> determinePatchedProperties(String patchJson) {
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(patchJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Set<String> propertyNames = new HashSet<>();
        for (var it = jsonNode.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> entry = it.next();
            propertyNames.add(entry.getKey());
        }
        return propertyNames;
    }

    private <T> Map<String, Object> extractPropertyValues(T bean, List<BeanPropertyDefinition> beanProperties) {
        return beanProperties.stream()
                .filter(def -> def.hasGetter())
                .collect(Collectors.toMap(
                        def -> def.getName(),
                        def -> def.getGetter().getValue(bean)
                ));
    }

}
