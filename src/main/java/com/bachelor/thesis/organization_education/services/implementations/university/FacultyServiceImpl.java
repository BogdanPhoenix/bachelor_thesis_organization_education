package com.bachelor.thesis.organization_education.services.implementations.university;

import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.repositories.university.FacultyRepository;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import com.bachelor.thesis.organization_education.requests.find.university.FacultyFindRequest;
import com.bachelor.thesis.organization_education.requests.insert.university.FacultyInsertRequest;
import com.bachelor.thesis.organization_education.requests.general.university.FacultyRequest;
import com.bachelor.thesis.organization_education.requests.find.university.UniversityFindRequest;
import com.bachelor.thesis.organization_education.services.implementations.crud.NameEntityServiceAbstract;
import com.bachelor.thesis.organization_education.services.interfaces.university.FacultyService;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FacultyServiceImpl extends NameEntityServiceAbstract<Faculty, FacultyRepository> implements FacultyService {
    private final UniversityService universityService;

    @Autowired
    protected FacultyServiceImpl(FacultyRepository repository, UniversityService universityService) {
        super(repository, "Faculties");

        this.universityService = universityService;
    }

    @Override
    protected Faculty createEntity(@NonNull Request request) {
        var facultyRequest = (FacultyRequest) request;
        var builder = Faculty.builder();
        return super.initEntity(builder, request)
                .university(facultyRequest.getUniversity())
                .build();
    }

    @Override
    public Faculty addResource(FacultyInsertRequest request, String userId) {
        var universityRequest = UniversityFindRequest.builder()
                .adminId(UUID.fromString(userId))
                .enName("")
                .uaName("")
                .build();

        var university = (University) universityService.getValue(universityRequest);

        var facultyRequest = FacultyRequest
                .builder()
                .university(university)
                .enName(request.getEnName())
                .uaName(request.getUaName())
                .build();

        return super.addValue(facultyRequest);
    }

    @Override
    protected Optional<Faculty> findEntity(@NonNull FindRequest request) {
        var facultyRequest = (FacultyFindRequest) request;

        return repository.findFaculty(
                facultyRequest.getUniversity(),
                facultyRequest.getEnName(),
                facultyRequest.getUaName()
        );
    }
}
