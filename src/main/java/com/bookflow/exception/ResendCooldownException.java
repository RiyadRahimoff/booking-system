package com.bookflow.exception;

public class ResendCooldownException extends RuntimeException {
    public ResendCooldownException(String message) {
        super(message);
    }
}
