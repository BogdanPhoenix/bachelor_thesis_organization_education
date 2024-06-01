package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bachelor.thesis.organization_education.services.interfaces.crud.*;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;

/**
 * Interface for managing class recordings, extending basic CRUD operations.
 */
public interface ClassRecordingService extends CreateService, ReadService, UpdateService, DeleteService, StateManagementService {
    /**
     * Returns all class entities that the teacher has created. The teacher ID is taken from the authorized user...
     *
     * @param pageable page settings
     * @return a set of entities.
     */
    Page<Response> getAllByLecturer(@NonNull Pageable pageable);
}
