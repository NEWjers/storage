package com.sonet.storage.specification;

import com.sonet.storage.model.item.Item;
import com.sonet.storage.model.moving.MovingRecord;
import com.sonet.storage.model.moving.MovingType;
import com.sonet.storage.model.producer.Producer;
import com.sonet.storage.model.user.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class MovingRecordSpecification {

    public static Specification<MovingRecord> getMovingRecordSpecification(String date, String user,
                                                                           MovingType movingType, String code,
                                                                           String size, String pack, String price,
                                                                           String description, String producer,
                                                                           String count) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (date != null && !date.isEmpty()) {
                Predicate predicate = criteriaBuilder.like(root.get("date"), "%" + date + "%");
                predicates.add(predicate);
            }

            if (user != null && !user.isEmpty()) {
                Path<User> path = root.get("user");
                Predicate predicate = criteriaBuilder.equal(path.<String>get("username"), user);
                predicates.add(predicate);
            }

            if (movingType != null) {
                Predicate predicate = criteriaBuilder.equal(root.<MovingType>get("type"), movingType);
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
