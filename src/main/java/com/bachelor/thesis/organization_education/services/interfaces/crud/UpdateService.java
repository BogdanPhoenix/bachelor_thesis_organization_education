package com.bachelor.thesis.organization_education.services.interfaces.crud;

import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.UUID;

/**
 * This interface defines methods for updating entities in the database.
 */
@Transactional
public interface UpdateService {
    /**
     * Updates the attributes of the selected entity.
     *
     * @param id unique identifier of the entity.
     * @param request with new data.
     * @return the updated entity.
     * @throws NotFindEntityInDataBaseException if the entity could not be found.
     */
    BaseTableInfo updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws NotFindEntityInDataBaseException;
}
