package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;

import java.sql.Time;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

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
@Table(name = "call_schedule",
        uniqueConstraints = @UniqueConstraint(columnNames = {"start_time", "end_time"})
)
public class CallSchedule extends BaseTableInfo {
    @NonNull
    @Column(name = "start_time", nullable = false)
    private Time startTime;

    @NonNull
    @Column(name = "end_time", nullable = false)
    private Time endTime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "call", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Schedule> schedules;

    @Override
    public Response getResponse() {
        return null;
    }
}
