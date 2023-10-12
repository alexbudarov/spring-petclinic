package org.springframework.samples.petclinic.rest;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.rest.rasupport.RaFilter;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/rest")
public class VisitRestController {

	private final VisitRepository visitRepository;
	private final PetRepository petRepository;
	private final RaProtocolUtil raProtocolUtil;

	public VisitRestController(VisitRepository visitRepository,
							   PetRepository petRepository,
							   RaProtocolUtil raProtocolUtil) {
		this.visitRepository = visitRepository;
		this.petRepository = petRepository;
		this.raProtocolUtil = raProtocolUtil;
	}

	@PostMapping("/visit")
	public ResponseEntity<VisitDto> save(@RequestBody @Valid VisitDto dto) {
		if (dto.id() != null || dto.petId() == null) {
			return ResponseEntity.badRequest().build();
		}
		Pet pet = petRepository.findById(dto.petId()).orElse(null);
		if (pet == null) {
			return ResponseEntity.badRequest().build();
		}

		Visit visit = dto.toEntity();
		Visit savedVisit = visitRepository.save(visit);

		pet.addVisit(savedVisit);
		petRepository.save(pet);

		return ResponseEntity.ok(VisitDto.toDto(savedVisit, pet.getId()));
	}

	@GetMapping("/visit")
	public ResponseEntity<List<VisitDto>> visitList(RaFilter filter, RaRangeSort rangeSort) {
		Integer petId = (Integer) filter.parameters.get("petId");
		if (petId != null) {
			List<VisitDto> list = visitRepository.findByPet(petId)
					.stream()
					.map(v -> VisitDto.toDto(v, petId))
					.collect(toList());

			return raProtocolUtil.convertToResponseEntity(list, "visit");
		}
		Page<Visit> page = visitRepository.findAll(rangeSort.pageable);
		return raProtocolUtil.convertToResponseEntity(page,
				v -> VisitDto.toDto(v, loadPetId(v)),
				"visit"
		);
	}

	@Nullable
	private Integer loadPetId(Visit visit) {
		return petRepository.loadPetByVisit(visit.getId());
	}
}
