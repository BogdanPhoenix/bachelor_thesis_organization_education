package com.bachelor.thesis.organization_education.responces.user;

import com.bachelor.thesis.organization_education.responces.abstract_type.Response;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends Response {
    private static final String EMPTY_TEMPLATE = "00000000-0000-0000-0000-000000000000";

    @NotNull
    private UUID userId;

    @Override
    public boolean isEmpty() {
        return userId == UUID.fromString(EMPTY_TEMPLATE);
    }

    public static @NonNull UserResponse empty() {
        return Response
                .initEmpty(builder())
                .userId(UUID.fromString(EMPTY_TEMPLATE))
                .build();
    }
}