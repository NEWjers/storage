package com.sonet.storage.specification;

import com.sonet.storage.model.arrival.Arrival;
import com.sonet.storage.model.user.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ArrivalSpecification {

    public static Specification<Arrival> getArrivalSpecification(String id, String date, String user) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null && !id.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<Long>get("id"), Long.parseLong(id));
                predicates.add(predicate);
            }

            if (date != null && !date.isEmpty()) {
                Predicate predicate = criteriaBuilder.like(root.get("date"), "%" + date + "%");
                predicates.add(predicate);
            }

            if (user != null && !user.isEmpty()) {
                Path<User> path = root.get("user");
                Predicate predicate = criteriaBuilder.equal(path.<String>get("username"), user);
                predicates.add(predicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
