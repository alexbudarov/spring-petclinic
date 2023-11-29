package org.springframework.samples.petclinic.vet;

import java.io.Serializable;

/**
 * DTO for {@link Specialty}
 */
public record SpecialtyDto(Integer id, String name) implements Serializable {
}