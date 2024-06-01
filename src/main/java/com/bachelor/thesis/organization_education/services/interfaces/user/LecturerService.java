package com.bachelor.thesis.organization_education.services.interfaces.user;

import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import com.bachelor.thesis.organization_education.dto.AcademicDiscipline;
import com.bachelor.thesis.organization_education.services.interfaces.crud.*;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;

import java.util.Set;
import java.util.UUID;

/**
 * Lecturer service that implements CRUD (create, read, update, delete) operations.
 */
@Transactional
public interface LecturerService extends CreateService, ReadService, UpdateService, DeleteService, StateManagementService {
    /**
     * Registering a new lecturer using the sent request and user ID.
     *
     * @param request request for registration
     * @param userId user ID
     * @throws DuplicateException if the table contains an entity value that is passed in the query.
     */
    BaseTableInfo registration(@NonNull RegistrationRequest request, @NonNull UUID userId) throws DuplicateException;

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

    /**
     * Allows you to get the disciplines taught by the lecturer.
     *
     * @param lecturerId lecturer identifier.
     * @return a set of disciplines taught by a teacher.
     * @throws NotFindEntityInDataBaseException if you can't find anything.
     */
    Set<AcademicDiscipline> getDisciplines(@NonNull UUID lecturerId) throws NotFindEntityInDataBaseException;
}
