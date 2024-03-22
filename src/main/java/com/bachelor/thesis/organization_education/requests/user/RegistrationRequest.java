package com.bachelor.thesis.organization_education.requests.user;

import com.bachelor.thesis.organization_education.annotations.ValidNameUser;
import com.bachelor.thesis.organization_education.enums.Role;
import com.bachelor.thesis.organization_education.requests.abstract_type.PasswordRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotEmpty(message = "Email should not be empty")
    @Size(min = 3, max = 255, message = "Email should be between 3 and 255 characters")
    @Email(message = """
            The email address you provided: "%s" has not been validated. It must comply with the following rules:
                - The main body must contain only letters (uppercase or lowercase) of the Latin alphabet, numbers, or the symbols '_', '.', '+', or '-'.
                - The '@' symbol must be followed by at least one character, which can be a letter (uppercase or lowercase) of the Latin alphabet, a number, or a dash.
                - The dot (.) in the domain must be followed by at least one character, which can be a letter (uppercase or lowercase) of the Latin alphabet, a number, a dash or a period.
            Your email address should look like: "username@domain.domain_zone"
            """)
    private String username;

    @NotNull
    private Role role;

    @NotNull
    @NotEmpty(message = "First name should not be empty")
    @Size(min = 2, max = 255, message = "First name should be between 2 and 255 characters")
    @ValidNameUser
    private String firstName;

    @NotNull
    @NotEmpty(message = "Last name should not be empty")
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
