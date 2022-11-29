package com.sonet.storage.service;

import com.sonet.storage.dto.request.arrival.ArrivalRequest;
import com.sonet.storage.dto.response.ArrivalResponse;
import com.sonet.storage.dto.response.MessageResponse;
import com.sonet.storage.dto.response.MovingRecordResponse;
import com.sonet.storage.model.arrival.Arrival;
import com.sonet.storage.model.item.Item;
import com.sonet.storage.model.moving.EMovingType;
import com.sonet.storage.model.moving.MovingRecord;
import com.sonet.storage.model.moving.MovingType;
import com.sonet.storage.model.record.Record;
import com.sonet.storage.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArrivalService {

    @Autowired
    private ArrivalRepository arrivalRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MovingTypeRepository movingTypeRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private MovingRecordRepository movingRecordRepository;

    public List<ArrivalResponse> getAllArrivals() {

        return arrivalRepository.findByOrderByIdAsc().stream().map(
                arrival -> new ArrivalResponse(
                        arrival.getId(),
                        arrival.getDate(),
                        arrival.getItems().stream().map(
                                movingRecord -> new MovingRecordResponse(
                                        movingRecord.getCount(),
                                        movingRecord.getDate(),
                                        movingRecord.getType().getName().name(),
                                        movingRecord.getItem()
                                )
                        ).collect(Collectors.toList())
                )
        ).collect(Collectors.toList());
    }

    public List<ArrivalResponse> getArrivalsPage(int page, int size, String sort, String way) {
        Pageable pageable;
        if ("asc".equals(way)) {
            pageable = PageRequest.of(page, size, Sort.by(sort));
        } else if ("desc".equals(way)){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        return arrivalRepository.findAll(pageable).stream().map(
                arrival -> new ArrivalResponse(
                        arrival.getId(),
                        arrival.getDate(),
                        arrival.getItems().stream().map(
                                movingRecord -> new MovingRecordResponse(
                                        movingRecord.getCount(),
                                        movingRecord.getDate(),
                                        movingRecord.getType().getName().name(),
                                        movingRecord.getItem()
                                )
                        ).collect(Collectors.toList())
                )
        ).collect(Collectors.toList());
    }

    public ResponseEntity<?> createArrival(List<ArrivalRequest> arrivals) {
        Arrival arrival = new Arrival();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        arrival.setDate(LocalDateTime.now().format(formatter));

        List<MovingRecord> movingRecords = new ArrayList<>();

        arrivals.forEach(arrivalRequest -> {
            if (itemRepository.existsById(arrivalRequest.getItem().getId())) {
                Item item = itemRepository.getById(arrivalRequest.getItem().getId());
                MovingRecord movingRecord = new MovingRecord();
                movingRecord.setDate(LocalDateTime.now().format(formatter));
                movingRecord.setCount(Long.parseLong(arrivalRequest.getItemCount()));
                movingRecord.setItem(item);

                MovingType movingType = movingTypeRepository.findByName(EMovingType.ARRIVAL).orElse(null);
                movingRecord.setType(movingType);

                movingRecords.add(movingRecord);
                movingRecordRepository.save(movingRecord);

                Record record = recordRepository.findByItem(item).orElse(null);

                if (record != null) {
                    record.setCount(record.getCount() + Long.parseLong(arrivalRequest.getItemCount()));
                    recordRepository.save(record);
                } else {
                    Record newRecord = new Record();
                    newRecord.setCount(Long.parseLong(arrivalRequest.getItemCount()));
                    newRecord.setItem(item);
                    recordRepository.save(newRecord);
                }
            }
        });

        arrival.setItems(movingRecords);
        arrivalRepository.save(arrival);

        return ResponseEntity.ok(new MessageResponse("Arrival created successfully!"));
    }
}
