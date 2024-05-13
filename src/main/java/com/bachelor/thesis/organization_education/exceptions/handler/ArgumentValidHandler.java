package com.bachelor.thesis.organization_education.exceptions.handler;

import jakarta.validation.ConstraintViolation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class ArgumentValidHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::mapObjectErrorToErrorResponse)
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }

    private ErrorResponse mapObjectErrorToErrorResponse(ObjectError error) {
        var fieldName = (error instanceof FieldError fieldError)
                ? fieldError.getField()
                : error.getObjectName();
        var value = (error instanceof FieldError fieldError)
                ? getValue(fieldError.getRejectedValue())
                : "";
        var errorMessage = error.getDefaultMessage();

        return new ErrorResponse(fieldName, value, errorMessage);
    }

    private String getValue(Object rejectedValue) {
        if(rejectedValue == null) {
            return "";
        } else if(rejectedValue instanceof Collection<?>) {
            return "A collection of values.";
        } else if(rejectedValue instanceof Map<?, ?>) {
            return "Dictionary of meanings.";
        } else if(rejectedValue instanceof Object[]) {
            return "An array of values.";
        }
        return rejectedValue.toString();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorResponse>> handleValidationExceptions(ConstraintViolationException ex) {
        var errors = ex.getConstraintViolations()
                .stream()
                .map(this::mapConstraintViolationToErrorResponse)
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }

    private ErrorResponse mapConstraintViolationToErrorResponse(ConstraintViolation<?> violation) {
        var fieldName = getFieldFromViolation(violation);
        var value = violation.getInvalidValue();
        var errorMessage = violation.getMessage();

        return new ErrorResponse(fieldName, value.toString(), errorMessage);
    }

    private String getFieldFromViolation(ConstraintViolation<?> violation) {
        var iterator = violation.getPropertyPath().iterator();
        var field = "";
        while (iterator.hasNext()) {
            field = iterator.next().getName();
        }

        return field;
    }

    public record ErrorResponse (
        String field,
        Object value,
        String errorMessage
    ) {}
}
