package org.example.service;

import lombok.extern.slf4j.Slf4j;
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

    public File generate(final FileDataAnalysisReportVO fileDataAnalysisReportVO, final String path, final String fileType) {

        final var filePath = path + File.separator + fileDataAnalysisReportVO.getFileName() + fileType;
        Path file = Paths.get(filePath);

        // TODO se o arquivo já existir?
        // TODO melhorar isso aqui
        try {
            return Files.write(file, fileDataAnalysisReportVO.toStringList(), StandardCharsets.UTF_8).toFile();
        } catch (IOException e) {
            return null;
        }
    }
}
