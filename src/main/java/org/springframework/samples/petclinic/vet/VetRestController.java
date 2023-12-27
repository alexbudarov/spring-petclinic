package org.springframework.samples.petclinic.vet;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.SpecFilterCondition;
import org.springframework.samples.petclinic.rest.rasupport.SpecFilterOperator;
import org.springframework.samples.petclinic.rest.rasupport.SpecificationFilterConverter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/vet")
public class VetRestController {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

	private final SpecificationFilterConverter specificationFilterConverter;

	public VetRestController(VetRepository vetRepository,
                             VetMapper vetMapper,
							 SpecificationFilterConverter specificationFilterConverter) {
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
		this.specificationFilterConverter = specificationFilterConverter;
	}

	@GetMapping(path="/by-ids")
    public ResponseEntity<List<VetDto>> getListByIds(@RequestParam List<Integer> ids) {
		List<VetDto> dtoList = vetRepository.findAllById(ids)
				.stream()
				.map(vetMapper::toDto)
				.toList();
		return ResponseEntity.ok(dtoList);
    }

	@GetMapping
	public List<VetDto> getList(@ModelAttribute VetListFilter filter, Sort sort) { // no paging; only sorting
		Specification<Vet> specification = specificationFilterConverter.convert(filter);
		return vetRepository.findAll(specification, sort)
			.stream()
			.map(vetMapper::toDto)
			.toList();
	}

	public record VetListFilter(
		@SpecFilterCondition(property = "id", joinCollection = "specialties")
		Integer specialtyId
	) {
	}
}

