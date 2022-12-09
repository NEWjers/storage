package com.sonet.storage.controller;

import com.sonet.storage.dto.request.sell.SellRequest;
import com.sonet.storage.dto.response.SellResponse;
import com.sonet.storage.service.SellService;
import com.sonet.storage.service.report.SellReportService;
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
@RequestMapping("/api/sells")
public class SellController {

    @Autowired
    private SellService sellService;

    @Autowired
    private SellReportService sellReportService;

    @GetMapping("page")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<SellResponse> getSellsPage(@RequestParam(name = "page") int page,
                                           @RequestParam(name = "size") int size,
                                           @RequestParam(name = "sort") String sort,
                                           @RequestParam(name = "way") String way,
                                           @RequestParam(name = "id") String id,
                                           @RequestParam(name = "date") String date,
                                           @RequestParam(name = "user") String user) {
        return sellService.getSellsPage(page, size, sort, way, id, date, user);
    }

    @GetMapping("size")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Integer getSellsNumber(@RequestParam(name = "id") String id,
                                  @RequestParam(name = "date") String date,
                                  @RequestParam(name = "user") String user) {
        return sellService.getAllSellsSize(id, date, user).size();
    }

    @PostMapping("report")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<byte[]> getSellNewReport(@RequestBody SellRequest[] sellRequests) throws JRException {
        List<SellRequest> sellRequestList = Arrays.asList(sellRequests);
        byte[] content = sellReportService.generateNewSellReport(sellRequestList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = "sell.pdf";
        headers.setContentDispositionFormData("filename", filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    @PostMapping("new")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createSell(@RequestBody SellRequest[] sellRequests) {
        List<SellRequest> sellRequestList = Arrays.asList(sellRequests);
        return sellService.createSell(sellRequestList);
    }
}
