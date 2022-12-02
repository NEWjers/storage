package com.sonet.storage.repository;

import com.sonet.storage.model.producer.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long>, JpaSpecificationExecutor<Producer> {

    List<Producer> findByOrderByIdAsc();

    Optional<Producer> findByName(String name);

    Boolean existsByName(String name);
}
