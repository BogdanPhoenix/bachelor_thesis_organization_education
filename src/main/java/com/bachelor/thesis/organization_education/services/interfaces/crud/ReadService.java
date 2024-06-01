package com.bachelor.thesis.organization_education.services.interfaces.crud;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.UUID;

/**
 * This interface defines methods for reading entities from the database.
 */
@Transactional
public interface ReadService {
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
     * Returns all entities in the table.
     *
     * @param pageable page settings
     * @return a set of entities.
     */
    Page<Response> getAll(Pageable pageable);
}
