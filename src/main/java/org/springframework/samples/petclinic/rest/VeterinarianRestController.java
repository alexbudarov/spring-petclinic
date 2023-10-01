package org.springframework.samples.petclinic.rest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSort;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/vet")
public class VeterinarianRestController {

    private final VetRepository vetRepository;
	private final RaProtocolUtil raProtocolUtil;

	public VeterinarianRestController(VetRepository vetRepository,
									  RaProtocolUtil raProtocolUtil) {
        this.vetRepository = vetRepository;
		this.raProtocolUtil = raProtocolUtil;
	}

    @GetMapping
    public ResponseEntity<List<Vet>> findAll(RaRangeSort range) {
		Page<Vet> page = vetRepository.findAll(range.pageable);

		ResponseEntity<List<Vet>> response = raProtocolUtil.convertToResponseEntity(page, "vet");
		return response;
    }
}

