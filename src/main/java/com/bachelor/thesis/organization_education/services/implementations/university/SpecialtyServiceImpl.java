package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.Specialty;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.repositories.university.SpecialtyRepository;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.general.university.SpecialtyRequest;
import com.bachelor.thesis.organization_education.requests.find.university.SpecialtyFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.SpecialtyService;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityGroupService;
import com.bachelor.thesis.organization_education.services.implementations.crud.NameEntityServiceAbstract;

import java.util.List;
import java.util.UUID;

@Service
public class SpecialtyServiceImpl extends NameEntityServiceAbstract<Specialty, SpecialtyRepository> implements SpecialtyService {
    @Autowired
    public SpecialtyServiceImpl(SpecialtyRepository repository, ApplicationContext context) {
        super(repository, "Specialties", context);
    }

    @Override
    protected Specialty createEntity(InsertRequest request) {
        var specialtyRequest = (SpecialtyRequest) request;
        var builder = Specialty.builder();
        return super.initEntity(builder, request)
                .number(specialtyRequest.getNumber())
                .build();
    }

    @Override
    public Specialty updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws NotFindEntityInDataBaseException {
        validateDuplicate(id, request.getFindRequest());
        return super.updateValue(id, request);
    }

    @Override
    protected void updateEntity(Specialty entity, UpdateRequest request) {
        var updateRequest = (SpecialtyRequest) request;
        updateIfPresent(updateRequest::getNumber, entity::setNumber);
        super.updateEntity(entity, request);
    }

    @Override
    protected List<Specialty> findAllEntitiesByRequest(@NonNull FindRequest request) {
        var specialtyRequest = (SpecialtyFindRequest) request;

        return repository.findAllByEnNameOrUaNameOrNumber(
                specialtyRequest.getEnName(),
                specialtyRequest.getUaName(),
                specialtyRequest.getNumber()
        );
    }

    @Override
    protected void selectedForDeactivateChild(UUID id) {
        var entity = repository.findById(id);
        entity.ifPresent(e ->
                deactivatedChild(e.getGroups(), UniversityGroupService.class)
        );
    }
}
