package org.springframework.samples.petclinic.rest;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.samples.petclinic.owner.Visit;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public record VisitFilter(
        String description,
        LocalDate dateBefore,
        LocalDate dateAfter
) {
        public Specification<Visit> toSpecification() {
                return Specification.where(descriptionSpec())
                        .and(dateBeforeSpec())
                        .and(dateAfterSpec());
        }

        private Specification<Visit> descriptionSpec() {
                return ((root, query, cb) -> StringUtils.hasText(description)
                        ? cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%")
                        : null);
        }

        private Specification<Visit> dateBeforeSpec() {
                return ((root, query, cb) -> dateBefore != null
                        ? cb.lessThanOrEqualTo(root.get("date"), dateBefore)
                        : null);
        }

        private Specification<Visit> dateAfterSpec() {
                return ((root, query, cb) -> dateAfter != null
                        ? cb.greaterThanOrEqualTo(root.get("date"), dateAfter)
                        : null);
        }
}
