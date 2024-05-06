package com.bachelor.thesis.organization_education.responces.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import com.bachelor.thesis.organization_education.enums.DayWeek;
import com.bachelor.thesis.organization_education.enums.Frequency;
import com.bachelor.thesis.organization_education.enums.TypeClass;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;

import java.sql.Time;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ScheduleResponse extends Response {
    @NonNull
    private UUID groupDiscipline;

    @NonNull
    private UUID audience;

    @NonNull
    private TypeClass typeClass;

    @NonNull
    private DayWeek dayWeek;

    @NonNull
    private Frequency frequency;

    @NonNull
    private Time startTime;

    @NonNull
    private Time endTime;
}
