package org.springframework.samples.petclinic.rest.rasupport;

import org.springframework.data.domain.Sort;

public class RaSort {
	public final Sort sort;

	public RaSort(Sort sort) {
		this.sort = sort;
	}
}
