package org.springframework.samples.petclinic.rest.rasupport.springdoc;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.discoverer.SpringDocParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.samples.petclinic.rest.rasupport.RaFilter;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeParam;
import org.springframework.samples.petclinic.rest.rasupport.RaSortParam;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Component
public class RaArgumentsOperationCustomizer implements OperationCustomizer {

    private final ParameterNameDiscoverer parameterNameDiscoverer;

    private final StringSchema stringSchema = new StringSchema();

    private final ObjectMapper objectMapper;

    public RaArgumentsOperationCustomizer(SpringDocParameterNameDiscoverer parameterNameDiscoverer, ObjectMapper objectMapper) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
        this.objectMapper = objectMapper;
    }

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();

        for (MethodParameter mp : methodParameters) {
            if (mp.getParameterAnnotation(RaFilter.class) != null) {
                customizeFilter(operation, mp);
            } else if (mp.getParameterAnnotation(RaRangeParam.class) != null) {
                customizeRange(operation, mp);
                if (mp.getParameterAnnotation(RaSortParam.class) != null) {
                    addSort(operation, mp);
                }
            }
            if (mp.getParameterAnnotation(RaSortParam.class) != null) {
                customizeSort(operation, mp);
            }
            // todo support RaSort, RaRange
        }
        return operation;
    }

    private void customizeRange(Operation operation, MethodParameter methodParam) {
        Parameter parameter = findParameterByMethodParam(operation, methodParam);
        if (parameter == null) {
            return;
        }
        parameter.setName("range");
        parameter.setSchema(stringSchema);
        parameter.setRequired(false);
        parameter.setExample("[0,4]");
    }

    private void customizeSort(Operation operation, MethodParameter methodParam) {
        Parameter parameter = findParameterByMethodParam(operation, methodParam);
        if (parameter == null) {
            return;
        }
        parameter.setName("sort");
        parameter.setSchema(stringSchema);
        parameter.setRequired(false);
        parameter.setExample("[\"id\", \"DESC\"]"); // todo take sample from entity
    }

    private void addSort(Operation operation, MethodParameter methodParam) {
        // also add sort param
        Parameter additionalParam = new Parameter()
                .name("sort")
                .schema(stringSchema)
                .required(false)
                .example("[\"id\", \"DESC\"]")
                .in("query");

        operation.addParametersItem(additionalParam);
    }

    private void customizeFilter(Operation operation, MethodParameter methodParam) {
        Parameter parameter = findParameterByMethodParam(operation, methodParam);
        if (parameter == null) {
            return;
        }
        parameter.setSchema(stringSchema);
        parameter.setRequired(false);
        parameter.setExample(createExample(methodParam.getParameterType()));
    }

    private Parameter findParameterByMethodParam(Operation operation, MethodParameter parameter) {
        String paramName = getOrDiscoverParamName(parameter);
        if (paramName == null) {
            return null;
        }

        Parameter swaggerParameter = operation.getParameters().stream()
                .filter(p -> p.getName().equals(paramName))
                .findFirst()
                .orElse(null);
        return swaggerParameter;
    }

    private String getOrDiscoverParamName(MethodParameter parameter) {
        // todo probably discoverer should be globally configured
        String paramName = parameter.getParameterName();
        if (paramName == null) {
            String[] parameterNames = null;
            if (parameter.getExecutable() instanceof Method method) {
                parameterNames = parameterNameDiscoverer.getParameterNames(method);
            }
            else if (parameter.getExecutable() instanceof Constructor<?> constructor) {
                parameterNames = parameterNameDiscoverer.getParameterNames(constructor);
            }
            if (parameterNames != null) {
                paramName = parameterNames[parameter.getParameterIndex()];
            }
        }
        return paramName;
    }

    private Object createExample(Class<?> parameterType) {
        // todo use some existing method from swagger / springdoc-openapi instead
        BeanDescription beanDescription = getBeanDescription(parameterType);
        AnnotatedConstructor constructor = beanDescription.getConstructors().get(0);
        Object[] args = new Object[constructor.getParameterCount()];
        for (int i = 0; i < constructor.getParameterCount(); i++) {
            JavaType type = constructor.getParameterType(i);
            if (type.isPrimitive()) {
                args[i] = getPrimitive(type);
            } else if (type.isArrayType()) {
                Object array = Array.newInstance(type.getContentType().getRawClass(), 1);
                Object arrayElement = getPrimitive(type.getContentType());
                Array.set(array, 0, arrayElement);
                args[i] = array;
            }
        }
        try {
            Object exampleObject = constructor.call(args);
            ObjectWriter writer = objectMapper.writer();
            return writer.writeValueAsString(exampleObject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object getPrimitive(JavaType type) {
        if (String.class.isAssignableFrom(type.getRawClass())) {
            return "string";
        } else if (Number.class.isAssignableFrom(type.getRawClass())) {
            return 0;
        } else if (Boolean.class.isAssignableFrom(type.getRawClass())) {
            return false;
        }
        return null;
    }

    private BeanDescription getBeanDescription(Class<?> targetClass) {
        DeserializationConfig config = objectMapper.getDeserializationConfig();
        ClassIntrospector introspector = config.getClassIntrospector();
        BeanDescription description = introspector.forDeserialization(config, objectMapper.constructType(targetClass),
                config);
        return description;
    }

}