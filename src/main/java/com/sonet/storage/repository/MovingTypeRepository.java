package com.sonet.storage.repository;

import com.sonet.storage.model.moving.EMovingType;
import com.sonet.storage.model.moving.MovingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovingTypeRepository extends JpaRepository<MovingType, Long> {

    Optional<MovingType> findByName(EMovingType name);
}
