package org.springframework.samples.petclinic.rest.rasupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RaRangeSortConverter {

    private final ObjectMapper objectMapper;

    public RaRangeSortConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public RaRange convertRangeParams(@Nullable String range) {
        if (range == null || range.equals("[]")) {
            return RaRange.empty(1000);
        }

        Integer[] integers;
        try {
            integers = objectMapper.readValue(range, Integer[].class);
        } catch (JsonProcessingException e) {
            throw new JsonConversionException("Invalid range format: " + range, e);
        }

        if (integers.length != 2) {
            throw new JsonConversionException("Invalid range format, must be array of two integers: " + range);
        }

        int rangeStart = integers[0];
        int rangeEnd = integers[1];

        return new RaRange(rangeStart, rangeEnd, true, 1000);
    }

    public RaSort convertSortParams(@Nullable String sort) {
        if (sort == null || sort.equals("[]")) {
            return RaSort.empty();
        }

        String[] strings;
        try {
            strings = objectMapper.readValue(sort, String[].class);
        } catch (JsonProcessingException e) {
            throw new JsonConversionException("Invalid sort format: " + sort, e);
        }

        if (strings.length != 2) {
            throw new JsonConversionException("Invalid range format, must be two strings: " + sort);
        }

        String field = strings[0];
        Sort.Direction direction = Sort.Direction.fromString(strings[1]);

        return new RaSort(field, direction, true);
    }

    public Pageable convertToPageable(@Nullable String range) {
        return convertRangeParams(range).toPageable();
    }

    public Pageable convertToPageable(@Nullable String range, @Nullable String sort) {
        RaRange raRange = convertRangeParams(range);
        RaSort raSort = convertSortParams(sort);
        return raRange.toPageable(raSort);
    }

    public Sort convertToSort(@Nullable String sort) {
        return convertSortParams(sort).toSort();
    }

}
