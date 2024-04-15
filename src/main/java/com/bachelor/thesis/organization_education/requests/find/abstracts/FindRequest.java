package com.bachelor.thesis.organization_education.requests.find.abstracts;

import org.springframework.validation.annotation.Validated;

/**
 * Interface for handling find requests.
 * This interface defines methods for find requests, including an optional method to skip certain operations.
 */
@Validated
public interface FindRequest {
    /**
     * Determines whether to skip certain operations.
     *
     * @return true if operations should be skipped, false otherwise.
     */
    default boolean skip() {
        return false;
    }
}
