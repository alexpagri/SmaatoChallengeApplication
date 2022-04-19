package com.example.challenge.rest.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class EndpointFailedAdvice extends ResponseEntityExceptionHandler {

    private String getMessage() {
        return "failed";
    }

    // handle thrown exceptions
    @ExceptionHandler(EndpointFailedException.class)
    @ResponseStatus(HttpStatus.OK)
    public String EndpointFailedExceptionHandler(EndpointFailedException ex) {
        return getMessage();
    }

    // covers datatype conversion issues
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(new EndpointFailedException(), getMessage(), headers, HttpStatus.OK, request);
    }
}
