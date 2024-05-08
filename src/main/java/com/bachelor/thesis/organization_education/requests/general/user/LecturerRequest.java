package com.bachelor.thesis.organization_education.requests.general.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotNull;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.enums.AcademicTitle;
import com.bachelor.thesis.organization_education.enums.AcademicDegree;
import com.bachelor.thesis.organization_education.requests.find.user.UserFindRequest;
import com.bachelor.thesis.organization_education.annotations.ProhibitValueAssignment;
import com.bachelor.thesis.organization_education.requests.update.abstracts.UpdateRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.InsertRequest;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@GroupSequence({LecturerRequest.class, InsertRequest.class, UpdateRequest.class})
public class LecturerRequest implements InsertRequest, UpdateRequest {
    @ProhibitValueAssignment(groups = UpdateRequest.class)
    private UUID userId;

    @NotNull(groups = InsertRequest.class)
    private AcademicTitle title;

    @NotNull(groups = InsertRequest.class)
    private AcademicDegree degree;

    @NotNull(groups = InsertRequest.class)
    private Faculty faculty;

    @Override
    public UserFindRequest getFindRequest() {
        return UserFindRequest.builder()
                .userId(userId)
                .build();
    }
}
