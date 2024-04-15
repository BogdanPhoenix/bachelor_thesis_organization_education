package com.bachelor.thesis.organization_education.requests.update.abstracts;

import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import org.springframework.validation.annotation.Validated;

/**
 * Interface representing an update request.
 * Contains a method to retrieve the find request associated with the update.
 */
@Validated
public interface UpdateRequest {
    /**
     * Retrieves the find request associated with the update.
     *
     * @return The find request.
     */
    FindRequest getFindRequest();
}
