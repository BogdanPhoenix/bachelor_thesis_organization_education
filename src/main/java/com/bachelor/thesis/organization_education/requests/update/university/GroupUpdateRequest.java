package com.bachelor.thesis.organization_education.requests.update.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import com.bachelor.thesis.organization_education.dto.Specialty;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.find.university.GroupFindRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GroupUpdateRequest implements UpdateRequest {
    private Lecturer curator;
    private Specialty specialty;
    private Faculty faculty;
    private short yearStart;
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
