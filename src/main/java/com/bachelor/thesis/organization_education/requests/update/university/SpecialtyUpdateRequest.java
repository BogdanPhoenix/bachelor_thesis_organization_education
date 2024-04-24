package com.bachelor.thesis.organization_education.requests.update.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import com.bachelor.thesis.organization_education.requests.find.university.SpecialtyFindRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.NameEntityUpdateRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SpecialtyUpdateRequest extends NameEntityUpdateRequest {
    @Min(0)
    @Max(999)
    private short number;

    @Override
    public SpecialtyFindRequest getFindRequest() {
        return SpecialtyFindRequest.builder()
                .enName(getEnName())
                .uaName(getUaName())
                .number(number)
                .build();
    }
}
