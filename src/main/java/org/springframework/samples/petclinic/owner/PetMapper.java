package org.springframework.samples.petclinic.owner;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapper {
    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(source = "typeId", target = "type.id")
    Pet toEntity(PetDto petDto);

    @InheritInverseConfiguration(name = "toEntity")
    PetDto toDto(Pet pet);

	// org.hibernate.HibernateException: identifier of an instance of org.springframework.samples.petclinic.owner.PetType was altered from 6 to 2
	// !!!
    // @InheritConfiguration(name = "toEntity")
	@Mapping(source = "typeId", target = "type")
	@Mapping(source = "ownerId", target = "owner")
    Pet update(PetDto petDto, @MappingTarget Pet pet);

	default PetType petType(Integer typeId) {
		if (typeId == null) {
			return null;
		}
		PetType type = new PetType();
		type.setId(typeId);
		return type;
	}

	default Owner petOwner(Integer ownerId) {
		if (ownerId == null) {
			return null;
		}
		Owner owner = new Owner();
		owner.setId(ownerId);
		return owner;
	}
}
