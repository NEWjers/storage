package com.sonet.storage.service;

import com.sonet.storage.dto.response.MovingRecordResponse;
import com.sonet.storage.model.moving.MovingRecord;
import com.sonet.storage.repository.MovingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovingRecordService {

    @Autowired
    private MovingRecordRepository movingRecordRepository;

    public List<MovingRecordResponse> getAllMovingRecords() {
        List<MovingRecord> movingRecords = movingRecordRepository.findByOrderByIdAsc();

        return movingRecords.stream().map(
                movingRecord -> new MovingRecordResponse(
                        movingRecord.getCount(),
                        movingRecord.getDate(),
                        movingRecord.getType().getName().name(),
                        movingRecord.getItem()
                )
        ).collect(Collectors.toList());
    }
}
