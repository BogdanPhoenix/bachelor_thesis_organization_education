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
public class UserInfoResponse extends Response {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    @Override
    public boolean isEmpty() {
        return firstName.isBlank() ||
                lastName.isBlank();
    }

    public static @NonNull UserInfoResponse empty() {
        return Response
                .initEmpty(builder())
                .firstName("")
                .lastName("")
                .build();
    }
}
