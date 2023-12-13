package org.springframework.samples.petclinic.vet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.RaFilter;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeParam;
import org.springframework.samples.petclinic.rest.rasupport.RaSortParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/vet")
public class VetRestController {

    private final VetRepository vetRepository;
    private final RaProtocolUtil raProtocolUtil;
    private final VetMapper vetMapper;

    public VetRestController(VetRepository vetRepository,
                             RaProtocolUtil raProtocolUtil,
                             VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.raProtocolUtil = raProtocolUtil;
        this.vetMapper = vetMapper;
    }

    @GetMapping
    public ResponseEntity<List<VetDto>> findAll(@RaFilter VetListFilter filter, @RaRangeParam @RaSortParam Pageable pageable) {
        if (filter.id() != null) { // getMany
            List<VetDto> dtoList = vetRepository.findAllById(filter.id())
                    .stream()
                    .map(vetMapper::toDto)
                    .toList();
            return ResponseEntity.ok(dtoList);
        }

        Page<Vet> page = vetRepository.findAll(pageable);
        return raProtocolUtil.convertToResponseEntity(page, vetMapper::toDto);
    }

    public record VetListFilter(
            List<Integer> id
    ) {
    }
}

