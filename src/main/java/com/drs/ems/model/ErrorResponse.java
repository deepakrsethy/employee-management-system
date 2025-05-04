package com.drs.ems.model;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        int status,
        String error,
        String eeroCode,
        LocalDateTime timeStamp
) {
    public ErrorResponse(String message, int status, String error, String errorCode) {
        this(message, status, error, errorCode, LocalDateTime.now());
    }
}
