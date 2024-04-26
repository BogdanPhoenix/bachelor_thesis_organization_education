package com.bachelor.thesis.organization_education.requests.general.university;

import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import jakarta.validation.GroupSequence;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.enums.AccreditationLevel;
import com.bachelor.thesis.organization_education.requests.general.abstracts.NameEntityRequest;
import com.bachelor.thesis.organization_education.requests.find.university.UniversityFindRequest;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@GroupSequence({UniversityRequest.class, InsertRequest.class, UpdateRequest.class})
public class UniversityRequest extends NameEntityRequest {
    @NotNull(groups = InsertRequest.class)
    private AccreditationLevel accreditationLevel;
    private UUID adminId;

    @Override
    public UniversityFindRequest getFindRequest() {
        return UniversityFindRequest.builder()
                .enName(getEnName())
                .uaName(getUaName())
                .adminId(adminId)
                .build();
    }

    public boolean accreditationLevelIsEmpty() {
        return accreditationLevel == null || accreditationLevel == AccreditationLevel.EMPTY;
    }
}
