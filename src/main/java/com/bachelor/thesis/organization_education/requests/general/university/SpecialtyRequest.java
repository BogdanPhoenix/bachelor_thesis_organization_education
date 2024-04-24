package com.bachelor.thesis.organization_education.requests.general.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import com.bachelor.thesis.organization_education.requests.general.abstracts.NameEntityRequest;
import com.bachelor.thesis.organization_education.requests.find.university.SpecialtyFindRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SpecialtyRequest extends NameEntityRequest {
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
