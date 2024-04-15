package com.bachelor.thesis.organization_education.services.interfaces;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.update.UpdateData;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import lombok.NonNull;
import jakarta.transaction.Transactional;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
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
     * @param request request to search for an entity, must not be {@literal null}.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void enable(@NonNull FindRequest request) throws NotFindEntityInDataBaseException;

    /**
     * Makes the entity inactive, which does not allow the program to interact with it.
     *
     * @param request request to search for an entity, must not be {@literal null}.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void disable(@NonNull FindRequest request) throws NotFindEntityInDataBaseException ;

    /**
     * Searches for an entity in the database by the specified query.
     *
     * @param request request to search for an entity.
     * @return the found subject.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    BaseTableInfo getValue(@NonNull FindRequest request) throws NotFindEntityInDataBaseException;

    /**
     * Returns the set of all table entities.
     *
     * @return a set of entities.
     */
    Set<BaseTableInfo> getAll();

    /**
     * Searches for an entity in the database according to the specified query and updates the attribute values with new ones.
     *
     * @param request request to search for an entity.
     * @return the updated content of the entity.
     * @throws DuplicateException if the table contains an entity value that is passed in the query.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    BaseTableInfo updateValue(@NonNull UpdateData<? extends UpdateRequest> request) throws DuplicateException, NotFindEntityInDataBaseException;

    /**
     * Searches for an entity in the database by the specified query and deletes it completely.
     *
     * @param request request to search for an entity.
     * @throws NotFindEntityInDataBaseException if the entity could not be found in the table by the specified query.
     */
    void deleteValue(@NonNull FindRequest request) throws NotFindEntityInDataBaseException;
}
