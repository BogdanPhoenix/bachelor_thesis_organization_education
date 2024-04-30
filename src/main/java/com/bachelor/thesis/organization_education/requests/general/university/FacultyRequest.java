package com.bachelor.thesis.organization_education.requests.general.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.GroupSequence;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.annotations.ValidNotUpdate;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.find.university.FacultyFindRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.NameEntityRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@GroupSequence({FacultyRequest.class, InsertRequest.class, UpdateRequest.class})
public class FacultyRequest extends NameEntityRequest {
    @ValidNotUpdate(groups = UpdateRequest.class)
    private University university;

    @Override
    public FacultyFindRequest getFindRequest() {
        return FacultyFindRequest.builder()
                .university(university)
                .enName(this.getEnName())
                .build();
    }
}