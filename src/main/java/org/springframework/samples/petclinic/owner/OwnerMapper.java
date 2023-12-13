package org.springframework.samples.petclinic.owner;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OwnerMapper {
    Owner toEntity(OwnerDto ownerDto);

    OwnerDto toDto(Owner owner);

    Owner update(OwnerDto ownerDto, @MappingTarget Owner owner);
}