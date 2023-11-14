package org.springframework.samples.petclinic.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/pet-type")
public class PetTypeRestController {

    private final OwnerRepository ownerRepository;
    private final RaProtocolUtil raProtocolUtil;
    private final PetTypeRepository petTypeRepository;
    private final PetTypeMapper petTypeMapper;

    public PetTypeRestController(OwnerRepository ownerRepository,
                                 RaProtocolUtil raProtocolUtil,
                                 PetTypeRepository petTypeRepository,
                                 PetTypeMapper petTypeMapper) {
        this.ownerRepository = ownerRepository;
        this.raProtocolUtil = raProtocolUtil;
        this.petTypeRepository = petTypeRepository;
        this.petTypeMapper = petTypeMapper;
    }

    @GetMapping
    public ResponseEntity<List<PetTypeDto>> findPetTypes(@RaFilter PetTypeListFilter filter) {
        if (filter.id() != null) { // getMany
            List<PetTypeDto> list = petTypeRepository.findByIdIn(filter.id())
                    .stream()
                    .map(petTypeMapper::toDto)
                    .toList();
            return ResponseEntity.ok(list);
        }

        List<PetTypeDto> petTypes = ownerRepository.findPetTypes()
                .stream()
                .map(petTypeMapper::toDto)
                .toList();

        var response = raProtocolUtil.convertToResponseEntity(petTypes);
        return response;
    }

    public record PetTypeListFilter(Integer[] id) {
    }
}

