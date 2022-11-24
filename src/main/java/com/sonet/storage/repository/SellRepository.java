package com.sonet.storage.repository;

import com.sonet.storage.model.sell.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellRepository extends JpaRepository<Sell, Long> {

    List<Sell> findByOrderByIdAsc();
}
