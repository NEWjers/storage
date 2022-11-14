package com.sonet.storage.repository;

import com.sonet.storage.model.producer.Producer;
import com.sonet.storage.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

    List<Producer> findByOrderByIdAsc();

    Boolean existsByName(String name);
}
