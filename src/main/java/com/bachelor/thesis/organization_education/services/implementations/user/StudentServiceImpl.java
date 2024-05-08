package com.bachelor.thesis.organization_education.services.implementations.user;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import com.bachelor.thesis.organization_education.dto.Student;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.find.user.UserFindRequest;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.repositories.user.StudentRepository;
import com.bachelor.thesis.organization_education.requests.general.user.StudentRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.StudentService;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;
import com.bachelor.thesis.organization_education.requests.insert.user.RegistrationStudentRequest;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;
import com.bachelor.thesis.organization_education.services.interfaces.university.StudentEvaluationService;

import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl extends CrudServiceAbstract<Student, StudentRepository> implements StudentService {
    @Autowired
    public StudentServiceImpl(StudentRepository repository, ApplicationContext context) {
        super(repository, "Students", context);
    }

    @Override
    protected Student createEntity(InsertRequest request) {
        var insertRequest = (StudentRequest) request;
        return Student.builder()
                .id(insertRequest.getUserId())
                .group(insertRequest.getGroup())
                .build();
    }

    @Override
    public BaseTableInfo registration(@NonNull RegistrationRequest request, @NonNull UUID userId) throws DuplicateException {
        var studentRegistrationRequest = (RegistrationStudentRequest) request;
        var studentRequest = StudentRequest.builder()
                .userId(userId)
                .group(studentRegistrationRequest.getGroup())
                .build();

        return super.addValue(studentRequest);
    }

    @Override
    protected List<Student> findAllEntitiesByRequest(@NonNull FindRequest request) {
        var findRequest = (UserFindRequest) request;
        return repository.findAllById(findRequest.getUserId());
    }

    @Override
    protected void updateEntity(Student entity, UpdateRequest request) {
        var updateRequest = (StudentRequest) request;
        updateIfPresent(updateRequest::getGroup, entity::setGroup);
    }

    @Override
    protected void selectedForDeactivateChild(UUID id) {
        var entity = repository.findById(id);
        entity.ifPresent(e -> deactivatedChild(e.getEvaluations(), StudentEvaluationService.class));
    }
}
