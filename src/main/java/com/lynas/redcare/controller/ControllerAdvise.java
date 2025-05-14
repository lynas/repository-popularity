package com.lynas.redcare.controller;

import com.lynas.redcare.dto.ErrorResponse;
import com.lynas.redcare.exception.APICallException;
import com.lynas.redcare.exception.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;

@RestControllerAdvice
public class ControllerAdvise {

    @ExceptionHandler(APICallException.class)
    public ResponseEntity<ErrorResponse> handleClientException(APICallException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                Collections.singletonMap("error", ex.getMessage()),
                "RESOURCE_NOT_FOUND"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatusCode().value()));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                Collections.singletonMap("error", ex.getMessage()),
                "INVALID"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatusCode().value()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        var paramName = ex.getName();
        var value = ex.getValue();
        var message = ex.getMessage();
        var lastUpdatedAt = paramName.equals("lastUpdatedAt");
        if (lastUpdatedAt) {
            message = String.format("Invalid %s field : [ %s ]", paramName, value);
        }

        ErrorResponse errorResponse = new ErrorResponse(
                Collections.singletonMap("error", message),
                "INVALID_INPUT"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
