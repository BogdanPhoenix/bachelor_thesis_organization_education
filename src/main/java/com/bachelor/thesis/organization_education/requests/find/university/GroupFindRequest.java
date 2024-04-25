package com.bachelor.thesis.organization_education.requests.find.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.persistence.MappedSuperclass;
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
public class GroupFindRequest implements FindRequest {
    @NonNull
    private Specialty specialty;
    @Min(2000)
    @Max(2100)
    private short yearStart;
    private boolean reducedForm;
}
