package org.example.service.processors;

import org.example.vo.FileDataAnalysisReportVO;
import org.springframework.stereotype.Service;

@Service
public class FileDataLineCustomerProcessor implements FileDataLineProcessorInterface{

    @Override
    public FileDataAnalysisReportVO processLine(FileDataAnalysisReportVO fileReport, String line) {
        // TODO validar linha via regex
        return fileReport.incrementCustomersAmount();
    }
}
