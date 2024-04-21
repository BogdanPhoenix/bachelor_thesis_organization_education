package com.bachelor.thesis.organization_education.requests.general.university;

import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.requests.general.abstracts.NameEntityRequest;
import com.bachelor.thesis.organization_education.requests.find.university.FacultyFindRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FacultyRequest extends NameEntityRequest {
    @NonNull
    private University university;

    @Override
    public FacultyFindRequest getFindRequest() {
        return FacultyFindRequest.builder()
                .university(university)
                .enName(this.getEnName())
                .build();
    }
}