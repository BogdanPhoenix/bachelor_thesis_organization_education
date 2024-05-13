package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;

/**
 * Service for managing the schedule.
 * Implements CRUD operations and additional functionality.
 */
public interface ScheduleService extends CrudService {
    /**
     * Returns all the Schedule entities created by the university administrator. The administrator ID is taken from the authorized user...
     *
     * @param pageable page settings
     * @return a set of entities.
     */
    Page<Response> getAllByUniversityAdmin(@NonNull Pageable pageable);

    /**
     * Returns all Schedule entities that are required for the lecturer. The lecturer ID is taken from the authorized user...
     *
     * @param pageable page settings
     * @return a set of entities.
     */
    Page<Response> getAllByLecturer(@NonNull Pageable pageable);

    /**
     * Returns all Schedule entities that are required for the student. The student ID is taken from the authorized user...
     *
     * @param pageable page settings
     * @return a set of entities.
     */
    Page<Response> getAllByStudent(@NonNull Pageable pageable);
}
