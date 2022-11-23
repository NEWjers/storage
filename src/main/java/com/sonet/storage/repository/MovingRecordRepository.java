package com.sonet.storage.repository;

import com.sonet.storage.model.moving.MovingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovingRecordRepository extends JpaRepository<MovingRecord, Long> {
}
