package org.springframework.samples.petclinic.vet;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link Vet}
 */
public record VetDto(Integer id, @NotBlank String firstName, @NotBlank String lastName)/* implements Serializable*/ {
}