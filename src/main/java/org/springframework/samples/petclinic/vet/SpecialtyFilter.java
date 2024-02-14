package org.springframework.samples.petclinic.vet;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record SpecialtyFilter(
        String q,

        Integer vetId
) {
        public Specification<Specialty> toSpecification() {
                return Specification.where(searchStringSpec())
                        .and(vetIdSpec());
        }

        private Specification<Specialty> searchStringSpec() {
                return ((root, query, cb) -> StringUtils.hasText(q)
                        ? cb.like(cb.lower(root.get("name")), q.toLowerCase() + "%")
                        : null);
        }

        private Specification<Specialty> vetIdSpec() {
                return ((root, query, cb) -> vetId != null
                        ? cb.equal(root.get("vets").get("id"), vetId)
                        : null);
        }
}
