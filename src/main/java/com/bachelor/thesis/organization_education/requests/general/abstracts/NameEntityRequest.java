package com.bachelor.thesis.organization_education.requests.general.abstracts;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import com.bachelor.thesis.organization_education.annotations.ValidNameEntity;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;

/**
 * The NameEntityRequest abstract class is the base class for request objects associated with entity names.
 * This class uses Lombok annotations to automatically generate getters, setters, toString(), constructors,
 * as well as equals() and hashCode() methods, which simplifies the work with objects.
 * It also has the ability to build objects using the “Builder” pattern through the @SuperBuilder annotation.
 * The class contains two fields for entity names - enName (English) and uaName (Ukrainian).
 */
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
