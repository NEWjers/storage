package com.sonet.storage.controller;

import com.sonet.storage.dto.request.producer.CreateProducerRequest;
import com.sonet.storage.dto.request.producer.UpdateProducerRequest;
import com.sonet.storage.dto.response.ProducerResponse;
import com.sonet.storage.service.ProducerService;
import com.sonet.storage.service.report.ProducerReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/producers")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ProducerReportService reportService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ProducerResponse> getAllProducers() {
        return producerService.getAllProducers();
    }

    @GetMapping("page")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ProducerResponse> getProducersPage(@RequestParam(name = "page") int page,
                                                   @RequestParam(name = "size") int size,
                                                   @RequestParam(name = "sort") String sort,
                                                   @RequestParam(name = "way") String way,
                                                   @RequestParam(name = "id") String id,
                                                   @RequestParam(name = "name") String name,
                                                   @RequestParam(name = "country") String country,
                                                   @RequestParam(name = "description") String description) {
        return producerService.getProducersPage(page, size, sort, way, id, name, country, description);
    }

    @GetMapping("size")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Integer getProducersNumber(@RequestParam(name = "id") String id,
                                      @RequestParam(name = "name") String name,
                                      @RequestParam(name = "country") String country,
                                      @RequestParam(name = "description") String description) {
        return producerService.getAllProducersSize(id, name, country, description);
    }

    @GetMapping("report")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<byte[]> getProducersReport(@RequestParam(name = "sort") String sort,
                                                     @RequestParam(name = "way") String way,
                                                     @RequestParam(name = "id") String id,
                                                     @RequestParam(name = "name") String name,
                                                     @RequestParam(name = "country") String country,
                                                     @RequestParam(name = "description") String description) throws JRException, FileNotFoundException {

        byte[] content = reportService.generateReport(sort, way, id, name, country, description);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = "producers.pdf";
        headers.setContentDispositionFormData("filename", filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
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
