package org.springframework.samples.petclinic.rest.rasupport;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class RaRange {
	public final int rangeStart;
	public final int rangeEnd;

	public final boolean unpaged;

	public RaRange(int rangeStart, int rangeEnd) {
		this.rangeStart = rangeStart;
		this.rangeEnd = rangeEnd;
		this.unpaged = false;
	}

	private RaRange(boolean unpaged) {
		this.rangeStart = -1;
		this.rangeEnd = -1;
		this.unpaged = unpaged;
	}

	public static RaRange unpaged() {
		return new RaRange(true);
	}

	public Pageable toPageable() {
		if (unpaged) {
			return Pageable.unpaged();
		}
		int pageSize = rangeEnd - rangeStart + 1;
		int pageNumber = rangeStart / pageSize;

		return PageRequest.of(pageNumber, pageSize);
	}
}
