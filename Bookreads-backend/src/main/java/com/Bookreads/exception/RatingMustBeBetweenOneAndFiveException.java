package com.Bookreads.exception;

public class RatingMustBeBetweenOneAndFiveException extends RuntimeException {
    public RatingMustBeBetweenOneAndFiveException() {

    }
    public RatingMustBeBetweenOneAndFiveException(String message) {
        super(message);
    }
    public RatingMustBeBetweenOneAndFiveException(Throwable cause) {
        super(cause);
    }
    public RatingMustBeBetweenOneAndFiveException(String message, Throwable cause) {
        super(message, cause);
    }
}
