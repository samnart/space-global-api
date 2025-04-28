package com.samnart.space_global.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class ErrorResponse {
    
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private Map<String, String> errors;

    public ErrorResponse(int status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(int status, String message, Map<String, String> errors) {
        this(status, message);
        this.errors = errors;
    }
}
