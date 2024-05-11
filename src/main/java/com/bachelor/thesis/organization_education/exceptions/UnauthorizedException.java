package com.bachelor.thesis.organization_education.exceptions;

/**
 * Custom exception class for unauthorized access.
 * Extends RuntimeException to indicate unchecked exception.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
