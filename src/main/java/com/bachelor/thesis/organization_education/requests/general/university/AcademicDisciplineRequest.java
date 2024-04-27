package com.bachelor.thesis.organization_education.requests.general.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.NameEntityRequest;
import com.bachelor.thesis.organization_education.requests.find.university.AcademicDisciplineFindRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@GroupSequence({AcademicDisciplineRequest.class, InsertRequest.class, UpdateRequest.class})
public class AcademicDisciplineRequest extends NameEntityRequest {
    @Min(value = 0, groups = {InsertRequest.class, UpdateRequest.class})
    @Max(value = 100, groups = {InsertRequest.class, UpdateRequest.class})
    private short amountCredits;

    @Override
    public AcademicDisciplineFindRequest getFindRequest() {
        return AcademicDisciplineFindRequest.builder()
                .enName(getEnName())
                .uaName(getUaName())
                .build();
    }

    public boolean amountCreditsIsEmpty() {
        return amountCredits <= 0;
    }
}
