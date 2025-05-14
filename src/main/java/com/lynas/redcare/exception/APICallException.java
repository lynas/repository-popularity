package com.lynas.redcare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class APICallException extends ResponseStatusException {
    public APICallException(Integer statusCode, String message) {
        super(HttpStatus.resolve(statusCode), message);
    }
}
