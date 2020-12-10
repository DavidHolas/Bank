package com.davidholas.assignment.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessException extends BaseException {

    public BusinessException(String errorMessage) {
        super(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
