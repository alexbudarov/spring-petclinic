package org.springframework.samples.petclinic.vet;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialtyMapper {
    Specialty toEntity(SpecialtyDto specialtyDto);

    SpecialtyDto toDto(Specialty specialty);

    Specialty update(SpecialtyDto specialtyDto, @MappingTarget Specialty specialty);
}