package com.bachelor.thesis.organization_education.dto;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.enums.AcademicDegree;
import com.bachelor.thesis.organization_education.enums.AcademicTitle;
import com.bachelor.thesis.organization_education.requests.find.user.LectureFindRequest;
import com.bachelor.thesis.organization_education.responces.user.LectureResponse;
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
@Table(name = "lectures")
public class Lecturer extends BaseTableInfo {
    @NonNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "title_id", nullable = false)
    private AcademicTitle title;

    @NonNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "degree_id", nullable = false)
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
    @OneToMany(mappedBy = "lecturer", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Schedule> schedules;

    @Override
    public LectureResponse getResponse() {
        var builder = LectureResponse.builder();
        super.initResponse(builder);
        return builder
                .userId(user)
                .title(title)
                .degree(degree)
                .faculty(faculty.getResponse())
                .build();
    }

    @Override
    public LectureFindRequest getFindRequest() {
        return LectureFindRequest.builder()
                .userId(user)
                .build();
    }
}
