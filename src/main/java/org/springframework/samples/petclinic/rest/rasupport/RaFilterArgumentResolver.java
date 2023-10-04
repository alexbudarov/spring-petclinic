package org.springframework.samples.petclinic.rest.rasupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RaFilterArgumentResolver implements HandlerMethodArgumentResolver {

    private final static Logger log = LoggerFactory.getLogger(RaFilterArgumentResolver.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return RaFilter.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        String filter = webRequest.getParameter("filter");
        if (filter == null || filter.equals("{}")) {
            return new RaFilter(Collections.emptyMap());
        }

        Map<String, Object> filterValues = parseJson(filter);
        return new RaFilter(filterValues);
    }

    private Map<String, Object> parseJson(String filter) {
        Map<String, Object> filterValues = new HashMap<>();
        JsonNode node;
        try {
            node = objectMapper.readTree(filter);
        } catch (JsonProcessingException e) {
            log.debug("Invalid sort format", e);
            return filterValues;
        }

        for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> entry = it.next();
            JsonNode value = entry.getValue();
            if (value.isValueNode()) {
                filterValues.put(entry.getKey(),
                        value.isTextual() ? value.asText() : (
                                value.isNumber() ? value.asLong() : (value.isBoolean() ? value.asBoolean() : null)
                        )
                );
            }
        }
        return filterValues;
    }
}
