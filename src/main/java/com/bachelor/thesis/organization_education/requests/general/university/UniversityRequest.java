package com.bachelor.thesis.organization_education.requests.general.university;

import com.bachelor.thesis.organization_education.annotations.ValidRequestEmpty;
import com.bachelor.thesis.organization_education.enums.AccreditationLevel;
import com.bachelor.thesis.organization_education.enums.PatternTemplate;
import com.bachelor.thesis.organization_education.requests.general.abstracts.NameEntityRequest;
import com.bachelor.thesis.organization_education.requests.find.university.UniversityFindRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ValidRequestEmpty
public class UniversityRequest extends NameEntityRequest {
    @NotNull
    private AccreditationLevel accreditationLevel;
    @NotNull
    private UUID adminId;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || adminId.equals(createEmptyUUID());
    }

    @Override
    public UniversityFindRequest getFindRequest() {
        return UniversityFindRequest.builder()
                .enName(getEnName())
                .uaName(getUaName())
                .adminId(adminId)
                .build();
    }

    private static UUID createEmptyUUID() {
        return UUID.fromString(PatternTemplate.EMPTY_UUID.getValue());
    }
}
