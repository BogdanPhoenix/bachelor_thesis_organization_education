package com.bachelor.thesis.organization_education.requests.general.university;

import com.bachelor.thesis.organization_education.annotations.ValidRequestEmpty;
import com.bachelor.thesis.organization_education.dto.University;
import com.bachelor.thesis.organization_education.requests.find.abstracts.FindRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.NameEntityRequest;
import com.bachelor.thesis.organization_education.requests.find.university.FacultyFindRequest;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ValidRequestEmpty
public class FacultyRequest extends NameEntityRequest {
    @NonNull
    private University university;

    @Setter(AccessLevel.PRIVATE)
    private FindRequest findRequest = FacultyFindRequest.builder()
            .university(university)
            .enName(this.getEnName())
            .build();
}