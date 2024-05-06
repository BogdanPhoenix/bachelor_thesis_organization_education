package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.enums.DayWeek;
import com.bachelor.thesis.organization_education.enums.Frequency;
import com.bachelor.thesis.organization_education.enums.TypeClass;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.university.ScheduleResponse;

import java.sql.Time;
import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Table(name = "schedules",
        uniqueConstraints = @UniqueConstraint(columnNames = {"group_discipline_id", "type_class"})
)
public class Schedule extends BaseTableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected UUID id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "group_discipline_id", nullable = false)
    private GroupDiscipline groupDiscipline;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "audience_id", nullable = false)
    private Audience audience;

    @NonNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type_class", nullable = false)
    private TypeClass typeClass;

    @NonNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "day_week", nullable = false)
    private DayWeek dayWeek;

    @NonNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "frequency", nullable = false)
    private Frequency frequency;

    @NonNull
    @Column(name = "start_time", nullable = false)
    private Time startTime;

    @NonNull
    @Column(name = "end_time", nullable = false)
    private Time endTime;

    @Override
    public ScheduleResponse getResponse() {
        var builder = ScheduleResponse.builder();
        super.initResponse(builder);
        return builder
                .groupDiscipline(groupDiscipline.getId())
                .audience(audience.getId())
                .typeClass(typeClass)
                .dayWeek(dayWeek)
                .frequency(frequency)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
