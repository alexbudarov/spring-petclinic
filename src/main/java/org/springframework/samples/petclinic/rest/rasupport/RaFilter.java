package org.springframework.samples.petclinic.rest.rasupport;

import java.util.Collections;
import java.util.Map;

public class RaFilter {
    public final Map<String, Object> parameters;

    public RaFilter(Map<String, Object> parameters) {
        this.parameters = Collections.unmodifiableMap(parameters);
    }
}
