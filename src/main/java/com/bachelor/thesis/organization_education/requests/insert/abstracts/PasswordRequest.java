package com.bachelor.thesis.organization_education.requests.insert.abstracts;

import com.bachelor.thesis.organization_education.annotations.PasswordMatches;
import com.bachelor.thesis.organization_education.annotations.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

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
