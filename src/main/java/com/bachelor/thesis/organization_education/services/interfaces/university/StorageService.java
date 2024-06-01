package com.bachelor.thesis.organization_education.services.interfaces.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.bachelor.thesis.organization_education.dto.Storage;
import com.bachelor.thesis.organization_education.exceptions.FileException;
import com.bachelor.thesis.organization_education.services.interfaces.crud.*;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.responces.university.DownloadFileResponse;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;

import java.util.UUID;

/**
 * Interface representing a service for managing storage operations.
 * Implementations should handle CRUD operations for storage entities.
 * This interface extends CrudService to inherit basic CRUD functionalities.
 */
public interface StorageService extends CreateService, ReadService, UpdateService, DeleteService, StateManagementService {
    /**
     * Uploads a file to storage associated with a user and a class recording.
     *
     * @param classRecordingId the unique identifier of the class recording.
     * @param file the file to be uploaded.
     * @return the storage entity representing the uploaded file.
     * @throws FileException if there is an error uploading the file.
     * @throws NullPointerException if null was passed to the request.
     * @throws DuplicateException if the table contains one of the entity values that is passed in the collection.
     */
    Storage uploadStorage(@NonNull UUID classRecordingId, @NonNull MultipartFile file) throws FileException, NullPointerException, DuplicateException;

    /**
     * Retrieves a page of storage entities associated with a user and a class recording.
     *
     * @param classRecordingId the unique identifier of the class recording.
     * @param pageable the pagination information.
     * @return a page of storage entities.
     */
    Page<Response> getStorages(@NonNull UUID classRecordingId, Pageable pageable);

    /**
     * Downloads a storage entity by its unique identifier.
     *
     * @param id the unique identifier of the storage entity.
     * @return the download file response.
     * @throws NotFindEntityInDataBaseException if the entity is not found in the database.
     * @throws FileException if an error occurs during file decoding.
     */
    DownloadFileResponse downloadStorage(@NonNull UUID id) throws NotFindEntityInDataBaseException, FileException;

    /**
     * Deactivates storage entities associated with a user.
     *
     * @param userId the unique identifier of the user.
     */
    void deactivateByUserId(@NonNull UUID userId);

    /**
     * Deletes storage entities associated with a user.
     *
     * @param userId the unique identifier of the user.
     */
    void deleteByUserId(@NonNull UUID userId);
}
