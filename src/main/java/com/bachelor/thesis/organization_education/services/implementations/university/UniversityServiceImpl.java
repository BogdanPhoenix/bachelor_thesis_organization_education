package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.repositories.university.UniversityRepository;
import com.bachelor.thesis.organization_education.requests.general.university.UniversityRequest;
import com.bachelor.thesis.organization_education.requests.find.university.UniversityFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import com.bachelor.thesis.organization_education.services.implementations.crud.NameEntityServiceAbstract;

import java.util.List;
import java.util.UUID;

@Service
public class UniversityServiceImpl extends NameEntityServiceAbstract<University, UniversityRepository> implements UniversityService {
    @Autowired
    protected UniversityServiceImpl(UniversityRepository repository, ApplicationContext context) {
        super(repository, "Universities", context);
    }

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
    public University addValue(@NonNull InsertRequest request) throws NullPointerException, DuplicateException {
        setAdminId((UniversityRequest) request);
        return super.addValue(request);
    }

    @Override
    public University updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws NotFindEntityInDataBaseException {
        setAdminId((UniversityRequest) request);
        validateDuplicate(id, request.getFindRequest());

        return super.updateValue(id, request);
    }

    private void setAdminId(UniversityRequest request) {
        var uuid = super.getAuthenticationUUID();
        request.setAdminId(uuid);
    }

    @Override
    protected void updateEntity(University entity, UpdateRequest request) {
        var updateRequest = (UniversityRequest) request;
        updateIfPresent(updateRequest::getAccreditationLevel, entity::setAccreditationLevel);
        super.updateEntity(entity, request);
    }

    @Override
    protected List<University> findAllEntitiesByRequest(@NonNull FindRequest request) {
        var findRequest = (UniversityFindRequest) request;
        return repository.findAllByAdminIdOrEnNameOrUaName(
                findRequest.getAdminId(),
                findRequest.getEnName(),
                findRequest.getUaName()
        );
    }

    @Override
    public void deactivateUserEntity(@NonNull UUID userId) {
        var university = repository.findByAdminId(userId);
        university.ifPresent(super::deactivateEntity);
    }

    @Override
    public void deleteUserEntity(UUID userId) {
        var university = repository.findByAdminId(userId);
        university.ifPresent(entity ->
                deleteValue(entity.getId())
        );
    }

    @Override
    public University findByUser(@NonNull UUID adminId) throws NotFindEntityInDataBaseException {
        return repository.findByAdminId(adminId)
                .orElseThrow(() -> new NotFindEntityInDataBaseException("Unable to find a university where the user with the specified ID \"" + adminId + "\" is an administrator."));
    }

    @Override
    protected void selectedForDeactivateChild(University entity) {
        deactivatedChild(entity.getFaculties(), FacultyServiceImpl.class);
        deactivatedChild(entity.getAudiences(), AudienceServiceImpl.class);
    }
}
