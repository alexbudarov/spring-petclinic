package org.springframework.samples.petclinic.rest;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.rest.rasupport.RaFilter;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/pet")
public class PetRestController {

	private final PetRepository petRepository;
	private final OwnerRepository ownerRepository;
	private final RaProtocolUtil raProtocolUtil;

	public PetRestController(PetRepository petRepository,
							 OwnerRepository ownerRepository,
							 RaProtocolUtil raProtocolUtil) {
		this.petRepository = petRepository;
		this.ownerRepository = ownerRepository;
		this.raProtocolUtil = raProtocolUtil;
	}

	@GetMapping("/{id}")
	public ResponseEntity<PetDto> findById(@PathVariable Integer id) {
		Optional<Pet> petOptional = petRepository.findById(id);
		return ResponseEntity.of(petOptional.map(this::toPetDto));
	}

	@GetMapping
	public ResponseEntity<List<PetDto>> petList(RaFilter filter,
												RaRangeSort range) {
		Object idFilterParam = filter.parameters.get("id");
		if (idFilterParam instanceof Object[]) {
			Page<Pet> entities = petRepository.findByIdIn((Object[]) idFilterParam, range.pageable);
			return raProtocolUtil.convertToResponseEntity(entities, this::toPetDto, "pet");
		}

		Page<Pet> page = petRepository.findAll(range.pageable);
		var response = raProtocolUtil.convertToResponseEntity(page, this::toPetDto, "pet");
		return response;
	}

	private PetDto toPetDto(Pet p) {
		return PetDto.toDto(p, loadOwnerId(p));
	}

	@Nullable
	private Integer loadOwnerId(Pet p) {
		Optional<Owner> owner = ownerRepository.findByPet(p.getId());
		return owner.map(Owner::getId).orElse(null);
	}

	@PostMapping
	public ResponseEntity<PetDto> create(@RequestBody @Valid PetDto petDto) {
		if (petDto.id() != null) {
			return ResponseEntity.badRequest().build();
		}
		Pet pet = petDto.toEntity();
		pet = petRepository.save(pet);

		// save association with owner
		Owner owner = petDto.ownerId() != null ? ownerRepository.findById(petDto.ownerId()) : null;
		if (owner != null) {
			owner = ownerRepository.findById(owner.getId());
			owner.getPets().add(pet);
			ownerRepository.save(owner);
		}

		return ResponseEntity.ok(toPetDto(pet));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PetDto> update(@PathVariable Integer id, @RequestBody @Valid PetDto petDto) {
		if (petDto.id() != null && !petDto.id().equals(id)) {
			return ResponseEntity.badRequest().build();
		}
		Pet pet = petRepository.findById(id).orElse(null);
		if (pet == null) {
			return ResponseEntity.notFound().build();
		}
		Owner oldOwner = ownerRepository.findByPet(id).orElse(null);

		Pet updatedPet = petDto.toEntity();

		// to avoid problem with cascading visits
		// map
		pet.setName(updatedPet.getName());
		pet.setBirthDate(updatedPet.getBirthDate());
		pet.setType(updatedPet.getType());

		pet = petRepository.save(pet);

		// handler owner change
		Owner owner = petDto.ownerId() != null ? ownerRepository.findById(petDto.ownerId()) : null;
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
