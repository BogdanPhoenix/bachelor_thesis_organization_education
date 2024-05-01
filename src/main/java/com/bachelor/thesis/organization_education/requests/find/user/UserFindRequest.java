package com.bachelor.thesis.organization_education.requests.find.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserFindRequest implements FindRequest {
    @NonNull
    private UUID userId;
}
