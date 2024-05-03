package com.bachelor.thesis.organization_education.services.implementations.university;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor.thesis.organization_education.dto.Schedule;
import com.bachelor.thesis.organization_education.exceptions.DuplicateException;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.services.interfaces.user.LecturerService;
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
        insertRequest.setLecturer(super.getValue(insertRequest.getLecturer(), LecturerService.class));
        insertRequest.setAudience(super.getValue(insertRequest.getAudience(), AudienceService.class));
    }

    @Override
    protected Schedule createEntity(InsertRequest request) {
        var insertRequest = (ScheduleRequest) request;
        return Schedule.builder()
                .groupDiscipline(insertRequest.getGroupDiscipline())
                .lecturer(insertRequest.getLecturer())
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
        return repository.findAllByGroupDisciplineAndLecturerAndTypeClass(
                findRequest.getGroupDiscipline(),
                findRequest.getLecturer(),
                findRequest.getTypeClass()
        );
    }

    @Override
    public Schedule updateValue(@NonNull UUID id, @NonNull UpdateRequest request) throws DuplicateException, NotFindEntityInDataBaseException {
        var entity = findValueById(id);
        return super.updateValue(entity, request);
    }

    @Override
    protected void updateEntity(Schedule entity, UpdateRequest request) {
        var updateRequest = (ScheduleRequest) request;

        if(!updateRequest.lecturerIsEmpty()) {
            entity.setLecturer(updateRequest.getLecturer());
        }
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

    @Override
    protected void selectedForDeactivateChild(UUID id) {

    }
}
