package com.sonet.storage.service.report;

import com.sonet.storage.model.record.Record;
import com.sonet.storage.repository.RecordRepository;
import com.sonet.storage.specification.RecordSpecification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordReportService {

    @Autowired
    private RecordRepository recordRepository;

    public byte[] generateReport(String sort, String way, String id, String code, String itemSize, String pack,
                                 String price, String description, String producer, String count) throws JRException {

        List<Record> records;

        if ("asc".equals(way)) {
            records = recordRepository
                    .findAll(RecordSpecification.getRecordSpecification(id, code, itemSize, pack, price, description, producer, count), Sort.by(sort));
        } else if ("desc".equals(way)){
            records = recordRepository
                    .findAll(RecordSpecification.getRecordSpecification(id, code, itemSize, pack, price, description, producer, count), Sort.by(sort).descending());
        } else {
            records = recordRepository
                    .findAll(RecordSpecification.getRecordSpecification(id, code, itemSize, pack, price, description, producer, count));
        }

        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/reports/record.jrxml");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Storage app");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(records);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
