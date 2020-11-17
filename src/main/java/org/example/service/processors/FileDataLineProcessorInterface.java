package org.example.service.processors;

import org.example.enums.DataFileLineTypeEnum;
import org.example.exception.FileLineValidationException;
import org.example.vo.FileDataAnalysisReportVO;

import java.util.regex.Pattern;

public interface FileDataLineProcessorInterface {

    FileDataAnalysisReportVO processLine(final FileDataAnalysisReportVO fileReport, final String line);

    default void validate(final String line) {

        final var lineTypeEnum = DataFileLineTypeEnum.fromLine(line)
                .orElseThrow(() -> new FileLineValidationException("File line code not found: " + line));

        if (!Pattern.compile(lineTypeEnum.getRegex()).matcher(line).find()) {
            throw new FileLineValidationException("Invalid file line structure: " + line);
        }
    }
}
