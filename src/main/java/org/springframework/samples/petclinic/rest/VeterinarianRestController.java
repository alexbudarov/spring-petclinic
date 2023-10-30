package org.springframework.samples.petclinic.rest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.RaFilter;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSort;
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
    public ResponseEntity<List<Vet>> findAll(RaFilter filter, RaRangeSort range) {
		Object idFilterParam = filter.parameters.get("id");
		if (idFilterParam instanceof Object[]) {
			Page<Vet> page = vetRepository.findByIdIn((Object[]) idFilterParam, range.pageable);
			return raProtocolUtil.convertToResponseEntity(page, "vet");
		}

		Page<Vet> page = vetRepository.findAll(range.pageable);

		ResponseEntity<List<Vet>> response = raProtocolUtil.convertToResponseEntity(page, "vet");
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
}

