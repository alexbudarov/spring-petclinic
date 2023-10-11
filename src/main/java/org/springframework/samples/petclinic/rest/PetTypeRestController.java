package org.springframework.samples.petclinic.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.owner.PetTypeRepository;
import org.springframework.samples.petclinic.rest.rasupport.RaFilter;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
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

    public PetTypeRestController(OwnerRepository ownerRepository,
                                 RaProtocolUtil raProtocolUtil,
                                 PetTypeRepository petTypeRepository) {
        this.ownerRepository = ownerRepository;
        this.raProtocolUtil = raProtocolUtil;
        this.petTypeRepository = petTypeRepository;
    }

    @GetMapping
    public ResponseEntity<List<PetType>> findPetTypes(RaFilter filter) {
        Object idFilterParam = filter.parameters.get("id");
        if (idFilterParam instanceof Object[]) {
            List<PetType> list = petTypeRepository.findByIdIn((Object[]) idFilterParam);
            return raProtocolUtil.convertToResponseEntity(list, "pet-type");
        }

        List<PetType> petTypes = ownerRepository.findPetTypes();

        ResponseEntity<List<PetType>> response = raProtocolUtil.convertToResponseEntity(petTypes, "pet-type");
        return response;
    }
}

