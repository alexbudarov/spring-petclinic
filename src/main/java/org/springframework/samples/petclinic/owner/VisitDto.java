package org.springframework.samples.petclinic.owner;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

/**
 * DTO for {@link Visit}
 */
public final class VisitDto {
    private final Integer id;
    private final LocalDate date;
    private final @NotBlank String description;
    private final Integer petId;
    private final Integer assignedVetId;
    private final Integer petOwnerId;

    public VisitDto(Integer id, LocalDate date, @NotBlank String description, Integer petId,
                    Integer assignedVetId, Integer petOwnerId) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.petId = petId;
        this.assignedVetId = assignedVetId;
        this.petOwnerId = petOwnerId;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public @NotBlank String getDescription() {
        return description;
    }

    public Integer getPetId() {
        return petId;
    }

    public Integer getAssignedVetId() {
        return assignedVetId;
    }

    public Integer getPetOwnerId() {
        return petOwnerId;
    }
}
