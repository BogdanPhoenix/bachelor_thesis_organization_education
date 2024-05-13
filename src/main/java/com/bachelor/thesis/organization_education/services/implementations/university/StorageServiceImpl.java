package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.Storage;
import com.bachelor.thesis.organization_education.dto.ClassRecording;
import com.bachelor.thesis.organization_education.exceptions.FileException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.responces.university.DownloadFileResponse;
import com.bachelor.thesis.organization_education.repositories.university.StorageRepository;
import com.bachelor.thesis.organization_education.requests.general.university.StorageRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.find.university.StorageFindRequest;
import com.bachelor.thesis.organization_education.services.implementations.tools.StorageTools;
import com.bachelor.thesis.organization_education.services.interfaces.university.StorageService;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;
import com.bachelor.thesis.organization_education.services.interfaces.university.ClassRecordingService;

import java.util.List;
import java.util.UUID;

@Service
public class StorageServiceImpl extends CrudServiceAbstract<Storage, StorageRepository> implements StorageService {
    private static final int MAX_NAME_SIZE = 255;

    @Autowired
    public StorageServiceImpl(StorageRepository repository, ApplicationContext context) {
        super(repository, "Files", context);
    }

    @Override
    protected Storage createEntity(InsertRequest request) {
        var insertRequest = (StorageRequest) request;
        return Storage.builder()
                .userId(insertRequest.getUserId())
                .classRecording(insertRequest.getClassRecording())
                .fileName(insertRequest.getFileName())
                .fileType(insertRequest.getFileType())
                .fileData(insertRequest.getFileData())
                .build();
    }

    @Override
    protected List<Storage> findAllEntitiesByRequest(@NonNull FindRequest request) {
        var findRequest = (StorageFindRequest) request;
        return repository.findAllByUserIdAndClassRecordingAndFileName(
                findRequest.getUserId(),
                findRequest.getClassRecording(),
                findRequest.getFileName()
        );
    }

    @Override
    protected void updateEntity(Storage entity, UpdateRequest request) {
        //Updating attributes for entities is not provided.
    }

    @Override
    public Storage uploadStorage( @NonNull UUID classRecordingId, @NonNull MultipartFile file) throws FileException {
        try {
            var userId = super.getAuthenticationUUID();
            var fileName = file.getOriginalFilename();
            var fileType = file.getContentType();
            var fileData = StorageTools.compressFile(file.getBytes());
            var classRecording = (ClassRecording) super.getBeanByClass(ClassRecordingService.class)
                    .getValue(classRecordingId);

            assert fileName != null;
            if(fileName.length() > MAX_NAME_SIZE) {
                var message = String.format("The permissible length of the file name should be no more than %d characters.", MAX_NAME_SIZE);
                throw new FileException(message);
            }

            var request = StorageRequest.builder()
                    .userId(userId)
                    .classRecording(classRecording)
                    .fileName(fileName)
                    .fileType(fileType)
                    .fileData(fileData)
                    .build();

            return super.addValue(request);
        } catch (Exception e) {
            throw new FileException(e.getMessage());
        }
    }

    @Override
    public Page<Response> getStorages(@NonNull UUID classRecordingId, Pageable pageable) {
        var userId = super.getAuthenticationUUID();
        var classRecording = (ClassRecording) super.getBeanByClass(ClassRecordingService.class)
                .getValue(classRecordingId);

        return repository.findAllByUserIdAndClassRecording(userId, classRecording, pageable)
                .map(BaseTableInfo::getResponse);
    }

    @Override
    public DownloadFileResponse downloadStorage(@NonNull UUID id) throws NotFindEntityInDataBaseException, FileException {
        var entity = findValueById(id);
        var file = StorageTools.decompressFile(entity.getFileData());

        return DownloadFileResponse.builder()
                .fileType(entity.getFileType())
                .fileData(file)
                .build();
    }

    @Override
    public void deactivateByUserId(@NonNull UUID userId) {
        repository.findAllByUserId(userId)
                .forEach(super::deactivateEntity);
    }

    @Override
    public void deleteByUserId(@NonNull UUID userId) {
        repository.findAllByUserId(userId)
                .forEach(storage -> deleteValue(storage.getId()));
    }
}
