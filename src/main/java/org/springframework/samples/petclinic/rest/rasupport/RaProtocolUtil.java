package org.springframework.samples.petclinic.rest.rasupport;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RaProtocolUtil {

	public static final String CONTENT_RANGE = HttpHeaders.CONTENT_RANGE;

	/*
	 * Fills response for getList() operation
	 *  with paging support
	 *  according to the "ra-data-simple-rest" conventions.
	 */
	public <T> ResponseEntity<List<T>> convertToResponseEntity(Page<T> page) {
		String contentRange = formatContentRange(
			page.getPageable().getOffset(),
			page.getPageable().getOffset() + page.getNumberOfElements() - 1,
			page.getTotalElements()
		);

		ResponseEntity<List<T>> response = ResponseEntity.status(HttpStatus.OK)
			.header(CONTENT_RANGE, contentRange)
			.body(page.getContent());
		return response;
	}

	public <T, D> ResponseEntity<List<D>> convertToResponseEntity(Page<T> page, Function<T, D> mapper) {
		String contentRange = formatContentRange(
				page.getPageable().getOffset(),
				page.getPageable().getOffset() + page.getNumberOfElements() - 1,
				page.getTotalElements()
		);

		ResponseEntity<List<D>> response = ResponseEntity.status(HttpStatus.OK)
				.header(CONTENT_RANGE, contentRange)
				.body(
						page.getContent().stream()
						.map(mapper)
						.collect(Collectors.toList())
				);
		return response;
	}

	public <T> ResponseEntity<List<T>> convertToResponseEntity(List<T> list) {
		String contentRange = formatContentRange(
				0,
				list.size() - 1,
				list.size()
		);

		ResponseEntity<List<T>> response = ResponseEntity.status(HttpStatus.OK)
				.header(CONTENT_RANGE, contentRange)
				.body(list);
		return response;
	}

	private String formatContentRange(long rangeStart, long rangeEnd, long total) {
		String resourceName = "unused"; // this value isn't used by the ra-data-simple-rest implementation
		return String.format(
				"%s %d-%d/%d",
				resourceName,
				rangeStart,
				rangeEnd,
				total
		);
	}
}
