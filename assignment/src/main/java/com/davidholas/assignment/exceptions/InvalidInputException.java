package com.davidholas.assignment.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends BaseException {

    public InvalidInputException(String errorMessage) {
        super(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
