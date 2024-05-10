package com.bachelor.thesis.organization_education.exceptions.handler;

import lombok.NonNull;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.client.RestClientException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.bachelor.thesis.organization_education.exceptions.FileException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.exceptions.UserCreatingException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.Map;

@RestControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            FileException.class,
            NotFoundException.class,
            DuplicateException.class,
            RestClientException.class,
            NullPointerException.class,
            IllegalStateException.class,
            UserCreatingException.class,
            NotFindEntityInDataBaseException.class
    })
    public ResponseEntity<Object> handleExceptionInfo(Exception ex) {
        var errors = createMessage(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(OAuth2AuthenticationException ex) {
        var errors = createMessage(ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        var errors = createMessage(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    private Map<String, String> createMessage(Exception ex) {
        return Map.of("errorMessage", ex.getMessage());
    }
}
