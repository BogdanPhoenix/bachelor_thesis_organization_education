package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.dto.StudentEvaluation;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.services.interfaces.university.*;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.responces.university.MagazineResponse;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;
import com.bachelor.thesis.organization_education.repositories.university.GroupDisciplineRepository;
import com.bachelor.thesis.organization_education.responces.university.EvaluationsForClassesResponse;
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
    public MagazineResponse getMagazine(@NonNull UUID studentId, @NonNull UUID entityId) throws NotFindEntityInDataBaseException {
        var magazine = getMagazine(entityId);

        for(var evaluation : magazine.getEvaluationsForClasses()) {
            evaluation.getStudentEvaluations()
                    .removeIf(studentEvaluation -> !studentEvaluation.getStudent()
                            .equals(studentId)
                    );
        }

        return magazine;
    }

    @Override
    public MagazineResponse getMagazine(@NonNull UUID id) throws NotFindEntityInDataBaseException {
        var entity = findValueById(id);
        return getMagazine(entity);
    }

    @Override
    public List<MagazineResponse> getMagazinesByLecturer(@NonNull UUID lecturerId) throws NotFindEntityInDataBaseException {
        return getAllMagazine()
                .stream()
                .filter(entity -> entity.getGroupDiscipline().getLecturer().equals(lecturerId))
                .toList();
    }

    @Override
    public List<MagazineResponse> getAllMagazine() {
       return repository.findAll()
               .stream()
               .filter(BaseTableInfo::isEnabled)
               .map(this::getMagazine)
               .toList();
    }

    private MagazineResponse getMagazine(GroupDiscipline groupDiscipline) {
        var set = new HashSet<EvaluationsForClassesResponse>();

        for(var classRecording : groupDiscipline.getClassRecordings()) {
            var key = classRecording.getResponse();
            var value = classRecording.getStudentEvaluations()
                    .stream()
                    .map(StudentEvaluation::getResponse)
                    .collect(Collectors.toSet());

            var classInfo = EvaluationsForClassesResponse.builder()
                    .classRecording(key)
                    .studentEvaluations(value)
                    .build();

           set.add(classInfo);
        }

        return MagazineResponse.builder()
                .groupDiscipline(groupDiscipline.getResponse())
                .evaluationsForClasses(set)
                .build();
    }
}
