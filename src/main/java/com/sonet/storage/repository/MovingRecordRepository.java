package com.sonet.storage.repository;

import com.sonet.storage.model.moving.MovingRecord;
import com.sonet.storage.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovingRecordRepository extends JpaRepository<MovingRecord, Long> {

    List<MovingRecord> findByOrderByIdAsc();

    List<MovingRecord> findByUser(User user);
}
