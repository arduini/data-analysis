package org.example.exception;

public class FileProcessingException extends RuntimeException {

    public FileProcessingException(final String msg, Throwable cause) {
        super(msg, cause);
    }
}
