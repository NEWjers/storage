package com.sonet.storage.service.report;

import com.sonet.storage.model.moving.EMovingType;
import com.sonet.storage.model.moving.MovingRecord;
import com.sonet.storage.model.moving.MovingType;
import com.sonet.storage.repository.MovingRecordRepository;
import com.sonet.storage.repository.MovingTypeRepository;
import com.sonet.storage.specification.MovingRecordSpecification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovingRecordReportService {

    @Autowired
    private MovingRecordRepository movingRecordRepository;

    @Autowired
    private MovingTypeRepository movingTypeRepository;

    public byte[] generateReport(String sort, String way, String date, String user, String movingType, String code,
                            String itemSize, String pack, String price, String description, String producer,
                            String count) throws JRException {

        List<MovingRecord> movingRecords;

        MovingType requiredMovingType = null;
        if (EMovingType.ARRIVAL.name().equals(movingType)) {
            requiredMovingType = movingTypeRepository.findByName(EMovingType.ARRIVAL).orElse(null);
        }
        if (EMovingType.SELL.name().equals(movingType)) {
            requiredMovingType = movingTypeRepository.findByName(EMovingType.SELL).orElse(null);
        }

        if ("asc".equals(way)) {
            movingRecords = movingRecordRepository
                    .findAll(MovingRecordSpecification
                            .getMovingRecordSpecification(date, user, requiredMovingType, code, itemSize, pack, price, description, producer, count), Sort.by(sort));
        } else if ("desc".equals(way)){
            movingRecords = movingRecordRepository
                    .findAll(MovingRecordSpecification
                            .getMovingRecordSpecification(date, user, requiredMovingType, code, itemSize, pack, price, description, producer, count), Sort.by(sort).descending());
        } else {
            movingRecords = movingRecordRepository
                    .findAll(MovingRecordSpecification
                            .getMovingRecordSpecification(date, user, requiredMovingType, code, itemSize, pack, price, description, producer, count));
        }

        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/reports/history.jrxml");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Storage app");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(movingRecords);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
