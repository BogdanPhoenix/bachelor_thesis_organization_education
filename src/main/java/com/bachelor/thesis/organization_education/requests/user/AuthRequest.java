package com.bachelor.thesis.organization_education.requests.user;

import com.bachelor.thesis.organization_education.annotations.ValidEmail;
import com.bachelor.thesis.organization_education.annotations.ValidPassword;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * A request for user authorization in the system using a login and password.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class AuthRequest extends Request {
    @NotNull
    @NotBlank(message = "The email should not be empty or contain only one space.")
    @Size(min = 3, max = 255, message = "Email should be between 3 and 255 characters")
    @ValidEmail
    private String username;

    @NotNull
    @NotBlank
    @ValidPassword
    @NotBlank(message = "The password should not be empty or contain only one space.")
    @Size(min = 8)
    private String password;

    @Override
    public boolean isEmpty() {
        return false;
    }
}
