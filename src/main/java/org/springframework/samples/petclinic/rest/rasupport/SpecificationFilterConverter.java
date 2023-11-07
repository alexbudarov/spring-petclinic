package org.springframework.samples.petclinic.rest.rasupport;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

@Component
public class SpecificationFilterConverter {

    /**
     * Convert static filter object to JPA specification.
     * Use {@link SpecFilterCondition} annotations as declaration of filter conditions.
     */
    public <T> Specification<T> convert(Object filter, Class<T> entityClass) {
        Map<Field, SpecFilterCondition> conditions = getDeclaredConditions(filter.getClass());
        List<PredicateFactory> predicateFactories = new ArrayList<>();
        for (Map.Entry<Field, SpecFilterCondition> entry: conditions.entrySet()) {
            Optional<PredicateFactory> predicate = convertToPredicate(entry.getKey(), entry.getValue(), filter);
            predicate.ifPresent(predicateFactories::add);
        }

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = predicateFactories.stream()
                    .map(pf -> pf.getLeafPredicate(root, criteriaBuilder))
                    .toList();

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Map<Field, SpecFilterCondition> getDeclaredConditions(Class<?> filterClass) {
        Map<Field, SpecFilterCondition> res = new HashMap<>();
        ReflectionUtils.doWithFields(filterClass,
                fc -> {
                    SpecFilterCondition annotation = fc.getAnnotation(SpecFilterCondition.class);
                    res.put(fc, annotation);
                }, f -> {
                    return f.isAnnotationPresent(SpecFilterCondition.class) && !Modifier.isStatic(f.getModifiers());
                }
        );
        return res;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Optional<PredicateFactory> convertToPredicate(Field field, SpecFilterCondition condition, Object filter) {
        field.setAccessible(true);
        Object filterValue = ReflectionUtils.getField(field, filter);
        if (filterValue == null) {
            return Optional.empty();
        }

        String propertyName = !condition.property().isEmpty() ? condition.property() : field.getName();

        PredicateFactory res =  (r, cb) -> {
            SpecFilterOperator op = condition.operator();
            Expression<?> leftArg = r.get(propertyName);
            Object rightArg = filterValue;

            // handler lowerCase option
            if (op == SpecFilterOperator.EQUALS || op == SpecFilterOperator.NOT_EQUALS || op == SpecFilterOperator.CONTAINS
                    || op == SpecFilterOperator.STARTS_WITH || op == SpecFilterOperator.ENDS_WITH) {
                if (condition.ignoreCase() && filterValue instanceof String) {
                    leftArg = cb.lower(r.get(propertyName));
                    rightArg = ((String) filterValue).toLowerCase();
                }
            }

            switch (op) {
                case EQUALS -> {
                    return cb.equal(leftArg, rightArg);
                }
                case NOT_EQUALS -> {
                    return cb.notEqual(leftArg, rightArg);
                }
                case CONTAINS -> {
                    return cb.like((Expression<String>) leftArg, "%" + rightArg + "%");
                }
                case STARTS_WITH -> {
                    return cb.like((Expression<String>) leftArg, rightArg + "%");
                }
                case ENDS_WITH -> {
                    return cb.like((Expression<String>) leftArg, "%" + rightArg);
                }
                case LESS -> {
                    return cb.lessThan((Expression<? extends Comparable>) leftArg, (Comparable) rightArg);
                }
                case LESS_OR_EQUALS -> {
                    return cb.lessThanOrEqualTo((Expression<? extends Comparable>) leftArg, (Comparable) rightArg);
                }
                case GREATER -> {
                    return cb.greaterThan((Expression<? extends Comparable>) leftArg, (Comparable) rightArg);
                }
                case GREATER_OR_EQUALS -> {
                    return cb.greaterThanOrEqualTo((Expression<? extends Comparable>) leftArg, (Comparable) rightArg);
                }
                case IN -> {
                    return leftArg.in((Object[]) rightArg);
                }
                case NOT_IN -> {
                    return cb.not(leftArg.in((Object[]) rightArg));
                }
                case IS_SET -> {
                    return Boolean.TRUE.equals(rightArg) ? leftArg.isNotNull() : leftArg.isNull();
                }
                case IS_NOT_SET -> {
                    return Boolean.TRUE.equals(rightArg) ? leftArg.isNull() : leftArg.isNotNull();
                }
                default -> throw new UnsupportedOperationException("Not supported yet: " + op);
            }
        };
        return Optional.of(res);
    }

    @FunctionalInterface
    private interface PredicateFactory {
        Predicate getLeafPredicate(Root<?> root, CriteriaBuilder criteriaBuilder);
    }

}
