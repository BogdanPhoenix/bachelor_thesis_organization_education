package com.bachelor.thesis.organization_education.services.interfaces.user;

import lombok.NonNull;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;

import java.util.UUID;

public interface StudentService extends CrudService {
    /**
     * Registering a new lecturer using the sent request and user ID.
     *
     * @param request request for registration
     * @param userId user ID
     * @throws DuplicateException if the table contains an entity value that is passed in the query.
     */
    BaseTableInfo registration(@NonNull RegistrationRequest request, @NonNull UUID userId) throws DuplicateException;
}
