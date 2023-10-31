package org.springframework.samples.petclinic.rest;

import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.rest.rasupport.RaFilter;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class VisitRestController {

	private final VisitRepository visitRepository;
	private final PetRepository petRepository;
	private final RaProtocolUtil raProtocolUtil;
	private final VisitMapper visitMapper;

	public VisitRestController(VisitRepository visitRepository,
							   PetRepository petRepository,
							   RaProtocolUtil raProtocolUtil,
							   VisitMapper visitMapper) {
		this.visitRepository = visitRepository;
		this.petRepository = petRepository;
		this.raProtocolUtil = raProtocolUtil;
		this.visitMapper = visitMapper;
	}

	@PostMapping("/visit")
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

	@GetMapping("/visit")
	public ResponseEntity<List<VisitDto>> visitList(RaFilter filter, RaRangeSort rangeSort) {
		Specification<Visit> specification = convertToSpecification(filter);
		Page<Visit> page = visitRepository.findAll(specification, rangeSort.pageable);
		return raProtocolUtil.convertToResponseEntity(page,
				visitMapper::toDto,
				"visit"
		);
	}

	private Specification<Visit> convertToSpecification(RaFilter filter) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			Map<String, Object> parameters = filter.parameters;
			if (!parameters.isEmpty()) {
				if (parameters.get("description") != null) {
					predicates.add(criteriaBuilder.like(
							criteriaBuilder.lower(root.get("description")),
							"%" + ((String) parameters.get("description")).toLowerCase() + "%")
					);
				}
				Object dateBefore = parameters.get("dateBefore");
				if (dateBefore != null) {
					LocalDate localDate = LocalDate.parse((String) dateBefore);
					predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), localDate));
				}
				Object dateAfter = parameters.get("dateAfter");
				if (dateAfter != null) {
					LocalDate localDate = LocalDate.parse((String) dateAfter);
					predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), localDate));
				}
				if (parameters.get("petId") != null) {
					From<Pet, Pet> petFrom = query.from(Pet.class);
					Join<Pet, Visit> visitsJoin = petFrom.join("visits");

					predicates.add(criteriaBuilder.equal(petFrom.get("id"), parameters.get("petId")));
					predicates.add(criteriaBuilder.equal(visitsJoin, root));
				}
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
