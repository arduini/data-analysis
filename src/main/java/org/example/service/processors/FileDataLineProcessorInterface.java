package org.example.service.processors;

import org.example.vo.FileDataAnalysisReportVO;

public interface FileDataLineProcessorInterface {

    FileDataAnalysisReportVO processLine(final FileDataAnalysisReportVO fileReport, String line);
}
