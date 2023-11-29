package org.springframework.samples.petclinic.vet;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialtyMapper {
    SpecialtyDto toDto(Specialty specialty);
}