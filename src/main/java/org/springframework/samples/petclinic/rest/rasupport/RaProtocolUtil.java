package org.springframework.samples.petclinic.rest.rasupport;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RaProtocolUtil {

	public static final String CONTENT_RANGE = HttpHeaders.CONTENT_RANGE;

	/*
	 * Fills response for getList() operation
	 *  with paging support
	 *  according to the "ra-data-simple-rest" conventions.
	 */
	public <T> ResponseEntity<List<T>> convertToResponseEntity(Page<T> page, String resourceName) {
		String contentRange = String.format(
			"%s %d-%d/%d",
			resourceName,
			page.getPageable().getOffset(),
			page.getPageable().getOffset() + page.getNumberOfElements(),
			page.getTotalElements()
		);

		ResponseEntity<List<T>> response = ResponseEntity.status(HttpStatus.OK)
			.header(CONTENT_RANGE, contentRange)
			.body(page.getContent());
		return response;
	}

}
