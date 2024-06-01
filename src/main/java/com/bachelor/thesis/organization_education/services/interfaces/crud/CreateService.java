package com.bachelor.thesis.organization_education.services.interfaces.crud;

import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;

import java.util.List;
import java.util.Collection;

/**
 * This interface defines methods for creating entities in the database.
 */
@Transactional
public interface CreateService {
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
}
