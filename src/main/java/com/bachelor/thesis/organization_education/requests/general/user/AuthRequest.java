package com.bachelor.thesis.organization_education.requests.general.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import com.bachelor.thesis.organization_education.annotations.Trimmed;
import com.bachelor.thesis.organization_education.annotations.ValidEmail;
import com.bachelor.thesis.organization_education.annotations.ValidPassword;

/**
 * A request for user authorization in the system using a login and password.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AuthRequest {
    @NotNull
    @NotBlank(message = "The email should not be empty or contain only one space.")
    @Size(min = 3, max = 255, message = "Email should be between 3 and 255 characters")
    @ValidEmail
    @Trimmed
    private String username;

    @NotNull
    @NotBlank
    @ValidPassword
    @NotBlank(message = "The password should not be empty or contain only one space.")
    @Size(min = 8)
    @Trimmed
    private String password;
}
