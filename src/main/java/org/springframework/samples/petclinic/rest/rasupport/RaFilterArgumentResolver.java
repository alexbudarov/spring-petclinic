package org.springframework.samples.petclinic.rest.rasupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class RaFilterArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper;

    public RaFilterArgumentResolver(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(RaFilter.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {
        Class<?> parameterClass = parameter.getParameterType();

        String filterParam = webRequest.getParameter("filter");
        if (filterParam == null) {
            return parseFilterAsJson("{}", parameterClass);
        }

        Object result = parseFilterAsJson(filterParam, parameterClass);
        return result;
    }

    private Object parseFilterAsJson(String filter, Class<?> parameterClass) {
        try {
            Object result = objectMapper.readValue(filter, parameterClass);
            return result;
        } catch (JsonProcessingException e) {
            throw new JsonConversionException("Invalid filter format: " + filter, e);
        }
    }
}
