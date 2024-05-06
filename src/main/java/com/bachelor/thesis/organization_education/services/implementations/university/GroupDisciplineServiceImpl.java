package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.dto.StudentEvaluation;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.services.interfaces.university.*;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.responces.university.MagazineResponse;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.LecturerService;
import com.bachelor.thesis.organization_education.responces.university.ClassRecordingResponse;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.responces.university.StudentEvaluationResponse;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;
import com.bachelor.thesis.organization_education.repositories.university.GroupDisciplineRepository;
import com.bachelor.thesis.organization_education.requests.general.university.GroupDisciplineRequest;
import com.bachelor.thesis.organization_education.requests.find.university.GroupDisciplineFindRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupDisciplineServiceImpl extends CrudServiceAbstract<GroupDiscipline, GroupDisciplineRepository> implements GroupDisciplineService {
    @Autowired
    public GroupDisciplineServiceImpl(GroupDisciplineRepository repository, ApplicationContext context) {
        super(repository, "Groups disciplines", context);
    }

    @Override
    protected void objectFormation(InsertRequest request) {
        var insertRequest = (GroupDisciplineRequest) request;
        insertRequest.setGroup(super.getValue(insertRequest.getGroup(), GroupService.class));
        insertRequest.setDiscipline(super.getValue(insertRequest.getDiscipline(), AcademicDisciplineService.class));
        insertRequest.setLecturer(super.getValue(insertRequest.getLecturer(), LecturerService.class));
    }

    @Override
    protected GroupDiscipline createEntity(InsertRequest request) {
        var insertRequest = (GroupDisciplineRequest) request;

        return GroupDiscipline.builder()
                .group(insertRequest.getGroup())
                .discipline(insertRequest.getDiscipline())
                .lecturer(insertRequest.getLecturer())
                .semester(insertRequest.getSemester())
                .amountLecture(insertRequest.getAmountLecture())
                .amountPractical(insertRequest.getAmountPractical())
                .build();
    }

    @Override
    public GroupDiscipline updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws DuplicateException, NotFindEntityInDataBaseException {
        var entity = findValueById(id);
        return updateValue(entity, request);
    }

    @Override
    protected List<GroupDiscipline> findAllEntitiesByRequest(@NonNull FindRequest request) {
        var findRequest = (GroupDisciplineFindRequest) request;
        return repository.findAllByGroupAndDiscipline(
                findRequest.getGroup(),
                findRequest.getDiscipline()
        );
    }

    @Override
    protected void updateEntity(GroupDiscipline entity, UpdateRequest request) {
        var updateRequest = (GroupDisciplineRequest) request;

        if(!updateRequest.lecturerIsEmpty()) {
            entity.setLecturer(updateRequest.getLecturer());
        }
        if(!updateRequest.amountLectureIsEmpty()) {
            entity.setAmountLecture(updateRequest.getAmountLecture());
        }
        if(!updateRequest.amountPracticalIsEmpty()) {
            entity.setAmountPractical(updateRequest.getAmountPractical());
        }
        if(!updateRequest.semesterIsEmpty()) {
            entity.setSemester(updateRequest.getSemester());
        }
    }

    @Override
    protected void selectedForDeactivateChild(UUID id) {
        var entity = repository.findById(id);
        entity.ifPresent(e -> {
            deactivatedChild(e.getClassRecordings(), ClassRecordingService.class);
            deactivatedChild(e.getSchedules(), ScheduleService.class);
        });
    }

    @Override
    public MagazineResponse getMagazine(@NonNull UUID id) throws NotFindEntityInDataBaseException {
        var entity = findValueById(id);
        return getMagazine(entity);
    }

    @Override
    public List<MagazineResponse> getAllMagazine(Pageable pageable) {
        var entities = getAll(pageable);
        var list = new ArrayList<MagazineResponse>();

        for(var entity : entities) {
            var magazine = (GroupDiscipline) entity;
            list.add(getMagazine(magazine));
        }

        return list;
    }

    private MagazineResponse getMagazine(GroupDiscipline groupDiscipline) {
        var map = new HashMap<ClassRecordingResponse, Set<StudentEvaluationResponse>>();

        for(var classRecording : groupDiscipline.getClassRecordings()) {
            var key = classRecording.getResponse();
            var value = classRecording.getStudentEvaluations()
                    .stream()
                    .map(StudentEvaluation::getResponse)
                    .collect(Collectors.toSet());

            map.put(key, value);
        }

        return MagazineResponse.builder()
                .groupDiscipline(groupDiscipline.getResponse())
                .evaluationMap(map)
                .build();
    }
}
