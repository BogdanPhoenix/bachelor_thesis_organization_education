package com.bachelor.thesis.organization_education.services.interfaces.user;

import lombok.NonNull;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import org.springframework.transaction.annotation.Transactional;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
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
    void registration(@NonNull RegistrationRequest request, @NonNull UUID userId) throws DuplicateException;

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
     * Searches for an entity in the database by the specified query and deletes it completely.
     *
     * @param userId unique identifier of the user.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void deleteValue(@NonNull UUID userId);

    /**
     * Adds a discipline to the database for the specified lecturer.
     *
     * @param lecturerId lecturer identifier
     * @param disciplineId identifier of the discipline
     * @throws NotFindEntityInDataBaseException exception thrown if the corresponding entity is not found in the database
     */
    void addDiscipline(@NonNull UUID lecturerId, @NonNull UUID disciplineId) throws NotFindEntityInDataBaseException;

    /**
     * Breaks the connection between the lecturer and the discipline.
     *
     * @param lecturerId lecturer identifier
     * @param disciplineId identifier of the discipline
     * @throws NotFindEntityInDataBaseException exception thrown if the corresponding entity is not found in the database
     */
    void disconnectDiscipline(@NonNull UUID lecturerId, @NonNull UUID disciplineId) throws NotFindEntityInDataBaseException;
}
