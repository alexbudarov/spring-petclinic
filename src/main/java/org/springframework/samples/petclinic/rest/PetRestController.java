package org.springframework.samples.petclinic.rest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class PetRestController {

	private final OwnerRepository ownerRepository;

	public PetRestController(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@GetMapping("/owner/{ownerId}/pet")
	public ResponseEntity<List<Pet>> petsByOwnerId(@PathVariable Integer ownerId) {
		Owner owner = ownerRepository.findById(ownerId);
		if (owner == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(owner.getPets());
	}

	@GetMapping("/owner/{ownerId}/pet/{petId}")
	public ResponseEntity<Pet> petById(@PathVariable Integer ownerId, @PathVariable Integer petId) {
		Owner owner = ownerRepository.findById(ownerId);
		if (owner == null) {
			return ResponseEntity.notFound().build();
		}
		List<Pet> pets = owner.getPets();
		return pets.stream()
			.filter(p -> p.getId().equals(petId))
			.findAny()
			.map(p -> ResponseEntity.ok(p))
			.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/owner/{ownerId}/pet")
	public ResponseEntity<Pet> createPet(@PathVariable Integer ownerId, @RequestBody @Valid Pet pet) {
		Owner owner = ownerRepository.findById(ownerId);
		if (owner == null) {
			return ResponseEntity.notFound().build();
		}

		pet.setId(null);
		owner.addPet(pet);
		ownerRepository.save(owner);
		owner = ownerRepository.findById(ownerId);
		return ResponseEntity.ok(owner.getPets().stream()
			.filter(p -> p.getName().equals(pet.getName()) && p.getType().getId().equals(pet.getType().getId()))
			.findAny().orElseThrow()
		);
	}

	@PutMapping("/owner/{ownerId}/pet/{petId}")
	public ResponseEntity<Pet> createPet(@PathVariable Integer ownerId, @PathVariable Integer petId,
										 @RequestBody @Valid Pet pet) {
		Owner owner = ownerRepository.findById(ownerId);
		if (owner == null) {
			return ResponseEntity.notFound().build();
		}

		Pet existingPet = owner.getPet(petId);
		if (existingPet == null) {
			return ResponseEntity.notFound().build();
		}
		existingPet.setName(pet.getName());
		existingPet.setType(pet.getType());
		existingPet.setBirthDate(pet.getBirthDate());

		ownerRepository.save(owner);
		owner = ownerRepository.findById(ownerId);
		return ResponseEntity.ok(owner.getPet(petId));
	}
}

