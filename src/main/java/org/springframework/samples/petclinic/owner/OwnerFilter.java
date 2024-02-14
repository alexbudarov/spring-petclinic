package org.springframework.samples.petclinic.owner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record OwnerFilter(
        String q,
        String telephone
) {

    public Specification<Owner> toSpecification() {
        return Specification.where(firstNameSpec().or(lastNameSpec()))
                .and(telephoneSpec());
    }

    private Specification<Owner> telephoneSpec() {
        return ((root, query, cb) -> StringUtils.hasText(telephone)
                ? cb.like(cb.lower(root.get("telephone")), telephone.toLowerCase() + "%")
                : null);
    }


    private Specification<Owner> firstNameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(q)
                ? cb.like(cb.lower(root.get("firstName")), "%" + q.toLowerCase() + "%")
                : null);
    }

    private Specification<Owner> lastNameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(q)
                ? cb.like(cb.lower(root.get("lastName")), "%" + q.toLowerCase() + "%")
                : null);
    }
}
