package org.springframework.samples.petclinic.rest.rasupport;

import org.springframework.data.domain.Pageable;

/*
 * Holder object containing paging and sorting information.
 */
public class RaRangeSort {
	public final Pageable pageable;

	public RaRangeSort(Pageable pageable) {
		this.pageable = pageable;
	}
}
