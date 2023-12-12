package org.springframework.samples.petclinic.rest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.owner.Visit;
import org.springframework.samples.petclinic.owner.VisitService;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/rest/visit")
public class VisitRestController {

    private final VisitService visitService;
    private final VetRepository vetRepository;
    private final PetRepository petRepository;
    private final SpecialtyRepository specialtyRepository;

    public VisitRestController(VisitService visitService,
                               VetRepository vetRepository,
                               PetRepository petRepository,
                               SpecialtyRepository specialtyRepository) {
        this.visitService = visitService;
        this.vetRepository = vetRepository;
        this.petRepository = petRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @GetMapping("/check-availability")
    public ResponseEntity<Boolean> isDateAvailable(@RequestParam("specialtyId") Integer specialtyId,
                                                   @RequestParam("date") LocalDate visitDate) {
        Optional<Specialty> specialty = specialtyRepository.findById(specialtyId);
        if (specialty.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean available = vetRepository.findBySpecialties_IdOrderByIdAsc(specialtyId)
                .stream()
                .anyMatch(v -> visitService.isDateSlotAvailable(v, visitDate));

        return ResponseEntity.ok(available);
    }

    @PostMapping("/request")
    public ResponseEntity<RequestVisitResponse> requestVisit(@RequestBody @Valid NewVisitRequest request) {
        Pet pet = petRepository.findById(request.petId()).orElseThrow(EntityNotFoundException::new);

        Vet vet = vetRepository.findBySpecialties_IdOrderByIdAsc(request.specialtyId())
                .stream()
                .filter(v -> visitService.isDateSlotAvailable(v, request.date()))
                .findAny()
                .orElse(null);

        if (vet == null) {
            return ResponseEntity.ok(
                    RequestVisitResponse.fail("Could not find any available vet for given specialty and date")
            );
        }

        Visit createdVisit = visitService.createVisit(pet, vet, request.date(), request.description());
        return ResponseEntity.ok(
                new RequestVisitResponse(true, createdVisit.getId(), null)
        );
    }

    public record NewVisitRequest(
            @NotNull Integer petId,
            @NotNull Integer specialtyId,
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
}

