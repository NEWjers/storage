package com.sonet.storage.controller;

import com.sonet.storage.dto.request.item.CreateItemRequest;
import com.sonet.storage.dto.request.item.UpdateItemRequest;
import com.sonet.storage.dto.response.ItemResponse;
import com.sonet.storage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ItemResponse> getAllItems() {
        return itemService.getAllItems();
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
