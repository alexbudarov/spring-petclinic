package org.springframework.samples.petclinic.vet;

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
@RequestMapping("/rest/specialty")
public class SpecialtyRestController {
    private final SpecialtyRepository specialtyRepository;
    private final RaProtocolUtil raProtocolUtil;
    private final SpecificationFilterConverter specificationFilterConverter;
    private final SpecialtyMapper specialtyMapper;

    public SpecialtyRestController(SpecialtyRepository specialtyRepository,
                                   RaProtocolUtil raProtocolUtil,
                                   SpecificationFilterConverter specificationFilterConverter,
                                   SpecialtyMapper specialtyMapper) {
        this.specialtyRepository = specialtyRepository;
        this.raProtocolUtil = raProtocolUtil;
        this.specificationFilterConverter = specificationFilterConverter;
        this.specialtyMapper = specialtyMapper;
    }

    @GetMapping
    public ResponseEntity<List<SpecialtyDto>> findAll(@RaFilter SpecialtyListFilter filter,
                                                   @RaRangeParam @RaSortParam Pageable pageable) {
        if (filter.id() != null) { // getMany
            List<SpecialtyDto> entityList = specialtyRepository.findAllById(filter.id())
                    .stream()
                    .map(specialtyMapper::toDto)
                    .toList();
            return ResponseEntity.ok(entityList);
        }

        Specification<Specialty> specification = specificationFilterConverter.convert(filter);
        Page<Specialty> page = specialtyRepository.findAll(specification, pageable);
        return raProtocolUtil.convertToResponseEntity(page, specialtyMapper::toDto);
    }

    public record SpecialtyListFilter(
            List<Integer> id,

            @JsonProperty("q")
            @SpecFilterCondition(property = "name", operator = SpecFilterOperator.STARTS_WITH, ignoreCase = true)
            String searchString
    ) {
    }
}

