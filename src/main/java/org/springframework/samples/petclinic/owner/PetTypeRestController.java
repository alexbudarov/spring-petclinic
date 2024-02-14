package org.springframework.samples.petclinic.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/pet-type")
public class PetTypeRestController {

    private final PetTypeRepository petTypeRepository;

    public PetTypeRestController(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

	@GetMapping(path="/by-ids")
	public List<PetType> getListById(@RequestParam List<Integer> ids) {
		List<PetType> list = petTypeRepository.findAllById(ids);
		return list;
	}

    @GetMapping
    public Page<PetType> getList(@ModelAttribute PetTypeFilter filter, Pageable pageable) {
        Specification<PetType> specification = filter.toSpecification();
        return petTypeRepository.findAll(specification, pageable);
    }
}

