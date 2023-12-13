package org.springframework.samples.petclinic.rest.rasupport;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

/**
 * Implementation of combination which is missing in Spring Data: no paging but with sorting.
 */
public class UnpagedWithSort implements Pageable {

    private final Sort sort;

    public UnpagedWithSort(Sort sort) {
        this.sort = sort;
    }

    // Like in org.springframework.data.domain.Unpaged

    @Override
    public boolean isPaged() {
        return false;
    }

    @Override
    public boolean isUnpaged() {
        return true;
    }

    @Override
    public Pageable previousOrFirst() {
        return this;
    }

    @Override
    public Pageable next() {
        return this;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public int getPageSize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getPageNumber() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getOffset() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pageable first() {
        return this;
    }

    @Override
    public Pageable withPage(int pageNumber) {

        if (pageNumber == 0) {
            return this;
        }

        throw new UnsupportedOperationException();
    }

    // like in org.springframework.data.domain.PageRequest

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof UnpagedWithSort that)) {
            return false;
        }

        return super.equals(that) && sort.equals(that.sort);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + sort.hashCode();
    }

    @Override
    public String toString() {
        return String.format("UnpagedWithSort [sort: %s]", sort);
    }
}
