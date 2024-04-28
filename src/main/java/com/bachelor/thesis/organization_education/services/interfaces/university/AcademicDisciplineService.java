package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.UUID;

/**
 * Interface for managing academic disciplines, extending basic CRUD operations.
 */
public interface AcademicDisciplineService extends CrudService {
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
}
