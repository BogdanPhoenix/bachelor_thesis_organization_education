package com.bachelor.thesis.organization_education.requests.user;

import com.bachelor.thesis.organization_education.annotations.ValidEmail;
import com.bachelor.thesis.organization_education.annotations.ValidNameUser;
import com.bachelor.thesis.organization_education.enums.Role;
import com.bachelor.thesis.organization_education.requests.abstract_type.PasswordRequest;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegistrationRequest extends PasswordRequest {
    @NotNull
    @NotBlank(message = "The email should not be empty or contain only one space.")
    @Size(min = 3, max = 255, message = "Email should be between 3 and 255 characters")
    @ValidEmail
    private String username;

    @NotNull
    private Role role;

    @NotNull
    @NotBlank(message = "The first name should not be empty or contain only one space.")
    @Size(min = 2, max = 255, message = "First name should be between 2 and 255 characters")
    @ValidNameUser
    private String firstName;

    @NotNull
    @NotBlank(message = "The last name should not be empty")
    @Size(min = 2, max = 255, message = "Last name should be between 2 and 255 characters")
    @ValidNameUser
    private String lastName;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                username.isBlank() ||
                firstName.isBlank() ||
                lastName.isBlank() ||
                role == Role.EMPTY;
    }

    public static @NonNull RegistrationRequest empty() {
        return PasswordRequest
                .initEmpty(builder())
                .username("")
                .firstName("")
                .lastName("")
                .role(Role.EMPTY)
                .build();
    }
}
