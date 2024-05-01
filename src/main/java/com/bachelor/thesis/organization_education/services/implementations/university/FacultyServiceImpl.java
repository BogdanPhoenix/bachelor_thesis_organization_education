package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.LecturerService;
import com.bachelor.thesis.organization_education.repositories.university.FacultyRepository;
import com.bachelor.thesis.organization_education.requests.general.university.FacultyRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.services.interfaces.university.GroupService;
import com.bachelor.thesis.organization_education.requests.find.university.FacultyFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.FacultyService;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import com.bachelor.thesis.organization_education.services.implementations.crud.NameEntityServiceAbstract;

import java.util.List;
import java.util.UUID;

@Service
public class FacultyServiceImpl extends NameEntityServiceAbstract<Faculty, FacultyRepository> implements FacultyService {
    @Autowired
    protected FacultyServiceImpl(FacultyRepository repository, ApplicationContext context) {
        super(repository, "Faculties", context);
    }

    @Override
    protected void objectFormation(InsertRequest request) { }

    @Override
    protected Faculty createEntity(InsertRequest request) {
        var facultyRequest = (FacultyRequest) request;
        var builder = Faculty.builder();
        return super.initEntity(builder, request)
                .university(facultyRequest.getUniversity())
                .build();
    }

    @Override
    public Faculty addResource(@NonNull FacultyRequest request, @NonNull String adminId) {
        var university = getUniversity(adminId);
        request.setUniversity(university);
        return super.addValue(request);
    }

    @Override
    public BaseTableInfo updateValue(@NonNull String adminId, @NonNull UUID entityId, @NonNull UpdateRequest request) throws DuplicateException, NotFindEntityInDataBaseException {
        var updateRequest = (FacultyRequest) request;
        var university = getUniversity(adminId);
        updateRequest.setUniversity(university);
        return super.updateValue(entityId, updateRequest);
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
    protected void selectedForDeactivateChild(UUID id) {
       var entity = repository.findById(id);
       entity.ifPresent(e -> {
           deactivatedChild(e.getGroups(), GroupService.class);
           deactivatedChild(e.getLecturers(), LecturerService.class);
       });
    }
}
