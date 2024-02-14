package org.springframework.samples.petclinic.vet;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/vet")
public class VetRestController {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

	public VetRestController(VetRepository vetRepository,
                             VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
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
	public List<VetDto> getList(@ModelAttribute VetFilter filter, Sort sort) { // no paging; only sorting
		Specification<Vet> specification = filter.toSpecification();
		return vetRepository.findAll(specification, sort)
			.stream()
			.map(vetMapper::toDto)
			.toList();
	}

}

