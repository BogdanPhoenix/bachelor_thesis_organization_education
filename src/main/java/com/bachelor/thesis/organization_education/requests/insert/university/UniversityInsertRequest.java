package com.bachelor.thesis.organization_education.requests.insert.university;

import com.bachelor.thesis.organization_education.enums.AccreditationLevel;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.NameEntityInsertRequest;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UniversityInsertRequest extends NameEntityInsertRequest {
    @NotNull
    private AccreditationLevel accreditationLevel;
}
