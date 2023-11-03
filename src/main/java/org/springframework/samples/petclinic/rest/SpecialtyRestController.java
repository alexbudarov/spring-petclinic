package org.springframework.samples.petclinic.rest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.*;
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
    public ResponseEntity<List<Specialty>> findAll(@RaFilter SpecialtyListFilter filter, RaRange range, RaSort sort) {
        if (filter.id() != null) {
            Page<Specialty> page = specialtyRepository.findByIdIn(filter.id(), range.toPageable(sort));
            return raProtocolUtil.convertToResponseEntity(page, "specialty");
        }

        // getManyReference()
        if (filter.vetId() != null) {
            Page<Specialty> page = specialtyRepository.findByVetId(filter.vetId(), range.toPageable(sort));
            return raProtocolUtil.convertToResponseEntity(page, "specialty");
        }

        Page<Specialty> page = specialtyRepository.findAll(range.toPageable(sort));
        var response = raProtocolUtil.convertToResponseEntity(page, "specialty");
        return response;
    }

    public record SpecialtyListFilter(
            Integer[] id, // used by DataProvider.getMany()
            Integer vetId) {
    }
}

