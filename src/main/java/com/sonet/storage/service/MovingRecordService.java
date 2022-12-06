package com.sonet.storage.service;

import com.sonet.storage.dto.response.MovingRecordResponse;
import com.sonet.storage.model.moving.EMovingType;
import com.sonet.storage.model.moving.MovingRecord;
import com.sonet.storage.model.moving.MovingType;
import com.sonet.storage.repository.MovingRecordRepository;
import com.sonet.storage.repository.MovingTypeRepository;
import com.sonet.storage.specification.MovingRecordSpecification;
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

    @Autowired
    private MovingTypeRepository movingTypeRepository;

    public List<MovingRecordResponse> getAllMovingRecordsSize(String date, String user, String movingType, String code,
                                                              String itemSize, String pack, String price,
                                                              String description, String producer, String count) {

        MovingType requiredMovingType = null;
        if (EMovingType.ARRIVAL.name().equals(movingType)) {
            requiredMovingType = movingTypeRepository.findByName(EMovingType.ARRIVAL).orElse(null);
        }
        if (EMovingType.SELL.name().equals(movingType)) {
            requiredMovingType = movingTypeRepository.findByName(EMovingType.SELL).orElse(null);
        }

        List<MovingRecord> movingRecords = movingRecordRepository
                .findAll(MovingRecordSpecification.getMovingRecordSpecification(
                        date, user, requiredMovingType, code, itemSize, pack, price, description, producer, count
                ));

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

    public List<MovingRecordResponse> getMovingRecordsPage(int page, int size, String sort, String way, String date,
                                                           String user, String movingType, String code, String itemSize,
                                                           String pack, String price, String description,
                                                           String producer, String count) {
        Pageable pageable;
        if ("asc".equals(way)) {
            pageable = PageRequest.of(page, size, Sort.by(sort));
        } else if ("desc".equals(way)){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        MovingType requiredMovingType = null;
        if (EMovingType.ARRIVAL.name().equals(movingType)) {
            requiredMovingType = movingTypeRepository.findByName(EMovingType.ARRIVAL).orElse(null);
        }
        if (EMovingType.SELL.name().equals(movingType)) {
            requiredMovingType = movingTypeRepository.findByName(EMovingType.SELL).orElse(null);
        }

        Page<MovingRecord> movingRecords = movingRecordRepository
                .findAll(MovingRecordSpecification
                        .getMovingRecordSpecification(date, user, requiredMovingType, code, itemSize, pack, price, description, producer, count), pageable);

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
