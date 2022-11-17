package com.sonet.storage.repository;

import com.sonet.storage.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByOrderByCodeAsc();

    Boolean existsByCode(String code);
}
