package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VisitService {
    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public boolean isDateSlotAvailable(Vet vet, LocalDate visitDate) {
        boolean conflictExists = visitRepository.existVisitsByDate(vet.getId(), visitDate);
        return !conflictExists;
    }

    public Visit createVisit(Pet pet, Vet vet, LocalDate date, String description) {
        Visit visit = new Visit();
        visit.setPet(pet);
        visit.setAssignedVet(vet);
        visit.setDate(date);
        visit.setDescription(description);
        return visitRepository.save(visit);
    }
}
