package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bachelor.thesis.organization_education.services.interfaces.crud.*;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;

/**
 * Service interface for managing faculties, extending basic CRUD operations.
 */
public interface FacultyService extends CreateService, ReadService, UpdateService, DeleteService, StateManagementService {
    /**
     * Returns all faculty entities created by the university administrator. The administrator ID is taken from the authorized user...
     *
     * @param pageable page settings
     * @return a set of entities.
     */
    Page<Response> getAllByUniversityAdmin(@NonNull Pageable pageable);
}
