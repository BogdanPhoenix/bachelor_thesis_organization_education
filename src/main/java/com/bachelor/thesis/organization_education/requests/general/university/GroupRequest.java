package com.bachelor.thesis.organization_education.requests.general.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.dto.Lecturer;
import com.bachelor.thesis.organization_education.dto.Specialty;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import com.bachelor.thesis.organization_education.requests.find.university.GroupFindRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GroupRequest implements Request {
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
}
