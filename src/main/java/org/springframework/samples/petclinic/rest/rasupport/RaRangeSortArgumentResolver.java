package org.springframework.samples.petclinic.rest.rasupport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class RaRangeSortArgumentResolver implements HandlerMethodArgumentResolver {
    @Value("${ra.range.defaultPageSize:1000}")
    private int globalDefaultPageSize;

    private final RaRangeSortConverter raRangeSortConverter;

    public RaRangeSortArgumentResolver(RaRangeSortConverter raRangeSortConverter) {
        this.raRangeSortConverter = raRangeSortConverter;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (Sort.class.equals(parameter.getParameterType())) {
            return parameter.getParameterAnnotation(RaSortParam.class) != null;
        }
        if (Pageable.class.equals(parameter.getParameterType())) {
            return parameter.getParameterAnnotation(RaRangeParam.class) != null;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {
        RaRangeParam rangeAnnotation = parameter.getParameterAnnotation(RaRangeParam.class);
        RaSortParam sortAnnotation = parameter.getParameterAnnotation(RaSortParam.class);

        if (Sort.class.equals(parameter.getParameterType()) && sortAnnotation != null) {
            return resolveSort(webRequest);
        }

        if (Pageable.class.equals(parameter.getParameterType()) && rangeAnnotation != null) {
            return resolvePageable(webRequest, rangeAnnotation, sortAnnotation);
        }
        return null;
    }

    private Object resolveSort(NativeWebRequest webRequest) {
        String sort = webRequest.getParameter("sort");
        RaSort raSort = raRangeSortConverter.convertSortParams(sort);
        return raSort.toSort();
    }

    private Object resolvePageable(NativeWebRequest webRequest, RaRangeParam rangeAnnotation, @Nullable RaSortParam sortAnnotation) {
        String range = webRequest.getParameter("range");
        String sort = sortAnnotation != null ? webRequest.getParameter("sort") : null;

        RaRange raRange = raRangeSortConverter.convertRangeParams(range);
        RaSort raSort = raRangeSortConverter.convertSortParams(sort);

        return raRange.toPageable(raSort, rangeAnnotation.defaultPageSize(), rangeAnnotation.allowUnpaged());
    }
}
