package com.bachelor.thesis.organization_education.exceptions;

/**
 * Custom exception class for handling file-related errors.
 * Extends RuntimeException for unchecked exception behavior.
 */
public class FileException extends RuntimeException {
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
