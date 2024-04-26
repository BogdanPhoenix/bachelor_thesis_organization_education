package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.university.GroupResponse;
import com.bachelor.thesis.organization_education.requests.find.university.GroupFindRequest;

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
@Table(name = "groups",
        uniqueConstraints = @UniqueConstraint(columnNames = {"specialty_id", "year_start", "reduced_form"})
)
public class Group extends BaseTableInfo {
    @NonNull
    @ManyToOne
    @JoinColumn(name = "curator_id", nullable = false)
    private Lecturer curator;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialty;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @NonNull
    @Column(name = "year_start", nullable = false)
    private Short yearStart;

    @NonNull
    @Column(name = "year_end", nullable = false)
    private Short yearEnd;

    @Column(name = "reduced_form")
    private boolean reducedForm;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "group", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<GroupsDiscipline> groupsDisciplines;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "group", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Schedule> schedules;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "group", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Student> students;

    @Override
    public GroupResponse getResponse() {
        var builder = GroupResponse.builder();
        super.initResponse(builder);
        return builder.curator(curator.getResponse())
                .specialty(specialty.getResponse())
                .faculty(faculty.getResponse())
                .yearStart(yearStart)
                .yearEnd(yearEnd)
                .reducedForm(reducedForm)
                .build();
    }

    @Override
    public GroupFindRequest getFindRequest() {
        return GroupFindRequest.builder()
                .specialty(specialty)
                .yearStart(yearStart)
                .reducedForm(reducedForm)
                .build();
    }
}
