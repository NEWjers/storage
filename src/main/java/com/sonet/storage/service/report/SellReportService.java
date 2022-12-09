package com.sonet.storage.service.report;

import com.sonet.storage.dto.request.arrival.ArrivalRequest;
import com.sonet.storage.dto.request.sell.SellRequest;
import com.sonet.storage.model.item.Item;
import com.sonet.storage.model.moving.EMovingType;
import com.sonet.storage.model.moving.MovingRecord;
import com.sonet.storage.model.moving.MovingType;
import com.sonet.storage.model.sell.Sell;
import com.sonet.storage.repository.ItemRepository;
import com.sonet.storage.repository.MovingTypeRepository;
import com.sonet.storage.repository.SellRepository;
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
public class SellReportService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovingTypeRepository movingTypeRepository;

    @Autowired
    private SellRepository sellRepository;

    public byte[] generateNewSellReport(List<SellRequest> sells) throws JRException {
        List<MovingRecord> movingRecords = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        sells.forEach(sellRequest -> {
            Item item = itemRepository.getById(sellRequest.getItem().getId());
            MovingRecord movingRecord = new MovingRecord();
            movingRecord.setDate(LocalDateTime.now().format(formatter));
            movingRecord.setCount(Long.parseLong(sellRequest.getItemCount()));
            movingRecord.setItem(item);

            movingRecord.setUser(userRepository.findByUsername(sellRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User does not exist")));

            MovingType movingType = movingTypeRepository.findByName(EMovingType.ARRIVAL).orElse(null);
            movingRecord.setType(movingType);

            movingRecords.add(movingRecord);
        });

        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/reports/sell.jrxml");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Storage app");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(movingRecords);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] generateExistedSellReport(Long sellId) throws JRException {
        Sell sell = sellRepository.findById(sellId).orElse(null);

        if (sell == null) {
            return null;
        }

        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/reports/sell.jrxml");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Storage app");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(sell.getItems());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
