package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.DataFileLineTypeEnum;
import org.example.service.processors.FileDataLineCustomerProcessor;
import org.example.service.processors.FileDataLineProcessorInterface;
import org.example.service.processors.FileDataLinePurchaseProcessor;
import org.example.service.processors.FileDataLineSalesmanProcessor;
import org.example.vo.FileDataAnalysisReportVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileLinesAnalyserService {

    private final FileDataLineSalesmanProcessor fileDataLineSalesmanProcessor;
    private final FileDataLineCustomerProcessor fileDataLineCustomerProcessor;
    private final FileDataLinePurchaseProcessor fileDataLinePurchaseProcessor;

    @Value("${data-analysis.file.separator}")
    private String dataSeparator;

    public FileDataAnalysisReportVO process(final Path filePath) {

        final var file = filePath.toFile();
        log.info("I=processing file: {}", file);

        final var fileReport = FileDataAnalysisReportVO.builder()
                .fileName(file.getName())
                .customersAmount(0)
                .salesPeopleAmount(0)
                .totalSalesBySalesMan(new HashMap<>())
                .build();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                processLineByCode(fileReport, line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO tratar excecoes

        return fileReport;
    }

    private void processLineByCode(FileDataAnalysisReportVO fileReport, final String line) {

        String[] values = line.split(dataSeparator);
        var code = DataFileLineTypeEnum.getByCode(values[0]);

        FileDataLineProcessorInterface lineProcessor;
        switch (code) {
            case SALESMAN:
                lineProcessor = fileDataLineSalesmanProcessor;
                break;
            case CUSTOMER:
                lineProcessor = fileDataLineCustomerProcessor;
                break;
            case PURCHASE:
                lineProcessor = fileDataLinePurchaseProcessor;
                break;
            default:
                log.error("E=File data line type unknown!!, line={}", line);
                return;
        }

        lineProcessor.processLine(fileReport, line);
    }
}
