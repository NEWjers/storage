package com.sonet.storage.controller;

import com.sonet.storage.dto.request.sell.SellRequest;
import com.sonet.storage.dto.response.SellResponse;
import com.sonet.storage.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sells")
public class SellController {

    @Autowired
    private SellService sellService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<SellResponse> getAllItems() {
        return sellService.getAllSells();
    }

    @PostMapping("new")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createArrival(@RequestBody SellRequest[] sellRequests) {
        List<SellRequest> sellRequestList = Arrays.asList(sellRequests);
        return sellService.createSell(sellRequestList);
    }
}
