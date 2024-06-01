package com.bachelor.thesis.organization_education.services.interfaces.crud;

import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This interface defines methods for deleting entities from the database.
 */
@Transactional
public interface DeleteService {
    /**
     * Searches for an entity in the database by the specified query and deletes it completely.
     *
     * @param id unique identifier of the entity.
     */
    void deleteValue(@NonNull UUID id);
}
