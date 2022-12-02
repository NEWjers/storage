package com.sonet.storage.service;

import com.sonet.storage.dto.request.producer.CreateProducerRequest;
import com.sonet.storage.dto.request.producer.UpdateProducerRequest;
import com.sonet.storage.dto.response.MessageResponse;
import com.sonet.storage.dto.response.ProducerResponse;
import com.sonet.storage.model.producer.Producer;
import com.sonet.storage.repository.ProducerRepository;
import com.sonet.storage.specification.ProducerSpecification;
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
public class ProducerService {

    @Autowired
    private ProducerRepository producerRepository;

    public List<ProducerResponse> getAllProducers() {
        List<Producer> producers = producerRepository.findByOrderByIdAsc();

        return producers.stream().map(
                producer -> new ProducerResponse(
                    producer.getId(),
                    producer.getName(),
                    producer.getCountry(),
                    producer.getDescription()
        )).collect(Collectors.toList());
    }

    public Integer getAllProducersSize (String id, String name, String country, String description) {
        List<Producer> producers = producerRepository
                .findAll(ProducerSpecification.getProducerSpecification(id, name, country, description));

        return producers.size();
    }

    public List<ProducerResponse> getProducersPage(int page, int size, String sort, String way, String id, String name,
                                                   String country, String description) {
        Pageable pageable;
        if ("asc".equals(way)) {
            pageable = PageRequest.of(page, size, Sort.by(sort));
        } else if ("desc".equals(way)){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<Producer> producers = producerRepository
                .findAll(ProducerSpecification.getProducerSpecification(id, name, country, description), pageable);

        return producers.stream().map(
                producer -> new ProducerResponse(
                        producer.getId(),
                        producer.getName(),
                        producer.getCountry(),
                        producer.getDescription()
                )).collect(Collectors.toList());
    }

    public ResponseEntity<?> createProducer(CreateProducerRequest createRequest) {
        if (producerRepository.existsByName(createRequest.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Producer with such name is already exist"));
        }

        Producer producer = new Producer(createRequest.getName(), createRequest.getCountry(), createRequest.getDescription());

        producerRepository.save(producer);

        return ResponseEntity.ok(new MessageResponse("Producer created successfully!"));
    }

    public ResponseEntity<?> updateProducer(UpdateProducerRequest updateRequest) {
        if (!producerRepository.existsById(updateRequest.getId())) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        Producer producer = producerRepository.getById(updateRequest.getId());

        if (updateRequest.getName() != null) {
            producer.setName(updateRequest.getName());
        }

        if (updateRequest.getCountry() != null) {
            producer.setCountry(updateRequest.getCountry());
        }

        if (updateRequest.getDescription() != null) {
            producer.setDescription(updateRequest.getDescription());
        }

        producerRepository.save(producer);

        return ResponseEntity.ok(new MessageResponse("Producer updated successfully!"));
    }

    public ResponseEntity<?> deleteProducer(Long id) {
        if (!producerRepository.existsById(id)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        producerRepository.deleteById(id);

        return ResponseEntity.ok(new MessageResponse("Producer deleted successfully!"));
    }
}
