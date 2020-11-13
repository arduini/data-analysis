package org.example.exception;

public class FileReportGenerationException extends RuntimeException {

    public FileReportGenerationException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
