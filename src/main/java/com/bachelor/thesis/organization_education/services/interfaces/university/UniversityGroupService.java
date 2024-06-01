package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bachelor.thesis.organization_education.services.interfaces.crud.*;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;

/**
 * Service for managing resource groups.
 * Implements CRUD operations and additional functionality.
 */
public interface UniversityGroupService extends CreateService, ReadService, UpdateService, DeleteService, StateManagementService {
    /**
     * Returns all group entities created by the university administrator. The administrator ID is taken from the authorized user...
     *
     * @param pageable page settings
     * @return a set of entities.
     */
    Page<Response> getAllByUniversityAdmin(@NonNull Pageable pageable);
}
