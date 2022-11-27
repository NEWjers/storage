package com.sonet.storage.controller;

import com.sonet.storage.dto.response.RecordResponse;
import com.sonet.storage.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<RecordResponse> getAllItems() {
        return recordService.getAllRecords();
    }

    @GetMapping("page")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<RecordResponse> getRecordsPage(@RequestParam(name = "page") int page,
                                               @RequestParam(name = "size") int size) {
        return recordService.getRecordsPage(page, size);
    }

    @GetMapping("size")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Integer getRecordsNumber() {
        return recordService.getAllRecords().size();
    }
}
