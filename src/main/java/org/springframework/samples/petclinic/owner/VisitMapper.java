package org.springframework.samples.petclinic.owner;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VisitMapper {
    @Mapping(source = "assignedVetId", target = "assignedVet.id")
    @Mapping(source = "petId", target = "pet.id")
    Visit toEntity(VisitDto visitDto);

    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(source = "pet.owner.id", target = "petOwnerId")
    VisitDto toDto(Visit visit);
}