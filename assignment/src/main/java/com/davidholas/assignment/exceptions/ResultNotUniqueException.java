package com.davidholas.assignment.exceptions;

import org.springframework.http.HttpStatus;

public class ResultNotUniqueException extends BaseException {

    public ResultNotUniqueException(String errorMessage) {
        super(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
