package org.example.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.FileProcessingException;
import org.example.exception.FileLineValidationException;
import org.example.service.processors.FileDataLineCustomerProcessor;
import org.example.service.processors.FileDataLineProcessorInterface;
import org.example.service.processors.FileDataLineSaleProcessor;
import org.example.service.processors.FileDataLineSalesmanProcessor;
import org.example.vo.FileDataAnalysisReportVO;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.regex.Pattern;

import static org.example.enums.DataFileLineTypeEnum.CUSTOMER;
import static org.example.enums.DataFileLineTypeEnum.SALE;
import static org.example.enums.DataFileLineTypeEnum.SALESMAN;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileLinesAnalyserService {

    private final FileDataLineSalesmanProcessor fileDataLineSalesmanProcessor;
    private final FileDataLineCustomerProcessor fileDataLineCustomerProcessor;
    private final FileDataLineSaleProcessor fileDataLineSaleProcessor;

    public FileDataAnalysisReportVO process(@NonNull final Path filePath) {

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
                log.debug("D=Processing file line, line={}", line);
                processLineByCode(fileReport, line);
            }
        }
        catch (FileNotFoundException e) {
            log.error("E=Error reading file, file={}", filePath, e);
            throw new FileProcessingException("Error reading file", e);
        }
        catch (IOException e) {
            log.error("E=Error reading line", e);
            throw new FileProcessingException("Error reading line", e);
        }
        catch (FileLineValidationException e) {
            log.error("E=Error processing line", e);
            throw new FileProcessingException("Error processing line", e);
        }

        return fileReport;
    }

    private void processLineByCode(@NonNull final FileDataAnalysisReportVO fileReport, @NonNull final String line) {

        FileDataLineProcessorInterface lineProcessor;
        if(Pattern.compile(SALESMAN.getRegex()).matcher(line).find()) {
            lineProcessor = fileDataLineSalesmanProcessor;
        }
        else if (Pattern.compile(CUSTOMER.getRegex()).matcher(line).find()) {
            lineProcessor = fileDataLineCustomerProcessor;
        }
        else if (Pattern.compile(SALE.getRegex()).matcher(line).find()) {
            lineProcessor = fileDataLineSaleProcessor;
        }
        else {
            throw new FileLineValidationException("File line structure unexpected: " + line);
        }

        lineProcessor.processLine(fileReport, line);
    }
}
