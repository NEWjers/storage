package com.sonet.storage.controller;

import com.sonet.storage.dto.response.MovingRecordResponse;
import com.sonet.storage.service.MovingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
