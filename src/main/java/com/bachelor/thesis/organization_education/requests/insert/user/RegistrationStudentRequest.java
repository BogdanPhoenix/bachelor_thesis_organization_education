package com.bachelor.thesis.organization_education.requests.insert.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.dto.UniversityGroup;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegistrationStudentRequest extends RegistrationRequest {
    @NotNull
    private UniversityGroup group;
}
