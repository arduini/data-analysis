package org.example.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.DataFileLineTypeEnum;
import org.example.exception.FileLineValidationException;
import org.example.service.processors.FileDataLineCustomerProcessor;
import org.example.service.processors.FileDataLineProcessorInterface;
import org.example.service.processors.FileDataLineSaleProcessor;
import org.example.service.processors.FileDataLineSalesmanProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLineProcessorFactory {

    private final FileDataLineSalesmanProcessor fileDataLineSalesmanProcessor;
    private final FileDataLineCustomerProcessor fileDataLineCustomerProcessor;
    private final FileDataLineSaleProcessor fileDataLineSaleProcessor;

    public FileDataLineProcessorInterface getDataLineProcessor(final String line) {

        final var lineTypeEnum = DataFileLineTypeEnum.fromLine(line)
                .orElseThrow(() -> new FileLineValidationException("File line code not found: " + line));

        switch (lineTypeEnum) {
            case SALESMAN:
                return fileDataLineSalesmanProcessor;
            case CUSTOMER:
                return fileDataLineCustomerProcessor;
            case SALE:
                return fileDataLineSaleProcessor;
            default:
                log.error("E=Line Type Processor not implemented, line={}, lineTypeEnum={}", line, lineTypeEnum);
                throw new FileLineValidationException("File line processor not found: " + line);
        }
    }
}
