package com.bachelor.thesis.organization_education.requests.general.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.GroupSequence;
import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.enums.AcademicTitle;
import com.bachelor.thesis.organization_education.enums.AcademicDegree;
import com.bachelor.thesis.organization_education.requests.find.user.LecturerFindRequest;
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
    private AcademicTitle title;
    private AcademicDegree degree;
    private Faculty faculty;
    private UUID userId;

    @Override
    public LecturerFindRequest getFindRequest() {
        return LecturerFindRequest.builder()
                .userId(userId)
                .build();
    }

    public boolean titleIsEmpty() {
        return title == null;
    }

    public boolean degreeIsEmpty() {
        return degree == null;
    }

    public boolean facultyIsEmpty() {
        return faculty == null;
    }
}
