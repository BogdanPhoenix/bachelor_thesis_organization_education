package com.bachelor.thesis.organization_education.requests.user;

import com.bachelor.thesis.organization_education.annotations.ValidNameUser;
import com.bachelor.thesis.organization_education.requests.abstract_type.Request;
import com.bachelor.thesis.organization_education.responces.user.UserResponse;
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
public class UserInfoRequest extends Request {
    @NotNull
    private UserResponse user;

    @NotNull
    @ValidNameUser
    @NotEmpty
    private String firstName;

    @NotNull
    @ValidNameUser
    @NotEmpty
    private String lastName;

    @Override
    public boolean isEmpty() {
        return user.isEmpty() ||
                firstName.isBlank() ||
                lastName.isBlank();
    }

    public static @NonNull UserInfoRequest empty() {
        return builder()
                .user(UserResponse.empty())
                .firstName("")
                .lastName("")
                .build();
    }
}
