package com.bachelor.thesis.organization_education.requests.general.university;

import com.bachelor.thesis.organization_education.requests.general.abstracts.TimeRange;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.dto.Audience;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import com.bachelor.thesis.organization_education.enums.DayWeek;
import com.bachelor.thesis.organization_education.enums.Frequency;
import com.bachelor.thesis.organization_education.enums.TypeClass;
import com.bachelor.thesis.organization_education.dto.GroupDiscipline;
import com.bachelor.thesis.organization_education.annotations.ProhibitValueAssignment;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.find.university.ScheduleFindRequest;

import java.sql.Time;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@GroupSequence({ScheduleRequest.class, InsertRequest.class, UpdateRequest.class})
public class ScheduleRequest implements InsertRequest, UpdateRequest, TimeRange {
    @NotNull(groups = InsertRequest.class)
    @ProhibitValueAssignment(groups = UpdateRequest.class)
    private GroupDiscipline groupDiscipline;

    @NotNull(groups = InsertRequest.class)
    private Lecturer lecturer;

    @NotNull(groups = InsertRequest.class)
    private Audience audience;

    @NotNull(groups = InsertRequest.class)
    @ProhibitValueAssignment(groups = UpdateRequest.class)
    private TypeClass typeClass;

    @NotNull(groups = InsertRequest.class)
    private DayWeek dayWeek;

    @NotNull(groups = InsertRequest.class)
    private Frequency frequency;

    @NotNull(groups = InsertRequest.class)
    private Time startTime;

    @NotNull(groups = InsertRequest.class)
    private Time endTime;

    @Override
    public ScheduleFindRequest getFindRequest() {
        return ScheduleFindRequest.builder()
                .groupDiscipline(groupDiscipline)
                .lecturer(lecturer)
                .typeClass(typeClass)
                .build();
    }

    public boolean lecturerIsEmpty() {
        return lecturer == null;
    }

    public boolean audienceIsEmpty() {
        return audience == null;
    }

    public boolean dayWeekIsEmpty() {
        return dayWeek == null;
    }

    public boolean frequencyIsEmpty() {
        return frequency == null;
    }

    public boolean startTimeIsEmpty() {
        return startTime == null;
    }

    public boolean endTimeIsEmpty() {
        return endTime == null;
    }
}
