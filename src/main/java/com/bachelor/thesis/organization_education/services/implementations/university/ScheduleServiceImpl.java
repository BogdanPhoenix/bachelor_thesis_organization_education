package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.Schedule;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.repositories.university.ScheduleRepository;
import com.bachelor.thesis.organization_education.requests.general.university.ScheduleRequest;
import com.bachelor.thesis.organization_education.exceptions.NotFindEntityInDataBaseException;
import com.bachelor.thesis.organization_education.requests.find.university.ScheduleFindRequest;
import com.bachelor.thesis.organization_education.services.interfaces.university.AudienceService;
import com.bachelor.thesis.organization_education.services.interfaces.university.ScheduleService;
import com.bachelor.thesis.organization_education.services.implementations.crud.CrudServiceAbstract;
import com.bachelor.thesis.organization_education.services.interfaces.university.GroupDisciplineService;

import java.util.List;
import java.util.UUID;

@Service
public class ScheduleServiceImpl extends CrudServiceAbstract<Schedule, ScheduleRepository> implements ScheduleService {
    @Autowired
    public ScheduleServiceImpl(ScheduleRepository repository, ApplicationContext context) {
        super(repository, "Schedules", context);
    }

    @Override
    protected void objectFormation(InsertRequest request) {
        var insertRequest = (ScheduleRequest) request;
        insertRequest.setGroupDiscipline(super.getValue(insertRequest.getGroupDiscipline(), GroupDisciplineService.class));
        insertRequest.setAudience(super.getValue(insertRequest.getAudience(), AudienceService.class));
    }

    @Override
    protected Schedule createEntity(InsertRequest request) {
        var insertRequest = (ScheduleRequest) request;
        return Schedule.builder()
                .groupDiscipline(insertRequest.getGroupDiscipline())
                .audience(insertRequest.getAudience())
                .typeClass(insertRequest.getTypeClass())
                .dayWeek(insertRequest.getDayWeek())
                .frequency(insertRequest.getFrequency())
                .startTime(insertRequest.getStartTime())
                .endTime(insertRequest.getEndTime())
                .build();
    }

    @Override
    protected List<Schedule> findAllEntitiesByRequest(@NonNull FindRequest request) {
        var findRequest = (ScheduleFindRequest) request;
        return repository.findAllByGroupDisciplineAndTypeClass(
                findRequest.getGroupDiscipline(),
                findRequest.getTypeClass()
        );
    }

    @Override
    public Schedule addValue(@NonNull InsertRequest request) throws DuplicateException, NullPointerException {
        var insertRequest = (ScheduleRequest) request;

        validateDuplicate(request.getFindRequest());
        objectFormation(request);
        validateMatchesSchedulesByLecturer(insertRequest);
        validateMatchesSchedulesByAudience(insertRequest);

        var newEntity = createEntity(request);
        return repository.save(newEntity);
    }

    @Override
    public Schedule updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws DuplicateException, NotFindEntityInDataBaseException {
        var updateRequest = (ScheduleRequest) request;

        validateMatchesSchedulesByLecturer(updateRequest);
        validateMatchesSchedulesByAudience(updateRequest);

        var entity = findValueById(id);
        return super.updateValue(entity, request);
    }

    private void validateMatchesSchedulesByLecturer(ScheduleRequest request) {
        var schedules = repository.findForMatchesByLecturer(
                request.getGroupDiscipline().getLecturer(),
                request.getDayWeek(),
                request.getStartTime(),
                request.getEndTime()
        );

        handleScheduleValidation(schedules, request.getGroupDiscipline(), FindBy.LECTURER);
    }

    private void validateMatchesSchedulesByAudience(ScheduleRequest request) {
        var schedules = repository.findForMatchesByAuditory(
                request.getAudience(),
                request.getDayWeek(),
                request.getStartTime(),
                request.getEndTime()
        );

        handleScheduleValidation(schedules, request.getGroupDiscipline(), FindBy.AUDIENCE);
    }

    private void handleScheduleValidation(List<Schedule> schedules, GroupDiscipline groupDiscipline, FindBy type) {
        var conflictingSchedule = schedules.stream()
                .filter(schedule -> !schedule.getGroupDiscipline().equals(groupDiscipline))
                .findFirst();

        if (conflictingSchedule.isPresent()) {
            var errorMessage = switch (type) {
                case LECTURER ->
                        "It's not possible to add the specified entity. The specified teacher already has another class scheduled for the specified hour.";
                case AUDIENCE ->
                        "The specified entity cannot be added. The audience is already occupied for the specified time period.";
            };
            throw new DuplicateException(errorMessage);
        }

        if (!schedules.isEmpty()) {
            var errorMessage = switch (type) {
                case LECTURER -> "The lesson to be held during the specified time period is already saved in the database.";
                case AUDIENCE -> "The database already contains a record of a class in the specified classroom.";
            };
            throw new DuplicateException(errorMessage);
        }
    }

    @Override
    protected void updateEntity(Schedule entity, UpdateRequest request) {
        var updateRequest = (ScheduleRequest) request;

        if(!updateRequest.audienceIsEmpty()) {
            entity.setAudience(updateRequest.getAudience());
        }
        if(!updateRequest.dayWeekIsEmpty()) {
            entity.setDayWeek(updateRequest.getDayWeek());
        }
        if(!updateRequest.frequencyIsEmpty()) {
            entity.setFrequency(updateRequest.getFrequency());
        }
        if(!updateRequest.startTimeIsEmpty()) {
            entity.setStartTime(updateRequest.getStartTime());
        }
        if(!updateRequest.endTimeIsEmpty()) {
            entity.setEndTime(updateRequest.getEndTime());
        }
    }

    private enum FindBy {
        LECTURER, AUDIENCE
    }
}
