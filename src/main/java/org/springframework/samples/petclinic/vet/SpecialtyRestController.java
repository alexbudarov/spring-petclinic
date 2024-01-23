package org.springframework.samples.petclinic.vet;

import io.amplicode.rautils.filter.SpecFilterCondition;
import io.amplicode.rautils.filter.SpecFilterOperator;
import io.amplicode.rautils.filter.SpecificationFilterConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/specialty")
public class SpecialtyRestController {
    private final SpecialtyRepository specialtyRepository;
    private final SpecificationFilterConverter specificationFilterConverter;
    private final SpecialtyMapper specialtyMapper;

    public SpecialtyRestController(SpecialtyRepository specialtyRepository,
                                   SpecificationFilterConverter specificationFilterConverter,
                                   SpecialtyMapper specialtyMapper) {
        this.specialtyRepository = specialtyRepository;
        this.specificationFilterConverter = specificationFilterConverter;
        this.specialtyMapper = specialtyMapper;
    }

	@GetMapping(path="/by-ids")
	public List<SpecialtyDto> getListByIds(@RequestParam List<Integer> ids) {
		List<SpecialtyDto> entityList = specialtyRepository.findAllById(ids)
			.stream()
			.map(specialtyMapper::toDto)
			.toList();
		return entityList;
	}

    @GetMapping
    public Page<SpecialtyDto> getList(@ModelAttribute SpecialtyListFilter filter, Pageable pageable) {
        Specification<Specialty> specification = specificationFilterConverter.convert(filter);
        Page<Specialty> page = specialtyRepository.findAll(specification, pageable);
        return page.map(specialtyMapper::toDto);
    }

    public record SpecialtyListFilter(
            @SpecFilterCondition(property = "name", operator = SpecFilterOperator.STARTS_WITH, ignoreCase = true)
            String q,

			@SpecFilterCondition(property = "id", joinCollection = "vets")
			Integer vetId
    ) {
    }
}

