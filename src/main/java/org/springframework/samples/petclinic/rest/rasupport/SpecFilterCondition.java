package org.springframework.samples.petclinic.rest.rasupport;

// not working for now, just for demonstration
public @interface SpecFilterCondition {
    String property() default "";

    SpecFilterOperator operator() default SpecFilterOperator.EQUALS;

    boolean ignoreCase() default false;
}
