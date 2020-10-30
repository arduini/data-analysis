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
        //return fileReport;
        // TODO validar linha via regex

        final var data = line.split(dataSeparator);

        final var sale = SaleVO.builder()
                .saleId(Long.valueOf(data[1]))
                .itens(processSaleItens(data[2]))
                .salesmanName(data[3])
                .build();

        final var totalSaleValue = calculateSaleTotalPrice(sale.getItens());

        if (Objects.isNull(fileReport.getMostExpensiveSalePrice()) || totalSaleValue.compareTo(fileReport.getMostExpensiveSalePrice()) == 1) {
            fileReport.setMostExpensiveSalePrice(totalSaleValue);
            fileReport.setMostExpensiveSaleId(sale.getSaleId());
        }

        return fileReport;
    }

    private List<SaleItemVO> processSaleItens(String itensArray) {

        // TODO refatorar pra usar regex
        // TODO validar linha via regex
        var list = new ArrayList<SaleItemVO>();

        itensArray = itensArray.substring(1, itensArray.length()-1);

        final var itens = itensArray.split(",");

        for (String s:itens) {

            var itens2 = s.split("-");
            list.add(SaleItemVO.builder()
                    .itemId(Long.valueOf(itens2[0]))
                    .amount(Integer.valueOf(itens2[1]))
                    .itemPrice(new BigDecimal(itens2[2]))
                    .build());
        }

        return list;
    }

    private BigDecimal calculateSaleTotalPrice(final List<SaleItemVO> itens) {

        var totalValue = BigDecimal.ZERO;

        for (SaleItemVO saleitem:itens) {
            var itemValue = saleitem.getItemPrice().multiply(new BigDecimal(saleitem.getAmount()));
            totalValue = totalValue.add(itemValue);
        }

        return totalValue;
    }
}
