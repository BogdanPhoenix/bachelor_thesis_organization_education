package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.enums.SemesterNumber;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.university.GroupDisciplineResponse;

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
@Table(name = "groups_disciplines",
        uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "discipline_id"})
)
public class GroupDiscipline extends BaseTableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected UUID id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private UniversityGroup group;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private AcademicDiscipline discipline;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "lecturer_id", nullable = false)
    private Lecturer lecturer;

    @NonNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "semester", nullable = false)
    private SemesterNumber semester;

    @NonNull
    @Column(name = "amount_practical", nullable = false)
    private Short amountPractical;

    @NonNull
    @Column(name = "amount_lecture", nullable = false)
    private Short amountLecture;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "magazine", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<ClassRecording> classRecordings;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "groupDiscipline", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Schedule> schedules;

    @Override
    public GroupDisciplineResponse getResponse() {
        var builder = GroupDisciplineResponse.builder();
        super.initResponse(builder);
        return builder
                .group(group.getId())
                .discipline(discipline.getId())
                .lecturer(lecturer.getId())
                .semester(semester)
                .amountLecture(amountLecture)
                .amountPractical(amountPractical)
                .build();
    }
}
