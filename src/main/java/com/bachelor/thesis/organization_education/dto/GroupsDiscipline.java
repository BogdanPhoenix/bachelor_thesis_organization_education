package com.bachelor.thesis.organization_education.dto;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

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
public class GroupsDiscipline extends BaseTableInfo {
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
    private Set<ClassRecordings> classRecordings;

    @Override
    public Response getResponse() {
        return null;
    }
}
