package com.sonet.storage.specification;

import com.sonet.storage.model.item.Item;
import com.sonet.storage.model.producer.Producer;
import com.sonet.storage.model.record.Record;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class RecordSpecification {

    public static Specification<Record> getRecordSpecification(String id, String code, String size, String pack,
                                                               String price, String description, String producer,
                                                               String count) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null && !id.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<Long>get("id"), Long.parseLong(id));
                predicates.add(predicate);
            }

            if (code != null && !code.isEmpty()) {
                Path<Item> path = root.get("item");
                Predicate predicate = criteriaBuilder.equal(path.<String>get("code"), code);
                predicates.add(predicate);
            }

            if (size != null && !size.isEmpty()) {
                Path<Item> path = root.get("item");
                Predicate predicate = criteriaBuilder.equal(path.<String>get("size"), size);
                predicates.add(predicate);
            }

            if (pack != null && !pack.isEmpty()) {
                Path<Item> path = root.get("item");
                Predicate predicate = criteriaBuilder.equal(path.<Integer>get("pack"), Integer.valueOf(pack));
                predicates.add(predicate);
            }

            if (price != null && !price.isEmpty()) {
                Path<Item> path = root.get("item");
                Predicate predicate = criteriaBuilder.equal(path.<Double>get("price"), Double.parseDouble(price));
                predicates.add(predicate);
            }

            if (description != null && !description.isEmpty()) {
                Path<Item> path = root.get("item");
                Predicate predicate = criteriaBuilder.equal(path.<String>get("description"), description);
                predicates.add(predicate);
            }

            if (producer != null && !producer.isEmpty()) {
                Path<Item> itemPath = root.get("item");
                Path<Producer> producerPath = itemPath.get("producer");
                Predicate predicate = criteriaBuilder.equal(producerPath.<String>get("name"), producer);
                predicates.add(predicate);
            }

            if (count != null && !count.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<Long>get("count"), Long.parseLong(count));
                predicates.add(predicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
