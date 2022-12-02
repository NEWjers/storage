package com.sonet.storage.specification;

import com.sonet.storage.model.user.Role;
import com.sonet.storage.model.user.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


public class UserSpecification {

    public static Specification<User> getUserSpecification(String id, String username, Role role) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null && !id.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<Long>get("id"), Long.parseLong(id));
                predicates.add(predicate);
            }

            if (username != null && !username.isEmpty()) {
                Predicate predicate = criteriaBuilder.equal(root.<String>get("username"), username);
                predicates.add(predicate);
            }

            if (role != null) {
                Predicate predicate = criteriaBuilder.equal(root.<Role>get("role"), role);
                predicates.add(predicate);
            }

            Predicate predicate = criteriaBuilder.equal(root.<Boolean>get("isArchived"), Boolean.valueOf(false));
            predicates.add(predicate);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
