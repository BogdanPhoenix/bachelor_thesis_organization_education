package com.bachelor.thesis.organization_education.services.interfaces.user;

import com.bachelor.thesis.organization_education.dto.Lecturer;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import lombok.NonNull;

import java.util.UUID;

/**
 * Lecturer service that implements CRUD (create, read, update, delete) operations.
 */
public interface LecturerService extends CrudService {
    /**
     * Registering a new lecturer using the sent request and user ID.
     *
     * @param request request for registration
     * @param userId user ID
     */
    void registration(@NonNull RegistrationRequest request, @NonNull String userId);

    /**
     * Updates the teacher data with the specified ID based on the received request.
     *
     * @param id teacher identifier
     * @param request update request
     * @return updated teacher data
     * @throws NotFindEntityInDataBaseException if the teacher is not found in the database
     */
    Lecturer updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws NotFindEntityInDataBaseException;
}
