package com.bachelor.thesis.organization_education.requests.update.user;

import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.enums.AcademicDegree;
import com.bachelor.thesis.organization_education.enums.AcademicTitle;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LectureUpdateRequest implements UpdateRequest {
    private AcademicTitle title;
    private AcademicDegree degree;
    private Faculty faculty;

    @Override
    public FindRequest getFindRequest() {
        return null;
    }
}
