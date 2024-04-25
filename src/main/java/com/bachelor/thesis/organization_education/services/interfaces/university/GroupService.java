package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import com.bachelor.thesis.organization_education.dto.Group;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.insert.university.GroupInsertRequest;

/**
 * Service for managing resource groups.
 * Implements CRUD operations and additional functionality.
 */
public interface GroupService extends CrudService {
    /**
     * Adds a new group based on a query.
     *
     * @param request request to add a group
     * @return the added group object
     * @throws DuplicateException if the resource already exists in the database
     * @throws NullPointerException if a null request was passed
     */
    Group addResource(@NonNull GroupInsertRequest request) throws DuplicateException, NullPointerException;
}
