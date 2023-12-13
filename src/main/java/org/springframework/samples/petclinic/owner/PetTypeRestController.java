package org.springframework.samples.petclinic.owner;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/pet-type")
public class PetTypeRestController {

    private final RaProtocolUtil raProtocolUtil;
    private final PetTypeRepository petTypeRepository;
    private final SpecificationFilterConverter specificationFilterConverter;

    public PetTypeRestController(RaProtocolUtil raProtocolUtil,
                                 PetTypeRepository petTypeRepository,
                                 SpecificationFilterConverter specificationFilterConverter) {
        this.raProtocolUtil = raProtocolUtil;
        this.petTypeRepository = petTypeRepository;
        this.specificationFilterConverter = specificationFilterConverter;
    }

    @GetMapping
    public ResponseEntity<List<PetType>> getList(@RaFilter PetTypeListFilter filter,
                                                         @RaRangeParam @RaSortParam Pageable pageable) {
        if (filter.id() != null) { // getMany
            List<PetType> list = petTypeRepository.findAllById(filter.id());
            return ResponseEntity.ok(list);
        }

        Specification<PetType> specification = specificationFilterConverter.convert(filter);
        Page<PetType> page = petTypeRepository.findAll(specification, pageable);
        return raProtocolUtil.convertToResponseEntity(page);
    }

    public record PetTypeListFilter(
            List<Integer> id,

            @JsonProperty("q")
            @SpecFilterCondition(property = "name", operator = SpecFilterOperator.STARTS_WITH, ignoreCase = true)
            String searchString
    ) {
    }
}

