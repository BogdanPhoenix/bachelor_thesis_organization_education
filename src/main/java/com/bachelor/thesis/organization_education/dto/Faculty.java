package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.validation.constraints.NotBlank;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
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
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"university_id", "en_name"}),
            @UniqueConstraint(columnNames = {"university_id", "ua_name"})
        }
)
public class Faculty extends BaseTableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected UUID id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @NonNull
    @NotBlank
    @Column(name = "en_name", nullable = false)
    private String enName;

    @NonNull
    @NotBlank
    @Column(name = "ua_name", nullable = false)
    private String uaName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "faculty", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Lecturer> lecturers;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "faculty", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<UniversityGroup> groups;

    @Override
    public FacultyResponse getResponse() {
        var responseBuilder = FacultyResponse.builder();
        super.initResponse(responseBuilder);
        return responseBuilder
                .university(university.getId())
                .enName(enName)
                .uaName(uaName)
                .build();
    }
}
