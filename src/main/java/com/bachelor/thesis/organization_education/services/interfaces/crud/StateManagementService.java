package com.bachelor.thesis.organization_education.services.interfaces.crud;

import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This interface defines methods for managing the state of entities.
 */
@Transactional
public interface StateManagementService {
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
}
