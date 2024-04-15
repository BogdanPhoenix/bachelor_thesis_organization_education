package com.bachelor.thesis.organization_education.requests.update.university;

import com.bachelor.thesis.organization_education.enums.AccreditationLevel;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.find.university.UniversityFindRequest;
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
public class UniversityUpdateRequest extends NameEntityUpdateRequest {
    private AccreditationLevel accreditationLevel;

    @Setter(AccessLevel.PRIVATE)
    private FindRequest findRequest = UniversityFindRequest.builder()
            .enName(getEnName())
            .uaName(getUaName())
            .build();
}
