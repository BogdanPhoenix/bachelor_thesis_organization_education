package com.bachelor.thesis.organization_education.dto;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;
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
@Table(name = "teachers")
public class Teacher extends BaseTableInfo {
    @NonNull
    @ManyToOne
    @JoinColumn(name = "title_id", nullable = false)
    private AcademicTitle title;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "degree_id", nullable = false)
    private AcademicDegree degree;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @NonNull
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "teachers_disciplines",
            joinColumns = @JoinColumn(name = "teacher_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "discipline_id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"teacher_id", "discipline_id"})
    )
    private List<AcademicDiscipline> disciplines;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "curator", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Group> groups;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "teacher", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Schedule> schedules;

    @Override
    public Response getResponse() {
        return null;
    }
}
