package org.springframework.samples.petclinic.rest.rasupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class RaSortArgumentResolver implements HandlerMethodArgumentResolver {
    private final static Logger log = LoggerFactory.getLogger(RaSortArgumentResolver.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return RaSort.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (!RaSort.class.equals(parameter.getParameterType())) {
            return null;
        }

        String sort = webRequest.getParameter("sort");

        if (sort == null || sort.equals("[]")) {
            return RaSort.empty();
        }

        String[] strings;
        try {
            strings = objectMapper.readValue(sort, String[].class);
        } catch (JsonProcessingException e) {
            // todo decide, throw bad request or silently handle
            log.debug("Invalid sort format", e);
            return RaSort.empty();
        }

        if (strings.length != 2) {
            log.debug("Invalid sort format: " + sort);
            return RaSort.empty();
        }

        String field = strings[0];
        Sort.Direction direction = Sort.Direction.fromString(strings[1]);

        return new RaSort(field, direction, true);
    }
}
