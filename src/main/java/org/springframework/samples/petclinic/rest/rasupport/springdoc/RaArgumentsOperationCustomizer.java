package org.springframework.samples.petclinic.rest.rasupport.springdoc;

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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Component
public class RaArgumentsOperationCustomizer implements OperationCustomizer {

    private final ParameterNameDiscoverer parameterNameDiscoverer;

    private final StringSchema stringSchema = new StringSchema();

    public RaArgumentsOperationCustomizer(SpringDocParameterNameDiscoverer parameterNameDiscoverer) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
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
        parameter.setExample("{}"); // todo proper sample
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
}