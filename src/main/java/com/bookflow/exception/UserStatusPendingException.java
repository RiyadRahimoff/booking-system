package com.bookflow.exception;

public class UserStatusPendingException extends RuntimeException {
    public UserStatusPendingException(String message) {
        super(message);
    }
}
