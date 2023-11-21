package org.springframework.samples.petclinic.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.*;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetDto;
import org.springframework.samples.petclinic.vet.VetMapper;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/vet")
public class VeterinarianRestController {

    private final VetRepository vetRepository;
	private final RaProtocolUtil raProtocolUtil;
	private final VetMapper vetMapper;
	private final RaRangeSortConverter raRangeSortConverter;

	public VeterinarianRestController(VetRepository vetRepository,
									  RaProtocolUtil raProtocolUtil,
									  VetMapper vetMapper,
									  RaRangeSortConverter raRangeSortConverter) {
        this.vetRepository = vetRepository;
		this.raProtocolUtil = raProtocolUtil;
		this.vetMapper = vetMapper;
		this.raRangeSortConverter = raRangeSortConverter;
	}

    @GetMapping
    public ResponseEntity<List<VetDto>> findAll(@RaFilter VetListFilter filter, @RequestParam(required = false) String sort) {
		if (filter.id() != null) { // getMany
			List<VetDto> dtoList = vetRepository.findByIdIn(filter.id()).stream()
					.map(vetMapper::toDto)
					.toList();
			return ResponseEntity.ok(dtoList);
		}

		Sort springDataSort = raRangeSortConverter.convertToSort(sort);
		List<Vet> list = vetRepository.findAll(springDataSort);
		var response = raProtocolUtil.convertToResponseEntity(list.stream().map(vetMapper::toDto).toList());
		return response;
    }

	@PostMapping("/byspec")
	public List<VetDto> findBySpecialties_IdIn(@RequestBody Collection<Integer> ids) {
		List<Vet> vets = vetRepository.findBySpecialties_IdIn(ids);
		List<VetDto> vetDtos = vets.stream()
				.map(vetMapper::toDto)
				.collect(Collectors.toList());
		return vetDtos;
	}

	public record VetListFilter(Integer[] id) {
	}
}

