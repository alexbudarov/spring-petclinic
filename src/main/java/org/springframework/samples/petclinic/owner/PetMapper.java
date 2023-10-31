package org.springframework.samples.petclinic.owner;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = MapperUtil.class)
public interface PetMapper {
    @Mapping(source = "typeId", target = "type")
    Pet toEntity(PetDto petDto);

    @Mapping(source = "type", target = "typeId")
    @Mapping(source = "id", target = "ownerId", qualifiedByName = "Pet#ownerId") // customization
    PetDto toDto(Pet pet);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "typeId", target = "type")
    Pet update(PetDto petDto, @MappingTarget Pet pet);
}