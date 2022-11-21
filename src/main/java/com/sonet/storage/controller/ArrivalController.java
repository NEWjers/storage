package com.sonet.storage.controller;

import com.sonet.storage.dto.response.ArrivalResponse;
import com.sonet.storage.service.ArrivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
