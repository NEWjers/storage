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

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<MovingRecordResponse> getAllMovingRecords() {
        return movingRecordService.getAllMovingRecords();
    }

    @GetMapping("page")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<MovingRecordResponse> getMovingRecordsPage(@RequestParam(name = "page") int page,
                                                           @RequestParam(name = "size") int size,
                                                           @RequestParam(name = "sort") String sort,
                                                           @RequestParam(name = "way") String way){
        return movingRecordService.getMovingRecordsPage(page, size, sort, way);
    }

    @GetMapping("size")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Integer getMovingRecordsNumber() {
        return movingRecordService.getAllMovingRecords().size();
    }
}
