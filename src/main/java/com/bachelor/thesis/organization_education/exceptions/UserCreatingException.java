package com.bachelor.thesis.organization_education.exceptions;

/**
 * An exception that occurs when a user account could not be created.
 * It is usually used to signal an invalid event in the context of creating an object.
 */
public class UserCreatingException extends RuntimeException {
    public UserCreatingException(String message) {
        super(message);
    }
}
