package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.repositories.university.FacultyRepository;
import com.bachelor.thesis.organization_education.requests.general.university.FacultyRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.find.university.FacultyFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.FacultyService;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import com.bachelor.thesis.organization_education.services.implementations.user.LecturerServiceImpl;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;

import java.util.UUID;
import java.util.List;
import java.util.Collection;

@Service
public class FacultyServiceImpl extends CrudServiceAbstract<Faculty, FacultyRepository> implements FacultyService {
    @Autowired
    protected FacultyServiceImpl(FacultyRepository repository, ApplicationContext context) {
        super(repository, "Faculties", context);
    }

    @Override
    protected Faculty createEntity(InsertRequest request) {
        var facultyRequest = (FacultyRequest) request;
        return Faculty.builder()
                .university(facultyRequest.getUniversity())
                .enName(facultyRequest.getEnName())
                .uaName(facultyRequest.getUaName())
                .build();
    }

    @Override
    public List<Response> addValue(@NonNull Collection<FacultyRequest> requests, @NonNull String adminId) throws NullPointerException, DuplicateException {
        var university = getUniversity(adminId);
        requests.forEach(entity -> entity.setUniversity(university));
        return super.addValue(requests);
    }

    @Override
    public Faculty addValue(@NonNull FacultyRequest request, @NonNull String adminId) {
        var university = getUniversity(adminId);
        request.setUniversity(university);
        return super.addValue(request);
    }

    @Override
    public BaseTableInfo updateValue(@NonNull String adminId, @NonNull UUID entityId, @NonNull FacultyRequest request) throws DuplicateException, NotFindEntityInDataBaseException {
        var university = getUniversity(adminId);
        request.setUniversity(university);
        validateDuplicate(entityId, request.getFindRequest());
        return super.updateValue(entityId, request);
    }

    @Override
    public Page<Response> getAllByUniversityAdmin(@NonNull Pageable pageable) {
        var uuid = super.getAuthenticationUUID();
        return repository.findAll(uuid, pageable)
                .map(BaseTableInfo::getResponse);
    }

    @Override
    protected void updateEntity(Faculty entity, UpdateRequest request) {
        var updateRequest = (FacultyRequest) request;

        updateIfPresent(updateRequest::getEnName, entity::setEnName);
        updateIfPresent(updateRequest::getUaName, entity::setUaName);
    }

    private University getUniversity(String adminId) {
        var uuid = UUID.fromString(adminId);
        var universityService = getBeanByClass(UniversityService.class);
        return universityService.findByUser(uuid);
    }

    @Override
    protected List<Faculty> findAllEntitiesByRequest(@NonNull FindRequest request) {
        var facultyRequest = (FacultyFindRequest) request;

        return repository.findAllFaculty(
                facultyRequest.getUniversity(),
                facultyRequest.getEnName(),
                facultyRequest.getUaName()
        );
    }

    @Override
    protected boolean checkOwner(Faculty entity, UUID userId) {
        return entity.getUniversity()
                .getAdminId()
                .equals(userId);
    }

    @Override
    protected void selectedForDeactivateChild(Faculty entity) {
        deactivatedChild(entity.getGroups(), UniversityGroupServiceImpl.class);
        deactivatedChild(entity.getLecturers(), LecturerServiceImpl.class);
    }
}
