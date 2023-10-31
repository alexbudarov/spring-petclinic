package org.springframework.samples.petclinic.owner;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = MapperUtil.class)
public interface VisitMapper {
    Visit toEntity(VisitDto visitDto);

    @Mapping(source = "id", target = "petId", qualifiedByName = "Visit#petId") // customization
    VisitDto toDto(Visit visit);
}