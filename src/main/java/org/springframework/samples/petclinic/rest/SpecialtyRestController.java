package org.springframework.samples.petclinic.rest;

import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.rasupport.*;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

        /*if (filter.id() != null) {
            Page<Specialty> page = specialtyRepository.findByIdIn(filter.id(), range.toPageable(sort));
            return raProtocolUtil.convertToResponseEntity(page, "specialty");
        }
        if (filter.vetId() != null) {
            Page<Specialty> page = specialtyRepository.findByVetId(filter.vetId(), range.toPageable(sort));
            return raProtocolUtil.convertToResponseEntity(page, "specialty");
        }*/

        Specification<Specialty> specification = convertToSpecification(filter);
        Page<Specialty> page = specialtyRepository.findAll(specification, range.toPageable(sort));
        var response = raProtocolUtil.convertToResponseEntity(page, "specialty");
        return response;
    }

    private Specification<Specialty> convertToSpecification(SpecialtyListFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // getMany()
            if (filter.id() != null) {
                predicates.add(root.get("id").in((Object[]) filter.id()));
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
            @SpecFilterCondition(operator = SpecFilterOperator.IN)
            Integer[] id,
            // custom
            Integer vetId
    ) {
    }
}

