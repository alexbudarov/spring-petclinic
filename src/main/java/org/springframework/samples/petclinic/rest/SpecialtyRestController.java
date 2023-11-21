package org.springframework.samples.petclinic.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.*;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/specialty")
public class SpecialtyRestController {

    private final SpecialtyRepository specialtyRepository;
    private final RaProtocolUtil raProtocolUtil;
    private final RaRangeSortConverter raRangeSortConverter;

    public SpecialtyRestController(SpecialtyRepository specialtyRepository,
                                   RaProtocolUtil raProtocolUtil,
                                   RaRangeSortConverter raRangeSortConverter) {
        this.specialtyRepository = specialtyRepository;
        this.raProtocolUtil = raProtocolUtil;
        this.raRangeSortConverter = raRangeSortConverter;
    }

    @GetMapping
    public ResponseEntity<List<Specialty>> findAll(@RaFilter SpecialtyListFilter filter,
                                                   @RequestParam(required = false) String range) {
        if (filter.id() != null) { // getMany
            List<Specialty> entityList = specialtyRepository.findByIdIn(filter.id());
            return ResponseEntity.ok(entityList);
        }
        /*if (filter.searchString() != null) {
            Page<Specialty> page = specialtyRepository.findByNameStartsWithIgnoreCase(filter.searchString(), range.toPageable(sort));
            return raProtocolUtil.convertToResponseEntity(page);
        }
        if (filter.vetId() != null) {
            Page<Specialty> page = specialtyRepository.findByVetId(filter.vetId(), range.toPageable(sort));
            return raProtocolUtil.convertToResponseEntity(page, "specialty");
        }*/

        Specification<Specialty> specification = convertToSpecification(filter);
        Pageable pageable = raRangeSortConverter.convertToPageable(range);
        Page<Specialty> page = specialtyRepository.findAll(specification, pageable);
        var response = raProtocolUtil.convertToResponseEntity(page);
        return response;
    }

    private Specification<Specialty> convertToSpecification(SpecialtyListFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.searchString() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        filter.searchString().toLowerCase() + "%" // starts with
                ));
            }

            // getManyReference() in VetList
            // custom condition
            if (filter.vetId() != null) {
                From<Vet, Vet> vetFrom = query.from(Vet.class);
                Join<Vet, Specialty> specJoin = vetFrom.join("specialties");

                predicates.add(criteriaBuilder.equal(vetFrom.get("id"), filter.vetId()));
                predicates.add(criteriaBuilder.equal(specJoin, root));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public record SpecialtyListFilter(
            Integer[] id,
            @JsonProperty("q") String searchString,
            // custom
            Integer vetId
    ) {
    }
}

