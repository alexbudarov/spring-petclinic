package org.springframework.samples.petclinic.owner;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link PetType}
 */
public record PetTypeDto(@NotNull Integer id, String name) implements Serializable {

    public static PetTypeDto toDto(PetType type) {
        return new PetTypeDto(type.getId(), type.getName());
    }

    public PetType toEntity() {
        PetType type = new PetType();
        type.setId(id());
        type.setName(name());
        return type;
    }
}