package com.bachelor.thesis.organization_education.requests.find.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AcademicYearFindRequest implements FindRequest {
    @NotNull
    @NonNull
    private University university;

    @NotNull
    @NonNull
    private Short startYear;

    @NotNull
    @NonNull
    private Short endYear;
}
