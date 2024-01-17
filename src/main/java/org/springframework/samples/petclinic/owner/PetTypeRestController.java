package org.springframework.samples.petclinic.owner;

import com.amplicode.restutils.filter.SpecFilterCondition;
import com.amplicode.restutils.filter.SpecFilterOperator;
import com.amplicode.restutils.filter.SpecificationFilterConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/pet-type")
public class PetTypeRestController {

    private final PetTypeRepository petTypeRepository;
    private final SpecificationFilterConverter specificationFilterConverter;

    public PetTypeRestController(PetTypeRepository petTypeRepository,
                                 SpecificationFilterConverter specificationFilterConverter) {
        this.petTypeRepository = petTypeRepository;
        this.specificationFilterConverter = specificationFilterConverter;
    }

	@GetMapping(path="/by-ids")
	public List<PetType> getListById(@RequestParam List<Integer> ids) {
		List<PetType> list = petTypeRepository.findAllById(ids);
		return list;
	}

    @GetMapping
    public Page<PetType> getList(@ModelAttribute PetTypeListFilter filter, Pageable pageable) {
        Specification<PetType> specification = specificationFilterConverter.convert(filter);
        return petTypeRepository.findAll(specification, pageable);
    }

    public record PetTypeListFilter(
            @SpecFilterCondition(property = "name", operator = SpecFilterOperator.STARTS_WITH, ignoreCase = true)
            String q
    ) {
    }
}

