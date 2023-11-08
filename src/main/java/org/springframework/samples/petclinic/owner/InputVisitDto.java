package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;

/**
 * DTO for {@link Visit}
 * Used as input for update() API operation.
 */
public record InputVisitDto(Integer id, LocalDate date, String description, Integer petId) {

}