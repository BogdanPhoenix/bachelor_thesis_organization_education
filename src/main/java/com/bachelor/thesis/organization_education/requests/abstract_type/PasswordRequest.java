package com.bachelor.thesis.organization_education.requests.abstract_type;

import com.bachelor.thesis.organization_education.annotations.PasswordMatches;
import com.bachelor.thesis.organization_education.annotations.ValidPassword;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@PasswordMatches(message = "The passwords you provided do not match.")
public abstract class PasswordRequest extends Request {
    @NotNull
    @NotEmpty
    @ValidPassword
    private String password;
    @NotNull
    @NotEmpty
    private String matchingPassword;

    @Override
    public boolean isEmpty() {
        return password.isBlank() ||
                matchingPassword.isBlank();
    }

    protected static <T extends PasswordRequestBuilder<?, ?>> @NonNull T initEmpty(@NonNull T builder) {
        builder.password("")
                .matchingPassword("");
        return builder;
    }
}
