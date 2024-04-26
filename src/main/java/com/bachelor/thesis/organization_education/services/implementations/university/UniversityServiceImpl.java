package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.repositories.university.UniversityRepository;
import com.bachelor.thesis.organization_education.requests.general.university.UniversityRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.FacultyService;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import com.bachelor.thesis.organization_education.services.implementations.crud.NameEntityServiceAbstract;

import java.util.UUID;

@Service
public class UniversityServiceImpl extends NameEntityServiceAbstract<University, UniversityRepository> implements UniversityService {
    @Autowired
    protected UniversityServiceImpl(UniversityRepository repository, ApplicationContext context) {
        super(repository, "Universities", context);
    }

    @Override
    protected void objectFormation(InsertRequest request) { }

    @Override
    protected University createEntity(InsertRequest request) {
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
    public University addResource(@NonNull UniversityRequest request, @NonNull String userId) throws NullPointerException, DuplicateException {
        request.setAdminId(UUID.fromString(userId));
        return super.addValue(request);
    }

    @Override
    protected void updateEntity(University entity, UpdateRequest request) {
        super.updateEntity(entity, request);

        var universityRequest = (UniversityRequest) request;

        if(!universityRequest.accreditationLevelIsEmpty()) {
            entity.setAccreditationLevel(universityRequest.getAccreditationLevel());
        }
    }

    @Override
    public void deactivateUserEntity(@NonNull String userId) {
        var uuid = UUID.fromString(userId);
        var university = repository.findByAdminId(uuid);
        university.ifPresent(entity ->
                deactivate(entity.getId())
        );
    }

    @Override
    public University findByUser(@NonNull String adminId) throws NotFindEntityInDataBaseException {
        var uuid = UUID.fromString(adminId);
        return repository.findByAdminId(uuid)
                .orElseThrow(() -> new NotFindEntityInDataBaseException("Unable to find a university where the user with the specified ID \"" + adminId + "\" is an administrator."));
    }

    @Override
    protected void selectedForDeactivateChild(Long id) {
        var entity = findEntityById(id);
        deactivatedChild(entity.getFaculties(), FacultyService.class);
    }
}
