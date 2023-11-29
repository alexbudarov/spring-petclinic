package org.springframework.samples.petclinic.owner;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * DTO for {@link Pet}
 */
public record PetDto(Integer id, @NotNull String name, LocalDate birthDate, @NotNull Integer typeId, @NotNull Integer ownerId) {

}
