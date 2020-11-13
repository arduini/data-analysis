package org.example.exception;

public class FileLineValidationException extends RuntimeException {

    public FileLineValidationException(final String msg) {
        super(msg);
    }
}
