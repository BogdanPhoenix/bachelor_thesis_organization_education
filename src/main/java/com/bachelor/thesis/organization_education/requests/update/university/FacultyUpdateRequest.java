package com.bachelor.thesis.organization_education.requests.update.university;

import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.find.university.FacultyFindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.NameEntityUpdateRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FacultyUpdateRequest extends NameEntityUpdateRequest {
    private University university;

    @Setter(AccessLevel.PRIVATE)
    private FindRequest findRequest = new FacultyFindRequest();
}
