package com.bachelor.thesis.organization_education.services.interfaces;

import com.bachelor.thesis.organization_education.requests.UpdateRequest;
import lombok.NonNull;
import jakarta.transaction.Transactional;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.Set;

/**
 * This interface represents a CRUD (Create, Read, Update, Delete) service
 * for interacting with entities in the database.
 * It defines methods for adding, enabling, disabling, retrieving,
 * updating, and deleting entities.
 */
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
     *
     * @param request request to search for an entity, must not be {@literal null}.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void enable(@NonNull Request request) throws NotFindEntityInDataBaseException;

    /**
     * Makes the entity inactive, which does not allow the program to interact with it.
     *
     * @param request request to search for an entity, must not be {@literal null}.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void disable(@NonNull Request request) throws NotFindEntityInDataBaseException ;

    /**
     * Searches for an entity in the database by the specified query.
     *
     * @param request request to search for an entity.
     * @return the response of the found entity.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    Response getValue(@NonNull Request request) throws NotFindEntityInDataBaseException;

    /**
     * Returns the set of all table entities.
     *
     * @return a set of entities.
     */
    Set<Response> getAll();

    /**
     * Searches for an entity in the database according to the specified query and updates the attribute values with new ones.
     *
     * @param request request to search for an entity.
     * @return a response of the updated content of the entity.
     * @throws DuplicateException if the table contains an entity value that is passed in the query.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    Response updateValue(@NonNull UpdateRequest<? extends Response, ? extends Request> request) throws DuplicateException, NotFindEntityInDataBaseException;

    /**
     * Searches for an entity in the database by the specified query and deletes it completely.
     *
     * @param request request to search for an entity.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void deleteValue(@NonNull Request request) throws NotFindEntityInDataBaseException;
}
