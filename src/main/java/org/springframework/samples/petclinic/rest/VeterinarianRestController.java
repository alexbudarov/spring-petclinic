package org.springframework.samples.petclinic.rest;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/vet")
public class VeterinarianRestController {

    private final VetRepository vetRepository;

    public VeterinarianRestController(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @GetMapping
    public Page<Vet> findAll(Pageable pageable) throws DataAccessException {
        return vetRepository.findAll(pageable);
    }

}

