package org.springframework.samples.petclinic.owner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record PetTypeFilter(
        String q
) {
        public Specification<PetType> toSpecification() {
                return Specification.where(searchStringSpec());
        }

        private Specification<PetType> searchStringSpec() {
                return ((root, query, cb) -> StringUtils.hasText(q)
                        ? cb.like(cb.lower(root.get("name")), q.toLowerCase() + "%")
                        : null);
        }
}
