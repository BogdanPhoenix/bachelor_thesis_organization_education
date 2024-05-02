package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.general.university.FacultyRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.UUID;

/**
 * Service interface for managing faculties, extending basic CRUD operations.
 */
public interface FacultyService extends CrudService {
    /**
     * Adds a new faculty resource based on the provided request and user ID.
     *
     * @param request   the request object containing data for creating the faculty.
     * @param adminId    the ID of the user performing the operation.
     * @return          the newly added faculty entity.
     * @throws NullPointerException if null was passed to the request.
     * @throws DuplicateException if the table contains an entity value that is passed in the query.
     */
    Faculty addResource(@NonNull FacultyRequest request, @NonNull String adminId) throws NullPointerException, DuplicateException;

    /**
     * Updates the attributes of the selected entity.
     *
     * @param adminId identifier of the university administrator.
     * @param entityId unique identifier of the entity.
     * @param request with new data.
     * @return the updated entity.
     * @throws DuplicateException if the table already contains the data that is passed in the query.
     * @throws NotFindEntityInDataBaseException if the entity could not be found.
     */
    BaseTableInfo updateValue(@NonNull String adminId, @NonNull UUID entityId, @NonNull FacultyRequest request) throws DuplicateException, NotFindEntityInDataBaseException;
}
