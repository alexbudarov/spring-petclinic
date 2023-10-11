package org.springframework.samples.petclinic.rest;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class PetRestController {

	private final PetRepository petRepository;
	private final OwnerRepository ownerRepository;

	public PetRestController(PetRepository petRepository,
							 OwnerRepository ownerRepository) {
		this.petRepository = petRepository;
		this.ownerRepository = ownerRepository;
	}

	@GetMapping("/pet/{id}")
	public ResponseEntity<PetDto> findById(@PathVariable Integer id) {
		Optional<Pet> petOptional = petRepository.findById(id);
		return ResponseEntity.of(petOptional.map(this::toPetDto));
	}

	private PetDto toPetDto(Pet p) {
		return PetDto.toDto(p, loadOwnerWithNames(p));
	}

	@Nullable
	private OwnerWithNames loadOwnerWithNames(Pet p) {
		Optional<Owner> owner = ownerRepository.findByPet(p.getId());
		return owner.map(OwnerWithNames::toDto).orElse(null);
	}

	@PostMapping("/pet")
	public ResponseEntity<PetDto> create(@RequestBody @Valid PetDto petDto) {
		if (petDto.id() != null) {
			return ResponseEntity.badRequest().build();
		}
		Pet pet = petDto.toEntity();
		pet = petRepository.save(pet);

		// save association with owner
		Owner owner = petDto.owner() != null ? petDto.owner().toEntity() : null;
		if (owner != null) {
			owner = ownerRepository.findById(owner.getId());
			owner.getPets().add(pet);
			ownerRepository.save(owner);
		}

		return ResponseEntity.ok(toPetDto(pet));
	}

	@PutMapping("/pet/{id}")
	public ResponseEntity<PetDto> update(@PathVariable Integer id, @RequestBody @Valid PetDto petDto) {
		if (petDto.id() != null && !petDto.id().equals(id)) {
			return ResponseEntity.badRequest().build();
		}
		if (!petRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		Owner oldOwner = ownerRepository.findByPet(id).orElse(null);

		Pet pet = petDto.toEntity();
		pet.setId(id);
		pet = petRepository.save(pet);

		// handler owner change
		Owner owner = petDto.owner() != null ? petDto.owner().toEntity() : null;
		if (oldOwner != null && owner == null) {
			oldOwner.getPets().remove(pet);
			ownerRepository.save(oldOwner);
		} else if (owner != null && (oldOwner == null || !owner.getId().equals(oldOwner.getId()))) {
			owner = ownerRepository.findById(owner.getId());
			owner.getPets().add(pet);
			ownerRepository.save(owner);
		}

		return ResponseEntity.ok(toPetDto(pet));
	}
}
