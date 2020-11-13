package org.example.unit.processors;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.service.processors.FileDataLineCustomerProcessor;
import org.example.vo.FileDataAnalysisReportVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FileDataLineCustomerProcessorTest {

    @InjectMocks
    private FileDataLineCustomerProcessor fileDataLineCustomerProcessor;

    @Test
    public void test_customerProcessorSuccess() {

        final var line = RandomStringUtils.randomAlphabetic(10);
        final var fileReportVO = FileDataAnalysisReportVO.builder()
                .customersAmount(0)
                .salesPeopleAmount(0)
                .build();

        fileDataLineCustomerProcessor.processLine(fileReportVO, line);

        assertEquals(Integer.valueOf(1), fileReportVO.getCustomersAmount());
        assertEquals(Integer.valueOf(0), fileReportVO.getSalesPeopleAmount());
    }
}
