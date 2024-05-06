package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.enums.AcademicTitle;
import com.bachelor.thesis.organization_education.enums.AcademicDegree;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.user.LecturerResponse;
import com.bachelor.thesis.organization_education.responces.university.AcademicDisciplineResponse;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;
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
@Table(name = "lecturers")
public class Lecturer extends BaseTableInfo {
    @Id
    @Column(name = "id")
    protected UUID id;

    @NonNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "title", nullable = false)
    private AcademicTitle title;

    @NonNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "degree", nullable = false)
    private AcademicDegree degree;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {PERSIST, MERGE, REFRESH})
    @JoinTable(
            name = "lecturer_disciplines",
            joinColumns = @JoinColumn(name = "lecturer_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "discipline_id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"lecturer_id", "discipline_id"})
    )
    private List<AcademicDiscipline> disciplines;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "curator", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Group> groups;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "lecturer", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<GroupDiscipline> groupDisciplines;

    @Override
    public LecturerResponse getResponse() {
        var builder = LecturerResponse.builder();
        var disciplinesResponse = disciplines == null
                ? new ArrayList<AcademicDisciplineResponse>()
                : disciplines
                .stream()
                .map(AcademicDiscipline::getResponse)
                .toList();

        super.initResponse(builder);
        return builder
                .title(title)
                .degree(degree)
                .faculty(faculty.getResponse())
                .disciplines(disciplinesResponse)
                .build();
    }
}
