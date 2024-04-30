package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.repositories.university.UniversityRepository;
import com.bachelor.thesis.organization_education.requests.general.university.UniversityRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.FacultyService;
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
        var uuid = UUID.fromString(userId);
        request.setAdminId(uuid);
        return super.addValue(request);
    }

    @Override
    public BaseTableInfo updateValue(@NonNull String adminId, @NonNull UUID entityId, @NonNull UpdateRequest request) throws DuplicateException, NotFindEntityInDataBaseException {
        var updateRequest = (UniversityRequest) request;
        var uuid = UUID.fromString(adminId);
        updateRequest.setAdminId(uuid);
        return super.updateValue(entityId, updateRequest);
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
        university.ifPresent(entity ->
                deactivate(entity.getId())
        );
    }

    @Override
    public University findByUser(@NonNull UUID adminId) throws NotFindEntityInDataBaseException {
        return repository.findByAdminId(adminId)
                .orElseThrow(() -> new NotFindEntityInDataBaseException("Unable to find a university where the user with the specified ID \"" + adminId + "\" is an administrator."));
    }

    @Override
    protected void selectedForDeactivateChild(UUID id) {
        var entity = findEntityById(id);
        deactivatedChild(entity.getFaculties(), FacultyService.class);
    }
}
