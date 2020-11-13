package org.example.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.FileProcessingException;
import org.example.exception.FileReportGenerationException;
import org.example.utils.FilesFinderUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilesAnalyserService {

    private final FilesFinderUtils fileFinderUtils;
    private final FileLinesAnalyserService fileLinesAnalyserService;
    private final FileReportGeneratorService fileReportGeneratorService;

    @Value("${data-analysis.file.input.path}")

    private String inputPath;

    @Value("${data-analysis.file.input.type}")
    private String inputType;

    @Value("${data-analysis.file.output.path}")
    private String outputPath;

    @Value("${data-analysis.file.output.type}")
    private String outputType;

    public void findAndProcessFiles() {

        fileFinderUtils.findFiles(inputPath, inputType)
                .forEach(this::processFile);
    }

    private void processFile(@NonNull final Path filePath) {

        try {
            Optional.of(filePath)
                    .map(fileLinesAnalyserService::process)
                    .map(f -> fileReportGeneratorService.generate(f, outputPath, outputType));
        }
        catch (FileProcessingException e) {
            log.error("E=Error processing file, file={}", filePath, e);
        }
        catch (FileReportGenerationException e) {
            log.error("E=Error generating report file, file={}", filePath, e);
        }
    }
}