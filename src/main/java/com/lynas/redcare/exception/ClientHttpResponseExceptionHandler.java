package com.lynas.redcare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class ClientHttpResponseExceptionHandler {
    private ClientHttpResponseExceptionHandler() {
    }

    public static boolean handleException(ClientHttpResponse clientHttpResponse) throws IOException {
        var status = clientHttpResponse.getStatusCode();
        if (status.is4xxClientError()) {
            String message = switch (status) {
                case HttpStatus.NOT_FOUND -> "Repository not found with input language and date";
                case HttpStatus.BAD_REQUEST -> "Bad request: check input language and date";
                default -> clientHttpResponse.getStatusText();
            };
            throw new APICallException(status.value(), message);
        }

        if (status.is5xxServerError()) {
            throw new APICallException(status.value(), "Internal Server Error");
        }
        return true;
    }
}
