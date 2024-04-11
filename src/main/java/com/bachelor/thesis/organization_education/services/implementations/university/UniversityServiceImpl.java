package com.bachelor.thesis.organization_education.services.implementations.university;

import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.enums.AccreditationLevel;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.repositories.university.UniversityRepository;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import com.bachelor.thesis.organization_education.requests.university.UniversityInsertRequest;
import com.bachelor.thesis.organization_education.requests.university.UniversityRequest;
import com.bachelor.thesis.organization_education.responces.university.UniversityResponse;
import com.bachelor.thesis.organization_education.services.implementations.NameEntityServiceAbstract;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UniversityServiceImpl extends NameEntityServiceAbstract<University, UniversityRepository> implements UniversityService {
    @Autowired
    protected UniversityServiceImpl(UniversityRepository repository) {
        super(repository, "Accreditation levels");
    }

    @Override
    protected University createEntity(@NonNull Request request) {
        var universityRequest = (UniversityRequest) request;
        var builder = University.builder();
        return super.initEntity(builder, request)
                .accreditationLevel(universityRequest.getAccreditationLevel())
                .adminId(universityRequest.getAdminId())
                .enName(universityRequest.getEnName())
                .uaName(universityRequest.getUaName())
                .build();
    }

    @Override
    public UniversityResponse addResource(@NonNull UniversityInsertRequest request, @NonNull String userId) throws NullPointerException, DuplicateException {
        var universityRequest = UniversityRequest.builder()
                .accreditationLevel(request.getAccreditationLevel())
                .adminId(UUID.fromString(userId))
                .enName(request.getEnName())
                .uaName(request.getUaName())
                .build();

        return (UniversityResponse) super.addValue(universityRequest);
    }

    @Override
    protected void updateEntity(University entity, Request request) {
        super.updateEntity(entity, request);

        var universityRequest = (UniversityRequest) request;

        if(universityRequest.getAccreditationLevel() == AccreditationLevel.EMPTY) {
            entity.setAccreditationLevel(universityRequest.getAccreditationLevel());
        }
    }
}
