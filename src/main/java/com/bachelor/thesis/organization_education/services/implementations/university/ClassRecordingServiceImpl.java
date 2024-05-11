package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.ClassRecording;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.StorageService;
import com.bachelor.thesis.organization_education.repositories.university.ClassRecordingRepository;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;
import com.bachelor.thesis.organization_education.requests.general.university.ClassRecordingRequest;
import com.bachelor.thesis.organization_education.requests.find.university.ClassRecordingFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.ClassRecordingService;
import com.bachelor.thesis.organization_education.services.interfaces.university.GroupDisciplineService;
import com.bachelor.thesis.organization_education.services.interfaces.university.StudentEvaluationService;

import java.util.List;
import java.util.UUID;

@Service
public class ClassRecordingServiceImpl extends CrudServiceAbstract<ClassRecording, ClassRecordingRepository> implements ClassRecordingService {
    @Autowired
    public ClassRecordingServiceImpl(ClassRecordingRepository repository, ApplicationContext context) {
        super(repository, "Class recordings", context);
    }

    @Override
    protected ClassRecording createEntity(InsertRequest request) {
        var insertRequest = (ClassRecordingRequest) request;
        return ClassRecording.builder()
                .magazine(insertRequest.getMagazine())
                .classTopic(insertRequest.getClassTopic())
                .description(insertRequest.getDescription())
                .build();
    }

    @Override
    public ClassRecording addValue(@NonNull InsertRequest request) throws DuplicateException, NullPointerException {
        var insertRequest = (ClassRecordingRequest) request;
        var uuid = super.getAuthenticationUUID();
        var magazine = (GroupDiscipline) super.getBeanByClass(GroupDisciplineService.class)
                .getValue(insertRequest.getMagazine().getId());

        checkLecturer(magazine.getLecturer(), uuid);
        return super.addValue(request);
    }

    @Override
    protected List<ClassRecording> findAllEntitiesByRequest(@NonNull FindRequest request) {
        var findRequest = (ClassRecordingFindRequest) request;
        return repository.findAllByMagazineAndClassTopic(
                findRequest.getMagazine(),
                findRequest.getClassTopic()
        );
    }

    @Override
    protected boolean checkOwner(ClassRecording entity, UUID userId) {
        return entity.getMagazine()
                .getLecturer()
                .getId()
                .equals(userId);
    }

    @Override
    protected void updateEntity(ClassRecording entity, UpdateRequest request) {
        var updateRequest = (ClassRecordingRequest) request;
        updateIfPresent(updateRequest::getClassTopic, entity::setClassTopic);
        updateIfPresent(updateRequest::getDescription, entity::setDescription);
    }

    @Override
    protected void selectedForDeactivateChild(ClassRecording entity) {
        deactivatedChild(entity.getStudentEvaluations(), StudentEvaluationService.class);
        deactivatedChild(entity.getStorages(), StorageService.class);
    }
}
