package org.springframework.samples.petclinic.vet;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface VetMapper {
    Vet toEntity(VetDto vetDto);

    VetDto toDto(Vet vet);

    Vet update(VetDto vetDto, @MappingTarget Vet vet);
}