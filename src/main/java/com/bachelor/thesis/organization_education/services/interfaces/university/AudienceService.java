package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bachelor.thesis.organization_education.services.interfaces.crud.*;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;

/**
 * Service responsible for operations on the audience.
 * Extends the main CRUD (create, read, update, delete) service.
 */
public interface AudienceService extends CreateService, ReadService, UpdateService, DeleteService, StateManagementService {
    /**
     * Returns all audience entities created by the university administrator. The administrator ID is taken from the authorized user...
     *
     * @param pageable page settings
     * @return a set of entities.
     */
    Page<Response> getAllByUniversityAdmin(@NonNull Pageable pageable);
}
