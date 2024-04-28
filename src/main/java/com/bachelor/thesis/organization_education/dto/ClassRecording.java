package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;

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
@Table(name = "class_recordings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"magazine_id", "class_topic"})
)
public class ClassRecording extends BaseTableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected UUID id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "magazine_id", nullable = false)
    private GroupDiscipline magazine;

    @NonNull
    @NotBlank
    @Column(name = "class_topic", nullable = false)
    private String classTopic;

    @NonNull
    @NotBlank
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "classRecording", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<StudentEvaluation> studentEvaluations;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "classRecording", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<File> files;

    @Override
    public Response getResponse() {
        return null;
    }
}
