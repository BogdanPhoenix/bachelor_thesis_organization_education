package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.UUID;

/**
 * This interface represents a service for managing university entities.
 * It extends the CrudService interface to inherit CRUD operations.
 */
public interface UniversityService extends CrudService {
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
     * Delete the university entity associated with the user.
     *
     * @param userId identifier of the university administrator.
     */
    void deleteUserEntity(UUID userId) ;
}
