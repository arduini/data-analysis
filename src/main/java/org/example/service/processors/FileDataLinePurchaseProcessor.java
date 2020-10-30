package org.example.service.processors;

import org.example.vo.FileDataAnalysisReportVO;
import org.example.vo.SaleItemVO;
import org.example.vo.SaleVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileDataLinePurchaseProcessor implements FileDataLineProcessorInterface{

    @Value("${data-analysis.file.separator}")
    private String dataSeparator;

    @Override
    public FileDataAnalysisReportVO processLine(FileDataAnalysisReportVO fileReport, String line) {
        // TODO validar linha via regex

        final var data = line.split(dataSeparator);

        final var sale = SaleVO.builder()
                .id(Long.valueOf(data[1]))
                .items(processSaleItens(data[2]))
                .salesmanName(data[3])
                .build();

        final var totalSaleValue = sale.calculateSaleTotalPrice();

        if (fileReport.getTotalSalesBySalesMan().containsKey(sale.getSalesmanName())) {
            fileReport.getTotalSalesBySalesMan().put(sale.getSalesmanName(), fileReport.getTotalSalesBySalesMan().get(sale.getSalesmanName()).add(totalSaleValue));
        } else {
            fileReport.getTotalSalesBySalesMan().put(sale.getSalesmanName(), totalSaleValue);
        }

        if (Objects.isNull(fileReport.getMostExpensiveSalePrice()) || totalSaleValue.compareTo(fileReport.getMostExpensiveSalePrice()) == 1) {
            fileReport.setMostExpensiveSalePrice(totalSaleValue);
            fileReport.setMostExpensiveSaleId(sale.getId());
        }

        return fileReport;
    }

    private List<SaleItemVO> processSaleItens(String itemsArray) {

        // TODO refatorar pra usar regex
        // TODO validar linha via regex
        var list = new ArrayList<SaleItemVO>();

        itemsArray = itemsArray.substring(1, itemsArray.length()-1);
        final var items = itemsArray.split(",");

        for (String item:items) {

            var itemDetails = item.split("-");
            list.add(SaleItemVO.builder()
                    .id(Long.valueOf(itemDetails[0]))
                    .quantity(Integer.valueOf(itemDetails[1]))
                    .price(new BigDecimal(itemDetails[2]))
                    .build());
        }

        return list;
    }
}
