package com.bachelor.thesis.organization_education.requests.general.abstracts;

import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import org.springframework.validation.annotation.Validated;

/**
 * An abstract class representing the query structure that can be used to interact with the system.
 */
@Validated
public interface Request {
    /**
     * Retrieves a FindRequest object representing the parameters for a find operation.
     *
     * @return a FindRequest object containing the criteria for the find operation.
     */
    FindRequest getFindRequest();
}