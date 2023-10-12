package org.springframework.samples.petclinic.owner;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

/**
 * DTO for {@link Visit}
 */
public record VisitDto(Integer id, LocalDate date, @NotBlank String description, Integer petId) {
    public static VisitDto toDto(Visit visit, Integer petId) {
        return new VisitDto(visit.getId(), visit.getDate(), visit.getDescription(), petId);
    }

    public Visit toEntity() {
        Visit visit = new Visit();
        visit.setId(id);
        visit.setDate(date);
        visit.setDescription(description);

        return visit;
    }
}