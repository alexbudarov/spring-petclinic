package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;

/**
 * DTO for {@link Pet}
 */
public record PetDto(Integer id, String name, LocalDate birthDate, Integer typeId, Integer ownerId) {

    public static PetDto toDto(Pet pet, Integer ownerId) {
        return new PetDto(pet.getId(), pet.getName(), pet.getBirthDate(),
                pet.getType() != null ? pet.getType().getId() : null, ownerId);
    }

    public Pet toEntity() {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setBirthDate(birthDate);

        PetType type = new PetType();
        type.setId(typeId);
        pet.setType(type);

        return pet;
    }
}