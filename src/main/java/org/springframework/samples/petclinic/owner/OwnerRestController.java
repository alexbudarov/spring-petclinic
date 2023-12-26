package org.springframework.samples.petclinic.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.SpecificationFilterConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/owner")
public class OwnerRestController {

	private final OwnerRepository ownerRepository;
	private final OwnerMapper ownerMapper;

	public OwnerRestController(OwnerRepository ownerRepository,
                               OwnerMapper ownerMapper) {
		this.ownerRepository = ownerRepository;
		this.ownerMapper = ownerMapper;
	}

	@GetMapping(path="/by-ids"/*, params = "id"*/)
	public List<OwnerDto> ownerListById(@RequestParam List<Integer> ids) {
		var dtoList = ownerRepository.findAllById(ids)
			.stream().map(ownerMapper::toDto)
			.toList();
		return dtoList;
	}

	@GetMapping/*(params="!id")*/
	public Page<OwnerDto> ownerList(OwnerListFilter filter, Pageable pageable) {
		// handle custom search string
		if (filter.q() != null) {
			Page<Owner> page = ownerRepository.findByFirstOrLastName(filter.q(), pageable);
			return page.map(ownerMapper::toDto);
		}

		if (filter.telephone() != null) {
			Page<Owner> page = ownerRepository.findByTelephoneStartsWith(filter.telephone(), pageable);
			return page.map(ownerMapper::toDto);
		}

		Page<Owner> page = ownerRepository.findAll(pageable);
		return page.map(ownerMapper::toDto);
	}

	public record OwnerListFilter(
		String q,

		String telephone
	) {
	}
}
