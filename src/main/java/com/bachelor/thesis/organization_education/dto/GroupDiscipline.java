package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
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
    private Group group;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private AcademicDiscipline discipline;

    @Column(name = "amount_practical", nullable = false)
    private short amountPractical;

    @Column(name = "amount_lecture", nullable = false)
    private short amountLecture;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "magazine", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<ClassRecording> classRecordings;

    @Override
    public GroupDisciplineResponse getResponse() {
        var builder = GroupDisciplineResponse.builder();
        super.initResponse(builder);
        return builder
                .group(group.getResponse())
                .discipline(discipline.getResponse())
                .amountLecture(amountLecture)
                .amountPractical(amountPractical)
                .build();
    }
}
