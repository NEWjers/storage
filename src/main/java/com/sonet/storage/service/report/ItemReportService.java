package com.sonet.storage.service.report;

import com.sonet.storage.model.item.Item;
import com.sonet.storage.repository.ItemRepository;
import com.sonet.storage.specification.ItemSpecification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemReportService {

    @Autowired
    private ItemRepository itemRepository;

    public byte[] generateReport(String sort, String way, String code, String itemSize, String pack, String price,
                                 String description, String producer) throws JRException {
        List<Item> items;

        if ("asc".equals(way)) {
            items = itemRepository
                    .findAll(ItemSpecification.getItemSpecification(code, itemSize, pack, price, description, producer), Sort.by(sort));
        } else if ("desc".equals(way)){
            items = itemRepository
                    .findAll(ItemSpecification.getItemSpecification(code, itemSize, pack, price, description, producer), Sort.by(sort).descending());
        } else {
            items = itemRepository
                    .findAll(ItemSpecification.getItemSpecification(code, itemSize, pack, price, description, producer));
        }

        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/reports/item.jrxml");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Storage app");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
