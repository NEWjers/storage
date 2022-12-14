package com.sonet.storage.repository;

import com.sonet.storage.model.item.Item;
import com.sonet.storage.model.record.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long>, JpaSpecificationExecutor<Record> {

    List<Record> findByOrderByIdAsc();

    Optional<Record> findByItem(Item item);
}
