package org.springframework.samples.petclinic.owner;

import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerMapper {
    Owner toEntity(OwnerDto ownerDto);

    @Mapping(target = "petIds", expression = "java(petsToPetIds(owner.getPets()))")
    OwnerDto toDto(Owner owner);

    Owner update(OwnerDto ownerDto, @MappingTarget Owner owner);

    default List<Integer> petsToPetIds(List<Pet> pets) {
        return pets.stream().map(Pet::getId).collect(Collectors.toList());
    }
}