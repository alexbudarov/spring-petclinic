package org.springframework.samples.petclinic.rest.rasupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class RaRangeArgumentResolver implements HandlerMethodArgumentResolver {
    @Value("${ra.range.defaultPageSize:100}")
    private int defaultPageSize;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return RaRange.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {
        if (!RaRange.class.equals(parameter.getParameterType())) {
            return null;
        }

        String range = webRequest.getParameter("range");

        if (range == null || range.equals("[]")) {
            return RaRange.empty(defaultPageSize);
        }

        Integer[] integers;
        try {
            integers = objectMapper.readValue(range, Integer[].class);
        } catch (JsonProcessingException e) {
			throw new JsonConversionException("Invalid range format: " + range, e);
        }

        if (integers.length != 2) {
			throw new JsonConversionException("Invalid range format, must be array of two integers: " + range);
        }

        int rangeStart = integers[0];
        int rangeEnd = integers[1];

        return new RaRange(rangeStart, rangeEnd, true, defaultPageSize);
    }
}
