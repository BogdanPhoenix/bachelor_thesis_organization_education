package com.bachelor.thesis.organization_education.requests.insert.abstracts;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import com.bachelor.thesis.organization_education.annotations.ValidEmail;
import com.bachelor.thesis.organization_education.annotations.ValidNameUser;

/**
 * The abstract RegistrationRequest class is the base class for user registration request objects.
 * It extends the PasswordRequest class and adds additional fields to identify the user during registration.
 * This class uses Lombok annotations to automatically generate getters, setters, constructors,
 * toString(), equals(), and hashCode() methods, which simplifies the work with objects.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Validated
public abstract class RegistrationRequest extends PasswordRequest{
    @NotNull
    @NotBlank(message = "The email should not be empty or contain only one space.")
    @Size(min = 3, max = 255, message = "Email should be between 3 and 255 characters")
    @ValidEmail
    private String username;

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
}
