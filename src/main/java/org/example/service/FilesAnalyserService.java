package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.DataFileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilesAnalyserService {

    private final FilesFinderService fileFinderService;
    private final FileLinesAnalyserService fileAnalyserService;
    private final FileReportGeneratorService fileReportGeneratorService;

    @Value("${data-analysis.file.input.path}")
    private String inputPath;

    @Value("${data-analysis.file.input.type}")
    private String inputType;

    @Value("${data-analysis.file.output.path}")
    private String outputPath;

    @Value("${data-analysis.file.output.type}")
    private String outputType;

    public void findAndProcessFiles() throws DataFileNotFoundException {

        fileFinderService.findFiles(inputPath, inputType)
            .orElseThrow(DataFileNotFoundException::new)
            .forEach(this::processFile);
    }

    private void processFile(Path file) {

        Optional.of(file)
            .map(fileAnalyserService::process)
            .map(f -> fileReportGeneratorService.generate(f, outputPath, outputType));
    }
}
