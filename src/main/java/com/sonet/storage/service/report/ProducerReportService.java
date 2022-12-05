package com.sonet.storage.service.report;

import com.sonet.storage.model.producer.Producer;
import com.sonet.storage.repository.ProducerRepository;
import com.sonet.storage.specification.ProducerSpecification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProducerReportService {

    @Autowired
    private ProducerRepository producerRepository;

    public byte[] generateReport(String sort, String way, String id, String name, String country, String description) throws FileNotFoundException, JRException {
        List<Producer> producers;

        if ("asc".equals(way)) {
            producers = producerRepository
                    .findAll(ProducerSpecification.getProducerSpecification(id, name, country, description), Sort.by(sort));
        } else if ("desc".equals(way)){
            producers = producerRepository
                    .findAll(ProducerSpecification.getProducerSpecification(id, name, country, description), Sort.by(sort).descending());
        } else {
            producers = producerRepository
                    .findAll(ProducerSpecification.getProducerSpecification(id, name, country, description));
        }

        File file = ResourceUtils.getFile("producer.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/reports/producer.jrxml");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Storage app");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(producers);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
