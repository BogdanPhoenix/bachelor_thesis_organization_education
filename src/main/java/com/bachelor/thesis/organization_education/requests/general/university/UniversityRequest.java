package com.bachelor.thesis.organization_education.requests.general.university;

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
public class UniversityRequest extends NameEntityRequest {
    @NotNull
    private AccreditationLevel accreditationLevel;
    @NotNull
    private UUID adminId;

    @Override
    public UniversityFindRequest getFindRequest() {
        return UniversityFindRequest.builder()
                .enName(getEnName())
                .uaName(getUaName())
                .adminId(adminId)
                .build();
    }
}
