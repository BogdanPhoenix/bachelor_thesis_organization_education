package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

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
    void addLecturer(@NonNull Long disciplineId, @NonNull Long lecturerId) throws NotFindEntityInDataBaseException;
}
