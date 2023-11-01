package org.springframework.samples.petclinic.rest.rasupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ReflexiveRaFilterArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper;

    public ReflexiveRaFilterArgumentResolver(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(ReactAdminFilter.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
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
            throw new RuntimeException("Invalid filter format", e);
        }
    }
}
