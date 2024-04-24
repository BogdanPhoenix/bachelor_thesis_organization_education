package com.bachelor.thesis.organization_education.services.interfaces.crud;

import lombok.NonNull;
import jakarta.transaction.Transactional;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
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
    BaseTableInfo addValue(@NonNull Request request) throws DuplicateException, NullPointerException;

    /**
     * Makes the entity active for further interaction with the program.
     *
     * @param id unique identifier of the entity.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void activate(@NonNull Long id) throws NotFindEntityInDataBaseException;

    /**
     * Makes the entity inactive, which does not allow the program to interact with it.
     *
     * @param id unique identifier of the entity.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void deactivate(@NonNull Long id) throws NotFindEntityInDataBaseException ;

    /**
     * Searches for an entity in the database by the specified query.
     *
     * @param request request to search for an entity.
     * @return the found subject.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    BaseTableInfo getValue(@NonNull FindRequest request) throws NotFindEntityInDataBaseException;

    /**
     * Searches for an entity in the database by the specified query.
     *
     * @param id unique identifier of the entity.
     * @return the found subject.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    BaseTableInfo getValue(@NonNull Long id) throws NotFindEntityInDataBaseException;

    /**
     * Returns the set of all table entities.
     *
     * @return a set of entities.
     */
    Set<BaseTableInfo> getAll();

    /**
     * Updates the attributes of the selected entity.
     *
     * @param id unique identifier of the entity.
     * @param request with new data.
     * @return the updated entity.
     * @throws DuplicateException if the table already contains the data that is passed in the query.
     * @throws NotFindEntityInDataBaseException if the entity could not be found.
     */
    BaseTableInfo updateValue(@NonNull Long id, @NonNull UpdateRequest request) throws DuplicateException, NotFindEntityInDataBaseException;

    /**
     * Searches for an entity in the database by the specified query and deletes it completely.
     *
     * @param id unique identifier of the entity.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void deleteValue(@NonNull Long id) throws NotFindEntityInDataBaseException;
}
