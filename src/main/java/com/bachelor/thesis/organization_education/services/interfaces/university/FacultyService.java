package com.bachelor.thesis.organization_education.services.interfaces.university;

import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.general.university.FacultyRequest;

/**
 * Service interface for managing faculties, extending basic CRUD operations.
 */
public interface FacultyService extends CrudService {
    /**
     * Adds a new faculty resource based on the provided request and user ID.
     *
     * @param request   the request object containing data for creating the faculty.
     * @param userId    the ID of the user performing the operation.
     * @return          the newly added faculty entity.
     * @throws NullPointerException if null was passed to the request.
     * @throws DuplicateException if the table contains an entity value that is passed in the query.
     */
    Faculty addResource(FacultyRequest request, String userId) throws NullPointerException, DuplicateException;
}
