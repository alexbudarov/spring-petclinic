package org.springframework.samples.petclinic.rest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class VisitRestController {

	private final VisitRepository visitRepository;
	private final OwnerRepository ownerRepository;
	private final PetRepository petRepository;

	public VisitRestController(VisitRepository visitRepository,
							   OwnerRepository ownerRepository,
							   PetRepository petRepository) {
		this.visitRepository = visitRepository;
		this.ownerRepository = ownerRepository;
		this.petRepository = petRepository;
	}

	@GetMapping("/owner/{ownerId}/pet/{petId}/visit")
	public List<Visit> findByOwnerAndPet(@PathVariable int ownerId, @PathVariable int petId) {
		return visitRepository.findByOwnerAndPet(ownerId, petId);
	}

	@PostMapping("/owner/{ownerId}/pet/{petId}/visit")
	public ResponseEntity<Visit> save(@PathVariable int ownerId, @PathVariable int petId,
									  @RequestBody @Valid Visit entity) {
		Owner owner = ownerRepository.findById(ownerId);
		if (owner == null) {
			return ResponseEntity.notFound().build();
		}

		Pet pet = owner.getPet(petId);
		if (pet == null) {
			return ResponseEntity.notFound().build();
		}

		Visit savedVisit = visitRepository.save(entity);
		pet.addVisit(savedVisit);
		petRepository.save(pet);

		return ResponseEntity.ok(savedVisit);
	}
}

