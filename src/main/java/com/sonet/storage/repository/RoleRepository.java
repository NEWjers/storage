package com.sonet.storage.repository;

import com.sonet.storage.model.user.ERole;
import com.sonet.storage.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
