package com.bachelor.thesis.organization_education.requests.user;

import com.bachelor.thesis.organization_education.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * A request for the university administrator to create an account for a university user.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegistrationOtherUserRequest extends RegistrationRequest {
    @NotNull
    private Role role;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || role == Role.EMPTY;
    }
}
