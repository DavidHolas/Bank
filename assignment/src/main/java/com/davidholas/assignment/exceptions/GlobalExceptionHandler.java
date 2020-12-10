package com.davidholas.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorMessage(), ex.getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorMessage(), ex.getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<Object> handleBussinesException(BusinessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorMessage(), ex.getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
