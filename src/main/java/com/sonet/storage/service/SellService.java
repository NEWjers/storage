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
import com.sonet.storage.specification.SellSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private UserRepository userRepository;

    public List<SellResponse> getAllSellsSize(String id, String date, String user) {
        return sellRepository.findAll(SellSpecification.getSellSpecification(id, date, user)).stream().map(
                sell -> new SellResponse(
                        sell.getId(),
                        sell.getDate(),
                        sell.getUser().getUsername(),
                        sell.getItems().stream().map(
                                movingRecord -> new MovingRecordResponse(
                                        movingRecord.getCount(),
                                        movingRecord.getDate(),
                                        movingRecord.getType().getName().name(),
                                        movingRecord.getUser().getUsername(),
                                        movingRecord.getItem()
                                )
                        ).collect(Collectors.toList())
                )
        ).collect(Collectors.toList());
    }

    public List<SellResponse> getSellsPage(int page, int size, String sort, String way, String id, String date,
                                           String user) {
        Pageable pageable;
        if ("asc".equals(way)) {
            pageable = PageRequest.of(page, size, Sort.by(sort));
        } else if ("desc".equals(way)){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        return sellRepository.findAll(SellSpecification.getSellSpecification(id, date, user), pageable).stream().map(
                sell -> new SellResponse(
                        sell.getId(),
                        sell.getDate(),
                        sell.getUser().getUsername(),
                        sell.getItems().stream().map(
                                movingRecord -> new MovingRecordResponse(
                                        movingRecord.getCount(),
                                        movingRecord.getDate(),
                                        movingRecord.getType().getName().name(),
                                        movingRecord.getUser().getUsername(),
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

        sell.setUser(userRepository.findByUsername(sells.get(0).getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist")));

        List<MovingRecord> movingRecords = new ArrayList<>();

        sells.forEach(sellRequest -> {
            if (itemRepository.existsById(sellRequest.getItem().getId())) {
                Item item = itemRepository.getById(sellRequest.getItem().getId());
                MovingRecord movingRecord = new MovingRecord();
                movingRecord.setDate(LocalDateTime.now().format(formatter));
                movingRecord.setCount(Long.parseLong(sellRequest.getItemCount()));
                movingRecord.setItem(item);

                movingRecord.setUser(userRepository.findByUsername(sellRequest.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("User does not exist")));

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
