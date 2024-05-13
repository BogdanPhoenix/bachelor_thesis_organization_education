package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.AcademicDiscipline;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.LecturerService;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.repositories.university.AcademicDisciplineRepository;
import com.bachelor.thesis.organization_education.requests.general.university.AcademicDisciplineRequest;
import com.bachelor.thesis.organization_education.services.implementations.crud.NameEntityServiceAbstract;
import com.bachelor.thesis.organization_education.services.interfaces.university.AcademicDisciplineService;

import java.util.UUID;

@Service
public class AcademicDisciplineServiceImpl extends NameEntityServiceAbstract<AcademicDiscipline, AcademicDisciplineRepository> implements AcademicDisciplineService {
    @Autowired
    public AcademicDisciplineServiceImpl(AcademicDisciplineRepository repository, ApplicationContext context) {
        super(repository, "Academic disciplines", context);
    }

    @Override
    protected AcademicDiscipline createEntity(InsertRequest request) {
        var disciplineRequest = (AcademicDisciplineRequest) request;
        var builder = AcademicDiscipline.builder();
        return super.initEntity(builder, request)
                .amountCredits(disciplineRequest.getAmountCredits())
                .build();
    }

    @Override
    public AcademicDiscipline updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws DuplicateException, NotFindEntityInDataBaseException {
        validateDuplicate(id, request.getFindRequest());
        return super.updateValue(id, request);
    }

    @Override
    protected void updateEntity(AcademicDiscipline entity, UpdateRequest request) {
        var updateRequest = (AcademicDisciplineRequest) request;
        updateIfPresent(updateRequest::getAmountCredits, entity::setAmountCredits);
        super.updateEntity(entity, request);
    }

    @Override
    protected void selectedForDeactivateChild(AcademicDiscipline entity) {
        deactivatedChild(entity.getGroupsDisciplines(), GroupDisciplineServiceImpl.class);
    }

    @Override
    public void addLecturer(@NonNull UUID disciplineId, @NonNull UUID lecturerId) throws NotFindEntityInDataBaseException {
        getBeanByClass(LecturerService.class)
                .addDiscipline(lecturerId, disciplineId);
    }

    @Override
    public void disconnectLecturer(@NonNull UUID disciplineId, @NonNull UUID lecturerId) throws NotFindEntityInDataBaseException {
        getBeanByClass(LecturerService.class)
                .disconnectDiscipline(lecturerId, disciplineId);
    }
}
