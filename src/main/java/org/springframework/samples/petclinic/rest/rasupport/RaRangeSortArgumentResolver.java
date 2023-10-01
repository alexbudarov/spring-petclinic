package org.springframework.samples.petclinic.rest.rasupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RaRangeSortArgumentResolver implements HandlerMethodArgumentResolver {

	private final static Logger log = LoggerFactory.getLogger(RaRangeSortArgumentResolver.class);
	private static final int DEFAULT_PAGE_SIZE = 20;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return RaRangeSort.class.equals(parameter.getParameterType())
			|| RaSort.class.equals(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
								  ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest,
								  WebDataBinderFactory binderFactory) {
		if (RaSort.class.equals(parameter.getParameterType())) {
			Sort sort = extractSort(webRequest);
			return new RaSort(sort);
		}

		Sort sort = extractSort(webRequest);
		Pageable pageable = extractRange(webRequest, sort);
		return new RaRangeSort(pageable);
	}

	private Sort extractSort(NativeWebRequest webRequest) {
		String sort = webRequest.getParameter("sort");

		if (sort == null || sort.equals("[]")) {
			return Sort.unsorted();
		}

		String[] strings;
		try {
			strings = objectMapper.readValue(sort, String[].class);
		} catch (JsonProcessingException e) {
			log.debug("Invalid sort format", e);
			return Sort.unsorted();
		}


		if (strings.length != 2) {
			log.debug("Invalid sort format: " + sort);
			return Sort.unsorted();
		}

		String field = strings[0];
		Sort.Direction direction = Sort.Direction.fromString(strings[1]);
		return Sort.by(direction, field);
	}

	private Pageable extractRange(NativeWebRequest webRequest, Sort sort) {
		String range = webRequest.getParameter("range");

		if (range == null || range.equals("[]")) {
			return createDefaultPageRequest(sort);
		}

		Integer[] integers;
		try {
			integers = objectMapper.readValue(range, Integer[].class);
		} catch (JsonProcessingException e) {
			log.debug("Invalid range format: " + range, e);
			return createDefaultPageRequest(sort);
		}

		if (integers.length != 2) {
			log.debug("Invalid range format: " + range);
			return createDefaultPageRequest(sort);
		}

		int rangeStart = integers[0];
		int rangeEnd = integers[1];

		int pageSize = rangeEnd - rangeStart + 1;
		int pageNumber = rangeStart / pageSize;

		return PageRequest.of(pageNumber, pageSize).withSort(sort);
	}

	private static PageRequest createDefaultPageRequest(Sort sort) {
		Sort.Order order = sort.stream().iterator().next();
		// use default page size
		return PageRequest.of(0, DEFAULT_PAGE_SIZE, order.getDirection(), order.getProperty());
	}
}
