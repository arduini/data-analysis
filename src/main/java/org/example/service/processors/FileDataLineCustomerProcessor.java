package org.example.service.processors;

import lombok.NonNull;
import org.example.vo.FileDataAnalysisReportVO;
import org.springframework.stereotype.Service;

@Service
public class FileDataLineCustomerProcessor implements FileDataLineProcessorInterface{

    @Override
    public FileDataAnalysisReportVO processLine(@NonNull final FileDataAnalysisReportVO fileReport, @NonNull final String line) {
        return fileReport.incrementCustomersAmount();
    }
}
