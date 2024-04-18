package com.bachelor.thesis.organization_education.dto;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.enums.DayWeek;
import com.bachelor.thesis.organization_education.enums.Frequency;
import com.bachelor.thesis.organization_education.enums.TypeClass;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
        uniqueConstraints = @UniqueConstraint(columnNames = {"discipline_id", "group_id", "teacher_id", "type_class"})
)
public class Schedule extends BaseTableInfo {
    @NonNull
    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private AcademicDiscipline discipline;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Lecture lecture;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "call_id", nullable = false)
    private CallSchedule call;

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

    @Override
    public Response getResponse() {
        return null;
    }

    @Override
    public FindRequest getFindRequest() {
        return null;
    }
}
