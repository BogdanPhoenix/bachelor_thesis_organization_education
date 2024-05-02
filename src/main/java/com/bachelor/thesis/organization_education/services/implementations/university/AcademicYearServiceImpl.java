package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.dto.AcademicYear;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.repositories.university.AcademicYearRepository;
import com.bachelor.thesis.organization_education.requests.general.university.AcademicYearRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.UniversityService;
import com.bachelor.thesis.organization_education.requests.find.university.AcademicYearFindRequest;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;
import com.bachelor.thesis.organization_education.services.interfaces.university.AcademicYearService;

import java.util.List;
import java.util.UUID;

@Service
public class AcademicYearServiceImpl extends CrudServiceAbstract<AcademicYear, AcademicYearRepository> implements AcademicYearService {
    @Autowired
    public AcademicYearServiceImpl(AcademicYearRepository repository, ApplicationContext context) {
        super(repository, "Academic years", context);
    }

    @Override
    protected void objectFormation(InsertRequest request) {

    }

    @Override
    protected AcademicYear createEntity(InsertRequest request) {
        var insertRequest = (AcademicYearRequest) request;
        return AcademicYear.builder()
                .university(insertRequest.getUniversity())
                .startYear(insertRequest.getStartYear())
                .endYear(insertRequest.getEndYear())
                .build();
    }

    @Override
    public BaseTableInfo addResource(@NonNull AcademicYearRequest request, @NonNull String adminId) throws NullPointerException, DuplicateException {
        var university = getUniversity(adminId);
        request.setUniversity(university);
        return super.addValue(request);
    }

    private University getUniversity(String adminId) {
        var uuid = UUID.fromString(adminId);
        var universityService = getBeanByClass(UniversityService.class);
        return universityService.findByUser(uuid);
    }

    @Override
    protected List<AcademicYear> findAllEntitiesByRequest(@NonNull FindRequest request) {
        var findRequest = (AcademicYearFindRequest) request;
        return repository.findAllByUniversityAndStartYearAndEndYear(
                findRequest.getUniversity(),
                findRequest.getStartYear(),
                findRequest.getEndYear()
        );
    }

    @Override
    protected void updateEntity(AcademicYear entity, UpdateRequest request) {
        //There are no plans to update the entity.
    }

    @Override
    protected void selectedForDeactivateChild(UUID id) {

    }
}
