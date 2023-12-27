package org.springframework.samples.petclinic.owner;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.RaPatchUtil;
import org.springframework.samples.petclinic.rest.rasupport.SpecFilterCondition;
import org.springframework.samples.petclinic.rest.rasupport.SpecFilterOperator;
import org.springframework.samples.petclinic.rest.rasupport.SpecificationFilterConverter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/pet")
public class PetRestController {

	private final PetRepository petRepository;
	private final PetMapper petMapper;
	private final RaPatchUtil raPatchUtil;
	private final SpecificationFilterConverter specificationFilterConverter;

	public PetRestController(PetRepository petRepository,
                             PetMapper petMapper,
                             RaPatchUtil raPatchUtil,
                             SpecificationFilterConverter specificationFilterConverter) {
		this.petRepository = petRepository;
		this.petMapper = petMapper;
		this.raPatchUtil = raPatchUtil;
		this.specificationFilterConverter = specificationFilterConverter;
	}

	@GetMapping("/{id}")
	public ResponseEntity<PetDto> findById(@PathVariable Integer id) {
		Optional<Pet> petOptional = petRepository.findById(id);
		return ResponseEntity.of(petOptional.map(petMapper::toDto));
	}

	@GetMapping(path="/by-ids")
	public List<PetDto> petListByIds(@RequestParam List<Integer> ids) {
        return petRepository.findAllById(ids)
			.stream()
			.map(petMapper::toDto)
			.toList();
	}

	@GetMapping
	public Page<PetDto> petList(@ModelAttribute PetFilter filter, @PageableDefault(size = 5) Pageable pageable) { // no way to declare "no sorting"
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()); // clear possible sorting params

		Specification<Pet> specification = specificationFilterConverter.convert(filter);
		Page<Pet> page = petRepository.findAll(specification, pageable);
		return page.map(petMapper::toDto);
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

	@PutMapping
	public ResponseEntity<List<Integer>> update(@RequestParam List<Integer> ids, @RequestBody String patchJson) {
		List<Pet> updatedEntities = new ArrayList<>();
		for (Integer id: ids) {
			Pet entity = petRepository.findById(id).orElse(null);
			if (entity == null) {
				continue;
			}

			PetDto dto = petMapper.toDto(entity);
			dto = raPatchUtil.patchAndValidate(dto, patchJson);

			if (dto.id() != null && !dto.id().equals(id)) { // attempt to change entity id
				return ResponseEntity.badRequest().build();
			}

			petMapper.update(dto, entity);
			updatedEntities.add(entity);
		}

		petRepository.saveAll(updatedEntities);

		List<Integer> updatedIds = updatedEntities.stream().map(e -> e.getId()).toList();
		return ResponseEntity.ok(updatedIds);
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
			@SpecFilterCondition(property = "name", operator = SpecFilterOperator.STARTS_WITH, ignoreCase = true)
			String q,

			@SpecFilterCondition(property = "owner.id", operator = SpecFilterOperator.EQUALS)
			Integer ownerId
	) {}
}
