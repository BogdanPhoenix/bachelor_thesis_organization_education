package com.bachelor.thesis.organization_education.requests.update.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import com.bachelor.thesis.organization_education.enums.AccreditationLevel;
import com.bachelor.thesis.organization_education.requests.find.university.UniversityFindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.NameEntityUpdateRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UniversityUpdateRequest extends NameEntityUpdateRequest {
    private AccreditationLevel accreditationLevel;

    @Override
    public UniversityFindRequest getFindRequest() {
        return UniversityFindRequest.builder()
                .enName(getEnName())
                .uaName(getUaName())
                .build();
    }

    public boolean accreditationLevelIsEmpty() {
        return accreditationLevel == AccreditationLevel.EMPTY;
    }
}
