package org.springframework.samples.petclinic.rest.rasupport;

import org.springframework.data.domain.Sort;

/**
 * Represents "sort" argument of getList() operation.
 * The argument will be resolved using conventions of the React Admin ra-data-simple-rest data provider.
 *
 * @see RaSortArgumentResolver
 */
public class RaSort {
    private final String property;
    private final Sort.Direction direction;
    private final boolean specified;

    public RaSort(String property, Sort.Direction direction, boolean specified) {
        this.property = property;
        this.direction = direction;
        this.specified = specified;
    }

    public static RaSort empty() {
        return new RaSort(null, null, false);
    }

    public String getProperty() {
        return property;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public boolean isSpecified() {
        return specified;
    }

    /**
     * Convert to Sort object for passing to Spring Data repository.
     */
    public Sort toSort() {
        if (!specified) {
            return Sort.unsorted();
        }
        return Sort.by(direction, property);
    }
}
