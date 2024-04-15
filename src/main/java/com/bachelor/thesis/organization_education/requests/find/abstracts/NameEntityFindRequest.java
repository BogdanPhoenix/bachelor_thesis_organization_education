package com.bachelor.thesis.organization_education.requests.find.abstracts;

import com.bachelor.thesis.organization_education.annotations.ValidNameEntity;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class NameEntityFindRequest implements FindRequest {
    @ValidNameEntity
    @Size(max = 255)
    private String enName;
    @ValidNameEntity
    @Size(max = 255)
    private String uaName;
}
