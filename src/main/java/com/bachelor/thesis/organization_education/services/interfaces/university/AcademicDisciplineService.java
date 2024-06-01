package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import com.bachelor.thesis.organization_education.services.interfaces.crud.*;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.UUID;

/**
 * Interface for managing academic disciplines, extending basic CRUD operations.
 */
public interface AcademicDisciplineService extends CreateService, ReadService, UpdateService, DeleteService, StateManagementService {
    /**
     * Цей метод призначає лектора за конкретною навчальною дисципліною.
     *
     * @param disciplineId Ідентифікатор навчальної дисципліни.
     * @param lecturerId Ідентифікатор лектора, якого потрібно призначити.
     * @throws NotFindEntityInDataBaseException Якщо вказану навчальну дисципліну або лектора не знайдено в базі даних.
     */
    void addLecturer(@NonNull UUID disciplineId, @NonNull UUID lecturerId) throws NotFindEntityInDataBaseException;

    /**
     * Breaks the connection between the discipline and the lecturer.
     *
     * @param disciplineId identifier of the discipline
     * @param lecturerId lecturer identifier
     * @throws NotFindEntityInDataBaseException exception thrown if the corresponding entity is not found in the database
     */
    void disconnectLecturer(@NonNull UUID disciplineId, @NonNull UUID lecturerId) throws NotFindEntityInDataBaseException;

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
