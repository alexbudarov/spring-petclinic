package org.springframework.samples.petclinic.rest;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/owner")
public class OwnerRestController {

	private final OwnerRepository ownerRepository;

	public OwnerRestController(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
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
	public Page<Owner> findByLastName(@RequestParam(required = false) String lastName, Pageable pageable) {
		if (lastName == null) {
			lastName = "";
		}
		return ownerRepository.findByLastName(lastName, pageable);
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
