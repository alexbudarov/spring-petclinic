package org.springframework.samples.petclinic.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

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
	public Page<OwnerDto> ownerList(@ModelAttribute OwnerFilter filter, Pageable pageable) {
		Specification<Owner> specification = filter.toSpecification();
		Page<Owner> page = ownerRepository.findAll(specification, pageable);
		return page.map(ownerMapper::toDto);
	}

	@DeleteMapping
	public List<Integer> delete(@RequestParam List<Integer> ids) {
		List<Owner> existingEntities = ownerRepository.findAllById(ids);

		ownerRepository.deleteAllById(ids);

		return existingEntities.stream().map(e -> e.getId()).toList();
	}

}
