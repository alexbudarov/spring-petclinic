package org.springframework.samples.petclinic.owner;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapper {
    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "ownerId", target = "owner")
    Pet toEntity(PetDto petDto);

    @Mapping(source = "type", target = "typeId")
    @Mapping(source = "owner", target = "ownerId")
    PetDto toDto(Pet pet);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "ownerId", target = "owner")
    Pet update(PetDto petDto, @MappingTarget Pet pet);

    default PetType type(Integer typeId) {
        if (typeId == null) {
            return null;
        }
        PetType type = new PetType();
        type.setId(typeId);
        return type;
    }

    default Integer typeId(PetType type) {
        return type != null ? type.getId() : null;
    }

    default Owner owner(Integer ownerId) {
        if (ownerId == null) {
            return null;
        }
        Owner owner = new Owner();
        owner.setId(ownerId);
        return owner;
    }

    default Integer ownerId(Owner owner) {
        return owner != null ? owner.getId() : null;
    }
}