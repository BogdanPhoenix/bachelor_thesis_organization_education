package com.bachelor.thesis.organization_education.requests.insert.user;

import com.bachelor.thesis.organization_education.dto.Group;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.RegistrationRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegistrationStudentRequest extends RegistrationRequest {
    @NotNull
    private Group group;
}
