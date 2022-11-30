package com.sonet.storage.service;

import com.sonet.storage.dto.response.MovingRecordResponse;
import com.sonet.storage.model.moving.MovingRecord;
import com.sonet.storage.repository.MovingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                        movingRecord.getUser().getUsername(),
                        movingRecord.getItem()
                )
        ).collect(Collectors.toList());
    }

    public List<MovingRecordResponse> getMovingRecordsPage(int page, int size, String sort, String way) {
        Pageable pageable;
        if ("asc".equals(way)) {
            pageable = PageRequest.of(page, size, Sort.by(sort));
        } else if ("desc".equals(way)){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<MovingRecord> movingRecords = movingRecordRepository.findAll(pageable);

        return movingRecords.stream().map(
                movingRecord -> new MovingRecordResponse(
                        movingRecord.getCount(),
                        movingRecord.getDate(),
                        movingRecord.getType().getName().name(),
                        movingRecord.getUser().getUsername(),
                        movingRecord.getItem()
                )
        ).collect(Collectors.toList());
    }
}
