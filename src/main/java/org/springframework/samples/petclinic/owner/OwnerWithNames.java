package org.springframework.samples.petclinic.owner;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link Owner}
 */
public record OwnerWithNames(@NotNull Integer id, String firstName, String lastName) {

    public static OwnerWithNames toDto(Owner owner) {
        return new OwnerWithNames(owner.getId(), owner.getFirstName(), owner.getLastName());
    }

    public Owner toEntity() {
        Owner owner = new Owner();
        owner.setId(id());
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        return owner;
    }
}