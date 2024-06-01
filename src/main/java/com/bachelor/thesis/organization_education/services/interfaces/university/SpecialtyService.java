package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import com.bachelor.thesis.organization_education.services.interfaces.crud.*;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.UUID;

/**
 * Interface representing a service for managing specialty.
 * Extends CrudService to provide basic CRUD operations.
 */
public interface SpecialtyService extends CreateService, ReadService, UpdateService, DeleteService, StateManagementService {

    /**
     * Updates the attributes of the selected entity.
     *
     * @param id unique identifier of the entity.
     * @param request with new data.
     * @return the updated entity.
     * @throws DuplicateException if the table already contains the data that is passed in the query.
     * @throws NotFindEntityInDataBaseException if the entity could not be found.
     */
    @Override
    BaseTableInfo updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws DuplicateException, NotFindEntityInDataBaseException;
}
