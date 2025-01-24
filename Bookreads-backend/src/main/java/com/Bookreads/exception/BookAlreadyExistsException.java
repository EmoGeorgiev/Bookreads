package com.Bookreads.exception;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException() {
    }

    public BookAlreadyExistsException(String message) {
        super(message);
    }

    public BookAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public BookAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
