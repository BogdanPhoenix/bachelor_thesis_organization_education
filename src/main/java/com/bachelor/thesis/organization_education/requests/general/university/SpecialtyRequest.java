package com.bachelor.thesis.organization_education.requests.general.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.NameEntityRequest;
import com.bachelor.thesis.organization_education.requests.find.university.SpecialtyFindRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@GroupSequence({SpecialtyRequest.class, InsertRequest.class, UpdateRequest.class})
public class SpecialtyRequest extends NameEntityRequest {
    @Min(value = 0, groups = {InsertRequest.class, UpdateRequest.class})
    @Max(value = 999, groups = {InsertRequest.class, UpdateRequest.class})
    private short number;

    @Override
    public SpecialtyFindRequest getFindRequest() {
        return SpecialtyFindRequest.builder()
                .enName(getEnName())
                .uaName(getUaName())
                .number(number)
                .build();
    }

    public boolean numberIsEmpty() {
        return number <= 0;
    }
}
