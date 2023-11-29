package org.springframework.samples.petclinic.rest.rasupport;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RaRangeParam {

    boolean allowUnpaged() default false;

    int defaultPageSize() default 10000;
}
