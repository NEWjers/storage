package com.sonet.storage.service;

import com.sonet.storage.dto.request.sell.SellRequest;
import com.sonet.storage.dto.response.MessageResponse;
import com.sonet.storage.dto.response.MovingRecordResponse;
import com.sonet.storage.dto.response.SellResponse;
import com.sonet.storage.model.item.Item;
import com.sonet.storage.model.moving.EMovingType;
import com.sonet.storage.model.moving.MovingRecord;
import com.sonet.storage.model.moving.MovingType;
import com.sonet.storage.model.record.Record;
import com.sonet.storage.model.sell.Sell;
import com.sonet.storage.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellService {

    @Autowired
    private SellRepository sellRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MovingTypeRepository movingTypeRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private MovingRecordRepository movingRecordRepository;

    public List<SellResponse> getAllSells() {
        return sellRepository.findByOrderByIdAsc().stream().map(
                sell -> new SellResponse(
                        sell.getId(),
                        sell.getDate(),
                        sell.getItems().stream().map(
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

    public List<SellResponse> getSellsPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sellRepository.findAll(pageable).stream().map(
                sell -> new SellResponse(
                        sell.getId(),
                        sell.getDate(),
                        sell.getItems().stream().map(
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

    public ResponseEntity<?> createSell(List<SellRequest> sells) {
        Sell sell = new Sell();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        sell.setDate(LocalDateTime.now().format(formatter));

        List<MovingRecord> movingRecords = new ArrayList<>();

        sells.forEach(sellRequest -> {
            if (itemRepository.existsById(sellRequest.getItem().getId())) {
                Item item = itemRepository.getById(sellRequest.getItem().getId());
                MovingRecord movingRecord = new MovingRecord();
                movingRecord.setDate(LocalDateTime.now().format(formatter));
                movingRecord.setCount(Long.parseLong(sellRequest.getItemCount()));
                movingRecord.setItem(item);

                MovingType movingType = movingTypeRepository.findByName(EMovingType.SELL).orElse(null);
                movingRecord.setType(movingType);

                movingRecords.add(movingRecord);
                movingRecordRepository.save(movingRecord);

                Record record = recordRepository.findByItem(item).orElse(null);

                if (record != null) {
                    record.setCount(record.getCount() - Long.parseLong(sellRequest.getItemCount()));
                    recordRepository.save(record);
                } else {
                    Record newRecord = new Record();
                    newRecord.setCount(Long.parseLong(sellRequest.getItemCount()));
                    newRecord.setItem(item);
                    recordRepository.save(newRecord);
                }
            }
        });

        sell.setItems(movingRecords);
        sellRepository.save(sell);

        return ResponseEntity.ok(new MessageResponse("Sell created successfully!"));
    }
}
