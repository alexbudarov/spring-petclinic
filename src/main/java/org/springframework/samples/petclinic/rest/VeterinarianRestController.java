package org.springframework.samples.petclinic.rest;

import org.springframework.data.domain.Page;
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

	public VeterinarianRestController(VetRepository vetRepository,
									  RaProtocolUtil raProtocolUtil,
									  VetMapper vetMapper) {
        this.vetRepository = vetRepository;
		this.raProtocolUtil = raProtocolUtil;
		this.vetMapper = vetMapper;
	}

    @GetMapping
    public ResponseEntity<List<VetDto>> findAll(@RaFilter VetListFilter filter, RaRange range, RaSort sort) {
		if (filter.id() != null) { // not used atm in the current UI
			Page<Vet> page = vetRepository.findByIdIn(filter.id(), range.toPageable(sort));
			return raProtocolUtil.convertToResponseEntity(page, vetMapper::toDto);
		}

		Page<Vet> page = vetRepository.findAll(range.toPageable(sort));
		var response = raProtocolUtil.convertToResponseEntity(page, vetMapper::toDto);
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

