package org.springframework.samples.petclinic.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.rest.rasupport.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/pet")
public class PetRestController {

	private final PetRepository petRepository;
	private final OwnerRepository ownerRepository;
	private final RaProtocolUtil raProtocolUtil;
	private final PetMapper petMapper;
	private final RaPatchUtil raPatchUtil;

	public PetRestController(PetRepository petRepository,
							 OwnerRepository ownerRepository,
							 RaProtocolUtil raProtocolUtil,
							 PetMapper petMapper,
							 RaPatchUtil raPatchUtil) {
		this.petRepository = petRepository;
		this.ownerRepository = ownerRepository;
		this.raProtocolUtil = raProtocolUtil;
		this.petMapper = petMapper;
		this.raPatchUtil = raPatchUtil;
	}

	@GetMapping("/{id}")
	public ResponseEntity<PetDto> findById(@PathVariable Integer id) {
		Optional<Pet> petOptional = petRepository.findById(id);
		return ResponseEntity.of(petOptional.map(petMapper::toDto));
	}

	@GetMapping
	public ResponseEntity<List<PetDto>> petList(@RaFilter PetFilter filter,
												@RaSortParam Sort sort) { // only sort, as example
		if (filter.id() != null) { // getMany
			List<Pet> entities = petRepository.findByIdIn(filter.id());
			var dtoList = entities.stream().map(petMapper::toDto).toList();
			return ResponseEntity.ok(dtoList);
		}

		/* custom logic */
		if (sort.isEmpty()) { // default sorting
			sort = Sort.by(Sort.Direction.ASC, "name");
		}
		Sort.Order namelengthOrder = sort.getOrderFor("namelength");
		Sort.Direction directionByNameLength = namelengthOrder != null ? namelengthOrder.getDirection() : null;
		if (directionByNameLength != null) {
			sort = Sort.unsorted();
		}
		/* end of custom logic */

		List<Pet> list;
		if (filter.searchString() != null) {
			list = petRepository.findByNameStartsWithIgnoreCase(filter.searchString(), sort);
		} else if (filter.ownerId() != null) {
			list = petRepository.loadByOwnerId(filter.ownerId(), sort);
		} else {
			list = petRepository.findAll(sort);
		}

		/* custom logic */
		if (directionByNameLength != null) { // sort by length of name
			list = new ArrayList<>(list);
			list.sort(Comparator.comparing(p -> {
				int nameLength = p.getName().length();
				return directionByNameLength.isAscending() ? nameLength : -nameLength;
            }));
		}
		/* end of custom logic */

		var response = raProtocolUtil.convertToResponseEntity(
				list.stream().map(petMapper::toDto).toList()
		);
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
	public ResponseEntity<PetDto> update(@PathVariable Integer id, @RequestBody String petDtoPatch) {
		Pet pet = petRepository.findById(id).orElse(null);
		if (pet == null) {
			return ResponseEntity.notFound().build();
		}
		Owner oldOwner = ownerRepository.findByPet(id).orElse(null);

		PetDto petDto = petMapper.toDto(pet);
		petDto = raPatchUtil.patchAndValidate(petDto, petDtoPatch);

		if (petDto.id() != null && !petDto.id().equals(id)) {
			return ResponseEntity.badRequest().build();
		}

		petMapper.update(petDto, pet);
		pet = petRepository.save(pet);

		// handle owner change
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

	public record PetFilter(Integer[] id, @JsonProperty("q") String searchString, Integer ownerId) {}
}
