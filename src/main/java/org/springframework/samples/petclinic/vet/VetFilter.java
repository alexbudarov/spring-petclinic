package org.springframework.samples.petclinic.vet;

import org.springframework.data.jpa.domain.Specification;

public record VetFilter(
        Integer specialtyId
) {
    public Specification<Vet> toSpecification() {
        return Specification.where(specialtyIdSpec());
    }

    private Specification<Vet> specialtyIdSpec() {
        return ((root, query, cb) -> specialtyId != null
                ? cb.equal(root.get("specialties").get("id"), specialtyId)
                : null);
    }
}
