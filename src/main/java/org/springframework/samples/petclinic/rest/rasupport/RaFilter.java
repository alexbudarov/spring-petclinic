package org.springframework.samples.petclinic.rest.rasupport;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks the filter argument of getList() operation.
 * The argument will be resolved using conventions of the React Admin ra-data-simple-rest data provider.
 *
 * @see RaFilterArgumentResolver
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RaFilter {
}
