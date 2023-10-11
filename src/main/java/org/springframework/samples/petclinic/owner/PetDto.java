package org.springframework.samples.petclinic.owner;

import jakarta.validation.Valid;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Pet}
 */
public record PetDto(Integer id, String name, LocalDate birthDate, PetTypeDto type, @Valid OwnerWithNames owner) implements Serializable {

    public static PetDto toDto(Pet pet, OwnerWithNames ownerDto) {
        return new PetDto(pet.getId(), pet.getName(), pet.getBirthDate(), PetTypeDto.toDto(pet.getType()), ownerDto);
    }

    public Pet toEntity() {
        Pet pet = new Pet();
        pet.setId(id());
        pet.setName(name());
        pet.setBirthDate(birthDate());
        pet.setType(type().toEntity());
        return pet;
    }
}