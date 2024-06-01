package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bachelor.thesis.organization_education.services.interfaces.crud.*;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.responces.university.MagazineResponse;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.UUID;

/**
 * An interface that implements interaction between groups and disciplines.
 * Extends the basic CRUD service interface to provide collaborative CRUD operations.
 */
public interface GroupDisciplineService extends CreateService, ReadService, UpdateService, DeleteService, StateManagementService {
    /**
     * Returns all group-discipline entities created by the university administrator. The administrator ID is taken from the authorized user...
     *
     * @param pageable page settings
     * @return a set of entities.
     */
    Page<Response> getAllByUniversityAdmin(@NonNull Pageable pageable);
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
     * @param pageable page settings
     * @return a list of magazines response.
     */
    Page<MagazineResponse> getMagazinesByLecturer(@NonNull UUID lecturerId, @NonNull Pageable pageable);

    /**
     * Retrieves all magazines with pagination support.
     *
     * @param pageable page settings
     * @return A list of magazine responses with pagination applied.
     */
    Page<MagazineResponse> getAllMagazine(@NonNull Pageable pageable);
}
