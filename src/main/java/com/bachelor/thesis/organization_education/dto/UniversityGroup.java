package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.university.UniversityGroupResponse;

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
@Table(name = "groups",
        uniqueConstraints = @UniqueConstraint(columnNames = {"specialty_id", "year_start", "reduced_form"})
)
public class UniversityGroup extends BaseTableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected UUID id;

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
    private Set<GroupDiscipline> groupsDisciplines;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "group", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Student> students;

    @Override
    public UniversityGroupResponse getResponse() {
        var builder = UniversityGroupResponse.builder();
        super.initResponse(builder);
        return builder.curator(curator.getId())
                .specialty(specialty.getId())
                .faculty(faculty.getId())
                .yearStart(yearStart)
                .yearEnd(yearEnd)
                .reducedForm(reducedForm)
                .build();
    }
}
