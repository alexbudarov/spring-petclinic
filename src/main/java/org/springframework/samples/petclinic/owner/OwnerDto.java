package org.springframework.samples.petclinic.owner;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link Owner}
 */
public record OwnerDto(Integer id, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String address,
                       @NotBlank String city,
                       @Digits(integer = 10, fraction = 0) @NotBlank String telephone) implements Serializable {
}