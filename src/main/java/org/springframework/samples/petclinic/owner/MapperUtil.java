package org.springframework.samples.petclinic.owner;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MapperUtil {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    public MapperUtil(OwnerRepository ownerRepository,
                      PetRepository petRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }

    // customization: handle additional field in PetDto
    @Named("Pet#ownerId")
    public Integer ownerId(Integer petId) {
        if (petId == null) {
            return null;
        }
        Optional<Owner> owner = ownerRepository.findByPet(petId);
        return owner.map(Owner::getId).orElse(null);
    }

    // normal implementation for associations
    public PetType type(Integer typeId) {
        if (typeId == null) {
            return null;
        }
        PetType type = new PetType();
        type.setId(typeId);
        return type;
    }

    // normal implementation for associations
    public Integer typeId(PetType type) {
        return type != null ? type.getId() : null;
    }

    // customization: handle additional field in PetDto
    @Named("Visit#petId")
    public Integer petId(Integer visitId) {
        if (visitId == null) {
            return null;
        }
        return petRepository.loadPetByVisit(visitId);
    }
}
