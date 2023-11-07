package org.springframework.samples.petclinic.rest.rasupport;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SpecFilterCondition {

    /** if empty - assumed to be the same as filter field name */
    String property() default "";

    SpecFilterOperator operator() default SpecFilterOperator.EQUALS;

    boolean ignoreCase() default false;
}
