package com.bachelor.thesis.organization_education.requests.insert.university;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.persistence.MappedSuperclass;
import com.bachelor.thesis.organization_education.requests.insert.abstracts.NameEntityInsertRequest;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SpecialtyInsertRequest extends NameEntityInsertRequest {
    @Min(0)
    @Max(999)
    private short number;
}
