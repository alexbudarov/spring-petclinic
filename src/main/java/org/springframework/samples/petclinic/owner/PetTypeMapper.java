package org.springframework.samples.petclinic.owner;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetTypeMapper {
    PetTypeDto toDto(PetType pet);
}