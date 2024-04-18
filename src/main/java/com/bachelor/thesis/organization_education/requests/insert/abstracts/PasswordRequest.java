package com.bachelor.thesis.organization_education.requests.insert.abstracts;

import com.bachelor.thesis.organization_education.annotations.PasswordMatches;
import com.bachelor.thesis.organization_education.annotations.ValidPassword;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@PasswordMatches(message = "The passwords you provided do not match.")
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
