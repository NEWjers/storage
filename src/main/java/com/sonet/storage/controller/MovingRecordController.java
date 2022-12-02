package com.sonet.storage.controller;

import com.sonet.storage.dto.response.MovingRecordResponse;
import com.sonet.storage.service.MovingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/moving-records")
public class MovingRecordController {

    @Autowired
    private MovingRecordService movingRecordService;

    @GetMapping("page")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<MovingRecordResponse> getMovingRecordsPage(@RequestParam(name = "page") int page,
                                                           @RequestParam(name = "size") int size,
                                                           @RequestParam(name = "sort") String sort,
                                                           @RequestParam(name = "way") String way,
                                                           @RequestParam(name = "date") String date,
                                                           @RequestParam(name = "user") String user,
                                                           @RequestParam(name = "movingType") String movingType,
                                                           @RequestParam(name = "code") String code,
                                                           @RequestParam(name = "itemSize") String itemSize,
                                                           @RequestParam(name = "pack") String pack,
                                                           @RequestParam(name = "price") String price,
                                                           @RequestParam(name = "description") String description,
                                                           @RequestParam(name = "producer") String producer,
                                                           @RequestParam(name = "count") String count){
        return movingRecordService
                .getMovingRecordsPage(page, size, sort, way, date, user, movingType, code, itemSize, pack, price, description, producer, count);
    }

    @GetMapping("size")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Integer getMovingRecordsNumber(@RequestParam(name = "date") String date,
                                          @RequestParam(name = "user") String user,
                                          @RequestParam(name = "movingType") String movingType,
                                          @RequestParam(name = "code") String code,
                                          @RequestParam(name = "itemSize") String itemSize,
                                          @RequestParam(name = "pack") String pack,
                                          @RequestParam(name = "price") String price,
                                          @RequestParam(name = "description") String description,
                                          @RequestParam(name = "producer") String producer,
                                          @RequestParam(name = "count") String count) {
        return movingRecordService
                .getAllMovingRecordsSize(date, user, movingType, code, itemSize, pack, price, description, producer, count).size();
    }
}
