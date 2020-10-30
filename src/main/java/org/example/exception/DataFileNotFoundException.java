package org.example.exception;

public class DataFileNotFoundException extends RuntimeException {

    public DataFileNotFoundException() {
        super();
    }

    public DataFileNotFoundException(final String msg) {
        super(msg);
    }
}
