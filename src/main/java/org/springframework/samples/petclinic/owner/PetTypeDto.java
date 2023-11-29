package org.springframework.samples.petclinic.owner;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link PetType}
 */
public record PetTypeDto(@NotNull Integer id, String name) {

}