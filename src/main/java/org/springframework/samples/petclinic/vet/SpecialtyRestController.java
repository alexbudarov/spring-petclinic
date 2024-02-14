package org.springframework.samples.petclinic.vet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/specialty")
public class SpecialtyRestController {
    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    public SpecialtyRestController(SpecialtyRepository specialtyRepository,
                                   SpecialtyMapper specialtyMapper) {
        this.specialtyRepository = specialtyRepository;
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
    public Page<SpecialtyDto> getList(@ModelAttribute SpecialtyFilter filter, Pageable pageable) {
        Specification<Specialty> specification = filter.toSpecification();
        Page<Specialty> page = specialtyRepository.findAll(specification, pageable);
        return page.map(specialtyMapper::toDto);
    }

}

