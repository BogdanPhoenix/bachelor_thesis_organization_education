package com.bachelor.thesis.organization_education.services.interfaces.crud;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.UUID;
import java.util.List;
import java.util.Collection;

/**
 * This interface represents a CRUD (Create, Read, Update, Delete) service
 * for interacting with entities in the database.
 * It defines methods for adding, enabling, disabling, retrieving,
 * updating, and deleting entities.
 */
@Transactional
public interface CrudService {
    /**
     * Creates objects based on the specified queries from the collection.
     *
     * @param requests collection of insert queries to process.
     * @return the list of responses after adding values.
     * @throws DuplicateException if the table contains one of the entity values that is passed in the collection.
     * @throws NullPointerException if null was passed to the request.
     */
    List<Response> addValue(@NonNull Collection<? extends InsertRequest> requests) throws DuplicateException, NullPointerException;

    /**
     * Creates an object according to the specified request.
     *
     * @param request must not be {@literal null}.
     * @return response for the saved entity; never {@literal null}.
     * @throws DuplicateException if the table contains an entity value that is passed in the query.
     * @throws NullPointerException if null was passed to the request.
     */
    BaseTableInfo addValue(@NonNull InsertRequest request) throws DuplicateException, NullPointerException;

    /**
     * Makes the entity active for further interaction with the program.
     *
     * @param id unique identifier of the entity.
     */
    void activate(@NonNull UUID id);

    /**
     * Makes the entity inactive, which does not allow the program to interact with it.
     *
     * @param id unique identifier of the entity.
     */
    void deactivate(@NonNull UUID id);

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
    BaseTableInfo getValue(@NonNull UUID id) throws NotFindEntityInDataBaseException;

    /**
     * Returns the set of all table entities.
     *
     * @param pageable page settings
     * @return a set of entities.
     */
    Page<BaseTableInfo> getAll(Pageable pageable);

    /**
     * Updates the attributes of the selected entity.
     *
     * @param id unique identifier of the entity.
     * @param request with new data.
     * @return the updated entity.
     * @throws NotFindEntityInDataBaseException if the entity could not be found.
     */
    BaseTableInfo updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws NotFindEntityInDataBaseException;

    /**
     * Searches for an entity in the database by the specified query and deletes it completely.
     *
     * @param id unique identifier of the entity.
     */
    void deleteValue(@NonNull UUID id);
}
