package com.bachelor.thesis.organization_education.services.implementations.university;

import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.insert.university.GroupInsertRequest;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import com.bachelor.thesis.organization_education.dto.Group;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.repositories.university.GroupRepository;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.university.GroupRequest;
import com.bachelor.thesis.organization_education.requests.find.university.GroupFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.GroupService;
import com.bachelor.thesis.organization_education.requests.update.university.GroupUpdateRequest;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;

import java.util.Optional;

@Service
public class GroupServiceImpl extends CrudServiceAbstract<Group, GroupRepository> implements GroupService {
    protected GroupServiceImpl(GroupRepository repository) {
        super(repository, "Groups");
    }

    @Override
    protected Group createEntity(Request request) {
        var groupRequest = (GroupRequest) request;
        return Group.builder()
                .curator(groupRequest.getCurator())
                .specialty(groupRequest.getSpecialty())
                .faculty(groupRequest.getFaculty())
                .yearStart(groupRequest.getYearStart())
                .yearEnd(groupRequest.getYearEnd())
                .reducedForm(groupRequest.isReducedForm())
                .build();
    }

    @Override
    public Group addResource(@NonNull GroupInsertRequest request) throws DuplicateException, NullPointerException {
        var groupRequest = GroupRequest.builder()
                .curator(request.getCurator())
                .specialty(request.getSpecialty())
                .faculty(request.getFaculty())
                .yearStart(request.getYearStart())
                .yearEnd(request.getYearEnd())
                .reducedForm(request.isReducedForm())
                .build();

        return super.addValue(groupRequest);
    }

    @Override
    protected Optional<Group> findEntityByRequest(@NonNull FindRequest request) {
        var groupRequest = (GroupFindRequest) request;

        return repository.findBySpecialtyAndYearStartAndReducedForm(
                groupRequest.getSpecialty(),
                groupRequest.getYearStart(),
                groupRequest.isReducedForm()
        );
    }

    @Override
    protected void updateEntity(Group entity, UpdateRequest request) {
        var groupRequest = (GroupUpdateRequest) request;

        if(!groupRequest.curatorIsEmpty()) {
            entity.setCurator(groupRequest.getCurator());
        }
        if(!groupRequest.specialtyIsEmpty()) {
            entity.setSpecialty(groupRequest.getSpecialty());
        }
        if(!groupRequest.facultyIsEmpty()) {
            entity.setFaculty(groupRequest.getFaculty());
        }
        if(!groupRequest.yearStartIsEmpty()) {
            entity.setYearStart(groupRequest.getYearStart());
        }
        if(!groupRequest.yearEndIsEmpty()) {
            entity.setYearEnd(groupRequest.getYearEnd());
        }

        entity.setReducedForm(groupRequest.isReducedForm());
    }

    @Override
    protected void selectedForDeactivateChild(Long id) { }
}
