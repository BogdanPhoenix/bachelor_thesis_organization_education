package com.bachelor.thesis.organization_education.requests.university;

import com.bachelor.thesis.organization_education.annotations.ValidRequestEmpty;
import com.bachelor.thesis.organization_education.enums.AccreditationLevel;
import com.bachelor.thesis.organization_education.requests.abstract_type.NameEntityRequest;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ValidRequestEmpty
public class UniversityInsertRequest extends NameEntityRequest {
    @NotNull
    private AccreditationLevel accreditationLevel;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || accreditationLevel == AccreditationLevel.EMPTY;
    }

    public static @NonNull UniversityInsertRequest empty() {
        return NameEntityRequest
                .initEmpty(builder())
                .accreditationLevel(AccreditationLevel.EMPTY)
                .build();
    }
}
