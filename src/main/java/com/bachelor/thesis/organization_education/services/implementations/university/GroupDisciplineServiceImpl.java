package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import com.bachelor.thesis.organization_education.dto.ClassRecording;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.dto.StudentEvaluation;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import com.bachelor.thesis.organization_education.services.interfaces.university.*;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.responces.university.MagazineResponse;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.responces.university.StudentEvaluationResponse;
import com.bachelor.thesis.organization_education.services.implementations.user.LecturerServiceImpl;
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

        updateIfPresent(updateRequest::getLecturer, entity::setLecturer);
        updateIfPresent(updateRequest::getAmountLecture, entity::setAmountLecture);
        updateIfPresent(updateRequest::getAmountPractical, entity::setAmountPractical);
        updateIfPresent(updateRequest::getSemester, entity::setSemester);
    }

    @Override
    protected boolean checkOwner(GroupDiscipline entity, UUID userId) {
        return entity.getGroup()
                .getFaculty()
                .getUniversity()
                .getAdminId()
                .equals(userId);
    }

    @Override
    protected void selectedForDeactivateChild(GroupDiscipline entity) {
        deactivatedChild(entity.getClassRecordings(), ClassRecordingServiceImpl.class);
        deactivatedChild(entity.getSchedules(), ScheduleServiceImpl.class);
    }

    @Override
    public Page<Response> getAllByUniversityAdmin(@NonNull Pageable pageable) {
        var uuid = super.getAuthenticationUUID();
        return repository.findAll(uuid, pageable)
                .map(BaseTableInfo::getResponse);
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
    public Page<MagazineResponse> getMagazinesByLecturer(@NonNull UUID lecturerId, @NonNull Pageable pageable) throws NotFindEntityInDataBaseException {
        var entity = (Lecturer) getBeanByClass(LecturerServiceImpl.class)
                .getValue(lecturerId);

        return repository.findAllByLecturer(entity, pageable)
                .map(this::getMagazine);
    }

    @Override
    public Page<MagazineResponse> getAllMagazine(@NonNull Pageable pageable) {
       return repository.findAllActive(pageable)
               .map(this::getMagazine);
    }

    private MagazineResponse getMagazine(GroupDiscipline groupDiscipline) {
        var evaluations = groupDiscipline.getClassRecordings()
                .stream()
                .map(this::mapToEvaluationsForClassesResponse)
                .collect(Collectors.toSet());

        return MagazineResponse.builder()
                .groupDiscipline(groupDiscipline.getResponse())
                .evaluationsForClasses(evaluations)
                .build();
    }

    private EvaluationsForClassesResponse mapToEvaluationsForClassesResponse(ClassRecording classRecording) {
        var key = classRecording.getResponse();
        var value = mapToStudentEvaluationResponse(classRecording);

        return new EvaluationsForClassesResponse(key, value);
    }

    private Set<StudentEvaluationResponse> mapToStudentEvaluationResponse(ClassRecording classRecording) {
        return classRecording.getStudentEvaluations()
                .stream()
                .map(StudentEvaluation::getResponse)
                .collect(Collectors.toSet());
    }
}
