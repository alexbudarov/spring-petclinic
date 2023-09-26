package org.springframework.samples.petclinic.rest;

import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/rest/pet-type")
public class PetTypeRestController {

    private final OwnerRepository ownerRepository;

    public PetTypeRestController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @GetMapping
    public List<PetType> findPetTypes() {
        return ownerRepository.findPetTypes();
    }
}

