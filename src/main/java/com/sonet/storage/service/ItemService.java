package com.sonet.storage.service;

import com.sonet.storage.dto.request.item.CreateItemRequest;
import com.sonet.storage.dto.request.item.UpdateItemRequest;
import com.sonet.storage.dto.response.ItemResponse;
import com.sonet.storage.dto.response.MessageResponse;
import com.sonet.storage.model.item.Item;
import com.sonet.storage.model.producer.Producer;
import com.sonet.storage.repository.ItemRepository;
import com.sonet.storage.repository.ProducerRepository;
import com.sonet.storage.specification.ItemSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProducerRepository producerRepository;

    public List<ItemResponse> getAllItems() {

        List<Item> items = itemRepository.findByOrderByCodeAsc();

        return items.stream().map(
                item -> new ItemResponse(
                        item.getId(),
                        item.getCode(),
                        item.getSize(),
                        item.getPack(),
                        item.getPrice(),
                        item.getDescription(),
                        item.getProducer()
                )
        ).collect(Collectors.toList());
    }

    public Integer getAllItemsSize(String code, String itemSize, String pack, String price,
                                   String description, String producer) {

        List<Item> items = itemRepository.findAll(ItemSpecification
                .getItemSpecification(code, itemSize, pack, price, description, producer));

        return items.size();
    }

    public List<ItemResponse> getItemsPage(int page, int size, String sort, String way, String code, String itemSize,
                                           String pack, String price, String description, String producer) {
        Pageable pageable;
        if ("asc".equals(way)) {
            pageable = PageRequest.of(page, size, Sort.by(sort));
        } else if ("desc".equals(way)){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<Item> items = itemRepository.findAll(ItemSpecification
                .getItemSpecification(code, itemSize, pack, price, description, producer), pageable);

        return items.stream().map(
                item -> new ItemResponse(
                        item.getId(),
                        item.getCode(),
                        item.getSize(),
                        item.getPack(),
                        item.getPrice(),
                        item.getDescription(),
                        item.getProducer()
                )
        ).collect(Collectors.toList());
    }

    public ResponseEntity<?> createItem(CreateItemRequest createRequest) {
        Producer producer = producerRepository.getById(createRequest.getProducerId());

        Item item = new Item(
                createRequest.getCode(),
                createRequest.getSize(),
                createRequest.getPack(),
                createRequest.getPrice(),
                createRequest.getDescription(),
                producer
        );

        itemRepository.save(item);

        return ResponseEntity.ok(new MessageResponse("Item created successfully!"));
    }

    public ResponseEntity<?> updateItem(UpdateItemRequest request) {
        if (!itemRepository.existsById(request.getId())) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        Item item = itemRepository.getById(request.getId());

        if (request.getCode() != null) {
            item.setCode(request.getCode());
        }

        if (request.getSize() != null) {
            item.setSize(request.getSize());
        }

        if (request.getPack() != null) {
            item.setPack(request.getPack());
        }

        if (request.getPrice() != null) {
            item.setPrice(request.getPrice());
        }

        if (request.getDescription() != null) {
            item.setDescription(request.getDescription());
        }

        if (request.getProducerId() != null) {
            Producer producer = producerRepository.getById(request.getProducerId());
            item.setProducer(producer);
        }

        itemRepository.save(item);

        return ResponseEntity.ok(new MessageResponse("Item updated successfully!"));
    }

    public ResponseEntity<?> deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        itemRepository.deleteById(id);

        return ResponseEntity.ok(new MessageResponse("Item deleted successfully!"));
    }
}
