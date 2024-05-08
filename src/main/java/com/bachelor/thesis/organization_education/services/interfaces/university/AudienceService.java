package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.requests.general.university.AudienceRequest;

import java.util.List;
import java.util.Collection;

/**
 * Service responsible for operations on the audience.
 * Extends the main CRUD (create, read, update, delete) service.
 */
public interface AudienceService extends CrudService {
    /**
     * Creates objects based on specified queries from the collection for the university to which the administrator belongs.
     *
     * @param requests collection of insert queries to process.
     * @param adminId identifier of the university administrator.
     * @return the list of responses after adding values.
     * @throws DuplicateException if the table contains one of the entity values that is passed in the collection.
     * @throws NullPointerException if null was passed to the request.
     */
    List<Response> addValue(@NonNull Collection<AudienceRequest> requests, @NonNull String adminId) throws NullPointerException, DuplicateException;
    /**
     * Adds an audience resource using the provided query.
     *
     * @param request request to add an audience resource.
     * @param adminId identifier of the university administrator who is adding the resource.
     * @return information about the base table representing the new resource.
     * @throws NullPointerException if one or both of the passed parameters are null.
     * @throws DuplicateException if an attempt is made to add a resource that already exists.
     */
    BaseTableInfo addResource(@NonNull AudienceRequest request, @NonNull String adminId) throws NullPointerException, DuplicateException;
}
