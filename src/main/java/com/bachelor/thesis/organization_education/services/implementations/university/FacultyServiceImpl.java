package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.LecturerService;
import com.bachelor.thesis.organization_education.repositories.university.FacultyRepository;
import com.bachelor.thesis.organization_education.requests.general.university.FacultyRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.GroupService;
import com.bachelor.thesis.organization_education.requests.find.university.FacultyFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.FacultyService;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import com.bachelor.thesis.organization_education.services.implementations.crud.NameEntityServiceAbstract;

import java.util.Optional;

@Service
public class FacultyServiceImpl extends NameEntityServiceAbstract<Faculty, FacultyRepository> implements FacultyService {
    private final UniversityService universityService;

    @Autowired
    protected FacultyServiceImpl(FacultyRepository repository, UniversityService universityService) {
        super(repository, "Faculties");

        this.universityService = universityService;
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
    public Faculty addResource(FacultyRequest request, String userId) {
        var university = universityService.findByUser(userId);
        request.setUniversity(university);
        return super.addValue(request);
    }

    @Override
    protected Optional<Faculty> findEntityByRequest(@NonNull FindRequest request) {
        var facultyRequest = (FacultyFindRequest) request;

        return repository.findFaculty(
                facultyRequest.getUniversity(),
                facultyRequest.getEnName(),
                facultyRequest.getUaName()
        );
    }

    @Override
    protected void selectedForDeactivateChild(Long id) {
       var entity = findEntityById(id);
       deactivatedChild(entity.getGroups(), GroupService.class);
       deactivatedChild(entity.getLecturers(), LecturerService.class);
    }
}
