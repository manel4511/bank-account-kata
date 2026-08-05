package com.sg.kata.bankaccount.adapter.in.web.exception;

import java.time.Instant;
import java.util.Map;

public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {
    public ApiError {
        validationErrors = validationErrors == null
                ? Map.of()
                : Map.copyOf(validationErrors);
    }
}