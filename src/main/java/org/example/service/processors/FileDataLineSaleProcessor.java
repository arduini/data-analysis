package org.example.service.processors;

import lombok.NonNull;
import org.example.exception.FileLineValidationException;
import org.example.vo.FileDataAnalysisReportVO;
import org.example.vo.SaleItemVO;
import org.example.vo.SaleVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.enums.DataFileLineTypeEnum.SALE;

@Service
public class FileDataLineSaleProcessor implements FileDataLineProcessorInterface{

    private final static String SALE_ITEM_REGEX_PATTERN = "([^,]+)";
    private final static String SALE_ITEM_DETAIL_REGEX_PATTERN = "^(\\d+)\\-(\\d+)\\-(\\d+\\.*\\d*)$";

    @Override
    public FileDataAnalysisReportVO processLine(@NonNull final FileDataAnalysisReportVO fileReport, @NonNull final String line) {

        Matcher saleLineMatcher = Pattern.compile(SALE.getRegex()).matcher(line);

        if (!saleLineMatcher.find()) {
            throw new FileLineValidationException("File line structure unexpected: " + line);
        }

        final var sale = SaleVO.builder()
                .id(Long.valueOf(saleLineMatcher.group(2)))
                .items(processSaleItems(saleLineMatcher.group(3)))
                .salesmanName(saleLineMatcher.group(4))
                .build();

        final var totalSaleValue = sale.calculateSaleTotalPrice();

        // Store the total sum of sales by salesman
        if (fileReport.getTotalSalesBySalesMan().containsKey(sale.getSalesmanName())) {
            fileReport.getTotalSalesBySalesMan().put(sale.getSalesmanName(),
                    fileReport.getTotalSalesBySalesMan().get(sale.getSalesmanName()).add(totalSaleValue));
        } else {
            fileReport.getTotalSalesBySalesMan().put(sale.getSalesmanName(), totalSaleValue);
        }

        // Store the most expensive sale information so far
        if (Objects.isNull(fileReport.getMostExpensiveSalePrice()) || totalSaleValue.compareTo(fileReport.getMostExpensiveSalePrice()) == 1) {
            fileReport.setMostExpensiveSalePrice(totalSaleValue);
            fileReport.setMostExpensiveSaleId(sale.getId());
        }

        return fileReport;
    }

    private List<SaleItemVO> processSaleItems(String itemsArray) {

        var saleItemsList = new ArrayList<SaleItemVO>();
        Matcher saleItemMatcher = Pattern.compile(SALE_ITEM_REGEX_PATTERN).matcher(itemsArray);

        while (saleItemMatcher.find()) {
            var saleItemDetailsStr = saleItemMatcher.group();
            Matcher saleItemDetailsMatcher = Pattern.compile(SALE_ITEM_DETAIL_REGEX_PATTERN).matcher(saleItemDetailsStr);

            if(saleItemDetailsMatcher.find()) {
                saleItemsList.add(SaleItemVO.builder()
                        .id(Long.valueOf(saleItemDetailsMatcher.group(1)))
                        .quantity(Integer.valueOf(saleItemDetailsMatcher.group(2)))
                        .price(new BigDecimal(saleItemDetailsMatcher.group(3)))
                        .build());
            }
            else {
                throw new FileLineValidationException("File line structure unexpected");
            }
        }

        return saleItemsList;
    }
}
