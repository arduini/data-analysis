package org.example.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.FilesAnalyserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileDataAnalyserJob {

    private final FilesAnalyserService fileDataAnalyserService;

    @Scheduled(cron = "${data-analysis.job.interval.cron}")
    public void run() {

        try {
            fileDataAnalyserService.findAndProcessFiles();
            log.info("I=data analaysis completed");
        }
        catch (Exception e) {
            log.error("E=error on finding and analysing data files", e);
        }
    }
}
