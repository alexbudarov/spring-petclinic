package org.springframework.samples.petclinic.rest;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSort;
import org.springframework.samples.petclinic.rest.rasupport.RaFilter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/pet")
public class PetRestController {

	private final PetRepository petRepository;
	private final OwnerRepository ownerRepository;
	private final RaProtocolUtil raProtocolUtil;
	private final PetMapper petMapper;

	public PetRestController(PetRepository petRepository,
							 OwnerRepository ownerRepository,
							 RaProtocolUtil raProtocolUtil,
							 PetMapper petMapper) {
		this.petRepository = petRepository;
		this.ownerRepository = ownerRepository;
		this.raProtocolUtil = raProtocolUtil;
		this.petMapper = petMapper;
	}

	@GetMapping("/{id}")
	public ResponseEntity<PetDto> findById(@PathVariable Integer id) {
		Optional<Pet> petOptional = petRepository.findById(id);
		return ResponseEntity.of(petOptional.map(petMapper::toDto));
	}

	@GetMapping
	public ResponseEntity<List<PetDto>> petList(@RaFilter PetFilter filter, RaRangeSort range) {
		Page<Pet> page;
		if (filter.id() != null) {
			page = petRepository.findByIdIn(filter.id(), range.pageable);
		} else if (filter.ownerId() != null) {
			page = petRepository.loadByOwnerId(filter.ownerId(), range.pageable);
		} else {
			page = petRepository.findAll(range.pageable);
		}
		var response = raProtocolUtil.convertToResponseEntity(page, petMapper::toDto, "pet");
		return response;
	}

	@PostMapping
	public ResponseEntity<PetDto> create(@RequestBody @Valid PetDto petDto) {
		if (petDto.id() != null) {
			return ResponseEntity.badRequest().build();
		}
		Pet pet = petMapper.toEntity(petDto);
		pet = petRepository.save(pet);

		// save association with owner
		Owner owner = petDto.ownerId() != null ? ownerRepository.findById(petDto.ownerId()) : null;
		if (owner != null) {
			owner = ownerRepository.findById(owner.getId());
			owner.getPets().add(pet);
			ownerRepository.save(owner);
		}

		return ResponseEntity.ok(petMapper.toDto(pet));
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

		petMapper.update(petDto, pet);
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

		return ResponseEntity.ok(petMapper.toDto(pet));
	}

	public record PetFilter(Integer[] id, Integer ownerId) {}
}
