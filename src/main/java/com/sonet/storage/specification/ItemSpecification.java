package com.sonet.storage.specification;

import com.sonet.storage.model.item.Item;
import com.sonet.storage.model.producer.Producer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ItemSpecification {

    public static Specification<Item> getItemSpecification(String code, String itemSize, String pack, String price,
                                                           String description, Producer producer) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (code != null && !code.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<String>get("code"), code);
                predicates.add(predicate);
            }

            if (itemSize != null && !itemSize.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<String>get("size"), itemSize);
                predicates.add(predicate);
            }

            if (pack != null && !pack.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<Integer>get("pack"), Integer.valueOf(pack));
                predicates.add(predicate);
            }

            if (price != null && !price.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<Double>get("price"), Double.parseDouble(price));
                predicates.add(predicate);
            }

            if (description != null && !description.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<String>get("description"), description);
                predicates.add(predicate);
            }

            if (producer != null) {
                Predicate predicate = criteriaBuilder.equal(root.<Producer>get("producer"), producer);
                predicates.add(predicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
