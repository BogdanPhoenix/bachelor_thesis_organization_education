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
import com.bachelor.thesis.organization_education.annotations.ValidNotUpdate;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.find.university.GroupFindRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@GroupSequence({GroupRequest.class, InsertRequest.class, UpdateRequest.class})
public class GroupRequest implements InsertRequest, UpdateRequest {
    @NotNull(groups = InsertRequest.class)
    private Lecturer curator;

    @NotNull(groups = InsertRequest.class)
    @ValidNotUpdate(groups = UpdateRequest.class)
    private Specialty specialty;

    @NotNull(groups = InsertRequest.class)
    private Faculty faculty;

    @Min(value = 1900, groups = InsertRequest.class)
    @ValidYear(groups = UpdateRequest.class)
    private short yearStart;

    @Min(value = 1900, groups = InsertRequest.class)
    @ValidYear(groups = UpdateRequest.class)
    private short yearEnd;

    private boolean reducedForm;

    @Override
    public GroupFindRequest getFindRequest() {
        return GroupFindRequest.builder()
                .specialty(specialty)
                .yearStart(yearStart)
                .reducedForm(reducedForm)
                .build();
    }

    public boolean curatorIsEmpty() {
        return curator == null;
    }

    public boolean specialtyIsEmpty() {
        return specialty == null;
    }

    public boolean facultyIsEmpty() {
        return faculty == null;
    }

    public boolean yearStartIsEmpty() {
        return yearStart <= 0;
    }

    public boolean yearEndIsEmpty() {
        return yearEnd <= 0;
    }
}
