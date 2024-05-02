package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.general.university.AcademicYearRequest;

/**
 * This interface defines the contract for managing academic years within the system.
 * It extends the CrudService interface, providing basic CRUD operations.
 */
public interface AcademicYearService extends CrudService {
    /**
     * Adds a new academic year resource based on the provided request and university admin ID.
     *
     * @param request object containing information about the academic year to be added.
     * @param adminId ID of the admin performing the operation.
     * @return a BaseTableInfo object representing the newly added academic year resource.
     * @throws NullPointerException if either the request or adminId is null.
     * @throws DuplicateException   if an academic year with the same details already exists.
     */
    BaseTableInfo addResource(@NonNull AcademicYearRequest request, @NonNull String adminId) throws NullPointerException, DuplicateException;
}
