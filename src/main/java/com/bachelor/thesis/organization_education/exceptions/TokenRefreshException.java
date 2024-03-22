package com.bachelor.thesis.organization_education.exceptions;

/**
 * An exception thrown when there are problems with the token update.
 * Usually used to signal a failed attempt to update a JWT token.
 */
public class TokenRefreshException extends RuntimeException {
    public TokenRefreshException(String message) {
        super(message);
    }

    public TokenRefreshException(Throwable cause) {
        super(cause);
    }
}