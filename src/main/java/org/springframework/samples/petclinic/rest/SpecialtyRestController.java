package org.springframework.samples.petclinic.rest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.RaFilter;
import org.springframework.samples.petclinic.rest.rasupport.RaProtocolUtil;
import org.springframework.samples.petclinic.rest.rasupport.RaRangeSort;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.SpecialtyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/specialty")
public class SpecialtyRestController {

    private final SpecialtyRepository specialtyRepository;
    private final RaProtocolUtil raProtocolUtil;

    public SpecialtyRestController(SpecialtyRepository specialtyRepository,
                                   RaProtocolUtil raProtocolUtil) {
        this.specialtyRepository = specialtyRepository;
        this.raProtocolUtil = raProtocolUtil;
    }

    @GetMapping
    public ResponseEntity<List<Specialty>> findAll(RaFilter filter, RaRangeSort range) {
        Object idFilterParam = filter.parameters.get("id");
        if (idFilterParam instanceof Object[]) {
            Page<Specialty> page = specialtyRepository.findByIdIn((Object[]) idFilterParam, range.pageable);
            return raProtocolUtil.convertToResponseEntity(page, "specialty");
        }

        Integer vetId = (Integer) filter.parameters.get("vetId");
        if (vetId != null) {
            Page<Specialty> page = specialtyRepository.findByVetId(vetId, range.pageable);
            return raProtocolUtil.convertToResponseEntity(page, "specialty");
        }

        Page<Specialty> page = specialtyRepository.findAll(range.pageable);

        var response = raProtocolUtil.convertToResponseEntity(page, "specialty");
        return response;
    }
}

