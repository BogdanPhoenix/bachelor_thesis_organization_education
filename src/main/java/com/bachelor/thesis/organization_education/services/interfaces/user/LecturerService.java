package com.bachelor.thesis.organization_education.services.interfaces.user;

import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import lombok.NonNull;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import org.springframework.transaction.annotation.Transactional;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;

import java.util.UUID;

/**
 * Lecturer service that implements CRUD (create, read, update, delete) operations.
 */
@Transactional
public interface LecturerService extends CrudService {
    /**
     * Registering a new lecturer using the sent request and user ID.
     *
     * @param request request for registration
     * @param userId user ID
     * @throws DuplicateException if the table contains an entity value that is passed in the query.
     */
    void registration(@NonNull RegistrationRequest request, @NonNull String userId) throws DuplicateException;

    /**
     * Updates the teacher data with the specified ID based on the received request.
     *
     * @param id teacher identifier
     * @param request update request
     * @return updated teacher data
     * @throws NotFindEntityInDataBaseException if the teacher is not found in the database
     */
    Lecturer updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws NotFindEntityInDataBaseException;

    /**
     * Makes the entity active for further interaction with the program.
     *
     * @param userId unique identifier of the user.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void activate(@NonNull String userId);

    /**
     * Makes the entity inactive, which does not allow the program to interact with it.
     *
     * @param userId unique identifier of the user.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void deactivate(@NonNull String userId);

    /**
     * Searches for an entity in the database by the specified query and deletes it completely.
     *
     * @param userId unique identifier of the user.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void deleteValue(@NonNull String userId);

    /**
     *
     * @param lecturerId
     * @param disciplineId
     * @throws NotFindEntityInDataBaseException
     */
    void addDiscipline(@NonNull Long lecturerId, @NonNull Long disciplineId) throws NotFindEntityInDataBaseException;
}
