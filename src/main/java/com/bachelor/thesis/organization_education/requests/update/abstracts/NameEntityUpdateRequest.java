package com.bachelor.thesis.organization_education.requests.update.abstracts;

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
public abstract class NameEntityUpdateRequest implements UpdateRequest {
    @ValidNameEntity
    @Size(max = 255)
    private String enName;
    @ValidNameEntity
    @Size(max = 255)
    private String uaName;
}
