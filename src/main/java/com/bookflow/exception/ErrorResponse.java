package com.bookflow.exception;

public record ErrorResponse(
        int statusCode,
        String message
) {
}
