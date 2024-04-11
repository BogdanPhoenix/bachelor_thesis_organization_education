package com.bachelor.thesis.organization_education.requests.university;

import com.bachelor.thesis.organization_education.annotations.ValidRequestEmpty;
import com.bachelor.thesis.organization_education.enums.PatternTemplate;
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
@ValidRequestEmpty
public class UniversityRequest extends UniversityInsertRequest {
    @NotNull
    private UUID adminId;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || adminId.equals(createEmptyUUID());
    }

    public static @NonNull UniversityRequest empty() {
        return UniversityInsertRequest
                .initEmpty(builder())
                .adminId(createEmptyUUID())
                .build();
    }

    private static UUID createEmptyUUID() {
        return UUID.fromString(PatternTemplate.EMPTY_UUID.getValue());
    }
}
