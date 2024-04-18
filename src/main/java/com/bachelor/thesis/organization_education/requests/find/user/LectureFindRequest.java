package com.bachelor.thesis.organization_education.requests.find.user;

import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LectureFindRequest implements FindRequest {
    @NonNull
    private UUID userId;

    @Override
    public boolean skip() {
        return true;
    }
}
