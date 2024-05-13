package com.bachelor.thesis.organization_education.requests.insert.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.enums.AcademicTitle;
import com.bachelor.thesis.organization_education.enums.AcademicDegree;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegistrationLecturerRequest extends RegistrationRequest {
    @NotNull
    private AcademicTitle title;

    @NotNull
    private AcademicDegree degree;

    @NotNull
    private Faculty faculty;
}
