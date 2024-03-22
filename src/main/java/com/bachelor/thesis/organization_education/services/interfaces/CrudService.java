package com.bachelor.thesis.organization_education.services.interfaces;

import lombok.NonNull;
import jakarta.transaction.Transactional;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

@Transactional
public interface CrudService {
    /**
     * Creates an object according to the specified request.
     *
     * @param request must not be {@literal null}.
     * @return response for the saved entity; never {@literal null}.
     * @throws DuplicateException if the table contains an entity value that is passed in the query.
     * @throws NullPointerException if null was passed to the request.
     */
    Response addValue(@NonNull Request request) throws DuplicateException, NullPointerException;

    /**
     * Makes the entity active for further interaction with the program.
     * @param request request to search for an entity, must not be {@literal null}.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void enable(@NonNull Request request) throws NotFindEntityInDataBaseException;

    /**
     * Makes the entity inactive, which does not allow the program to interact with it.
     * @param request request to search for an entity, must not be {@literal null}.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void disable(@NonNull Request request) throws NotFindEntityInDataBaseException ;
}
