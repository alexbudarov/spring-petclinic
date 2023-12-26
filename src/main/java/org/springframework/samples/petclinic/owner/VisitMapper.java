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

    @InheritConfiguration(name = "toEntity")
    Visit update(VisitDto visitDto, @MappingTarget Visit visit);

	@AfterMapping
	default void clearEmptyReferences(@MappingTarget Visit visit) {
		// workaround for https://github.com/mapstruct/mapstruct/issues/1166
		if (visit.getAssignedVet() != null && visit.getAssignedVet().getId() == null) {
			visit.setAssignedVet(null);
		}
		if (visit.getPet() != null && visit.getPet().getId() == null) {
			visit.setPet(null);
		}
	}

}
