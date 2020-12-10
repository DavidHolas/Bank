package com.davidholas.assignment.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage, HttpStatus.NOT_FOUND);
    }
}
