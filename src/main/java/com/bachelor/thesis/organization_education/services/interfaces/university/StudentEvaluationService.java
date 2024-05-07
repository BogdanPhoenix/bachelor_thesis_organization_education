package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bachelor.thesis.organization_education.dto.StudentEvaluation;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.UUID;

/**
 * Interface defining a service for managing student evaluations.
 * Extends the common CRUD operations service.
 */
public interface StudentEvaluationService extends CrudService {
    /**
     * Retrieves a page of evaluations associated with a specific recording.
     *
     * @param recordingId The UUID of the recording.
     * @param pageable    The pagination information.
     * @return A page of StudentEvaluation objects.
     * @throws NotFindEntityInDataBaseException if the recording entity is not found in the database.
     */
    Page<StudentEvaluation> getEvaluationsForRecording(@NonNull UUID recordingId, @NonNull Pageable pageable) throws NotFindEntityInDataBaseException;
}
