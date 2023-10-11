package org.springframework.samples.petclinic.rest;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.rest.rasupport.RaFilter;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/owner")
public class OwnerRestController {

	private final OwnerRepository ownerRepository;
	private final RaProtocolUtil raProtocolUtil;

	public OwnerRestController(OwnerRepository ownerRepository,
							   RaProtocolUtil raProtocolUtil) {
		this.ownerRepository = ownerRepository;
		this.raProtocolUtil = raProtocolUtil;
	}

	@GetMapping("/{id}")
	public Owner findById(@PathVariable Integer id) {
		return ownerRepository.findById(id);
	}
	/*
	// proper implementation
    public ResponseEntity<Owner> findById(@PathVariable Integer id) {
		Owner owner = ownerRepository.findById(id);
		return ResponseEntity.ofNullable(owner);
	}
	 */

	@GetMapping
	public ResponseEntity<List<Owner>> ownerList(RaFilter filter,
												 RaRangeSort range) {
		Object idFilterParam = filter.parameters.get("id");
		if (idFilterParam instanceof Object[]) {
			Page<Owner> entities = ownerRepository.findByIdIn((Object[]) idFilterParam, range.pageable);
			return raProtocolUtil.convertToResponseEntity(entities, "owner");
		}

		String lastName = (String) filter.parameters.get("lastName");
		if (lastName == null) {
			lastName = "";
		}
		Page<Owner> page = ownerRepository.findByLastName(lastName, range.pageable);
		ResponseEntity<List<Owner>> response = raProtocolUtil.convertToResponseEntity(page, "owner");
		return response;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public Owner create(@RequestBody @Valid Owner owner) {
		owner.setId(null);
		ownerRepository.save(owner);
		return owner;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Owner update(@PathVariable Integer id, @RequestBody @Valid Owner owner) {
		owner.setId(id);
		ownerRepository.save(owner);
		return ownerRepository.findById(id);
	}
}
