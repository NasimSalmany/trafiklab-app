package com.app.trafiklab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ExternalApiException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ErrorMessage externalApiException(ExternalApiException ex) {
        return new ErrorMessage(HttpStatus.TOO_MANY_REQUESTS.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                "API usage limit exceeded. Please try again later.");
    }
}
