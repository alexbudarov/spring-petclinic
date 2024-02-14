package org.springframework.samples.petclinic.owner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record PetFilter(
        String q,
        Integer ownerId
) {

        public Specification<Pet> toSpecification() {
                return Specification.where(searchStringSpec())
                        .and(ownerIdSpec());
        }

        private Specification<Pet> searchStringSpec() {
                return ((root, query, cb) -> StringUtils.hasText(q)
                        ? cb.like(cb.lower(root.get("name")), "%" + q.toLowerCase() + "%")
                        : null);
        }

        private Specification<Pet> ownerIdSpec() {
                return ((root, query, cb) -> ownerId != null
                        ? cb.equal(root.get("owner").get("id"), ownerId)
                        : null);
        }
}
