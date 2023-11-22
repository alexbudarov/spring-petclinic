package org.springframework.samples.petclinic.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.Medicine;
import org.springframework.samples.petclinic.owner.MedicineRepository;
import org.springframework.samples.petclinic.rest.rasupport.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/medicine")
public class MedicineRestController {

    private final MedicineRepository medicineRepository;
    private final RaProtocolUtil raProtocolUtil;
    private final SpecificationFilterConverter specificationFilterConverter;

    public MedicineRestController(MedicineRepository medicineRepository,
                                  RaProtocolUtil raProtocolUtil,
                                  SpecificationFilterConverter specificationFilterConverter) {
        this.medicineRepository = medicineRepository;
        this.raProtocolUtil = raProtocolUtil;
        this.specificationFilterConverter = specificationFilterConverter;
    }

    @GetMapping
    public ResponseEntity<List<Medicine>> findAll(@RaFilter MedicineListFilter filter,
                                                   RaRange range, RaSort sort) {
        if (filter.id() != null) { // getMany
            List<Medicine> entityList = medicineRepository.findByIdIn(filter.id());
            return ResponseEntity.ok(entityList);
        }
        Specification<Medicine> specification = specificationFilterConverter.convert(filter);
        Page<Medicine> page = medicineRepository.findAll(specification, range.toPageable(sort));
        var response = raProtocolUtil.convertToResponseEntity(page);
        return response;
    }

    public record MedicineListFilter(
            Integer[] id,

            @SpecFilterCondition(operator = SpecFilterOperator.CONTAINS, ignoreCase = true)
            String name
    ) {
    }
}

