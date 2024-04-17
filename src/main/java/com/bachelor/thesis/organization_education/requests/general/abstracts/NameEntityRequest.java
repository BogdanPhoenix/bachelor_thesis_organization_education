package com.bachelor.thesis.organization_education.requests.general.abstracts;

import com.bachelor.thesis.organization_education.annotations.ValidNameEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @Override
    public boolean isEmpty() {
        return enName.isBlank() || uaName.isBlank();
    }
}
