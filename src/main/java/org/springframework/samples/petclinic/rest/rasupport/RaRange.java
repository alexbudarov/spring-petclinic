package org.springframework.samples.petclinic.rest.rasupport;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

/**
 * Represents "range" (pagination) argument of getList() operation.
 * The argument will be resolved using conventions of the React Admin ra-data-simple-rest data provider.
 *
 * @see RaRangeArgumentResolver
 */
public class RaRange {
    private final int rangeStart;
    private final int rangeEnd;
    private final boolean specified;

    private final int globalDefaultPageSize;

    public RaRange(int rangeStart, int rangeEnd, boolean specified, int defaultPageSize) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.specified = specified;
        this.globalDefaultPageSize = defaultPageSize;
    }

    public static RaRange empty(int defaultPageSize) {
        return new RaRange(0, 0, false, defaultPageSize);
    }

    public int getRangeStart() {
        return rangeStart;
    }

    public int getRangeEnd() {
        return rangeEnd;
    }

    public boolean isSpecified() {
        return specified;
    }

    /**
     * Convert to Pageable object for passing to Spring Data repository.
     */
    public Pageable toPageable() {
        return toPageable(globalDefaultPageSize);
    }

    /**
     * Convert to Pageable object for passing to Spring Data repository.
     * @param sort additional sorting information to be included into Pageable object
     */
    public Pageable toPageable(RaSort sort) {
        return toPageable(sort, globalDefaultPageSize);
    }

    /**
     * Convert to Pageable object for passing to Spring Data repository.
     * @param defaultPageSize default page size if range argument is missing
     */
    public Pageable toPageable(int defaultPageSize) {
        return toPageable(null, defaultPageSize);
    }

    /**
     * Convert to Pageable object for passing to Spring Data repository.
     * @param sort additional sorting information to be included into Pageable object
     * @param defaultPageSize default page size if range argument is missing
     */
    public Pageable toPageable(@Nullable RaSort sort, int defaultPageSize) {
        if (!specified) {
            return createDefaultPageRequest(sort, defaultPageSize);
        }

        int pageSize = calculatePageSize();
        int pageNumber = calculatePageNumber(pageSize);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        if (sort != null) {
            return pageRequest.withSort(sort.toSort());
        }
        return pageRequest;
    }

    public int calculatePageSize() {
        return rangeEnd - rangeStart + 1;
    }

    public int calculatePageNumber(int pageSize) {
        return rangeStart / pageSize;
    }

    private Pageable createDefaultPageRequest(@Nullable RaSort raSort, int defaultPageSize) {
        if (raSort != null && raSort.isSpecified()) {
            /*if (allowUnpaged) { // todo remove
                return new UnpagedWithSort(raSort.toSort());
            } else {*/
                return PageRequest.of(0, defaultPageSize, raSort.getDirection(), raSort.getProperty());
            //}
        } else {
            /*if (allowUnpaged) {
                return Pageable.unpaged();
            } else {*/
                return PageRequest.of(0, defaultPageSize);
            //}
        }
    }
}
