package com.sonet.storage.controller;

import com.sonet.storage.dto.response.RecordResponse;
import com.sonet.storage.service.RecordService;
import com.sonet.storage.service.report.RecordReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordReportService recordReportService;

    @GetMapping("page")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<RecordResponse> getRecordsPage(@RequestParam(name = "page") int page,
                                               @RequestParam(name = "size") int size,
                                               @RequestParam(name = "sort") String sort,
                                               @RequestParam(name = "way") String way,
                                               @RequestParam(name = "id") String id,
                                               @RequestParam(name = "code") String code,
                                               @RequestParam(name = "itemSize") String itemSize,
                                               @RequestParam(name = "pack") String pack,
                                               @RequestParam(name = "price") String price,
                                               @RequestParam(name = "description") String description,
                                               @RequestParam(name = "producer") String producer,
                                               @RequestParam(name = "count") String count) {

        return recordService
                .getRecordsPage(page, size, sort, way, id, code, itemSize, pack, price, description, producer, count);
    }

    @GetMapping("size")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Integer getRecordsNumber(@RequestParam(name = "id") String id,
                                    @RequestParam(name = "code") String code,
                                    @RequestParam(name = "itemSize") String itemSize,
                                    @RequestParam(name = "pack") String pack,
                                    @RequestParam(name = "price") String price,
                                    @RequestParam(name = "description") String description,
                                    @RequestParam(name = "producer") String producer,
                                    @RequestParam(name = "count") String count) {

        return recordService.getAllRecordsSize(id, code, itemSize, pack, price, description, producer, count).size();
    }

    @GetMapping("report")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<byte[]> getRecordsReport(@RequestParam(name = "sort") String sort,
                                                   @RequestParam(name = "way") String way,
                                                   @RequestParam(name = "id") String id,
                                                   @RequestParam(name = "code") String code,
                                                   @RequestParam(name = "itemSize") String itemSize,
                                                   @RequestParam(name = "pack") String pack,
                                                   @RequestParam(name = "price") String price,
                                                   @RequestParam(name = "description") String description,
                                                   @RequestParam(name = "producer") String producer,
                                                   @RequestParam(name = "count") String count) throws JRException {

        byte[] content = recordReportService.generateReport(sort, way, id, code, itemSize, pack, price, description, producer, count);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = "records.pdf";
        headers.setContentDispositionFormData("filename", filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
