package com.bachelor.thesis.organization_education.dto;

import com.bachelor.thesis.organization_education.dto.abstract_type.NameEntity;
import com.bachelor.thesis.organization_education.enums.AccreditationLevel;
import com.bachelor.thesis.organization_education.responces.university.UniversityResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "universities")
public class University extends NameEntity {
    @NonNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "accreditation_id", nullable = false)
    private AccreditationLevel accreditationLevel;

    @NonNull
    @Column(name = "admin_id", nullable = false)
    private UUID adminId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "university", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Audience> audiences;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "university", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Faculty> faculties;

    @Override
    public UniversityResponse getResponse() {
        var responseBuilder = UniversityResponse.builder();
        super.initResponse(responseBuilder);
        return responseBuilder
                .accreditationLevel(accreditationLevel)
                .adminId(adminId)
                .build();
    }
}
