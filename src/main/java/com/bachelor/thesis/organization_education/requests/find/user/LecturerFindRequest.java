package com.bachelor.thesis.organization_education.requests.find.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
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
public class LecturerFindRequest implements FindRequest {
    @NotNull
    @NonNull
    private UUID userId;

    @Override
    public boolean skip() {
        return true;
    }
}
