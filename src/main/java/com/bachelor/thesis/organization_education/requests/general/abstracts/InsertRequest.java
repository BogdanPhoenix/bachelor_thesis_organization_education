package com.bachelor.thesis.organization_education.requests.general.abstracts;

import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;

/**
 * Interface for processing data addition requests.
 * This interface defines methods for requests to insert data into the database.
 */
public interface InsertRequest {
    /**
     * Retrieves a FindRequest object representing the parameters for a find operation.
     *
     * @return a FindRequest object containing the criteria for the find operation.
     */
    FindRequest getFindRequest();
}