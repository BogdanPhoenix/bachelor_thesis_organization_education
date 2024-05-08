package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.ClassRecording;
import com.bachelor.thesis.organization_education.dto.StudentEvaluation;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;
import com.bachelor.thesis.organization_education.repositories.university.StudentEvaluationRepository;
import com.bachelor.thesis.organization_education.requests.general.university.StudentEvaluationRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.ClassRecordingService;
import com.bachelor.thesis.organization_education.requests.find.university.StudentEvaluationFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.StudentEvaluationService;

import java.util.List;
import java.util.UUID;

@Service
public class StudentEvaluationServiceImpl extends CrudServiceAbstract<StudentEvaluation, StudentEvaluationRepository> implements StudentEvaluationService {
    @Autowired
    public StudentEvaluationServiceImpl(StudentEvaluationRepository repository, ApplicationContext context) {
        super(repository, "Students evaluations", context);
    }

    @Override
    protected StudentEvaluation createEntity(InsertRequest request) {
        var insertRequest = (StudentEvaluationRequest) request;
        return StudentEvaluation.builder()
                .student(insertRequest.getStudent())
                .classRecording(insertRequest.getClassRecording())
                .evaluation(insertRequest.getEvaluation())
                .present(insertRequest.isPresent())
                .build();
    }

    @Override
    protected List<StudentEvaluation> findAllEntitiesByRequest(@NonNull FindRequest request) {
        var findRequest = (StudentEvaluationFindRequest) request;
        return repository.findByStudentAndClassRecording(
                findRequest.getStudent(),
                findRequest.getClassRecording()
        );
    }

    @Override
    protected void updateEntity(StudentEvaluation entity, UpdateRequest request) {
        var updateRequest = (StudentEvaluationRequest) request;

        updateIfPresent(updateRequest::getEvaluation, entity::setEvaluation);
        updateIfPresent(updateRequest::isPresent, entity::setPresent);

        if(!updateRequest.isPresent()) {
            entity.setEvaluation((short) 0);
        }
    }

    @Override
    public Page<StudentEvaluation> getEvaluationsForRecording(@NonNull UUID recordingId, @NonNull Pageable pageable) throws NotFindEntityInDataBaseException {
        var classRecordingService = super.getBeanByClass(ClassRecordingService.class);
        var classRecording = (ClassRecording) classRecordingService.getValue(recordingId);
        return repository.findAllByClassRecording(classRecording, pageable);
    }
}
