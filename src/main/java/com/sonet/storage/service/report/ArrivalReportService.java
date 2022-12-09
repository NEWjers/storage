package com.sonet.storage.service.report;

import com.sonet.storage.dto.request.arrival.ArrivalRequest;
import com.sonet.storage.model.arrival.Arrival;
import com.sonet.storage.model.item.Item;
import com.sonet.storage.model.moving.EMovingType;
import com.sonet.storage.model.moving.MovingRecord;
import com.sonet.storage.model.moving.MovingType;
import com.sonet.storage.repository.ArrivalRepository;
import com.sonet.storage.repository.ItemRepository;
import com.sonet.storage.repository.MovingTypeRepository;
import com.sonet.storage.repository.UserRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArrivalReportService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovingTypeRepository movingTypeRepository;

    @Autowired
    private ArrivalRepository arrivalRepository;

    public byte[] generateNewArrivalReport(List<ArrivalRequest> arrivals) throws JRException {

        List<MovingRecord> movingRecords = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        arrivals.forEach(arrivalRequest -> {
            if (itemRepository.existsById(arrivalRequest.getItem().getId())) {
                Item item = itemRepository.getById(arrivalRequest.getItem().getId());
                MovingRecord movingRecord = new MovingRecord();
                movingRecord.setDate(LocalDateTime.now().format(formatter));
                movingRecord.setCount(Long.parseLong(arrivalRequest.getItemCount()));
                movingRecord.setItem(item);

                movingRecord.setUser(userRepository.findByUsername(arrivalRequest.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("User does not exist")));

                MovingType movingType = movingTypeRepository.findByName(EMovingType.ARRIVAL).orElse(null);
                movingRecord.setType(movingType);

                movingRecords.add(movingRecord);
            }
        });

        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/reports/arrival.jrxml");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Storage app");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(movingRecords);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] generateExistedArrivalReport(Long arrivalId) throws JRException {
        Arrival arrival = arrivalRepository.findById(arrivalId).orElse(null);

        if (arrival == null) {
            return null;
        }

        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/reports/arrival.jrxml");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Storage app");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(arrival.getItems());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
