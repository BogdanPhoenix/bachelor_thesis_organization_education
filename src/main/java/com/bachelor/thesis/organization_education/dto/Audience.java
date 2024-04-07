package com.bachelor.thesis.organization_education.dto;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

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
@Table(name = "audiences",
        uniqueConstraints = @UniqueConstraint(columnNames = {"university_id", "num_floor", "num_audience"})
)
public class Audience extends BaseTableInfo {
    @NonNull
    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @Column(name = "num_floor", nullable = false)
    private short numFloor;

    @Column(name = "num_audience", nullable = false)
    private short numAudience;

    @Column(name = "num_seats", nullable = false)
    private short numSeats;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "audience", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Schedule> schedules;

    @Override
    public Response getResponse() {
        return null;
    }
}
