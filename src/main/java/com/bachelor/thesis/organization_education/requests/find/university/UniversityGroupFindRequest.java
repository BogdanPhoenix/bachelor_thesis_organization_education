package com.bachelor.thesis.organization_education.requests.find.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Min;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.dto.Specialty;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UniversityGroupFindRequest implements FindRequest {
    @NotNull
    @NonNull
    private Specialty specialty;

    @NotNull
    @Min(1900)
    private Short yearStart;

    private boolean reducedForm;
}
