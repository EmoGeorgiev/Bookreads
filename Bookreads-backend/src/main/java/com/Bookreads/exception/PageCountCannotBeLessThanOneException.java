package com.Bookreads.exception;

public class PageCountCannotBeLessThanOneException extends RuntimeException {
    public PageCountCannotBeLessThanOneException() {

    }
    public PageCountCannotBeLessThanOneException(String message) {
        super(message);
    }
    public PageCountCannotBeLessThanOneException(Throwable cause) {
        super(cause);
    }
    public PageCountCannotBeLessThanOneException(String message, Throwable cause) {
        super(message, cause);
    }
}
