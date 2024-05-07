package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.ClassRecording;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.repositories.university.ClassRecordingRepository;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;
import com.bachelor.thesis.organization_education.requests.general.university.ClassRecordingRequest;
import com.bachelor.thesis.organization_education.requests.find.university.ClassRecordingFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.ClassRecordingService;
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
    protected List<ClassRecording> findAllEntitiesByRequest(@NonNull FindRequest request) {
        var findRequest = (ClassRecordingFindRequest) request;
        return repository.findAllByMagazineAndClassTopic(
                findRequest.getMagazine(),
                findRequest.getClassTopic()
        );
    }

    @Override
    protected void updateEntity(ClassRecording entity, UpdateRequest request) {
        var updateRequest = (ClassRecordingRequest) request;

        if(!updateRequest.classTopicIsEmpty()) {
            entity.setClassTopic(updateRequest.getClassTopic());
        }
        if(!updateRequest.descriptionIsEmpty()) {
            entity.setDescription(updateRequest.getDescription());
        }
    }

    @Override
    protected void selectedForDeactivateChild(UUID id) {
        var entity = repository.findById(id);
        entity.ifPresent(e -> deactivatedChild(e.getStudentEvaluations(), StudentEvaluationService.class));
    }
}
