package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import com.bachelor.thesis.organization_education.dto.Specialty;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.repositories.university.SpecialtyRepository;
import com.bachelor.thesis.organization_education.requests.general.university.SpecialtyRequest;
import com.bachelor.thesis.organization_education.requests.find.university.SpecialtyFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.SpecialtyService;
import com.bachelor.thesis.organization_education.requests.insert.university.SpecialtyInsertRequest;
import com.bachelor.thesis.organization_education.requests.update.university.SpecialtyUpdateRequest;
import com.bachelor.thesis.organization_education.services.implementations.crud.NameEntityServiceAbstract;

import java.util.Optional;

@Service
public class SpecialtyServiceImpl extends NameEntityServiceAbstract<Specialty, SpecialtyRepository> implements SpecialtyService {
    protected SpecialtyServiceImpl(SpecialtyRepository repository) {
        super(repository, "Specialties");
    }

    @Override
    protected Specialty createEntity(Request request) {
        var specialtyRequest = (SpecialtyRequest) request;
        var builder = Specialty.builder();
        return super.initEntity(builder, request)
                .number(specialtyRequest.getNumber())
                .build();
    }

    @Override
    public Specialty addResource(@NonNull SpecialtyInsertRequest request) {
        var specialtyRequest = SpecialtyRequest.builder()
                .enName(request.getEnName())
                .uaName(request.getUaName())
                .number(request.getNumber())
                .build();

        return addValue(specialtyRequest);
    }

    @Override
    protected void updateEntity(Specialty entity, UpdateRequest request) {
        var specialtyRequest = (SpecialtyUpdateRequest) request;

        if(!specialtyRequest.numberIsEmpty()) {
            entity.setNumber(specialtyRequest.getNumber());
        }

        super.updateEntity(entity, request);
    }

    @Override
    protected Optional<Specialty> findEntityByRequest(@NonNull FindRequest request) {
        var specialtyRequest = (SpecialtyFindRequest) request;

        return repository.findByEnNameOrUaNameOrNumber(
                specialtyRequest.getEnName(),
                specialtyRequest.getUaName(),
                specialtyRequest.getNumber()
        );
    }

    @Override
    protected void selectedForDeactivateChild(Long id) { }
}
