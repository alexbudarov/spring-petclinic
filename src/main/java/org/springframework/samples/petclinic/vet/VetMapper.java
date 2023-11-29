package org.springframework.samples.petclinic.vet;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface VetMapper {
    VetDto toDto(Vet vet);
}