package com.lynas.redcare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientException extends ResponseStatusException {
    public ClientException(Integer statusCode, String message) {
        super(HttpStatus.resolve(statusCode), message);
    }
}
