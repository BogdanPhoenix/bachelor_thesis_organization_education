package com.bachelor.thesis.organization_education.services.implementations.university;

import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.enums.AccreditationLevel;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.repositories.university.UniversityRepository;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import com.bachelor.thesis.organization_education.requests.find.university.UniversityFindRequest;
import com.bachelor.thesis.organization_education.requests.insert.university.UniversityInsertRequest;
import com.bachelor.thesis.organization_education.requests.general.university.UniversityRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.update.university.UniversityUpdateRequest;
import com.bachelor.thesis.organization_education.services.implementations.crud.NameEntityServiceAbstract;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
    public University addResource(@NonNull UniversityInsertRequest request, @NonNull String userId) throws NullPointerException, DuplicateException {
        var universityRequest = UniversityRequest.builder()
                .accreditationLevel(request.getAccreditationLevel())
                .adminId(UUID.fromString(userId))
                .enName(request.getEnName())
                .uaName(request.getUaName())
                .build();

        return super.addValue(universityRequest);
    }

    @Override
    protected void updateEntity(University entity, UpdateRequest request) {
        super.updateEntity(entity, request);

        var universityRequest = (UniversityUpdateRequest) request;

        if(universityRequest.getAccreditationLevel() == AccreditationLevel.EMPTY) {
            entity.setAccreditationLevel(universityRequest.getAccreditationLevel());
        }
    }

    @Override
    protected Optional<University> findEntity(@NonNull FindRequest request) {
        var universityRequest = (UniversityFindRequest) request;

        return repository.findByEnNameOrUaNameOrAdminId(
                universityRequest.getEnName(),
                universityRequest.getUaName(),
                universityRequest.getAdminId()
        );
    }
}
