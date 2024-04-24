package com.bachelor.thesis.organization_education.requests.find.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.persistence.MappedSuperclass;
import com.bachelor.thesis.organization_education.requests.find.abstracts.NameEntityFindRequest;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SpecialtyFindRequest extends NameEntityFindRequest {
    @Min(0)
    @Max(999)
    private short number;
}
