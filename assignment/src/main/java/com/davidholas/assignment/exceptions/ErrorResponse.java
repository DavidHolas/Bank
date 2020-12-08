package com.davidholas.assignment.exceptions;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String errorMessage;

    private LocalDateTime timestamp;

    public ErrorResponse(String message, LocalDateTime timestamp) {
        this.errorMessage = message;
        this.timestamp = timestamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
