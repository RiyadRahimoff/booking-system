package com.bookflow.exception;

public class UserCannotBlockException extends RuntimeException {
    public UserCannotBlockException(String message) {
        super(message);
    }
}
