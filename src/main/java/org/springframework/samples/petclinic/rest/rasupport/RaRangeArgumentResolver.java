package org.springframework.samples.petclinic.rest.rasupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class RaRangeArgumentResolver implements HandlerMethodArgumentResolver {
    private final static Logger log = LoggerFactory.getLogger(RaRangeArgumentResolver.class);

    @Value("${ra.range.defaultPageSize:100}")
    private int defaultPageSize;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return RaRange.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
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
            // todo decide, throw bad request or silently handle
            log.debug("Invalid range format: " + range, e);
            return RaRange.empty(defaultPageSize);
        }

        if (integers.length != 2) {
            log.debug("Invalid range format: " + range);
            return RaRange.empty(defaultPageSize);
        }

        int rangeStart = integers[0];
        int rangeEnd = integers[1];

        return new RaRange(rangeStart, rangeEnd, true, defaultPageSize);
    }
}
