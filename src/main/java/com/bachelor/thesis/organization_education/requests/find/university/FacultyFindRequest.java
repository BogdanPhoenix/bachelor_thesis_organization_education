package com.bachelor.thesis.organization_education.requests.find.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.requests.find.abstracts.NameEntityFindRequest;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FacultyFindRequest extends NameEntityFindRequest {
    @NotNull
    @NonNull
    private University university;
}
