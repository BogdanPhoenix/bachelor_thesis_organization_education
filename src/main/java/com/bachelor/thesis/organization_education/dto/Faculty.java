package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.dto.abstract_type.NameEntity;
import com.bachelor.thesis.organization_education.responces.university.FacultyResponse;

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
@EqualsAndHashCode(callSuper = true)
@Table(name = "faculties",
        uniqueConstraints = @UniqueConstraint(columnNames = {"university_id", "en_name"})
)
public class Faculty extends NameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected UUID id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "faculty", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Lecturer> lecturers;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "faculty", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Group> groups;

    @Override
    public FacultyResponse getResponse() {
        var responseBuilder = FacultyResponse.builder();
        super.initResponse(responseBuilder);
        return responseBuilder
                .university(university.getId())
                .build();
    }
}
