package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.responces.user.StudentResponse;
import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;

import java.util.UUID;

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
@Table(name = "students",
        uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "id"})
)
public class Student extends BaseTableInfo {
    @Id
    @Column(name = "id")
    protected UUID id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Override
    public StudentResponse getResponse() {
        var builder = StudentResponse.builder();
        super.initResponse(builder);
        return builder
                .group(group.getResponse())
                .build();
    }
}
