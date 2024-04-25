package com.bachelor.thesis.organization_education.exceptions.handler;

import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.exceptions.UserCreatingException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

@RestControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            NotFoundException.class,
            DuplicateException.class,
            RestClientException.class,
            NullPointerException.class,
            IllegalStateException.class,
            UserCreatingException.class,
            NotFindEntityInDataBaseException.class
    })
    public ResponseEntity<Object> handleExceptionInfo(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(OAuth2AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
