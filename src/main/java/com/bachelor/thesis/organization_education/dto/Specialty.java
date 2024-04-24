package com.bachelor.thesis.organization_education.dto;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.bachelor.thesis.organization_education.dto.abstract_type.NameEntity;
import com.bachelor.thesis.organization_education.responces.university.SpecialtyResponse;
import com.bachelor.thesis.organization_education.requests.find.university.SpecialtyFindRequest;

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
@Table(name = "specialties")
public class Specialty extends NameEntity {
    @Column(name = "number", nullable = false, unique = true)
    private short number;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "specialty", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Group> groups;

    @Override
    public SpecialtyResponse getResponse() {
        var builder = SpecialtyResponse.builder();
        super.initResponse(builder);
        return builder.number(number)
                .build();
    }

    @Override
    public SpecialtyFindRequest getFindRequest() {
        return SpecialtyFindRequest.builder()
                .number(number)
                .build();
    }
}
