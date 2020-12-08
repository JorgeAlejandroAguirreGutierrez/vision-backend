package com.proyecto.sicecuador.exception;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    private ResponseEntity<Object> handleResponseStatusError(ResponseStatusException ex, WebRequest request) {
        final RestExceptionMessage apiError = new RestExceptionMessage(ex.getStatus(), ex.getReason());
        if(ex.getStatus().equals(HttpStatus.BAD_REQUEST)) {
            apiError.setMessage("Bad parameters in the request: ".concat(apiError.getMessage()));
        }
        return handleExceptionInternal(ex, apiError,
                new HttpHeaders(), ex.getStatus(), request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String>errors=ex.getConstraintViolations()
                .stream()
                .map(it ->
                    it.getPropertyPath().toString()+" "+ it.getMessage())
                .collect(Collectors.toList());
        final RestExceptionMessage apiError = new RestExceptionMessage(HttpStatus.BAD_REQUEST, ex.getMessage(), errors);

        return handleExceptionInternal(ex, apiError,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final RestExceptionMessage apiError = new RestExceptionMessage(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    public ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final RestExceptionMessage apiError = new RestExceptionMessage(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }
    
    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final RestExceptionMessage apiError = new RestExceptionMessage(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }
}
