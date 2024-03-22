package com.bachelor.thesis.organization_education.responces.user;

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
public class RegisteredResponse extends Response {
    @NotNull
    private String accessToken;
    @NotNull
    private String username;

    @Override
    public boolean isEmpty() {
        return accessToken.isBlank() ||
                username.isBlank();
    }
}