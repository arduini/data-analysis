package org.example.unit.processors;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.exception.FileLineValidationException;
import org.example.service.processors.FileDataLineSaleProcessor;
import org.example.vo.FileDataAnalysisReportVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FileDataLineSaleProcessorTest {

    @InjectMocks
    private FileDataLineSaleProcessor fileDataLineSaleProcessor;

    @Test
    public void test_invalidLine() {

        final var fileReportVO = FileDataAnalysisReportVO.builder()
                .customersAmount(0)
                .salesPeopleAmount(0)
                .totalSalesBySalesMan(new HashMap<>())
                .build();

        final var line = RandomStringUtils.randomAlphabetic(10);
        Exception exception = assertThrows(FileLineValidationException.class, () ->
                fileDataLineSaleProcessor.processLine(fileReportVO, line));
        assertEquals("File line structure unexpected: " + line, exception.getMessage());
    }

    @Test
    public void test_invalidSaleItem() {

        final var fileReportVO = FileDataAnalysisReportVO.builder()
                .customersAmount(0)
                .salesPeopleAmount(0)
                .totalSalesBySalesMan(new HashMap<>())
                .build();

        final var line = "003ç10ç[" + RandomStringUtils.randomAlphabetic(10) + "]çPedro";
        Exception exception = assertThrows(FileLineValidationException.class, () ->
                fileDataLineSaleProcessor.processLine(fileReportVO, line));
        assertEquals("File line structure unexpected", exception.getMessage());
    }

    @Test
    public void test_customerProcessorSuccess() {

        final var fileReportVO = FileDataAnalysisReportVO.builder()
                .customersAmount(0)
                .salesPeopleAmount(0)
                .totalSalesBySalesMan(new HashMap<>())
                .build();

        final var line = "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro";
        fileDataLineSaleProcessor.processLine(fileReportVO, line);

        assertEquals(10L, fileReportVO.getMostExpensiveSaleId());
        assertEquals(new BigDecimal(1199.00).setScale(2), fileReportVO.getMostExpensiveSalePrice());
        assertEquals("Pedro", fileReportVO.getWorstSalesmanName());
    }

    @Test
    public void test_customerProcessorAddSaleSuccess() {

        final var fileReportVO = FileDataAnalysisReportVO.builder()
                .customersAmount(0)
                .salesPeopleAmount(0)
                .totalSalesBySalesMan(new HashMap<>())
                .build();

        final var line = "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro";
        fileDataLineSaleProcessor.processLine(fileReportVO, line);

        final var line2 = "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPedro";
        fileDataLineSaleProcessor.processLine(fileReportVO, line2);

        assertEquals(10L, fileReportVO.getMostExpensiveSaleId());
        assertEquals(new BigDecimal(5000), fileReportVO.getMostExpensiveSalePrice());
        assertEquals("Pedro", fileReportVO.getWorstSalesmanName());
    }
}
