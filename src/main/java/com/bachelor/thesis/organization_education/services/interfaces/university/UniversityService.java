package com.bachelor.thesis.organization_education.services.interfaces.university;

import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.insert.university.UniversityInsertRequest;
import com.bachelor.thesis.organization_education.services.interfaces.CrudService;
import lombok.NonNull;

/**
 * This interface represents a service for managing university entities.
 * It extends the CrudService interface to inherit CRUD operations.
 */
public interface UniversityService extends CrudService {
    /**
     * Create a new record about the university.
     *
     * @param request data to create a new entity.
     * @param userId identifier of the user who added the resource.
     * @return the saved entity; never {@literal null}.
     * @throws NullPointerException if null was passed to the request.
     * @throws DuplicateException if the table contains an entity value that is passed in the query.
     */
    University addResource(@NonNull UniversityInsertRequest request, @NonNull String userId) throws NullPointerException, DuplicateException;
}
