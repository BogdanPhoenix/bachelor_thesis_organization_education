package com.bachelor.thesis.organization_education.services.interfaces.university;

import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import lombok.NonNull;
import com.bachelor.thesis.organization_education.dto.Specialty;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.insert.university.SpecialtyInsertRequest;

/**
 * Interface representing a service for managing specialty.
 * Extends CrudService to provide basic CRUD operations.
 */
public interface SpecialtyService extends CrudService {
    /**
     * Adds a new specialty resource based on the provided insert request.
     *
     * @param request The insert request for the new specialty.
     * @return The added specialty.
     * @throws DuplicateException If a specialty with the same data already exists.
     * @throws NullPointerException If the insert request is null.
     */
    Specialty addResource(@NonNull SpecialtyInsertRequest request) throws DuplicateException, NullPointerException;
}
