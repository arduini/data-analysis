package org.example.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.FileReportGenerationException;
import org.example.vo.FileDataAnalysisReportVO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileReportGeneratorService {

    public File generate(@NonNull final FileDataAnalysisReportVO fileDataAnalysisReportVO, @NonNull final String path, @NonNull final String fileType) {

        final var filePath = path + File.separator + fileDataAnalysisReportVO.getFileName() + fileType;
        Path file = Paths.get(filePath);
        file.toFile().getParentFile().mkdirs();

        try {
            return Files.write(file, fileDataAnalysisReportVO.toStringList(), StandardCharsets.UTF_8).toFile();
        }
        catch (IOException e) {
            log.error("E=Error generating report file, file={}", fileDataAnalysisReportVO.getFileName(), e);
            throw new FileReportGenerationException("Error generating report file", e);
        }
    }
}
