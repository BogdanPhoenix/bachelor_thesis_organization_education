package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.responces.university.MagazineResponse;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.List;
import java.util.UUID;

/**
 * An interface that implements interaction between groups and disciplines.
 * Extends the basic CRUD service interface to provide collaborative CRUD operations.
 */
public interface GroupDisciplineService extends CrudService {
    /**
     * Retrieves a magazine by its unique identifier.
     *
     * @param id the unique identifier of the magazine.
     * @return the magazine response containing details of the magazine.
     * @throws NotFindEntityInDataBaseException if the magazine with the given ID is not found in the database.
     */
    MagazineResponse getMagazine(@NonNull UUID id) throws NotFindEntityInDataBaseException;

    /**
     * Returns the log by its unique identifier for the specified student.
     *
     * @param studentId unique student identifier.
     * @param entityId unique magazine identifier.
     * @return the magazine response containing the log details.
     * @throws NotFindEntityInDataBaseException if the log with the specified ID is not found in the database.
     */
    MagazineResponse getMagazine(@NonNull UUID studentId, @NonNull UUID entityId) throws NotFindEntityInDataBaseException;

    /**
     * Returns all journals that belong to the specified teacher.
     *
     * @param lecturerId unique identifier of the lecturer.
     * @return a list of magazines response.
     */
    List<MagazineResponse> getMagazinesByLecturer(@NonNull UUID lecturerId);

    /**
     * Retrieves all magazines with pagination support.
     *
     * @return A list of magazine responses with pagination applied.
     */
    List<MagazineResponse> getAllMagazine();
}
