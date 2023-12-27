package org.springframework.samples.petclinic.owner;

import org.mapstruct.*;
import org.springframework.samples.petclinic.vet.Vet;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VisitMapper {
    @Mapping(source = "assignedVetId", target = "assignedVet.id")
    @Mapping(source = "petId", target = "pet.id")
    Visit toEntity(VisitDto visitDto);

    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(source = "pet.owner.id", target = "petOwnerId")
    VisitDto toDto(Visit visit);

    // @InheritConfiguration(name = "toEntity")
	@Mapping(source = "petId", target = "pet")
	@Mapping(source = "assignedVetId", target = "assignedVet")
    Visit update(VisitDto visitDto, @MappingTarget Visit visit);

	default Pet visitPet(Integer petId) {
		if (petId == null) {
			return null;
		}
		Pet pet = new Pet();
		pet.setId(petId);
		return pet;
	}

	default Vet visitAssignedVet(Integer assignedVetId) {
		if (assignedVetId == null) {
			return null;
		}
		Vet vet = new Vet();
		vet.setId(assignedVetId);
		return vet;
	}
}
