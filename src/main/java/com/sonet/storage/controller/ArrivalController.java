package com.sonet.storage.controller;

import com.sonet.storage.dto.request.arrival.ArrivalRequest;
import com.sonet.storage.dto.response.ArrivalResponse;
import com.sonet.storage.service.ArrivalService;
import com.sonet.storage.service.report.ArrivalReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    private ArrivalReportService arrivalReportService;

    @GetMapping("page")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ArrivalResponse> getArrivalsPage(@RequestParam(name = "page") int page,
                                                 @RequestParam(name = "size") int size,
                                                 @RequestParam(name = "sort") String sort,
                                                 @RequestParam(name = "way") String way,
                                                 @RequestParam(name = "id") String id,
                                                 @RequestParam(name = "date") String date,
                                                 @RequestParam(name = "user") String user) {
        return arrivalService.getArrivalsPage(page, size, sort, way, id, date, user);
    }

    @GetMapping("size")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Integer getArrivalsNumber(@RequestParam(name = "id") String id,
                                     @RequestParam(name = "date") String date,
                                     @RequestParam(name = "user") String user) {
        return arrivalService.getAllArrivalsSize(id, date, user).size();
    }

    @PostMapping("report")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<byte[]> getArrivalNewReport(@RequestBody ArrivalRequest[] arrivalRequests) throws JRException {
        List<ArrivalRequest> arrivalRequestList = Arrays.asList(arrivalRequests);
        byte[] content = arrivalReportService.generateNewArrivalReport(arrivalRequestList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = "producers.pdf";
        headers.setContentDispositionFormData("filename", filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    @PostMapping("new")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createArrival(@RequestBody ArrivalRequest[] arrivalRequests) {
        List<ArrivalRequest> arrivalRequestList = Arrays.asList(arrivalRequests);
        return arrivalService.createArrival(arrivalRequestList);
    }
}
