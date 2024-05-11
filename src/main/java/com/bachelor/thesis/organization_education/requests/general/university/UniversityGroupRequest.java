package com.bachelor.thesis.organization_education.requests.general.university;

import lombok.*;
import jakarta.validation.GroupSequence;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import com.bachelor.thesis.organization_education.dto.Specialty;
import com.bachelor.thesis.organization_education.annotations.ValidYear;
import com.bachelor.thesis.organization_education.annotations.ProhibitValueAssignment;
import com.bachelor.thesis.organization_education.requests.general.abstracts.YearRange;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.find.university.UniversityGroupFindRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@GroupSequence({UniversityGroupRequest.class, InsertRequest.class, UpdateRequest.class})
public class UniversityGroupRequest implements InsertRequest, UpdateRequest, YearRange {
    @NotNull(groups = InsertRequest.class)
    private Lecturer curator;

    @NotNull(groups = InsertRequest.class)
    @ProhibitValueAssignment(groups = UpdateRequest.class)
    private Specialty specialty;

    @NotNull(groups = InsertRequest.class)
    private Faculty faculty;

    @NotNull(groups = {InsertRequest.class, UpdateRequest.class})
    @Min(value = 1900, groups = InsertRequest.class)
    @ValidYear(groups = UpdateRequest.class)
    private Short yearStart;

    @NotNull(groups = {InsertRequest.class, UpdateRequest.class})
    @Min(value = 1900, groups = InsertRequest.class)
    @ValidYear(groups = UpdateRequest.class)
    private Short yearEnd;

    private boolean reducedForm;

    @Override
    public UniversityGroupFindRequest getFindRequest() {
        return UniversityGroupFindRequest.builder()
                .specialty(specialty)
                .yearStart(yearStart)
                .reducedForm(reducedForm)
                .build();
    }
}
