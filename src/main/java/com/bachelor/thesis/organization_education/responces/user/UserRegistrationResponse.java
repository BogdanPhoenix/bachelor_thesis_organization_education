package com.bachelor.thesis.organization_education.responces.user;

import com.bachelor.thesis.organization_education.enums.Role;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserRegistrationResponse extends Response {
    @NonNull
    private String username;
    @NotNull
    private Role role;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    @Override
    public boolean isEmpty() {
        return username.isBlank() ||
                role == Role.EMPTY ||
                firstName.isBlank() ||
                lastName.isBlank();
    }
}
