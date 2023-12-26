package org.springframework.samples.petclinic.vet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

