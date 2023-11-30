package org.springframework.samples.petclinic.visit;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.rest.rasupport.*;
import org.springframework.samples.petclinic.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/visit")
public class VisitRestController {

    private final VisitService visitService;
    private final VetRepository vetRepository;
    private final PetRepository petRepository;
    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final RaProtocolUtil raProtocolUtil;
    private final SpecificationFilterConverter specificationFilterConverter;

    public VisitRestController(VisitService visitService,
                               VetRepository vetRepository,
                               SpecialtyRepository specialtyRepository,
                               PetRepository petRepository,
                               VisitRepository visitRepository,
                               VisitMapper visitMapper,
                               RaProtocolUtil raProtocolUtil,
                               SpecificationFilterConverter specificationFilterConverter) {
        this.visitService = visitService;
        this.vetRepository = vetRepository;
        this.petRepository = petRepository;
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
        this.raProtocolUtil = raProtocolUtil;
        this.specificationFilterConverter = specificationFilterConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDto> findById(@PathVariable Integer id) {
        Optional<Visit> petOptional = visitRepository.findById(id);
        return ResponseEntity.of(petOptional.map(visitMapper::toDto));
    }

    @GetMapping
    public ResponseEntity<List<VisitDto>> visitList(
            @RaFilter VisitListFilter filter,
            @RaRangeParam @RaSortParam Pageable pageable
    ) {
        Specification<Visit> specification = specificationFilterConverter.convert(filter);
        Page<Visit> page = visitRepository.findAll(specification, pageable);
        return raProtocolUtil.convertToResponseEntity(page, visitMapper::toDto);
    }

    @GetMapping("/check-availability")
    public ResponseEntity<Boolean> isDateAvailable(@RequestParam("vetId") Integer vetId,
                                                   @RequestParam("date") LocalDate visitDate) {
        Optional<Vet> vet = vetRepository.findById(vetId);
        if (vet.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean available = visitService.isDateSlotAvailable(vet.get(), visitDate);
        return ResponseEntity.ok(available);
    }

    @PostMapping("/request")
    public ResponseEntity<RequestVisitResponse> requestVisit(@RequestBody @Valid NewVisitRequest request) {
        if (request.vetId() == null && request.specialtyId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Pet pet = petRepository.findById(request.petId()).orElseThrow(EntityNotFoundException::new);

        Vet vet;
        if (request.vetId() != null) {
            vet = vetRepository.findById(request.vetId())
                    .orElseThrow(EntityNotFoundException::new); // 404 if not found

            if (!visitService.isDateSlotAvailable(vet, request.date())) {
                return ResponseEntity.ok(
                        RequestVisitResponse.fail("Requested vet isn't available at a given date")
                );
            }
        } else {
            vet = vetRepository.findBySpecialties_IdOrderByIdAsc(request.specialtyId())
                    .stream()
                    .filter(v -> visitService.isDateSlotAvailable(v, request.date()))
                    .findAny()
                    .orElse(null);

            if (vet == null) {
                return ResponseEntity.ok(
                        RequestVisitResponse.fail("Could not find any available vet for given specialty and date")
                );
            }
        }

        Visit createdVisit = visitService.createVisit(pet, vet, request.date(), request.description());
        return ResponseEntity.ok(
                new RequestVisitResponse(true, createdVisit.getId(), null)
        );
    }

    public record NewVisitRequest(
            @NotNull Integer petId,
            Integer specialtyId,
            Integer vetId,
            @Future @NotNull LocalDate date,
            @NotBlank String description) {
    }

    public record RequestVisitResponse(
            boolean success,
            Integer visitId,
            String errorMessage
    ) {
        public static RequestVisitResponse fail(String errorMessage) {
            return new RequestVisitResponse(false, null, errorMessage);
        }
    }

    public record VisitListFilter(
            @SpecFilterCondition(operator = SpecFilterOperator.CONTAINS, ignoreCase = true)
            String description,

            @SpecFilterCondition(property = "date", operator = SpecFilterOperator.LESS_OR_EQUALS)
            LocalDate dateBefore,

            @SpecFilterCondition(property = "date", operator = SpecFilterOperator.GREATER_OR_EQUALS)
            LocalDate dateAfter,

            @SpecFilterCondition(property = "assignedVet.id", operator = SpecFilterOperator.EQUALS)
            Integer assignedVetId
            ) {
    }
}

