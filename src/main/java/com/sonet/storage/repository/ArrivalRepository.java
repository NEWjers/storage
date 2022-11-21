package com.sonet.storage.repository;

import com.sonet.storage.model.arrival.Arrival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArrivalRepository extends JpaRepository<Arrival, Long> {

    List<Arrival> findByOrderByIdAsc();
}
