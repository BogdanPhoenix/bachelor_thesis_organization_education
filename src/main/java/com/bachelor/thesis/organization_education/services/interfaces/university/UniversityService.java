package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.general.university.UniversityRequest;

import java.util.UUID;

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
    University addValue(@NonNull UniversityRequest request, @NonNull String userId) throws NullPointerException, DuplicateException;

    /**
     * Deactivate the university entity associated with the user.
     *
     * @param userId identifier of the university administrator.
     */
    void deactivateUserEntity(@NonNull UUID userId);

    /**
     * Find a university entity by the user identifier.
     *
     * @param adminId identifier of the university administrator.
     * @return the university entity associated with the user.
     * @throws NotFindEntityInDataBaseException if no entity is found for the given identifier.
     */
    University findByUser(@NonNull UUID adminId) throws NotFindEntityInDataBaseException;

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
    BaseTableInfo updateValue(@NonNull String adminId, @NonNull UUID entityId, @NonNull UpdateRequest request) throws DuplicateException, NotFindEntityInDataBaseException;

    /**
     * Delete the university entity associated with the user.
     *
     * @param userId identifier of the university administrator.
     */
    void deleteUserEntity(UUID userId) ;
}
