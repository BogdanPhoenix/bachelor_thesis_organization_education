package com.bachelor.thesis.organization_education.requests.general.user;

import com.bachelor.thesis.organization_education.dto.Faculty;
import com.bachelor.thesis.organization_education.enums.AcademicDegree;
import com.bachelor.thesis.organization_education.enums.AcademicTitle;
import com.bachelor.thesis.organization_education.requests.find.user.LecturerFindRequest;
import com.bachelor.thesis.organization_education.requests.general.abstracts.Request;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LecturerRequest implements Request {
    @NonNull
    private AcademicTitle title;

    @NonNull
    private AcademicDegree degree;

    @NonNull
    private Faculty faculty;

    @NonNull
    private UUID userId;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public LecturerFindRequest getFindRequest() {
        return LecturerFindRequest.builder()
                .userId(userId)
                .build();
    }
}
