package com.bachelor.thesis.organization_education.requests.insert.abstracts;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import com.bachelor.thesis.organization_education.annotations.ValidPassword;
import com.bachelor.thesis.organization_education.annotations.PasswordMatches;

/**
 * The abstract PasswordRequest class is the base class for password-related request objects,
 * used to create or change user passwords.
 * This class uses Lombok annotations to automatically generate getters, setters, constructors,
 * toString(), equals(), and hashCode() methods, which simplifies the work with objects.
 * Annotations are also used to validate passwords:
 * - The @PasswordMatches annotation checks for password matches and provides the user with a message if there is a mismatch.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@PasswordMatches(message = "The passwords you provided do not match.")
@Validated
public abstract class PasswordRequest {
    @NotNull
    @NotBlank
    @ValidPassword
    @Size(min = 8)
    private String password;

    @NotNull
    @NotBlank
    private String matchingPassword;
}
