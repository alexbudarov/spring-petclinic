package org.springframework.samples.petclinic.owner;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/pet")
public class PetRestController {

	private final PetRepository petRepository;
	private final PetMapper petMapper;
	private final RaProtocolUtil raProtocolUtil;
	private final RaPatchUtil raPatchUtil;
	private final SpecificationFilterConverter specificationFilterConverter;

	public PetRestController(PetRepository petRepository,
                             RaProtocolUtil raProtocolUtil,
                             PetMapper petMapper,
                             RaPatchUtil raPatchUtil,
                             SpecificationFilterConverter specificationFilterConverter) {
		this.petRepository = petRepository;
		this.raProtocolUtil = raProtocolUtil;
		this.petMapper = petMapper;
		this.raPatchUtil = raPatchUtil;
		this.specificationFilterConverter = specificationFilterConverter;
	}

	@GetMapping("/{id}")
	public ResponseEntity<PetDto> findById(@PathVariable Integer id) {
		Optional<Pet> petOptional = petRepository.findById(id);
		return ResponseEntity.of(petOptional.map(petMapper::toDto));
	}

	@GetMapping
	public ResponseEntity<List<PetDto>> petList(@RaFilter PetFilter filter,
												@RaRangeParam Pageable pageable) { // only paging, as example
		if (filter.id() != null) { // getMany
			var dtoList = petRepository.findAllById(filter.id())
					.stream()
					.map(petMapper::toDto)
					.toList();
			return ResponseEntity.ok(dtoList);
		}

		Specification<Pet> specification = specificationFilterConverter.convert(filter);
		Page<Pet> page = petRepository.findAll(specification, pageable);

		return raProtocolUtil.convertToResponseEntity(page, petMapper::toDto);
	}

	@PostMapping
	public ResponseEntity<PetDto> create(@RequestBody @Valid PetDto petDto) {
		if (petDto.id() != null) {
			return ResponseEntity.badRequest().build();
		}
		Pet pet = petMapper.toEntity(petDto);
		pet = petRepository.save(pet);

		return ResponseEntity.ok(petMapper.toDto(pet));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PetDto> update(@PathVariable Integer id, @RequestBody String petDtoPatch) {
		Pet pet = petRepository.findById(id).orElse(null);
		if (pet == null) {
			return ResponseEntity.notFound().build();
		}

		PetDto petDto = petMapper.toDto(pet);
		petDto = raPatchUtil.patchAndValidate(petDto, petDtoPatch);

		if (petDto.id() != null && !petDto.id().equals(id)) {
			return ResponseEntity.badRequest().build();
		}

		petMapper.update(petDto, pet);
		pet = petRepository.save(pet);

		return ResponseEntity.ok(petMapper.toDto(pet));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PetDto> delete(@PathVariable Integer id) {
		Optional<PetDto> petDto = petRepository.findById(id)
				.map(petMapper::toDto);

		if (petDto.isPresent()) {
			petRepository.deleteById(id);
		}
		return ResponseEntity.of(petDto);
	}

	public record PetFilter(
			List<Integer> id,

			@JsonProperty("q")
			@SpecFilterCondition(property = "name", operator = SpecFilterOperator.STARTS_WITH, ignoreCase = true)
			String searchString,

			@SpecFilterCondition(property = "owner.id", operator = SpecFilterOperator.EQUALS)
			Integer ownerId
	) {}
}
