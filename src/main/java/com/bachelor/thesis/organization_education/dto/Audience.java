package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.university.AudienceResponse;

import java.util.Set;
import java.util.UUID;

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
@Table(name = "audiences",
        uniqueConstraints = @UniqueConstraint(columnNames = {"university_id", "num_floor", "num_audience"})
)
public class Audience extends BaseTableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected UUID id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @NonNull
    @Column(name = "num_floor", nullable = false)
    private Short numFloor;

    @NonNull
    @Column(name = "num_audience", nullable = false)
    private Short numAudience;

    @NonNull
    @Column(name = "num_seats", nullable = false)
    private Short numSeats;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "audience", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Schedule> schedules;

    @Override
    public AudienceResponse getResponse() {
        var builder = AudienceResponse.builder();
        super.initResponse(builder);
        return builder
                .university(university.getResponse())
                .numFloor(numFloor)
                .numAudience(numAudience)
                .numSeats(numSeats)
                .build();
    }
}
