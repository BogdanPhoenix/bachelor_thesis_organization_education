package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import com.bachelor.thesis.organization_education.services.interfaces.crud.CrudService;
import com.bachelor.thesis.organization_education.responces.university.MagazineResponse;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.List;
import java.util.UUID;

/**
 * An interface that implements interaction between groups and disciplines.
 * Extends the basic CRUD service interface to provide collaborative CRUD operations.
 */
public interface GroupDisciplineService extends CrudService {
    /**
     Retrieves a magazine by its unique identifier.
     @param id The unique identifier of the magazine.
     @return The magazine response containing details of the magazine.
     @throws NotFindEntityInDataBaseException If the magazine with the given ID is not found in the database.
     */
    MagazineResponse getMagazine(@NonNull UUID id) throws NotFindEntityInDataBaseException;

    /**
     Retrieves all magazines with pagination support.
     @param pageable The pagination information.
     @return A list of magazine responses with pagination applied.
     */
    List<MagazineResponse> getAllMagazine(Pageable pageable);
}
