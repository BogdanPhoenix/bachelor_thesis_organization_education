package com.bachelor.thesis.organization_education.requests.insert.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.persistence.MappedSuperclass;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import com.bachelor.thesis.organization_education.dto.Specialty;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.InsertRequest;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class GroupInsertRequest implements InsertRequest {
    @NonNull
    private Lecturer curator;
    @NonNull
    private Specialty specialty;
    @NonNull
    private Faculty faculty;
    @Min(2000)
    @Max(2100)
    private short yearStart;
    @Min(2000)
    @Max(2100)
    private short yearEnd;
    private boolean reducedForm;
}
