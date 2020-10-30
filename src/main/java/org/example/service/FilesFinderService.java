package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FilesFinderService {

    public List<Path> findFiles(final String path, final String fileType) {

        final var filesList = new ArrayList<Path>();

        try (DirectoryStream<Path> paths = Files.newDirectoryStream(
                Paths.get(path),
                p -> p.toString().endsWith(fileType) && p.toFile().isFile())) {
            paths.forEach(p -> filesList.add(p));
        } catch (IOException e) {
            log.error("E=error finding files in directory, path={}, fileType={}", path, fileType, e);
        }

        return filesList;
    }
}
