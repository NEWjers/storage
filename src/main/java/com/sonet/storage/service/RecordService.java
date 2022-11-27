package com.sonet.storage.service;

import com.sonet.storage.dto.response.RecordResponse;
import com.sonet.storage.model.record.Record;
import com.sonet.storage.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public List<RecordResponse> getAllRecords() {

        List<Record> records = recordRepository.findByOrderByIdAsc();

        return records.stream().map(
                record -> new RecordResponse(
                        record.getId(),
                        record.getCount(),
                        record.getItem()
                )
        ).collect(Collectors.toList());
    }

    public List<RecordResponse> getRecordsPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Record> records = recordRepository.findAll(pageable);

        return records.stream().map(
                record -> new RecordResponse(
                        record.getId(),
                        record.getCount(),
                        record.getItem()
                )
        ).collect(Collectors.toList());
    }
}
