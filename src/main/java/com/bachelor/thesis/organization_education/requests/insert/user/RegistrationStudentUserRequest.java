package com.bachelor.thesis.organization_education.requests.insert.user;

import com.bachelor.thesis.organization_education.annotations.ValidRequestEmpty;
import com.bachelor.thesis.organization_education.dto.Group;
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
@ValidRequestEmpty
public class RegistrationStudentUserRequest extends RegistrationUserRequest {
    @NotNull
    private Group group;
}
