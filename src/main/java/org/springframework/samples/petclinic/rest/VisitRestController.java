package org.springframework.samples.petclinic.rest;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.persistence.criteria.From;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	private final RaPatchUtil raPatchUtil;
	private final RaRangeSortConverter raRangeSortConverter;

	public VisitRestController(VisitRepository visitRepository,
							   PetRepository petRepository,
							   RaProtocolUtil raProtocolUtil,
							   VisitMapper visitMapper,
							   SpecificationFilterConverter specificationFilterConverter,
							   RaPatchUtil raPatchUtil,
							   RaRangeSortConverter raRangeSortConverter) {
		this.visitRepository = visitRepository;
		this.petRepository = petRepository;
		this.raProtocolUtil = raProtocolUtil;
		this.visitMapper = visitMapper;
		this.specificationFilterConverter = specificationFilterConverter;
		this.raPatchUtil = raPatchUtil;
		this.raRangeSortConverter = raRangeSortConverter;
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

	@PutMapping("/{id}")
	public ResponseEntity<VisitDto> update(@PathVariable Integer id, @RequestBody String visitDtoPatch) {
		// no DTO:
		// 1) load entity by id
		// 2) read and assign using: ObjectMapper#readerForUpdating()
		// 3) save via repository

		// DTO (or quasi DTO)
		// DTO means attribute conversion or cutting of some fields
		// 1.1) load entity
		// 1.2) convert entity -> DTO (MapStruct)
		// 1.3) patch DTO from request body (Jackson)
		//    .1) by updating mutable object,
		//    .2) or cloning immutable one
		// 1.4) update() entity from DTO (MapStruct)
		// 1.5) save entity


		Visit visit = visitRepository.findById(id).orElse(null); // 1.1
		if (visit == null) {
			return ResponseEntity.notFound().build();
		}

		VisitDto visitDto = visitMapper.toDto(visit); // 1.2
		visitDto = raPatchUtil.patchAndValidate(visitDto, visitDtoPatch); // 1.3

		if (visitDto.id() != null && !visitDto.id().equals(id)) {
			return ResponseEntity.badRequest().build();
		}

		visitMapper.update(visitDto, visit); // 1.4
		visit = visitRepository.save(visit); // 1.5

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
	public ResponseEntity<List<VisitDto>> visitList(
			/*@Parameter(required = false, explode = Explode.FALSE, style = ParameterStyle.MATRIX)*/
			// @RequestParam(required = false)
			@RaFilter VisitListFilter filter,
			@Parameter(example = "\"[0,4]\"") @RequestParam(required = false) String range,
			@Parameter(example = "[\"date\", \"DESC\"]") @RequestParam(required = false) String sort
	) {
		Specification<Visit> specification = convertToSpecification(filter);
		Pageable pageable = raRangeSortConverter.convertToPageable(range, sort);
		Page<Visit> page = visitRepository.findAll(specification, pageable);
		return raProtocolUtil.convertToResponseEntity(page,
				visitMapper::toDto
		);
	}

	private Specification<Visit> convertToSpecification(VisitListFilter filter) {
		Specification<Visit> specification = specificationFilterConverter.convert(filter);

		// add custom conditions
		if (filter != null && filter.petId() != null) {
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
