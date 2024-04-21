package com.bachelor.thesis.organization_education.requests.general.abstracts;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.annotations.ValidNameEntity;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class NameEntityRequest implements Request {
    @NotNull
    @ValidNameEntity
    @Size(max = 255)
    private String enName;
    @NotNull
    @ValidNameEntity
    @Size(max = 255)
    private String uaName;
}
