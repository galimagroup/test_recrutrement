package com.architech.test.product.utils;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path
) {
    public ErrorResponse(HttpStatus status, String message, String path) {
        this(
            LocalDateTime.now(),
            status.value(),
            status.getReasonPhrase(),
            message,
            path
        );
    }
}
