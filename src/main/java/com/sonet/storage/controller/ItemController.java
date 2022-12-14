package com.sonet.storage.controller;

import com.sonet.storage.dto.request.item.CreateItemRequest;
import com.sonet.storage.dto.request.item.UpdateItemRequest;
import com.sonet.storage.dto.response.ItemResponse;
import com.sonet.storage.service.ItemService;
import com.sonet.storage.service.report.ItemReportService;
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
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemReportService itemReportService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ItemResponse> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("page")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ItemResponse> getItemsPage(@RequestParam(name = "page") int page,
                                           @RequestParam(name = "size") int size,
                                           @RequestParam(name = "sort") String sort,
                                           @RequestParam(name = "way") String way,
                                           @RequestParam(name = "code") String code,
                                           @RequestParam(name = "itemSize") String itemSize,
                                           @RequestParam(name = "pack") String pack,
                                           @RequestParam(name = "price") String price,
                                           @RequestParam(name = "description") String description,
                                           @RequestParam(name = "producer") String producer) {
        return itemService.getItemsPage(page, size, sort, way, code, itemSize, pack, price, description, producer);
    }

    @GetMapping("size")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Integer getItemsNumber(@RequestParam(name = "code") String code,
                                  @RequestParam(name = "itemSize") String itemSize,
                                  @RequestParam(name = "pack") String pack,
                                  @RequestParam(name = "price") String price,
                                  @RequestParam(name = "description") String description,
                                  @RequestParam(name = "producer") String producer) {
        return itemService.getAllItemsSize(code, itemSize, pack, price, description, producer);
    }

    @GetMapping("report")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<byte[]> getItemsReport(@RequestParam(name = "sort") String sort,
                                                 @RequestParam(name = "way") String way,
                                                 @RequestParam(name = "code") String code,
                                                 @RequestParam(name = "itemSize") String itemSize,
                                                 @RequestParam(name = "pack") String pack,
                                                 @RequestParam(name = "price") String price,
                                                 @RequestParam(name = "description") String description,
                                                 @RequestParam(name = "producer") String producer) throws JRException {

        byte[] content = itemReportService.generateReport(sort, way, code, itemSize, pack, price, description, producer);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = "items.pdf";
        headers.setContentDispositionFormData("filename", filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    @PostMapping("new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createItem(@RequestBody CreateItemRequest createItemRequest) {
        return itemService.createItem(createItemRequest);
    }

    @PostMapping("update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateItem(@RequestBody UpdateItemRequest updateItemRequest) {
        return itemService.updateItem(updateItemRequest);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteItem(@PathVariable("id") Long id) {
        return itemService.deleteItem(id);
    }
}
