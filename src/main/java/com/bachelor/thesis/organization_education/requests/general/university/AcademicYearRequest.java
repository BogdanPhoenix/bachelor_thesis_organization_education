package com.bachelor.thesis.organization_education.requests.general.university;

import lombok.*;
import jakarta.validation.GroupSequence;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.annotations.ProhibitValueAssignment;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.find.university.AcademicYearFindRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@GroupSequence({AcademicYearRequest.class, InsertRequest.class, UpdateRequest.class})
public class AcademicYearRequest implements InsertRequest, UpdateRequest {
    @ProhibitValueAssignment(groups = {InsertRequest.class, UpdateRequest.class})
    private University university;

    @NotNull(groups = InsertRequest.class)
    @ProhibitValueAssignment(groups = UpdateRequest.class)
    @Min(value = 1900, groups = InsertRequest.class)
    @Max(value = 2100, groups = InsertRequest.class)
    private Short startYear;

    @NotNull(groups = InsertRequest.class)
    @ProhibitValueAssignment(groups = UpdateRequest.class)
    @Min(value = 1900, groups = InsertRequest.class)
    @Max(value = 2100, groups = InsertRequest.class)
    private Short endYear;

    @Override
    public AcademicYearFindRequest getFindRequest() {
        return AcademicYearFindRequest.builder()
                .university(university)
                .startYear(startYear)
                .endYear(endYear)
                .build();
    }
}
