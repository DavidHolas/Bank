package com.davidholas.assignment.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class BaseException extends RuntimeException{

    private HttpStatus httpStatus;

    private String errorMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private BaseException() {
        this.timestamp = LocalDateTime.now();
    }

    public BaseException(String errorMessage, HttpStatus status) {
        this();
        this.errorMessage = errorMessage;
        this.httpStatus = status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
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
