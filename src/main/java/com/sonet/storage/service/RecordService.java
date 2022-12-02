package com.sonet.storage.service;

import com.sonet.storage.dto.response.RecordResponse;
import com.sonet.storage.model.record.Record;
import com.sonet.storage.repository.RecordRepository;
import com.sonet.storage.specification.RecordSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public List<RecordResponse> getAllRecordsSize(String id, String code, String itemSize, String pack,
                                                  String price, String description, String producer,
                                                  String count) {

        List<Record> records = recordRepository.findAll(
                RecordSpecification.getRecordSpecification(id, code, itemSize, pack, price, description, producer, count));

        return records.stream().map(
                record -> new RecordResponse(
                        record.getId(),
                        record.getCount(),
                        record.getItem()
                )
        ).collect(Collectors.toList());
    }

    public List<RecordResponse> getRecordsPage(int page, int size, String sort, String way, String id, String code,
                                               String itemSize, String pack, String price, String description,
                                               String producer, String count) {
        Pageable pageable;
        if ("asc".equals(way)) {
            pageable = PageRequest.of(page, size, Sort.by(sort));
        } else if ("desc".equals(way)){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<Record> records = recordRepository.findAll(RecordSpecification
                .getRecordSpecification(id, code, itemSize, pack, price, description, producer, count), pageable);

        return records.stream().map(
                record -> new RecordResponse(
                        record.getId(),
                        record.getCount(),
                        record.getItem()
                )
        ).collect(Collectors.toList());
    }
}
