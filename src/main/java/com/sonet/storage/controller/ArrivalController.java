package com.sonet.storage.controller;

import com.sonet.storage.dto.request.arrival.ArrivalRequest;
import com.sonet.storage.dto.response.ArrivalResponse;
import com.sonet.storage.service.ArrivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/arrivals")
public class ArrivalController {

    @Autowired
    private ArrivalService arrivalService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ArrivalResponse> getAllItems() {
        return arrivalService.getAllArrivals();
    }

    @PostMapping("new")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createArrival(@RequestBody ArrivalRequest[] arrivalRequests) {
        List<ArrivalRequest> arrivalRequestList = Arrays.asList(arrivalRequests);
        return arrivalService.createArrival(arrivalRequestList);
    }
}