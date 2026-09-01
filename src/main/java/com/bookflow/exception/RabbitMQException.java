package com.bookflow.exception;

public class RabbitMQException extends RuntimeException {
    public RabbitMQException(String message) {
        super(message);
    }
}
