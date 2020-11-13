package org.example.unit.processors;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.service.processors.FileDataLineSalesmanProcessor;
import org.example.vo.FileDataAnalysisReportVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FileDataLineSalesmanProcessorTest {

    @InjectMocks
    private FileDataLineSalesmanProcessor fileDataLineSalesmanProcessor;

    @Test
    public void test_customerProcessorSuccess() {

        final var line = RandomStringUtils.randomAlphabetic(10);
        final var fileReportVO = FileDataAnalysisReportVO.builder()
                .salesPeopleAmount(0)
                .customersAmount(0)
                .build();

        fileDataLineSalesmanProcessor.processLine(fileReportVO, line);

        assertEquals(Integer.valueOf(1), fileReportVO.getSalesPeopleAmount());
        assertEquals(Integer.valueOf(0), fileReportVO.getCustomersAmount());
    }
}
