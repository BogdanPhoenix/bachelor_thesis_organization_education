package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import com.bachelor.thesis.organization_education.dto.Group;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.repositories.university.GroupRepository;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.general.university.GroupRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.LecturerService;
import com.bachelor.thesis.organization_education.requests.find.university.GroupFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.GroupService;
import com.bachelor.thesis.organization_education.services.interfaces.university.FacultyService;
import com.bachelor.thesis.organization_education.services.interfaces.university.SpecialtyService;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;
import com.bachelor.thesis.organization_education.services.interfaces.university.GroupDisciplineService;

import java.util.List;
import java.util.UUID;

@Service
public class GroupServiceImpl extends CrudServiceAbstract<Group, GroupRepository> implements GroupService {
    @Autowired
    public GroupServiceImpl(GroupRepository repository, ApplicationContext context) {
        super(repository, "Groups", context);
    }

    @Override
    protected void objectFormation(InsertRequest request) {
        var groupRequest = (GroupRequest) request;
        groupRequest.setCurator(super.getValue(groupRequest.getCurator(), LecturerService.class));
        groupRequest.setSpecialty(super.getValue(groupRequest.getSpecialty(), SpecialtyService.class));
        groupRequest.setFaculty(super.getValue(groupRequest.getFaculty(), FacultyService.class));
    }

    @Override
    protected Group createEntity(InsertRequest request) {
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
    protected List<Group> findAllEntitiesByRequest(@NonNull FindRequest request) {
        var groupRequest = (GroupFindRequest) request;

        return repository.findAllBySpecialtyAndYearStartAndReducedForm(
                groupRequest.getSpecialty(),
                groupRequest.getYearStart(),
                groupRequest.isReducedForm()
        );
    }

    @Override
    protected void updateEntity(Group entity, UpdateRequest request) {
        var groupRequest = (GroupRequest) request;

        if(!groupRequest.curatorIsEmpty()) {
            var curator = super.getValue(groupRequest.getCurator(), LecturerService.class);
            entity.setCurator(curator);
        }
        if(!groupRequest.specialtyIsEmpty()) {
            var specialty = super.getValue(groupRequest.getSpecialty(), SpecialtyService.class);
            entity.setSpecialty(specialty);
        }
        if(!groupRequest.facultyIsEmpty()) {
            var faculty = super.getValue(groupRequest.getFaculty(), FacultyService.class);
            entity.setFaculty(faculty);
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
    protected void selectedForDeactivateChild(UUID id) {
        var entity = repository.findById(id);
        entity.ifPresent(e -> deactivatedChild(e.getGroupsDisciplines(), GroupDisciplineService.class));
    }
}
