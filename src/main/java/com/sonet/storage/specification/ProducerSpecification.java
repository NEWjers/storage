package com.sonet.storage.specification;

import com.sonet.storage.model.producer.Producer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


public class ProducerSpecification {

    public static Specification<Producer> getProducerSpecification(String id, String name, String country, String description) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null && !id.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<Long>get("id"), Long.parseLong(id));
                predicates.add(predicate);
            }

            if (name != null && !name.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<String>get("name"), name);
                predicates.add(predicate);
            }

            if (country != null && !country.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<String>get("country"), country);
                predicates.add(predicate);
            }

            if (description != null && !description.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<String>get("description"), description);
                predicates.add(predicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
