package org.springframework.samples.petclinic.rest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class PetRestController {

	private final PetRepository petRepository;

	public PetRestController(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@GetMapping("/pet/{id}")
	public ResponseEntity<Pet> findById(@PathVariable Integer id) {
		Optional<Pet> petOptional = petRepository.findById(id);
		return ResponseEntity.of(petOptional);
	}

	@PostMapping("/pet")
	public ResponseEntity<Pet> create(@RequestBody @Valid Pet entity) {
		if (entity.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		Pet pet = petRepository.save(entity);
		return ResponseEntity.ok(pet);
	}

	@PutMapping("/pet/{id}")
	public ResponseEntity<Pet> update(@PathVariable Integer id, @RequestBody @Valid Pet entity) {
		if (entity.getId() != null && !entity.getId().equals(id)) {
			return ResponseEntity.badRequest().build();
		}
		if (!petRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		Pet pet = petRepository.save(entity);
		return ResponseEntity.ok(pet);
	}
}
