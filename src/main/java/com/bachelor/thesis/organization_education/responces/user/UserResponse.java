package com.bachelor.thesis.organization_education.responces.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import org.keycloak.representations.idm.UserRepresentation;
import com.bachelor.thesis.organization_education.responces.abstract_type.Response;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserResponse {
    @NonNull
    private UserRepresentation userInfo;

    @NonNull
    private Response otherInfo;
}
