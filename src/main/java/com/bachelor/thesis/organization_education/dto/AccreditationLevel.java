package com.bachelor.thesis.organization_education.dto;

import com.bachelor.thesis.organization_education.dto.abstract_type.NameEntity;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

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
@Table(name = "accreditation_levels")
public class AccreditationLevel extends NameEntity {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "accreditationLevel", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<University> universities;

    @Override
    public Response getResponse() {
        return null;
    }
}
