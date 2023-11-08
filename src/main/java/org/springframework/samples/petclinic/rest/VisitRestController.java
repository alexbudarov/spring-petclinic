package org.springframework.samples.petclinic.rest;

import jakarta.persistence.criteria.From;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.rest.rasupport.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/visit")
public class VisitRestController {

	private final VisitRepository visitRepository;
	private final PetRepository petRepository;
	private final RaProtocolUtil raProtocolUtil;
	private final VisitMapper visitMapper;
	private final SpecificationFilterConverter specificationFilterConverter;

	public VisitRestController(VisitRepository visitRepository,
							   PetRepository petRepository,
							   RaProtocolUtil raProtocolUtil,
							   VisitMapper visitMapper,
							   SpecificationFilterConverter specificationFilterConverter) {
		this.visitRepository = visitRepository;
		this.petRepository = petRepository;
		this.raProtocolUtil = raProtocolUtil;
		this.visitMapper = visitMapper;
		this.specificationFilterConverter = specificationFilterConverter;
	}

	@PostMapping
	public ResponseEntity<VisitDto> create(@RequestBody @Valid VisitDto dto) {
		if (dto.id() != null || dto.petId() == null) {
			return ResponseEntity.badRequest().build();
		}
		Pet pet = petRepository.findById(dto.petId()).orElse(null);
		if (pet == null) {
			return ResponseEntity.badRequest().build();
		}

		Visit visit = visitMapper.toEntity(dto);
		Visit savedVisit = visitRepository.save(visit);

		pet.addVisit(savedVisit);
		petRepository.save(pet);

		return ResponseEntity.ok(visitMapper.toDto(savedVisit));
	}

	// todo proper implementation of patch mechanics
	@PutMapping("/{id}")
	public ResponseEntity<VisitDto> update(@PathVariable Integer id, @RequestBody @Valid InputVisitDto visitDto) {
		if (visitDto.id() != null && !visitDto.id().equals(id)) {
			return ResponseEntity.badRequest().build();
		}
		Visit visit = visitRepository.findById(id).orElse(null);
		if (visit == null) {
			return ResponseEntity.notFound().build();
		}

		visitMapper.partialUpdate(visitDto, visit);
		visit = visitRepository.save(visit);

		// don't handle VisitDto#petId change, this is a custom field

		return ResponseEntity.ok(visitMapper.toDto(visit));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<VisitDto> delete(@PathVariable Integer id) {
		Optional<VisitDto> visitDto = visitRepository.findById(id).map(visitMapper::toDto);

		if (visitDto.isPresent()) {
			visitRepository.deleteById(id);
		}

		return ResponseEntity.of(visitDto);
	}

	@GetMapping
	public ResponseEntity<List<VisitDto>> visitList(@RaFilter VisitListFilter filter, RaRange range, RaSort sort) {
		Specification<Visit> specification = convertToSpecification(filter);
		Page<Visit> page = visitRepository.findAll(specification, range.toPageable(sort));
		return raProtocolUtil.convertToResponseEntity(page,
				visitMapper::toDto,
				"visit"
		);
	}

	private Specification<Visit> convertToSpecification(VisitListFilter filter) {
		Specification<Visit> specification = specificationFilterConverter.convert(filter);

		// add custom conditions
		if (filter.petId() != null) {
			specification = specification.and((root, query, criteriaBuilder) -> {
				From<Pet, Pet> petFrom = query.from(Pet.class);
				return criteriaBuilder.and(
						criteriaBuilder.equal(petFrom.get("id"), filter.petId()),
						criteriaBuilder.isMember(root, petFrom.<Collection<Visit>>get("visits"))
				);
			});
		}
		return specification;
	}

	/*private Specification<Visit> convertToSpecification(VisitListFilter filter) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (filter.description() != null) {
				predicates.add(criteriaBuilder.like(
						criteriaBuilder.lower(root.get("description")),
						"%" + (filter.description()).toLowerCase() + "%")
				);
			}
			if (filter.dateBefore() != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), filter.dateBefore()));
			}
			if (filter.dateAfter() != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), filter.dateAfter()));
			}
			if (filter.petId() != null) {
				From<Pet, Pet> petFrom = query.from(Pet.class);
				Join<Pet, Visit> visitsJoin = petFrom.join("visits");

				predicates.add(criteriaBuilder.equal(petFrom.get("id"), filter.petId()));
				predicates.add(criteriaBuilder.equal(visitsJoin, root));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}*/

	public record VisitListFilter(
			@SpecFilterCondition(operator = SpecFilterOperator.CONTAINS, ignoreCase = true)
			String description,

			@SpecFilterCondition(property = "date", operator = SpecFilterOperator.LESS_OR_EQUALS)
			LocalDate dateBefore,

			@SpecFilterCondition(property = "date", operator = SpecFilterOperator.GREATER_OR_EQUALS)
			LocalDate dateAfter,

			// custom
			Integer petId) {
	}
}
