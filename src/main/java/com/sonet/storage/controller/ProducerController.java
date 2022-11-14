package com.sonet.storage.controller;

import com.sonet.storage.dto.request.CreateProducerRequest;
import com.sonet.storage.dto.request.UpdateProducerRequest;
import com.sonet.storage.dto.response.ProducerResponse;
import com.sonet.storage.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/producers")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ProducerResponse> getAllProducers() {
        return producerService.getAllProducers();
    }

    @PostMapping("new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProducer(@RequestBody CreateProducerRequest createRequest) {
        return producerService.createProducer(createRequest);
    }

    @PostMapping("update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProducer(@RequestBody UpdateProducerRequest updateRequest) {
        return producerService.updateProducer(updateRequest);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return producerService.deleteProducer(id);
    }
}