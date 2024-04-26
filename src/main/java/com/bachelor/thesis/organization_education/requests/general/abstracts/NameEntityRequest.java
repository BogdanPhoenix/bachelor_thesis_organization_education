package com.bachelor.thesis.organization_education.requests.general.abstracts;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import com.bachelor.thesis.organization_education.annotations.ValidNameEntity;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class NameEntityRequest implements InsertRequest, UpdateRequest {
    @NotNull(groups = InsertRequest.class)
    @NotBlank(groups = InsertRequest.class)
    @Size(max = 255, groups = {InsertRequest.class, UpdateRequest.class})
    @ValidNameEntity(groups = {InsertRequest.class, UpdateRequest.class})
    private String enName;

    @NotNull(groups = InsertRequest.class)
    @NotBlank(groups = InsertRequest.class)
    @Size(max = 255, groups = {InsertRequest.class, UpdateRequest.class})
    @ValidNameEntity(groups = {InsertRequest.class, UpdateRequest.class})
    private String uaName;

    public boolean enNameIsEmpty() {
        return enName == null || enName.isBlank();
    }

    public boolean uaNameIsEmpty() {
        return uaName == null || uaName.isBlank();
    }
}
