package org.springframework.samples.petclinic.rest;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.rest.rasupport.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/owner")
public class OwnerRestController {

	private final OwnerRepository ownerRepository;
	private final RaProtocolUtil raProtocolUtil;
	private final OwnerMapper ownerMapper;

	public OwnerRestController(OwnerRepository ownerRepository,
							   RaProtocolUtil raProtocolUtil,
							   OwnerMapper ownerMapper) {
		this.ownerRepository = ownerRepository;
		this.raProtocolUtil = raProtocolUtil;
		this.ownerMapper = ownerMapper;
	}

	@GetMapping("/{id}")
	public ResponseEntity<OwnerDto> findById(@PathVariable Integer id) {
		Owner ownerOrNull = ownerRepository.findById(id);
		return ResponseEntity.ofNullable(ownerMapper.toDto(ownerOrNull));
	}

	@GetMapping
	public ResponseEntity<List<OwnerDto>> ownerList(@RaFilter OwnerListFilter filter,
													RaRange range, RaSort sort) {

		Specification<Owner> specification = convertToSpecification(filter);
		Page<Owner> page = ownerRepository.findAll(specification, range.toPageable(sort));

		return raProtocolUtil.convertToResponseEntity(page, ownerMapper::toDto, "owner");
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public OwnerDto create(@RequestBody @Valid OwnerDto ownerDto) {
		Owner owner = ownerMapper.toEntity(ownerDto);
		owner.setId(null);
		ownerRepository.save(owner);
		return ownerMapper.toDto(owner);
	}

	@PutMapping("/{id}")
	public ResponseEntity<OwnerDto> update(@PathVariable Integer id, @RequestBody @Valid OwnerDto ownerDto) {
		if (ownerDto.id() != null && !ownerDto.id().equals(id)) {
			return ResponseEntity.badRequest().build();
		}
		Owner owner = ownerRepository.findById(id);
		ownerMapper.update(ownerDto, owner);
		ownerRepository.save(owner);

		OwnerDto updatedDto = ownerMapper.toDto(ownerRepository.findById(id));
		return ResponseEntity.ok(updatedDto);
	}

	private Specification<Owner> convertToSpecification(OwnerListFilter filter) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			// getMany()
			if (filter.id() != null) {
				predicates.add(root.get("id").in((Object[]) filter.id()));
			}

			// filter by lastName contains ignore case
			if (filter.lastName() != null) {
				predicates.add(criteriaBuilder.like(
						criteriaBuilder.lower(root.get("lastName")),
						"%" + filter.lastName().toLowerCase() + "%"
				));
			}

			// filter by custom condition
			if (filter.petTypeId() != null) {
				Join<Owner, Pet> petJoin = root.join("pets");
				predicates.add(
						criteriaBuilder.equal(petJoin.get("type").get("id"), filter.petTypeId())
				);
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

	public record OwnerListFilter(
		@SpecFilterCondition(operator = SpecFilterOperator.IN)
		Integer[] id,

		@SpecFilterCondition(operator = SpecFilterOperator.CONTAINS, ignoreCase = true)
		String lastName,

		@SpecFilterCondition(property = "pets.type.id", operator = SpecFilterOperator.EQUALS)
		Integer petTypeId
	) {
	}

}

