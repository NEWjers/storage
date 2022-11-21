package com.sonet.storage.service;

import com.sonet.storage.dto.response.ArrivalResponse;
import com.sonet.storage.dto.response.MovingRecordResponse;
import com.sonet.storage.repository.ArrivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArrivalService {

    @Autowired
    private ArrivalRepository arrivalRepository;

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
}
