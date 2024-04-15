package com.bachelor.thesis.organization_education.responces.university;

import com.bachelor.thesis.organization_education.responces.abstract_type.NameEntityResponse;
import jakarta.persistence.MappedSuperclass;
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
public class FacultyResponse extends NameEntityResponse {
    @NonNull
    private UniversityResponse university;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || university.isEmpty();
    }

    public static @NonNull FacultyResponse empty() {
        return NameEntityResponse
                .initEmpty(builder())
                .university(UniversityResponse.empty())
                .build();
    }
}
